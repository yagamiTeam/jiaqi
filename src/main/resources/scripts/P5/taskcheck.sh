#!/bin/bash
#################################################
# ProgramName	: taskcheck.sh					#
#												#
# Version		: v1.0							#
#												#
# Description	: 批量修改自动任务未收到990返回时登记簿状态	#
#												#
# Author		: 高健							#
#################################################

LOOPTIME=60

TASKFILE=$HOME/scripts/taskcheck.txt

SQL_EXEC()
{
sqlplus -S ${connstr}<<!
set head off
set serveroutput off
set trimspool on
set echo off
set pagesize 0
set feedback off
set term off
${sqlcomm}
quit
!
}

connstr=${ZFDB_CONN}

while [ 1 ]
do
	grep "*" $TASKFILE|awk -F '*' '{print $2}'|while read LINE
	do
		#获取任务名、表名
		tsknm=`echo $LINE|awk '{print $1}'`
		tblnm=`echo $LINE|awk '{print $3}'`
		
		#报文类型，针对小额组包各报文控制表特殊处理
		if [ $tsknm == "P5033D003" -o $tsknm == "P5033D007" -o $tsknm == "P5033D011" ]
		then
			#获取报文类型
			msgtp=`echo $LINE|awk '{print $4}|'|tr -d " "`
			
			#获取当前时、分、秒
			lt_h=`date "+%H"`
			lt_m=`date "+%M"`
			lt_s=`date "+%S"`
			
			#获取小额往账组包控制表时间间隔(分钟为单位)
			sqlcomm="select TM_ITRV from MICR_NSTACCT_PKG_CTRL_TBL where MSGRP_TP='$msgtp';"
			itval=`SQL_EXEC`
			if [ $lt_m == "00" ]
			then
				bf_h=`expr "$lt_h - 1"`
				bf_m="59"
				bf_s=$lt_s
			else
				bf_h=$lt_h
				bf_m=`expr $lt_m - $itval`
				bf_s=$lt_s
			fi
			
			bftime=${bf_h}${bf_m}${bf_s}
			
			#超过最大发送册数仍未收到990回执的直接置为失败
			sqlcomm="update $tblnm set MSGRP_PCSST='05' where MSGRP_RCV_SND_IDCD='02' and MSGRP_PCSST='09' and SND_CNT>=3 and MSGRP_SND_TM < $bftime;"
			SQL_EXEC
		else
			#超过最大发送次数认为收到990回执的直接置为失败
			sqlcomm="update $tblnm set MSGRP_PCSST='05' where MSGRP_RCV_SND_IDCD='02' and MSGRP_PCSST='09' and SND_CNT>=3;"
			SQL_EXEC
		fi
		
		#未超过最大发送次数且未收到990回执的置为待发送重新发
		sqlcomm="update $tblnm set MSGRP_PCSST='01' where MSGRP_RCV_SND_IDCD='02' and MSGRP_PCSST='09' and SND_CNT<3;"
		SQL_EXEC
	done
	#break
	
	sleep ${LOOPTIME}
	
	continue
done
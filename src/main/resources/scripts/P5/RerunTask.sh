#!/bin/sh
#日终任务重做脚本

sysDateTime=`date +%Y%m%d%H%M%S`
sysDate=`date +%Y%m%d`
sysTime=`date +%H%M%S`

#输出信息函数
EchoMsg()
{
	logLevel=$1
	
	if [ "$logLevel" = "1" ]
	then
		levelStr="INFO"
	elif [ "$logLevel" = "2" ]
	then
		levelStr="WARN"
	else
		levelStr="ERROR"
	fi
	
	echo [$levelStr] $2
}

RerunTaskMenu()
{
	clear
	echo ""
	echo ""
	echo "		------------------日终任务菜单------------------"
	echo "			1 大额日终任务								"
	echo "			2 小额日终任务								"
	echo "			3 超网日终任务								"
	echo "			4 跨境支付日终任务								"
	echo "			5 村行日终任务								"
	echo "			6 日终状态查询								"
	echo "			q 退出									"
	echo "		--------------------------------------------"
}

RouteTaskMenu()
{
	clear
	echo ""
	echo ""
	echo "		------------------${1}日终任务菜单--------------"
	echo "			1 修改清算日期								"
	echo "			2 修改日终状态								"
	echo "			3 生成本地汇总对账数据和发送对账通知					"
	echo "			4 生成本地汇总对账数据							"
	echo "			5 发送汇总对账通知								"
	echo "			6 生成本地明显对账数据							"
	echo "			7 发送明细对账通知								"
	echo "			8 发送汇总记账通知								"
	echo "			9 任务状态查询								"
	echo "			10 日终数据查询								"
	echo "			11 日终流程执行状态							"
	echo "			q 退出									"
	echo "		--------------------------------------------"
}

QueryCCPCDataMenu()
{
	clear
	echo ""
	echo ""
	echo "		------------------${1}日终查询清单--------------"
	echo "			1 人行汇总对账数据查询							"
	echo "			2 人行明细对账数据查询							"
	echo "			3 人行调账数据查询								"
	echo "			q 退出									"
	echo "		--------------------------------------------"
}

VBANKTaskMenu() 
{
	clear
	echo ""
	echo ""
	echo "		------------------村行日终任务菜单----------------"
	echo "			1 生成大额对账文件								"
	echo "			2 生成小额对账文件								"
	echo "			q 退出									"
	echo "		--------------------------------------------"
}

QuerySettleStatusMenu()
{
	clear
	echo "系统当前时间: $showData $showTime"
	echo ""
	echo -e "		系统名称\t系统简\t清算日期\t系统状态\t当前任务"
	echo "		------------------------------------------------------------------------"
	echo -e "		大额\t\t${HVPSName}\t\t${HVPSDate}\t${HVPSShowStatus}\t\t${HVPSStatus}"
	echo -e "		小额\t\t${BEPSName}\t\t${BEPSDate}\t${BEPSShowStatus}\t\t${BEPSStatus}"
	echo -e "		超网\t\t${IBPSName}\t\t${IBPSDate}\t${IBPSShowStatus}\t\t${IBPSStatus}"
	echo -e "		跨境支付\t${CIPSName}\t\t${CIPSDate}\t${CIPSShowStatus}\t\t${CIPSStatus}"
	echo "		------------------------------------------------------------------------"
}

CheckInputDate()
{
	yesorno=`echo "$1" | awk '{if($0 ~ "^[0-9]*$")
									print 1
								else
									print 0
								}'
	`
	if [ $yesorno -eq 0 ]
	then
		EchoMsg "3" "输入的日期[$inPutValue]中含有非法字符"
		return 1
	fi
	
	yesorno=`echo "$1" | awk '{if(length($0) == 8)
									print 1
								else
									print 0
								}'
	`
	if [ $yesorno -eq 0 ]
	then
		EchoMsg "3" "输入的日期[$inPutValue]长度不正确"
		return 1
	fi
	
	if [ $inPutValue -gt $sysDate ]
	then
		EchoMsg "3" "输入的日期[$inPutValue]大于系统日期"
		return 1
	fi
	
	return 0
}

UpdateClearDate()
{
	count=0
	echo -e "当前清算日期为:\c"
	
echo `sqlplus -s $ZFDB_CONN<<!
select to_char(CLRG_DT,'yyyymmdd') from EOD_STM_ST_TBL where STM_ID='$1';
!`|awk '{print "["$3"]"}'

	while true
	do
		if [ $count -eq 3 ]
		then
			echo ""
			EchoMsg "2" "输入错误次数超过3次，回车继续...	"
			return 1
		fi
		
		echo -e "请输入清算日期: \c"
		read inPutValue
		
		CheckInpitDate $inPutValue
		if [ $? -ne 0 ]
		then
			count=`expr $count + 1`
			continue
		fi
		
		echo -e "确认将清算日期改为[$inPutValue]?(Y/N)	\c"
		read yesorno
		if [ "$yesorno" = "N" ] || [ "$yesorno" = "n" ]
		then
			continue
		fi
		
returnValue=`sqlplus -s $ZFDB_CONN<<! 2>/dev/null
update EOD_STM_ST_TBL set CLRG_DT=to_date('$inPutValue','yyyymmdd') where STM_ID='$1';
!`

returnValue=`sqlplus -s $ZFDB_CONN<<! 2>/dev/null
select to_char(CLRG_DT, 'yyyymmdd') from EOD_STM_ST_TBL where STM_ID='$1';
!`

		clearDate=`echo $returnValue|awk '{print $3}'`
		if [ "$inPutValue" != "$clearDate" ]
		then
			EchoMsg "3" "修改日期失败，清算日期为[$clearDate]"
		else
			EchoMsg "1" "修改日期成功，清算日期为[$clearDate]"
			break
		fi
	done
}

UpdateDEStatus()
{
	echo -e "当前日终状态为:\c"

echo `sqlplus -s $ZFDB_CONN<<!
select DYTM_EOD_STCD from EOD_STM_ST_TBL where STM_ID='$1';
!`|awk '{print "["3"]"}'
	
	echo -e "请输入新的状态:	\c"
	read inPutValue
	
	echo -e "确认将日终状态改为[$inPutValue]?(Y/N)	\c"
	read yesorno
	if [ "$yesorno" = "N" ] || [ "$yesorno" = "n" ]
	then
		return 1
	fi

returnValue=`sqlplus -s $ZFDB_CONN<<! 2>/dev/null
update EOD_STM_ST_TBL set DYTM_EOD_STCD='$inPutValue' where STM_ID='$1';
!`

returnValue=`sqlplus -s $ZFDB_CONN<<! 2>/dev/null
select DYTM_EOD_STCD from EOD_STM_ST_TBL where STM_ID='$1';
!`

	DEStat=`echo $returnValue|awk '{print $3}'`
	if [ "$inPutValue" != "$DEStat" ]
	then
		EchoMsg "3" "修改状态失败，日终状态为[$DEStat]"
	else
		EchoMsg "1" "修改状态成功，日终状态为[$DEStat]"
	fi
}

CheckClearDate()
{
returnValue=`sqlplus -s $ZFDB_CONN<<! 2>/dev/null
select to_char(CLRG_DT,'yyyymmdd') from EOD_STM_ST_TBL where STM_ID='$1';
!`

	clearDate=`echo $returnValue|awk '{print $3}'`
	
	echo -e "当前的清算日期为[$clearDate]，是否继续?(Q/q退出，其他键继续)\c"
	read yesorno
	if [ "$yesorno" = "Q" ] || [ "$yesorno" = "q" ]
	then
		return 1
	fi
	
	return 0
}

CheckSingleTashStatus()
{
returnValue=`sqlplus -s $ZFDB_CONN<<! 2>/dev/null
select CLRG_DT from EOD_STM_ST_TBL where STM_ID='$1';	
!`

	clearDate=`echo $returnValue|awk '{print $3}'`

returnValue=`sqlplus -s $ZFDB_CONN<<! 2>/dev/null
select SPCL_TSK_DSC, substr(PYST_DTBS_TM,1,8),PCSG_STCD from EOD_DTBS_SCAN_TBL where TASK_NM='$1';
!`

	clearName=`echo $returnValue|awk '{print $7}'`
	runDate=`echo $returnValue|awk '{print $8}'`
	status=`echo $returnValue|awk '{print $9}'`
	if [ $status -eq 2 ]
	then
		EchoMsg "2" "任务[$clearName]任务正在执行中..."
		return 1
	fi
	
	return 0
}

UpdateSingleTaskStatus()
{
returnValue=`sqlplus -s $ZFDB_CONN`<<! 2>/dev/null
update EOD_DTBS_SCAN_TBL set PCSG_STCD='1' where TASK_NM='$1';
!`
}

CheckTaskStatus()
{
	runFlag=0
	
	for taskName in $*
	do
		CheckSingleTaskStatus $taskName
		if [ $? -ne 0 ]
		then
			runFlag=1
		fi
	done
	
	if [ $runFlag -eq 1 ]
	then
		echo -e "是否继续?(Q/q退出，其他键继续)\c"
		read yesorno
		if [ "$yesorno" = "Q" ] || [ "$yesorno" = "q" ]
		then
			return 1
		fi
	fi
	
	UpdateSingleTaskStatus $1
	
	return 0
}

CheckSvcName()
{
	EchoMsg "1" "注意查看[$/HOME/log/rh_end/Task.log.$sysDate]日志中的下列任务是否成功完成: "
	
	for svcName in $*
	do
		echo "$svcName"
	done
}

CreateTotalFileAndSendNT()
{
	CheckClearDate $1
	if [ $? -ne 0 ]
	then
		return 0
	fi
	
	if [ $1 = "HVPS" ]
	then
		Tasklist="DAYEND_CREATEHVPS01 DAYEND_SENDHVPSNT01"
	elif [ $1 = "BEPS" ]
	then
		Tasklist="DAYEND_CREATEBEPS01 DAYEND_SENDBEPSNT01"
	elif [ $1 = "IBPS" ]
	then
		Tasklist="DAYEND_CREATEIBPS01 DAYEND_SENDIBPSNT01"
	fi
	
	CheckTaskStatus $Tasklist
	if [ $? -ne 0 ]
	then
		return 0
	fi
	
	EchoMsg "1" "生成${CNNAME}汇总对账文件任务和发送${CNNAME}汇总记账通知任务已开始重跑..."
	
	CheckSvcName $Tasklist
}

CreateTotalFile()
{
	CheckClearDate $1
	if [ $? -ne 0 ]
	then
		return 0
	fi
	
	if [ $1 = "HVPS" ]
	then
		Tasklist="DAYEND_CREATEHVPS01_SND"
	elif [ $1 = "BEPS" ]
	then
		Tasklist="DAYEND_CREATEBEPS01_SND"
	elif [ $1 = "IBPS" ]
	then
		Tasklist="DAYEND_CREATEIBPS01_SND"
	fi
	
	EchoMsg "1" "生成${CNNAME}汇总对账文件任务已开始重跑..."
	
	CheckSvcName $Tasklist
}

SendTotalReconNotice()
{
	CheckClearDate $1
	if [ $? -ne 0 ]
	then
		return 0
	fi
	
	if [ $1 = "HVPS" ]
	then
		Tasklist="DAYEND_SENDHVPSNT01"
	elif [ $1 = "BEPS" ]
	then
		Tasklist="DAYEND_SENDBEPSNT01"
	elif [ $1 = "IBPS" ]
	then
		Tasklist="DAYEND_SENDIBPSNT01"
	elif [ $1 = "CIPS" ]
	then
		Tasklist="DAYEND_CHKCIPS_01"
		DeleteCCPCDetailData $1
	fi
	
	CheckTaskStatus $Tasklist
	if [ $? -ne 0 ]
	then
		return 0
	fi
	
	EchoMsg "1" "发送${CNNAME}汇总对账通知任务已开始重跑..."
	
	CheckSvcName $Tasklist
}

DeleteTableByClearDate()
{
returnValue=`sqlplus -s $STDB_CONN<<! 2>/dev/null
delete from $1 where CLRG_DT=TO_DATE('$2','yyyymmdd');
commit;
!`
}

DeleteTableByClearDate1()
{
returnValue=`sqlplus -s $ZFDB_CONN<<! 2>/dev/null
update $1 set MSGRP_PCSST='02' where MSGRP_RCNCL_DT=TO_DATE('$2','yyyymmdd') and MSGRP_PCSST != '02';
commit;
!`
}

DeletePartByClearDate()
{
	PART_NAME=`sqlplus -s $STDB_CONN @getPart.sql $1 $2`
	
	DROP_PART=`sqlplus -s $STDB_CONN @dropPart.sql $1 ${PART_NAME}`
}

DeleteCCPCDetailData()
{
returnValue=`sqlplus -s $ZFDB_CONN<<! 2>/dev/null
select to_char(CLRG_DT,'yyyymmdd') from EOD_STM_ST_TBL where STM_ID='$1';
!`
	
	clearDate=`echo $returnValue|awk '{print $3}'`
	
	if [ $1 = "HVPS" ]
	then
		DeleteTableByClearDate "bigamt_dtl_rcncl_rgs" "$clearDate"
		DeletePartByClearDate "bigamt_dtl_rcncl_rgs" "$clearDate"
	elif [ $1 = "BEPS" ]
	then
		DeleteTableByClearDate "micr_bag_vrf_rgs" "$clearDate"
		DeletePartByClearDate "micr_bsndtl_vrf_rgs_aflt_tbl" "$clearDate"
		DeletePartByClearDate "micr_inf_dtl_vrf_rgs_aflt_tbl" "$clearDate"
	elif [ $1 = "IBPS" ]
	then
		DeleteTableByClearDate "ebnkg_bsndtl_vrf_rgs" "$clearDate"
		DeletePartByClearDate "ebnkg_bsndtl_vrf_rgs_aflt_tbl" "$clearDate"
	elif [ $1 = "CIPS" ]
	then
		DeleteTableByClearDate1 "CIPS_XBBD_VA_Anr_RgstBook" "$clearDate"
	fi
}

CreateDetailFile()
{
	CheckClearDate $1
	if [ $? -ne 0 ]
	then
		return 0
	fi
	
	if [ $1 = "HVPS" ]
	then
		Tasklist="DAYEND_CREATEHVPS02 DAYEND_SENDHVPSNT02"
	elif [ $1 = "BEPS" ]
	then
		Tasklist="DAYEND_CREATEBEPS02 DAYEND_SENDBEPSNT02"
	elif [ $1 = "IBPS" ]
	then
		Tasklist="DAYEND_CREATEIBPS02 DAYEND_SENDIBPSNT02"
	fi
	
	CheckTaskStatus $Tasklist
	if [ $? -ne 0 ]
	then
		return 0
	fi
	
	EchoMsg "1" "生成${CNNAME}明细对账文件任务已开始重跑..."
	
	CheckSvcName $Tasklist 
}

SendDetailReconNotice()
{
	CheckClearDate $1
	if [ $? -ne 0 ]
	then
		return 0
	fi
	
	if [ $1 = "HVPS" ]
	then
		Tasklist="DAYEND_SENDHVPSNT02"
	elif [ $1 = "BEPS" ]
	then
		Tasklist="DAYEND_SENDBEPSNT02"
	elif [ $1 = "IBPS" ]
	then
		Tasklist="DAYEND_SENDIBPSNT02"
	elif [ $1 = "CIPS" ]
	then
		Tasklist="DAYEND_CHKCIPS_02"
	fi
	
	if [ $1 != "CIPS" ]
	then
		DeleteCCPCDetailData $1
	fi
	
	CheckTaskStatus $Tasklist
	if [ $? -ne 0 ]
	then
		return 0
	fi
	
	EchoMsg "1" "发送${CNNAME}明细对账通知任务已开始重跑..."
	
	CheckSvcName $Tasklist
}

SendTallyNotice()
{
	CheckClearDate $1
	if [ $? -ne 0 ]
	then
		return 0
	fi
	
	if [ $1 = "HVPS" ]
	then
		Tasklist="DAYEND_HVTTLACCTAPL"
	elif [ $1 = "BEPS" ]
	then
		Tasklist="DAYEND_BETTLACCTAPL"
	elif [ $1 = "IBPS" ]
	then
		Tasklist="DAYEND_IBTTLACCTAPL"
	elif [ $1 = "CIPS" ]
	then
		Tasklist="DAYEND_CIPSACCTAPL"
	fi
	
	CheckTaskStatus $Tasklist
	if [ $? -ne 0 ]
	then
		return 0
	fi
	
	EchoMsg "1" "发送${CNNAME}汇总记账通知任务已开始重跑..."
	
	CheckSvcName $Tasklist
}

GetTaskResult()
{
returnValue=`sqlplus -s $ZFDB_CONN<<! 2>/dev/null
select PCSG_STCD from EOD_DTBS_SCAN_TBL where TASK_NM='$1';
!`
	taskStat=`echo $returnValue|awk '{print $3}'`
	if [ $taskStat = "1" ]
	then
		EchoMsg "1" "待执行..."
	elif [ $taskStat = "2" ]
	then
		EchoMsg "1" "执行中..."
	elif [ $taskStat = "3" ]
	then
		EchoMsg "1" "执行成功..."
	elif [ $taskStat = "4" ]
	then
		EchoMsg "3" "执行失败..."
	fi
}

GetCNName()
{
	if [ $1 = "HVPS" ]
	then
		CNNAME="大额"
	elif [ $1 = "BEPS" ]
	then
		CNNAME="小额"
	elif [ $1 = "IBPS" ]
	then
		CNNAME="超网"
	elif [ $1 = "CIPS" ]
	then
		CNNAME="跨境"
	else
		EchoMsg "3" "$1汇路不存在..."
	fi 
}

QueryCCPCTotalData()
{
returnValue=`sqlplus -s $ZFDB_CONN<<! 2>/dev/null
select to_char(CLRG_DT,'yyyymmdd') from EOD_STM_ST_TBL where STM_ID='$1';
!`
	clearDate=`echo $returnValue|awk '{print $3}'`
	
	echo "当前清算日期: $clearDate"
	
	if [ $1 = "HVPS" ]
	then
		sqlStr="select * from BIGAMT_SMY_RCNCL_RGS_AFLT_TBL where CLRG_DT=to_date('$clearDate','yyyymmdd')"
	elif [ $1 = "BEPS" ]
	then
		sqlStr="select * from MICR_SMY_VRF_RGS_AFLT_TBL where CLRG_DT=to_date('$clearDate','yyyymmdd')"
	elif [ $1 = "IBPS" ]
	then
		sqlStr="select * from EBNKG_SMY_VRF_RGS_AFLT_TBL where CLRG_DT=to_date('$clearDate','yyyymmdd')"
	elif [ $1 = "CIPS" ]
	then
		sqlStr="select * from EBNKG_SMY_VRF_RGS_AFLT_TBL where CLRG_DT=to_date('$clearDate','yyyymmdd')"
	fi
	
	if [ $1 = "CIPS" ]
	then
returnValue=`sqlplus -s $ZFDB_CONN<! 2/dev/null`
set head off
set echo off;
set pagesize 10000;
set linesize 20000;
set newpage 1;
set numwidth 17;
set long 1000;
$sqlStr;
!`
	else
returnValue=`sqlplus -s $STDB_CONN<<! 2>/dev/null`
set head off
set echo off;
set pagesize 10000;
set linesize 20000;
set newpage 1;
set numwidth 17;
set long 1000;
$sqlStr;
!`
	fi
echo "$returnValue"
}

QueryCCPCDetailData()
{
returnValue=`sqlplus -s $ZFDB_CONN<<! 2/dev/null
select to_char(CLRG_DT,'yyyymmdd') from EOD_STM_ST_TBL where STM_ID='$1';
!`
	clearDate=`echo $returnValue|awk '{print $3}'`

echo "当前清算日期: $clearDate"

	if [ $1 = "CIPS" ]
	then
returnValue=`sqlplus -s $ZFDB_CONN<<! 2>/dev/null
select distinct ORI_APLY_MSGIDNO, MSGRP_PCSST, MSGRP_RCNCL_DT, OMTP, DBTCR_IDR from CIPS_XBBD_VA_ANR_RGSTBOOK where MSGRP_RCNCL_DT=TO_DATE('$clearDate','yyyymmdd') and MSGRP_TP='cips.713.001.01';
!`
	else
 returnValue=`sqlplus -s $STDB_CONN<<! 2>/dev/null
 select ORI_MSGIDNO, EXPC_TOT_RCRD_NUM, HVBN_RCV_RCRD_NUM, HVBN_RCV_COMPL from MSGRP_RCV_CTRL_TBL where STM_ID='$1' and LSTTM_RCV_TM=TO_DATE('$clearDate','yyyymmdd');
 !`
 	fi
 	echo "$returnValue"
}

QueryCCPCAdjustData()
{
returnValue=`sqlplus -s $ZFDB_CONN<<! 2>/dev/null
select to_char(CLRG_DT,'yyyymmdd') from EOD_STM_ST_TBL where STM_ID='$1';
!`
	clearDate=`echo $returnValue|awk '{print $3}'`
	
	echo "当前清算日期: $clearDate"
	
	if [ $1 = "HVPS" ]
	then
		sqlStr="select * from BIGAMT_BSN_DWLD_DTL_MSGRP_RG0 where CLRG_DT=TO_DATE('$clearDate','yyyymmdd')"
	elif [ $1 = "BEPS" ]
	then
		sqlStr="select * from MICR_BSN_DWLD_DTL_MSGRP_RGST1 where CLRG_DT=TO_DATE('$clearDate','yyyymmdd')"
	elif [ $1 = "IBPS" ]
	then
		sqlStr="select * from EBNKG_DTL_VRF_DWLD_APLY_DTL_2 where CLRG_DT=TO_DATE('$clearDate','yyyymmdd')"
	elif [ $1 = "CIPS" ]
	then
		sqlStr="select * from CIPS_OMR_ADDNSR_RGSTBOOK where MSGRP_RCNCL_DT=TO_DATE('$clearDate','yyyymmdd')"
	fi

returnValue=`sqlplus -s $ZFDB_CONN<<! 2>/dev/null`
set head off
set echo off;
set pagesize 10000;
set linesize 20000;
set newpage 1;
set numwidth 17;
set long 1000;
$sqlStr;
!`
#echo "原报文标识号 原发起机构 来往标识 借贷标识 原报文类型 报文标识号"
echo "$returnValue"
#|awk '{
#print substr($0,1,16) substr($0,1,34)}'
}

GetRouteShowStatus()
{
	if [ $HVPSStatus = "日间" ]
	then 
		HVPSShowStatus="日间"
		HVPSStatus=""
	else
		HVPSShowStatus="日终"
	fi
	
	if [ $BEPSStatus = "日间" ]
	then
		BEPSShowStatus="日间"
		BEPSStatus=""
	else
		BEPSShowStatus="日终"
	fi
	
	if [ $IBPSStatus = "日间" ]
	then 
		IBPSShowStatus="日间"
		IBPSStatus=""
	else
		IBPSShowStatus="日终"
	
	if [ $CIPSStatus = "日间" ]
	then
		CIPSShowStatus="日间"
		CIPSStatus=""
	else
		CIPSShowStatus="日终"
	fi
}

QuerySettleStatus()
{
returnValue=`sqlplus -s $ZFDB_CONN<<! 2>/dev/null`
set head off
select STM_ID, to_char(CLRG_DT,'yyyy-mm-dd'),decode(DYTM_EOD_STCD,'0','日间','1','日终开始','2','汇总对账','3','汇总不符','4','明细对账',DYTM_EOD_STCD,'异常状态') from EOD_STM_ST_TBL;
!`
	BEPSName=`echo $returnValue|awk '{print $1}'`
	BEPSDate=`echo $returnValue|awk '{print $2}'`
	BEPSStatus=`echo $returnValue|awk '{print $3}'`
	IBPSName=`echo $returnValue|awk '{print $4}'`
	IBPSDate=`echo $returnValue|awk '{print $5}'`
	IBPSStatus=`echo $returnValue|awk '{print $6}'`
	HVPSName=`echo $returnValue|awk '{print $7}'`
	HVPSDate=`echo $returnValue|awk '{print $8}'`
	HVPSStatus=`echo $returnValue|awk '{print $9}'`
	CIPSName=`echo $returnValue|awk '{print $10}'`
	CIPSDate=`echo $returnValue|awk '{print $11}'`
	CIPSStatus=`echo $returnValue|awk '{print $12}'`
	
	GetRouteShowStatus
	showDate=`date +%-Y-%m-%d`
	showTime=`date +%H:%M:%S`
	QuerySettleStatusMenu
	
	echo -e "\n按回车继续...	\c"
	read inPutValue	
}

RouteTask()
{
	while true
	do
		GetCNName $1
		RouteTaskMenu $CNNAME
		echo -e "请选择要执行的任务:	\c"
		read cd_choice
		case $cd_choice in
			1) UpdateClearDate $1
			;;
			2) UpdateDEStatus $1
			;;
			3) CreateTotalFileAndSendNT $1
			;;
			4) CreateTotalFile $1
			;;
			5) SendTotalReconNotice $1
			;;
			6) CreateDetailFile $1
			;;
			7) SendDetailFile $1
			;;
			8) SendTallyNotice $1
			;;
			9) QueryTaskStatus $1
			;;
			10) QueryCCPCData $1
			;;
			11) QueryTaskExecSt $1
			;;
			q|Q) break
			;;
			*) continue
			;;
		esac
		
		echo -e "按回车继续...	\c"
		read inPutValue
	done
}

QueryTaskStatus()
{
returnValue=`sqlplus -s $ZFDB_CONN<<! 2>/dev/null
set echo off;
set serveroutput off;
set trimspool on;
set trimout on;
set head off;
set echo off;
set pagesize 10000;
set linesize 20000;
set newpage 1;
set numwidth 17;
set long 1000;
set feedback off;
set term off;
select SPCL_TSK_DSC,PCSG_STCD,PYST_DTBS_TM from EOD_DTBS_SCAN_TBL where TASK_NM like '%${1}%'
!`

echo "$returnValue"
}

QueryTaskExecSt()
{
SYSNO=`echo $1|awk '{print substr($0,1,2)}'`
case $1 in
	HVPS)SVCNAME_ARR=(DAYEND_CREATEHVPS01 DAYEND_SENDHVPSNT01 DAYEND_CREATEHVPS02 DAYEND_SENDHVPSNT02 DAYEND_APPLYHVPSDATA DAYEND_CREATEHVPS01_SND DAYEND_HVTTLACCTAPL);;
	BEPS)SVCNAME_ARR=(DAYEND_CREATEBEPS01 DAYEND_SENDBEPSNT01 DAYEND_CREATEBEPS02 DAYEND_SENDBEPSNT02 DAYEND_APPLYBEPSDATA DAYEND_CREATEBEPS01_SND DAYEND_BETTLACCTAPL);;
	IBPS)SVCNAME_ARR=(DAYEND_CREATEIBPS01 DAYEND_SENDIBPSNT01 DAYEND_CREATEIBPS02 DAYEND_SENDIBPSNT02 DAYEND_APPLYIBPSDATA DAYEND_CREATEIBPS01_SND DAYEND_IBTTLACCTAPL);;
	CIPS)SVCNAME_ARR=(DAYEND_CHKCIPS_01 DAYEND_CHKCIPS_02 DAYEND_HVTTLACCTAPL);;
esac

returnValue=`sqlplus -s $ZFDB_CONN<<! 2>/dev/null
set echo off;
set serveroutput off;
set trimspool on;
set trimout on;
set head off;
set echo off;
set pagesize 10000;
set linesize 20000;
set newpage 1;
set numwidth 17;
set long 1000;
set feedback off;
set term off;
select TASK_NM,PCSG_STCD,PYST_DTBS_TM,SPCL_TSK_DSC from EOD_DTBS_SCAN_TBL where TASK_NM like '%$SYSNO%'
!`

#echo "$returnValue"|tr -s "[]"
SYSDATE=`date +%Y%m%d`

echo "任务名 任务状态 时间戳 执行情况 中文描述"|awk '{printf "%-27s %-4s %-14s %-7s %s\n",$1,$2,$3,$4,$5}'
echo "---------------------------------------------------------------------"

for LOOP in ${SVCNAME_ARR[*]}
do
	echo "$returnValue"|sed -n '/'$LOOP /'p|awk '{
		printf "%-30s %-8s %-17s ",$1,$2,$3;
		if('$SYSDATE' == substr($3,1,8)){
			if($2 == 3){
				printf "成功执行 OK";
			}else if($2==2){
				printf "正常执行... "
			}else if($2==4){
				printf "失败执行!!! "
			}else if($2==1){
				printf "等待执行... "
			}
		}
		else
			printf "未有执行	";
		print $4
	}'
done
}

QueryCCPCData()
{
	while true
	do
		GetCNName $1
		QueryCCPCDataMenu $CNNAME
		echo -e "请选择要执行的任务:	\c"
		read cd_choice
		case $cd_choice in
			1) QueryCCPCTotalData $1
			;;
			2) QueryCCPCDetailData $1
			;;
			3) QueryCCPCAdjustData $1
			;;
			q|Q) break
			;;
			*) continue
			;;
			esac
			
			echo -e "按回车继续...	\c"
			read inPutValue
	done
}

VBANKSettleTask()
{
	while true
	do
		VBANKTaskMenu
		echo -e "请选择要执行的人物: 	\c"
		read cd_choice
		case $cd_choice in
			1) CreateVBANKFile HVPS
			;;
			2) CreateVBANKFile BEPS
			;;
			q|Q) break
			;;
			*) continue
			;;
			esac
	done
}

RerunTask()
{
	while true
	do
		RerunTaskMenu
		echo -e "请选择要执行得到任务:	\c"
		read cd_choice
		case $cd_choice in
			1) RouteTask HVPS
			;;
			2) RouteTask BEPS
			;;
			3) RouteTask IBPS
			;;
			4) RouteTask CIPS
			;;
			5) VBANKSettleTask
			;;
			6) QuerySettleStatus
			;;
			q|Q) break
			;;
			*)	continue
			;;
			esac
	done
}

#main
RerunTask

exit 0
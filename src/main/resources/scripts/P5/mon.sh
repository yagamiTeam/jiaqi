#!/bin/ksh
#
#
GPWD=`pwd`
TMPFIL1="$HOME/tmp/tmpfil1.$$.tmp"
TMPFIL2="$HOME/tmp/tmpfil2.$$.tmp"
TMPFIL3="$HOME/tmp/tmpfil3.$$.tmp"

#--load cfg file
load_cfg()
{
	while read xx yy zz aa
	do
	{
		if [ "x$xx" = "x$msgrp_tp" ];then
			node_msgidno=$yy;
			msgrp_tp1=$zz;
			node_stcd=$aa;
		fi
		
		if [ "x`echo "$xx"|awk '{print substr($0,1,5);}'`" = "xDATE=" ];then
			vdate=`echo "$xx"|awk -F'=' '{print $2}'`
		fi
		
		if [ "x`echo "$xx"|awk '{print substr($0,1,9);}'`" = "xINTERVAL=" ];then
			vinterval=`echo "$xx"|awk -F"=" '{print $2}'`
		fi
	}
	done<mon.cfg
	return 0;
}

#--get time
get_time()
{
	vyear=`date +%Y`
	vmon=`date +%m`
	vday=`date +%d`
	vhour=`date +%H`
	vmin=`date +%M`
	((vmin=$vmin-1))
	if [ $vmin -lt 0 ];then
		((vmin=$vmin+60))
		((vhour=$vhour-1))
	fi
	if [ $vhour -lt 0 ];then
		((vhour=24+$vhour))
		((vday=$vday-1))
	fi
	if [ $vday -le 0 ];then
		vday=30
	fi
	vdate="$vday_$vhour_$vmin"
}

#--monitor once
mon_one()
{
	##
	>$TMPFIL2
	get_time
	if [ -f *OUT_*${vdate}* ];then
		grep -l "${msgrp_tp}" *OUT_*${vdate}*>$TMPFIL3
		for i in `cat $TMPFIL3`
		do
			cat $i|awk 'BEGIN {
				ifind=0;
			}
			{
				if(index($0,"Document")>0&&index($0,"'$msgrp_tp'")>0)
					vfind=1;
				if(vfind==1&&index($0,"'$node_msgidno'")>0)
				{
					print $0;
					vfind=2;	
				}
			}'|awk -F"<${node_msgidno}>" '{print $2}'|awk -F"</${node_msgidno}>" '{print $1}'|sort -u>>$TMPFIL1
		done
	else
		>$TMPFIL1
	fi
	for i in `cat $TMPFIL1|sort -u`
	do
		vfilein=`grep -l "$i" *IN*${vdate}*|sort -u|awk '{if(NR==1)print $0}'`
		echo $vfilein
		if [ "x$vfilein" = "x" ];then
			vstcd="NO_RSP"
		else
			vstcd=`cat $vfilein|awk 'BEGIN{
				vfind=0;
			}
			{
				if(vfind==0&&index($0,"'$msgrp_tp1'")>0)
					vfind=1;
				if(vfind==1&&index($0,"OrgnlMsgId")>0&&index($0,"'$i'")>0)
					vfind=2;
				if(vfind==2&&index($0,"'$node_stcd'")>0)
				{
					print $0;
					vfind=3;
				}
			}'|awk -F"<$node_stcd>" '{print $2}'|awk -F"</$node_stcd>" '{print $1}'`
		fi
		echo "$i $vstcd">>$TMPFIL2
	done
	
	vend=$SECONDS
	((vtime=$vend-$vstart))
	clear
	#echo $vstart,$vend,$vtime
	echo "`tput sgr 0 1`"
	echo "`tput sgr 0 1`	`tput sgr 1 0`往账监控界面`tput sgr 0 1`"
	echo "监控日期:${vdate}	刷新间隔:${vinterval}S		报文类型:${msgrp_tp} 耗时:${vtime}S"
	echo "报文类型 回执状态 记录数"|awk '{printf("%-26s%-16s%-10s\n", $1, $2, $3)}'
	for x in `cat $TMPFIL2|awk '{print $2}'|sort -u`
	do
		vcnt=`cat $TMPFIL2|awk '{
			if($2=="'$x'")
				print $0;
		}'|wc -l|sed "s/\ //g"`
		echo "$msgrp_tp $x $vcnt"|awk '{printf("%-30s%-20s%-16s\n",$1,$2,$3)}'
	done
	rm -f $TMPFIL1 $TMPFIL2
	return 0;
}

#--main
main()
{
	mkdir -p ~/tmp/
	#load cfg
	msgrp_tp="$1"
	node_msgidno=""
	node_stcd=""
	vdate=""
	vinterval=""
	vstart=$SECONDS
	
	load_cfg
	if [ "x$node_msgidno" = "x" ];then
		echo "MsgRp_TP[${msgrp_tp}] do not exist in CFGFILE!"
		return 1
	fi
	
	#-monitor dyn
	while [1];
	do
	{
		mon_one
		sleep $vinterval
	}
	done
	return 0;
}

main $*
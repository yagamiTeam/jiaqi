#!/bin/sh
m_choice=0
ACSName=0
ACSDate=0
ACSStatus=0

QuerySettleStatusMenu()
{
	clear
	echo "系统当前时间: $showDate $showTime"
	echo ""
	echo -e "	系统名称\t系统简称\t清算日期\t系统状态\t当前任务"
	echo "		------------------------------------------"
	echo -e "	ACS\t\t${ACSName}\t\t${ACSDate}\t${ACSShowStatus}\t\t${ACSStatus}"
	echo "		------------------------------------------"
}

GetRouteShowStatus()
{
	if [ $ACSStatus = "日间" ]
	then
		ACSShowStatus="日间"
		ACSStatus=""
	else
		ACSShowStatus="日终"
	fi
}

QuerySettleStatus()
{
sys_id=$1
returnValue=`sqlplus -s $STDB_CONN<<! 2>/dev/null
set head off
select STM_ID, to_char(CLRG_DT,'yyyy-mm-dd'),decode(DYTM_EOD_STCD,'0','日间','1','日终开始','2','汇总对账','3','汇总不符',DYTM_EOD_STCD,'异常状态') from EOD_STM_ST_TBL where STM_ID='$sys_id';
!`
	
	ACSName=`echo $returnValue|awk '{print $1}'`
	ACSDate=`echo $returnValue|awk '{print $2}'`
	ACSStatus=`echo $returnValue|awk '{print $3}'`
	
	GetRouteShowStatus
	showDate=`date +%-Y-%m-%d`
	showTime=`date +%H:%M:%S`
	QuerySettleStatusMenu
	
	echo -e "\n按回车继续...	\c"
	read inputValue
}

menu()
{
	clear
	echo
	echo
	#printf "\t\t*****************************************************\n"
	printf "\t\t|******************* ACS日终 **************************|\n"
	printf "\t\t|****************************************************|\n"
	printf "\t\t|*				1 日终状态查询							*|\n"
	printf "\t\t|*				2 日终重跑								*|\n"
	printf "\t\t|*				3 申请人行汇总数据							*|\n"
	printf "\t\t|*				4 申请系统状态							*|\n"
	printf "\t\t|*				q 退出								*|\n"
	printf "\t\t|****************************************************|\n"
	tput sgr 0 1;printf "\t\t\t请输入数字选择操作: ";tput sgr 0 0;
	read m_choice;
}

main()
{
	while true
	do
		menu
		if [ "x${m_choice}" = "xq" -o "x${m_choice}" = "xQ" ]
		then
			break
		fi
		case $m_choice in
		1)
			QuerySettleStatus "ACS"
		;;
		2)
			RerunACSTask.sh
		;;
		3)
			ApplyACSTotalData.sh "ACS"
		;;
		4)
			ApplyACSSysst.sh
		*)
			continue
		;;
		esac
	done
}

main
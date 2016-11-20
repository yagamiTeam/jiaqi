function p660
{
yes_or_not="n"
clear
printf "\n\n\t"
printf "输入要申请的汇总数据清算日期(yyyymmdd): "
read clear_date
while true
do
	if [ ${#clear_date} != 8 ]
	then
		printf "\t清算日期格式错误，正确格式(yyyymmdd)\n"
		printf "\t输入要申请的汇总数据清算日期(yyyymmdd): "
		read clear_date
	else
		echo -e "\t输入的清算日期为: $clear_date,是否确认申请该日期汇总核对数据?(Y/N) \c"
		read yes_or_not
		if [ "x${yes_or_not}" = "xy" -o "x${yes_or_not}" = "xY" ]
		then
			break
		elif [ "x${yes_or_not}" = "xn" -o "x${yes_or_not}" = "xN" ]
		then
			return 0
		fi
	fi
done

sqlplus -s $STDB_CONN <<EOF
set linesize 1000
set pagesize 0
set heading off
set term off
set termout off
set feedback off
set newpage none
set echo off
set heading off
set trim off
delete from RECPT_SMY_VRF_APLY_RGS where ACS_RCNCL_DT=to_date($clear_date,'yyyymmdd');
insert into RECPT_SMY_VRF_APLY_RGS (ACS_MSGIDNO, ACS_ITT_PCP_INST, ACS_RCNCL_DT)
values (concat('9999',to_char(sysdate,'yyyymmddhhMIssss')), 'C1010511003703',to_date($clear_date,'yyyymmdd'));
update MSGRP_PCSG_CTRL_TBL set MSGIDNO = (select ACS_MSGIDNO from RECPT_SMY_VRF_APLY_RGS where ACS_RCNCL_DT=to_date($clear_date,'yyyymmdd'))
where MSGIDNO='9999999999' and MSGRP_TP='AMFE.660.001.01' and CLRG_DT=to_date($clear_date,'yyyymmdd');
commit;
EOF
printf "\t申请报文登记完成，请查看人行汇总核对报文是否已发送\n"
read xxx
return 0
}

############ main ############
if [ "x$1" == "x" ]
then
	echo "usage: $0 Syscode"
	exit 0
fi

if [ $1 = "ACS" ]
then
	p660
fi
apply919()
{
#set -x
sleep 2
#clear
msgid=`date +'%Y%m%d%H%M%S%S'`
datime=`date +'%Y-%m-%d %H:%M:%S'`
dt=`date +'%Y%m%d'`
olid=`date +'%Y%m%d%N'`
accno=$1
#echo $msgid
#echo $datime
#echo $dt
#echo $olid
#echo $pdtm
#echo $accno
echo $msgid >>apply919_msgid.txt
echo "报文标识号[$msgid]，申请参与机构号[$accno]，是否确认申请?(y/n)"
read -e yn
if [ "x$yn" != "xy" -a "x$yn" != "xY" ]
then
	echo "已放弃申请"
	return 0
}

sqlplus -s $ZFDB_CONN <<EOF
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

insert into BASE_DATA_MGT_DTL_RGSTBOOK
(ITT_PCPR_BRNO,MSGRP_RCV_SND_IDCD,MSGIDNO,MSGRP_DTL_SN,BLNG_DRC_PCPR_BRNO,MULTI_TENANCY_ID,PYST_DTBS_TM)
values (
'105100000017','02',$msgid,1,$accno,'CN000',$pdtm
);

insert into BASE_DATA_MGT_MSGRP_RGSTBOOK
values (
'$dt',
'105100000017',
'02',
$msgid,
'ccms.919.001.01',
'','','','','',
to_date($dt,'yyyymmdd'),
0,
'CN000'
);
EOF
}

clear
echo "请输入要申请的参与机构号，多个机构号用空格分开: "
read accnos
#accnos=`cat waitapl.txt`
echo "需要申请的机构号如下: "
echo $accnos
for acc in $accnos
do
	if [[ "$acc" =~ ^[0-9]{1,20}$ && `echo $acc | awk '{print length($0)}'` <15 ]]
	then
		apply919 $acc
	else
		echo "申请机构号$acc有误，放弃申请"
	fi
done

echo "成功完成"
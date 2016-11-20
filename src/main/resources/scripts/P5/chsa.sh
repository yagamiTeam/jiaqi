#!/bin/ksh

sysTime=`date +"%Y/%m/%d %H:%M:%S"`
projectFile=$HOME/etc/Project.xml

sed "s/Version=.*/Version=\"$sysTime\"/g" $projectFile > $projectFile.new

exit 0

if [ $# -ne 2 ]
then
	echo "Usage:chas.sh CA 0/1(0-close, 1-open)"
	exit -1
fi

if [ $2 -ne 0 ] && [ $2 -ne 1 ]
then
	echo "type[$2] is not exist!"
	echo "0/1(0-close, 1-open)"
	exit -1
fi

dtaParm=$HOME/etc/COMM/$1/DtaParm.xml
if [ ! -f $dtaParm ]
then
	echo "CA[$1] is not exists!"
	exit -1
fi

sed "s/DtaSecurity SecMode.*/DtaSecurity SecMode=\"$2\"/g" $dtaParm > $dtaParm.sec
if [ $? -ne 0 ]
then 
	echo "Change SecMode Failed!"
	exit -1
fi

mv $dtaParm.sec $dtaParm
if [ $? -ne 0 ]
then
	echo "Move File Failed!"
	exit -1
fi

exit 0
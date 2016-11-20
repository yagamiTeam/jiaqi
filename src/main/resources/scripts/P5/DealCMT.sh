#!/bin/ksh

SysDate=`date +%Y%m%d`

WLUser1=wl_cnaps
WLUser2=wl_cnaps
WLIP1=11.170.200.1
WLIP2=11.170.200.3
WLPort=21
LocalPath=$HOME/data/CMT/$SysDate/
FileName=CMT.RES.$SysDate
CMTFILE=$/HOME/data/CMT/$SysData/CMT.RES.$SysDate

CreateDirectory()
{
	if [ ! -d $1 ]
	then
		mkdir -p $1
		if [ $? -ne 0 ]
		then
			echo "创建目录[$1]失败"
			exit -1
		fi
	fi
}

GetCMTFileFromWL()
{
	RemotePath=/home/ap/$2/data/CMT/

ftp -iv -n $1 $3 <<! 2>&1
use $2 $2
bin
lcd $LocalPath
cd $RemotePath
get $FileName
by
!

	mv $CMTFile $CMTFile.$1
	if [ $? -ne 0 ]
	then
		echo "移动文件[$CMTFile]到[$CMTFile.$1]失败"
		exit -1
	fi
}

CombineCMTFile()
{
	echo "支付途径|@|系统工作日期|@|付款行行号|@付款人名称|@|付款人账号|@|收款行行号|@|收款人名称|@|收款人账号|@|金额|@|附言" > $CMTFile.bak
	
	cat $CMTFile.bak $CMTFile.$WLIP1 $CMTFile.$WLIP2 > $CMTFile
	
	rm $CMTFile.bak
	if [ $? -ne 0 ]
	then
		echo "删除文件[$CMTFile.bak]失败"
	fi
}

#main
echo "处理一代报文开始..."
CreateDirectory $LocalPath

echo "获取外联[$WLIP1]上的一代报文..."
GetCMTFileFromWL $WLIP1 $WLUser1 $WLPort

echo "获取外联[$WLIP2]上的一代报文..."
GetCMTFileFromWL $WLIP2 $WUser2 $WPort

echo "合并一代报文..."
CombineCMTFile

echo "处理一代报文结束..."

exit 0
#!/bin/ksh
userid=$ZFDB_CONN
if [ "x$2" = "x" ];then
	userid=$ZFDB_CONN
else
	if [ "x$2" = "xzf" ];then
		userid=$ZFDB
	fi
	if [ "x$2" = "xfx" ];then
		userid=$FXDB_CONN
	fi
	if [ "x$2" = "xst" ];then
		userid=$STDB_CONN
	fi
fi

if [ "x$1" = "x" ];then
	echo " 后台:将某个表导成dump文件"
	echo " USG:$0 tbname!"
	exit 1
fi
echo "导出${1}开始..."
exp $userid file=$1.dmp log=$1.log tables=$1 buffer=4096000 feedback=10000
if [ $? -eq 0 ];then
	echo "导出${1}成功，存放在${1}.dmp中！"
	exit 0
else
	echo "导出${1}失败，请查看日志${1}.log!"
	exit 1
fi
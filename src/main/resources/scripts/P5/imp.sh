#----cr by 付毕生 20130913
#----功能：将某个表对应的dump文件导入到表中
userid=$ZFDB_CONN
if [ "x$2" = "x" ];then
	userid=$ZFDB_CONN
else
	if [ "x$2" = "xzf" ];then
		userid=$ZFDB_CONN
	fi
	if [ "x$2" = "xfx" ];then
		userid=$FXDB_CONN
	fi
	if [ "x$2" = "xst" ];then
		userid=$STDB_CONN
	fi
fi

if [ "x$1" = "x" ];then
	echo " 后台:将某个表对应的dmp文件导入到表中"
	echo " USG:$0 tbname!"
	exit
fi
echo $1
imp $userid file=$1.dmp log=$1.log tables=$1 buffer=2048000 commit=y ignore=y feedback=10000
if [ $? -eq 0 ];then
	echo "导入${1}.dmp成功！"
	exit 0
else
	exit "导入${1}.dmp失败，请查看日志${1}.log!"
	exit 1
fi
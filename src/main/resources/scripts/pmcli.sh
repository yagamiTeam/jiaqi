#!/bin/bash

#变量值如果没有空格，则不需要双引号，否则必须加双引号
URL=http://128.192.124.41:8101/npmsrmb/command/

echo -e "请选择启动脚本：\n"
echo "1. 缓存脚本"
echo "2. 扫描脚本"

while true
do
	printf ">>"
	read type	#使用read输入变量时最好加双引号，因为输入的字符串可能有空格
	
	#对read输入变量一般判断：
	#1.是否为空    2.是否为exit或quit  3. 正常取值    4. 其他
	if [ "$type" = "" ];then
		continue
	elif [ "$type" = "exit" ] || [ "$type" = "quit" ];then
		exit 1	#执行脚本时要用./ 或 sh，不能用 . 执行，否则会退出终端
	elif [ "$type" = "1" ];then
		curl --data "type=pcache command='help'" $URL
		break
	elif [ "$type" = "2" ];then
		curl --data "type=scan oper='help'" $URL
		break
	else
		echo -e ">>请重新选择。"
	fi
done

while true
do
	printf ">>"
	read command
	if [ "$command" = "" ];then
		continue
	elif [ "$command" = "exit" ] || [ "$command" = "quit" ];then
		exit 2
	else
		if [ "$type" = "1" ];then
			curl --data "type=pcache command='$command'" $URL
		else
			curl --data "type=scan oper='$command'" $URL
		fi
	fi
done
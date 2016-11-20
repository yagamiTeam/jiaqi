#!/bin/ksh

CustomFile=$HOME/src/Custom.makedir

CreateSrc()
{
	cd $1
	
	localDir=`pwd`
	
	srcDir=`echo "$localDir"|sed 's/etc/src/g'`
	
	homeLen=`echo "$HOME"|wc -c`
	needLen=`expr $homeLen + 1`
	
	srcSuxDir=`echo "$srcDir"|cut -b ${needLen}-`
	
	if [ $srcSuxDir != "src" ] && [ $srcSuxDir != "src/BUSI" ] && [ $srcSuxDir != "src/COMM" ] && [ $srcSuxDir != "src/BUSI/PubApp" ]
	then
		echo "	\${BIPAPPDIR}/$srcSuxDir \\" >> $CustomFile
	fi
	
	mkdir -p $srcDir
	
	for dName in `ls -l|awk '{print $9}'`
	do
		if [ ! -d $dName]
		then
			if [ $dName == "Makefile" ] || [ $dName == "RegistFunc" ]
			then
				mv $localDir/$dName $srcDir/$dName
			fi
		fi
	done
	
	for dName in `ls -l|awk '{print $9}'`
	do
		if [ -d $dName ]
		then
			CreateSrc $dName
		fi
	done
	
	cd ..
	
	return
}

#main

mkdir -p $HOME/src

echo "CUSTOMDIR= \\" > $CustomeFile

CreateSrc $HOME/etc

exit 0
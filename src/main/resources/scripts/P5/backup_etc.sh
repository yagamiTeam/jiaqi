#!/bin/ksh
homedir=$HOME
strdate=`date +%Y%m%d_%H`
targetfile=$homedir/backup/etc_$strdate.tar
upload_file=etc_$strdate.tar.gz

BACKUP_FILE_BY_CATEGROY()
{
	#>$homedir/scripts
	mkdir -p ~/backup/
	tar cvf $targetfile $homedir/scripts
	tar rvf $targetfile $homedir/.bash_profile
	cd $homedir
	#find ./etc -name "*" -o -name "*c" -type f |awk -F'/' '{if($2!="ect"||$3!=".dump")print $0}'>>~/backup/back.log
	for filename in `find ./etc -name "*"|awk '{if($0!="./etc")print $0}'|awk -F'/' '{if($2!="etc"||$3!=".dump")print $0}'`
	do
		#echo "$filename">>~/backup/tmp.txt
		tar rvf ${targetfile} ${filename}
	done
	gzip -f ${targetfile}
}

BACKUP_FILE_BY_CATEGROY

sftp dev03@128.194.9.6 <<!
cd /home/backupfile
lcd ${homedir}/backup
put ${upload_file}
quit
!
rm -f ${targetfile}.gz
echo "`date +%Y%m%d\ %H:%M:%D`:etc back finished!">>~/backup/back.log
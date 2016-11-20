homedir=$HOME
strdate=`date +%Y%m%d_%H`
targetfile=$homedir/backup/code_$strdate.tar
upload_file=code_$strdate.tar.gz

BACKUP_FILE_BY_CATEGROY()
{
	#>$homedir/scripts
	mkdir -p ~/backup/
	tar cvf $targetfile $homedir/scripts
	cd $homedir
	for filename in `find ./src -name "*.h" -o -name "*.c" -o -name "Makefile" -o -name "*.ec" -o -name "*.pc" -type f `
	do
		tar rvf ${targetfile} ${filename}
	done
	for filename in `find ./include -name "*.h" -o -name "*c" -type f`
	do
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
ftp -i 128.192.139.40<<END
user cdp cdp07112013
bin
put ${upload_file}
by
END
rm -f ${targetfile}.gz
echo "`date +%Y%m%d\ %H:%M:%D`: code backup finished!">>~/backup/back.log
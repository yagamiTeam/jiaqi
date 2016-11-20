#!/bin/ksh

for filename in `find . ! -name "." -prune -type f|sed 's/\.\///g'`
do
	iconv -f GBK -t UTF-8 $filename > $filename.utf
	mv $filename.utf $filename
done

exit 0
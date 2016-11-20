cd /home/backupfile
for i in `find . -mtime +30 -name "*.gz"`
do
	\rm -f $i
done
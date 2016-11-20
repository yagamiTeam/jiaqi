#!/bin/ksh

if [ $# -ne 1 ]
then
	echo "Usage: $0 TABLENAME"
	exit -1
fi

tableName=`echo "$1"|tr '[a-z]' '[A-Z]'`

sqlplus $ZFDB_CONN <<!
set heading off
set long 1000000
set term off

spool $1.sql

select dbms_metadata.get_ddl('TABLE','$tableName')

spool off
!

exit 0
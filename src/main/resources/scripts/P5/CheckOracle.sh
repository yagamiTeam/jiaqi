#!/bin/ksh

retValue=`sqlplus $STDB_CONN << ! 2>/dev/null
quit
!`
versionId=`echo $retValue|awk '{print $3}'`
echo "$versionId"

exit 0
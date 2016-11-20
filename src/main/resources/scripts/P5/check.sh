#!/bin/bash

SQL_EXEC()
{
sqlplus -S ${connstr}<<!
set head off
set serveroutput off
set trimspool on
set echo off
set pagesize 0
set feedback off
set term off
${sqlcomm}
quit
!
}

connstr=${ZFDB_CONN}

sqlcomm="select * from pmdta;"

SQL_EXEC

echo $#
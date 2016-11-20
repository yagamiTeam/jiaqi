bipclient <<!
mode -m AP001 -d ALL
mode -m AP001 -X OFF
quit
!
sleep 2

pskill BIPLISTEN
pskill BIPCMD
sleep 2

delenum
sleep 1

param_tab_shm del
sleep 1

export LANG=C
tmshutdown -y
export LANG=zh_CN.utf8
sleep 1

echo "ƽ̨��ֹͣ"
BIPLISTEN -a 128.194.9.112 -p 12800

sleep 1
bipclient <<!
mode -m AP001 -X INIT
mode -m AP001 -X ON
quit
!

sleep 1
bipclient <<!
mode -m AP001 -b ALL
quit
!

sleep 1
crtenum

sleep 1
param_tab_shm init

sleep 1
export LANG=C
tmboot -y
export LANG=zh_CN.utf8

echo "平台已启动"
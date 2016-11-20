vopt="$1"
vfile="$2"
if [ "x$vopt" = "x-p" ];then
	vopt="put"
else
	vopt="get"
fi
ftp -i 128.199.29.80<<END
user wl_cnaps wl_cnaps
bin
cd tmp
${vopt} ${vfile}
by
END
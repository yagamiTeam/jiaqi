cd $BIPDIR/autoconf/src
make clean
make
cd -

cd $BIPDIR/autoconf/src/acf
./configure
cd -

cd $BIPDIR/autoconf
./configure
make clean
make 
cd -

chmod +x $BIPDIR/scripts/*
exec('Utils.sci');
//exec('stixbox-master/macros/ciboot.sci');
exec('libscilab/stixbox-master/loader.sce');
exec('libscilab/apifun_0.4.1/loader.sce');
exec('libscilab/distfun_0.8/loader.sce');
xdel();xdel();

for i=1:6
    disp(i,'i');
    disp(ciboot(strtod(read_csv('test/vecTestBootstrap.csv')),mean,i,0.95,1000));
end

exec('loader.sce');

xdel();xdel();

for i=1:6
    disp(i,'i');
    disp(ciboot(strtod(read_csv('test/vecTestBootstrap.csv')),mean,i,0.95,1000));
end

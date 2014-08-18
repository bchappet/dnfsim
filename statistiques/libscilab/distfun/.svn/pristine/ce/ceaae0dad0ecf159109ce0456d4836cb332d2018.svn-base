// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- JVM NOT MANDATORY -->

path = distfun_getpath (  );
exec(fullfile(path,"tests","unit_tests","testingutilities.sce"));

//
// Consistency Checks
//
M = 80;
N = 50;
k = 30;
rtol=1.e-12;
p=linspace(0.1,0.9,5);
CheckInverseCDF("distfun_hygeinv",list(distfun_hygeinv,M,k,N),p,rtol);

// Accuracy test using data in hypergeometric.R.dataset.csv file
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","hyge","hypergeometric.dataset.csv");
table = readCsvDataset(dataset);
ntests = size(table,"r");
precision=1.e-12;
for i = 1 : ntests
    x = table(i,1);
    M = table(i,2);
    k = table(i,3);
    N = table(i,4);
    p = table(i,6);
    q = table(i,7);
    if (p<q) then
        computed = distfun_hygeinv(p,M,k,N);
        if (computed>0) then
            pxm = distfun_hygecdf(computed-1,M,k,N);
            assert_checktrue ( pxm<=p*(1+precision) );
        end
        px = distfun_hygecdf(computed,M,k,N);
        assert_checktrue ( p<=px*(1+precision) );
    else
        computed = distfun_hygeinv(q,M,k,N,%f);
        if (computed>0) then
            qxm = distfun_hygecdf(computed-1,M,k,N,%f);
            assert_checktrue ( qxm*(1+precision)>=q );
        end
        qx = distfun_hygecdf(computed,M,k,N,%f);
        assert_checktrue ( q*(1+precision)>=qx );
    end
end
//
// Test inversion at extreme x
// 1. Lower tail
//
x=distfun_hygeinv(0.,80,50,30);
expected=0;
assert_checkequal(x,expected);
//
x=distfun_hygeinv(1.127e-22,80,50,30);
expected=0;
assert_checkequal(x,expected);
//
x=distfun_hygeinv(1.128e-22,80,50,30);
expected=1;
assert_checkequal(x,expected);
//
x=distfun_hygeinv(1-1.e-9,80,50,30);
expected=30;
assert_checkequal(x,expected);
//
x = distfun_hygeinv(1.e-8,80,50,30);
expected = 7;
assert_checkequal(x,expected);
//
x=distfun_hygeinv(1.,80,50,30);
expected=30;
assert_checkequal(x,expected);
//
// 2. Upper tail
//
x=distfun_hygeinv(0.,80,50,30,%f);
expected=30;
assert_checkequal(x,expected);
//
x=distfun_hygeinv(1.e-100,80,50,30,%f);
expected=30;
assert_checkequal(x,expected);
//
x=distfun_hygeinv(1.e-9,80,50,30,%f);
expected=30;
assert_checkequal(x,expected);
//
x=distfun_hygeinv(1.e-8,80,50,30,%f);
expected=29;
assert_checkequal(x,expected);
//
x=distfun_hygeinv(1.e-6,80,50,30,%f);
expected=28;
assert_checkequal(x,expected);
//
x = distfun_hygeinv(1-1.e-8,80,50,30,%f);
expected = 7;
assert_checkequal(x,expected);
//
x=distfun_hygeinv(1.,80,50,30,%f);
expected=0;
assert_checkequal(x,expected);
//
// Performance
tic();
y = distfun_hygeinv(0:0.01:1,80,50,30);
t=toc();
assert_checktrue(t<1.);
//

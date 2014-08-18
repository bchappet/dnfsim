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
pr=0.7;
rtol=1.e-12;
p=linspace(0.1,0.9,5);
CheckInverseCDF("distfun_geoinv",list(distfun_geoinv,pr),p,rtol);

//
// Test small values of p
//
Xn = distfun_geoinv(1.e-15,0.1);
expected = 0.;
assert_checkalmostequal(Xn,expected);
//
Xn = distfun_geoinv(1.e-15,0.1,%f);
expected = 327.;
assert_checkalmostequal(Xn,expected);
//
// Test small values of Pr
// Expected values computed from Wolfram Alpha.
//
Xn = distfun_geoinv(0.1,1.e-20,%f);
expected = 230258509299404568400;
assert_checkalmostequal(Xn,expected,%eps);
//
Xn = distfun_geoinv(0.1,1.e-20);
expected = 10536051565782630122;
assert_checkalmostequal(Xn,expected,%eps);
//
// Test small values of 1-p
//
Xn=distfun_geoinv([0.99 1. 0.999 1.],0.7);
assert_checkequal(Xn,[3 %inf 5 %inf]);
//
Xn=distfun_geoinv([0.01 0. 0.001 0.],0.7,%f);
assert_checkequal(Xn,[3 %inf 5 %inf]);
//
Xn=distfun_geoinv([1.e-17 1.e-30],0.7,%f);
assert_checkequal(Xn,[32 57]);
//
// Accuracy test using data in geo.dataset.csv file
precision = 1.e-8;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","geo","geo.dataset.csv");
table = readCsvDataset(dataset);
ntests = size(table,"r");
for i = 1 : ntests
    x = table(i,1);
    Pr = table(i,2);
    p = table(i,4);
    q = table(i,5);
    if (p<q) then
        computed = distfun_geoinv(p,Pr);
        pxm = distfun_geocdf(computed-1,Pr);
        px = distfun_geocdf(computed,Pr);
        assert_checktrue ( pxm<=p*(1+precision) );
        assert_checktrue ( p<=px*(1+precision) );
    else
        computed = distfun_geoinv(q,Pr,%f);
        qxm = distfun_geocdf(computed-1,Pr,%f);
        qx = distfun_geocdf(computed,Pr,%f);
        assert_checktrue ( qxm*(1+precision)>=q );
        assert_checktrue ( q*(1+precision)>=qx );
    end
end

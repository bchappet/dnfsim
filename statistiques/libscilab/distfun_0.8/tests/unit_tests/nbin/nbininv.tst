// Copyright (C) 2014 - Michael Baudin
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
R=50;
P=0.7;
rtol=1.e-12;
p=linspace(0.1,0.9,5);
CheckInverseCDF("distfun_nbininv",list(distfun_nbininv,R,P),p,rtol);

// Accuracy test
precision = 1.e-12;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","nbin","nbin.dataset.csv");
table = readCsvDataset(dataset);
ntests = size(table,"r");
for i = 1 : ntests
    x = table(i,1);
    R = table(i,2);
    P = table(i,3);
    p = table(i,5);
    q = table(i,6);
    if (p<q) then
        computed = distfun_nbininv(p,R,P);
        if (computed>0) then
            pxm = distfun_nbincdf(computed-1,R,P);
            assert_checktrue ( pxm<=p*(1+precision) );
        end
        px = distfun_nbincdf(computed,R,P);
        assert_checktrue ( p<=px*(1+precision) );
    else
        computed = distfun_nbininv(q,R,P,%f);
        if (computed>0) then
            qxm = distfun_nbincdf(computed-1,R,P,%f);
            assert_checktrue ( qxm*(1+precision)>=q );
        end
        qx = distfun_nbincdf(computed,R,P,%f);
        assert_checktrue ( q*(1+precision)>=qx );
    end
    // Compute number of significant digits
    if ( %f ) then
        d = assert_computedigits ( computed , x );
        mprintf("Test #%d/%d: x=%s, Digits = %.1f\n",i,ntests,string(x),d);
    end
end

// Extreme inputs
//
R=10;
//
x = distfun_nbininv(1,R,1);
assert_checkequal(x,0);
//
x = distfun_nbininv(1,R,0.5);
assert_checkequal(x,%inf);
//
x = distfun_nbininv(1,R,0.);
assert_checktrue(isnan(x));
//
x = distfun_nbininv(1,R,1,%f);
assert_checkequal(x,0);
//
x = distfun_nbininv(1,R,0.5,%f);
assert_checkequal(x,0);
//
x = distfun_nbininv(1,R,0.,%f);
assert_checktrue(isnan(x));
//
x = distfun_nbininv(0,R,0.5);
assert_checkequal(x,0);
//
x = distfun_nbininv(0,R,0.5,%f);
assert_checkequal(x,%inf);
//
x = distfun_nbininv(0,R,0.);
assert_checktrue(isnan(x));
//
x = distfun_nbininv(0,R,0.,%f);
assert_checktrue(isnan(x));
//
x = distfun_nbininv(0,R,1.);
assert_checkequal(x,0);
//
x = distfun_nbininv(0,R,1.,%f);
assert_checkequal(x,0.);
//
x=distfun_nbininv(1.e-4,10,0.5);
assert_checkequal(x,0.);
//
x=distfun_nbininv(1-1.e-4,10,0.5,%f);
assert_checkequal(x,0.);
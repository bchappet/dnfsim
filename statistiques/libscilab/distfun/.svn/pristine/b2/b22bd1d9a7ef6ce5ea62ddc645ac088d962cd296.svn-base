// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

//
// <-- JVM NOT MANDATORY -->

path = distfun_getpath (  );
exec(fullfile(path,"tests","unit_tests","testingutilities.sce"));

//
// Consistency Checks
//
a=2;
b=3;
x=[
0
exp(2)
exp(2.5)
exp(3)
30
];
rtol=1.e-12;
CheckCDF("distfun_logucdf",list(distfun_logucdf,a,b),x,rtol);

//
// Check zeros
x = [exp(2) exp(2.5)];
p = distfun_logucdf ( x , 0.0 , 10 );
assert_checkequal(p,[0.2 0.25]);

//
// Check accuracy
//
precision = 1000*%eps;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","logu","logu.dataset.csv");
table = readCsvDataset(dataset);
ntests = size(table,"r");
for i = 1 : ntests
    x = table(i,1);
    a = table(i,2);
    b = table(i,3);
    p = table(i,5);
    q = table(i,6);
    computed = distfun_logucdf ( x , a , b );
    assert_checkalmostequal ( computed , p , precision );
    if ( %f ) then
        d = assert_computedigits ( computed , p );
        mprintf("Test #%d/%d: Digits = %.1f\n",i,ntests,d);
    end
    computed = distfun_logucdf ( x , a , b , %f );
    assert_checkalmostequal ( computed , q , precision );
end


// Copyright (C) 2013 - Michael Baudin
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
mu=5;
sigma=6;
rtol=1.e-12;
x=[
0.2937283
0.6067423
0.9765364
1.560462
3.1075117
];
CheckCDF("distfun_tnormcdf",list(distfun_fcdf,mu,sigma),x,rtol);

//
// Test accuracy
//
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","tnorm","tnorm.dataset.csv");
table = readCsvDataset(dataset);
precision = 1.e-12;
ntests = size(table,"r");
for i = 1 : ntests
    x = table(i,1);
    mu = table(i,2);
    sigma = table(i,3);
    a = table(i,4);
    b = table(i,5);
    p = table(i,7);
    q = table(i,8);
    computed = distfun_tnormcdf ( x , mu , sigma,a,b );
    assert_checkalmostequal ( computed , p , precision );
    if ( %f ) then
        d = assert_computedigits ( computed , p );
        mprintf("Test #%d/%d: Digits = %.1f\n",i,ntests,d);
    end
    computed = distfun_tnormcdf ( x , mu,sigma ,a,b, %f );
    assert_checkalmostequal ( computed , q , precision );
end

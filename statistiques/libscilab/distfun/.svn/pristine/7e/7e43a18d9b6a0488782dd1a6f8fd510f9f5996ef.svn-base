// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2009 - 2011 - DIGITEO - Michael Baudin
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
CheckCDF("distfun_normcdf",list(distfun_fcdf,mu,sigma),x,rtol);

//
// Test accuracy
//
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","norm","normal.R.dataset.csv");
table = readCsvDataset(dataset);
precision = 1.e-12;
ntests = size(table,"r");
for i = 1 : ntests
    x = table(i,1);
    mu = table(i,2);
    sigma = table(i,3);
    p = table(i,5);
    q = table(i,6);
    computedP = distfun_normcdf ( x , mu , sigma );
    assert_checkalmostequal ( computedP , p , precision );
    computedQ = distfun_normcdf ( x , mu,sigma , %f );
    assert_checkalmostequal ( computedQ , q , precision );
    if ( %f ) then
        dp = assert_computedigits ( computedP , p );
        dq = assert_computedigits ( computedQ , q );
        mprintf("Test #%d/%d: P Digits = %.1f, Q Digits = %.1f\n",k,nt,dp,dq);
    end
end

// See upper tail (compare with R)
p = distfun_normcdf ( 7, 4, 1 );
assert_checkalmostequal ( p , 0.99865010196837 , 1.e-15 );
p = distfun_normcdf ( 7, 4, 1 , %f );
assert_checkalmostequal ( p , 0.001349898031630095 , 1.e-15 );
p = distfun_normcdf ( 15, 4, 1 , %f );
assert_checkalmostequal ( p , 1.910659574498676e-28 , 1.e-13 );

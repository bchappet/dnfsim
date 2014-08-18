// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2011 - INRIA - Michael Baudin
// Copyright (C) 2011 - DIGITEO - Michael Baudin
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
mu=3;
sigma=2;
x=[
1.5478964  
7.0371199  
20.085537  
57.32868   
260.63035  
];
rtol=1.e-12;
CheckCDF("distfun_logncdf",list(distfun_logncdf,mu,sigma),x,rtol);

//
// Check zeros
x = [0 1];
p = distfun_logncdf ( x , 0.0 , 10 );
assert_checkequal(p,[0. 0.5]);

// See upper tail : compare with R
expected = 2.0301530180231740447e-14;
p = distfun_logncdf ( 1.e7 , 1. , 2. , %f );
assert_checkalmostequal ( p , expected , 100*%eps );

//
// Check accuracy
//
precision = 1000*%eps;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","logn","logn.dataset.csv");
table = readCsvDataset(dataset);
ntests = size(table,"r");
for i = 1 : ntests
    x = table(i,1);
    mu = table(i,2);
    sigma = table(i,3);
    p = table(i,5);
    q = table(i,6);
    computed = distfun_logncdf ( x , mu , sigma );
    assert_checkalmostequal ( computed , p , precision );
    if ( %f ) then
        d = assert_computedigits ( computed , p );
        mprintf("Test #%d/%d: Digits = %.1f\n",i,ntests,d);
    end
    computed = distfun_logncdf ( x , mu , sigma , %f );
    assert_checkalmostequal ( computed , q , precision );
end


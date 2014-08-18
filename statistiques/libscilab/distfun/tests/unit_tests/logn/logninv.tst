// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2011 - INRIA - Michael Baudin
// Copyright (C) 2011 - DIGITEO - Michael Baudin
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
mu=5;
sigma=6;
p=linspace(0.1,0.9,5);
rtol=1.e-12;
CheckInverseCDF("distfun_logninv",list(distfun_logninv,mu,sigma),p,rtol);

//
// See upper tail: compare with R
expected = 4.5117910634839439865;
x = distfun_logninv ( 0.6 , 1. , 2. );
assert_checkalmostequal ( x , expected , 100*%eps );
//
expected = 21481242.111263956875;
x = distfun_logninv ( 1.e-15 , 1. , 2. , %f );
assert_checkalmostequal ( x , expected , 100*%eps );

//
// Test accuracy
//
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","logn","logn.dataset.csv");
table = readCsvDataset(dataset);
precision = 1000*%eps;
ntests = size(table,"r");
for i = 1 : ntests
    x = table(i,1);
    mu = table(i,2);
    sigma = table(i,3);
    p = table(i,5);
    q = table(i,6);
    if (p<q) then
        computed = distfun_logninv ( p , mu , sigma );
    else
        computed = distfun_logninv ( q , mu , sigma , %f );
    end
    assert_checkalmostequal ( computed , x , precision );
end

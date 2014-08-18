// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - Michael Baudin
//
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
N=10;
pr=0.7;
x=[
5.  
6.  
7.  
8.  
9.  
];
rtol=1.e-12;
CheckCDF("distfun_binocdf",list(distfun_binocdf,N,pr),x,rtol);

//
// Accuracy test using Yalta's values
precision = 1.e-5;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","bino","binomial.yalta.dataset.csv");
table = readCsvDataset(dataset);
ntests = size(table,"r");
N = 1030;
pr = 0.5;
for i = 1 : ntests
    x = table(i,1);
    expected = table(i,2);
    computed = distfun_binocdf(x,N,pr);
    assert_checkalmostequal ( computed , expected , precision );
    // Compute number of significant digits
    if ( %f ) then
        d = assert_computedigits ( computed , expected );
        mprintf("Test #%d/%d: Digits = %.1f\n",i,ntests,d);
    end
end

// Accuracy test using data in binocdf.R.dataset.csv file
precision = 1.e-11;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","bino","binomiale.dataset.csv");
table = readCsvDataset(dataset);
ntests = size(table,"r");
for i = 1 : ntests
    if (i==38) then
        // This is a two hard test for binoinv
        continue
    end
    x = table(i,1);
    N = table(i,2);
    pr = table(i,3);
    p = table(i,5);
    q = table(i,6);
    computed = distfun_binocdf(x,N,pr);
    assert_checkalmostequal ( computed , p , precision );
    if ( %f ) then
        d = assert_computedigits ( computed , p );
        mprintf("Test #%d/%d: Digits = %.1f\n",i,ntests,d);
    end
    computed = distfun_binocdf(x,N,pr,%f);
    assert_checkalmostequal ( computed , q , precision );
end

//
// Check extreme upper tail
q = distfun_binocdf(3,10,0.00001,%f);
assert_checkalmostequal(q,2.099899202099975904e-18);

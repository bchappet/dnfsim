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
N=10;
pr=0.7;
x=[
5.  
6.  
7.  
8.  
9.  
];
CheckPDF("distfun_binopdf",list(distfun_binopdf,N,pr),x);
rtol=1.e-14;
CheckPDFvsCDF(list(distfun_binopdf,N,pr),list(distfun_binocdf,N,pr),x,rtol,%f);

// Accuracy tests with data present in binocdf.R.dataset.csv
//
precision = 1.e-11;
path = distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","bino","binomiale.dataset.csv");
table = readCsvDataset(dataset);
ntests = size(table,"r");
for i = 1 : ntests
    x = table(i,1);
    N = table(i,2);
    pr = table(i,3);
    expected = table(i,4);
    computed = distfun_binopdf(x,N,pr);
    assert_checkalmostequal ( computed , expected , precision );
    if ( %f ) then
        d = assert_computedigits ( computed , expected );
        mprintf("Test #%d/%d: Digits = %.1f\n",i,ntests,d);
    end
end

// See http://forge.scilab.org/index.php/p/distfun/issues/900
// 2147483647 is the maximum integer.
// Values greater than this are not doubles anymore.
x=1;
N=2147483647;
pr=1/N;
computed = distfun_binopdf(x,N,pr);
expected = 0.3678794;
assert_checkalmostequal ( computed , expected , 1.e-6 );
//
x=2;
N=2147483647;
pr=1/N;
computed = distfun_binopdf(x,N,pr);
expected = 0.1839397;
assert_checkalmostequal ( computed , expected , 1.e-6 );
//
// Extreme inputs
x=4999.;
N=9999.;
p=0.5;
computed = distfun_binopdf(x,N,p);
expected = 0.0079786461393821558191;
assert_checkalmostequal ( computed , expected , 1.e-7 );
// Check distfun_binopdf for values of pr closer to 1
computed = distfun_binopdf(1,2,0.9999);
expected = 1.999799999999779835D-04;
assert_checkalmostequal(computed,expected);


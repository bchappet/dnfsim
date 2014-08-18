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
//
a=5;
b=6;
x=[
    14.595546  
    21.801654  
    28.025453  
    35.342168  
    47.961538  
];
CheckPDF("distfun_gampdf",list(distfun_gampdf,a,b),x);
rtol=1.e-9;
CheckPDFvsCDF(list(distfun_gampdf,a,b),list(distfun_gamcdf,a,b),x,rtol);

//
// Accuracy test
// Numerical values computed from Matlab 7.7.0
precision = 10*%eps;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","gam","gammacdf.matlab.dataset.csv");
table = readCsvDataset(dataset);
ntests = size(table,"r");
for i = 1 : ntests
  x = table(i,1);
  shape = table(i,2);
  scale = table(i,3);
  expected = table(i,4);
  computed = distfun_gampdf(x,shape,scale);
  d = assert_computedigits ( computed , expected );
  //mprintf("Test #%d/%d: Digits = %.1f\n",i,ntests,d);
  assert_checkalmostequal ( computed , expected , precision );
end

//
// Accuracy test
// Numerical values computed from R
precision = 1000*%eps;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","gam","gamma.dataset.csv");
table = readCsvDataset(dataset);
ntests = size(table,"r");
for i = 1 : ntests
  x = table(i,1);
  shape = table(i,2);
  scale = table(i,3);
  expected = table(i,4);
  computed = distfun_gampdf(x,shape,scale);
  d = assert_computedigits ( computed , expected );
  //mprintf("Test #%d/%d: Digits = %.1f\n",i,ntests,d);
  assert_checkalmostequal ( computed , expected , precision );
end
//
// b cannot be zero
y=distfun_gampdf(500,250,[1 2]);
expected = [6.09037089D-36 1.26114581D-02];
assert_checkalmostequal(y,expected,1.e-5);
//
x=0;
a=1;
b=0.5;
y=distfun_gampdf(x,a,b);
expected=2.;
assert_checkequal(y,expected);
//
x=0;
a=1;
b=2;
y=distfun_gampdf(x,a,b);
expected=0.5;
assert_checkequal(y,expected);
//
// x=0, a<1
x=0;
a=0.5;
b=3;
y=distfun_gampdf(x,a,b);
expected=%inf;
assert_checkequal(y,expected);
//
// x=0, a>1
x=0;
a=2;
b=3;
y=distfun_gampdf(x,a,b);
expected=0;
assert_checkequal(y,expected);

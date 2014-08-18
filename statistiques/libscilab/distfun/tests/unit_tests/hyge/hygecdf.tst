// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2012 - Prateek Papriwal
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
M=80;
k=60;
N=30;
x=[
    20.  
    22.  
    23.  
    24.  
    25.  
];
rtol=1.e-12;
CheckCDF("distfun_hygecdf",list(distfun_hygecdf,M,k,N),x,rtol);

//
// Check that p(x)=0, if x<N-M+k
computed=distfun_hygecdf(0,80,60,30);
expected = 0.;
assert_checkequal(computed,expected);
//
computed=distfun_hygecdf(30,80,60,30,%t);
expected = 1.;
assert_checkequal(computed,expected);
//
// Check that the inputs are correctly checked
computed=distfun_hygecdf([0 17],80,60,30);
expected = [0. 4.1403970e-3];
assert_checkalmostequal(computed,expected,1.e-7);
//
// Check vectorisation
// The parameters below are chosen so that 
// the minimum x such that p(x)>0 is x=10.
// For x=0,1,...,9, p(x)=0.
//
M = 100;
k = 50;
N = 60;
x = floor(linspace(0,40,10));
rtol=1.e-12;
CheckCDF("distfun_hygecdf",list(distfun_hygecdf,M,k,N),x,rtol);

//
// Check vectorisation
M = 1000;
N = 600;
k = 400;
x = [10 20 30];
rtol=1.e-12;
CheckCDF("distfun_hygecdf",list(distfun_hygecdf,M,k,N),x,rtol);
//
// Check vectorisation (all possible x)
M = 1000;
N = 600;
k = 400;
x = 0:40:400;
rtol=1.e-12;
CheckCDF("distfun_hygecdf",list(distfun_hygecdf,M,k,N),x,rtol);


// Accuracy test using data in hypergeometric.R.dataset.csv file
precision = 1.e-12;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","hyge","hypergeometric.dataset.csv");
table = readCsvDataset(dataset);
ntests = size(table,"r");
for i = 1 : ntests
  x = table(i,1);
  M = table(i,2);
  k = table(i,3);
  N = table(i,4);
  expected = table(i,6);
  computed = distfun_hygecdf(x,M,k,N);
  assert_checkalmostequal ( computed , expected , precision );
  // Compute number of significant digits
  if ( %f ) then
    d = assert_computedigits ( computed , expected );
    mprintf("Test #%d/%d: Digits = %.1f\n",i,ntests,d);
  end
end

//
// See upper tail
p = distfun_hygecdf(20,80,50,30);
lt_expected = 0.7974774;
assert_checkalmostequal(p,lt_expected,1.e-7);
//
q = distfun_hygecdf(20,80,50,30,%f);
ut_expected = 0.2025226;
assert_checkalmostequal(q,ut_expected,1.e-7);
//
x=320;
M=1030;
k=515;
N=500;
q = distfun_hygecdf(x,M,k,N,%f);
ut_expected = 4.5157321191192804676e-19;
assert_checkalmostequal(q,ut_expected,1.e-7);
//
// Benchmark
tic();
y = distfun_hygecdf(0:300,800,500,300);
t=toc();
assert_checktrue(t<1.);
//
M=1.e8;
k=1.e7;
N=1.e6;
x=N;
p=distfun_hygecdf(x,M,k,N);
assert_checkequal(p,1.);
//
// Test from R
p=distfun_hygecdf(59,300,150,60,%f);
assert_checkalmostequal(p,5.111204798e-22,1.e-7);
//
// Test just greater than the mode:
// a large number of loops is required.
p=distfun_hygecdf(31,300,150,60);
expected=0.66737716514585077032;
assert_checkalmostequal(p,expected,1.e-12);
//
// Test just smaller than the mode
// a large number of loops is required.
p=distfun_hygecdf(29,300,150,60);
expected=0.44266900315385915299;
assert_checkalmostequal(p,expected,1.e-12);
//
// Test large values of M
p=0.99;
M=1.e8;
k=1.e7;
N=1.e6;
p=distfun_hygecdf(13,M,k,N);
assert_checkequal(p,0);
//
p=0.99;
M=1.e8;
k=1.e7;
N=1.e6;
p=distfun_hygecdf(100000,M,k,N);
assert_checkalmostequal(p,0.5008429,1.e-6);
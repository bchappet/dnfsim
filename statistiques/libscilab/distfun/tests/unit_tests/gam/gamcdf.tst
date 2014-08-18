// Copyright (C) 2012 - 2014 - Michael Baudin
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
a=5;
b=6;
x=[
    14.595546  
    21.801654  
    28.025453  
    35.342168  
    47.961538  
];
rtol=1.e-12;
CheckCDF("distfun_gamcdf",list(distfun_gamcdf,a,b),x,rtol);

//
a = 1:6;
b = 5:10;
p = distfun_gamcdf(a.*b,a,b);
e = [  0.6321  0.5940  0.5768  0.5665  0.5595  0.5543];
assert_checkalmostequal(p,e,1.e-3);
//
// Accuracy test
// Numerical values computed from Yalta
//
precision = 1.e-5;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","gam","gammacdf.yalta.dataset.csv");
table = readCsvDataset(dataset);
ntests = size(table,"r");
for i = 1 : ntests
  x = table(i,1);
  shape = table(i,2);
  scale = table(i,3);
  expected = table(i,4);
  computed = distfun_gamcdf(x,shape,scale);
  assert_checkalmostequal ( computed , expected , precision );
  // Compute number of significant digits
  if ( %f ) then
    d = assert_computedigits ( computed , expected );
    mprintf("Test #%d/%d: Digits = %.1f\n",i,ntests,d);
  end
end

//
// Accuracy test
// Numerical values computed from R
precision = 1.e-12;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","gam","gamma.dataset.csv");
table = readCsvDataset(dataset);
ntests = size(table,"r");
for i = 1 : ntests
    x = table(i,1);
    shape = table(i,2);
    scale = table(i,3);
    p = table(i,5);
    q = table(i,6);
    computedP = distfun_gamcdf(x,shape,scale);
    assert_checkalmostequal ( computedP , p , precision );
    computedQ = distfun_gamcdf(x,shape,scale,%f);
    assert_checkalmostequal ( computedQ , q , precision );
    if (%f) then
        dP = assert_computedigits ( computedP , p );
        dQ = assert_computedigits ( computedQ , q );
        mprintf("Test #%d/%d: Digits P= %.1f, Q= %.1f\n",i,ntests,dP,dQ);
    end
end

//
// See upper tail, and compare with R
p = distfun_gamcdf(1,3,5);
assert_checkalmostequal(p,0.0011484812448621334151,100*%eps);
p = distfun_gamcdf(1,3,5,%f);
assert_checkalmostequal(p,0.99885151875513777942,100*%eps);
p = distfun_gamcdf(300,3,5,%f);
assert_checkalmostequal(p, 1.6295866529378136268e-23,100*%eps);

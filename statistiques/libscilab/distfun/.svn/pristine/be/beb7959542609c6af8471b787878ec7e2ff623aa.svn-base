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
//
mu=5;
sigma=6;
a=4.5;
b=7;
x=[
  4.7468024
  4.9931769
  5.2395399
  6.7414155
];
CheckPDF("distfun_tnormpdf",list(distfun_tnormpdf,mu,sigma,a,b),x);
rtol=1.e-9;
CheckPDFvsCDF(list(distfun_tnormpdf,mu,sigma,a,b),list(distfun_tnormcdf,mu,sigma,a,b),x,rtol);

//
// Test accuracy
//
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","tnorm","tnorm.dataset.csv");
table = readCsvDataset(dataset);
precision = 100*%eps;
ntests = size(table,"r");
for i = 1 : ntests
  x = table(i,1);
  mu = table(i,2);
  sigma = table(i,3);
  a = table(i,4);
  b = table(i,5);
  expected = table(i,6);
  computed = distfun_tnormpdf ( x , mu , sigma,a,b );
  assert_checkalmostequal ( computed , expected , precision );
  if ( %f ) then
    d = assert_computedigits ( computed , expected );
    mprintf("Test #%d/%d: Digits = %.1f\n",i,ntests,d);
  end
end

// Copyright (C) 2014 - Michael Baudin
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
k=5;
delta=7;
x=[
    1.610308   
    2.9999081  
    4.3514602  
    6.06443    
    9.2363569  
];
rtol=1.e-12;
CheckCDF("distfun_ncx2cdf",list(distfun_ncx2cdf,k,delta),x,rtol);

//
// Accuracy test using dataset
precision = 1.e-12;
atol=1.e-4;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","ncx2","ncx2.dataset.csv");
table = readCsvDataset(dataset);
ntests = size(table,"r");
for i = 1 : ntests
  x = table(i,1);
  k = table(i,2);
  delta = table(i,3);
  p = table(i,5);
  q = table(i,6);
  computed = distfun_ncx2cdf(x,k,delta);
  assert_checkalmostequal ( computed , p , precision, atol );
  // Compute number of significant digits
  if ( %f ) then
    d = assert_computedigits ( computed , p );
    mprintf("Test #%d/%d: Digits = %.1f\n",i,ntests,d);
  end
  computed = distfun_ncx2cdf(x,k,delta,%f);
  assert_checkalmostequal ( computed , q , precision , atol);
end


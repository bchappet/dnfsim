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
k=5;
x=[
    1.610308   
    2.9999081  
    4.3514602  
    6.06443    
    9.2363569  
];
rtol=1.e-12;
CheckCDF("distfun_chi2cdf",list(distfun_chi2cdf,k),x,rtol);

//
// Accuracy test using data in chi2pdf.R.dataset.csv file
precision = 1.e-12;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","chi2","chi2.dataset.csv");
table = readCsvDataset(dataset);
ntests = size(table,"r");
for i = 1 : ntests
  x = table(i,1);
  k = table(i,2);
  p = table(i,4);
  q = table(i,5);
  computed = distfun_chi2cdf(x,k);
  assert_checkalmostequal ( computed , p , precision );
  // Compute number of significant digits
  if ( %f ) then
    d = assert_computedigits ( computed , p );
    mprintf("Test #%d/%d: Digits = %.1f\n",i,ntests,d);
  end
  computed = distfun_chi2cdf(x,k,%f);
  assert_checkalmostequal ( computed , q , precision );
end


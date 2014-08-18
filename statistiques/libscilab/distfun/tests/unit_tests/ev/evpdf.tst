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
x=[
  - 2.6893094  
    1.8535969  
    5.         
    8.1464031  
    12.689309  
];
CheckPDF("distfun_evpdf",list(distfun_evpdf,mu,sigma),x);
rtol=1.e-9;
CheckPDFvsCDF(list(distfun_evpdf,mu,sigma),list(distfun_evcdf,mu,sigma),x,rtol);

//
// Test accuracy
//
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","ev","ev.dataset.csv");
table = readCsvDataset(dataset);
precision = 100*%eps;
ntests = size(table,"r");
for i = 1 : ntests
  x = table(i,1);
  mu = table(i,2);
  sigma = table(i,3);
  expected = table(i,4);
  computed = distfun_evpdf ( x , mu , sigma );
  assert_checkalmostequal ( computed , expected , precision );
  if ( %f ) then
    d = assert_computedigits ( computed , expected );
    mprintf("Test #%d/%d: Digits = %.1f\n",i,ntests,d);
  end
end
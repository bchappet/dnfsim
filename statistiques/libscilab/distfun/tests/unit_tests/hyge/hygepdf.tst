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
CheckPDF("distfun_hygepdf",list(distfun_hygepdf,M,k,N),x);
rtol=1.e-11;
CheckPDFvsCDF(list(distfun_hygepdf,M,k,N),list(distfun_hygecdf,M,k,N),x,rtol,%f);

//
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
  expected = table(i,5);
  computed = distfun_hygepdf(x,M,k,N);
  assert_checkalmostequal ( computed , expected , precision );
  // Compute number of significant digits
  if ( %f ) then
    d = assert_computedigits ( computed , expected );
    mprintf("Test #%d/%d: Digits = %.1f\n",i,ntests,d);
  end
end

// Accuracy test using data in hypergeometric_yalta.dataset.csv file
precision = 1.e-5;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","hyge","hypergeometric_yalta.dataset.csv");
table = readCsvDataset(dataset);
ntests = size(table,"r");
for i = 1 : ntests
  x = table(i,1);
  M = table(i,2);
  k = table(i,3);
  N = table(i,4);
  expected = table(i,5);
  computed = distfun_hygepdf(x,M,k,N);
  assert_checkalmostequal ( computed , expected , precision );
  // Compute number of significant digits
  if ( %f ) then
    d = assert_computedigits ( computed , expected );
    mprintf("Test #%d/%d: Digits = %.1f\n",i,ntests,d);
  end
end

//
// Check accuracy
computed = distfun_hygepdf ( 200 , 1030 , 500 , 515 );
expected = 1.6557e-10;
assert_checkalmostequal(computed,expected,1.e-4);
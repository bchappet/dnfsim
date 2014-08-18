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
lambda=80;
x=[
    69.  
    75.  
    80.  
    85.  
    92.  
];
rtol=1.e-12;
CheckCDF("distfun_poisscdf",list(distfun_poisscdf,lambda),x,rtol);

// Accuracy tests
//
precision = 1.e-5;
path = distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","poiss","poisson.dataset.csv");
table = readCsvDataset(dataset);
ntests = size(table,"r");
for i = 1 : ntests
    if (i==32) then
        // See bug http://forge.scilab.org/index.php/p/distfun/issues/961
        continue
    end
  x = table(i,1);
  lambda = table(i,2);
  p = table(i,4);
  q = table(i,5);
  computed = distfun_poisscdf(x,lambda);
  assert_checkalmostequal ( computed , p , precision );
  computed = distfun_poisscdf(x,lambda,%f);
  assert_checkalmostequal ( computed , q , precision );
end

//
// Tests from Yalta
// table = [x lambda p precision]
//
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","poiss","poisson.cdf.yalta.dataset.csv");
table = readCsvDataset(dataset);
precision = 1.e-5;
nt = size(table,"r");
for k = 1 : nt
  Xk = table(k,1);
  lambda = table(k,2);
  expected = table(k,3);
  computed=distfun_poisscdf(Xk,lambda);
  assert_checkalmostequal ( computed , expected , precision );
  if ( %f ) then
    dp = assert_computedigits ( expected , computed );
    mprintf("Test #%3d/%3d: Digits P= %.1f\n",k,nt,dp);
  end
end

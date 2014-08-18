// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2011 - INRIA - Michael Baudin
// Copyright (C) 2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

//
// <-- JVM NOT MANDATORY -->

path = distfun_getpath (  );
exec(fullfile(path,"tests","unit_tests","testingutilities.sce"));

//
// Consistency Checks
//
mu=5;
x=[
    0.5268026  
    1.7833747  
    3.4657359  
    6.019864   
    11.512925  
];
CheckPDF("distfun_exppdf",list(distfun_exppdf,mu),x);
rtol=1.e-9;
CheckPDFvsCDF(list(distfun_exppdf,mu),list(distfun_expcdf,mu),x,rtol);
//
// Check negative argument
x = -10;
mu = 2;
computed = distfun_exppdf ( x , mu );
expected = 0;
assert_checkequal ( computed , expected );

//
// [x mu P]
precision = 100*%eps;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","exp","exp.dataset.csv");
table = readCsvDataset(dataset);
ntests = size(table,"r");
for i = 1 : ntests
  x = table(i,1);
  mu = table(i,2);
  y = table(i,3);
  computed = distfun_exppdf ( x , mu );
  assert_checkalmostequal ( computed , y , precision );
  if ( %f ) then
    d = assert_computedigits ( computed , y );
    mprintf("Test #%d/%d: Digits = %.1f\n",i,ntests,d);
  end
end

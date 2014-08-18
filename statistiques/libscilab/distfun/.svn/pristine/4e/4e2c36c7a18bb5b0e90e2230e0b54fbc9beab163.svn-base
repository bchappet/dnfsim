// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2011 - INRIA - Michael Baudin
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
mu=5;
x=[
    0.5268026  
    1.7833747  
    3.4657359  
    6.019864   
    11.512925  
];
rtol=1.e-12;
CheckCDF("distfun_expcdf",list(distfun_expcdf,mu),x,rtol);

//
// See upper tail, compare with R
p = distfun_expcdf ( 2 , 1/3 );
assert_checkalmostequal ( p , 0.99752124782333362329 , 1.e-15 );
p = distfun_expcdf ( 2 , 1/3 , %f );
assert_checkalmostequal ( p , 0.0024787521766663584907 , 1.e-15 );
//
// http://forge.scilab.org/index.php/p/distfun/issues/781/
p = distfun_expcdf ( 1.e-20 , 1 );
assert_checkalmostequal ( p , 1.e-20 , %eps );
//
// Check negative argument
x = -10;
mu = 2;
computed = distfun_expcdf ( x , mu );
expected = 0;
assert_checkequal ( computed , expected );

//
// Test accuracy
//
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","exp","exp.dataset.csv");
table = readCsvDataset(dataset);
precision = 100*%eps;
nt = size(table,"r");
for k = 1 : nt
  x = table(k,1);
  mu = table(k,2);
  p = table(k,4);
  q = table(k,5);
  computed = distfun_expcdf ( x , mu );
  assert_checkalmostequal ( computed , p , precision );
  computed = distfun_expcdf ( x , mu , %f );
  assert_checkalmostequal ( computed , q , precision );
end


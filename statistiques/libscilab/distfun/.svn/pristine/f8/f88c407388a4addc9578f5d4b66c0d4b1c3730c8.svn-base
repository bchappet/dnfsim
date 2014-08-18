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

// Check expansion of arguments
mu = matrix(1:6,2,3);
checkRNG ( 2 , 3 , list(distfun_exprnd ,mu));

//
// Test the distribution of random numbers
//
rtol = 1.e-1;
atol = 1.e-1;

// The number of classes in the histogram
// NC must be even.
NC = 2*12;
N=10000;

// Set the seed to always get the same random numbers
distfun_seedset(0);

A = 20;
R = distfun_exprnd(A,400,800);
[M,V] = distfun_expstat (A);
checkMeanVariance ( 400 , 800 , R , M , V , rtol );
//
for A = linspace(0.1,50,10)
  R = distfun_exprnd(A,1,N);
  checkRNGLaw ( R , list(distfun_expcdf,A) , N , NC , rtol , atol );
end


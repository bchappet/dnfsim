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

// Check expansion of arguments
lambda = matrix(1:6,2,3);
checkRNG ( 2 , 3 , list(distfun_poissrnd ,lambda));

//
// Test the distribution of random numbers
//
rtol = 1.e-2;
atol = 1.e-1;

// The number of classes in the histogram
// NC must be even.
NC = 2*12;
N=10000;

// Set the seed to always get the same random numbers
distfun_seedset(0);

A = 10;
R = distfun_poissrnd(A,400,800);
[M,V] = distfun_poissstat(A);
checkMeanVariance ( 400 , 800 , R , M , V , rtol );
//
for A = floor(linspace(50,70,10))
  R = distfun_poissrnd(A,1,N);
  checkRNGLaw ( R , list(distfun_poisscdf,A) , N , NC , rtol , atol , %f );
end


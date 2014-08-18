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

// Check expansion of arguments
mu = matrix(1:6,2,3);
sigma = matrix(2:7,2,3);
checkRNG ( 2 , 3 , list(distfun_evrnd ,mu,sigma));

//
// Test the distribution of random numbers
//
rtol = 5.e-2;
atol = 5.e-2;

// The number of classes in the histogram
// NC must be even.
NC = 2*12;
N=10000;

// Set the seed to always get the same random numbers
distfun_seedset(0);

A = 7;
B = 12;
R = distfun_evrnd(A,B,400,800);
[M,V] = distfun_evstat(A,B);
checkMeanVariance ( 400 , 800 , R , M , V , rtol );
//
for A = linspace(1,20,4)
  for B = linspace(1,20,4)
    R = distfun_evrnd(A,B,1,N);
    checkRNGLaw ( R , list(distfun_evcdf,A,B) , N , NC , rtol , atol );
  end
end


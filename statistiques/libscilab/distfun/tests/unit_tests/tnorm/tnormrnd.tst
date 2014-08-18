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
a=-1*ones(2,3);
b=1*ones(2,3);
checkRNG ( 2 , 3 , list(distfun_tnormrnd,mu,sigma,a,b));

//
// Test the distribution of random numbers
//
rtol = 1.e-2;
atol = 1.e-2;

// The number of classes in the histogram
// NC must be even.
NC = 2*12;
N=10000;

// Set the seed to always get the same random numbers
distfun_seedset(0);

mu = 7;
sigma = 12;
a=5;
b=9;
R = distfun_tnormrnd(mu,sigma,a,b,400,200);
[M,V] = distfun_tnormstat(mu,sigma,a,b);
checkMeanVariance ( 400 , 200 , R , M , V , rtol );
//
a=5;
b=9;
for mu = linspace(1,20,4)
  for sigma = linspace(1,20,4)
    R = distfun_tnormrnd(mu,sigma,a,b,1,N);
    checkRNGLaw ( R , list(distfun_tnormcdf,mu,sigma,a,b) , N , NC , rtol , atol );
  end
end


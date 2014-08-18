// Copyright (C) 2014 - Michael Baudin
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
m = matrix(1:6,2,3);
checkRNG ( 2 , 3 , list(distfun_unidrnd ,m));
//
// Test the distribution of random numbers
//
rtol = 1.e-1;
atol = 1.e-1;
// The number of classes in the histogram
// NC must be even.
NC = 2*12;
m=10000;
// Set the seed to always get the same random numbers
distfun_seedset(0);
N = 30;
R = distfun_unidrnd(N,400,800);
[M,V]=distfun_unidstat(N);
checkMeanVariance ( 400 , 800 , R , M , V , rtol );
//
for N = floor(linspace(60,100,6))
   R = distfun_unidrnd(N,1,m);
   checkRNGLaw ( R , list(distfun_unidcdf,N) , m , NC , rtol , atol , %f );
end

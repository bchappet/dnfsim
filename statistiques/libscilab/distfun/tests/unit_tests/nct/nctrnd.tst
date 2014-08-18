// Copyright (C) 2014 - Michael Baudin
//
// This file v1st be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- JVM NOT MANDATORY -->

path = distfun_getpath (  );
exec(fullfile(path,"tests","unit_tests","testingutilities.sce"));

// Check expansion of arguments
v = matrix(1:6,2,3);
delta=v+10;
checkRNG ( 2 , 3 , list(distfun_nctrnd , v,delta));

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
//
// Increase the tolerance for this test.
v = 5;
delta=10;
[M,V] = distfun_nctstat(v,delta);
R = distfun_nctrnd(v,delta,400,800);
checkMeanVariance ( 400 , 800 , R , M , V , rtol, atol );
//
for delta = floor(linspace(4,20,3))
for v = floor(linspace(4,20,3))
    R = distfun_nctrnd(v,delta,1,N);
    checkRNGLaw ( R , list(distfun_nctcdf,v,delta) , N , NC , rtol , atol );
end
end

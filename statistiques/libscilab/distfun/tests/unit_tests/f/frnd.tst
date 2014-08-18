// Copyright (C) 2012 - Michael Baudin
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
a = matrix(1:6,2,3);
b = a+1;
checkRNG ( 2 , 3 , list(distfun_frnd , a, b));

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
v1 = 7;
v2 = 12;
[M,V] = distfun_fstat(v1,v2);
R = distfun_frnd(v1,v2,400,800);
checkMeanVariance ( 400 , 800 , R , M , V , 4*rtol );
//
for v1 = floor(linspace(10,20,4))
  for v2 = floor(linspace(15,20,4))
    R = distfun_frnd(v1,v2,1,N);
    checkRNGLaw ( R , list(distfun_fcdf,v1,v2) , N , NC , 2*rtol , atol );
  end
end


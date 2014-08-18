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
a = matrix(1:6,2,3);
b=a+10;
checkRNG ( 2 , 3 , list(distfun_unifrnd ,a,b));

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

A = 0.1;
B = 2.3;
R = distfun_unifrnd(A,B,400,800);
[M,V] = distfun_unifstat(A,B);
checkMeanVariance ( 400 , 800 , R , M , V , rtol );

//
for A = linspace(0.1,0.9,4)
for B = linspace(2.5,4.2,4)
   R = distfun_unifrnd(A,B,1,N);
   checkRNGLaw ( R , list(distfun_unifcdf,A,B) , N , NC , rtol , atol );
end
end

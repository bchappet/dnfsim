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
k = matrix(1:6,2,3);
// Test a non-integer value
k(2,3)=1.5;
delta=matrix(2:7,2,3);
checkRNG ( 2 , 3 , list(distfun_ncx2rnd,k,delta));

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

A = 7;
B = 10;
R = distfun_ncx2rnd(A,B,400,800);
[M,V] = distfun_ncx2stat(A,B);
checkMeanVariance ( 400 , 800 , R , M , V , rtol );

// Increase the tolerance for this test.
for A = floor(linspace(1,20,5))
for B = floor(linspace(1,20,5))
    R = distfun_ncx2rnd(A,B,1,N);
    checkRNGLaw ( R , list(distfun_ncx2cdf,A,B) , N , NC , 2*rtol , atol );
end
end

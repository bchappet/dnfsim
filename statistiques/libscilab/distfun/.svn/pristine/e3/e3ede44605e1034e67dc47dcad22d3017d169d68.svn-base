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
A = matrix(floor(linspace(80,100,6)),2,3);
B = matrix(floor(linspace(8,10,6)),2,3);
C = matrix(floor(linspace(8,10,6)),2,3);
checkRNG ( 2 , 3 , list(distfun_hygernd ,A,B,C));

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

A = 8;
B = 3;
C = 5;
R = distfun_hygernd(A,B,C,400,800);
[M,V] = distfun_hygestat(A,B,C);
checkMeanVariance ( 400 , 800 , R , M , V , rtol );

//
for A = floor(linspace(80,100,2))
for B = floor(linspace(8,10,2))
for C = floor(linspace(8,10,2))
    R = distfun_hygernd(A,B,C,1,N);
   checkRNGLaw ( R , list(distfun_hygecdf,A,B,C) , N , NC , rtol , atol , %f );
end
end
end


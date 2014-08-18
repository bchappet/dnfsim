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
R = matrix(1:6,2,3);
P = matrix(linspace(0.1,0.5,6),2,3);
checkRNG ( 2 , 3 , list(distfun_nbinrnd ,R,P));

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

R = 10;
P = 0.4;
[M,V] = distfun_nbinstat (R,P);
R = distfun_nbinrnd(R,P,400,800);
checkMeanVariance ( 400 , 800 , R , M , V , rtol );
//
for R = floor(linspace(10,50,4))
for P = linspace(0.1,0.9,4)
  x = distfun_nbinrnd(R,P,1,N);
  checkRNGLaw ( x , list(distfun_nbincdf,R,P) , N , NC , rtol , atol , %f );
end
end


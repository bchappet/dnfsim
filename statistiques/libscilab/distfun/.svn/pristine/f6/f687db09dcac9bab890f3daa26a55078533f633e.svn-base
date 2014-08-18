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

//
// One single run (default n=1)
mu=[12,-31];
sigma = [
 3.0  0.5
 0.5  1.0
];
x=distfun_rndmvn(mu,sigma);
assert_checkequal(size(x),[1,2]);
//
// Several runs
mu=[12,-31];
sigma = [
 3.0  0.5
 0.5  1.0
];
n=10000;
x=distfun_rndmvn(mu,sigma,n);
assert_checkequal(size(x),[n,2]);
atol=0.1;
computed=mean(x,"r");
assert_checkalmostequal(computed,mu,[],atol);
computed=test_cov(x);
assert_checkalmostequal(computed,sigma,[],atol);
//
// Dimension 5 - One single run (default n=1)
mu=[12,-31,312,-4321];
sigma = [
 3.0  0.5  0.8  0.1
 0.5  1.0  0.9  0.2
 0.8  0.9  7.5  0.3
 0.1  0.2  0.3  5.1
];
x=distfun_rndmvn(mu,sigma);
assert_checkequal(size(x),[1,4]);
//
// Dimension 5 - Several runs
mu=[12,-31,312,-4321];
sigma = [
 3.0  0.5  0.8  0.1
 0.5  1.0  0.9  0.2
 0.8  0.9  7.5  0.3
 0.1  0.2  0.3  5.1
];
n=10000;
x=distfun_rndmvn(mu,sigma,n);
assert_checkequal(size(x),[n,4]);
atol=0.1;
computed=mean(x,"r");
assert_checkalmostequal(computed,mu,[],atol);
computed=test_cov(x);
assert_checkalmostequal(computed,sigma,[],atol);

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
//
// We check that each marginal i is normal
for marginalindex=1:2
    x=distfun_rndmvn(mu,sigma,N);
    R=x(:,marginalindex);
    A=mu(marginalindex);
    B=sqrt(sigma(marginalindex,marginalindex));
    checkRNGLaw ( R , list(distfun_normcdf,A,B) , N , NC , rtol , atol );
end

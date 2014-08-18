// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_poissrnd(varargin)
    // Poisson random numbers
    //
    // Calling Sequence
    //   x = distfun_poissrnd(lambda)
    //   x = distfun_poissrnd(lambda,[m,n])
    //   x = distfun_poissrnd(lambda,m,n)
    //
    // Parameters
    //   lambda : a matrix of doubles, the average rate of occurrence. lambda>0.
    //   m : a 1-by-1 matrix of floating point integers, the number of rows of x
    //   n : a 1-by-1 matrix of floating point integers, the number of columns of x
    //   x: a matrix of doubles, the random numbers in the set {0,1,2,3,...}.
    //
    // Description
    //   Generates random variables from the Poisson distribution function.
    //
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Examples
    // // Test with expanded lambda
    // x = distfun_poissrnd(1:6)
    //
    // // Check expansion of lambda in x = distfun_poissrnd(lambda)
    // x = distfun_poissrnd([12 14 20])
    // // Check x = distfun_poissrnd(lambda,v)
    // x = distfun_poissrnd(2,[4 5])
    //
    // // Check mean and variance
    // N = 50000;
    // lambda = 13;
    // x = distfun_poissrnd(lambda,[1 N]);
    // Mx = mean(x)
    // Vx = variance(x)
    // [M,V] = distfun_poissstat (lambda)
    //
    // // Check actual distribution
    // lambda=12;
    // N=10000;
    // h=scf();
    // x=distfun_poissrnd(lambda,1,N);
    // distfun_inthisto(x);
    // h.children.children(1).children.background=-2;
    // x=0:2*lambda;
    // y=distfun_poisspdf(x,lambda);
    // plot(x,y,"ro-");
    // xtitle("Poisson Random Numbers","X","Frequency")
    // legend(["Empirical","Density"]);
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Poisson_distribution
    // 
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
    // Copyright (C) 2012 - 2014 - Michael Baudin
endfunction

// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_nbinrnd(varargin)
    // Negative Binomial random numbers
    //
    // Calling Sequence
    //   x = distfun_nbinrnd(R,P)
    //   x = distfun_nbinrnd(R,P,[m,n])
    //   x = distfun_nbinrnd(R,P,m,n)
    //
    // Parameters
    //   R : a matrix of doubles, the number of successes. R belongs to the set {0,1,2,3,4,.......}
    //   P : a matrix of doubles, the probability of getting success in a Bernoulli trial. P in [0,1].
    //   m : a 1-by-1 matrix of floating point integers, the number of rows of x
    //   n : a 1-by-1 matrix of floating point integers, the number of columns of x
    //   x: a matrix of doubles, the random numbers, in the set {0,1,2,3,...}.
    //
    // Description
    //   Generates random variables from the Negative Binomial distribution function.
    //
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Examples
    // // Check with expanded R
    // R = [10 100 1000 10000]
    // P = 0.1
    // x = distfun_nbinrnd(R, P)
    //
    // // Check with expanded P
    // R =100
    // P = [0.1 0.2 0.3 0.4]
    // x = distfun_nbinrnd(R, P)
    //
    // // Check x = distfun_nbinrnd(R,P,[m,n])
    // x = distfun_nbinrnd(100,0.2,[4 5])
    //
    // //Check mean and variance
    // R = 10
    // P = 0.3
    // x = distfun_nbinrnd(R,P,[1 5000]);
    // Mx = mean(x)
    // Vx = variance(x)
    // [M,V] = distfun_nbinstat(R,P)
    //
    // // Check actual distribution
    // R=10;
    // P=0.7;
    // K=1000;
    // h=scf();
    // x=distfun_nbinrnd(R,P,1,K);
    // distfun_inthisto(x);
    // h.children.children(1).children.background=-2;
    // x=0:20;
    // y=distfun_nbinpdf(x,R,P);
    // plot(x,y,"ro-");
    // xtitle("Negative Binomial Random Numbers","X","Density")
    // legend(["Empirical","Density"]);
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Negative_binomial_distribution
    //
    // Authors
    // Copyright (C) 2014 - Michael Baudin

endfunction

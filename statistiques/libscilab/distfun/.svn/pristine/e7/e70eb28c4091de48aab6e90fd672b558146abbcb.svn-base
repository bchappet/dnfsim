// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_binornd(varargin)
    // Binomial random numbers
    //
    // Calling Sequence
    //   x = distfun_binornd(N,pr)
    //   x = distfun_binornd(N,pr,[m,n])
    //   x = distfun_binornd(N,pr,m,n)
    //
    // Parameters
    //   N : a matrix of doubles , the total number of binomial trials . N belongs to the set {1,2,3,4,.......}
    //   pr : a matrix of doubles, the probability of getting success in a Bernoulli trial. pr in [0,1].
    //   m : a 1-by-1 matrix of floating point integers, the number of rows of x
    //   n : a 1-by-1 matrix of floating point integers, the number of columns of x
    //   x: a matrix of doubles, the random numbers, in the set {0,1,2,3,...}.
    //
    // Description
    //   Generates random variables from the Binomial distribution function.
    //
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // If N<=2147483647, the algorithm is based on 
    //
    // Kachitvichyanukul, V. and Schmeiser, B. W. "Binomial Random Variate Generation.", 
    // Communications of the ACM, 31, 2 (February, 1988) 216.
    //
    // If N is larger, then we invert the CDF.
    //
    // Examples
    // // Check with expanded N
    // N = [10 100 1000 10000]
    // pr = 0.1
    // x = distfun_binornd(N, pr)
    //
    // // Check with expanded pr
    // N =100
    // pr = [0.1 0.2 0.3 0.4]
    // x = distfun_binornd(N, pr)
    //
    // // Check x = distfun_binornd(pr,v)
    // x = distfun_binornd(100,0.2,[4 5])
    //
    // //Check mean and variance
    // N = 1000
    // pr = 0.3
    // n = 5000
    // x = distfun_binornd(N,pr,[1 n]);
    // Mx = mean(x)
    // Vx = variance(x)
    // [M,V] = distfun_binostat(N,pr)
    //
    // // Check actual distribution
    // N=10;
    // pr=0.7;
    // K=10000;
    // h=scf();
    // x=distfun_binornd(N,pr,1,K);
    // distfun_inthisto(x);
    // h.children.children(1).children.background=-2;
    // x=0:N;
    // y=distfun_binopdf(x,N,pr);
    // plot(x,y,"ro-");
    // xtitle("Binomial Random Numbers","X","Density")
    // legend(["Empirical","Density"]);
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Binomial_distribution
    //
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
    // Copyright (C) 2012 - 2014 - Michael Baudin

endfunction

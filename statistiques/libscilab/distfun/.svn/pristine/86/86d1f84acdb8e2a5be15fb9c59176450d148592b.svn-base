// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_hygernd(varargin)
    // Hypergeometric random numbers
    //
    // Calling Sequence
    //   x = distfun_hygernd(M,k,N)
    //   x = distfun_hygernd(M,k,N,[m,n])
    //   x = distfun_hygernd(M,k,N,m,n)
    //
    // Parameters
    //   M : a matrix of doubles, the total size of the population . M belongs to the set {0,1,2,3........}
    //   k : a matrix of doubles, the number of successful states in the population . k belongs to the set {0,1,2,3.......M-1,M}
    //   N : a matrix of doubles, the total number of draws in the experiment . N belongs to the set {0,1,2,3,.......M-1,M}
    //   m : a 1-by-1 matrix of floating point integers, the number of rows of x
    //   n : a 1-by-1 matrix of floating point integers, the number of columns of x
    //   x: a matrix of doubles, the random numbers in the set {0,1,2,...,min(N,k)}.
    //
    // Description
    //   Generates random variables from the Hypergeometric distribution function.
    //
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // The algorithm is based on the inversion of the CDF.
    //
    // Examples
    // // Check x = distfun_hygernd(M,k,N,v)
    // x = distfun_hygernd(80,50,30,[4 5])
    //
    // // Check mean and variance
    // M = 80;
    // k = 50;
    // N = 30;
    // n = 50000;
    // x = distfun_hygernd(M,k,N,[1 n]);
    // Mx = mean(x)
    // Vx = variance(x)
    // [M,V] = distfun_hygestat(M,k,N)
    //
    // // Check actual distribution
    // h=scf();
    // M=80;
    // k=50;
    // N=30;
    // nsamples=10000;
    // x=distfun_hygernd(M,k,N,1,nsamples);
    // distfun_inthisto(x);
    // h.children.children(1).children.background=-2;
    // [Me,Va] = distfun_hygestat(M,k,N);
    // x=int(Me-3*sqrt(Va)):int(Me+3*sqrt(Va));
    // y=distfun_hygepdf(x,M,k,N);
    // plot(x,y,"ro-");
    // xtitle("Hypergeometric Random Numbers","X","Frequency")
    // legend(["Empirical","PDF"]);
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Hypergeometric_distribution
    //
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
    // Copyright (C) 2012 - 2014 - Michael Baudin

endfunction

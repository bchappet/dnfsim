// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_ksrnd(varargin)
    // Kolmogorov-Smirnov random numbers
    //
    // Calling Sequence
    //   x = distfun_ksrnd(N)
    //   x = distfun_ksrnd(N,[m,n])
    //   x = distfun_ksrnd(N,m,n)
    //
    // Parameters
    //   N : a matrix of doubles , the number of observations. N belongs to the set {1,2,3,4,.......,2147483647}
    //   m : a 1-by-1 matrix of floating point integers, the number of rows of x
    //   n : a 1-by-1 matrix of floating point integers, the number of columns of x
    //   x: a matrix of doubles, the random numbers, x in [0,1]
    //
    // Description
    //   Generates random variables from the Kolmogorov-Smirnov distribution function.
    //
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Examples
    // x = distfun_ksrnd(10)
    //
    // // Check x = distfun_ksrnd(N,v)
    // x = distfun_ksrnd(100,[4 5])
    //
    // // Check actual distribution
    // N=10;
    // K=10000;
    // h=scf();
    // x=distfun_ksrnd(N,1,K);
    // histplot(20,x);
    // x=linspace(0,1);
    // y=distfun_kspdf(x,N);
    // plot(x,y,"b-")
    // xtitle("Kolmogorov Smirnov Random Numbers","X","Density")
    // legend(["Empirical","Exact"]);
    //
    // // Compare empirical CDF with exact CDF
    // N=10;
    // K=10000;
    // y=1:K;
    // y=y/K;
    // x=distfun_ksrnd(N,[1,K]);
    // x=gsort(x,"g","i");
    // scf();
    // plot(x,y,"r-");
    // x=linspace(0,1);
    // p=distfun_kscdf(x,N);
    // plot(x,p,"b-");
    // legend(["Empirical","Exact"]);
    // xtitle("Kolmogorov-Smirnov Distribution","x","P(X<x)");
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Binomial_distribution
    //
    // Authors
    // Copyright (C) 2014 - Michael Baudin

endfunction

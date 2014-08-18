// Copyright (C) 2012 - 2014 - Michael Baudin
// Copyright (C) 2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_exprnd ( varargin )
    // Exponential random numbers
    //
    // Calling Sequence
    //   x = distfun_exprnd ( mu )
    //   x = distfun_exprnd ( mu , [m,n] )
    //   x = distfun_exprnd ( mu , m , n )
    //
    // Parameters
    //   mu : a matrix of doubles, the average. mu>0
    //   m : a 1-by-1 matrix of floating point integers, the number of rows of x
    //   n : a 1-by-1 matrix of floating point integers, the number of columns of x
    //   x: a matrix of doubles, the random numbers.
    //
    // Description
    //   Generates random variables from the Exponential distribution.
    //
    //   Any scalar input argument is expanded to a matrix of doubles of 
    //   the same size as the other input arguments.
    //
    // Examples
    // // Use x = distfun_exprnd ( mu )
    // x=distfun_exprnd(1:6)
    //
    // // Check x = distfun_exprnd ( mu , v )
    // x = distfun_exprnd(2,[1 5])
    // x = distfun_exprnd(2,[3 2])
    //
    // // Check x = distfun_exprnd ( mu , m , n )
    // x = distfun_exprnd([1 2 3;4 5 6],2,3)
    // x = distfun_exprnd(2,2,3)
    // x = distfun_exprnd(1,2,3)
    //
    // // Check mean and variance for x = distfun_exprnd ( mu )
    // N = 1000;
    // mu = 2;
    // x = distfun_exprnd(mu,1,N);
    // M=mean(x)
    // V=variance(x)
    // [M,V]=distfun_expstat(mu)
    //
    // // Make a plot of the actual distribution of the numbers
    // mu = 2;
    // scf();
    // x = distfun_exprnd(mu,1,1000);
    // histplot(10,x)
    // x = linspace(0,14,1000);
    // y = distfun_exppdf(x,mu);
    // plot(x,y)
    // xtitle("Exponential Random Numbers","X","Density")
    // legend(["Empirical","PDF"]);
    //
    // Authors
    //   Copyright (C) 2012 - 2014 - Michael Baudin
    //   Copyright (C) 2009-2011 - DIGITEO - Michael Baudin

endfunction


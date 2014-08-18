// Copyright (C) 2012 - 2014 - Michael Baudin
// Copyright (C) 2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_gamrnd ( varargin )
    // Gamma random numbers
    //
    // Calling Sequence
    //   x = distfun_gamrnd ( a , b )
    //   x = distfun_gamrnd ( a , b , [m,n] )
    //   x = distfun_gamrnd ( a , b , m , n )
    //
    // Parameters
    //   a : a matrix of doubles, the shape parameter, a>0.
    //   b : a matrix of doubles, the scale parameter, b>0.
    //   m : a 1-by-1 matrix of floating point integers, the number of rows of x
    //   n : a 1-by-1 matrix of floating point integers, the number of columns of x
    //   x: a matrix of doubles, the random numbers, x>=0.
    //
    // Description
    //   Generates random variablesfrom the Gamma distribution function.
    //
    //   Any scalar input argument is expanded to a matrix of doubles of 
    //   the same size as the other input arguments.
    //
    // Examples
    // // Use x = distfun_gamrnd ( a , b )
    // x=distfun_gamrnd(1:6,(1:6)^-1)
    // x=distfun_gamrnd(1:6,1)
    // x=distfun_gamrnd(1,(1:6)^-1)
    //
    // // Check x = distfun_gamrnd ( a , b , v )
    // x = distfun_gamrnd(2,1,[1 5])
    // x = distfun_gamrnd(2,1,[3 2])
    //
    // // Check x = distfun_gamrnd ( a , b , m , n )
    // x = distfun_gamrnd([1 2 3;4 5 6],0.1,2,3)
    // x = distfun_gamrnd(2,1,2,3)
    // x = distfun_gamrnd(1,[1 2 3;4 5 6],2,3)
    //
    // // Check mean and variance for x = distfun_gamrnd ( a , b )
    // N = 1000;
    // a = 2;
    // b = 3;
    // [M,V] = distfun_gamstat ( a , b )
    // x = distfun_gamrnd(2,3,[1 N]);
    // Mc = mean(x)
    // Mv = variance(x)
    //
    // // Make a plot of the actual distribution of the numbers
    // scf();
    // a = 2;
    // b = 3;
    // x = distfun_gamrnd(a,b,1,1000);
    // histplot(10,x)
    // x = linspace(0,30,1000);
    // y = distfun_gampdf(x,a,b);
    // plot(x,y)
    // xtitle("Gamma Random Numbers","X","Density")
    // legend(["Empirical","PDF"]);
    //
    // Authors
    //   Copyright (C) 2012 - 2014 - Michael Baudin
    //   Copyright (C) 2011 - DIGITEO - Michael Baudin

endfunction


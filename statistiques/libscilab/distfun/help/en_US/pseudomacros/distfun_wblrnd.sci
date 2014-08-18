// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_wblrnd ( varargin )
    // Weibull random numbers
    //
    // Calling Sequence
    //   x = distfun_wblrnd ( a , b )
    //   x = distfun_wblrnd ( a , b , [m,n] )
    //   x = distfun_wblrnd ( a , b , m , n )
    //
    // Parameters
    //   a : a matrix of doubles, the scale parameter, a>0.
    //   b : a matrix of doubles, the shape parameter, b>0.
    //   m : a 1-by-1 matrix of floating point integers, the number of rows of x
    //   n : a 1-by-1 matrix of floating point integers, the number of columns of x
    //   x: a matrix of doubles, the random numbers, x>=0
    //
    // Description
    //   Generates random variables from the Weibull distribution.
    //
    //   Any scalar input argument is expanded to a matrix of doubles of 
    //   the same size as the other input arguments.
    //
    // Examples
    // // Use x = distfun_wblrnd(a,b)
    // x=distfun_wblrnd(1:6,(1:6)^-1)
    //
    // // Check x = distfun_wblrnd ( a , b , v )
    // x = distfun_wblrnd(2,1,[3 2])
    //
    // // Check mean and variance
    // a = 2;
    // b = 3;
    // x=distfun_wblrnd(a,b,[1000,1]);
    // [M,V] = distfun_wblstat ( a , b )
    // Mx = mean(x)
    // Vx = variance(x)
    //
    // // Make a plot of the actual distribution of the numbers
    // a = 2;
    // b = 3;
    // x = distfun_wblrnd(a,b,1,1000);
    // scf();
    // histplot(10,x)
    // x = linspace(0,4.,1000);
    // y = distfun_wblpdf(x,a,b);
    // plot(x,y)
    // xtitle("Weibull random variables","X","Density");
    // legend(["Empirical","PDF"]);
    //
    // Authors
    //   Copyright (C) 2014 - Michael Baudin

endfunction


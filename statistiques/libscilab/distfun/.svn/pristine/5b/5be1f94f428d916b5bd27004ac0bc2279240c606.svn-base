// Copyright (C) 2012 - 2014 - Michael Baudin
// Copyright (C) 2009-2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_betarnd ( varargin )
    // Beta random numbers
    //
    // Calling Sequence
    //   x = distfun_betarnd ( a , b )
    //   x = distfun_betarnd ( a , b , [m,n] )
    //   x = distfun_betarnd ( a , b , m , n )
    //
    // Parameters
    //   a : a matrix of doubles, the first shape parameter, a>=0.
    //   b : a matrix of doubles, the first shape parameter, b>=0.
    //   m : a 1-by-1 matrix of floating point integers, the number of rows of x
    //   n : a 1-by-1 matrix of floating point integers, the number of columns of x
    //   x: a matrix of doubles, the random numbers, in the interval [0,1].
    //
    // Description
    //   Generates random variables from the Beta distribution.
    //
    //   Any scalar input argument is expanded to a matrix of doubles of 
    //   the same size as the other input arguments.
    //
    // Examples
    // // Use x = distfun_betarnd ( a , b )
    // x=distfun_betarnd(1:6,(1:6)^-1)
    // x=distfun_betarnd(1:6,1)
    // x=distfun_betarnd(1,(1:6)^-1)
    //
    // // Check x = distfun_betarnd ( a , b , v )
    // x = distfun_betarnd(2,1,[1 5])
    // x = distfun_betarnd(2,1,[3 2])
    //
    // // Use x = distfun_betarnd ( a , b , m , n )
    // x = distfun_betarnd([1 2 3;4 5 6],0.1,2,3)
    // x = distfun_betarnd(2,1,2,3)
    // x = distfun_betarnd(1,[1 2 3;4 5 6],2,3)
    //
    // // Check mean and variance for x = distfun_betarnd ( a , b )
    // N = 1000;
    // a = 1:6;
    // b = (1:6)^-1;
    // for i = 1:N
    //   computed(i,1:6) = distfun_betarnd(a,b);
    // end
    // [M,V] = distfun_betastat ( a , b )
    // Mx = mean(computed, "r")
    // Vx = variance(computed, "r")
    //
    // // Make a plot of the actual distribution of the numbers
    // a = 2;
    // b = 3;
    // x = distfun_betarnd(a,b,1,1000);
    // histplot(10,x)
    // x = linspace(0,1,1000);
    // y = distfun_betapdf(x,a,b);
    // plot(x,y)
    // xtitle("Beta random variables","X","Density");
    // legend(["Empirical","PDF"]);
    //
    // Authors
    //   Copyright (C) 2012 - 2014 - Michael Baudin
    //   Copyright (C) 2009-2011 - DIGITEO - Michael Baudin

endfunction


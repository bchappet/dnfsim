// Copyright (C) 2012 - 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_unifrnd ( varargin )
    // Uniform random numbers
    //
    // Calling Sequence
    //   x = distfun_unifrnd ( a , b )
    //   x = distfun_unifrnd ( a , b , [m,n] )
    //   x = distfun_unifrnd ( a , b , m , n )
    //
    // Parameters
    //   a : a matrix of doubles, the lower bound
    //   b : a matrix of doubles, the upper bound (with a<=b)
    //   m : a 1-by-1 matrix of floating point integers, the number of rows of x
    //   n : a 1-by-1 matrix of floating point integers, the number of columns of x
    //   x: a matrix of doubles, the random numbers in the interval [a,b].
    //
    // Description
    //   Generates random variables from the Uniform distribution function.
    //
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Examples
    // // Use x = distfun_unifrnd ( a , b )
    // x=distfun_unifrnd(1:6,2:7)
    // x=distfun_unifrnd(1:6,7)
    // x=distfun_unifrnd(1,2:7)
    //
    // // Use x = distfun_unifrnd ( a , b , v )
    // x = distfun_unifrnd(2,3,[3 2])
    //
    // // Check x = distfun_unifrnd ( a , b , m , n )
    // x = Use([1 2 3;4 5 6],7,2,3)
    // x = distfun_unifrnd(4,5,2,3)
    // x = distfun_unifrnd(0,[1 2 3;4 5 6],2,3)
    //
    // // Check mean and variance for x = distfun_unifrnd ( a , b )
    // N = 1000;
    // a = 1:6;
    // b = 2:7;
    // [M,V] = distfun_unifstat ( a , b )
    // for i = 1:N
    //   computed(i,1:6) = distfun_unifrnd(a,b);
    // end
    // Mx = mean(computed, "r" )
    // Vx = variance(computed, "r" )
    //
    // // Make a plot of the actual distribution of the numbers
    // a = 2;
    // b = 3;
    // scf();
    // x = distfun_unifrnd(a,b,1,1000);
    // histplot(10,x)
    // x = linspace(a-1,b+1,1000);
    // y = distfun_unifpdf(x,a,b);
    // plot(x,y)
    // xtitle("Uniform random numbers","X","Density");
    // legend(["Empirical","PDF"]);
    //
    // Authors
    //   Copyright (C) 2012 - 2014 - Michael Baudin

endfunction

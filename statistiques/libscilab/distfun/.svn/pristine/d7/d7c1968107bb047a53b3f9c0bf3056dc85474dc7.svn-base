// Copyright (C) 2012 - 2014 - Michael Baudin
// Copyright (C) 2009 - 2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_normrnd ( varargin )
    // Normal random numbers
    //
    // Calling Sequence
    //   x = distfun_normrnd ( mu , sigma )
    //   x = distfun_normrnd ( mu , sigma , [m,n] )
    //   x = distfun_normrnd ( mu , sigma , m , n )
    //
    // Parameters
    //   mu : a matrix of doubles, the average
    //   sigma : a matrix of doubles, the standard deviation. sigma>0.
    //   m : a 1-by-1 matrix of floating point integers, the number of rows of x
    //   n : a 1-by-1 matrix of floating point integers, the number of columns of x
    //   x: a matrix of doubles, the random numbers.
    //
    // Description
    //   Generates random variables from the Normal distribution function.
    //
    //   Any scalar input argument is expanded to a matrix of doubles of 
    //   the same size as the other input arguments.
    // 
    // Examples
    // // Test both mu and sigma expanded
    // x = distfun_normrnd(1:6,(1:6)^-1)
    //
    // // Test sigma expansion
    // x = distfun_normrnd(1:6,2.0)
    //
    // // Test mu expansion
    // x = distfun_normrnd(1.0,1:6)
    //
    // // Test with v
    // x = distfun_normrnd(0,1,[3 2])
    //
    // // Test with m, n
    // x = distfun_normrnd(0,1,3,2)
    //
    // // Make a plot of the actual distribution of the numbers
    // mu = 2;
    // sigma = 3;
    // scf();
    // x = distfun_normrnd(mu,sigma,1,1000);
    // histplot(10,x)
    // x = linspace(-10,10,1000);
    // y = distfun_normpdf(x,mu,sigma);
    // plot(x,y)
    // xtitle("Normal Random Numbers","X","Density")
    // legend(["Empirical","PDF"]);
    //
    // Authors
    //   Copyright (C) 2009 - 2011 - DIGITEO - Michael Baudin
    //   Copyright (C) 2012 - 2014 - Michael Baudin

endfunction


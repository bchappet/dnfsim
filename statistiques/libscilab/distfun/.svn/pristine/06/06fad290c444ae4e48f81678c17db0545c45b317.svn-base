// Copyright (C) 2012 - 2014 - Michael Baudin
// Copyright (C) 2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_lognrnd ( varargin )
    // Lognormal random numbers
    //
    // Calling Sequence
    //   x = distfun_lognrnd ( mu , sigma )
    //   x = distfun_lognrnd ( mu , sigma , [m,n] )
    //   x = distfun_lognrnd ( mu , sigma , m , n )
    //
    // Parameters
    //   mu : a matrix of doubles, the average.
    //   sigma : a matrix of doubles, the standard deviation. sigma>0.
    //   m : a 1-by-1 matrix of floating point integers, the number of rows of x
    //   n : a 1-by-1 matrix of floating point integers, the number of columns of x
    //   x: a matrix of doubles, the positive random numbers.
    //
    // Description
    //   Generates random variables from the lognormal distribution function.
    //
    //   Any scalar input argument is expanded to a matrix of doubles of 
    //   the same size as the other input arguments.
    // 
    // Examples
    // // Test both mu and sigma expanded
    // x = distfun_lognrnd(1:6,(1:6)^-1)
    //
    // // Test sigma expansion
    // x = distfun_lognrnd(1:6,2.0)
    //
    // // Test mu expansion
    // x = distfun_lognrnd(1.0,1:6)
    //
    // // Test with v
    // x = distfun_lognrnd(0,1,[3 2])
    //
    // // Test with m, n
    // x = distfun_lognrnd(0,1,3,2)
    //
    // // Make a plot of the actual distribution of the numbers
    // scf();
    // mu = 2;
    // sigma = 3;
    // x = distfun_lognrnd(mu,sigma,1,1000);
    // histplot(10,log(x));
    // x = linspace(-10,10,1000);
    // y = distfun_normpdf(x,mu,sigma);
    // plot(x,y)
    // xtitle("Lognormal random variables","Log(X)","Density");
    // legend(["Empirical","PDF"]);
    //
    // Authors
    //   Copyright (C) 2012 - 2014 - Michael Baudin
    //   Copyright (C) 2011 - DIGITEO - Michael Baudin

endfunction

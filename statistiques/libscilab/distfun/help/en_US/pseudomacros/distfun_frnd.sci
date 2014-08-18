// Copyright (C) 2012 - 2014 - Michael Baudin
// Copyright (C) 2012 - Prateek Papriwal
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_frnd(varargin)
    // F-Distribution random numbers
    //
    // Calling Sequence
    //   x = distfun_frnd(v1,v2)
    //   x = distfun_frnd(v1,v2,[m,n])
    //   x = distfun_frnd(v1,v2,m,n)
    //
    // Parameters
    //   v1 : a matrix of doubles, numerator degrees of freedom, v1>0 (can be non integer). 
    //   v2 : a matrix of doubles, denominator degrees of freedom, v2>0 (can be non integer). 
    //   m : a 1-by-1 matrix of floating point integers, the number of rows of x
    //   n : a 1-by-1 matrix of floating point integers, the number of columns of x
    //   x: a matrix of doubles, the random numbers in the set [0,∞).
    //
    // Description
    //   Generates random variables from the F-distribution function.
    //
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Examples
    // // Test with expanded v1 and v2
    // x = distfun_frnd(1:6,1:6)
    //
    // // Check x = distfun_frnd(v1,v2,v)
    // x = distfun_frnd(2,5,[3 3])
    //
    // // Check mean and variance
    // N = 5000;
    // v1 = 3;
    // v2 = 5;
    // x = distfun_frnd(v1,v2,[1 N]);
    // RM = mean(x)
    // RV = variance(x)
    // [M,V] = distfun_fstat(v1,v2)
    //
    // // Make a plot of the actual distribution of the numbers
    // v1 = 10;
    // v2 = 50;
    // data = distfun_frnd(v1,v2,1,1000);
    // scf();
    // histplot(20,data)
    // x = linspace(0,4,1000);
    // y = distfun_fpdf(x,v1,v2);
    // plot(x,y)
    // xtitle("F Random Numbers","X","Density")
    // legend(["Empirical","PDF"]);
    //
    // // Make a plot of a ratio of chi-square random numbers
    // scf();
    // v1 = 10;
    // v2 = 50;
    // R1 = distfun_chi2rnd(v1,1,1000);
    // R2 = distfun_chi2rnd(v2,1,1000);
    // x = (R1./v1)./(R2./v2);
    // histplot(20,x)
    // x = linspace(0,4,1000);
    // y = distfun_fpdf(x,v1,v2);
    // plot(x,y)
    // xtitle("$\textrm{Random Numbers }\frac{R_1/v_1}{R_2/v_2}$","X","Density")
    // legend(["Empirical","PDF"]);
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/F-distribution
    // 
    // Authors
    // Copyright (C) 2012 - 2014 - Michael Baudin
    // Copyright (C) 2012 - Prateek Papriwal

endfunction

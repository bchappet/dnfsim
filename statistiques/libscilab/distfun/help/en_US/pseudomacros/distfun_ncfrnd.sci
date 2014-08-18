// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_ncfrnd(varargin)
    // Noncentral F random numbers
    //
    // Calling Sequence
    //   x = distfun_ncfrnd(v1,v2,delta)
    //   x = distfun_ncfrnd(v1,v2,delta,[m,n])
    //   x = distfun_ncfrnd(v1,v2,delta,m,n)
    //
    // Parameters
    //   v1 : a matrix of doubles, numerator degrees of freedom, v1>0 (can be non integer). 
    //   v2 : a matrix of doubles, denominator degrees of freedom, v2>0 (can be non integer). 
    //   delta : a matrix of doubles, the noncentrality parameter, delta>=0
    //   m : a 1-by-1 matrix of floating point integers, the number of rows of x
    //   n : a 1-by-1 matrix of floating point integers, the number of columns of x
    //   x: a matrix of doubles, the random numbers in the set [0,∞).
    //
    // Description
    //   Generates random variables from the Noncentral F-distribution function.
    //
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Examples
    // x = distfun_ncfrnd(2,5,3)
	//
    // // Test with expanded v1, v2 and delta
    // x = distfun_ncfrnd(1:6,1:6,3)
    //
    // // Check mean and variance
    // N = 5000;
    // v1 = 3;
    // v2 = 5;
    // delta = 7;
    // x = distfun_ncfrnd(v1,v2,delta,[1 N]);
    // RM = mean(x)
    // RV = variance(x)
    // [M,V] = distfun_ncfstat(v1,v2,delta)
    //
    // // Make a plot of the actual distribution of the numbers
    // v1 = 10;
    // v2 = 50;
    // delta = 7;
    // data = distfun_ncfrnd(v1,v2,delta,1,1000);
    // scf();
    // histplot(20,data)
    // x = linspace(0,4,1000);
    // y = distfun_ncfpdf(x,v1,v2,delta);
    // plot(x,y)
    // xtitle("Noncentral F Random Numbers","X","Density")
    // legend(["Empirical","PDF"]);
    //
    // // Make a plot of a ratio of chi-square random numbers
    // scf();
    // v1 = 10;
    // v2 = 50;
    // delta = 7;
    // R1 = distfun_ncx2rnd(v1,delta,1,1000);
    // R2 = distfun_chi2rnd(v2,1,1000);
    // x = (R1./v1)./(R2./v2);
    // histplot(20,x)
    // x = linspace(0,4,1000);
    // y = distfun_ncfpdf(x,v1,v2,delta);
    // plot(x,y)
    // xtitle("$\textrm{Random Numbers }\frac{R_1/v_1}{R_2/v_2}$","X","Density")
    // legend(["Empirical","PDF"]);
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Noncentral_F-distribution
    // 
    // Authors
    // Copyright (C) 2014 - Michael Baudin

endfunction

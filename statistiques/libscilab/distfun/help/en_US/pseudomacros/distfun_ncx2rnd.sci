// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_ncx2rnd(varargin)
    // Noncentral Chi-Squared random numbers
    //
    // Calling Sequence
    //   x = distfun_ncx2rnd(k,delta)
    //   x = distfun_ncx2rnd(k,delta,[m,n])
    //   x = distfun_ncx2rnd(k,delta,m,n)
    //
    // Parameters
    //   k : a matrix of doubles, the number of degrees of freedom, k>0 (can be non integer)
    //   delta : a matrix of doubles, the noncentrality parameter, delta>=0
    //   m : a 1-by-1 matrix of floating point integers, the number of rows of x
    //   n : a 1-by-1 matrix of floating point integers, the number of columns of x
    //   x: a matrix of doubles, the positive random numbers.
    //
    // Description
    //   Generates random variables from the Noncentral Chi-Squared distribution function.
    //
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Examples
    // // Test with expanded k
    // x = distfun_ncx2rnd((1:6)',10)
    //
    // // Check x = distfun_ncx2rnd(k,delta,[m,n])
    // x = distfun_ncx2rnd(2,10,[4 5])
    //
    // // Check mean and variance
    // N = 50000;
    // k = 3;
    // delta = 5;
    // x = distfun_ncx2rnd(k,delta,[1 N]);
    // Mx = mean(x)
    // Vx = variance(x)
    // [M,V] = distfun_ncx2stat (k,delta)
    //
    // // Compare histogram of random numbers 
    // // with PDF.
    // k=5;
    // delta=7;
    // scf();
    // x=distfun_ncx2rnd(k,delta,10000,1);
    // histplot(30,x);
    // x=linspace(0,30,1000);
    // y=distfun_ncx2pdf(x,k,delta);
    // plot(x,y,"b-");
    // xtitle("Noncentral Chi-Squared random numbers","X","Density");
    // legend(["Empirical","PDF"]);
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Noncentral_chi-squared_distribution
    //
    // Authors
    // Copyright (C) 2014 - Michael Baudin

endfunction

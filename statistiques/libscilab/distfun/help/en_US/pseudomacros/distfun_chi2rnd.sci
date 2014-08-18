// Copyright (C) 2014 - Michael Baudin
// Copyright (C) 2012 - Prateek Papriwal
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_chi2rnd(varargin)
    // Chi-Square random numbers
    //
    // Calling Sequence
    //   x = distfun_chi2rnd(k)
    //   x = distfun_chi2rnd(k,[m,n])
    //   x = distfun_chi2rnd(k,m,n)
    //
    // Parameters
    //   k : a matrix of doubles, the number of degrees of freedom, k>0 (can be non integer)
    //   m : a 1-by-1 matrix of floating point integers, the number of rows of x
    //   n : a 1-by-1 matrix of floating point integers, the number of columns of x
    //   x: a matrix of doubles, the positive random numbers.
    //
    // Description
    //   Generates random variables from the Chi-Square distribution function.
    //
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Examples
    // // Test with expanded k
    // x = distfun_chi2rnd((1:6)')
    //
    // // Check x = distfun_chi2rnd(k,v)
    // x = distfun_chi2rnd(2,[4 5])
    //
    // // Check mean and variance
    // N = 50000;
    // k = 3;
    // x = distfun_chi2rnd(k,[1 N]);
    // Mx = mean(x)
    // Vx = variance(x)
    // [M,V] = distfun_chi2stat (k)
    //
    // // Compare histogram of random numbers 
    // // with PDF.
    // k=5;
    // scf();
    // x=distfun_chi2rnd(k,10000,1);
    // histplot(30,x);
    // x=linspace(0,25,1000);
    // y=distfun_chi2pdf(x,k);
    // plot(x,y,"b-");
    // xtitle("Chi-square random numbers","X","Density");
    // legend(["Empirical","PDF"]);
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Chi-squared_distribution
    //
    // Authors
    // Copyright (C) 2014 - Michael Baudin
    // Copyright (C) 2012 - Prateek Papriwal

endfunction

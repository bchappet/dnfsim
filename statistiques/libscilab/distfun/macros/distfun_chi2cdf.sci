// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
//
function p = distfun_chi2cdf(varargin)
    // Chi-squared CDF
    //
    // Calling Sequence
    //   p = distfun_chi2cdf(x,k)
    //   p = distfun_chi2cdf(x,k,lowertail)
    //   
    // Parameters
    //   x : a matrix of doubles, the outcome, greater or equal to zero
    //   k : a matrix of doubles, the number of degrees of freedom, k>0 (can be non integer)
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   p : a matrix of doubles, the probability.
    //
    // Description
    //   Computes the cumulative distribution function of 
    //   the Chi-squared distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Examples
    // // Test with x scalar, k scalar
    // computed = distfun_chi2cdf(4,5)
    // expected = 0.4505840
    //
    // // Test with expanded x, k scalar
    // computed = distfun_chi2cdf([2 6],5)
    // expected = [0.1508550 0.6937811]
    //
    // // Test with x scalar, k expanded
    // computed = distfun_chi2cdf(4,[4 7])
    // expected = [0.5939942 0.2202226]
    // 
    // // Test with both x,k expanded
    // computed = distfun_chi2cdf([2 6],[3 4])
    // expected = [0.4275933 0.8008517]
    //
    // // Plot the function
    // h=scf();
    // k = [2 3 4 6 9 12];
    // cols = [1 2 3 4 5 6];
    // lgd = [];
    // for i = 1:size(k,'c')
    //   x = linspace(0,10,1000);
    //   y = distfun_chi2cdf ( x , k(i) );
    //   plot(x,y)
    //   str = msprintf("k=%s",string(k(i)));
    //   lgd($+1) = str;
    // end
    // for i = 1:size(k,'c')
    //     hcc = h.children.children;
    //     hcc.children(size(k,'c') - i + 1).foreground = cols(i);
    // end
    // xtitle("Chi-squared CDF","x","$P(X\leq x)$");
    // legend(lgd);
    // 
    // Bibliography
    // http://en.wikipedia.org/wiki/Chi-squared_distribution
    //
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
    // Copyright (C) 2012 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_chi2cdf",rhs,2:3)
    apifun_checklhs("distfun_chi2cdf",lhs,0:1)

    x = varargin(1)
    k = varargin(2)
    lowertail = apifun_argindefault(varargin,3,%t)
    //
    // Check type
    //
    apifun_checktype("distfun_chi2cdf",x,"x",1,"constant")
    apifun_checktype("distfun_chi2cdf",k,"k",2,"constant")
    apifun_checktype("distfun_chi2cdf",lowertail,"lowertail",3,"boolean")

    apifun_checkscalar("distfun_chi2cdf",lowertail,"lowertail",3)
    //
    //Check content
    //    
    apifun_checkgreq("distfun_chi2cdf",x,"x",1,0)
    tiny=number_properties("tiny")
    apifun_checkgreq("distfun_chi2cdf",k,"k",2,tiny)

    [x,k] = apifun_expandvar(x,k)
    p = distfun_cdfchi2(x,k,lowertail)
endfunction

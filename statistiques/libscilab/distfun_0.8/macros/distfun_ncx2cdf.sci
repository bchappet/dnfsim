// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
//
function p = distfun_ncx2cdf(varargin)
    // Noncentral Chi-Squared CDF
    //
    // Calling Sequence
    //   p = distfun_ncx2cdf(x,k,delta)
    //   p = distfun_ncx2cdf(x,k,delta,lowertail)
    //   
    // Parameters
    //   x : a matrix of doubles, the outcome, greater or equal to zero
    //   k : a matrix of doubles, the number of degrees of freedom, k>0 (can be non integer)
    //   delta : a matrix of doubles, the noncentrality parameter, delta>=0
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   p : a matrix of doubles, the probability.
    //
    // Description
    //   Computes the cumulative distribution function of 
    //   the Noncentral Chi-Squared distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // <emphasis>Caution</emphasis>
    // This distribution is known to have inferior accuracy in 
    // some cases.
    //
    // Examples
    // computed = distfun_ncx2cdf(9,4,5)
    // expected = 0.5692367
    //
    // // Plot the function
    // h=scf();
    // k = [2 2 2 4 4 4];
    // delta = [1 2 3 1 2 3];
    // cols = [1 2 3 4 5 6];
    // lgd = [];
    // for i = 1:size(k,'c')
    //   x = linspace(0,10,1000);
    //   y = distfun_ncx2cdf ( x, k(i), delta(i) );
    //   plot(x,y)
    //   str = msprintf("k=%s, delta=%s",..
    //       string(k(i)),string(delta(i)));
    //   lgd($+1) = str;
    // end
    // for i = 1:size(k,'c')
    //     hcc = h.children.children;
    //     hcc.children(size(k,'c') - i + 1).foreground = cols(i);
    // end
    // xtitle("Noncentral Chi-squared CDF","x","$P(X\leq x)$");
    // legend(lgd);
    // 
    // Bibliography
    // http://en.wikipedia.org/wiki/Noncentral_chi-squared_distribution
    //
    // Authors
    // Copyright (C) 2014 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_ncx2cdf",rhs,3:4)
    apifun_checklhs("distfun_ncx2cdf",lhs,0:1)

    x = varargin(1)
    k = varargin(2)
    delta = varargin(3)
    lowertail = apifun_argindefault(varargin,4,%t)
    //
    // Check type
    apifun_checktype("distfun_ncx2cdf",x,"x",1,"constant")
    apifun_checktype("distfun_ncx2cdf",k,"k",2,"constant")
    apifun_checktype("distfun_ncx2cdf",delta,"delta",3,"constant")
    apifun_checktype("distfun_ncx2cdf",lowertail,"lowertail",4,"boolean")
    //
    // Check size
    apifun_checkscalar("distfun_ncx2cdf",lowertail,"lowertail",3)
    //
    // Check content
    apifun_checkgreq("distfun_ncx2cdf",x,"x",1,0)
    tiny=number_properties("tiny")
    apifun_checkgreq("distfun_ncx2cdf",k,"k",2,tiny)
    apifun_checkgreq("distfun_ncx2cdf",delta,"delta",3,0.)

    [x,k,delta] = apifun_expandvar(x,k,delta)
    p = distfun_cdfncx2(x,k,delta,lowertail)
endfunction

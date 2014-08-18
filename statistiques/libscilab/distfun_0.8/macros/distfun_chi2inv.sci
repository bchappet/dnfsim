// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
//

function x = distfun_chi2inv(varargin)
    // Chi-squared Inverse CDF
    //
    // Calling Sequence
    //   x = distfun_chi2cdf(p,k)
    //   x = distfun_chi2cdf(p,k,lowertail)
    //   
    // Parameters
    //   p : a matrix of doubles, the probability.
    //   k : a matrix of doubles, the number of degrees of freedom, k>0 (can be non integer)
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   x : a matrix of doubles, the outcome. x belongs to the set {0,1,2,3,......}
    //
    // Description
    //   Computes the Inverse cumulative distribution function of 
    //   the Chi-squared distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Examples
    // // Test with p scalar, k scalar
    // computed = distfun_chi2inv(0.4,5)
    // expected = 3.6554996
    //
    // // Test with expanded p, k scalar
    // computed = distfun_chi2inv([0.2 0.6],5)
    // expected = [2.3425343 5.1318671]
    //
    // // Test with p scalar, k expanded
    // computed = distfun_chi2inv(0.44,[4 7])
    // expected = [2.9870195 5.827751]
    // 
    // // Test with both p,k expanded
    // computed = distfun_chi2inv([0.22 0.66],[3 4])
    // expected = [1.0878828 4.5215487]
    //
    // // Test small values of p
    // x = distfun_chi2inv(1.e-15,6)
    // expected = 0.0000363
    // x = distfun_chi2inv(1.e-15,6,%f)
    // expected = 82.67507
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Chi-squared_distribution
    //
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
    // Copyright (C) 2012 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_chi2inv",rhs,2:3)
    apifun_checklhs("distfun_chi2inv",lhs,0:1)

    p = varargin(1)
    k = varargin(2)
    lowertail = apifun_argindefault(varargin,3,%t)

    //
    // Check type
    apifun_checktype("distfun_chi2inv",p,"p",1,"constant")
    apifun_checktype("distfun_chi2inv",k,"k",2,"constant")
    apifun_checktype("distfun_chi2inv",lowertail,"lowertail",3,"boolean")

    apifun_checkscalar("distfun_chi2inv",lowertail,"lowertail",3)
    //
    // Check Content
    apifun_checkrange("distfun_chi2inv",p,"p",1,0,1)
    tiny=number_properties("tiny")
    apifun_checkgreq("distfun_chi2inv",k,"k",2,tiny)

    [p,k] = apifun_expandvar(p,k)
    x = distfun_invchi2(p,k,lowertail)
endfunction

// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
//

function x = distfun_unidinv(varargin)
    // Uniform Discrete Inverse CDF
    //
    // Calling Sequence
    //   x = distfun_unidinv(p,N)
    //   x = distfun_unidinv(p,N,lowertail)
    //
    //Parameters
    //   p : a matrix of doubles, the probability. Must be in the range [0,1].
    //   N : a matrix of doubles , the total number of binomial trials . N belongs to the set {1,2,3,4,.......}
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   x : a matrix of doubles, the outcome. x belongs to the set {0,1,2,3,...,N}
    //
    // Description
    //   Computes the Inverse cumulative distribution function of 
    //   the Uniform Discrete distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    //Examples
    // // Check with all arguments scalar
    // x = distfun_unidinv(0.21,162)
    // expected = 35
    //
    // // check with p expanded
    // x = distfun_unidinv([0.05 0.95],162)
    // expected = [9 154]
    //
    // // Check with N expanded
    // x = distfun_unidinv(0.05,[100 162])
    // expected = [5 9]
    //
    // //Check with all arguments expanded
    // x = distfun_unidinv([0.05 0.95],[100 162])
    // expected = [5 154]
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Binomial_distribution
    //
    // Authors
    // Copyright (C) 2014 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_unidinv",rhs,2:3)
    apifun_checklhs("distfun_unidinv",lhs,0:1)

    p = varargin(1)
    N = varargin(2)
    lowertail = apifun_argindefault(varargin,3,%t)
    //
    // Check type
    apifun_checktype("distfun_unidinv",p,"p",1,"constant")
    apifun_checktype("distfun_unidinv",N,"N",2,"constant")
    apifun_checktype("distfun_unidinv",lowertail,"lowertail",3,"boolean")
    //
    // Check size
    apifun_checkscalar("distfun_unidinv",lowertail,"lowertail",3)
    //
    // Check Content
    apifun_checkrange("distfun_unidinv",p,"p",1,0,1)
    apifun_checkgreq("distfun_unidinv",N,"N",2,1)
    apifun_checkflint("distfun_unidinv",N,"N",2)

    [p,N] = apifun_expandvar(p,N)
    if (lowertail) then
        x=ceil(p.*N)
    else
        x=N-floor(p.*N)
    end
endfunction

// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
//

function x = distfun_binoinv(varargin)
    // Binomial Inverse CDF
    //
    // Calling Sequence
    //   x = distfun_binoinv(p,N,pr)
    //   x = distfun_binoinv(p,N,pr,lowertail)
    //
    //Parameters
    //   p : a matrix of doubles, the probability. Must be in the range [0,1].
    //   N : a matrix of doubles , the total number of binomial trials . N belongs to the set {1,2,3,4,.......}
    //   pr : a matrix of doubles,  the probability of success in a Bernoulli trial. pr in [0,1].
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   x : a matrix of doubles, the number of successes. x belongs to the set {0,1,2,3,...,N}
    //
    // Description
    //   Computes the Inverse cumulative distribution function of 
    //   the Binomial distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    //Examples
    //
    // // Check with all arguments scalar
    // x = distfun_binoinv(0.21,162,0.5)
    // expected = 76
    //
    // // check with p expanded
    // x = distfun_binoinv([0.05 0.95],162,0.5)
    // expected = [71 91]
    //
    // // Check with N expanded
    // x = distfun_binoinv(0.05,[100 162],0.5)
    // expected = [42 71]
    //
    // // Check with expanded pr
    // x = distfun_binoinv(0.05,162,[0.2 0.5])
    // expected = [24 71]
    //
    // //Check with all arguments expanded
    // x = distfun_binoinv([0.05 0.95],[100 162],[0.2 0.5])
    // expected = [14 91]
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Binomial_distribution
    //
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
    // Copyright (C) 2012 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_binoinv",rhs,3:4)
    apifun_checklhs("distfun_binoinv",lhs,0:1)

    p = varargin(1)
    N = varargin(2)
    pr = varargin(3)
    lowertail = apifun_argindefault(varargin,4,%t)
    //
    // Check type
    apifun_checktype("distfun_binoinv",p,"p",1,"constant")
    apifun_checktype("distfun_binoinv",N,"N",2,"constant")
    apifun_checktype("distfun_binoinv",pr,"pr",3,"constant")
    apifun_checktype("distfun_binoinv",lowertail,"lowertail",4,"boolean")
    //
    // Check size
    apifun_checkscalar("distfun_binoinv",lowertail,"lowertail",4)
    //
    // Check Content
    apifun_checkrange("distfun_binoinv",p,"p",1,0,1)
    apifun_checkgreq("distfun_binoinv",N,"N",2,1)
    apifun_checkflint("distfun_binoinv",N,"N",2)
    apifun_checkrange("distfun_binoinv",pr,"pr",3,0,1)

    [p,N,pr] = apifun_expandvar(p,N,pr)
    x = distfun_invbino(p,N,pr,lowertail)
endfunction

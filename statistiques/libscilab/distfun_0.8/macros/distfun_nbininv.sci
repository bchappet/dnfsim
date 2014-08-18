// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
//

function x = distfun_nbininv(varargin)
    // Negative Binomial Inverse CDF
    //
    // Calling Sequence
    //   x = distfun_nbininv(p,R,P)
    //   x = distfun_nbininv(p,R,P,lowertail)
    //
    // Parameters
    //   p : a matrix of doubles, the probability. Must be in the range [0,1].
    //   R : a matrix of doubles, the number of successes. R belongs to the set {0,1,2,3,4,.......}
    //   P : a matrix of doubles, the probability of getting success in a Bernoulli trial. P in [0,1].
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   x : a matrix of doubles, the extra trials for R successes. x belongs to the set {0,1,2,3,...,N}
    //
    // Description
    //   Computes the Inverse cumulative distribution function of 
    //   the Negative Binomial distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Examples
    // // Check with all arguments scalar
    // x = distfun_nbininv(0.21,10,0.5)
    // expected = 6
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Negative_binomial_distribution
    //
    // Authors
    // Copyright (C) 2014 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_nbininv",rhs,3:4)
    apifun_checklhs("distfun_nbininv",lhs,0:1)

    p = varargin(1)
    R = varargin(2)
    P = varargin(3)
    lowertail = apifun_argindefault(varargin,4,%t)
    //
    // Check type
    apifun_checktype("distfun_nbininv",p,"p",1,"constant")
    apifun_checktype("distfun_nbininv",R,"R",2,"constant")
    apifun_checktype("distfun_nbininv",P,"P",3,"constant")
    apifun_checktype("distfun_nbininv",lowertail,"lowertail",4,"boolean")
    //
    // Check size
    apifun_checkscalar("distfun_nbininv",lowertail,"lowertail",4)
    //
    // Check Content
    apifun_checkrange("distfun_nbininv",p,"p",1,0,1)
    apifun_checkflint("distfun_nbincdf",R,"R",2)
    apifun_checkgreq("distfun_nbincdf",R,"R",2,1)
    apifun_checkrange("distfun_nbincdf",P,"P",3,0.,1.)
    //
    [p,R,P] = apifun_expandvar(p,R,P)
    //
    x=distfun_invnbn(p,R,P,lowertail)
endfunction

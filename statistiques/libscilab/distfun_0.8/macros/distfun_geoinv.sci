// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
//

function x = distfun_geoinv(varargin)
    //  Geometric Inverse CDF
    //
    // Calling Sequence
    //   x = distfun_geoinv(p,pr)
    //   x = distfun_geoinv(p,pr,lowertail)
    //
    // Parameters
    //   p : a matrix of doubles, the probability. Must be in the range [0,1].
    //   pr : a matrix of doubles,  the probability of success in a Bernoulli trial. pr in (0,1].
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   x : a matrix of doubles, the outcome. x belongs to the set {0,1,2,3,......}
    //
    // Description
    //   Computes the Inverse cumulative distribution function of 
    //   the Geometric distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Examples
    // // Test with p scalar, pr scalar
    //x = distfun_geoinv(0.999,0.5)
    //expected = 9;
    //x = distfun_geoinv(1-0.999,0.5,%f)
    //expected = 9;
    //
    // // Test with expanded p , scalar pr
    //x = distfun_geoinv([0.32 0.3],0.2)
    //expected = [1. 1.];
    //
    // // Test with scalar p, expanded pr
    //x = distfun_geoinv(0.22,[0.33 0.1])
    //expected = [0. 2.];
    //
    //
    // // Test small values of p
    // xn = distfun_geoinv(1.e-15,0.1)
    // expected = 0.;
    // xn = distfun_geoinv(1.e-15,0.1,%f)
    // expected = 327.;
    //
    // // Test small values of pr
    // xn = distfun_geoinv(0.1,1.e-20)
    // expected = 10536051565782630122;
    // xn = distfun_geoinv(0.1,1.e-20,%f)
    // expected = 230258509299404568400;
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Geometric_distribution
    //
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
    // Copyright (C) 2012 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_geoinv",rhs,2:3)
    apifun_checklhs("distfun_geoinv",lhs,0:1)

    p = varargin(1)
    pr = varargin(2)
    lowertail = apifun_argindefault(varargin,3,%t)
    //
    // Check type
    apifun_checktype("distfun_geoinv",p,"p",1,"constant")
    apifun_checktype("distfun_geoinv",pr,"pr",2,"constant")
    apifun_checktype("distfun_geoinv",lowertail,"lowertail",3,"boolean")

    apifun_checkscalar("distfun_geoinv",lowertail,"lowertail",3)
    //
    // Check Content
    apifun_checkrange("distfun_geoinv",p,"p",1,0,1)
	tiniest=number_properties("tiniest")
    apifun_checkrange("distfun_geoinv",pr,"pr",2,tiniest,1)

    [p,pr] = apifun_expandvar(p,pr)
    x=distfun_invgeo(p,pr,lowertail)
endfunction

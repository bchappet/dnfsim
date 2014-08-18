// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
//

function x = distfun_ncfinv(varargin)
    // Noncentral F Inverse CDF
    //
    // Calling Sequence
    //   x = distfun_ncfinv(p,v1,v2,delta)
    //   x = distfun_ncfinv(p,v1,v2,delta,lowertail)
    //
    // Parameters
    //   p : a matrix of doubles, the probability. Must be in the range [0,1].
    //   v1 : a matrix of doubles, numerator degrees of freedom, v1>0 (can be non integer). 
    //   v2 : a matrix of doubles, denominator degrees of freedom, v2>0 (can be non integer). 
    //   delta : a matrix of doubles, the noncentrality parameter, delta>=0
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   x : a matrix of doubles. x is real and x>=0.
    //
    // Description
    //   Computes the Inverse cumulative distribution function of 
    //   the Noncentral F distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // <emphasis>Caution</emphasis>
    // This distribution is known to have inferior accuracy in 
    // some cases.
    //
    // Examples
    // computed = distfun_ncfinv(0.7, 4, 12, 0.3)
    // expected = 1.4786561151681499
    //
    // // Test with small value of p
    // computed = distfun_ncfinv(1.e-15,4, 12, 0.3)
    // expected = 3.342537766492919456D-07
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Noncentral_F-distribution
    //
    // Authors
    // Copyright (C) 2014 - Michael Baudin
    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_ncfinv",rhs,4:5)
    apifun_checklhs("distfun_ncfinv",lhs,0:1)
    
    p = varargin(1)
    v1 = varargin(2)
    v2=varargin(3)
    delta=varargin(4)
    lowertail = apifun_argindefault(varargin,5,%t)
    //
    // Check type
    apifun_checktype("distfun_ncfinv",p,"p",1,"constant")
    apifun_checktype("distfun_ncfinv",v1,"v1",2,"constant")
    apifun_checktype("distfun_ncfinv",v2,"v2",3,"constant")
    apifun_checktype("distfun_ncfinv",delta,"delta",4,"constant")
    apifun_checktype("distfun_ncfinv",lowertail,"lowertail",5,"boolean")
    //
    // Check size
    apifun_checkscalar("distfun_ncfinv",lowertail,"lowertail",5)
    //
    // Check content
    apifun_checkrange( "distfun_ncfinv" , p , "p" , 1 , 0, 1 )
    tiniest=number_properties("tiniest")
    apifun_checkgreq("distfun_ncfinv",v1,"v1",2,tiniest)
    apifun_checkgreq("distfun_ncfinv",v2,"v2",3,tiniest)
    apifun_checkgreq("distfun_ncfinv",delta,"delta",4,0.)
    
    [p,v1,v2,delta] = apifun_expandvar(p,v1,v2,delta)
    x = distfun_invncf(p,v1,v2,delta,lowertail)
endfunction

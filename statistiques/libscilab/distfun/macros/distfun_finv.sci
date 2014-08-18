// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
//

function x = distfun_finv(varargin)
    // F-distribution Inverse CDF
    //
    // Calling Sequence
    //   x = distfun_finv(p,v1,v2)
    //   x = distfun_finv(p,v1,v2,lowertail)
    //
    // Parameters
    //   p : a matrix of doubles, the probability. Must be in the range [0,1].
    //   v1 : a matrix of doubles, numerator degrees of freedom, v1>0 (can be non integer). 
    //   v2 : a matrix of doubles, denominator degrees of freedom, v2>0 (can be non integer). 
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   x : a matrix of doubles. x is real and x>=0.
    //
    // Description
    //   Computes the Inverse cumulative distribution function of 
    //   the f distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Examples
    //
    // // Test with p,v1,v2 scalar
    // computed = distfun_finv(0.95,5,10)
    // expected = 3.325834530413011247D+00
    //
    // // Test with p expanded, v1 and v2 scalar
    // computed = distfun_finv([0.5 0.95],5,10)
    // expected = [
	// 9.319331608510478260D-01 
	// 3.325834530413011247D+00
	// ]'
    //
    // // Test with p scalar, v1 and v2 expanded
    // computed = distfun_finv(0.95,[5 10],[10 5])
    // expected = [
	// 3.325834530413011247D+00 
	// 4.735063069693421944D+00
	// ]
    //
    // // Test with p,v1,v2 expanded
    // computed = distfun_finv([0.5 0.95],[5 10],[10 5])
    // expected = [
	// 9.319331608510478260D-01 
	// 4.735063069693421944D+00
	// ]
    //
    // // Test with small value of p
    // computed = distfun_finv(1.e-15,5,10)
    // expected = 5.660986432909706613D-07
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/F-distribution
    //
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
    // Copyright (C) 2012 - Michael Baudin
    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_finv",rhs,3:4)
    apifun_checklhs("distfun_finv",lhs,0:1)
    
    p = varargin(1)
    v1 = varargin(2)
    v2=varargin(3)
    lowertail = apifun_argindefault(varargin,4,%t)
    //
    // Check type
    apifun_checktype("distfun_finv",p,"p",1,"constant")
    apifun_checktype("distfun_finv",v1,"v1",2,"constant")
    apifun_checktype("distfun_finv",v2,"v2",3,"constant")
    apifun_checktype("distfun_finv",lowertail,"lowertail",4,"boolean")
    //
    // Check size
    apifun_checkscalar("distfun_finv",lowertail,"lowertail",4)
    //
    // Check content
    apifun_checkrange( "distfun_finv" , p , "p" , 1 , 0, 1 )
    tiniest=number_properties("tiniest")
    apifun_checkgreq("distfun_finv",v1,"v1",2,tiniest)
    apifun_checkgreq("distfun_finv",v2,"v2",3,tiniest)
    
    [p,v1,v2] = apifun_expandvar(p,v1,v2)
    x = distfun_invf(p,v1,v2,lowertail)
endfunction

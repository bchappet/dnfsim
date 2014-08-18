// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_unifinv ( varargin )
    // Uniform Inverse CDF
    //
    // Calling Sequence
    //   x = distfun_unifinv ( p , a , b )
    //   x = distfun_unifinv ( p , a , b , lowertail )
    //
    // Parameters
    //   p : a matrix of doubles, the probability. Must be in the range [0,1].
    //   a : a matrix of doubles, the lower bound
    //   b : a matrix of doubles, the upper bound (with a<=b)
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   x : a matrix of doubles, the outcome
    //
    // Description
    //   Computes the inverse Uniform cumulated probability 
    //   distribution function of the Uniform function.
    //   
    //   Any scalar input argument is expanded to a matrix of 
    //   doubles of the same size as the other input arguments.
    //
    // Examples
    // // Test argument expansion
    // x=distfun_unifinv([0.5 0.9 0.7],1.0,2.0)
    // expected = [1.5    1.9    1.7]
    //
    // // Test with expanded arguments
    // x=distfun_unifinv([0.5 0.9 0.7],[1 1 1],[2 2 2])
    // expected = [1.5    1.9    1.7]
    //
    // // See upper tail
    // x=distfun_unifinv(1.e-15,1.,2.)
    // x=distfun_unifinv(1.e-15,1.,2.,%f)
    //
    // Authors
    //   Copyright (C) 2012 - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_unifinv" , rhs , 3:4 )
    apifun_checklhs ( "distfun_unifinv" , lhs , 0:1 )
    //
    p = varargin ( 1 )
    a = varargin ( 2 )
    b = varargin ( 3 )
    lowertail = apifun_argindefault ( varargin , 4 , %t )
    //
    // Check type
    apifun_checktype ( "distfun_unifinv" , p , "p" , 1 , "constant" )
    apifun_checktype ( "distfun_unifinv" , a , "a" , 2 , "constant" )
    apifun_checktype ( "distfun_unifinv" , b , "b" , 3 , "constant" )
    apifun_checktype ( "distfun_unifinv" , lowertail , "lowertail" , 4 , "boolean" )
    //
    // Check size
    apifun_checkscalar ( "distfun_unifinv" , lowertail , "lowertail" , 4 )
    //
    [ p , a , b ] = apifun_expandvar ( p , a , b )
    //
    // Check content
    apifun_checkrange( "distfun_unifinv" , p , "p" , 1 , 0, 1 )
    apifun_checkgreq ( "distfun_unifinv" , b , "b" , 3 , a )
    //
    x=distfun_invunif(p,a,b,lowertail)
endfunction


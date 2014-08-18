// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_gaminv ( varargin )
    // Gamma Inverse CDF
    //
    // Calling Sequence
    //   x = distfun_gaminv ( p , a , b )
    //   x = distfun_gaminv ( p , a , b , lowertail )
    //
    // Parameters
    //   p : a matrix of doubles, the probability. Must be in the range [0,1].
    //   a : a matrix of doubles, the shape parameter, a>0.
    //   b : a matrix of doubles, the scale parameter, b>0.
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   x : a matrix of doubles, the outcome, x>=0
    //
    // Description
    //   Computes the Gamma inverse cumulated probability distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of 
    //   doubles of the same size as the other input arguments.
    //
    // Examples
    // // Test x scalar, a scalar, b expanded
    // b = 1:5;
    // p = [  ..
    //   6.321205588285576660D-01  ..
    //   3.934693402873664647D-01  ..
    //   2.834686894262107293D-01  ..
    //   2.211992169285951215D-01  ..
    //   1.812692469220181790D-01  ..
    // ]
    // x = distfun_gaminv(p,1,b)
    //
    // // See upper tail
    // distfun_gaminv(1.e-20,3,5,%f)
    //
    // Authors
    //   Copyright (C) 2011 - DIGITEO - Michael Baudin
    //   Copyright (C) 2012 - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_gaminv" , rhs , 3:4 )
    apifun_checklhs ( "distfun_gaminv" , lhs , 0:1 )
    //
    p = varargin(1)
    a = varargin(2)
    b = varargin(3)
    lowertail = apifun_argindefault ( varargin , 4 , %t )
    //
    // Check type
    apifun_checktype ( "distfun_gaminv" , p , "p" , 1 , "constant" )
    apifun_checktype ( "distfun_gaminv" , a , "a" , 2 , "constant" )
    apifun_checktype ( "distfun_gaminv" , b , "b" , 3 , "constant" )
    apifun_checktype ( "distfun_gaminv" , lowertail , "lowertail" , 4 , "boolean" )
    //
    // Check size (let expandvar do the check for the other args)
    apifun_checkscalar ( "distfun_gaminv" , lowertail , "lowertail" , 4 )
    //
    // Check content
    apifun_checkrange( "distfun_gaminv" , p , "p" , 1 , 0, 1 )
    tiny=number_properties("tiny")
    apifun_checkgreq( "distfun_gaminv" , a , "a" , 2 , tiny )
    apifun_checkgreq( "distfun_gaminv" , b , "b" , 3 , tiny )
    //
    [ p , a , b ] = apifun_expandvar ( p , a , b )
    //
    x = distfun_invgam ( p, a , b , lowertail)
endfunction


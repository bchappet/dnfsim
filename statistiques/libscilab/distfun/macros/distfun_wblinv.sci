// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_wblinv ( varargin )
    // Weibull Inverse CDF
    //
    // Calling Sequence
    //   x = distfun_wblinv ( p , a , b )
    //   x = distfun_wblinv ( p , a , b , lowertail )
    //
    // Parameters
    //   p : a matrix of doubles, the probability. Must be in the range [0,1].
    //   a : a matrix of doubles, the scale parameter, a>0.
    //   b : a matrix of doubles, the shape parameter, b>0.
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   x : a matrix of doubles, the outcome
    //
    // Description
    // Computes the Inverse Weibull cumulative probability distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles of the same size as the other input arguments.
    //
    // Examples
    // // Check expansion of a and b
    // p = [0.01 0.5 0.99];
    // x = distfun_wblinv(p,10,5)
    // expected = [3.9850715  9.2931959  13.572165]
    //
    // // See upper tail
    // p = [0.01 0.5 0.99];
    // x = distfun_wblinv(p,[8 9 10],[5 6 7])
    // x = distfun_wblinv(p,[8 9 10],[5 6 7],%f)
    //
    // Authors
    //  Copyright (C) 2014 - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_wblinv" , rhs , 3:4 )
    apifun_checklhs ( "distfun_wblinv" , lhs , 0:1 )
    //
    p  = varargin(1) 
    a  = varargin(2)
    b = varargin(3)
    lowertail = apifun_argindefault ( varargin , 4 , %t )
    //
    // Check type
    apifun_checktype ( "distfun_wblinv" , p , "p" , 1 , "constant" )
    apifun_checktype ( "distfun_wblinv" , a , "a" , 2 , "constant" )
    apifun_checktype ( "distfun_wblinv" , b , "b" , 3 , "constant" )
    //
    // Check size (let expandvar do the check for the other args)
    apifun_checkscalar ( "distfun_wblinv" , lowertail , "lowertail" , 4 )
    //
    // Check content
    apifun_checkrange( "distfun_wblinv" , p , "p" , 1 , 0, 1 )
    tiny=number_properties("tiny")
    apifun_checkgreq ( "distfun_wblinv" , a , "a" , 2 , tiny )
    apifun_checkgreq ( "distfun_wblinv" , b , "b" , 3 , tiny )  
    //
    [ p , a , b ] = apifun_expandvar ( p , a , b )
    x = distfun_invwbl(p,a,b,lowertail)
endfunction


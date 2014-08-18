// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2009-2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_betainv ( varargin )
    // Beta Inverse CDF
    //
    // Calling Sequence
    //   x = distfun_betainv ( p , a , b )
    //   x = distfun_betainv ( p , a , b , lowertail )
    //
    // Parameters
    //   p : a matrix of doubles, the probability. Must be in the range [0,1].
    //   a : a matrix of doubles, the first shape parameter, a>=0.
    //   b : a matrix of doubles, the second shape parameter, b>=0.
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   x : a matrix of doubles, the outcome
    //
    // Description
    // Computes the Inverse Beta cumulative probability distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles of the same size as the other input arguments.
    //
    // Examples
    // // Check expansion of a and b
    // p = [0.01 0.5 0.99];
    // x = distfun_betainv(p,10,5)
    // expected = [..
    // 0.372565305114573 , .. 
    // 0.674248844713787 , .. 
    // 0.898071404278149 ..
    // ]
    // //
    // // Check with expanded a and b
    // p = [0.01 0.5 0.99];
    // x = distfun_betainv(p,[8 9 10],[5 6 7])
    // expected = [..
    // 0.302404222173137 , ..
    // 0.604556744392119 , ..
    // 0.833540311685497 ..
    // ]
    //
    // // See upper tail
    // p = [0.01 0.5 0.99];
    // x = distfun_betainv(p,[8 9 10],[5 6 7])
    // x = distfun_betainv(p,[8 9 10],[5 6 7],%f)
    //
    // Authors
    //  Copyright (C) 2012 - Michael Baudin
    //  Copyright (C) 2009-2011 - DIGITEO - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_betainv" , rhs , 3:4 )
    apifun_checklhs ( "distfun_betainv" , lhs , 0:1 )
    //
    p  = varargin(1) 
    a  = varargin(2)
    b = varargin(3)
    lowertail = apifun_argindefault ( varargin , 4 , %t )
    //
    // Check type
    apifun_checktype ( "distfun_betainv" , p , "p" , 1 , "constant" )
    apifun_checktype ( "distfun_betainv" , a , "a" , 2 , "constant" )
    apifun_checktype ( "distfun_betainv" , b , "b" , 3 , "constant" )
    //
    // Check size (let expandvar do the check for the other args)
    apifun_checkscalar ( "distfun_betainv" , lowertail , "lowertail" , 4 )
    //
    // Check content
    apifun_checkrange( "distfun_betainv" , p , "p" , 1 , 0, 1 )
    apifun_checkgreq ( "distfun_betainv" , a , "a" , 2 , 0 )
    apifun_checkgreq ( "distfun_betainv" , b , "b" , 3 , 0 )  
    //
    [ p , a , b ] = apifun_expandvar ( p , a , b )
    x = distfun_invbeta(p,a,b,lowertail)
endfunction


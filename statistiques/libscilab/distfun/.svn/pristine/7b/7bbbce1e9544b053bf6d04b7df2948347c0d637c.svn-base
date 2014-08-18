// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt



function x = distfun_loguinv ( varargin )
    // LogUniform Inverse CDF
    //
    // Calling Sequence
    //   x = distfun_loguinv ( p , a , b )
    //   x = distfun_loguinv ( p , a , b , lowertail )
    //
    // Parameters
    //   p : a matrix of doubles, the probability. Must be in the range [0,1].
    //   a: a matrix of doubles, the minimum of the underlying uniform variable. 
    //   b: a matrix of doubles, the maximum of the underlying uniform variable. b>a.
    // lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    // x: a matrix of doubles
    //
    // Description
    //   This function computes the LogUniform quantile.
    //
    //   Any scalar input argument is expanded to a matrix of doubles of the same size 
    //   as the other input arguments.
    //
    // Any optional input argument equal to the empty matrix will be set to its
    // default value.
    //
    // Examples
    // x=distfun_loguinv(0.6,1,2)
    // xexpected = 4.9530324
    //
    // // Check the inverse LogUniforme
    // a=1;
    // b=10;
    // x=exp(2)
    // p=distfun_logucdf(x,a,b)
    // x=distfun_loguinv(p,a,b) // Must be exp(2)
    //
    // // Test upper tail
    // x=distfun_loguinv(0.1,1,2,%f) // 6.6858944
    //
    // Authors
    // Copyright (C) 2013 - Michael Baudin

    [lhs, rhs] = argn()
    apifun_checkrhs ( "distfun_loguinv" , rhs , 3:4 )
    apifun_checklhs ( "distfun_loguinv" , lhs , 0:1 )
    //
    p = varargin(1)
    a = varargin(2)
    b = varargin(3)
    lowertail = apifun_argindefault ( varargin , 4 , %t )
    //
    // Check type
    apifun_checktype ( "distfun_loguinv" , p , "p" , 1 , "constant" )
    apifun_checktype ( "distfun_loguinv" , a , "a" , 2 , "constant" )
    apifun_checktype ( "distfun_loguinv" , b , "b" , 3 , "constant" )
    apifun_checktype ( "distfun_loguinv" , lowertail , "lowertail" , 4 , "boolean" )
    //
    // Check size : OK
    [ p , a , b ] = apifun_expandvar ( p , a , b )
    //
    // Check content
    apifun_checkrange ( "distfun_loguinv" , p , "p" , 1 , 0 , 1 )
    apifun_checkgreq ( "distfun_loguinv" , b , "b" , 2 , a )  
    //
    // Proceed ...
    //
    if (a==[]) then
        x=[]
        return
    end
    if (lowertail) then
        x=exp(a+p.*(b-a))
    else
        x=exp(b+p.*(a-b))
    end
endfunction


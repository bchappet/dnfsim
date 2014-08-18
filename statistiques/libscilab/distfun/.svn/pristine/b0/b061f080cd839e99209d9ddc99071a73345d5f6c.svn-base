// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2011 - INRIA - Michael Baudin
// Copyright (C) 2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt


function x = distfun_expinv ( varargin )
    // Exponential Inverse CDF
    //
    // Calling Sequence
    //   x = distfun_expinv ( p , mu )
    //   x = distfun_expinv ( p , mu , lowertail )
    //
    // Parameters
    // p: a matrix of doubles, the probability
    // mu : a matrix of doubles, the average. mu>0
    // lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    // x: a matrix of doubles
    //
    // Description
    //   This function computes the Exponential Quantile.
    //
    //   Any scalar input argument is expanded to a matrix of doubles of the same size 
    //   as the other input arguments.
    //
    // Any optional input argument equal to the empty matrix will be set to its
    // default value.
    //
    // Examples
    // x = distfun_expinv ( 0.7 , 2. )
    // xexpected = 2.4079456
    //
    // // See upper tail
    // x = distfun_expinv ( 0.8 , 1/2 )
    // x = distfun_expinv ( 0.8 , 1/2 , %f )
    // // See an extreme case
    // x = distfun_expinv ( 1.e-20 , 1/2 , %f )
    // // See an extreme case
    // x = distfun_expinv(1.e-20,1)
    //
    // Authors
    // Copyright (C) 2008-2011 - INRIA - Michael Baudin
    // Copyright (C) 2012 - Michael Baudin
    //
    // Bibliography
    // Wikipedia, Exponential distribution function, http://en.wikipedia.org/wiki/Exponential_distribution

    [lhs, rhs] = argn()
    apifun_checkrhs ( "distfun_expinv" , rhs , 2:3 )
    apifun_checklhs ( "distfun_expinv" , lhs , 0:1 )
    //
    p = varargin(1)
    mu = varargin(2)
    lowertail = apifun_argindefault ( varargin , 3 , %t )
    //
    // Check type
    apifun_checktype ( "distfun_expinv" , p , "p" , 1 , "constant" )
    apifun_checktype ( "distfun_expinv" , mu , "mu" , 2 , "constant" )
    apifun_checktype ( "distfun_expinv" , lowertail , "lowertail" , 3 , "boolean" )
    //
    // Check size (let expandvar do the check for the other args)
    apifun_checkscalar ( "distfun_expinv" , lowertail , "lowertail" , 3 )
    //
    // Check content
    apifun_checkrange ( "distfun_expinv" , p , "p" , 1 , 0 , 1 )
    tiny = number_properties("tiny")
    apifun_checkgreq ( "distfun_expinv" , mu , "mu" , 2 , tiny )
    //
    // Proceed ...
    [ p , mu ] = apifun_expandvar ( p , mu )
    x=distfun_invexp(p,mu,lowertail)
endfunction


// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2009 - 2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_norminv ( varargin )
    // Normal Inverse CDF
    //
    // Calling Sequence
    //   x = distfun_norminv ( p , mu , sigma )
    //   x = distfun_norminv ( p , mu , sigma , lowertail )
    //
    // Parameters
    //   p : a matrix of doubles, the probability. Must be in the range [0,1].
    //   mu : a matrix of doubles, the mean.
    //   sigma : a matrix of doubles, the standard deviation. sigma>0.
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   x : a matrix of doubles, the outcome
    //
    // Description
    //   Computes the inverse Normal cumulated probability distribution function of the Normal (Laplace-Gauss) function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles of the same size as the other input arguments.
    //
    // Examples
    // // Test with default mu, sigma
    // x = distfun_norminv ( [0.5 0.9 0.7] , 0 , 1 )
    // expected = [ ..
    // 0.000000000000000 , ..
    // 1.281551565544601 , ..
    // 0.524400512708041 ..
    // ]
    //
    // // Test argument expansion
    // x=distfun_norminv([0.5 0.9 0.7],1.0,2.0)
    // expected = [ ..
    //  1.00000000000000 , ..
    //  3.56310313108920 , ..
    //  2.04880102541608 ..
    // ]
    //
    // // Test with expanded arguments
    // x=distfun_norminv([0.5 0.9 0.7],[1 1 1],[2 2 2])
    // expected = [ ..
    // 1.00000000000000 , ..
    // 3.56310313108920 , ..
    // 2.04880102541608 ..
    // ]
    //
    // // See upper tail
    // x=distfun_norminv(0.0001,1.,2.,%f)
    // // See an extreme case
    // x=distfun_norminv(1.e-20,1.,2.,%f)
    //
    // Authors
    //   Copyright (C) 2009 - 2011 - DIGITEO - Michael Baudin
    //   Copyright (C) 2012 - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_norminv" , rhs , 3:4 )
    apifun_checklhs ( "distfun_norminv" , lhs , 0:1 )
    //
    p = varargin ( 1 )
    mu = varargin (2)
    sigma = varargin (3)
    lowertail = apifun_argindefault ( varargin , 4 , %t )
    //
    // Check type
    apifun_checktype ( "distfun_norminv" , p , "p" , 1 , "constant" )
    apifun_checktype ( "distfun_norminv" , mu , "mu" , 2 , "constant" )
    apifun_checktype ( "distfun_norminv" , sigma , "sigma" , 3 , "constant" )
    apifun_checktype ( "distfun_norminv" , lowertail , "lowertail" , 4 , "boolean" )
    //
    // Check size (let expandvar do the check for the other args)
    apifun_checkscalar ( "distfun_norminv" , lowertail , "lowertail" , 4 )
    //
    // Check content
    apifun_checkrange( "distfun_norminv" , p , "p" , 1 , 0, 1 )
    tiny = number_properties("tiny")
    apifun_checkgreq ( "distfun_norminv" , sigma , "sigma" , 3 , tiny )
    //
    [ p , mu , sigma ] = apifun_expandvar ( p , mu , sigma )
    x = distfun_invnorm(p,mu,sigma,lowertail)
endfunction


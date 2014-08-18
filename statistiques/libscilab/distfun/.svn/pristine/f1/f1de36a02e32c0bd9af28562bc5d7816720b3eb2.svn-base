// Copyright (C) 2013 - Michael Baudin

//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_evinv ( varargin )
    // Extreme value (Gumbel) Inverse CDF
    //
    // Calling Sequence
    //   x = distfun_evinv ( p , mu , sigma )
    //   x = distfun_evinv ( p , mu , sigma , lowertail )
    //
    // Parameters
    //   p : a matrix of doubles, the probability. Must be in the range [0,1].
    //   mu : a matrix of doubles, the location.
    //   sigma : a matrix of doubles, the scale. sigma>0.
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   x : a matrix of doubles, the outcome
    //
    // Description
    //   Computes the inverse cumulated probability distribution function of the Extreme value (Gumbel) function.
    //   This is the minimum Gumbel distribution.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles of the same size as the other input arguments.
    //
    // Examples
    // x = distfun_evinv([0.5 0.9 0.7],0,1)
    // expected = [-0.3665129 0.8340324 0.1856268]
    //
    // x=1;
    // mu=0.5;
    // sigma=2.;
    // lowertail=%t;
    // p=distfun_evcdf(x,mu,sigma,lowertail);
    // x1=distfun_evinv(p,mu,sigma,lowertail)
    // //
    // lowertail=%f;
    // p=distfun_evcdf(x,mu,sigma,lowertail);
    // x1=distfun_evinv(p,mu,sigma,lowertail)
    //
    // Authors
    //   Copyright (C) 2013 - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_evinv" , rhs , 3:4 )
    apifun_checklhs ( "distfun_evinv" , lhs , 0:1 )
    //
    p = varargin ( 1 )
    mu = varargin (2)
    sigma = varargin (3)
    lowertail = apifun_argindefault ( varargin , 4 , %t )
    //
    // Check type
    apifun_checktype ( "distfun_evinv" , p , "p" , 1 , "constant" )
    apifun_checktype ( "distfun_evinv" , mu , "mu" , 2 , "constant" )
    apifun_checktype ( "distfun_evinv" , sigma , "sigma" , 3 , "constant" )
    apifun_checktype ( "distfun_evinv" , lowertail , "lowertail" , 4 , "boolean" )
    //
    // Check size (let expandvar do the check for the other args)
    apifun_checkscalar ( "distfun_evinv" , lowertail , "lowertail" , 4 )
    //
    // Check content
    apifun_checkrange( "distfun_evinv" , p , "p" , 1 , 0, 1 )
    tiny = number_properties("tiny")
    apifun_checkgreq ( "distfun_evinv" , sigma , "sigma" , 3 , tiny )
    //
    [ p , mu , sigma ] = apifun_expandvar ( p , mu , sigma )
    //
    x=distfun_invev(p,mu,sigma,lowertail)
endfunction


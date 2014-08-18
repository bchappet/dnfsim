// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function p = distfun_evcdf ( varargin )
    // Extreme value (Gumbel) CDF
    //
    // Calling Sequence
    //   p=distfun_evcdf(x,mu,sigma)
    //   p=distfun_evcdf(x,mu,sigma,lowertail)
    //
    // Parameters
    //   x : a matrix of doubles, the outcome
    //   mu : a matrix of doubles, the mean
    //   sigma : a matrix of doubles, the standard deviation. sigma>0.
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   p : a matrix of doubles, the probability.
    //
    // Description
    //   Computes the cumulated probability distribution function 
    //   of the Extreme value (Gumbel) function.
    //   This is the minimum Gumbel distribution.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Examples
    // // Test expanded arguments
    // computed = distfun_evcdf([1 2 3],1,2)
    // expected = [0.6321206 0.8077044 0.9340120]
    //
    // // Plot the Gumbel CDF
    // N=1000;
    // x=linspace(-20,5,N);
    // p1= distfun_evcdf(x,-0.5,2.,%t);
    // p2= distfun_evcdf(x,-1.0,2.,%t);
    // p3= distfun_evcdf(x,-1.5,3.,%t);
    // p4= distfun_evcdf(x,-3.0,4.,%t);
    // scf();
    // xtitle("Gumbel","x","P(X<x)");
    // plot(x,p1,"r-")
    // plot(x,p2,"g-")
    // plot(x,p3,"b-")
    // plot(x,p4,"c-")
    // leg(1)="$\mu=-0.5,\beta=2.0$";
    // leg(2)="$\mu=-1.0,\beta=2.0$";
    // leg(3)="$\mu=-1.5,\beta=3.0$";
    // leg(4)="$\mu=-3.0,\beta=4.0$";
    // legend(leg,"in_upper_left");
    // 
    // p=distfun_evcdf(1,0.5,2.,%t)
    // q=distfun_evcdf(1,0.5,2.,%f)
    // p+q
    //
    // Authors
    //   Copyright (C) 2013 - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_evcdf" , rhs , 3:4 )
    apifun_checklhs ( "distfun_evcdf" , lhs , 0:1 )
    //
    x = varargin ( 1 )
    mu = varargin ( 2 )
    sigma = varargin ( 3 )
    lowertail = apifun_argindefault ( varargin , 4 , %t )
    //
    apifun_checktype ( "distfun_evcdf" , x , "x" , 1 , "constant" )
    apifun_checktype ( "distfun_evcdf" , mu , "mu" , 2 , "constant" )
    apifun_checktype ( "distfun_evcdf" , sigma , "sigma" , 3 , "constant" )
    apifun_checktype ( "distfun_evcdf" , lowertail , "lowertail" , 4 , "boolean" )
    //
    // Check size (let expandvar do the check for the other args)
    apifun_checkscalar ( "distfun_evcdf" , lowertail , "lowertail" , 4 )
    //
    // Check content
    tiny = number_properties("tiny")
    apifun_checkgreq ( "distfun_evcdf" , sigma , "sigma" , 3 , tiny )
    //
    [ x , mu , sigma ] = apifun_expandvar ( x , mu , sigma )
    //
    p=distfun_cdfev(x,mu,sigma,lowertail)
endfunction


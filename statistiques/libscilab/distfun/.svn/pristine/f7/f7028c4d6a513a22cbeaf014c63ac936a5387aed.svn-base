// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2011 - INRIA - Michael Baudin
// Copyright (C) 2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt


function p = distfun_expcdf ( varargin )
    // Exponential CDF
    //
    // Calling Sequence
    //   p = distfun_expcdf ( x , mu )
    //   p = distfun_expcdf ( x , mu , lowertail )
    //
    // Parameters
    // x: a matrix of doubles
    // mu : a matrix of doubles, the average. mu>0
    // lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    // p: a matrix of doubles, the probability
    //
    // Description
    //   This function computes the Exponential CDF.
    //
    //   Any scalar input argument is expanded to a matrix of doubles of the same size 
    //   as the other input arguments.
    //
    // Any optional input argument equal to the empty matrix will be set to its
    // default value.
    // 
    // Examples
    // x = 2
    // mu = 1/3
    // computed = distfun_expcdf ( x , mu )
    // expected = 9.975212478233337e-1
    //
    // // http://en.wikipedia.org/wiki/Exponential_distribution
    // scf();
    // x = linspace(0,5,1000);
    // p = distfun_expcdf ( x , 2 );
    // plot(x,p, "r-" );
    // p = distfun_expcdf ( x , 1 );
    // plot(x,p, "m-" );
    // p = distfun_expcdf ( x , 2/3 );
    // plot(x,p, "c-" );
    // xtitle("Exponential CDF","x","$P(X\leq x)$");
    // legend(["mu=2","mu=1","mu=2/3"]);
    //
    // // See upper tail
    // p = distfun_expcdf ( 2 , 1/3 )
    // q = distfun_expcdf ( 2 , 1/3 , %f )
    // p+q 
    //
    // // See accuracy for small x
    // p = distfun_expcdf ( 1.e-20 , 1 )
    // expected = 1.e-20
    //
    // // For negative inputs, the probability is 
    // // zero
    // distfun_expcdf(-10,2)
    //
    // Authors
    // Copyright (C) 2008-2011 - INRIA - Michael Baudin
    // Copyright (C) 2011 - DIGITEO - Michael Baudin
    // Copyright (C) 2012 - Michael Baudin
    //
    // Bibliography
    // Wikipedia, Exponential distribution function, http://en.wikipedia.org/wiki/Exponential_distribution

    [lhs, rhs] = argn()
    apifun_checkrhs ( "distfun_expcdf" , rhs , 2:3 )
    apifun_checklhs ( "distfun_expcdf" , lhs , 0:1 )
    //
    x = varargin(1)
    mu = varargin(2)
    lowertail = apifun_argindefault ( varargin , 3 , %t )
    //
    // Check type
    apifun_checktype ( "distfun_expcdf" , x , "x" , 1 , "constant" )
    apifun_checktype ( "distfun_expcdf" , mu , "mu" , 2 , "constant" )
    apifun_checktype ( "distfun_expcdf" , lowertail , "lowertail" , 3 , "boolean" )
    //
    // Check size (let expandvar do the check for the other args)
    apifun_checkscalar ( "distfun_expcdf" , lowertail , "lowertail" , 3 )
    //
    // Check content
    tiny = number_properties("tiny")
    apifun_checkgreq ( "distfun_expcdf" , mu , "mu" , 2 , tiny )
    //
    // Proceed ...
    [ x , mu ] = apifun_expandvar ( x , mu )
    //
    p=distfun_cdfexp(x,mu,lowertail)
endfunction


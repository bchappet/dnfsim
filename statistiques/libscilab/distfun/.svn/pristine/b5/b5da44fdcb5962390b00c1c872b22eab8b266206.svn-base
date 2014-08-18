// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2008 - 2011 - INRIA - Michael Baudin
// Copyright (C) 2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function p = distfun_logncdf ( varargin )
    // Lognormal CDF
    //
    // Calling Sequence
    //   p = distfun_logncdf ( x , mu , sigma )
    //   p = distfun_logncdf ( x , mu , sigma , lowertail )
    //
    // Parameters
    // x: a matrix of doubles
    // mu: a matrix of doubles, the mean of the underlying normal variable.
    // sigma: a matrix of doubles, the variance of the underlying normal variable. sigma>0.
    // lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    // p: a matrix of doubles, the probability
    //
    // Description
    //   This function computes the Lognormal Cumulated Density Function.
    //
    //   Any scalar input argument is expanded to a matrix of doubles of the same size 
    //   as the other input arguments.
    //
    // Any optional input argument equal to the empty matrix will be set to its
    // default value.
    //
    // Examples
    // p = distfun_logncdf ( 2. , 3. , 10. )
    // pexpected = 0.4087797
    //
    // // See wikipedia.org
    // scf();
    // x = linspace ( 0 , 3 , 1000 );
    // p = distfun_logncdf ( x , 0. , 10 );
    // plot ( x , p , "k" );
    // p = distfun_logncdf ( x , 0. , 3/2 );
    // plot ( x , p , "b" );
    // p = distfun_logncdf ( x , 0. , 1 );
    // plot ( x , p , "g" );
    // p = distfun_logncdf ( x , 0. , 1/2 );
    // plot ( x , p , "y" );
    // p = distfun_logncdf ( x , 0. , 1/4 );
    // plot ( x , p , "r" );
    // legend ( ["s=10" "s=3/2" "s=1" "s=1/2" "s=1/4"] );
    // xtitle("The log-normale CDF","x","$P(X\leq x)$");
	//
	// // See upper tail : 2.0301530180231740447e-14
	//  p = distfun_logncdf ( 1.e7 , 1. , 2. , %f )
    //
    // Authors
    // Copyright (C) 2008 - 2011 - INRIA - Michael Baudin
    // Copyright (C) 2011 - DIGITEO - Michael Baudin
    // Copyright (C) 2012 - Michael Baudin
    //
    // Bibliography
    // Dider Pelat, "Bases et méthodes pour le traitement de données", section 8.2.8, "Loi log-normale".
    // Wikipedia, Lognormal probability distribution function, http://en.wikipedia.org/wiki/File:Lognormal_distribution_PDF.png
    // Wikipedia, Lognormal cumulated distribution function, http://en.wikipedia.org/wiki/File:Lognormal_distribution_CDF.png

    [lhs, rhs] = argn()
    apifun_checkrhs ( "distfun_logncdf" , rhs , 3:4 )
    apifun_checklhs ( "distfun_logncdf" , lhs , 0:1 )
    //
    x = varargin(1)
    mu = varargin(2)
    sigma = varargin(3)
    lowertail = apifun_argindefault ( varargin , 4 , %t )
    //
    // Check type
    apifun_checktype ( "distfun_logncdf" , x , "x" , 1 , "constant" )
    apifun_checktype ( "distfun_logncdf" , mu , "mu" , 2 , "constant" )
    apifun_checktype ( "distfun_logncdf" , sigma , "sigma" , 3 , "constant" )
    apifun_checktype ( "distfun_logncdf" , lowertail , "lowertail" , 4 , "boolean" )
    //
    // Check size : OK
    //
    // Check content
    tiny = number_properties("tiny")
    apifun_checkgreq ( "distfun_logncdf" , sigma , "sigma" , 3 , tiny )
    //
    // Proceed ...
    [ x , mu , sigma ] = apifun_expandvar ( x , mu , sigma )
    //
    p=distfun_cdflogn(x,mu,sigma,lowertail)
endfunction

// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2008-2011 - INRIA - Michael Baudin
// Copyright (C) 2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt



function x = distfun_logninv ( varargin )
    // Lognormal Inverse CDF
    //
    // Calling Sequence
    //   x = distfun_logninv ( p , mu , sigma )
    //   x = distfun_logninv ( p , mu , sigma , lowertail )
    //
    // Parameters
    //   p : a matrix of doubles, the probability. Must be in the range [0,1].
    // mu: a matrix of doubles, the mean of the underlying normal variable.
    // sigma: a matrix of doubles, the variance of the underlying normal variable. sigma>0.
    // lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    // x: a matrix of doubles
    //
    // Description
    //   This function computes the Lognormal quantile.
    //
    //   Any scalar input argument is expanded to a matrix of doubles of the same size 
    //   as the other input arguments.
    //
    // Any optional input argument equal to the empty matrix will be set to its
    // default value.
    //
    // Examples
	// x = distfun_logninv ( 0.6 , 1. , 2. )
	// xexpected = 4.5117910634839439865
	//
    // // Check the inverse lognormale
    // mu = 1;
    // sigma=10;
    // x=2;
    // p = distfun_logncdf ( x , mu , sigma ); // 0.4877603
    // x = distfun_logninv ( p , mu , sigma ) // Must be 2
	//
	// // See upper tail: 21481242.111263956875
	// x = distfun_logninv ( 1.e-15 , 1. , 2. , %f )
    //
    // Authors
    // Copyright (C) 2008-2011 - INRIA - Michael Baudin
    // Copyright (C) 2011 - DIGITEO - Michael Baudin
    // Copyright (C) 2012 - Michael Baudin
    //
    // Bibliography
    // Dider Pelat, "Bases et méthodes pour le traitement de données", section 8.2.8, "Loi log-normale".
    // Wikipedia, Lognormal probability distribution function, http://en.wikipedia.org/wiki/File:Lognormal_distribution_PDF.png
    // Wikipedia, Lognormal cumulated distribution function, http://en.wikipedia.org/wiki/File:Lognormal_distribution_CDF.png

    [lhs, rhs] = argn()
    apifun_checkrhs ( "distfun_logninv" , rhs , 3:4 )
    apifun_checklhs ( "distfun_logninv" , lhs , 0:1 )
    //
    p = varargin(1)
    mu = varargin(2)
    sigma = varargin(3)
    lowertail = apifun_argindefault ( varargin , 4 , %t )
    //
    // Check type
    apifun_checktype ( "distfun_logninv" , p , "p" , 1 , "constant" )
    apifun_checktype ( "distfun_logninv" , mu , "mu" , 2 , "constant" )
    apifun_checktype ( "distfun_logninv" , sigma , "sigma" , 3 , "constant" )
    apifun_checktype ( "distfun_logninv" , lowertail , "lowertail" , 4 , "boolean" )
    //
    // Check size : OK
    //
    // Check content
    apifun_checkrange ( "distfun_logninv" , p , "p" , 1 , 0 , 1 )
    tiny = number_properties("tiny")
    apifun_checkgreq ( "distfun_logninv" , sigma , "sigma" , 3 , tiny )
    //
    // Proceed ...
    [ p , mu , sigma ] = apifun_expandvar ( p , mu , sigma )
    x=distfun_invlogn(p,mu,sigma,lowertail)
endfunction


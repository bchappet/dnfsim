// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2009 - 2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function p = distfun_normcdf ( varargin )
    //   Normal CDF
    //
    // Calling Sequence
    //   p=distfun_normcdf(x,mu,sigma)
    //   p=distfun_normcdf(x,mu,sigma,lowertail)
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
    //   of the Normal (Laplace-Gauss) function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Examples
    // // Test expanded arguments
    // computed = distfun_normcdf ( [1 2 3] , [1 1 1] , [2 2 2] )
    // expected = [
    // 	0.500000000000000 , ..
    //  0.691462461274013 , ..
    //  0.841344746068543
	// ]
    //
    // // Test argument expansion
    // computed = distfun_normcdf ( [1 2 3] , 1.0 , 2.0 )
    // expected = [
    // 0.500000000000000 , ..
    // 0.691462461274013 , ..
    // 0.841344746068543 
	// ]
    //
    // // Plot the function
    // mu = [0 0 0 -2];
    // sigma2 = [0.2 1.0 5.0 0.5];
    // cols = [1 2 3 4];
    // nf = size(cols,"*");
    // lgd = [];
    // scf();
    // for k = 1 : nf
    //   x = linspace(-5,5,1000);
    //   y = distfun_normcdf ( x , mu(k) , sqrt(sigma2(k)) );
    //   plot(x,y)
    //   str = msprintf("mu=%s, sigma^2=%s",..
    //       string(mu(k)),string(sigma2(k)));
    //   lgd($+1) = str;
    // end
    // h = gcf();
    // for k = 1 : nf
    //   hk = h.children.children.children(nf - k + 1);
    //   hk.foreground = cols(k);
    // end
    // xtitle("Normal CDF","x","$P(X\leq x)$");
    // legend(lgd);
    //
    //  // See upper tail
    //  p = distfun_normcdf ( 7, 4, 1 )
    //  q = distfun_normcdf ( 7, 4, 1 , %f )
    //  p+q
    //  // See an extreme case
    //  distfun_normcdf ( 15, 4, 1 , %f )
    //
    // Authors
    //   Copyright (C) 2009 - 2011 - DIGITEO - Michael Baudin
    //   Copyright (C) 2012 - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_normcdf" , rhs , 3:4 )
    apifun_checklhs ( "distfun_normcdf" , lhs , 0:1 )
    //
    x = varargin ( 1 )
    mu = varargin ( 2 )
    sigma = varargin ( 3 )
    lowertail = apifun_argindefault ( varargin , 4 , %t )
    //
    apifun_checktype ( "distfun_normcdf" , x , "x" , 1 , "constant" )
    apifun_checktype ( "distfun_normcdf" , mu , "mu" , 2 , "constant" )
    apifun_checktype ( "distfun_normcdf" , sigma , "sigma" , 3 , "constant" )
    apifun_checktype ( "distfun_normcdf" , lowertail , "lowertail" , 4 , "boolean" )
    //
    // Check size (let expandvar do the check for the other args)
    apifun_checkscalar ( "distfun_normcdf" , lowertail , "lowertail" , 4 )
    //
    // Check content
    tiny = number_properties("tiny")
    apifun_checkgreq ( "distfun_normcdf" , sigma , "sigma" , 3 , tiny )
    //
    [ x , mu , sigma ] = apifun_expandvar ( x , mu , sigma )
    p = distfun_cdfnorm( x, mu, sigma, lowertail )
endfunction


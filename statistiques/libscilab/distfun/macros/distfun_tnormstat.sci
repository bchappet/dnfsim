// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [M,V]=distfun_tnormstat(mu,sigma,a,b)
    // Truncated Normal  mean and variance
    //
    // Calling Sequence
    //   M=distfun_tnormstat(mu,sigma,a,b)
    //   [M,V]=distfun_tnormstat(mu,sigma,a,b)
    //
    // Parameters
    //   mu : a matrix of doubles, the average
    //   sigma : a matrix of doubles, the standard deviation. sigma>0.
    //   a : a matrix of doubles, the lower bound
    //   b : a matrix of doubles, the upper bound (with a<=b)
    //   M : a matrix of doubles, the mean
    //   V : a matrix of doubles, the variance
    //
    // Description
    //   Computes statistics from the Normal distribution.
    //
    // The mean and variance of the Normal distribution are
    //
    //<latex>
    //\begin{eqnarray}
    // M &=& \mu + \sigma \frac{\phi(\alpha)-\phi(\beta)}{Z}\\
    // V &=& \sigma^2 \left[1 + \frac{\alpha \phi(\alpha)-\beta\phi(\beta)}{Z} 
	// - \left(\frac{\phi(\alpha)-\phi(\beta)}{Z}\right)^2\right]
    //\end{eqnarray}
    //</latex>
    //
    //   Any scalar input argument is expanded to a matrix of 
    //   doubles of the same size as the other input arguments.
    //
    // Examples
    //
    // mu = 1:6;
    // sigma = 7:12;
	// a=-10;
	// b=10;
    // [M,V]=distfun_tnormstat(mu,sigma,a,b)
    // 
	// // Compare with random numbers
	// a=-10;
    // b=10;
    // N=1000;
    // //
    // mu=-8;
    // sigma=2;
    // [M,V]=distfun_tnormstat(mu,sigma,a,b);
    // x=distfun_tnormrnd(mu,sigma,a,b,N,1);
    // [M,mean(x)]
    // [V,variance(x)]
    // //
    // mu=0;
    // sigma=2;
    // [M,V]=distfun_tnormstat(mu,sigma,a,b);
    // x=distfun_tnormrnd(mu,sigma,a,b,N,1);
    // [M,mean(x)]
    // [V,variance(x)]
    // //
    // mu=9;
    // sigma=10;
    // [M,V]=distfun_tnormstat(mu,sigma,a,b);
    // x=distfun_tnormrnd(mu,sigma,a,b,N,1);
    // [M,mean(x)]
    // [V,variance(x)]
    // //
    // mu=0;
    // sigma=10;
    // [M,V]=distfun_tnormstat(mu,sigma,a,b);
    // x=distfun_tnormrnd(mu,sigma,a,b,N,1);
    // [M,mean(x)]
    // [V,variance(x)]
    //
    // Authors
    //   Copyright (C) 2013 - Michael Baudin
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Truncated_normal_distribution

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_tnormstat" , rhs , 4 )
    apifun_checklhs ( "distfun_tnormstat" , lhs , 0:2 )
    //
    // Check type
    apifun_checktype ( "distfun_tnormstat" , mu , "mu" , 1 , "constant" )
    apifun_checktype ( "distfun_tnormstat" , sigma , "sigma" , 2 , "constant" )
    apifun_checktype ( "distfun_tnormstat" , a , "a" , 3 , "constant" )
    apifun_checktype ( "distfun_tnormstat" , b , "b" , 4 , "constant" )
    //
    // Check content
    tiny = number_properties("tiny")
    apifun_checkgreq ( "distfun_tnormstat" , sigma , "sigma" , 2 , tiny )
    apifun_checkgreq( "distfun_tnormstat" , b , "b" , 3 , a )
    //
    [mu,sigma,a,b]=apifun_expandvar(mu,sigma,a,b)
    //
    al=(a-mu)/sigma
    be=(b-mu)/sigma
    yal=distfun_normpdf(al,0,1)
    ybe=distfun_normpdf(be,0,1)
    pal=distfun_normcdf(al,0,1)
    pbe=distfun_normcdf(be,0,1)
    z=pbe-pal
    M=mu+(yal-ybe)*sigma/z
    s=(al*yal-be*ybe)/z
    t=(yal-ybe)/z
    V=sigma^2*(1+s-t^2)
endfunction


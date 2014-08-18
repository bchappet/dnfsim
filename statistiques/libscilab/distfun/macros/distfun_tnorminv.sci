// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_tnorminv ( varargin )
    // Truncated Normal Inverse CDF
    //
    // Calling Sequence
    //   x=distfun_tnorminv(p,mu,sigma,a,b)
    //   x=distfun_tnorminv(p,mu,sigma,a,b,lowertail)
    //
    // Parameters
    //   p : a matrix of doubles, the probability. Must be in the range [0,1].
    //   mu : a matrix of doubles, the mean.
    //   sigma : a matrix of doubles, the standard deviation. sigma>0.
    //   a : a matrix of doubles, the lower bound
    //   b : a matrix of doubles, the upper bound (with a<=b)
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   x : a matrix of doubles, the outcome
    //
    // Description
    //   Computes the inverse Normal cumulated probability distribution function of the Normal (Laplace-Gauss) function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles of the same size as the other input arguments.
    //
    // Examples
    // p=[0.01 0.5 0.9 0.99 0.7]'
    // distfun_tnorminv (p,0,1,-1,1)
    // expected = [
    // -0.9721734
    // 0.
    // 0.7490146
    // 0.9721734
    // 0.3492199
    // ]
    //
    // // Plot accuracy
    // // See how the accuracy decreases when 
    // // we invert the upper tail:
    // // in the upper left figure, the number of 
    // // significant digits goes down from 16 to 0.
    // // This is what the lowertail=%f option is for.
    // a=-10;
    // b=10;
    // //
    // mu=-8;
    // sigma=2;
    // x=linspace(a,b,1000);
    // p1=distfun_tnormcdf(x,mu,sigma,a,b);
    // x1=distfun_tnorminv(p1,mu,sigma,a,b);
    // d1=assert_computedigits(x,x1);
    // //
    // mu=0;
    // sigma=2;
    // x=linspace(a,b,1000);
    // p2=distfun_tnormcdf(x,mu,sigma,a,b);
    // x2=distfun_tnorminv(p2,mu,sigma,a,b);
    // d2=assert_computedigits(x,x2);
    // //
    // mu=9;
    // sigma=10;
    // x=linspace(a,b,1000);
    // p3=distfun_tnormcdf(x,mu,sigma,a,b);
    // x3=distfun_tnorminv(p3,mu,sigma,a,b);
    // d3=assert_computedigits(x,x3);
    // //
    // mu=0;
    // sigma=10;
    // x=linspace(a,b,1000);
    // p4=distfun_tnormcdf(x,mu,sigma,a,b);
    // x4=distfun_tnorminv(p4,mu,sigma,a,b);
    // d4=assert_computedigits(x,x4);
    // //
    // scf();
    // subplot(2,2,1)
    // plot(x,d1)
    // sshared="$\textrm{Inv. Trunc. normal [-10,10], }";
    // s=sshared+"\mu=-8,\sigma=2$";
    // xtitle(s,"X","Digits");
    // subplot(2,2,2)
    // plot(x,d2)
    // s=sshared+"\mu=0,\sigma=2$";
    // xtitle(s,"X","Digits");
    // subplot(2,2,3)
    // plot(x,d3)
    // s=sshared+"\mu=9,\sigma=10$";
    // xtitle(s,"X","Digits");
    // subplot(2,2,4)
    // plot(x,d4)
    // s=sshared+"\mu=0,\sigma=10$";
    // xtitle(s,"X","Digits");
    //
    // Authors
    //   Copyright (C) 2013 - Michael Baudin
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Truncated_normal_distribution

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_tnorminv" , rhs , 5:6 )
    apifun_checklhs ( "distfun_tnorminv" , lhs , 0:1 )
    //
    p = varargin ( 1 )
    mu = varargin (2)
    sigma = varargin (3)
    a = varargin (4)
    b = varargin (5)
    lowertail = apifun_argindefault ( varargin , 6 , %t )
    //
    // Check type
    apifun_checktype ( "distfun_tnorminv" , p , "p" , 1 , "constant" )
    apifun_checktype ( "distfun_tnorminv" , mu , "mu" , 2 , "constant" )
    apifun_checktype ( "distfun_tnorminv" , sigma , "sigma" , 3 , "constant" )
    apifun_checktype ( "distfun_tnorminv" , a , "a" , 4 , "constant" )
    apifun_checktype ( "distfun_tnorminv" , b , "b" , 5 , "constant" )
    apifun_checktype ( "distfun_tnorminv" , lowertail , "lowertail" , 6 , "boolean" )
    //
    // Check size (let expandvar do the check for the other args)
    apifun_checkscalar ( "distfun_tnorminv" , lowertail , "lowertail" , 6 )
    //
    // Check content
    apifun_checkrange( "distfun_tnorminv" , p , "p" , 1 , 0, 1 )
    tiny = number_properties("tiny")
    apifun_checkgreq ( "distfun_tnorminv" , sigma , "sigma" , 3 , tiny )
    //
    [ p,mu,sigma,a,b] = apifun_expandvar ( p,mu,sigma,a,b)
    apifun_checkgreq( "distfun_tnorminv" , b , "b" , 5 , a )
    //
    al=(a-mu)./sigma
    be=(b-mu)./sigma
    pal=distfun_normcdf(al,0,1)
    pbe=distfun_normcdf(be,0,1)
    z=pbe-pal
    if (lowertail) then
        xi=distfun_norminv(pal+z.*p,0,1)
    else
        xi=distfun_norminv(pbe-z.*p,0,1)
    end
    x=mu+sigma.*xi
endfunction


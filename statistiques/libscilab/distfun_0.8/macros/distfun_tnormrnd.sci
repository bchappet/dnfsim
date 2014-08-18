// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x=distfun_tnormrnd ( varargin )
    // Truncated Normal random numbers
    //
    // Calling Sequence
    //   x=distfun_tnormrnd(mu,sigma,a,b)
    //   x=distfun_tnormrnd(mu,sigma,a,b,[m,n])
    //   x=distfun_tnormrnd(mu,sigma,a,b,m,n)
    //
    // Parameters
    //   mu : a matrix of doubles, the average
    //   sigma : a matrix of doubles, the standard deviation. sigma>0.
    //   a : a matrix of doubles, the lower bound
    //   b : a matrix of doubles, the upper bound (with a<=b)
    //   m : a 1-by-1 matrix of floating point integers, the number of rows of x
    //   n : a 1-by-1 matrix of floating point integers, the number of columns of x
    //   x: a matrix of doubles, the random numbers.
    //
    // Description
    //   Generates random variables from the Normal distribution function.
    //
    //   Any scalar input argument is expanded to a matrix of doubles of 
    //   the same size as the other input arguments.
    // 
    // Examples
    // x=distfun_tnormrnd(3,5,2,4)
    //
    // // Test sigma expansion
    // x=distfun_tnormrnd(3,5:7,2,4)
    //
    // // Test mu expansion
    // x=distfun_tnormrnd(3:6,5,2,4)
    //
    // // Test with v
    // x=distfun_tnormrnd(3,5,2,4,[3 2])
    //
    // // Test with m, n
    // x=distfun_tnormrnd(3,5,2,4,3,2)
    //
    // // Make a plot of the actual distribution of the numbers
    // a=-10;
    // b=10;
    // N=1000;
    // x=linspace(a,b,1000);
    // y1=distfun_tnormpdf(x,-8,2,a,b);
    // R1=distfun_tnormrnd(-8,2,a,b,N,1);
    // y2=distfun_tnormpdf(x,0,2,a,b);
    // R2=distfun_tnormrnd(0,2,a,b,N,1);
    // y3=distfun_tnormpdf(x,9,10,a,b);
    // R3=distfun_tnormrnd(9,10,a,b,N,1);
    // y4=distfun_tnormpdf(x,0,10,a,b);
    // R4=distfun_tnormrnd(0,10,a,b,N,1);
    // scf();
    // subplot(2,2,1)
    // histplot(20,R1);
    // plot(x,y1);
    // s="$\textrm{Trunc. normal [-10,10], }\mu=-8,\sigma=2$";
    // xtitle(s,"X","Frequency");
    // legend(["Data","PDF"]);
    // //
    // subplot(2,2,2)
    // histplot(20,R2);
    // plot(x,y2);
    // s="$\textrm{Trunc. normal [-10,10], }\mu=0,\sigma=2$";
    // xtitle(s,"X","Frequency");
    // legend(["Data","PDF"]);
    // //
    // subplot(2,2,3)
    // histplot(20,R3);
    // plot(x,y3);
    // s="$\textrm{Trunc. normal [-10,10], }\mu=9,\sigma=10$";
    // xtitle(s,"X","Frequency");
    // legend(["Data","PDF"]);
    // //
    // subplot(2,2,4)
    // histplot(20,R4);
    // plot(x,y4);
    // s="$\textrm{Trunc. normal [-10,10], }\mu=0,\sigma=10$";
    // xtitle(s,"X","Frequency");
    // legend(["Data","PDF"]);
    //
    // Authors
    //   Copyright (C) 2013 - Michael Baudin
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Truncated_normal_distribution

    // Load Internals lib
    path = distfun_getpath (  )
    internallib  = lib(fullfile(path,"macros","internals"))

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_tnormrnd" , rhs , 4:6 )
    apifun_checklhs ( "distfun_tnormrnd" , lhs , 0:1 )
    //
    mu = varargin(1)
    sigma = varargin(2)
    a = varargin(3)
    b = varargin(4)
    //
    // Check type
    apifun_checktype ( "distfun_tnormrnd" , mu , "mu" , 1 , "constant" )
    apifun_checktype ( "distfun_tnormrnd" , sigma , "sigma" , 2 , "constant" )
    apifun_checktype ( "distfun_tnormrnd" , a , "a" , 3 , "constant" )
    apifun_checktype ( "distfun_tnormrnd" , b , "b" , 4 , "constant" )
    if ( rhs == 5 ) then
        v = varargin(5)
    end
    if ( rhs == 6 ) then
        m = varargin(5)
        n = varargin(6)
    end
    //
    // Check size : OK
    //
    // Check content
    tiny = number_properties("tiny")
    apifun_checkgreq ( "distfun_tnormrnd" , sigma , "sigma" , 2 , tiny )
    //
    // Check v, m, n
    distfun_checkvmn ( "distfun_tnormrnd" , 5 , varargin(5:$) )
    //
    [mu,sigma,a,b] = apifun_expandvar(mu,sigma,a,b)
    apifun_checkgreq( "distfun_tnormrnd" , b , "b" , 3 , a )
    //
    mu = apifun_expandfromsize(1,mu,varargin(5:$))
    sigma = apifun_expandfromsize(1,sigma,varargin(5:$))
    a = apifun_expandfromsize(1,a,varargin(5:$))
    b = apifun_expandfromsize(1,b,varargin(5:$))
    m = size(mu,"r")
    n = size(mu,"c")
    //
    u=distfun_unifrnd(0,1,m,n)
    x=distfun_tnorminv(u,mu,sigma,a,b)
endfunction


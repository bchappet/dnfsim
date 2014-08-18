// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function p = distfun_tnormcdf ( varargin )
    //  Truncated Normal CDF
    //
    // Calling Sequence
    //   p=distfun_tnormcdf(x,mu,sigma,a,b)
    //   p=distfun_tnormcdf(x,mu,sigma,a,b,lowertail)
    //
    // Parameters
    //   x : a matrix of doubles, the outcome
    //   mu : a matrix of doubles, the mean
    //   sigma : a matrix of doubles, the standard deviation. sigma>0.
    //   a : a matrix of doubles, the lower bound
    //   b : a matrix of doubles, the upper bound (with a<=b)
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
    // p=distfun_tnormcdf([-2 1.5 3],[1 1 1],[2 2 2],0,2)
    // expected = [0. 0.7577694 1.]
    //
    // // Test argument expansion
    // computed=distfun_tnormcdf([-3 -1 1 2 3],1.,2.,-1,2)
    // expected=[0. 0. 0.6406534 1. 1.]
    //
    // // Plot the function
    // a=-10;
    // b=10;
    // x=linspace(a,b,1000);
    // p1=distfun_tnormcdf(x,-8,2,a,b);
    // p2=distfun_tnormcdf(x,0,2,a,b);
    // p3=distfun_tnormcdf(x,9,10,a,b);
    // p4=distfun_tnormcdf(x,0,10,a,b);
    // scf();
    // plot(x,p1,"k-")
    // plot(x,p2,"b-")
    // plot(x,p3,"r-")
    // plot(x,p4,"g-")
    // xtitle("Truncated normal [-10,10]","X","CDF");
    // legend(["$\mu=-8,\sigma=2$","$\mu=0,\sigma=2$",..
    // "$\mu=9,\sigma=10$","$\mu=0,\sigma=10$"]);
    //
    // Authors
    //   Copyright (C) 2013 - Michael Baudin
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Truncated_normal_distribution

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_tnormcdf" , rhs , 5:6 )
    apifun_checklhs ( "distfun_tnormcdf" , lhs , 0:1 )
    //
    x = varargin ( 1 )
    mu = varargin ( 2 )
    sigma = varargin ( 3 )
    a = varargin ( 4 )
    b = varargin ( 5 )
    lowertail = apifun_argindefault ( varargin , 6 , %t )
    //
    apifun_checktype ( "distfun_tnormcdf" , x , "x" , 1 , "constant" )
    apifun_checktype ( "distfun_tnormcdf" , mu , "mu" , 2 , "constant" )
    apifun_checktype ( "distfun_tnormcdf" , sigma , "sigma" , 3 , "constant" )
    apifun_checktype ( "distfun_tnormcdf" , a , "a" , 4 , "constant" )
    apifun_checktype ( "distfun_tnormcdf" , b , "b" , 5 , "constant" )
    apifun_checktype ( "distfun_tnormcdf" , lowertail , "lowertail" , 6 , "boolean" )
    //
    // Check size (let expandvar do the check for the other args)
    apifun_checkscalar ( "distfun_tnormcdf" , lowertail , "lowertail" , 4 )
    //
    // Check content
    tiny = number_properties("tiny")
    apifun_checkgreq ( "distfun_tnormcdf" , sigma , "sigma" , 3 , tiny )
    apifun_checkgreq( "distfun_tnormstat" , b , "b" , 3 , a )
    //
    [x,mu,sigma,a,b]=apifun_expandvar(x,mu,sigma,a,b)
    //
    p=zeros(x)
    i=find(x>=a & x<=b)
    xi(i)=(x(i)-mu(i))./sigma(i)
    al(i)=(a(i)-mu(i))./sigma(i)
    be(i)=(b(i)-mu(i))./sigma(i)
    pxi(i)=distfun_normcdf(xi(i),0,1)
    pal(i)=distfun_normcdf(al(i),0,1)
    pbe(i)=distfun_normcdf(be(i),0,1)
    z(i)=pbe(i)-pal(i)
    if (lowertail) then
        p(i)=(pxi(i)-pal(i))./z(i)
        p(x>b)=1
    else
        p(x<a)=1
        p(i)=(pbe(i)-pxi(i))./z(i)
        p(x>b)=0
    end

endfunction


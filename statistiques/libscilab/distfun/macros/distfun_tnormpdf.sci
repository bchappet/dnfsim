// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function y=distfun_tnormpdf(varargin )
    // Truncated Normal PDF
    //
    // Calling Sequence
    //   y=distfun_tnormpdf(x,mu,sigma,a,b)
    //
    // Parameters
    //   x : a matrix of doubles, the outcome
    //   mu : a matrix of doubles, the mean
    //   sigma : a matrix of doubles, the standard deviation. sigma>0.
    //   a : a matrix of doubles, the lower bound
    //   b : a matrix of doubles, the upper bound (with a<=b)
    //   y : a matrix of doubles, the density
    //
    // Description
    //   Computes the probability distribution function of the Normal (Laplace-Gauss) function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles of the same size as the other input arguments.
    //
    //   The function definition is:
    //
    //<latex>
    //\begin{eqnarray}
    //f(x,\mu,\sigma,a,b) = \frac{\phi(\xi)}{\sigma Z}
    //\end{eqnarray}
    //</latex>
    // 
    // if x is in the [a,b] interval and zero otherwise, 
    // where
    //
    //<latex>
    //\begin{eqnarray}
    //\xi&=&\frac{x-\mu}{\sigma}\\
    //Z&=&\Phi(b)-\Phi(a)
    //\end{eqnarray}
    //</latex>
    //
    // and <latex>$\Phi(x)$</latex> is the standard normal cumulated distribution function (i.e. CDF), 
    // and <latex>$\phi(x)$</latex> is the standard normal probability distribution function (i.e. PDF).
    //
    // Examples
    //   y=distfun_tnormpdf([-2 -1 0 1],0,1,-1,1)
    //   expected = [ 0.241970724519143   0.241970724519143 ];
    //
    // // Plot the function
    // a=-10;
    // b=10;
    // x=linspace(a,b,1000);
    // y1=distfun_tnormpdf(x,-8,2,a,b);
    // y2=distfun_tnormpdf(x,0,2,a,b);
    // y3=distfun_tnormpdf(x,9,10,a,b);
    // y4=distfun_tnormpdf(x,0,10,a,b);
    // scf();
    // plot(x,y1,"k-")
    // plot(x,y2,"b-")
    // plot(x,y3,"r-")
    // plot(x,y4,"g-")
    // xtitle("Truncated normal [-10,10]","X","PDF");
    // legend(["$\mu=-8,\sigma=2$","$\mu=0,\sigma=2$",..
    // "$\mu=9,\sigma=10$","$\mu=0,\sigma=10$"]);
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Truncated_normal_distribution
    //
    // Authors
    //   Copyright (C) 2013 - Michael Baudin


    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_tnormpdf" , rhs , 5 )
    apifun_checklhs ( "distfun_tnormpdf" , lhs , 0:1 )
    //
    x = varargin ( 1 )
    mu = varargin (2)
    sigma = varargin (3)
    a = varargin (4)
    b = varargin (5)
    //
    // Check Type
    apifun_checktype ( "distfun_tnormpdf" , x , "x" , 1 , "constant" )
    apifun_checktype ( "distfun_tnormpdf" , mu , "mu" , 2 , "constant" )
    apifun_checktype ( "distfun_tnormpdf" , sigma , "sigma" , 3 , "constant" )
    apifun_checktype ( "distfun_tnormpdf" , a , "a" , 4 , "constant" )
    apifun_checktype ( "distfun_tnormpdf" , b , "b" , 5 , "constant" )
    //
    // Check Size
    // Nothing to to
    //
    // Check Content
    tiny = number_properties("tiny")
    apifun_checkgreq ( "distfun_tnormpdf" , sigma , "sigma" , 3 , tiny )
    //
    [ x,mu,sigma,a,b] = apifun_expandvar(x,mu,sigma,a,b)
    apifun_checkgreq( "distfun_tnormpdf" , b , "b" , 5 , a )
    //
    if (x==[]) then
        y=[]
        return
    end
    //
    y=zeros(x)
    i=find(x>=a & x<=b)
    xi(i)=(x(i)-mu(i))./sigma(i)
    al(i)=(a(i)-mu(i))./sigma(i)
    be(i)=(b(i)-mu(i))./sigma(i)
    yxi(i)=distfun_normpdf(xi(i),0,1)
    pal(i)=distfun_normcdf(al(i),0,1)
    pbe(i)=distfun_normcdf(be(i),0,1)
    z(i)=pbe(i)-pal(i)
    y(i)=yxi(i)./sigma(i)./z(i)
endfunction


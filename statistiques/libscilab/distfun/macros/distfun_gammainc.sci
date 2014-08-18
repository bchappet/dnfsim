// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function y = distfun_gammainc ( varargin )
    // Regularized incomplete Gamma function
    //
    // Calling Sequence
    //   y = distfun_gammainc ( x , a )
    //   y = distfun_gammainc ( x , a , lowertail )
    //
    // Parameters
    //   x : a matrix of doubles, the upper limit of integration of the gamma density, x>=0.
    //   a : a matrix of doubles, the parameter. a>0
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then computes the integral from 0 to x otherwise from x to infinity.
    //   y : a matrix of doubles, the incomplete gamma function.
    //
    // Description
    // Returns the regularized incomplete gamma function.
    // If lowertail=%t, the function definition is:
    //
    // <latex>
    // \begin{eqnarray}
    // P(x,a) = \frac{1}{\Gamma(a)}\int_0^x e^{-t} t^{a-1} dt
    // \end{eqnarray}
    // </latex>
    //
    // If lowertail=%f, the function definition is:
    //
    // <latex>
    // \begin{eqnarray}
    // Q(x,a) = \frac{1}{\Gamma(a)}\int_x^\infty  e^{-t} t^{a-1} dt
    // \end{eqnarray}
    // </latex>
    //
    // We have
    //
    // <latex>
    // \begin{eqnarray}
    // P(x,a) + Q(x,a) = 1
    // \end{eqnarray}
    // </latex>
    //
    // The lowertail option may be used to overcome the 
    // limitations of floating point arithmetic. 
    // We may use lowertail=%f when the output of the gamminc 
    // function with lowertail=%t is very close to 1. 
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Notes
    //
    // The function P(x,a) is the cumulative distribution function 
    // of a Gamma random variable with shape parameter a and 
    // scale parameter 1.
    //
    // If a>0 is an integer, then Q(x,a) is the cumulative 
    // distribution function of a Poisson random variable 
    // with mean equal to a.
    //
    // When a→0, lim P(x,a)=1, and lim Q(x,a)=0
    //
    // Examples
    // distfun_gammainc(1,2) // Expected : 0.264241117657115
    // distfun_gammainc(2,3) // Expected : 0.323323583816936
    // distfun_gammainc(2,3,%t) // Expected : 0.323323583816936
    // // We have distfun_gammainc(x,a,%t) == 1 - distfun_gammainc(x,a,%f)
    // distfun_gammainc(2,3,%f) // Expected : 0.676676416183064
    //
    // // The following example shows how to use the tail argument.
    // // For a=1 and x>40, the result is so close to 1 that the 
    // // result is represented by the floating point number y=1.
    // distfun_gammainc(40,1) // Expected : 1
    // // This is why we may compute the complementary probability with
    // // the tail option.
    // distfun_gammainc(40,1,%f) // Expected : 4.248354255291594e-018
    //
    // // Show the expansion of a
    //  x = [1 2 3;4 5 6];
    //  a = 2;
    //  distfun_gammainc(x,a)
    //
    // 
    // // Plot the function
    // a = [1 2 3 5 9];
    // cols = [1 2 3 4 5];
    // nf = size(cols,"*");
    // lgd = [];
    // scf();
    // for k = 1 : nf
    //     x = linspace(0,20,1000);
    //     y = distfun_gammainc ( x , a(k) );
    //     plot(x,y)
    //     str = msprintf("a=%s",string(a(k)));
    //     lgd($+1) = str;
    // end
    // h = gcf();
    // for k = 1 : nf
    //     hk = h.children.children.children(nf - k + 1);
    //     hk.foreground = cols(k);
    // end
    // xtitle("Regularized Incomplete Gamma","x","P(x,a)")
    // legend(lgd);
    //
    // Authors
    // Copyright (C) 2014 - Michael Baudin
    //

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_gammainc" , rhs , 2:3 )
    apifun_checklhs ( "distfun_gammainc" , lhs , 0:1 )
    //
    x = varargin(1)
    a = varargin(2)
    lowertail = apifun_argindefault ( varargin , 3 , %t )
    //
    // Check type
    apifun_checktype ( "distfun_gammainc" , x , "x" , 1 , "constant" )
    apifun_checktype ( "distfun_gammainc" , a , "a" , 2 , "constant" )
    apifun_checktype ( "distfun_gammainc" , lowertail , "lowertail" , 3 , "boolean" )
    //
    // Check size (let expandvar do the check for the other args)
    apifun_checkscalar ( "distfun_gammainc" , lowertail , "lowertail" , 3 )
    //
    // Check content
    apifun_checkgreq( "distfun_gammainc" , x , "x" , 1 , 0 )
    tiny=number_properties("tiny")
    apifun_checkgreq( "distfun_gammainc" , a , "a" , 2 , tiny )
    //
    [ x , a ] = apifun_expandvar ( x , a )
    //
    y = distfun_incgamma ( x , a , lowertail )

endfunction

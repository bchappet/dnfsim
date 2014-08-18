// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function p = distfun_wblcdf ( varargin )
    // Weibull CDF
    //
    // Calling Sequence
    //   p = distfun_wblcdf ( x , a , b )
    //   p = distfun_wblcdf ( x , a , b , lowertail )
    //
    // Parameters
    //   x : a matrix of doubles, the outcome, x>=0
    //   a : a matrix of doubles, the scale parameter, a>0.
    //   b : a matrix of doubles, the shape parameter, b>0.
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   p : a matrix of doubles, the probability.
    //
    // Description
    //   Computes the Weibull cumulated distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles of the same size as the other input arguments.
    //
    // Examples
    // // Check expansion of a and b
    // computed = distfun_wblcdf ( 0.1:0.2:0.7 , 2 , 2 )
    // expected = [0.0024969  0.0222488  0.0605869  0.1152941]
    //
    // // Check bounds of x
    // // This generates an error:
    // // distfun_wblcdf([-1 0 1 2],2,3)
    //
    // // Plot the function
    // a = 1;
    // b = [0.5 1 1.5 5];
    // cols = [1 2 3 5];
    // nf = size(cols,"*");
    // lgd = [];
    // scf();
    // for k = 1 : nf
    //     x = linspace(0,2.5,1000);
    //     y = distfun_wblcdf(x,a,b(k));
    //     plot(x,y)
    //     str = msprintf("a=%s, b=%s",..
    //     string(a),string(b(k)));
    //     lgd($+1) = str;
    // end
    // h = gcf();
    // for k = 1 : nf
    //     hk = h.children.children.children(nf - k + 1);
    //     hk.foreground = cols(k);
    // end
    // xtitle("Weibull CDF","x","$P(X\leq x)$");
    // legend(lgd);
    //
    // Authors
    //   Copyright (C) 2014 - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_wblcdf" , rhs , 3:4 )
    apifun_checklhs ( "distfun_wblcdf" , lhs , 0:1 )
    //
    x = varargin(1)
    a = varargin(2)
    b = varargin(3)
    lowertail = apifun_argindefault ( varargin , 4 , %t )
    //
    // Check type
    apifun_checktype ( "distfun_wblcdf" , x , "x" , 1 , "constant" )
    apifun_checktype ( "distfun_wblcdf" , a , "a" , 2 , "constant" )
    apifun_checktype ( "distfun_wblcdf" , b , "b" , 3 , "constant" )
    apifun_checktype ( "distfun_wblcdf" , lowertail , "lowertail" , 4 , "boolean" )
    //
    // Check size (let expandvar do the check for the other args)
    apifun_checkscalar ( "distfun_wblcdf" , lowertail , "lowertail" , 4 )
    //
    // Check content
    apifun_checkgreq ( "distfun_wblcdf" , x , "x" , 1 , 0 )
    tiny=number_properties("tiny")
    apifun_checkgreq ( "distfun_wblcdf" , a , "a" , 2 , tiny )
    apifun_checkgreq ( "distfun_wblcdf" , b , "b" , 3 , tiny )  
    //
    [ x , a , b ] = apifun_expandvar ( x , a , b )
    //
    p = distfun_cdfwbl ( x , a , b , lowertail )
endfunction


// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function p = distfun_gamcdf ( varargin )
    // Gamma CDF
    //
    // Calling Sequence
    //   p = distfun_gamcdf ( x , a , b )
    //   p = distfun_gamcdf ( x , a , b , lowertail )
    //
    // Parameters
    //   x : a matrix of doubles, the outcome, x>=0
    //   a : a matrix of doubles, the shape parameter, a>0.
    //   b : a matrix of doubles, the scale parameter, b>0.
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   p : a matrix of doubles, the probability
    //
    // Description
    //   Computes the Gamma cumulated probability distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Examples
    // // Test x scalar, a scalar, b expanded
    // b = 1:5;
    // computed = distfun_gamcdf(1,1,b)
    // expected = [  ..
    //   6.321205588285576660D-01  ..
    //   3.934693402873664647D-01  ..
    //   2.834686894262107293D-01  ..
    //   2.211992169285951215D-01  ..
    //   1.812692469220181790D-01  ..
    // ]
    //
    // // Plot the function
    // shape = [1 2 3 5 9];
    // scale = [2 2 2 1 0.5];
    // cols = [1 2 3 4 5];
    // nf = size(cols,"*");
    // lgd = [];
    // scf();
    // for k = 1 : nf
    //   x = linspace(0,20,1000);
    //   y = distfun_gamcdf ( x , shape(k) , scale(k) );
    //   plot(x,y)
    //   str = msprintf("shape=%s, scale=%s",..
    //       string(shape(k)),string(scale(k)));
    //   lgd($+1) = str;
    // end
    // h = gcf();
    // for k = 1 : nf
    //   hk = h.children.children.children(nf - k + 1);
    //   hk.foreground = cols(k);
    // end
    // xtitle("Gamma CDF","x","$P(X\leq x)$")
    // legend(lgd);
    //
    // // See upper tail
    // p = distfun_gamcdf(1,3,5)
    // q = distfun_gamcdf(1,3,5,%f)
    // p+q
    // // See an extreme case
    // p = distfun_gamcdf(300,3,5,%f)
    //
    // Authors
    //   Copyright (C) 2011 - DIGITEO - Michael Baudin
    //   Copyright (C) 2012 - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_gamcdf" , rhs , 3:4 )
    apifun_checklhs ( "distfun_gamcdf" , lhs , 0:1 )
    //
    x = varargin(1)
    a = varargin(2)
    b = varargin(3)
    lowertail = apifun_argindefault ( varargin , 4 , %t )
    //
    // Check type
    apifun_checktype ( "distfun_gamcdf" , x , "x" , 1 , "constant" )
    apifun_checktype ( "distfun_gamcdf" , a , "a" , 2 , "constant" )
    apifun_checktype ( "distfun_gamcdf" , b , "b" , 3 , "constant" )
    apifun_checktype ( "distfun_gamcdf" , lowertail , "lowertail" , 4 , "boolean" )
    //
    // Check size (let expandvar do the check for the other args)
    apifun_checkscalar ( "distfun_gamcdf" , lowertail , "lowertail" , 4 )
    //
    // Check content
    apifun_checkgreq( "distfun_gamcdf" , x , "x" , 1 , 0 )
	tiny=number_properties("tiny")
    apifun_checkgreq( "distfun_gamcdf" , a , "a" , 2 , tiny )
    apifun_checkgreq( "distfun_gamcdf" , b , "b" , 3 , tiny )
    //
    [ x , a , b ] = apifun_expandvar ( x , a , b )
    //
    p = distfun_cdfgam ( x , a , b , lowertail )
endfunction


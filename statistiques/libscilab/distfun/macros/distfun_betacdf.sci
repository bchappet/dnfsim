// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2009-2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function p = distfun_betacdf ( varargin )
    // Beta CDF
    //
    // Calling Sequence
    //   p = distfun_betacdf ( x , a , b )
    //   p = distfun_betacdf ( x , a , b , lowertail )
    //
    // Parameters
    //   x : a matrix of doubles, the outcome. Should be in the [0,1] interval. If not, an error is generated.
    //   a : a matrix of doubles, the first shape parameter, a>=0.
    //   b : a matrix of doubles, the second shape parameter, b>=0.
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   p : a matrix of doubles, the probability.
    //
    // Description
    //   Computes the Beta cumulated distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles of the same size as the other input arguments.
    //
    // Examples
    // // Check expansion of a and b
    // computed = distfun_betacdf ( 0.1:0.2:0.9 , 2 , 2 )
    // expected = [0.0280,0.2160,0.5000,0.7840,0.9720]
    //
    // // Check with expanded arguments
    // x = 0.1:0.2:0.9;
    // a = [2 3 4 5 6];
    // b = [5 4 3 2 1];
    // computed = distfun_betacdf ( x , a, b )
    // expected = [0.114265,0.25569,0.34375,0.420175,0.531441]
    //
    // // Check bounds of x
	// // This generates an error:
    // // distfun_betacdf([-1 0 1 2],2,3)
    //
    // // Plot the function
    // a = [0.5 5 1 2 2];
    // b = [0.5 1 3 2 5];
    // cols = [1 2 3 4 5];
    // nf = size(cols,"*");
    // lgd = [];
    // scf();
    // for k = 1 : nf
    //   x = linspace(0,1,1000);
    //   y = distfun_betacdf ( x , a(k) , b(k) );
    //   plot(x,y)
    //   str = msprintf("a=%s, b=%s",..
    //       string(a(k)),string(b(k)));
    //   lgd($+1) = str;
    // end
    // h = gcf();
    // for k = 1 : nf
    //   hk = h.children.children.children(nf - k + 1);
    //   hk.foreground = cols(k);
    // end
    // xtitle("Beta CDF","x","$P(X\leq x)$");
    // legend(lgd);
    //
    // // See upper tail
    // p = distfun_betacdf ( 0.1:0.2:0.9 , 2 , 2 )
    // q = distfun_betacdf ( 0.1:0.2:0.9 , 2 , 2 , %f )
    // p+q
    // // See an extreme case
    // distfun_betacdf ( 0.999 , 1 , 10 , %f )
    // expected = 1.e-30
    //
    // Authors
    //   Copyright (C) 2012 - Michael Baudin
    //   Copyright (C) 2009-2011 - DIGITEO - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_betacdf" , rhs , 3:4 )
    apifun_checklhs ( "distfun_betacdf" , lhs , 0:1 )
    //
    x = varargin(1)
    a = varargin(2)
    b = varargin(3)
    lowertail = apifun_argindefault ( varargin , 4 , %t )
    //
    // Check type
    apifun_checktype ( "distfun_betacdf" , x , "x" , 1 , "constant" )
    apifun_checktype ( "distfun_betacdf" , a , "a" , 2 , "constant" )
    apifun_checktype ( "distfun_betacdf" , b , "b" , 3 , "constant" )
    apifun_checktype ( "distfun_betacdf" , lowertail , "lowertail" , 4 , "boolean" )
    //
    // Check size (let expandvar do the check for the other args)
    apifun_checkscalar ( "distfun_betacdf" , lowertail , "lowertail" , 4 )
    //
    // Check content
    apifun_checkrange ( "distfun_betacdf" , x , "x" , 1 , 0 , 1 )
    apifun_checkgreq ( "distfun_betacdf" , a , "a" , 2 , 0 )
    apifun_checkgreq ( "distfun_betacdf" , b , "b" , 3 , 0 )  
    //
    [ x , a , b ] = apifun_expandvar ( x , a , b )
    //
    p = distfun_cdfbeta ( x , a , b , lowertail )
endfunction


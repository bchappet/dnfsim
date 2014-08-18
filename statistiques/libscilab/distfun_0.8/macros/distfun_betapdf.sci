// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2009-2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function y = distfun_betapdf ( x , a , b )
    // Beta PDF
    //
    // Calling Sequence
    //   y = distfun_betapdf ( x , a , b )
    //
    // Parameters
    //   x : a matrix of doubles, the outcome. Should be in the [0,1] interval.
    //   a : a matrix of doubles, the first shape parameter, a>=0.
    //   b : a matrix of doubles, the second shape parameter, b>=0.
    //   y : a matrix of doubles, the density
    //
    // Description
    //   Computes the Beta probability distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles of the same size as the other input arguments.
    //
    // Let us denote by B the Beta function. The function definition is:
    //
    //<latex>
    //\begin{eqnarray}
    //f(x,a,b) = \frac{1}{B(a,b)} x^{a-1} (1-x)^{b-1}
    //\end{eqnarray}
    //</latex>
    //
    // if x is in [0,1]. 
    //
    // Examples
    // // Check with a and b to be expanded
    // computed = distfun_betapdf ( 0.1:0.2:0.9 , 2 , 3 )
    // expected = [0.9720,1.7640,1.5000,0.7560,0.1080]
    // // Check with expanded arguments
    // x = 0.1:0.2:0.9;
    // a = [2 3 4 5 6];
    // b = [5 4 3 2 1];
    // computed = distfun_betapdf ( x , a, b )
    // expected = [1.9683,1.8522,1.8750,2.1609,3.54294]
    // // Check at the bounds
    // computed = distfun_betapdf ( [-1 0 1 2], 1 , 2 ) 
    // expected = [0 0 0 0]
    //
    // // Plot the function
    // a = [0.5 5 1 2 2];
    // b = [0.5 1 3 2 5];
    // cols = [1 2 3 4 5];
    // nf = size(cols,"*");
    // lgd = [];
    // scf();
    // for k = 1 : nf
    //   x = linspace(0.01,0.99,1000);
    //   y = distfun_betapdf ( x , a(k) , b(k) );
    //   plot(x,y)
    //   str = msprintf("a=%s, b=%s",..
    //      string(a(k)),string(b(k)));
    //   lgd($+1) = str;
    // end
    // h = gcf();
    // for k = 1 : nf
    //   hk = h.children.children.children(nf - k + 1);
    //   hk.foreground = cols(k);
    // end
    // xtitle("Beta PDF","x","y");
    // legend(lgd);
    // h.children.data_bounds=[0,0;1,3];
    //
    // Authors
    //   Copyright (C) 2009-2011 - DIGITEO - Michael Baudin
    //   Copyright (C) 2012 - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_betapdf" , rhs , 3 )
    apifun_checklhs ( "distfun_betapdf" , lhs , 0:1 )
    //
    // Check type
    apifun_checktype ( "distfun_betapdf" , x , "x" , 1 , "constant" )
    apifun_checktype ( "distfun_betapdf" , a , "a" , 2 , "constant" )
    apifun_checktype ( "distfun_betapdf" , b , "b" , 3 , "constant" )
    //
    apifun_checkrange ( "distfun_betapdf" , x , "x" , 1 , 0 , 1 )
    apifun_checkgreq ( "distfun_betapdf" , a , "a" , 2 , 0 )
    apifun_checkgreq ( "distfun_betapdf" , b , "b" , 3 , 0 )  
    //
    [ x , a , b ] = apifun_expandvar ( x , a , b )
    //
    y = distfun_pdfbeta(x,a,b)
endfunction


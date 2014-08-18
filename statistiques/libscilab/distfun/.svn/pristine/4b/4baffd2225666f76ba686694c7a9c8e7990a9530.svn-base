// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2010 - 2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function y = distfun_gampdf ( x , a , b )
    // Gamma PDF
    //
    // Calling Sequence
    //   y = distfun_gampdf ( x , a , b )
    //
    // Parameters
    //   x : a matrix of doubles, the outcome, x>=0
    //   a : a matrix of doubles, the shape parameter, a>0.
    //   b : a matrix of doubles, the scale parameter, b>0.
    //   y : a matrix of doubles, the density
    //
    // Description
    //   Computes the Gamma probability distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles of the same size as the other input arguments.
    //
    //   The function definition is:
    //
    //<latex>
    //\begin{eqnarray}
    //f(x,a,b) = \frac{1}{b^a\Gamma(a)} x^{a-1} \exp\left(-\frac{x}{b}\right)
    //\end{eqnarray}
    //</latex>
    //
    // Compatibility note. 
    // 
    // This function is compatible with Matlab, but 
    // not compatible with R. 
    // Indeed, notice that b, the scale, is the inverse of the rate. 
    // Other computing languages (including R), use 1/b as the 
    // second parameter of the Gamma distribution.
    // Hence, the calling sequence
    //
    // distfun_gampdf(x,a,b)
    // 
    // corresponds to the R calling sequence:
    //
    // dgamma(x,a,1/b)
    //
    // Examples
    // // Test x scalar, a scalar, b expanded
    // b = 1:5;
    // computed = distfun_gampdf(1,1,b);
    // expected = [..
    // 3.678794411714423340D-01 , ..
    // 3.032653298563167121D-01 , ..
    // 2.388437701912631272D-01 , ..
    // 1.947001957678512196D-01 , ..
    // 1.637461506155963586D-01 ..
    // ];
    //
    // // Test all expanded
    // computed = distfun_gampdf([1 1],[2 2],[3 3]);
    // expected = [..
    // 7.961459006375437575D-02 , ..
    // 7.961459006375437575D-02 ..
    // ];
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
    //   y = distfun_gampdf ( x , shape(k) , scale(k) );
    //   plot(x,y)
    //   str = msprintf("shape=%s, scale=%s",..
    //     string(shape(k)),string(scale(k)));
    //   lgd($+1) = str;
    // end
    // h = gcf();
    // for k = 1 : nf
    //   hcc = h.children.children;
    //   hcc.children(nf - k + 1).foreground = cols(k);
    // end
    // xtitle("Gamma PDF","x","y")
    // legend(lgd);
    //
    // Authors
    //   Copyright (C) 2010 - 2011 - DIGITEO - Michael Baudin
    //   Copyright (C) 2012 - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_gampdf" , rhs , 3 )
    apifun_checklhs ( "distfun_gampdf" , lhs , 0:1 )
    //
    apifun_checktype ( "distfun_gampdf" , x , "x" , 1 , "constant" )
    apifun_checktype ( "distfun_gampdf" , a , "a" , 2 , "constant" )
    apifun_checktype ( "distfun_gampdf" , b , "b" , 3 , "constant" )
    //
    // Check content
    apifun_checkgreq("distfun_gampdf",x,"x",1,0)
	tiny=number_properties("tiny")
    apifun_checkgreq("distfun_gampdf",a,"a",2,tiny)
    apifun_checkgreq("distfun_gampdf",b,"b",3,tiny)
    //
    [ x , a , b ] = apifun_expandvar ( x , a , b )
    //
    y=distfun_pdfgam( x , a , b )
endfunction

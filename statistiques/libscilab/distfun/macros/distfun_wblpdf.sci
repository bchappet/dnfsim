// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function y = distfun_wblpdf ( x , a , b )
    // Weibull PDF
    //
    // Calling Sequence
    //   y = distfun_wblpdf ( x , a , b )
    //
    // Parameters
    //   x : a matrix of doubles, the outcome, x>=0
    //   a : a matrix of doubles, the scale parameter, a>0.
    //   b : a matrix of doubles, the shape parameter, b>0.
    //   y : a matrix of doubles, the density
    //
    // Description
    //   Computes the Weibull probability distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles of the same size as the other input arguments.
    //
    // The function definition is:
    //
    //<latex>
    //\begin{eqnarray}
    // f(x,a,b)=\frac{b}{a} \left(\frac{x}{a}\right)^{b-1} 
    // \exp\left(-(x/a)^b\right)
    //\end{eqnarray}
    //</latex>
    //
    // if x>=0.
    //
    // If b=1, this corresponds to the Exponential distribution. 
    //
    // If b=2, this corresponds to the Rayleigh distribution. 
    //
    // The value of the density at x=0 depends on b. 
    // <itemizedlist>
    //   <listitem>
    //     <para>
    // If 0<b<1, then f(x) → infinity when x → zero.
    //     </para>
    //   </listitem>
    //   <listitem>
    //     <para>
    // If b=1, then f(x) → 1/a when x → zero.
    //     </para>
    //   </listitem>
    //   <listitem>
    //     <para>
    // If b>1, then f(x) → zero when x → zero.
    //     </para>
    //   </listitem>
    // </itemizedlist>
    //
    // Compatibility
    //
    // This distribution has the same parameters as in Matlab. 
    //
    // In R, the parameters a and b are switched, so that 
    // the statement in distfun :
    //
    // <programlisting>
    // y=distfun_wblpdf(x,a,b)
    // </programlisting>
    //
    // is the same as the statement in R :
    //
    // <programlisting>
    // y=dweibull(x,b,a)
    // </programlisting>
    //
    // Examples
    // // Check with a and b to be expanded
    // computed = distfun_wblpdf ( 0.1:0.2:0.7 , 2 , 3 )
    // expected = [0.0037495  0.0336363  0.0922965  0.1760382]
    //
    // // Plot the function
    // a = 1;
    // b = [0.5 1 1.5 5];
    // cols = [1 2 3 5];
    // nf = size(cols,"*");
    // lgd = [];
    // scf();
    // for k = 1 : nf
    //   x = linspace(0,2.5,1000);
    //   y = distfun_wblpdf(x,a,b(k));
    //   plot(x,y)
    //   str = msprintf("a=%s, b=%s",..
    //      string(a),string(b(k)));
    //   lgd($+1) = str;
    // end
    // h = gcf();
    // for k = 1 : nf
    //   hk = h.children.children.children(nf - k + 1);
    //   hk.foreground = cols(k);
    // end
    // xtitle("Weibull PDF","x","y");
    // legend(lgd);
    // h.children.data_bounds=[0,0;2.5,2.5];
    //
    // Authors
    //   Copyright (C) 2014 - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_wblpdf" , rhs , 3 )
    apifun_checklhs ( "distfun_wblpdf" , lhs , 0:1 )
    //
    // Check type
    apifun_checktype ( "distfun_wblpdf" , x , "x" , 1 , "constant" )
    apifun_checktype ( "distfun_wblpdf" , a , "a" , 2 , "constant" )
    apifun_checktype ( "distfun_wblpdf" , b , "b" , 3 , "constant" )
    //
    apifun_checkgreq ( "distfun_wblpdf" , x , "x" , 1 , 0 )
    tiny=number_properties("tiny")
    apifun_checkgreq ( "distfun_wblpdf" , a , "a" , 2 , tiny )
    apifun_checkgreq ( "distfun_wblpdf" , b , "b" , 3 , tiny )  
    //
    [ x , a , b ] = apifun_expandvar ( x , a , b )
    //
    y = distfun_pdfwbl(x,a,b)
endfunction


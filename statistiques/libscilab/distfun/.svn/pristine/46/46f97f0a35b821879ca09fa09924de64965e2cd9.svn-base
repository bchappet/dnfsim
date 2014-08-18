// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function y = distfun_ncx2pdf(varargin)
    // oncentral Chi-squared PDF
    //
    // Calling Sequence
    //   y = distfun_ncx2pdf(x,k,delta)
    //   
    // Parameters
    //   x : a matrix of doubles, the outcome, greater or equal to zero
    //   k : a matrix of doubles, the number of degrees of freedom, k>0 (can be non integer)
    //   delta : a matrix of doubles, the noncentrality parameter, delta>=0
    //   y : a matrix of doubles, the probability density.
    //
    // Description
    //   Computes the probability distribution function of 
    //   the Noncentral Chi-squared distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // <emphasis>Caution</emphasis>
    // This distribution is known to have inferior accuracy in 
    // some cases.
    //
    //   The function definition is: 
    //
    // <latex>
    // \begin{eqnarray}
    // f(x) = \sum_{i\geq 0} \frac{1}{i!} e^{-\delta/2} (\delta/2)^i f_{k+2i}(x),
    // \end{eqnarray}
    // </latex>
    //
	// where <latex>f_{k+2i}</latex> is the chi-squared 
	// probability density function with k+2i degrees of 
	// freedom.
    //
    // Examples
    // // Test with x scalar, k scalar
    // computed = distfun_ncx2pdf(9,5,4)
    // expected = 0.0756164
    //
    // // Plot the function
    // h=scf();
    // k = [2 2 2 4 4 4];
    // delta = [1 2 3 1 2 3];
    // cols = [1 2 3 4 5 6];
    // lgd = [];
    // for i = 1:size(k,"c")
    //   x = linspace(0,10,1000);
    //   y = distfun_ncx2pdf ( x , k(i), delta(i));
    //   plot(x,y)
    //   str = msprintf("k=%s, delta=%s",..
    //     string(k(i)),string(delta(i)));
    //   lgd($+1) = str;
    // end
    // for i = 1:size(k,"c")
    //     hcc = h.children.children;
    //     hcc.children(size(k,"c") - i + 1).foreground = cols(i);
    // end
    // xtitle("Noncentral Chi-squared PDF","x","y")
    // legend(lgd);
    // 
    // Bibliography
    // http://en.wikipedia.org/wiki/Chi-squared_distribution
    //
    // Authors
    // Copyright (C) 2014 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_ncx2pdf",rhs,3)
    apifun_checklhs("distfun_ncx2pdf",lhs,0:1)

    x=varargin(1)
    k=varargin(2)
    delta=varargin(3)
    //
    // Check type
    apifun_checktype("distfun_ncx2pdf",x,"x",1,"constant")
    apifun_checktype("distfun_ncx2pdf",k,"k",2,"constant")
    apifun_checktype("distfun_ncx2pdf",delta,"delta",3,"constant")
    //
    // Check content
    apifun_checkgreq("distfun_ncx2pdf",x,"x",1,0)
    tiny=number_properties("tiny")
    apifun_checkgreq("distfun_ncx2pdf",k,"k",2,tiny)
    apifun_checkgreq("distfun_ncx2inv",delta,"delta",3,0.)

    [x,k,delta] = apifun_expandvar(x,k,delta)
    y = distfun_pdfncx2(x,k,delta)
endfunction

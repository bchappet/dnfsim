// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function y = distfun_chi2pdf(varargin)
    // Chi-squared PDF
    //
    // Calling Sequence
    //   y = distfun_chi2pdf(x,k)
    //   
    // Parameters
    //   x : a matrix of doubles, the outcome, greater or equal to zero
    //   k : a matrix of doubles, the number of degrees of freedom, k>0 (can be non integer)
    //   y : a matrix of doubles, the probability density.
    //
    // Description
    //   Computes the probability distribution function of 
    //   the Chi-squared distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    //   The function definition is:
    //
    //<latex>
    //\begin{eqnarray}
    //f(x,k) = \frac{1}{2^{\frac{k}{2}}\Gamma\left(\frac{k}{2}\right)} x^{\frac{k}{2}-1} \exp\left(-\frac{x}{2}\right)
    //\end{eqnarray}
    //</latex>
    //
    // Analysis of the random variable.
    //
	// If Z1, ..., Zk are independent standard normal random variables, 
	// then 
    //
    //<latex>
    //\begin{eqnarray}
    //Q=Z_1^2+...+Z_k^2
    //\end{eqnarray}
    //</latex>
	//
	// has a chi-squared distribution with k degrees of freedom.
    //
    // Examples
    // // Test with x scalar, k scalar
    // computed = distfun_chi2pdf(4,5)
    // expected = 0.1439759
    //
    // // Test with expanded x, k scalar
    // computed = distfun_chi2pdf([2 6],5)
    // expected = [0.1383692 0.0973043]
    //
    // // Test with x scalar, k expanded
    // computed = distfun_chi2pdf(4,[4 7])
    // expected = [0.1353353 0.1151807]
    // 
    // // Test with both x,k expanded
    // computed = distfun_chi2pdf([2 6],[3 4])
    // expected = [0.2075537 0.0746806]
    //
    // // Plot the function
    // h=scf();
    // k = [2 3 4 6 9 12];
    // cols = [1 2 3 4 5 6];
    // lgd = [];
    // for i = 1:size(k,"c")
    //   x = linspace(0,10,1000);
    //   y = distfun_chi2pdf ( x , k(i) );
    //   plot(x,y)
    //   str = msprintf("k=%s",string(k(i)));
    //   lgd($+1) = str;
    // end
    // for i = 1:size(k,"c")
    //     hcc = h.children.children;
    //     hcc.children(size(k,"c") - i + 1).foreground = cols(i);
    // end
    // xtitle("Chi-squared PDF","x","y")
    // legend(lgd);
    // 
    // Bibliography
    // http://en.wikipedia.org/wiki/Chi-squared_distribution
    //
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
    // Copyright (C) 2012 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_chi2pdf",rhs,2)
    apifun_checklhs("distfun_chi2pdf",lhs,0:1)

    x=varargin(1)
    k=varargin(2)
    //
    // Check type
    apifun_checktype("distfun_chi2pdf",x,"x",1,"constant")
    apifun_checktype("distfun_chi2pdf",k,"k",2,"constant")
    //
    // Check content
    apifun_checkgreq("distfun_chi2pdf",x,"x",1,0)
    tiny=number_properties("tiny")
    apifun_checkgreq("distfun_chi2pdf",k,"k",2,tiny)

    [x,k] = apifun_expandvar(x,k)
    y = distfun_pdfchi2(x,k)
endfunction

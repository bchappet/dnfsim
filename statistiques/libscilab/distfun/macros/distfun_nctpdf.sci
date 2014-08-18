// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function y = distfun_nctpdf(varargin)
    // Noncentral T PDF
    //
    // Calling Sequence
    //   y = distfun_nctpdf ( x , v , delta )
    //
    // Parameters
    //   x : a matrix of doubles, the outcome.
    //   v : a matrix of doubles, the number of degrees of freedom, v>0. 
    //   delta : a matrix of doubles, the noncentrality parameter, delta is real
    //   y : a matrix of doubles, the density
    //
    // Description
    //   Computes the Noncentral T probability distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles of the same size as the other input arguments.
    //
    // <emphasis>Caution</emphasis>
    // This distribution is known to have inferior accuracy in 
    // some cases.
    //
    // If Z is a normal random variable with mean 0 and standard deviation 1, 
    // and T is a Chi-squared random variable with v degrees of freedom, 
    // then the variable
    //
    //<latex>
    //\begin{eqnarray}
    //\frac{Z+\delta}{\sqrt{T/v}}
    //\end{eqnarray}
    //</latex>
    //
    // has a Noncentral T distribution with v degrees of freedom 
    // and delta noncentrality parameter.
    //
    // When the number of degrees of freedom v increases, the 
    // Noncentral T distribution approaches the Normal distribution with 
    // mean 0 and variance 1.
    //
    // Examples
    // y=distfun_nctpdf(7,2,10)
    // expected = 0.0750308
    //
    // // Plot the function
    // h=scf();
    // x = linspace(-5,10,1000);
    // p1 = distfun_nctpdf(x,1,0);
    // p2 = distfun_nctpdf(x,4,0);
    // p3 = distfun_nctpdf(x,1,2);
    // p4 = distfun_nctpdf(x,4,2);
    // plot(x,p1,"r")
    // plot(x,p2,"g")
    // plot(x,p3,"b")
    // plot(x,p4,"k")
    // legend(["v=1, delta=0", ..
    // "v=4, delta=0", ..
    // "v=1, delta=2", ..
    // "v=4, delta=2"]);
    // xtitle("Noncentral T PDF","x","y");
    //
    // Authors
    // Copyright (C) 2014 - Michael Baudin
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Noncentral_t-distribution

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_nctpdf",rhs,3)
    apifun_checklhs("distfun_nctpdf",lhs,0:1)

    x=varargin(1)
    v=varargin(2)
    delta = varargin(3)
    //
    // Check type
    apifun_checktype("distfun_nctpdf",x,"x",1,"constant")
    apifun_checktype("distfun_nctpdf",v,"v",2,"constant")
    apifun_checktype("distfun_nctpdf",delta,"delta",3,"constant")
    //
    // Check content
    tiny=number_properties("tiny") 
    apifun_checkgreq("distfun_nctpdf",v,"v",2,tiny)

    [x,v,delta] = apifun_expandvar(x,v,delta)

    if (x == []) then
        y = []
        return
    end

    y = distfun_pdfnct(x,v,delta)
endfunction

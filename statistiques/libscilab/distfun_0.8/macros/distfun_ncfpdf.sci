// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function y = distfun_ncfpdf(varargin)
    // Noncentral F-distribution PDF
    //
    // Calling Sequence
    //   y = distfun_ncfpdf(x,v1,v2,delta)
    //   
    // Parameters
    //   x : a matrix of doubles. x is real and x>=0.
    //   v1 : a matrix of doubles, numerator degrees of freedom, v1>0 (can be non integer).
    //   v2 : a matrix of doubles, denominator degrees of freedom, v2>0 (can be non integer).
    //   delta : a matrix of doubles, the noncentrality parameter, delta>=0
    //   y : a matrix of doubles, the probability density.
    //
    // Description
    //   Computes the probability distribution function of 
    //   the Noncentral F distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // <emphasis>Caution</emphasis>
    // This distribution is known to have inferior accuracy in 
    // some cases.
    //
    // The Noncentral F distribution has density
    //
    // <latex>
    // \begin{eqnarray}
    // f(x) = \sum_{k\geq 0} \frac{e^{-\delta/2}(\delta/2)^k}{B(v_2/2,v_1/2+k) k!} \left(\frac{v_1}{v_2}\right)^{v_1/2+k} \left(\frac{v_2}{v_2+v_1 x}\right)^{(v_1+v_2)/2+k} x^{v_1/2-1+k}
    // \end{eqnarray}
    // </latex>
    //
    // for x >= 0 and is zero if x<0.
    //
    // In the previous equation, the function B(x,y) is the beta 
    // function, defined by the equation :
    //
    // <latex>
    // \begin{eqnarray}
    // B(x,y) = \frac{\Gamma(x)\Gamma(y)}{\Gamma(x+y)}.
    // \end{eqnarray}
    // </latex>
    //
    // If delta=0, the Noncentral F distribution is 
    // equal to the F distribution.
    //
    // Analysis of the random variable.
    //
    // If X is a noncentral chi-squared random variable with v1 degrees of freedom, 
    // and non centrality parameter delta, 
    // Y is a chi-squared random variable with v2 degrees of freedom, therefore 
    // the random variable
    //
    // <latex>
    // \begin{eqnarray}
    // F = \frac{X/v_1}{Y/v_2}
    // \end{eqnarray}
    // </latex>
    //
    // has a Noncentral F-distribution with parameters v1, delta and v2.
    //
    // Examples
    // // Test with x scalar, v1 scalar, v2 scalar
    // computed = distfun_ncfpdf(5, 4, 12, 0.3)
    // expected = 0.011247305243159637
    //
    // // Plot the function
    // h=scf();
    // x = linspace(0,15,1000);
    // p1 = distfun_ncfpdf(x,10,20,0);
    // p2 = distfun_ncfpdf(x,10,20,1);
    // p3 = distfun_ncfpdf(x,10,20,5);
    // p4 = distfun_ncfpdf(x,10,20,10);
    // p5 = distfun_ncfpdf(x,10,20,40);
    // plot(x,p1,"r")
    // plot(x,p2,"g")
    // plot(x,p3,"b")
    // plot(x,p4,"y")
    // plot(x,p5,"k")
    // legend([
    // "v1=10, v2=20, delta=0"
    // "v1=10, v2=20, delta=1"
    // "v1=10, v2=20, delta=5"
    // "v1=10, v2=20, delta=10"
    // "v1=10, v2=20, delta=40"
    // ]);
    // xtitle("Noncentral F PDF","x","y");
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Noncentral_F-distribution
    // http://www.boost.org/doc/libs/1_55_0/libs/math/doc/html/math_toolkit/dist_ref/dists/nc_f_dist.html
    //
    // Authors
    // Copyright (C) 2014 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_ncfpdf",rhs,4)
    apifun_checklhs("distfun_ncfpdf",lhs,0:1)

    x=varargin(1)
    v1=varargin(2)
    v2=varargin(3)
    delta=varargin(4)
    //
    // Check type
    apifun_checktype("distfun_ncfpdf",x,"x",1,"constant")
    apifun_checktype("distfun_ncfpdf",v1,"v1",2,"constant")
    apifun_checktype("distfun_ncfpdf",v2,"v2",3,"constant")
    apifun_checktype("distfun_ncfpdf",delta,"delta",4,"constant")
    //
    // Check content
    apifun_checkgreq("distfun_ncfpdf",x,"x",1,0)
    tiniest=number_properties("tiniest")
    apifun_checkgreq("distfun_ncfpdf",v1,"v1",2,tiniest)
    apifun_checkgreq("distfun_ncfpdf",v2,"v2",3,tiniest)
    apifun_checkgreq("distfun_ncfpdf",delta,"delta",4,0.)

    [x,v1,v2,delta] = apifun_expandvar(x,v1,v2,delta)

    y = distfun_pdfncf(x,v1,v2, delta)
endfunction


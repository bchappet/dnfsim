// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function y = distfun_fpdf(varargin)
    // F-distribution PDF
    //
    // Calling Sequence
    //   y = distfun_fpdf(x,v1,v2)
    //   
    // Parameters
    //   x : a matrix of doubles. x is real and x>=0.
    //   v1 : a matrix of doubles, numerator degrees of freedom, v1>0 (can be non integer).
    //   v2 : a matrix of doubles, denominator degrees of freedom, v2>0 (can be non integer).
    //   y : a matrix of doubles, the probability density.
    //
    // Description
    //   Computes the probability distribution function of 
    //   the f distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // The F distribution has density
    //
    // <latex>
    // \begin{eqnarray}
    // f(x) = \frac{\sqrt{\frac{(v_1 x)^{v_1} v_2^{v_2}}{(v_1 x+v_2)^{v_1+v_2}}}}{x B\left(\frac{v_1}{2},\frac{v_2}{2}\right)}
    // \end{eqnarray}
    // </latex>
    //
    // for x >= 0 and is zero if x<0.
    //
    // Analysis of the random variable.
    //
    // If R1 is a chi-squared random variable with v1 degrees of freedom and 
    // R2 is a chi-squared random variable with v2 degrees of freedom, therefore 
    // the random variable
    //
    // <latex>
    // \begin{eqnarray}
    // \frac{R_1/v_1}{R_2/v_2}
    // \end{eqnarray}
    // </latex>
    //
    // has a F-distribution with parameters v1 and v2.
    //
    // Examples
    // // Test with x scalar, v1 scalar, v2 scalar
    // computed = distfun_fpdf(3,4,5) 
    // expected = 0.06817955
    //
    // // Test with x scalar, v1 scalar, v2 scalar
    // computed = distfun_fpdf(3,2.5,1.5) 
    // expected = 0.0623281
    //
    // computed = distfun_fpdf(1.e2,1.e10,1.e-10) 
    // expected = 5.000D-13
    //
    // // Plot the function
    // h=scf();
    // x = linspace(0,5,1000);
    // p1 = distfun_fcdf(x,1,1);
    // p2 = distfun_fcdf(x,2,1);
    // p3 = distfun_fcdf(x,5,2);
    // p4 = distfun_fcdf(x,100,1);
    // p5 = distfun_fcdf(x,100,100);
    // plot(x,p1,"r")
    // plot(x,p2,"g")
    // plot(x,p3,"b")
    // plot(x,p4,"y")
    // plot(x,p5,"k")
    // legend([
    // "v1=1, v2=1"
    // "v1=2, v2=1";
    // "v1=5, v2=2"
    // "v1=100, v2=1"
    // "v1=100, v2=100"
    // ]);
    // xtitle("F PDF","x","y");
    //
    // // See how the distribution goes when
    // // v1 and v2 are large
    // scf();
    // x=linspace(0,2,100);
    // y=distfun_fpdf(x,1.e1,1.e1);
    // plot(x,y,"r")
    // y=distfun_fpdf(x,1.e2,1.e2);
    // plot(x,y,"g")
    // y=distfun_fpdf(x,1.e3,1.e3);
    // plot(x,y,"b")
    // y=distfun_fpdf(x,1.e4,1.e4);
    // plot(x,y,"k")
    // xtitle("F PDF","x","Density")
    // legend([
    // "v1=10^1, v2=10^1"
    // "v1=10^2, v2=10^2"
    // "v1=10^3, v2=10^3"
    // "v1=10^4, v2=10^4"
    // ]);
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/F-distribution
    // Catherine Loader, http://svn.r-project.org/R/trunk/src/nmath/df.c 
    //
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
    // Copyright (C) 2012 - Michael Baudin
    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_fpdf",rhs,3)
    apifun_checklhs("distfun_fpdf",lhs,0:1)

    x=varargin(1)
    v1=varargin(2)
    v2=varargin(3)
    //
    // Check type
    apifun_checktype("distfun_fpdf",x,"x",1,"constant")
    apifun_checktype("distfun_fpdf",v1,"v1",2,"constant")
    apifun_checktype("distfun_fpdf",v2,"v2",3,"constant")
    //
    // Check content
    apifun_checkgreq("distfun_fpdf",x,"x",1,0)
    tiniest=number_properties("tiniest")
    apifun_checkgreq("distfun_fpdf",v1,"v1",2,tiniest)
    apifun_checkgreq("distfun_fpdf",v2,"v2",3,tiniest)

    [x,v1,v2] = apifun_expandvar(x,v1,v2)

    y = distfun_pdff(x,v1,v2)
endfunction


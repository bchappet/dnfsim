// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function y = distfun_tpdf(varargin)
    // T PDF
    //
    // Calling Sequence
    //   y = distfun_tpdf ( x , v )
    //
    // Parameters
    //   x : a matrix of doubles, the outcome.
    //   v : a matrix of doubles, the number of degrees of freedom, v>0. 
    //   y : a matrix of doubles, the density
    //
    // Description
    //   Computes the T probability distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles of the same size as the other input arguments.
    //
    // The function definition is:
    //
    //<latex>
    //\begin{eqnarray}
    //f(x,v) = \frac{\Gamma\left(\frac{v+1}{2}\right)}{\Gamma\left(\frac{v}{2}\right)} \frac{1}{\sqrt{v\pi}} \frac{1}{\left(1+\frac{x^2}{v}\right)^{\frac{v+1}{2}}}
    //\end{eqnarray}
    //</latex>
    //
    // Analysis of the random variable.
    //
    // If Z is a normal random variable with mean 0 and standard deviation 1, 
	// and C is a chi-squared random variable with v degrees of freedom, then 
	// the variable
    //
    //<latex>
    //\begin{eqnarray}
    //\frac{Z}{\sqrt{C/v}}
    //\end{eqnarray}
    //</latex>
	//
	// has a T distribution with v degrees of freedom.
    //
    // When the number of degrees of freedom v increases, the 
    // T distribution approaches the Normal distribution with 
    // mean 0 and variance 1.
    //
    // Examples
    // // Check with a and b to be expanded
    // computed = distfun_tpdf ( -3:2:3 , 2 )
    // expected = [0.0274101,0.1924501,0.1924501,0.0274101]
    // // Check with expanded arguments
    // x = -3:2:3;
    // v = [5 4 3 2];
    // computed = distfun_tpdf ( x , v )
    // expected = [0.0172926,0.2146625,0.2067483,0.0274101]
    //
    // // Plot the function
    // h=scf();
    // x = linspace(-5,5,1000);
    // p1 = distfun_tpdf(x,1);
    // p2 = distfun_tpdf(x,2);
    // p3 = distfun_tpdf(x,5);
    // p4 = distfun_tpdf(x,%inf);
    // plot(x,p1,"r")
    // plot(x,p2,"g")
    // plot(x,p3,"b")
    // plot(x,p4,"k")
    // legend(["v=1" "v=2" "v=5" "v=Inf"]);
    // xtitle("T PDF","x","y");
    //
    // // The number of degrees of freedom can have a 
    // // fractional part (i.e. a true real number)
    // computed = distfun_tpdf ( 2 , 2.73 ) // 0.0677824
    //
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
    // Copyright (C) 2012 - Michael Baudin
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Student%27s_t-distribution


    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_tpdf",rhs,2)
    apifun_checklhs("distfun_tpdf",lhs,0:1)

    x=varargin(1)
    v=varargin(2)
    //
    // Check type
    apifun_checktype("distfun_tpdf",x,"x",1,"constant")
    apifun_checktype("distfun_tpdf",v,"v",2,"constant")
    //
    // Check content
    tiny=number_properties("tiny") 
    apifun_checkgreq("distfun_tpdf",v,"v",2,tiny)

    [x,v] = apifun_expandvar(x,v)

    if (x == []) then
        y = []
        return
    end

    y = distfun_pdft(x,v)
endfunction

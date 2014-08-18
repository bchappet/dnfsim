// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt


function y = distfun_geopdf(varargin)
    // Geometric PDF
    //
    // Calling Sequence
    //   y = distfun_geopdf(x,pr)
    //   
    // Parameters
    //   x : a matrix of doubles, the number of Bernoulli trials after which the first success occurs. x belongs to the set {0,1,2,3,.....}
    //   pr : a matrix of doubles,  the probability of success in a Bernoulli trial. pr in (0,1].
    //   y : a matrix of doubles, the probability density.
    //
    // Description
    //   Computes the probability distribution function of 
    //   the Geometric distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    //   The function definition is:
    //
    // <latex>
    // \begin{eqnarray}
    // f(x,pr) = pr\left(1-pr\right)^x
    // \end{eqnarray}
    // </latex>
	//
	// Analysis of the random variable
	//
	// The random variable X is the number of Bernoulli trials 
	// needed to get one success. 
    //
    // Compatibility Note : x belongs to the set {0,1,2,3,...}. 
    // This choice is compatible with Matlab and R. 
    // This is different from Scilab v5 grand(m,n,"geom"), which 
    // uses {1,2,3,...}. 
    //
    // Examples
    // // Test x scalar , pr scalar
    // computed = distfun_geopdf(3,0.5)
    // expected = 0.0625;
    //
    // // Test with x expanded, with pr scalar
    //computed = distfun_geopdf([2 3],0.1)
    //expected = [0.081 0.0729];
    //
    // //Test with x scalar, pr expanded
    //computed = distfun_geopdf(3,[0.2 0.4])
    //expected = [0.1024 0.0864];
    //
    // //Test with both arguments expanded
    //computed = distfun_geopdf([3 4 8],[0.5 0.8 0.2])
    //expected = [0.0625 0.00128 0.033554432];
    //
    // // Plot the function
    // scf();
    // x = 0:10;
    // y = distfun_geopdf(x,0.2);
    // plot(x,y,"ro-");
    // y1 = distfun_geopdf(x,0.5);
    // plot(x,y1,"go-");
    // y2 = distfun_geopdf(x,0.8);
    // plot(x,y2,"bo-");
    // xtitle("Geometric PDF","x","P(X=x)");
    // legend(["pr=0.2","pr=0.5","pr=0.8"]);
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Geometric_distribution
    //
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
    // Copyright (C) 2012 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_geopdf",rhs,2)
    apifun_checklhs("distfun_geopdf",lhs,0:1)

    x=varargin(1)
    pr=varargin(2)
    //
    // Check type
    apifun_checktype("distfun_geopdf",x,"x",1,"constant")
    apifun_checktype("distfun_geopdf",pr,"pr",2,"constant")
    //
    // Check content
    apifun_checkflint("distfun_geopdf",x,"x",1)
    apifun_checkgreq("distfun_geopdf",x,"x",1,0)
	tiniest=number_properties("tiniest")
    apifun_checkrange("distfun_geopdf",pr,"pr",2,tiniest,1)

    [x,pr] = apifun_expandvar(x,pr)

    y = distfun_pdfgeo(x,pr)
endfunction

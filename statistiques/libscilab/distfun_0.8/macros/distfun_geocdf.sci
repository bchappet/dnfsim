// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
//
function p = distfun_geocdf(varargin)
    // Geometric CDF
    //
    // Calling Sequence
    //   p = distfun_geocdf(x,pr)
    //   p = distfun_geocdf(x,pr,lowertail)
    //
    // Parameters
    //   x : a matrix of doubles, the number of Bernoulli trials after which the first success occurs. x belongs to the set {0,1,2,3,......}
    //   pr : a matrix of doubles,  the probability of success in a Bernoulli trial. pr in (0,1].
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   p : a matrix of doubles, the probability.
    //
    // Description
    //   Computes the cumulative distribution function of 
    //   the Geometric distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Examples
    //p = distfun_geocdf(3,0.5)
    //expected = 0.9375;
    //
    // //Test with x expanded, pr scalar
    //computed = distfun_geocdf([2 3],0.33)
    //expected = [0.699237 0.7984888];
    //
    // //Test with x scalar, pr expanded
    //computed = distfun_geocdf(3,[0.2 0.4])
    //expected = [0.5904 0.8704];
    //
    // //Test with both the arguments expanded
    //
    //computed = distfun_geocdf([3 4 8],[0.5 0.8 0.2])
    //expected = [0.9375 0.99968 0.8657823];
    //
    // //Plot the function
    // h=scf(); 
    // x=(0:10)';
    // p1=distfun_geocdf(x,0.2);
    // p2=distfun_geocdf(x,0.5);
    // p3=distfun_geocdf(x,0.8);
    // legendspec=["pr=0.2","pr=0.5","pr=0.8"];
    // distfun_plotintcdf(x,[p1,p2,p3],["r" "g" "b"],legendspec);
    // xtitle("Geometric CDF")
    // h.children.children(1).legend_location="in_lower_right";
    // 
    // // See upper tail
    // p = distfun_geocdf(3,0.1)
    // lt_expected = 0.3439;
    // q = distfun_geocdf(3,0.1,%f)
    // ut_expected = 0.6561;
    // p+q
    //
    // // See accuray in the upper tail
    // p = distfun_geocdf(100,0.5,%f)
    // expected = 3.944305e-31
    // // See accuray when pr is small
    // p = distfun_geocdf(1,1.e-20)
    // expected = 2.e-20
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Geometric_distribution
    // 
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
    // Copyright (C) 2012 - Michael Baudin

    [lhs,rhs] = argn();
    apifun_checkrhs("distfun_geocdf",rhs,2:3)
    apifun_checklhs("distfun_geocdf",lhs,0:1)

    x = varargin(1)
    pr = varargin(2)
    lowertail = apifun_argindefault(varargin,3,%t)
    //
    // Check type
    apifun_checktype("distfun_geocdf",x,"x",1,"constant")
    apifun_checktype("distfun_geocdf",pr,"pr",2,"constant")
    apifun_checktype("distfun_geocdf",lowertail,"lowertail",3,"boolean")

    apifun_checkscalar("distfun_geocdf",lowertail,"lowertail",3)
    //
    // Check content
    apifun_checkflint("distfun_geocdf",x,"x",1)
    apifun_checkgreq("distfun_geocdf",x,"x",1,0)
	tiniest=number_properties("tiniest")
    apifun_checkrange("distfun_geocdf",pr,"pr",2,tiniest,1)

    [x,pr] = apifun_expandvar(x,pr)
    p=distfun_cdfgeo(x,pr,lowertail)
endfunction

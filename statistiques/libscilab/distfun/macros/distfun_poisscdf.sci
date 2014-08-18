// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function p = distfun_poisscdf(varargin)
    // Poisson CDF
    //
    // Calling Sequence
    //   p = distfun_poisscdf(x,lambda)
    //   p = distfun_poisscdf(x,lambda,lowertail)
    //
    // Parameters
    //   x : a matrix of doubles, the number of occurrences of events. x belongs to the set {0,1,2,3,.....}.
    //   lambda : a matrix of doubles, the average rate of occurrence. lambda>0.
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   p : a matrix of doubles, the probability.
    //
    // Description
    //   Computes the cumulative distribution function of 
    //   the Poisson distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Examples
    // //Test with x scalar, lambda scalar
    // p = distfun_poisscdf(0,2)
    // expected = 0.1353353;
    //
    // //Test with x expanded, lambda scalar
    // computed = distfun_poisscdf([0 3],2)
    // expected = [0.1353353 0.8571235];
    //
    // //Test with x scalar, lambda expanded
    // computed = distfun_poisscdf(3,[2 4])
    // expected = [0.8571235 0.4334701];
    //
    // //Test with both the arguments expanded
    // computed = distfun_poisscdf([3 4 8],[5 8 2])
    // expected = [0.2650259 0.0996324 0.9997626];
    //
    // //Plot the function
    // h=scf();
    // x=(0:20)';
    // p1=distfun_poisscdf(x,1);
    // p2=distfun_poisscdf(x,4);
    // p3=distfun_poisscdf(x,10);
    // legendspec=["lambda=1","lambda=4","lambda=10"];
    // distfun_plotintcdf(x,[p1,p2,p3],["r" "g" "b"],legendspec);
    // xtitle("Poisson CDF")
    // h.children.children(1).legend_location="in_lower_right";
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Poisson_distribution
    // 
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
    // Copyright (C) 2012 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_poisscdf",rhs,2:3)
    apifun_checklhs("distfun_poisscdf",lhs,0:1)

    x = varargin(1)
    lambda = varargin(2)
    lowertail = apifun_argindefault(varargin,3,%t)
    //
    // Check type
    //
    apifun_checktype("distfun_poisscdf",x,"x",1,"constant")
    apifun_checktype("distfun_poisscdf",lambda,"lambda",2,"constant")
    apifun_checktype("distfun_poisscdf",lowertail,"lowertail",3,"boolean")

    apifun_checkscalar("distfun_poisscdf",lowertail,"lowertail",3)
    //
    //Check content
    //    
    apifun_checkgreq("distfun_poisscdf",x,"x",1,0)
    apifun_checkflint("distfun_poisscdf",x,"x",1)
    tiny = number_properties("tiny")
    apifun_checkgreq("distfun_poisscdf",lambda,"lambda",2,tiny)

    [x,lambda] = apifun_expandvar(x,lambda)
    p = distfun_cdfpoiss(x,lambda,lowertail)
endfunction

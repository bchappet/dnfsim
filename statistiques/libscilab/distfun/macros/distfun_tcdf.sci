// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function p = distfun_tcdf(varargin)
    // T CDF
    //
    // Calling Sequence
    //   p = distfun_tcdf ( x , v )
    //   p = distfun_tcdf ( x , v , lowertail )
    //
    // Parameters
    //   x : a matrix of doubles, the outcome. 
    //   v : a matrix of doubles, the number of degrees of freedom, v>0. 
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   p : a matrix of doubles, the probability.
    //
    // Description
    //   Computes the T cumulated distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles of the same size as the other input arguments.
    //
    // Examples
    // p = distfun_tcdf(1,2) // 0.7886751
    // // With lower tail
    // p = distfun_tcdf(1,2,%f) // 0.2113249
    //
    // // Plot the function
    // h=scf();
    // x = linspace(-5,5,1000);
    // p1 = distfun_tcdf(x,1);
    // p2 = distfun_tcdf(x,2);
    // p3 = distfun_tcdf(x,5);
    // p4 = distfun_tcdf(x,%inf);
    // plot(x,p1,"r")
    // plot(x,p2,"g")
    // plot(x,p3,"b")
    // plot(x,p4,"k")
    // legend(["v=1" "v=2" "v=5" "v=Inf"]);
    // xtitle("T CDF","x","$P(X\leq x)$");
    //
    // // An extreme case
    // distfun_tcdf(40,10,%f) // 1.140D-12
    //
    // Authors
    //   Copyright (C) 2012 - Michael Baudin
    //   Copyright (C) 2012 - Prateek Papriwal

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_tcdf",rhs,2:3)
    apifun_checklhs("distfun_tcdf",lhs,0:1)
    //
    x = varargin(1)
    v = varargin(2)
    lowertail = apifun_argindefault(varargin,3,%t)
    //
    // Check type
    apifun_checktype("distfun_tcdf",x,"x",1,"constant")
    apifun_checktype("distfun_tcdf",v,"v",2,"constant")
    apifun_checktype("distfun_tcdf",lowertail,"lowertail",3,"boolean")

    apifun_checkscalar("distfun_tcdf",lowertail,"lowertail",3)
    //
    // Check content
    tiny=number_properties("tiny") 
    apifun_checkgreq("distfun_tcdf",v,"v",2,tiny)

    [x,v] = apifun_expandvar(x,v)
    p = distfun_cdft(x,v,lowertail)
endfunction

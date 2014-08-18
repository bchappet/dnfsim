// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function p = distfun_nctcdf(varargin)
    // Noncentral T CDF
    //
    // Calling Sequence
    //   p = distfun_nctcdf ( x , v , delta )
    //   p = distfun_nctcdf ( x , v , delta , lowertail )
    //
    // Parameters
    //   x : a matrix of doubles, the outcome. 
    //   v : a matrix of doubles, the number of degrees of freedom, v>0. 
    //   delta : a matrix of doubles, the noncentrality parameter, delta is real
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   p : a matrix of doubles, the probability.
    //
    // Description
    //   Computes the Noncentral T cumulated distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles of the same size as the other input arguments.
    //
    // <emphasis>Caution</emphasis>
    // This distribution is known to have inferior accuracy in 
    // some cases.
    //
    // Examples
    // p = distfun_nctcdf(8,2,10)
    // expected=0.2164194
    //
    // // Plot the function
    // h=scf();
    // x = linspace(-5,10,1000);
    // p1 = distfun_nctcdf(x,1,0);
    // p2 = distfun_nctcdf(x,4,0);
    // p3 = distfun_nctcdf(x,1,2);
    // p4 = distfun_nctcdf(x,4,2);
    // plot(x,p1,"r")
    // plot(x,p2,"g")
    // plot(x,p3,"b")
    // plot(x,p4,"k")
    // legend(["v=1, delta=0", ..
    // "v=4, delta=0", ..
    // "v=1, delta=2", ..
    // "v=4, delta=2"]);
    // xtitle("Noncentral T CDF","x","$P(X\leq x)$");
    //
    // Authors
    //   Copyright (C) 2014 - Prateek Papriwal

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_nctcdf",rhs,3:4)
    apifun_checklhs("distfun_nctcdf",lhs,0:1)
    //
    x = varargin(1)
    v = varargin(2)
    delta = varargin(3)
    lowertail = apifun_argindefault(varargin,4,%t)
    //
    // Check type
    apifun_checktype("distfun_nctcdf",x,"x",1,"constant")
    apifun_checktype("distfun_nctcdf",v,"v",2,"constant")
    apifun_checktype("distfun_nctcdf",delta,"delta",3,"constant")
    apifun_checktype("distfun_nctcdf",lowertail,"lowertail",4,"boolean")

    apifun_checkscalar("distfun_nctcdf",lowertail,"lowertail",4)
    //
    // Check content
    tiny=number_properties("tiny") 
    apifun_checkgreq("distfun_nctcdf",v,"v",2,tiny)

    [x,v,delta] = apifun_expandvar(x,v,delta)
    p = distfun_cdfnct(x,v,delta,lowertail)
endfunction

// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function p = distfun_ncfcdf(varargin)
    // Noncentral F CDF
    //
    // Calling Sequence
    //   p = distfun_ncfcdf(x,v1,v2,delta)
    //   p = distfun_ncfcdf(x,v1,v2,delta,lowertail)
    //
    // Parameters
    //   x : a matrix of doubles. x is real and x>=0.
    //   v1 : a matrix of doubles, numerator degrees of freedom, v1>0 (can be non integer). 
    //   v2 : a matrix of doubles, denominator degrees of freedom, v2>0 (can be non integer). 
    //   delta : a matrix of doubles, the noncentrality parameter, delta>=0
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   p : a matrix of doubles, the probability.
    //
    // Description
    //   Computes the cumulative distribution function of 
    //   the Noncentral F distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // <emphasis>Caution</emphasis>
    // This distribution is known to have inferior accuracy in 
    // some cases.
    //
    // Examples
    // // Test with x, v1, v2 scalar
    // computed = distfun_ncfcdf(5,4,12,0.3)
    // expected = 0.98319765219863320
    //
    // // Plot the function
    // h=scf();
    // x = linspace(0,15,1000);
    // p1 = distfun_ncfcdf(x,10,20,0);
    // p2 = distfun_ncfcdf(x,10,20,1);
    // p3 = distfun_ncfcdf(x,10,20,5);
    // p4 = distfun_ncfcdf(x,10,20,10);
    // p5 = distfun_ncfcdf(x,10,20,40);
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
    // xtitle("Noncentral F CDF","x","$P(X\leq x)$");
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Noncentral_F-distribution
    // http://www.boost.org/doc/libs/1_55_0/libs/math/doc/html/math_toolkit/dist_ref/dists/nc_f_dist.html
    // 
    // Authors
    // Copyright (C) 2014 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_ncfcdf",rhs,4:5)
    apifun_checklhs("distfun_ncfcdf",lhs,0:1)

    x = varargin(1)
    v1 = varargin(2)
    v2=varargin(3)
    delta=varargin(4)
    lowertail = apifun_argindefault(varargin,5,%t)
    //
    // Check type
    apifun_checktype("distfun_ncfcdf",x,"x",1,"constant")
    apifun_checktype("distfun_ncfcdf",v1,"v1",2,"constant")
    apifun_checktype("distfun_ncfcdf",v2,"v2",3,"constant")
    apifun_checktype("distfun_ncfcdf",delta,"delta",4,"constant")
    apifun_checktype("distfun_ncfcdf",lowertail,"lowertail",5,"boolean")
    //
    // Check size   
    apifun_checkscalar("distfun_ncfcdf",lowertail,"lowertail",5)
    //
    // Check content
    apifun_checkgreq("distfun_ncfcdf",x,"x",1,0)
    tiniest=number_properties("tiniest")
    apifun_checkgreq("distfun_ncfcdf",v1,"v1",2,tiniest)
    apifun_checkgreq("distfun_ncfcdf",v2,"v2",3,tiniest)
    apifun_checkgreq("distfun_ncfcdf",delta,"delta",4,0.)

    [x,v1,v2,delta] = apifun_expandvar(x,v1,v2,delta)
    p = distfun_cdfncf(x,v1,v2,delta,lowertail)
endfunction

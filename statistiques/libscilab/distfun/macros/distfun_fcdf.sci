// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function p = distfun_fcdf(varargin)
    // F-distribution CDF
    //
    // Calling Sequence
    //   p = distfun_fcdf(x,v1,v2)
    //   p = distfun_fcdf(x,v1,v2,lowertail)
    //
    // Parameters
    //   x : a matrix of doubles. x is real and x>=0.
    //   v1 : a matrix of doubles, numerator degrees of freedom, v1>0 (can be non integer). 
    //   v2 : a matrix of doubles, denominator degrees of freedom, v2>0 (can be non integer). 
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   p : a matrix of doubles, the probability.
    //
    // Description
    //   Computes the cumulative distribution function of 
    //   the f distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Examples
    // // Test with x, v1, v2 scalar
    // computed = distfun_fcdf(2,1,6)
    // expected = 7.929687500000000000D-01
    //
    // // Test with x expanded, v1 and v2 scalar
    // computed = distfun_fcdf([2 5],1,6)
    // expected = [
    // 7.929687500000000000D-01 
    // 9.332931980379590708D-01
    // ]'
    //
    // // Test with x,v1,v2 expanded
    // computed = distfun_fcdf(2:6,1:5,6:10)
    // expected = [
    // 7.929687500000000000D-01    
    // 8.854377836609319541D-01 
    // 9.481063231515756140D-01    
    // 9.787944634589056392D-01 
    // 9.919248908050191105D-01
    // ]'
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
    // xtitle("F CDF","x","$P(X\leq x)$");
    //
    //Bibliography
    // http://en.wikipedia.org/wiki/F-distribution
    // 
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
    // Copyright (C) 2012 - Michael Baudin
    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_fcdf",rhs,3:4)
    apifun_checklhs("distfun_fcdf",lhs,0:1)
    
    x = varargin(1)
    v1 = varargin(2)
    v2=varargin(3)
    lowertail = apifun_argindefault(varargin,4,%t)
    //
    // Check type
    apifun_checktype("distfun_fcdf",x,"x",1,"constant")
    apifun_checktype("distfun_fcdf",v1,"v1",2,"constant")
    apifun_checktype("distfun_fcdf",v2,"v2",3,"constant")
    apifun_checktype("distfun_fcdf",lowertail,"lowertail",4,"boolean")
    //
    // Check size   
    apifun_checkscalar("distfun_fcdf",lowertail,"lowertail",4)
    //
    // Check content
    apifun_checkgreq("distfun_fcdf",x,"x",1,0)
    tiniest=number_properties("tiniest")
    apifun_checkgreq("distfun_fcdf",v1,"v1",2,tiniest)
    apifun_checkgreq("distfun_fcdf",v2,"v2",3,tiniest)
    
    [x,v1,v2] = apifun_expandvar(x,v1,v2)
    p = distfun_cdff(x,v1,v2,lowertail)
endfunction

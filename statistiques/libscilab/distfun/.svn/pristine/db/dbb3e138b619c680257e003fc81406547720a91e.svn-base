// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_tinv(varargin)
    // T Inverse CDF
    //
    // Calling Sequence
    //   x = distfun_tinv ( p , v )
    //   x = distfun_tinv ( p , v , lowertail )
    //
    // Parameters
    //   p : a matrix of doubles, the probability. Must be in the range [0,1].
    //   v : a matrix of doubles, the number of degrees of freedom, v>0. 
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   x : a matrix of doubles, the outcome
    //
    // Description
    // Computes the Inverse T cumulative probability distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles of the same size as the other input arguments.
    //
    // Examples
    // x = distfun_tinv(0.999,5) // 5.8934295
    //
    // // With extreme values of p
    // x = distfun_tinv(0,5) // x=-Inf
    // x = distfun_tinv(1,5) // x=+Inf
    //
    // // With lower tail
    // x = distfun_tinv(1.e-20,5,%f) // 15683.925
    //
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
    // Copyright (C) 2012 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_tinv",rhs,2:3)
    apifun_checklhs("distfun_tinv",lhs,0:1)
    //
    p = varargin(1)
    v = varargin(2)
    lowertail = apifun_argindefault(varargin,3,%t)
    //
    // Check type
    apifun_checktype("distfun_tinv",p,"p",1,"constant")
    apifun_checktype("distfun_tinv",v,"v",2,"constant")
    apifun_checktype("distfun_tinv",lowertail,"lowertail",3,"boolean")
    //
    // Check size
    apifun_checkscalar("distfun_tinv",lowertail,"lowertail",3)
    //
    // Check content
    apifun_checkrange( "distfun_tinv" , p , "p" , 1 , 0, 1 )
    tiny=number_properties("tiny") 
    apifun_checkgreq("distfun_tinv",v,"v",2,tiny)

    [p,v] = apifun_expandvar(p,v)
    x = distfun_invt(p,v,lowertail)
endfunction

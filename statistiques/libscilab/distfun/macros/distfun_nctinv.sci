// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_nctinv(varargin)
    // Noncentral T Inverse CDF
    //
    // Calling Sequence
    //   x = distfun_nctinv ( p , v , delta )
    //   x = distfun_nctinv ( p , v , delta , lowertail )
    //
    // Parameters
    //   p : a matrix of doubles, the probability. Must be in the range [0,1].
    //   v : a matrix of doubles, the number of degrees of freedom, v>0. 
    //   delta : a matrix of doubles, the noncentrality parameter, delta is real
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   x : a matrix of doubles, the outcome
    //
    // Description
    // Computes the Inverse Noncentral T cumulative probability distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles of the same size as the other input arguments.
    //
    // <emphasis>Caution</emphasis>
    // This distribution is known to have inferior accuracy in 
    // some cases.
    //
    // Examples
    // x = distfun_nctinv(0.7,5,10)
    // expected=8.9885923
    //
    // Authors
    // Copyright (C) 2014 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_nctinv",rhs,3:4)
    apifun_checklhs("distfun_nctinv",lhs,0:1)
    //
    p = varargin(1)
    v = varargin(2)
    delta = varargin(3)
    lowertail = apifun_argindefault(varargin,4,%t)
    //
    // Check type
    apifun_checktype("distfun_nctinv",p,"p",1,"constant")
    apifun_checktype("distfun_nctinv",v,"v",2,"constant")
    apifun_checktype("distfun_nctinv",delta,"delta",3,"constant")
    apifun_checktype("distfun_nctinv",lowertail,"lowertail",4,"boolean")
    //
    // Check size
    apifun_checkscalar("distfun_nctinv",lowertail,"lowertail",4)
    //
    // Check content
    apifun_checkrange( "distfun_nctinv" , p , "p" , 1 , 0, 1 )
    tiny=number_properties("tiny") 
    apifun_checkgreq("distfun_nctinv",v,"v",2,tiny)

    [p,v,delta] = apifun_expandvar(p,v,delta)
    x = distfun_invnct(p,v,delta,lowertail)
endfunction

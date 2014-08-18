// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [M,V]  = distfun_tstat(varargin)
    // T  mean and variance
    //
    // Calling Sequence
    //   M = distfun_tstat ( v )
    //   [M,V] = distfun_tstat ( v )
    //
    // Parameters
    //   v : a matrix of doubles, the number of degrees of freedom, v>0. 
    //   M : a matrix of doubles, the mean
    //   V : a matrix of doubles, the variance
    //
    // Description
    //   Computes statistics from the T distribution.
    //
    // The mean of the T distribution is
    //
    //<latex>
    //\begin{eqnarray}
    // M=v
    //\end{eqnarray}
    //</latex>
    //
    // if v > 1. M is undefined if 0 < v <= 1.
    //
    // The variance of the T distribution is
    //
    //<latex>
    //\begin{eqnarray}
    // V=\frac{v}{v-2}
    //\end{eqnarray}
    //</latex>
    //
    // if v > 2. 
    // We have V=Inf if 1 < v <= 2. V is undefined if 0 < v <= 1.
    //
    //   Any scalar input argument is expanded to a matrix of 
    //   doubles of the same size as the other input arguments.
    //
    // Examples
    // [M,V] = distfun_tstat ( [0.5 1 1.5 2 2.3 4] )
    // me=[%nan %nan 0. 0. 0. 0.]
    // ve=[%nan %nan %inf %inf 2.3/0.3 2]
    //
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
    // Copyright (C) 2012 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_tstat",rhs,1)
    apifun_checklhs("distfun_tstat",lhs,1:2)
    //
    v = varargin(1)
    //
    // Check type
    apifun_checktype("distfun_tstat",v,"v",1,"constant")

    // Check content
    tiny=number_properties("tiny") 
    apifun_checkgreq("distfun_tstat",v,"v",1,tiny)

    [v] = apifun_expandvar(v)

    M=ones(v)*%nan
    i=find(v>1)
    Z=zeros(v)
    M(i)=Z(i)

    V=ones(v)*%nan
    i=find( v > 1 & v<=2 )
    I=ones(v)*%inf
    V(i)=I(i)
    i=find( v > 2 )
    V(i) = v(i) ./ (v(i)-2)

endfunction

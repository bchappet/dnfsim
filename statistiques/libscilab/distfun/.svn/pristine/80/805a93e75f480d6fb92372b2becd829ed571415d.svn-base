// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [M,V]  = distfun_nctstat(varargin)
    // Noncentral T  mean and variance
    //
    // Calling Sequence
    //   M = distfun_nctstat ( v , delta )
    //   [M,V] = distfun_nctstat ( v , delta )
    //
    // Parameters
    //   v : a matrix of doubles, the number of degrees of freedom, v>0. 
    //   delta : a matrix of doubles, the noncentrality parameter, delta is real
    //   M : a matrix of doubles, the mean
    //   V : a matrix of doubles, the variance
    //
    // Description
    //   Computes statistics from the Noncentral T distribution.
    //
    // The mean of the Noncentral T distribution is
    //
    //<latex>
    //\begin{eqnarray}
    // M=\delta \sqrt{v/2} \frac{\Gamma((v-1)/2)}{\Gamma(v/2)}
    //\end{eqnarray}
    //</latex>
    //
    // if v > 1. M is undefined if 0 < v <= 1.
    //
    // The variance of the Noncentral T distribution is
    //
    //<latex>
    //\begin{eqnarray}
    // V=\frac{v(1+\delta^2)}{v-2} - M^2
    //\end{eqnarray}
    //</latex>
    //
    // if v > 2. 
    // V is undefined if 0 < v <= 2.
    //
    //   Any scalar input argument is expanded to a matrix of 
    //   doubles of the same size as the other input arguments.
    //
    // Examples
    // delta=10;
    // v = [0.5 1 1.5 2 2.3 4];
    // [M,V]=distfun_nctstat(v,delta)
    // me=[%nan %nan %nan %nan 521.0145  44.920367]
    // ve=[%nan %nan 25.622878  17.724539  15.915993  12.533141]
    //
    // Authors
    // Copyright (C) 2014 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_nctstat",rhs,2)
    apifun_checklhs("distfun_nctstat",lhs,1:2)
    //
    v = varargin(1)
    delta = varargin(2)
    //
    // Check type
    apifun_checktype("distfun_nctstat",v,"v",1,"constant")
    apifun_checktype("distfun_nctstat",delta,"delta",2,"constant")

    // Check content
    tiny=number_properties("tiny") 
    apifun_checkgreq("distfun_nctstat",v,"v",1,tiny)

    [v,delta] = apifun_expandvar(v,delta)

    // Mean : Nan for v<=1
    M=%nan*ones(v)
    // Mean : general case
    i=find( v > 1)
    M(i)=delta(i).*sqrt(v(i)/2).*gamma((v(i)-1)/2)./gamma(v(i)/2)
    
    // Variance : Nan for v<=2
    V=%nan*ones(v)
    // Variance : general case
    i=find( v > 2)
    V(i)=v(i).*(1+delta(i).^2)./(v(i)-2)-M(i).^2
endfunction

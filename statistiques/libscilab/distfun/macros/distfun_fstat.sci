// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [M,V]  = distfun_fstat(varargin)
    //  F-Distribution mean and variance
    //
    // Calling Sequence
    //   M = distfun_fstat(v1,v2)
    //   [M,V] = distfun_fstat(v1,v2)
    //
    // Parameters
    //   v1 : a matrix of doubles, numerator degrees of freedom, v1>0 (can be non integer). 
    //   v2 : a matrix of doubles, denominator degrees of freedom, v2>0 (can be non integer).
    //   M : a matrix of doubles, the mean
    //   V : a matrix of doubles, the variance
    //
    // Description
    //   Computes statistics from the F-distribution.
    //
    //   Any scalar input argument is expanded to a matrix of 
    //   doubles of the same size as the other input arguments.
    //
    // The mean of the F distribution is
    //
    //<latex>
    //\begin{eqnarray}
    // M &=& \frac{v_2}{v_2-2}
    //\end{eqnarray}
    //</latex>
    //
    // if v2>0, and M is set to %nan otherwise.
    //
    // The variance of the F distribution is
    //
    //<latex>
    //\begin{eqnarray}
    // V &=& \frac{2 v_2^2 (v_1+v_2-2)}{v_1(v_2-2)^2(v_2-4)}
    //\end{eqnarray}
    //</latex>
    //
    // if v2>4, and V is set to %nan otherwise.
    //
    // Examples
    // // Test with scalar v1 and v2
    // [M,V] = distfun_fstat(7,5)
    // Me = 1.666666666666666741D+00
    // Ve = 7.936507936507936734D+00
    // 
    // // Test with expanded v1 and v2
    // [M,V] = distfun_fstat(1:5,1:5)
    // Me = [%nan  %nan  3.0000  2.0000  1.6667]
    // Ve = [%nan  %nan  %nan  %nan  8.8889]
    //
    // Bibliography
    //http://en.wikipedia.org/wiki/F-distribution
    // 
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
    // Copyright (C) 2012 - Michael Baudin
    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_fstat",rhs,2)
    apifun_checklhs("distfun_fstat",lhs,1:2)

    v1 = varargin(1)
    v2 = varargin(2)

    //
    // Check type
    apifun_checktype("distfun_fstat",v1,"v1",1,"constant")
    apifun_checktype("distfun_fstat",v2,"v2",2,"constant")

    // Check content 
    tiniest=number_properties("tiniest")
    apifun_checkgreq("distfun_fstat",v1,"v1",1,tiniest)
    apifun_checkgreq("distfun_fstat",v2,"v2",2,tiniest)

    [v1,v2] = apifun_expandvar(v1,v2)

    M=ones(v1)*%nan
    i=find(v2>2)
    M(i)=v2(i)./(v2(i)-2)

    V=ones(v1)*%nan
    i=find(v2>4)
    V(i)=(2.*v2(i).^2.*(v1(i)+v2(i)-2))./(v1(i).*(v2(i)-2).^2.*(v2(i)-4))
endfunction

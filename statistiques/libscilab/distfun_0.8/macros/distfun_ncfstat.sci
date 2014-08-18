// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [M,V]  = distfun_ncfstat(varargin)
    //  Noncentral F-Distribution mean and variance
    //
    // Calling Sequence
    //   M = distfun_ncfstat(v1,v2,delta)
    //   [M,V] = distfun_ncfstat(v1,v2,delta)
    //
    // Parameters
    //   v1 : a matrix of doubles, numerator degrees of freedom, v1>0 (can be non integer). 
    //   v2 : a matrix of doubles, denominator degrees of freedom, v2>0 (can be non integer).
    //   delta : a matrix of doubles, the noncentrality parameter, delta>=0
    //   M : a matrix of doubles, the mean
    //   V : a matrix of doubles, the variance
    //
    // Description
    //   Computes statistics from the Noncentral F-distribution.
    //
    //   Any scalar input argument is expanded to a matrix of 
    //   doubles of the same size as the other input arguments.
    //
    // The mean of the Noncentral F distribution is
    //
    //<latex>
    //\begin{eqnarray}
    // M &=& \frac{v_2(v_1+\delta)}{v_1(v_2-2)}
    //\end{eqnarray}
    //</latex>
    //
    // if v2>2, and M is set to %nan otherwise.
    //
    // The variance of the Noncentral F distribution is
    //
    //<latex>
    //\begin{eqnarray}
    // V &=& 2 \frac{(v_1+\delta)^2+(v_1+2\delta)(v_2-2)}{(v_2-2)^2(v_2-4)} \left(\frac{v_2}{v_1}\right)^2
    //\end{eqnarray}
    //</latex>
    //
    // if v2>4, and V is set to %nan otherwise.
    //
    // Examples
    // // Test with scalar v1 and v2
    // [M,V] = distfun_ncfstat(4,12,0.3)
    // Me = 1.29
    // Ve = 1.451025
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Noncentral_F-distribution
    // 
    // Authors
    // Copyright (C) 2014 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_ncfstat",rhs,3)
    apifun_checklhs("distfun_ncfstat",lhs,1:2)

    v1 = varargin(1)
    v2 = varargin(2)
    delta=varargin(3)
    //
    // Check type
    apifun_checktype("distfun_ncfstat",v1,"v1",1,"constant")
    apifun_checktype("distfun_ncfstat",v2,"v2",2,"constant")
    apifun_checktype("distfun_ncfstat",delta,"delta",3,"constant")

    // Check content 
    tiniest=number_properties("tiniest")
    apifun_checkgreq("distfun_ncfstat",v1,"v1",1,tiniest)
    apifun_checkgreq("distfun_ncfstat",v2,"v2",2,tiniest)
    apifun_checkgreq("distfun_ncfstat",delta,"delta",3,0.)

    [v1,v2,delta] = apifun_expandvar(v1,v2,delta)

    M=ones(v1)*%nan
    i=find(v2>2)
    M(i)=v2(i).*(v1(i)+delta(i))./(v2(i)-2)./v1(i)

    V=ones(v1)*%nan
    i=find(v2>4)
    num=ones(v1)
    den=ones(v1)
    sqa=ones(v1)
    num(i)=(v1(i)+delta(i)).^2+(v1(i)+2*delta(i)).*(v2(i)-2)
    den(i)=(v2(i)-2).^2 .*(v2(i)-4)
    sqa(i)=(v2(i)./v1(i)).^2
    V(i)=2*num(i)./den(i).*sqa(i)
endfunction

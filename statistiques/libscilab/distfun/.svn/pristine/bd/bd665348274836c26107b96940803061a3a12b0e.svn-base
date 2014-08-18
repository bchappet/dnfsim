// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [M,V]  = distfun_ksstat(varargin)
    //  Kolmogorov-Smirnov mean and variance
    //
    // Calling Sequence
    //   M = distfun_ksstat(N)
    //   [M,V] = distfun_ksstat(N)
    //
    // Parameters
    //   N : a matrix of doubles , the number of observations. N belongs to the set {1,2,3,4,.......,2147483647}
    //   M : a matrix of doubles, the mean
    //   V : a matrix of doubles, the variance
    //
    // Description
    //   Computes statistics from the Kolmogorov-Smirnov distribution.
    //
    //   Any scalar input argument is expanded to a matrix of 
    //   doubles of the same size as the other input arguments.
    //
    // Only the asymptotic mean and variance is implemented in 
    // this function, i.e. the mean and variance computed 
    // by this function is more and more accurate when 
    // n increases.
    //
    // The asymptotic mean and variance of the Kolmogorov-Smirnov distribution is
    //
    //<latex>
    //\begin{eqnarray}
    // \lim_{N\rightarrow \infty} M &=& \frac{\sqrt{\pi/2}\log(2)}{\sqrt{N}} \\
    // \lim_{N\rightarrow \infty} V &=& \frac{\pi^2}{12N}-M^2
    //\end{eqnarray}
    //</latex>
    //
    // Examples
    // [M,V] = distfun_ksstat(5)
    // Me = 0.3885084
    // Ve = 0.0135546
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Kolmogorov%E2%80%93Smirnov_test
    // 
    // Authors
    // Copyright (C) 2014 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_ksstat",rhs,1)
    apifun_checklhs("distfun_ksstat",lhs,1:2)

    N = varargin(1)
    //
    // Check type
    apifun_checktype("distfun_ksstat",N,"N",1,"constant")

    // Check content 
    tiniest=number_properties("tiniest")
    apifun_checkgreq("distfun_ksstat",N,"N",1,1)
    apifun_checkflint("distfun_ksstat",N,"N",1)
    apifun_checkloweq("distfun_ksstat",N,"N",1,2147483647)

    M = sqrt(%pi/2)*log(2)./sqrt(N)
    V = (%pi**2/12)./N-M.^2
endfunction

// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [M,V] = distfun_wblstat ( a, b )
    // Weibull  mean and variance
    //
    // Calling Sequence
    //   M = distfun_wblstat ( a , b )
    //   [M,V] = distfun_wblstat ( a , b )
    //
    // Parameters
    //   a : a matrix of doubles, the scale parameter, a>0.
    //   b : a matrix of doubles, the shape parameter, b>0.
    //   M : a matrix of doubles, the mean
    //   V : a matrix of doubles, the variance
    //
    // Description
    //   Computes statistics from the Weibull distribution.
    //
    // The mean and variance of the Weibull distribution are
    //
    //<latex>
    //\begin{eqnarray}
    // M &=& a \Gamma\left(1 + \frac{1}{b}\right) \\
    // V &=& a^2 \Gamma\left(1 + \frac{2}{b}\right)-M^2
    //\end{eqnarray}
    //</latex>
    //
    //   Any scalar input argument is expanded to a matrix of 
    //   doubles of the same size as the other input arguments.
    //
    // Examples
    // [M,V]=distfun_wblstat(1:3,(1:3)^-1)
    // me = [1.    4.    18.]';
    // ve = [1.    80.    6156.]';
    //
    // Authors
    //   Copyright (C) 2014 - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_wblstat" , rhs , 2 )
    apifun_checklhs ( "distfun_wblstat" , lhs , 0:2 )
    //
    // Check type
    apifun_checktype ( "distfun_wblstat" , a , "a" , 1 , "constant" )
    apifun_checktype ( "distfun_wblstat" , b , "b" , 2 , "constant" )
    //
    // Check content
    tiny=number_properties("tiny")
    apifun_checkgreq ( "distfun_wblstat" , a , "a" , 1 , tiny )
    apifun_checkgreq ( "distfun_wblstat" , b , "b" , 2 , tiny )  
    //
    [ a , b ] = apifun_expandvar ( a , b )
    //
    M = a .* gamma(1 + 1 ./b)
    V = a.^2 .* gamma(1 + 2 ./b)-M^2
endfunction


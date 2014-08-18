// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [M,V] = distfun_gamstat ( a, b )
    // Gamma  mean and variance
    //
    // Calling Sequence
    //   M = distfun_gamstat ( a , b )
    //   [M,V] = distfun_gamstat ( a , b )
    //
    // Parameters
    //   a : a matrix of doubles, the shape parameter, a>0.
    //   b : a matrix of doubles, the scale parameter, b>0.
    //   M : a matrix of doubles, the mean
    //   V : a matrix of doubles, the variance
    //
    // Description
    //   Computes statistics from the Gamma distribution.
    //
    // The mean and variance of the Gamma distribution are
    //
    //<latex>
    //\begin{eqnarray}
    // M &=& a * b \\
    // V &=& a * b^2
    //\end{eqnarray}
    //</latex>
    //
    //   Any scalar input argument is expanded to a matrix of 
    //   doubles of the same size as the other input arguments.
    //
    // Examples
    // a = 1:6;
    // b = 7:12;
    // [M,V] = distfun_gamstat ( a , b )
    // me = [..
    // 7
    // 16
    // 27
    // 40
    // 55
    // 72
    // ]'
    // ve = [..
    // 49
    // 128
    // 243
    // 400
    // 605
    // 864
    // ]'
    //
    // Authors
    //   Copyright (C) 2012 - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_gamstat" , rhs , 2 )
    apifun_checklhs ( "distfun_gamstat" , lhs , 0:2 )
    //
    // Check type
    apifun_checktype ( "distfun_gamstat" , a , "a" , 1 , "constant" )
    apifun_checktype ( "distfun_gamstat" , b , "b" , 2 , "constant" )
    //
    // Check content
	tiny=number_properties("tiny")
    apifun_checkgreq ( "distfun_gamstat" , a , "a" , 1 , tiny )
    apifun_checkgreq ( "distfun_gamstat" , b , "b" , 2 , tiny )  
    //
    [ a , b ] = apifun_expandvar ( a , b )
    //
    M = a .* b
    V = M .* b
endfunction


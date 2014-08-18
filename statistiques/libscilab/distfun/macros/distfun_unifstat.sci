// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [M,V] = distfun_unifstat ( a, b )
    // Uniform  mean and variance
    //
    // Calling Sequence
    //   M = distfun_unifstat ( a , b )
    //   [M,V] = distfun_unifstat ( a , b )
    //
    // Parameters
    //   a : a matrix of doubles, the lower bound
    //   b : a matrix of doubles, the upper bound (with a<=b)
    //   M : a matrix of doubles, the mean
    //   V : a matrix of doubles, the variance
    //
    // Description
    //   Computes statistics from the Uniform distribution.
    //
    // The mean and variance of the Uniform distribution are
    //
    //<latex>
    //\begin{eqnarray}
    // M &=& \frac{a+b}{2} \\
    // V &=& \frac{(b-a)^2}{12}
    //\end{eqnarray}
    //</latex>
    //
    //   Any scalar input argument is expanded to a matrix of 
    //   doubles of the same size as the other input arguments.
    //
    // Examples
    //
    // a = 1:6;
    // b = 2:7;
    // [M,V] = distfun_unifstat ( a , b )
    // me = [1.5    2.5    3.5    4.5    5.5    6.5];
    // ve = [0.0833333    0.0833333    0.0833333    0.0833333    0.0833333    0.0833333];
    //
    // Authors
    //   Copyright (C) 2012 - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_unifstat" , rhs , 2 )
    apifun_checklhs ( "distfun_unifstat" , lhs , 0:2 )
    //
    // Check type
    apifun_checktype ( "distfun_unifstat" , a , "a" , 1 , "constant" )
    apifun_checktype ( "distfun_unifstat" , b , "b" , 2 , "constant" )
    //
    [ a , b ] = apifun_expandvar ( a , b )
	if ( a == [] ) then
	    M = []
	    V = []
		return
    end
    //
    // Check content
    apifun_checkgreq ( "distfun_unifstat" , b , "b" , 2 , a )  
    //
    M = (a+b) ./ 2
    V = (b-a).^2 ./ 12
endfunction


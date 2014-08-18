// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [M,V] = distfun_unidstat ( N )
    // Uniform Discrete mean and variance
    //
    // Calling Sequence
    //   M = distfun_unidstat ( N )
    //   [M,V] = distfun_unidstat ( N )
    //
    // Parameters
    //   N : a matrix of doubles, the upper bound (with N>=1)
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
    // M &=& \frac{N+1}{2} \\
    // V &=& \frac{N^2-1}{12}
    //\end{eqnarray}
    //</latex>
    //
    //   Any scalar input argument is expanded to a matrix of 
    //   doubles of the same size as the other input arguments.
    //
    // Examples
    // N = 2:7;
    // [M,V] = distfun_unidstat ( N )
    // me = [1.5    2.    2.5    3.    3.5    4.];
    // ve = [0.25    0.6666667    1.25    2.    2.9166667    4.];
    //
    // Authors
    //   Copyright (C) 2014 - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_unidstat" , rhs , 1 )
    apifun_checklhs ( "distfun_unidstat" , lhs , 0:2 )
    //
    // Check type
    apifun_checktype ( "distfun_unidstat" , N , "N" , 1 , "constant" )
    //
    if ( N == [] ) then
        M = []
        V = []
        return
    end
    //
    // Check content
    apifun_checkgreq ( "distfun_unidstat" , N , "N" , 1 , 1 )  
    apifun_checkflint("distfun_unidstat" , N , "N" , 1)
    //
    M=(N+1)./2
    V=(N**2-1)./12
endfunction

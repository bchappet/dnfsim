// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt


function [M,V] = distfun_expstat ( mu )
    // Exponential mean and variance
    //
    // Calling Sequence
    //   [M,V] = distfun_expstat ( mu )
    //
    // Parameters
    //   mu : a matrix of doubles, the average. mu>0
    //   M : a matrix of doubles, the mean
    //   V : a matrix of doubles, the variance
    //
    // Description
    //   This function computes the mean and the variance of the 
    //   Exponential distribution.
    //
    // The mean and variance of the Exponential distribution are
    //
    //<latex>
    //\begin{eqnarray}
    // M &=& \mu \\
    // V &=& \mu^2
    //\end{eqnarray}
    //</latex>
    //
    // Examples
    // [M,V] = distfun_expstat([1 10 100 1000])
    //
    // Authors
    // Copyright (C) 2012 - Michael Baudin
    //
    // Bibliography
    // Wikipedia, Exponential distribution function, http://en.wikipedia.org/wiki/Exponential_distribution

    [lhs, rhs] = argn()
    apifun_checkrhs ( "distfun_expstat" , rhs , 1 )
    apifun_checklhs ( "distfun_expstat" , lhs , 0:2 )
    //
    // Check type
    apifun_checktype ( "distfun_expstat" , mu , "mu" , 1 , "constant" )
    //
    // Check size : OK
    //
    // Check content
    tiny = number_properties("tiny")
    apifun_checkgreq ( "distfun_expstat" , mu , "mu" , 1 , tiny )
    //
    // Proceed ...
    //
    M = mu
    V = mu.^2
endfunction


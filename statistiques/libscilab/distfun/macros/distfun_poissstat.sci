// Copyright (C) 2012 - Prateek Papriwal
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [M,V]  = distfun_poissstat(lambda)
    // Poisson mean and variance
    //
    // Calling Sequence
    //   M = distfun_poissstat(lambda)
    //   [M,V] = distfun_poissstat(lambda)
    //
    // Parameters
    //   lambda : a matrix of doubles, the average rate of occurrence. lambda>0.
    //   M : a matrix of doubles, the mean
    //   V : a matrix of doubles, the variance
    //
    // Description
    //   Computes statistics from the Poisson distribution.
    //
    //   Any scalar input argument is expanded to a matrix of 
    //   doubles of the same size as the other input arguments.
    //
    // The Mean and Variance of the Poisson Distribution are
    //
    //<latex>
    //\begin{eqnarray}
    // M &=& \lambda \\
    // V &=& \lambda
    //\end{eqnarray}
    //</latex>
    // 
    // Examples
    // // Test with expanded lambda
    //[m,v] = distfun_poissstat((1:6))
    //me = [ 1 2 3 4 5 6 ];
    //ve = [ 1 2 3 4 5 6 ];
    //
    // //Accuracy test
    //lambda = [ 11 22 33 ];
    //[M,V] = distfun_poissstat ( lambda )
    //ve = [ 11 22 33 ];
    //me = [ 11 22 33 ];
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Poisson_distribution
    // 
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
    // Copyright (C) 2012 - Michael Baudin
    //
    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_poissstat",rhs,1)
    apifun_checklhs("distfun_poissstat",lhs,1:2)
    //
    // Check type
    apifun_checktype("distfun_poissstat",lambda,"lambda",1,"constant")
    // 
    // Check content
    tiny = number_properties("tiny")
    apifun_checkgreq("distfun_poissstat",lambda,"lambda",1,tiny)

    //
    [lambda] = apifun_expandvar(lambda)
    
    M = lambda 
    V = lambda
    
endfunction
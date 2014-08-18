// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [M,V] = distfun_chi2stat (k)
    // Chi-Squared mean and variance
    //
    // Calling Sequence
    //   M = distfun_chi2stat(k)
    //   [M,V] = distfun_chi2stat(k)
    //
    // Parameters
    //   k : a matrix of doubles, the number of degrees of freedom, k>0 (can be non integer)
    //   M : a matrix of doubles, the mean
    //   V : a matrix of doubles, the variance
    //
    // Description
    //   Computes statistics from the Chi-Squared distribution.
    //
    //   Any scalar input argument is expanded to a matrix of 
    //   doubles of the same size as the other input arguments.
    //
    // The Mean and Variance of the Geometric Distribution are
    //
    //<latex>
    //\begin{eqnarray}
    // M &=& k \\
    // V &=& 2k
    //\end{eqnarray}
    //</latex>
    // 
    // Examples
    // // Test with expanded k
    //[m,v] = distfun_chi2stat((1:6))
    //me = [ 1 2 3 4 5 6];
    //ve = [ 2 4 6 8 10 12];
    //
    // //Accuracy test
    //k=[3 6 9];
    //[M,V] = distfun_chi2stat ( k )
    //ve= [6 12 18];
    //me = [3 6 9];
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Chi-squared_distribution
    //
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
    // Copyright (C) 2012 - Michael Baudin
    //

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_chi2stat",rhs,1)
    apifun_checklhs("distfun_chi2stat",lhs,1:2)
    // Check type
    //
    apifun_checktype("distfun_chi2stat",k,"k",1,"constant")

    //Check content
    // 
    tiny=number_properties("tiny")
    apifun_checkgreq("distfun_chi2stat",k,"k",1,tiny)
    //
    M = k 
    V = 2 .* k
endfunction

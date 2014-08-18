// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [M,V] = distfun_ncx2stat (k,delta)
    // Noncentral Chi-Squared  mean and variance
    //
    // Calling Sequence
    //   M = distfun_ncx2stat(k,delta)
    //   [M,V] = distfun_ncx2stat(k,delta)
    //
    // Parameters
    //   k : a matrix of doubles, the number of degrees of freedom, k>0 (can be non integer)
    //   delta : a matrix of doubles, the noncentrality parameter, delta>=0
    //   M : a matrix of doubles, the mean
    //   V : a matrix of doubles, the variance
    //
    // Description
    //   Computes statistics from the Noncentral Chi-Squared distribution.
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
    // [m,v] = distfun_ncx2stat((1:6))
    // me = [ 1 2 3 4 5 6];
    // ve = [ 2 4 6 8 10 12];
    //
    // //Accuracy test
    // k=[3 6 9];
    // [M,V] = distfun_ncx2stat ( k )
    // ve= [6 12 18];
    // me = [3 6 9];
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Noncentral_chi-squared_distribution
    //
    // Authors
    // Copyright (C) 2014 - Michael Baudin
    //

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_ncx2stat",rhs,2)
    apifun_checklhs("distfun_ncx2stat",lhs,1:2)
    // Check type
    //
    apifun_checktype("distfun_ncx2stat",k,"k",1,"constant")
    apifun_checktype("distfun_ncx2stat",delta,"delta",2,"constant")

    // Check content
    // 
    tiny=number_properties("tiny")
    apifun_checkgreq("distfun_ncx2stat",k,"k",1,tiny)
    apifun_checkgreq("distfun_ncx2stat",delta,"delta",2,0.)
    //
    M = k + delta
    V = 2*(k+2*delta)
endfunction

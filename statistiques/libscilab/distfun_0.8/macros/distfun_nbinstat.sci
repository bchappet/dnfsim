// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYInG, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [M,V] = distfun_nbinstat(R,P)
    // Negative Binomial  mean and variance
    //
    // Calling Sequence
    //   M = distfun_nbinstat(R,P)
    //   [M,V] = distfun_nbinstat(R,P)
    //
    // Parameters
    //   R : a matrix of doubles, the number of successes. R belongs to the set {0,1,2,3,4,.......}
    //   P : a matrix of doubles, the probability of getting success in a Bernoulli trial. P in [0,1].
    //   M : a matrix of doubles, the mean
    //   V : a matrix of doubles, the variance
    //
    // Description
    //   Computes statistics from the Negative Binomial distribution.
    //
    //   Any scalar input argument is expanded to a matrix of 
    //   doubles of the same size as the other input arguments.
    //
    //    The Mean and Variance of the Negative Binomial Distribution is:
    //
    //<latex>
    //\begin{eqnarray}
    // M &=& R \frac{1-P}{P} \\
    // V &=& R \frac{1-P}{P^2}
    //\end{eqnarray}
    //</latex>
    //
    // Examples
    // // Check with expanded R
    // R = [1 2 3 4 5]
    // P = 0.1
    // [M,V] = distfun_nbinstat(R,P)
    // me = [9.    18.    27.    36.    45.]
    // ve = [90.    180.    270.    360.    450.]
    //
    // //Check with expanded P
    // R = 5
    // P = [0.1 0.2 0.3 0.4]
    // [M,V] = distfun_nbinstat(R,P)
    // me = [45.    20.    11.666667    7.5]
    // ve = [450.    100.    38.888889    18.75]
    // 
    // //Check with both R and P expanded
    // R = [1 2 3 4]
    // P = [0.1 0.2 0.3 0.4]
    // [M,V] = distfun_nbinstat(R,P)
    // me = [9.    8.    7.    6.]
    // ve = [90.    40.    23.333333    15.]
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Negative_binomial_distribution
    //
    // Authors
    // Copyright (C) 2014 - Michael Baudin
	
    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_nbinstat",rhs,2)
    apifun_checklhs("distfun_nbinstat",lhs,1:2)
    //
    // Check type
    apifun_checktype("distfun_nbinstat",R,"R",1,"constant")
    apifun_checktype("distfun_nbinstat",P,"P",2,"constant")
    //
    // Check content
    apifun_checkflint("distfun_nbinstat",R,"R",1)
    apifun_checkgreq("distfun_nbinstat",R,"R",1,1)
    apifun_checkrange("distfun_nbinstat",P,"P",2,0.,1.)
    //
    [R,P] = apifun_expandvar(R,P)
    
    Q=1-P
    M=R.*Q./P
    V=R.*Q./P.^2
endfunction

// Copyright (C) 2012 - 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [M,C] = distfun_mnstat(n,P)
    // Multinomial mean and covariance
    //
    // Calling Sequence
    // M = distfun_mnstat(n,P)
    // [M,C] = distfun_mnstat(n,P)
    //
    // Parameters
    // n : a 1-by-1 matrix of doubles, integer value, positive, the number of trials
    // P : a 1-by-k matrix of doubles, positive, sum to 1, the probability of the k categories
    // M : a 1-by-1 matrix of doubles, the mean
    // C : a k-by-k matrix of doubles, the variance-covariance matrix
    //
    // Description
    // Computes statistics from the multinomial distribution. 
    // On input, we assume that sum(P)==1.
    //
    //    The Mean and Covariance matrix of the Multinomial Distribution is:
    //
    //<latex>
    //\begin{eqnarray}
    // M &=& n P \\
    // C_{i,i} &=& n p_i (1-p_i) \\
    // C_{i,j} &=& - n p_i p_j, \quad \textrm{i\neq j}
    //\end{eqnarray}
    //</latex>
    //
    // Examples
    // n = 10
    // P = [0.3 0.4 0.3]
    // [M,C] = distfun_mnstat(n,P)
    // Mexpected = [3 4 3]
    // Cexpected = [
    //    2.1  -1.2  -0.9  
    //   -1.2   2.4  -1.2  
    //   -0.9  -1.2   2.1  
    //]
    //
    // Authors
    // Copyright (C) 2012 - 2014 - Michael Baudin
    //
    // Bibliography
    // Wikipedia, Multinomial distribution function, http://en.wikipedia.org/wiki/Multinomial_distribution

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_mnstat",rhs,2)
    apifun_checklhs("distfun_mnstat",lhs,1:2)
    //
    // Check type
    apifun_checktype("distfun_mnstat",n,"n",1,"constant")
    apifun_checktype("distfun_mnstat",P,"P",2,"constant")
    //
    // Check size
	apifun_checkvecrow("distfun_mnstat",P,"P",2)
    //
    // Check content
    apifun_checkgreq("distfun_mnstat",n,"n",1,1)
    apifun_checkflint("distfun_mnstat",n,"n",1)
    apifun_checkrange("distfun_mnstat",P,"P",2,0,1)
    apifun_checkrange("distfun_mnstat",sum(P),"sum(P)",2,1-10*%eps,1)
    //

    M = n*P
    k = size(P,"*")
    C = zeros(k,k)
    for i = 1 : k
        C(i,i) = n*P(i)*(1-P(i))
        for j = i+1 : k
            C(i,j) = -n*P(i)*P(j)
            C(j,i) = C(i,j)
        end
    end
endfunction

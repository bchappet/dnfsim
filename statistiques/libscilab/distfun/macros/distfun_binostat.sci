// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2012 - Prateek Papriwal
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYInG, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [M,V] = distfun_binostat(N,pr)
    // Binomial  mean and variance
    //
    // Calling Sequence
    //   M = distfun_binostat(N,pr)
    //   [M,V] = distfun_binostat(N,pr)
    //
    // Parameters
    //   pr : a matrix of doubles, the probability of getting success in a Bernoulli trial. pr in [0,1].
    //   N : a matrix of doubles , the total number of binomial trials . N belongs to the set {1,2,3,4,.......}
    //   M : a matrix of doubles, the mean
    //   V : a matrix of doubles, the variance
    //
    // Description
    //   Computes statistics from the Binomial distribution.
    //
    //   Any scalar input argument is expanded to a matrix of 
    //   doubles of the same size as the other input arguments.
    //
    //    The Mean and Variance of the Binomial Distribution is:
    //
    //<latex>
    //\begin{eqnarray}
    // M &=& N p_r \\
    // V &=& N p_r (1-p_r)
    //\end{eqnarray}
    //</latex>
    //
    // Examples
    //
    // // Check with expanded N
    // N = [10 100 1000 10000]
    // pr = 0.1
    // [m,v] = distfun_binostat(N,pr)
    // me = [1 10 100 1000]
    // ve = [0.9 9 90 900]
    //
    // //Check with expanded pr
    // N = 100
    // pr = [0.1 0.2 0.3 0.4]
    // [m,v] = distfun_binostat(N,pr)
    // me = [10 20 30 40]
    // ve = [9 16 21 24]
    // 
    // //Check with both N and pr expanded
    // N = [10 100 1000 10000]
    // pr = [0.1 0.2 0.3 0.4]
    // [m,v] = distfun_binostat(N,pr)
    // me = [1 20 300 4000]
    // ve = [0.9 16 210 2400]
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Binomial_distribution
    //
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
	// Copyright (C) 2012 - Michael Baudin
	
    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_binostat",rhs,2)
    apifun_checklhs("distfun_binostat",lhs,1:2)
    //
    // Check type
    apifun_checktype("distfun_binostat",N,"N",1,"constant")
    apifun_checktype("distfun_binostat",pr,"pr",2,"constant")
    //
    // Check content
    apifun_checkgreq("distfun_binostat",N,"N",1,1)
    apifun_checkflint("distfun_binostat",N,"N",1)
    apifun_checkrange("distfun_binostat",pr,"pr",2,0,1)
    //
    [N,pr] = apifun_expandvar(N,pr)
    
    M = N .* pr
    V = N .* pr .* (1-pr)
endfunction

// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should haV receiVd as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [MN,V] = distfun_hygestat(M,k,N)
    // Hypergeometric  mean and variance
    //
    // Calling Sequence
    //   MN = distfun_hygestat(M,k,N)
    //   [MN,V] = distfun_hygestat(M,k,N)
    //
    // Parameters
    //   M : a matrix of doubles, the size of total population. M belongs to the set {0,1,2,3........}
    //   k : a matrix of doubles, the number of success states in the population. k belongs to the set {0,1,2,3,.......M-1,M}
    //   N : a matrix of doubles, the total number of draws in the experiment. N belongs to the set {0,1,2,3.......M-1,M}
    //
    //   MN : a matrix of doubles, the mean
    //   V : a matrix of doubles, the variance
    //
    // Description
    //   Computes statistics from the Hypergeometric distribution.
    //
    //   Any scalar input argument is expanded to a matrix of 
    //   doubles of the same size as the other input arguments.
    //
    // The Mean and Variance of the Hypergeometric Distribution are
    //
    // <latex>
    // \begin{eqnarray}
    // MN &=& N \frac{k}{M} \\
    // V &=& N \frac{k}{M} \left(1-\frac{k}{M}\right) \frac{M-N}{M-1}
    // \end{eqnarray}
    // </latex>
	//
	// The expression (M-N)/(M-1) is called the correction factor. 
	// This comes from the comparison with the variance of the binomial 
	// distribution with parameters N and pr=k/M.
	//
    // Examples
    // //Accuracy test
    // M = [50 60 70]
    // N = [20 30 40]
    // k = [10 25 35]
    // [MN,V] = distfun_hygestat ( M,N,k )
    // V = [1.9591837 3.7076271 4.3478261]
    // MN = [4 12.5 20]
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Hypergeometric_distribution
    //
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
	// Copyright (C) 2012 - Michael Baudin
    //
    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_hygestat",rhs,3)
    apifun_checklhs("distfun_hygestat",lhs,1:2)
    
    // Check type
    //
    apifun_checktype("distfun_hygestat",M,"M",1,"constant")
    apifun_checktype("distfun_hygestat",k,"k",2,"constant")
    apifun_checktype("distfun_hygestat",N,"N",3,"constant")
    
    [M,k,N] = apifun_expandvar(M,k,N)
    if (M == []) then
        MN=[]
        V=[]
        return
    end 
    //
    // Check content
    apifun_checkgreq("distfun_hygestat",M,"M",1,N)
    apifun_checkgreq("distfun_hygestat",k,"k",2,0)
    apifun_checkloweq("distfun_hygestat",k,"k",2,M)
    apifun_checkgreq("distfun_hygestat",N,"N",3,0)
    apifun_checkloweq("distfun_hygestat",N,"N",3,M)
    //
    //
    MN = N .* k ./ M
    V = N .* k .* (M-k) .* (M -N) ./ M ./ M ./ (M - 1)
endfunction

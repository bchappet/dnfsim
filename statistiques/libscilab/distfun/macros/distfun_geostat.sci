// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [M,V] = distfun_geostat (pr)
    // Geometric  mean and variance
    //
    // Calling Sequence
    //   M = distfun_geostat(pr)
    //   [M,V] = distfun_geostat(pr)
    //
    // Parameters
    //   pr : a matrix of doubles, the probability of getting success in a Bernoulli trial. pr in (0,1].
    //   M : a matrix of doubles, the mean
    //   V : a matrix of doubles, the variance
    //
    // Description
    //   Computes statistics from the Geometric distribution.
    //
    //   Any scalar input argument is expanded to a matrix of 
    //   doubles of the same size as the other input arguments.
    //
    // The Mean and Variance of the Geometric Distribution are
    //
    //<latex>
    //\begin{eqnarray}
    // M &=& \frac{1-pr}{pr} \\
    // V &=& \frac{1-pr}{pr^2}
    //\end{eqnarray}
    //</latex>
    // 
    // Examples
    // // Test with expanded pr
    //[m,v] = distfun_geostat(1 ./(1:6))
    //me = [ 0  1.0000  2.0000  3.0000  4.0000  5.0000];
    //ve = [ 0  2.0000  6.0000  12.0000  20.0000  30.0000];
    //
    // //Accuracy test
    //pr=[0.11 0.22 0.33];
    //[M,V] = distfun_geostat ( pr )
    //ve= [73.553719 16.115702 6.1524334 ];
    //me = [8.0909091 3.5454545 2.030303 ];
    //
    // Bibliography
    //http://en.wikipedia.org/wiki/Geometric_distribution
    // 
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
    // Copyright (C) 2012 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_geostat",rhs,1)
    apifun_checklhs("distfun_geostat",lhs,1:2)
    //
    // Check type
    apifun_checktype("distfun_geostat",pr,"pr",1,"constant")
    // 
    // Check content
	tiniest=number_properties("tiniest")
    apifun_checkrange("distfun_geostat",pr,"pr",1,tiniest,1)

    //
    [pr] = apifun_expandvar(pr)

    M = (1-pr) ./pr
    V = (1-pr) ./ pr.^2
endfunction

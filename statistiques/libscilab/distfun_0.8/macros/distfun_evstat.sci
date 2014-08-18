// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [M,V] = distfun_evstat ( mu, sigma )
    // Extreme value (Gumbel) mean and variance
    //
    // Calling Sequence
    //   M = distfun_evstat ( mu , sigma )
    //   [M,V] = distfun_evstat ( mu , sigma )
    //
    // Parameters
    //   mu : a matrix of doubles, the average
    //   sigma : a matrix of doubles, the standard deviation. sigma>0.
    //   M : a matrix of doubles, the mean
    //   V : a matrix of doubles, the variance
    //
    // Description
    //   Computes statistics from the Extreme value (Gumbel) distribution.
    //   This is the minimum Gumbel distribution.
    //
    // The mean and variance of the Extreme value (Gumbel) distribution are
    //
    //<latex>
    //\begin{eqnarray}
    // M &=& \mu+\sigma^2 \\
    // V &=& \frac{\pi^2\sigma^2}{6}
    //\end{eqnarray}
    //</latex>
    //
    // where
    //
    //<screen>
    //z=dlgamma(1)
    //</screen>
    //
    //   Any scalar input argument is expanded to a matrix of 
    //   doubles of the same size as the other input arguments.
    //
    // Examples
    // [M,V]=distfun_evstat(5,2)
    // me = 3.8455687;
    // ve = 6.5797363;
    //
    // mu=0.5;
    // sigma=2.0;
    // [M,V]=distfun_evstat(mu,sigma);
    // R=distfun_evrnd(mu,sigma,1000,1000);
    // m=mean(R,"r");
    // v=variance(R,"r");
    // // 
    // scf();
    // xtitle("Estimate of mean","Mean","Frequency")
    // histplot(11,m);
    // plot([M,M],[0,5])
    // legend(["Data","Exact"]);
    // // 
    // scf();
    // xtitle("Estimate of variance","Variance","Frequency")
    // histplot(11,v);
    // plot([V,V],[0,1])
    // legend(["Data","Exact"]);
    //
    // Authors
    //   Copyright (C) 2013 - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_evstat" , rhs , 2 )
    apifun_checklhs ( "distfun_evstat" , lhs , 0:2 )
    //
    // Check type
    apifun_checktype ( "distfun_evstat" , mu , "mu" , 1 , "constant" )
    apifun_checktype ( "distfun_evstat" , sigma , "sigma" , 2 , "constant" )
    //
    // Check content
    tiny = number_properties("tiny")
    apifun_checkgreq ( "distfun_evstat" , sigma , "sigma" , 2 , tiny )
    //
    [ mu , sigma ] = apifun_expandvar ( mu , sigma )
    //
    z=dlgamma(1)
    M=mu+sigma*z
    V=%pi^2*sigma^2/6
endfunction


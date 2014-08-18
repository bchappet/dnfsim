// Copyright (C) 2011 - 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [M,V] = distfun_lognstat ( mu, sigma )
  // LogNormal mean and variance
  //
  // Calling Sequence
  //   M = distfun_lognstat ( mu , sigma )
  //   [M,V] = distfun_lognstat ( mu , sigma )
  //
  // Parameters
  //   mu : a matrix of doubles, the mean of the underlying normal variable. 
  //   sigma : a matrix of doubles, the standard deviation of the underlying normal variable. sigma>0.
  //   M : a matrix of doubles, the mean
  //   V : a matrix of doubles, the variance
  //
  // Description
  //   Computes statistics from the LogNormal distribution.
  //
  // The mean and variance of the lognormal distribution are
  //
  //<latex>
  //\begin{eqnarray}
  // M &=& \exp\left(\mu+\sigma^2/2\right) \\
  // V &=& \left(\exp\left(\sigma^2\right)-1\right) \exp\left(2\mu+\sigma^2\right)
  //\end{eqnarray}
  //</latex>
  //
  // A lognormal distribution with mean m and 
  // variance v has parameters
  //
  // <screen>
  // mu = log(M) - 0.5 * log(1+V./(M.^2))
  // sigma = sqrt(log(1+V./(M.^2)))
  // </screen>  
  //
  //   Any scalar input argument is expanded to a matrix of doubles of the same size as the other input arguments.
  //
  // Examples
  // mu = 1:6;
  // sigma = (1:6)^-1;
  // [M,V] = distfun_lognstat ( mu , sigma )
  // // See if we can recover the original parameters:
  // mu = log(M) - 0.5 * log(1+V./(M.^2))
  // sigma = sqrt(log(1+V./(M.^2)))
  //
  // Authors
  //   Copyright (C) 2011 - 2012 - Michael Baudin

  [lhs,rhs]=argn()
  apifun_checkrhs ( "distfun_lognstat" , rhs , 2 )
  apifun_checklhs ( "distfun_lognstat" , lhs , 0:2 )
  //
  // Check type
  apifun_checktype ( "distfun_lognstat" , mu , "mu" , 1 , "constant" )
  apifun_checktype ( "distfun_lognstat" , sigma , "sigma" , 2 , "constant" )
  //
  // Check content
  tiny = number_properties("tiny")
  apifun_checkgreq ( "distfun_lognstat" , sigma , "sigma" , 2 , tiny )  
  //
  [ mu , sigma ] = apifun_expandvar ( mu , sigma )
  //
  M = exp(mu+sigma.^2/2)
  V = (exp(sigma.^2)-1) .* exp(2*mu+sigma.^2)
endfunction


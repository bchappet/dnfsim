// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [M,V] = distfun_normstat ( mu, sigma )
  // Normal  mean and variance
  //
  // Calling Sequence
  //   M = distfun_normstat ( mu , sigma )
  //   [M,V] = distfun_normstat ( mu , sigma )
  //
  // Parameters
  //   mu : a matrix of doubles, the average
  //   sigma : a matrix of doubles, the standard deviation. sigma>0.
  //   M : a matrix of doubles, the mean
  //   V : a matrix of doubles, the variance
  //
  // Description
  //   Computes statistics from the Normal distribution.
  //
  // The mean and variance of the Normal distribution are
  //
  //<latex>
  //\begin{eqnarray}
  // M &=& \mu \\
  // V &=& \sigma^2
  //\end{eqnarray}
  //</latex>
  //
  //   Any scalar input argument is expanded to a matrix of 
  //   doubles of the same size as the other input arguments.
  //
  // Examples
  //
  // mu = 1:6;
  // sigma = 7:12;
  // [M,V] = distfun_normstat ( mu , sigma )
  // me = 1:6;
  // ve = [49. 64. 81. 100. 121. 144.];
  //
  // Authors
  //   Copyright (C) 2011 - DIGITEO - Michael Baudin

  [lhs,rhs]=argn()
  apifun_checkrhs ( "distfun_normstat" , rhs , 2 )
  apifun_checklhs ( "distfun_normstat" , lhs , 0:2 )
  //
  // Check type
  apifun_checktype ( "distfun_normstat" , mu , "mu" , 1 , "constant" )
  apifun_checktype ( "distfun_normstat" , sigma , "sigma" , 2 , "constant" )
  //
  // Check content
  tiny = number_properties("tiny")
  apifun_checkgreq ( "distfun_normstat" , sigma , "sigma" , 2 , tiny )
  //
  [ mu , sigma ] = apifun_expandvar ( mu , sigma )
  //
  M = mu
  V = sigma.^2
endfunction


// Copyright (C) 2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [M,V] = distfun_betastat ( a, b )
  // Beta  mean and variance
  //
  // Calling Sequence
  //   M = distfun_betastat ( a , b )
  //   [M,V] = distfun_betastat ( a , b )
  //
  // Parameters
  //   a : a matrix of doubles, the first shape parameter, a>=0.
  //   b : a matrix of doubles, the first shape parameter, b>=0.
  //   M : a matrix of doubles, the mean
  //   V : a matrix of doubles, the variance
  //
  // Description
  //   Computes statistics from the Beta distribution.
  //
  // The mean and variance of the Beta distribution are
  //
  //<latex>
  //\begin{eqnarray}
  // M &=& \frac{a}{a+b} \\
  // V &=& \frac{ab}{(a+b)^2 (a+b+1)}
  //\end{eqnarray}
  //</latex>
  //
  //   Any scalar input argument is expanded to a matrix of 
  //   doubles of the same size as the other input arguments.
  //
  // Examples
  //
  // a = 1:6;
  // b = (1:6)^-1;
  // [M,V] = distfun_betastat ( a , b )
  // me = [..
  // 0.5 ..
  // 0.8 ..
  // 0.9 ..
  // 0.9411765..
  // 0.9615385..
  // 0.9729730..
  // ]';
  // ve = [..
  // 0.0833333..
  // 0.0457143..
  // 0.0207692..
  // 0.0105454..
  // 0.0059649..
  // 0.0036693..
  // ]';
  //
  // Authors
  //   Copyright (C) 2011 - DIGITEO - Michael Baudin

  [lhs,rhs]=argn()
  apifun_checkrhs ( "distfun_betastat" , rhs , 2 )
  apifun_checklhs ( "distfun_betastat" , lhs , 0:2 )
  //
  // Check type
  apifun_checktype ( "distfun_betastat" , a , "a" , 1 , "constant" )
  apifun_checktype ( "distfun_betastat" , b , "b" , 2 , "constant" )
  //
  // Check content
  apifun_checkgreq ( "distfun_betastat" , a , "a" , 1 , 0 )
  apifun_checkgreq ( "distfun_betastat" , b , "b" , 2 , 0 )  
  //
  [ a , b ] = apifun_expandvar ( a , b )
  //
  M = a ./ (a+b)
  V = a.*b ./ (a+b).^2 ./ (a+b+1)
endfunction


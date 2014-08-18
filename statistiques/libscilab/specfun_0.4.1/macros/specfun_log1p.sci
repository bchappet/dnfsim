// Copyright (C) 2010 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function y = specfun_log1p ( x )
  // Compute log(1+x) accurately for small values of x.
  //
  // Calling Sequence
  //   y = specfun_log1p ( x )
  //
  // Parameters
  //   x : a matrix of doubles
  //   y : a matrix of doubles, log(1+x)
  //
  // Description
  // Returns log(1+x) accurately for small values of x.
  //
  // The method suggested by Kahan is used to improve accuracy.
  //
  // Examples
  // specfun_log1p(2)
  //
  // // Plot this function for positive inputs.
  // scf();
  // plot(linspace(-0.5,2,1000),specfun_log1p)
  //
  // // Compare the precision of log1p and log
  // // for small x: log1p gives the exact result
  // // while log(1+x) is not as accurate.
  // x = 0.000001;
  // e = 9.9999950000033333308e-7 // From Wolfram Alpha
  // y1 = specfun_log1p(x); abs(y1-e)/abs(e)
  // y2 = log(1+x);  abs(y2-e)/abs(e)
  //
  // Authors
  // Michael Baudin, DIGITEO, 2010
  //
  // Bibliography
  //   Don Hatch, http://www.plunk.org/~hatch/rightway.php
  //   Nicholas J. Higham, "Accuracy and Stability of Numerical Algorithms", SIAM, 2002
  //   William Kahan, "Branch Cuts for Complex Elementary Functions, or Much Ado About Nothing's Sign Bit" in The State of the Art in Numerical Analysis, (eds. Iserles and Powell), Clarendon Press, Oxford, 1987.
  //   http://www.google.com/codesearch/p?hl=fr#AFRiEzdmb_g/trunk/matlab/mathutil/log1p.m&q=log1p%20lang:matlab&d=3
  //   http://bugs.python.org/file8685/cmath_py.py
  //   Robert Sedgewick and Kevin Wayne, "Floating Point", http://www.cs.princeton.edu/introcs/91float/
  //   W. Kahan, "A Logarithm Too Clever by Half", 2004, http://www.cs.berkeley.edu/~wkahan/LOG10HAF.TXT

  [lhs, rhs] = argn()
  apifun_checkrhs ( "specfun_log1p" , rhs , 1 )
  apifun_checklhs ( "specfun_log1p" , lhs , 1 )
  //
  apifun_checktype ( "specfun_log1p" , x , "x" , 1 , "constant" )
  //
  y = x
  //
  u = 1 + x
  m = find(u <> 1)
  y(m) = log(u(m)) .* (x(m) ./ (u(m) - 1))
  //
  m = find(x==0)
  y(m) = 0
  //
  m = find(x==-%inf)
  y(m) = %inf + %pi * %i
  //
  m = find(x==%inf)
  y(m) = %inf
endfunction




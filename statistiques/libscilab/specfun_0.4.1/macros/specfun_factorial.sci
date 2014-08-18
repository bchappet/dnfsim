// Copyright (C) 2010 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function f = specfun_factorial ( n )
  // The factorial function
  //
  // Calling Sequence
  //   f = specfun_factorial ( n )
  //
  // Parameters
  //   n : a matrix of floating point integers, must be positive
  //   f : a matrix of floating point integers
  //
  // Description
  //   Returns the factorial of n, that is, the product of all 
  //   integers 1 * 2 * ... * n.
  //
  //   This function overflows as soon as n>170.
  //
  //   Our tests indicate that this function is exact for n 
  //   lower or equal to 16.
  //   For n>16, the relative error can be as large as 200*%eps.
  //
  // Examples
  // // Make a table of factorial
  // n = (0:30)';
  // [n specfun_factorial(n)]
  //
  // // See that we must round the gamma function
  // n = 12;
  // specfun_factorial(n)
  // gamma(n+1)
  //
  // // See the limits of factorial: f(171)=%inf
  // specfun_factorial(170) // 7.257415615307998967e306
  // specfun_factorial(171) // %inf
  //
  // // Plot the function on all its range.
  // scf();
  // plot ( 1:170 , specfun_factorial , "b-o" )
  // h = gcf();
  // h.children.log_flags="nln";
  //
  // Authors
  // Michael Baudin - 2010 
  //
  // Bibliography
  //   "Introduction to discrete probabilities", Michael Baudin, 2010
  
  [lhs, rhs] = argn()
  apifun_checkrhs ( "specfun_factorial" , rhs , 1 )
  apifun_checklhs ( "specfun_factorial" , lhs , 1 )
  //
  apifun_checktype ( "specfun_factorial" , n , "n" , 1 , "constant" )
  //
  apifun_checkrange ( "specfun_factorial" , n , "n" , 1 , 0 , %inf )
  apifun_checkflint ( "specfun_factorial" , n , "n" , 1 )
  //
  f = gamma ( n + 1 )
  f = round ( f )
endfunction


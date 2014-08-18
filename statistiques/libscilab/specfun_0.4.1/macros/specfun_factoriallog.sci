// Copyright (C) 2010 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function flog = specfun_factoriallog ( n )
  // The log-factorial function
  //
  // Calling Sequence
  //   flog = specfun_factoriallog ( n )
  //
  // Parameters
  //   n : a matrix of floating point integers, must be positive
  //   flog : a matrix of floating point integers
  //
  // Description
  //   Returns the logarithm of the factorial of n.
  //
  //   This function overflows as soon as x>1.e306.
  //
  // Examples
  // // Generates an error: this is not an integer
  // specfun_factoriallog(1.5)
  //
  // // Generates an error: this is not positive
  // specfun_factoriallog(-1)
  //
  // // Plot the function
  // scf();
  // plot(0:170,specfun_factoriallog)
  //
  // Authors
  // Michael Baudin - 2010 
  //
  // Bibliography
  //   "Introduction to discrete probabilities", Michael Baudin, 2010
  
  [lhs, rhs] = argn()
  apifun_checkrhs ( "specfun_factoriallog" , rhs , 1 )
  apifun_checklhs ( "specfun_factoriallog" , lhs , 1 )
  //
  apifun_checktype ( "specfun_factoriallog" , n , "n" , 1 , "constant" )
  //
  apifun_checkrange ( "specfun_factoriallog" , n , "n" , 1 , 0 , %inf )
  //
  apifun_checkflint ( "specfun_factoriallog" , n , "n" , 1 )
  //
  flog = gammaln(n+1)
endfunction


// Copyright (C) 2009 - 2010 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function b = specfun_nchoosek ( n , k )
  //   Returns the binomial number (n,k).
  //
  // Parameters
  //   n : a matrix of floating point integers, must be positive
  //   k : a matrix of floating point integers, must be positive
  //   n : a matrix of floating point integers
  //
  // Calling Sequence
  //   b = specfun_nchoosek ( n , k )
  //
  // Description
  //   Computes the number of k-element subsets of an n-element set.
  //   It is mathematically defined by 
  //
  //   <latex>
  //   \begin{eqnarray}
  //   \frac{n!}{k! (n-k)!}
  //   \end{eqnarray}
  //   </latex>
  //
  //   which is equivalent to ( n*(n-1)*...*(n-k+1) ) / ( k*(k-1)*...*1 ).
  //   The implementation, though, uses a robust floating point implementation, 
  //   based on the gammaln and exp functions.
  //
  //   If n < 0, k < 0 or k > n, then an error is generated.
  //
  // Note about Matlab compatibility.
  //
  // The specfun_nchoosek function and the Matlab/nchoosek functions 
  // are different. 
  // The Matlab nchoosek function can both compute the function 
  // value and generate the combinations.
  // In this module, the two features are separated and are 
  // provided by specfun_nchoosek (the function value) and specfun_subset
  // (the combination generation).
  // We think that this is a better design. 
  // In practice, the specfun_nchoosek can take a matrix n and a 
  // scalar k, which cannot be done by Matlab/nchoosek.
  //
  // Note about floating point accuracy.
  //
  //   We have factorial(170) ~ 7.e306. 
  //   So n=170 is the greatest integer for which n! can be computed.
  //   If the naive formula b = n!/(k! (n-k)! )
  //   was used directly, the maximum value n for which 
  //   the binomial can be computed is n = 170.
  //   But binomial(171,1) = 1, so there is no reason
  //   to prevent the computation of 1 because an intermediate
  //   result is greater than 1.e308.
  //   This is why the gammaln function, combined with exp, is used instead.
  //
  // Note about rounding for integer inputs.
  //
  //   If n = 4 and k = 1, the gammaln and exp functions 
  //   are accurate only to 1ulp. This leads to the 
  //   result that b = 3.99...99998.
  //   It is the result of the fact that we use the elementary 
  //   functions exp and gammaln.
  //   This is very close to 4, but is not equal to 4.
  //   Assume that you compute c = b (mod 4) and you 
  //   get c = b = 3.99...99998, intead of getting c  = 0.
  //   This is why, when input arguments are integers,
  //   the result is rounded to the nearest integer with the round function.
  //
  // Examples
  // c = specfun_nchoosek ( 4 , 1 ) // 4
  // c = specfun_nchoosek ( 5 , 0 ) // 1
  // c = specfun_nchoosek ( 5 , 1 ) // 5
  // c = specfun_nchoosek ( 5 , 2 ) // 10
  // c = specfun_nchoosek ( 5 , 3 ) // 10
  // c = specfun_nchoosek ( 5 , 4 ) // 5
  // c = specfun_nchoosek ( 5 , 5 ) // 1
  //
  // // The following test shows that the implementation is not naive
  // c = specfun_nchoosek ( 10000 , 134 ) // 
  // exact = 2.050083865033972676e307
  // relerror = abs(c-exact)/exact
  // // On a Windows system, we got ~ 1.e-11, which shows that the implementation 
  // // was, in this case, accurate up to 11 digits (instead of 15-17).
  //
  // // The following test shows that the implementation is vectorized.
  // specfun_nchoosek (10,0:10) // [1,10,45,120,210,252,210,120,45,10,1]
  // // The following would be different in Matlab
  // specfun_nchoosek (1:10,1)
  // // The following would be impossible in Matlab
  // specfun_nchoosek (1:10,1:10)
  //
  // // Generates an error
  // c = specfun_nchoosek ( 17 , 18 ) 
  // c = specfun_nchoosek ( 17 , -1 ) 
  // c = specfun_nchoosek ( 1.5 , 0.5 )
  //
  // // This generates an error, since n and k do not 
  // // have the same size.
  // specfun_nchoosek (ones(4,5),ones(2,3))
  //
  // Authors
  //   Copyright (C) 2009 - 2010 - Michael Baudin
  //   Copyright (C) 2009 - John Burkardt
  //
  // Bibliography
  //   "Introduction to discrete probabilities with Scilab", Michael Baudin, 2010
  //   http://bugzilla.scilab.org/show_bug.cgi?id=7589, a bug report for the lack of specfun_nchoosek function in Scilab.
  //   http://en.wikipedia.org/wiki/Binomial_coefficients
  //   http://wiki.tcl.tk/1755
  
  [lhs, rhs] = argn()
  apifun_checkrhs ( "specfun_nchoosek" , rhs , 2 )
  apifun_checklhs ( "specfun_nchoosek" , lhs , 1 )
  //
  // Check type
  apifun_checktype ( "specfun_nchoosek" , n , "n" , 1 , "constant" )
  apifun_checktype ( "specfun_nchoosek" , k , "k" , 2 , "constant" )
  //
  // Check size
  if ( size(n,"*") <> 1 & size(k,"*") <> 1 ) then
    apifun_checkdims ( "specfun_nchoosek" , k , "k" , 2 , size(n) )
  end
  //
  // Check content
  apifun_checkrange ( "specfun_nchoosek" , n , "n" , 1 , 0 , %inf )
  apifun_checkrange ( "specfun_nchoosek" , k , "k" , 2 , 0 , %inf )
  //
  // Check that n and k are flints
  apifun_checkflint ( "specfun_nchoosek" , n , "n" , 1 )
  apifun_checkflint ( "specfun_nchoosek" , k , "k" , 2 )
  //
  // Check consistency between k and n
  if ( or(k > n) ) then 
    errmsg = msprintf ( gettext ( "%s: For at least one entry, we do not have k<=n." ) , ...
    "specfun_nchoosek" )
    error ( errmsg )
  end
  r = gammaln ( n + 1 ) - gammaln (k + 1) - gammaln (n - k + 1)
  b = exp( r )
  b = round ( b )
endfunction


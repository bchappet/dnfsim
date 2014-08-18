// Copyright (C) 2010 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
//
// <-- JVM NOT MANDATORY -->
// <-- ENGLISH IMPOSED -->


//
// Values of n for which factorial is exact.
// Exact values from Wolfram Alpha.
expected = [
  2   0.69314718055994530941
  3   1.79175946922805500081
  4   3.17805383034794561964
  5   4.78749174278204599424
  10  15.10441257307551529522
  130 506.1328253420348751997
  1.e1 15.10441257307551529522570
  1.e2 363.739375555563490144079993
  1.e3 5912.12817848816334887813088
  1.e5   1.0512992218991218651292e6
  1.e10  2.2025850931183643239998237e11
  1.e100 2.292585092994045684e102
  1.e200 4.595170185988091368e202
  1.e305 7.012884533631839336e307
];
ntests = size(expected,"r");
for k = 1 : ntests
  n = expected(k,1);
  flog = expected(k,2);
  computed = specfun_factoriallog(n);
  if ( %f ) then
    r = ceil(abs(computed-flog)/flog/%eps);
    mprintf("k=%d, r=%d\n",k,r)
  end
  assert_checkalmostequal ( computed, flog, 5*%eps );
end
//
assert_checkequal ( specfun_factoriallog(0) , 0 );
assert_checkequal ( specfun_factoriallog(1) , 0 );
assert_checkequal ( specfun_factoriallog(1.e306) , %inf );
assert_checkequal ( specfun_factoriallog(%inf) , %inf );


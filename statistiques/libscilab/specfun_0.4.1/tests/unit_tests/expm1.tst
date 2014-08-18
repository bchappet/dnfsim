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

////////////////////////////////////////////////////////////////////////
// 
// Check Argument Checking
//
assert_checkerror ( "specfun_expm1()" , "specfun_expm1: Unexpected number of input arguments : 0 provided while the number of expected input arguments should be in the set [1]." );
assert_checkerror ( "specfun_expm1(1,2)" , "Wrong number of input arguments:" );
assert_checkerror ( "specfun_expm1(""a"")" , "specfun_expm1: Expected type [""constant""] for input argument x at input #1, but got ""string"" instead." );
////////////////////////////////////////////////////////////////////////
// 
// Check robustness
computed = specfun_expm1([]);
assert_checkequal ( computed , [] );
//
// IEEE values
computed = specfun_expm1([+0 -0 %inf -%inf %nan]);
assert_checkequal ( computed , [0 0 %inf -1 %nan] );
//
// A complex
computed = specfun_expm1(1+%i);
expected = 0.468693939915885 + 2.287355287178842 * %i;
assert_checkalmostequal ( computed , expected , 2*%eps );
//
// A row vector
computed = specfun_expm1(1:4);
expected = [
  1.718281828459045235
  6.389056098930650227
  19.08553692318766774
  53.59815003314423907
]';
assert_checkalmostequal ( computed , expected , %eps );
//
// A column vector
computed = specfun_expm1((1:4)');
expected = [
  1.718281828459045235 
  6.389056098930650227
  19.08553692318766774
  53.59815003314423907
];
assert_checkalmostequal ( computed , expected , %eps );
//
// A matrix
computed = specfun_expm1(ones(2,3));
expected = 1.718281828459045235 * ones(2,3);
assert_checkalmostequal ( computed , expected , %eps );
////////////////////////////////////////////////////////////
//
// Some simple accuracy tests
// [x fexact]
table = [
1.e-3  1.00050016670834166e-3
1.e-6  1.00000050000016666e-6
1.e-9  1.00000000050000000e-9
1.e-12 1.00000000000050000e-12
1.e-20 1.00000000000000000e-20
-1.e-3  -9.9950016662500833194e-4
-1.e-6  -9.9999950000016666662e-7
-1.e-9  -9.9999999950000000016e-10
-1.e-12 -9.9999999999950000000e-13
-1.e-20 -9.9999999999999999999e-21
];
ntests = size(table,"r");
x = table(:,1);
computed = specfun_expm1(x);
expected = table(:,2);
digits = assert_computedigits ( computed , expected );
requireddigits = 15.5;
assert_checktrue ( digits >= requireddigits );
for i = 1 : ntests
  if ( %f ) then
    mprintf("Test #%2d/%2d, x=%s, y=%s, e=%s, d=%3.1f, %s\n", ..
      i, ntests, sci2exp(x(i)), sci2exp(computed(i)), sci2exp(expected(i)) , digits(i), ok);
  end
end





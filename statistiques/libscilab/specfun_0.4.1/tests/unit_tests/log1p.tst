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
assert_checkerror ( "specfun_log1p()" , "specfun_log1p: Unexpected number of input arguments : 0 provided while the number of expected input arguments should be in the set [1]." );
assert_checkerror ( "specfun_log1p(1,2)" , "Wrong number of input arguments:" );
assert_checkerror ( "specfun_log1p(""a"")" , "specfun_log1p: Expected type [""constant""] for input argument x at input #1, but got ""string"" instead." );
////////////////////////////////////////////////////////////////////////
// 
// Check robustness
computed = specfun_log1p([]);
assert_checkequal ( computed , [] );
//
// IEEE values
computed = specfun_log1p([+0 -0 %inf -%inf %nan] );
assert_checkequal ( computed , [0 0 %inf %inf+%pi*%i complex(%nan,%nan)] );
//
// A row vector
computed = specfun_log1p(1:4);
expected = [0.69314718055994529 1.09861228866810978 1.38629436111989057 1.60943791243410028];
assert_checkequal ( computed , expected );
//
// A column vector
computed = specfun_log1p((1:4)');
expected = [0.69314718055994529 1.09861228866810978 1.38629436111989057 1.60943791243410028]';
assert_checkequal ( computed , expected );
//
// A matrix
computed = specfun_log1p(ones(2,3));
expected = 0.69314718055994529 * ones(2,3);
assert_checkequal ( computed , expected );
////////////////////////////////////////////////////////////////////////
// 
// Check accuracy
format("e",10);
path=specfun_getpath();
dataset = fullfile(path,"tests","unit_tests","log1p.dataset.xcas.csv");
table = assert_csvread ( dataset , "," , [] , "/#(.*)/" );
ntests = size(table,"r");
x = evstr(table(:,1));
computed = specfun_log1p(x);
expected = evstr(table(:,2));
condition = evstr(table(:,3));
digits = assert_computedigits ( computed , expected );
// Accept to lose 1/2 decimal digit more than the condition would predict
offset = -0.5;
requireddigits = assert_cond2reqdigits ( condition , offset );
assert_checktrue ( digits >= requireddigits );
for i = 1 : ntests
  if ( %f ) then
    mprintf("Test #%2d/%2d, x=%s, y=%s, e=%s, c=%e, d=%3.1f, %s\n", ..
      i, ntests, sci2exp(x(i)), sci2exp(computed(i)), sci2exp(expected(i)) , condition(i), digits(i), ok);
  end
end





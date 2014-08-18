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
assert_checkerror ( "specfun_gammainc()" , "specfun_gammainc: Unexpected number of input arguments : 0 provided while the number of expected input arguments should be in the set [2 3]." );
assert_checkerror ( "specfun_gammainc(1)" , "specfun_gammainc: Unexpected number of input arguments : 1 provided while the number of expected input arguments should be in the set [2 3]." );
assert_checkerror ( "specfun_gammainc(""a"",2)" , "specfun_gammainc: Expected type [""constant""] for input argument x at input #1, but got ""string"" instead." );
assert_checkerror ( "specfun_gammainc(1,""a"")" , "specfun_gammainc: Expected type [""constant""] for input argument a at input #2, but got ""string"" instead." );

////////////////////////////////////////////////////////////////////////
//
// Check accuracy
//
computed = specfun_gammainc(1,2);
assert_checkalmostequal ( computed , 0.264241117657115 , 10 * %eps );
//
computed = specfun_gammainc(2,3);
assert_checkalmostequal ( computed , 0.323323583816936 , 10 * %eps );
//
computed = specfun_gammainc(2,3,"lower");
assert_checkalmostequal ( computed , 0.323323583816936 , 10 * %eps );
//
// We have specfun_gammainc(x,a,"lower") == 1 - specfun_gammainc(x,a,"upper")
computed = specfun_gammainc(2,3,"upper");
assert_checkalmostequal ( computed , 0.676676416183064 , 10 * %eps );
//
// The following example shows how to use the tail argument.
// For a=1 and x>40, the result is so close to 1 that the
// result is represented by the floating point number computed=1.
computed = specfun_gammainc(40,1);
assert_checkalmostequal ( computed , 1 , %eps );
//
// This is why we may compute the complementary probability with
// the tail option.
computed = specfun_gammainc(40,1,"upper");
assert_checkalmostequal ( computed , 4.248354255291594e-018 , 10 * %eps );

//
// Show vectorized support
//
// Show expansion of a
x = [1 2 3;4 5 6];
a = 2;
computed = specfun_gammainc(x,a);
expected = [
    2.642411176571152764D-01    5.939941502901617820D-01    8.008517265285441944D-01  
    9.084218055563291205D-01    9.595723180054872570D-01    9.826487347633354741D-01  
];
assert_checkalmostequal ( computed , expected , 10 * %eps );
//
// Show expansion of x
x = 2;
a = [1 2 3;4 5 6];
computed = specfun_gammainc(x,a);
expected = [
    8.646647167633872977D-01    5.939941502901617820D-01    3.233235838169365439D-01  
    1.428765395014529316D-01    5.265301734371115316D-02    1.656360848061444804D-02  
];
assert_checkalmostequal ( computed , expected , 10 * %eps );
//
// Check error when size of a and x do not match
x = [1 2 3 4 5 6];
a = [1 2 3;4 5 6];
assert_checkerror ( "computed = specfun_gammainc(x,a);" , "specfun_gammainc: Arguments #1 and #2 do not match. Size of x is [1,6] while size of a is [2,3]." );

//
// Process the special case a=0
x = [0 1 2 3 4 5 6 7 8 9 10];
a = [0 1 0 2 0 3 0 4 0 5 0];
computed = specfun_gammainc(x,a,"lower");
expected = [
   1.000000000000000
   0.632120558828558
   1.000000000000000
   0.800851726528544
   1.000000000000000
   0.875347980516919
   1.000000000000000
   0.918234583755278
   1.000000000000000
   0.945036358504895
   1.000000000000000
]';
assert_checkalmostequal ( computed , expected , 10 * %eps );
//
x = [0 1 2 3 4 5 6 7 8 9 10];
a = [0 1 0 2 0 3 0 4 0 5 0];
computed = specfun_gammainc(x,a,"upper");
expected = [
                   0
   0.367879441171442
                   0
   0.199148273471456
                   0
   0.124652019483081
                   0
   0.081765416244722
                   0
   0.054963641495105
                   0
]';
assert_checkalmostequal ( computed , expected , 10 * %eps );


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



n=5;
U = [
    1.    1.    1.    1.    1.  
    0.    1.    2.    3.    4.  
    0.    0.    1.    3.    6.  
    0.    0.    0.    1.    4.  
    0.    0.    0.    0.    1.  
];
S = [
    1.    1.    1.     1.     1.   
    1.    2.    3.     4.     5.   
    1.    3.    6.     10.    15.  
    1.    4.    10.    20.    35.  
    1.    5.    15.    35.    70.  
];
//
computed = specfun_pascal ( n );
assert_checkequal ( computed , S );
//
computed = specfun_pascal ( n , 0 );
assert_checkequal ( computed , S );
//
computed = specfun_pascal ( n , 1 );
assert_checkequal ( computed , U' );
//
computed = specfun_pascal ( n , -1 );
assert_checkequal ( computed , U );
//
computed = specfun_pascal ( 0 , -1 );
assert_checkequal ( computed , [] );
//
computed = specfun_pascal ( 0 , 0 );
assert_checkequal ( computed , [] );
//
computed = specfun_pascal ( 0 , 1 );
assert_checkequal ( computed , [] );
//
computed = specfun_pascal ( 1 , -1 );
assert_checkequal ( computed , 1 );
//
computed = specfun_pascal ( 1 , 0 );
assert_checkequal ( computed , 1 );
//
computed = specfun_pascal ( 1 , 1 );
assert_checkequal ( computed , 1 );
//






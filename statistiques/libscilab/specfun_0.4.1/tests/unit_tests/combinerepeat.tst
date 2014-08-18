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
// Test 2 combinations with one row vector
x = [1 2 3];
computed = specfun_combinerepeat ( x , 2 );
expected = [
1,1,1,2,2,2,3,3,3;  
1,2,3,1,2,3,1,2,3
];
assert_checkequal ( computed , expected );
//
// Test 1 combinations with one row vector
x = [1 2 3];
computed = specfun_combinerepeat ( x , 1 );
expected = x;
assert_checkequal ( computed , expected );
//
// Test 3 combinations with one row vector
x = [1 2 3];
computed = specfun_combinerepeat ( x , 3 );
expected = [
1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3,3,3;  
1,1,1,2,2,2,3,3,3,1,1,1,2,2,2,3,3,3,1,1,1,2,2,2,3,3,3;   
1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3
];
assert_checkequal ( computed , expected );
//
// Repeated combinations of booleans
computed = specfun_combinerepeat ( [%t %f] , 2 );
expected = [%t,%t,%f,%f;%t,%f,%t,%f];
assert_checkequal ( computed , expected );
//
// Repeated combinations of strings
computed = specfun_combinerepeat ( ["A" "C" "T" "G"] , 2 );
expected = [
"A","A","A","A","C","C","C","C","T","T","T","T","G","G","G","G";  
"A","C","T","G","A","C","T","G","A","C","T","G","A","C","T","G"
];
assert_checkequal ( computed , expected );
//
// Repeated combinations of integers
computed = specfun_combinerepeat ( uint8(1:3) , 2 );
expected = uint8([
1,1,1,2,2,2,3,3,3;  
1,2,3,1,2,3,1,2,3
]);
assert_checkequal ( computed , expected );


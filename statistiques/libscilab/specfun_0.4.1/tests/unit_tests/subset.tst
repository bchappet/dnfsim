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





x = [1 2 3 4];
k = 3;
computed = specfun_subset ( x , k );
expected = [
    1 2 3
    1 2 4
    1 3 4
    2 3 4
];
assert_checkequal ( computed , expected );
//
x = [1 2 3 4];
k = 3;
computed = specfun_subset ( x , k , "r" );
expected = [
    1 2 3
    1 2 4
    1 3 4
    2 3 4
];
assert_checkequal ( computed , expected );
//
x = [1 2 3 4]';
k = 3;
computed = specfun_subset ( x , k , "c" );
expected = [
    1 2 3
    1 2 4
    1 3 4
    2 3 4
]';
assert_checkequal ( computed , expected );
//
x = [17 32 48 53];
k = 3;
computed = specfun_subset ( x , k );
expected = [
     17 32 48
     17 32 53
     17 48 53
     32 48 53
];
assert_checkequal ( computed , expected );
//
x = [17 32 48 53 72];
k = 3;
computed = specfun_subset ( x , k );
expected = [
     17 32 48
     17 32 53
     17 32 72
     17 48 53
     17 48 72
     17 53 72
     32 48 53
     32 48 72
     32 53 72
     48 53 72
];
assert_checkequal ( computed , expected );
//
x = [17 32 48 53 72];
k = 4;
computed = specfun_subset ( x , k );
expected = [
    17 32 48 53  
    17 32 48 72
    17 32 53 72
    17 48 53 72
    32 48 53 72
];
assert_checkequal ( computed , expected );
//
// Indirectly combine strings
x = ["a" "b" "c" "d" "e"];
k = 3;
n=size(x,"*");
map = specfun_subset((1:n),k);
cnk = specfun_nchoosek(n,k);
computed = matrix(x(map),cnk,k);
expected = [
  "a" "b" "c";  
  "a" "b" "d";
  "a" "b" "e";
  "a" "c" "d";
  "a" "c" "e";
  "a" "d" "e";
  "b" "c" "d";
  "b" "c" "e";
  "b" "d" "e";
  "c" "d" "e"
];
assert_checkequal ( computed , expected );
//
// Directly combine strings
computed = specfun_subset(["a" "b" "c" "d" "e" "f"],4);
expected = [
  "a" "b" "c" "d"  
  "a" "b" "c" "e"
  "a" "b" "c" "f"
  "a" "b" "d" "e"
  "a" "b" "d" "f"
  "a" "b" "e" "f"
  "a" "c" "d" "e"
  "a" "c" "d" "f"
  "a" "c" "e" "f"
  "a" "d" "e" "f"
  "b" "c" "d" "e"
  "b" "c" "d" "f"
  "b" "c" "e" "f"
  "b" "d" "e" "f"
  "c" "d" "e" "f"
];
assert_checkequal ( computed , expected );
//
// Directly combine strings - column direction
computed = specfun_subset(["a" "b" "c" "d" "e" "f"]',4,"c");
expected = [
  "a" "b" "c" "d"  
  "a" "b" "c" "e"
  "a" "b" "c" "f"
  "a" "b" "d" "e"
  "a" "b" "d" "f"
  "a" "b" "e" "f"
  "a" "c" "d" "e"
  "a" "c" "d" "f"
  "a" "c" "e" "f"
  "a" "d" "e" "f"
  "b" "c" "d" "e"
  "b" "c" "d" "f"
  "b" "c" "e" "f"
  "b" "d" "e" "f"
  "c" "d" "e" "f"
]';
assert_checkequal ( computed , expected );
//
// Directly combine strings - row direction
computed = specfun_subset(["a" "b" "c" "d" "e" "f"],4,"r");
expected = [
  "a" "b" "c" "d"  
  "a" "b" "c" "e"
  "a" "b" "c" "f"
  "a" "b" "d" "e"
  "a" "b" "d" "f"
  "a" "b" "e" "f"
  "a" "c" "d" "e"
  "a" "c" "d" "f"
  "a" "c" "e" "f"
  "a" "d" "e" "f"
  "b" "c" "d" "e"
  "b" "c" "d" "f"
  "b" "c" "e" "f"
  "b" "d" "e" "f"
  "c" "d" "e" "f"
];
assert_checkequal ( computed , expected );
//
// Combinations of booleans
computed = specfun_subset ( [%t %f %t %f] , 3 );
expected = [%t,%f,%t;%t,%f,%f;%t,%t,%f;%f,%t,%f];
assert_checkequal ( computed , expected );
//
// Repeated combinations of integers
computed = specfun_subset ( uint8(1:3) , 2 );
expected = uint8([
  1 2;
  1 3;
  2 3
]);
assert_checkequal ( computed , expected );

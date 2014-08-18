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
// Test 1 vector
x = [1 2 3];
computed = specfun_combine ( x );
expected = x;
assert_checkequal ( computed , expected );
//
// Test 2 row vectors
x = [1 2 3];
y = [4 5 6];
computed = specfun_combine ( x , y );
expected = [
1 1 1 2 2 2 3 3 3; 
4 5 6 4 5 6 4 5 6
];
assert_checkequal ( computed , expected );
//
// Test 3 row vectors
x = [1 2 3];
y = [4 5 6];
z = [7 8 9];
computed = specfun_combine ( x , y , z );
expected = [
1 1 1 1 1 1 1 1 1 2 2 2 2 2 2 2 2 2 3 3 3 3 3 3 3 3 3;  
4 4 4 5 5 5 6 6 6 4 4 4 5 5 5 6 6 6 4 4 4 5 5 5 6 6 6;   
7 8 9 7 8 9 7 8 9 7 8 9 7 8 9 7 8 9 7 8 9 7 8 9 7 8 9
];
assert_checkequal ( computed , expected );
//
x = [1 2;3 4];
y = [5 6;7 8];
computed = specfun_combine ( x , y );
expected = [
  1,2,5,6;
  1,2,7,8;
  3,4,5,6;
  3,4,7,8
];
//
// Combine random matrices of random sizes.
// Shows that any matrices of any dimensions can be combined.
for k = 1 : 5
  m = grand(1,2,"uin",1,5);
  n = grand(1,2,"uin",1,5);
  x = grand(m(1),n(1),"uin",1,m(1)*n(1));
  y = grand(m(2),n(2),"uin",1,m(2)*n(2));
  c = specfun_combine ( x , y );
  assert_checkequal ( size(c) , [m(1)+m(2) n(1)*n(2)] );
end
for k = 1 : 5
  m = grand(1,3,"uin",1,5);
  n = grand(1,3,"uin",1,5);
  x = grand(m(1),n(1),"uin",1,m(1)*n(1));
  y = grand(m(2),n(2),"uin",1,m(2)*n(2));
  z = grand(m(3),n(3),"uin",1,m(3)*n(3));
  c = specfun_combine ( x , y , z );
  assert_checkequal ( size(c) , [m(1)+m(2)+m(3) n(1)*n(2)*n(3)] );
end

//
// Test 2 column vectors
x = [
1
2
3
];
y = [
4
5
6
];
computed = specfun_combine ( x' , y' )';
expected = [
1 4;   
1 5;   
1 6;   
2 4;   
2 5;   
2 6;   
3 4;   
3 5;   
3 6
];
assert_checkequal ( computed , expected );
//
// Test 3 column vectors
x = [
1
2
3
];
y = [
4
5
6
];
z = [
7
8
9
];
computed = specfun_combine ( x' , y' , z' )';
expected = [
1 4 7;  
1 4 8;   
1 4 9;   
1 5 7;   
1 5 8;   
1 5 9;   
1 6 7;   
1 6 8;   
1 6 9;   
2 4 7;   
2 4 8;   
2 4 9;   
2 5 7;   
2 5 8;   
2 5 9;   
2 6 7;   
2 6 8;   
2 6 9;   
3 4 7;   
3 4 8;   
3 4 9;   
3 5 7;   
3 5 8;   
3 5 9;   
3 6 7;   
3 6 8;   
3 6 9
];
assert_checkequal ( computed  , expected );
//
// Produces combinations of booleans
computed = (specfun_combine(0:1,0:1,0:1)==ones(3,2^3));
expected = [%f,%f,%f,%f,%t,%t,%t,%t;%f,%f,%t,%t,%f,%f,%t,%t;%f,%t,%f,%t,%f,%t,%f,%t];
assert_checkequal ( computed  , expected );
//
// Combine matrices of letters
k = specfun_combine ( 1:2 , 1:2 );
m1=["a" "b"];
m2=["c" "d"];
computed = [m1(k(1,:));m2(k(2,:))];
expected = [
"a","a","b","b"
"c","d","c","d"
];
assert_checkequal ( computed  , expected );
//
// Combine matrices of strings
computed = specfun_combine ( ["a" "b"] , ["c" "d"] );
expected = [
"a","a","b","b"
"c","d","c","d"
];
assert_checkequal ( computed  , expected );
//
// Combine matrices of strings
computed = specfun_combine ( ["a" "b"]' , ["c" "d"]' );
expected = ["a";"b";"c";"d"];
assert_checkequal ( computed  , expected );
//
// Combine matrices of strings
computed = specfun_combine ( ["a" "b" "c";"d" "e" "f"] , "x" );
expected = ["a","b","c";"d","e","f";"x","x","x"];
assert_checkequal ( computed  , expected );
//
// Combine 3 strings
x = ["a" "b" "c"];
y = ["d" "e"];
z = ["f" "g" "h"];
computed = specfun_combine ( x , y , z );
expected = [
"a","a","a","a","a","a","b","b","b","b","b","b","c","c","c","c","c","c"
"d","d","d","e","e","e","d","d","d","e","e","e","d","d","d","e","e","e"   
"f","g","h","f","g","h","f","g","h","f","g","h","f","g","h","f","g","h"
];
assert_checkequal ( computed  , expected );
//
// Combine booleans
computed = specfun_combine ( [%t %f] , [%t %f] , [%t %f] );
expected = [%t,%t,%t,%t,%f,%f,%f,%f;%t,%t,%f,%f,%t,%t,%f,%f;%t,%f,%t,%f,%t,%f,%t,%f];
assert_checkequal ( computed  , expected );
//
// Combine integers
computed = specfun_combine(uint8(1:4),uint8(1:3));
expected = uint8([
1,1,1,2,2,2,3,3,3,4,4,4;
1,2,3,1,2,3,1,2,3,1,2,3
]);
assert_checkequal ( computed  , expected );




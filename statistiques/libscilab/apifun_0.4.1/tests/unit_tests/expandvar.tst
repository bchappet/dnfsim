// Copyright (C) 2009-2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- JVM NOT MANDATORY -->

//
// assert_close --
//   Returns 1 if the two real matrices computed and expected are close,
//   i.e. if the relative distance between computed and expected is lesser than epsilon.
// Arguments
//   computed, expected : the two matrices to compare
//   epsilon : a small number
//
function flag = assert_close ( computed, expected, epsilon )
  if expected==0.0 then
    shift = norm(computed-expected);
  else
    shift = norm(computed-expected)/norm(expected);
  end
  if shift < epsilon then
    flag = 1;
  else
    flag = 0;
  end
  if flag <> 1 then pause,end
endfunction
//
// assert_equal --
//   Returns 1 if the two real matrices computed and expected are equal.
// Arguments
//   computed, expected : the two matrices to compare
//   epsilon : a small number
//
function flag = assert_equal ( computed , expected )
  if computed==expected then
    flag = 1;
  else
    flag = 0;
  end
  if flag <> 1 then pause,end
endfunction

var1 = apifun_expandvar ( 1.0 );
assert_equal ( var1 , 1.0 );
[ var1 , var2 ] = apifun_expandvar ( 1.0 , 2.0 );
assert_equal ( var1 , 1.0 );
assert_equal ( var2 , 2.0 );
[ var1 , var2 ] = apifun_expandvar ( 1.0 , [2.0 3.0] );
assert_equal ( var1 , [ 1.0 1.0 ] );
assert_equal ( var2 , [ 2.0 3.0 ] );
[ var1 , var2 ] = apifun_expandvar ( [ 1.0 2.0 ] , 3.0 );
assert_equal ( var1 , [ 1.0 2.0 ] );
assert_equal ( var2 , [ 3.0 3.0 ] );
[ var1 , var2 ] = apifun_expandvar ( [ 1.0 2.0 ] , [ 3.0 4.0 ] );
assert_equal ( var1 , [ 1.0 2.0 ] );
assert_equal ( var2 , [ 3.0 4.0 ] );
[ var1 , var2 ] = apifun_expandvar ( 1.0 , [2.0 3.0].' );
assert_equal ( var1 , [ 1.0 1.0 ].' );
assert_equal ( var2 , [ 2.0 3.0 ].' );
[ var1 , var2 ] = apifun_expandvar ( [ 1.0 2.0 ].' , 3.0 );
assert_equal ( var1 , [ 1.0 2.0 ].' );
assert_equal ( var2 , [ 3.0 3.0 ].' );

// A practical use-case.
// The function f has three arguments: x, a, b
function y = myfunction(x,a,b)
    [ a , b ] = apifun_expandvar ( a , b )
    y = x*a+b
endfunction
// Regular use
x = 2;
y = myfunction(x,1,2);
assert_equal ( y , 4 );
// Extended use-cases
y = myfunction(x,[1;2;3;4],2)
assert_equal ( y , [4;6;8;10] );
//
y = myfunction(x,2,[1;2;3;4])
assert_equal ( y , [5;6;7;8] );
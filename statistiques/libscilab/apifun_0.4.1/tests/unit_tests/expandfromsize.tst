// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2009 - 2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- JVM NOT MANDATORY -->

//
// assert_equal --
//   Returns 1 if the two real matrices computed and expected are equal.
// Arguments
//   computed, expected : the two matrices to compare
//   epsilon : a small number
//
function flag = assert_equal ( computed , expected )
  if ( and ( computed==expected ) ) then
    flag = 1;
  else
    flag = 0;
  end
  if flag <> 1 then pause,end
endfunction

///////////////////////////////////////////////////////////////////////////////////
// 
// Expand one variable
//
// Test a expanded
a = apifun_expandfromsize(1,1:6);
assert_equal ( a , 1:6 );
//
// Test with v
a = apifun_expandfromsize(1,0,[3 5]);
assert_equal ( a , zeros(3,5) );
//
//
// Test with m , n
//
// Expand a
a = apifun_expandfromsize(1,[1 2 3;4 5 6],2,3);
assert_equal ( a , [1 2 3;4 5 6] );
//
// Expand a 
a = apifun_expandfromsize(1,0,2,3);
assert_equal ( a , zeros(2,3) );
//
// a already expanded
a = apifun_expandfromsize(1,[1 2 3;4 5 6],2,3);
assert_equal ( a , [1 2 3;4 5 6] );

///////////////////////////////////////////////////////////////////////////////////
// 
// Expand two variables
//
// Test both a and b expanded
[a,b] = apifun_expandfromsize(2,1:6,(1:6).^-1);
assert_equal ( a , 1:6 );
assert_equal ( b , (1:6).^-1 );
//
// Test a expansion
[a,b] = apifun_expandfromsize(2,1,1:6);
assert_equal ( a , [1 1 1 1 1 1] );
assert_equal ( b , 1:6 );
//
// Test b expansion
[a,b] = apifun_expandfromsize(2,1:6,2);
assert_equal ( a , 1:6 );
assert_equal ( b , [2 2 2 2 2 2] );
//
// Test with v
[a,b] = apifun_expandfromsize(2,0,1,[3 5]);
assert_equal ( a , zeros(3,5) );
assert_equal ( b , ones(3,5) );
//
//
// Test with m , n
//
// Expand a
[a,b] = apifun_expandfromsize(2,1,[1 2 3;4 5 6],2,3);
assert_equal ( a , ones(2,3) );
assert_equal ( b , [1 2 3;4 5 6] );
//
// Expand b
[a,b] = apifun_expandfromsize(2,[1 2 3;4 5 6],0.1,2,3);
assert_equal ( a , [1 2 3;4 5 6] );
assert_equal ( b , 0.1*ones(2,3) );
//
// Expand a and b
[a,b] = apifun_expandfromsize(2,0,1,2,3);
assert_equal ( a , zeros(2,3) );
assert_equal ( b , ones(2,3) );
//
// a and b already expanded
[a,b] = apifun_expandfromsize(2,[1 2 3;4 5 6],[1 2 3;4 5 6],2,3);
assert_equal ( a , [1 2 3;4 5 6] );
assert_equal ( b , [1 2 3;4 5 6] );

///////////////////////////////////////////////////////////////////////////////////
//
// A practical use-case.
// The function produces normal random numbers.
function X = myfunction(mu,sigma,m,n)
    [ mu , sigma ] = apifun_expandfromsize (2,mu,sigma,m,n)
    u = grand(m,n,"unf",0,1)
    X = cdfnor("X",mu,sigma,u,1-u)
endfunction
mu = 1;
sigma = 2;
X = myfunction(mu,sigma,3,5);
assert_equal ( size(X) , [3 5] );
//
mu = 12*ones(3,5);
X = myfunction(mu,sigma,3,5);
assert_equal ( size(X) , [3 5] );
///////////////////////////////////////////////////////////////////////////////////
// 
// Expand three variables
//
// Test both a and b and c expanded
[a,b,c] = apifun_expandfromsize(3,1:6,(1:6).^-1,(1:6).^2);
assert_equal ( a , 1:6 );
assert_equal ( b , (1:6).^-1 );
assert_equal ( c , (1:6).^2 );
//
// Test a expansion
[a,b,c] = apifun_expandfromsize(3,1,1:6,(1:6).^2);
assert_equal ( a , [1 1 1 1 1 1] );
assert_equal ( b , 1:6 );
assert_equal ( c , (1:6).^2 );
//
// Test b expansion
[a,b,c] = apifun_expandfromsize(3,1:6,2,(1:6).^2);
assert_equal ( a , 1:6 );
assert_equal ( b , [2 2 2 2 2 2] );
assert_equal ( c , (1:6).^2 );
//
// Test c expansion
[a,b,c] = apifun_expandfromsize(3,1:6,(1:6).^2,2);
assert_equal ( a , 1:6 );
assert_equal ( b , (1:6).^2 );
assert_equal ( c , [2 2 2 2 2 2] );
//
// Test with v
[a,b,c] = apifun_expandfromsize(3,0,1,2,[3 5]);
assert_equal ( a , zeros(3,5) );
assert_equal ( b , ones(3,5) );
assert_equal ( c , 2*ones(3,5) );
//
//
// Test with m , n
//
// Expand a
[a,b,c] = apifun_expandfromsize(3,12,[1 2 3;4 5 6],[7 8 9;10 11 12],2,3);
assert_equal ( a , 12*ones(2,3) );
assert_equal ( b , [1 2 3;4 5 6] );
assert_equal ( c , [7 8 9;10 11 12] );
//
// Expand b
[a,b,c] = apifun_expandfromsize(3,[1 2 3;4 5 6],0.1,[7 8 9;10 11 12],2,3);
assert_equal ( a , [1 2 3;4 5 6] );
assert_equal ( b , 0.1*ones(2,3) );
assert_equal ( c , [7 8 9;10 11 12] );
//
// Expand c
[a,b,c] = apifun_expandfromsize(3,[1 2 3;4 5 6],[7 8 9;10 11 12],12,2,3);
assert_equal ( a , [1 2 3;4 5 6] );
assert_equal ( b , [7 8 9;10 11 12] );
assert_equal ( c , 12*ones(2,3) );
//
// Expand a and b and c
[a,b,c] = apifun_expandfromsize(3,0,1,2,2,3);
assert_equal ( a , zeros(2,3) );
assert_equal ( b , ones(2,3) );
assert_equal ( c , 2*ones(2,3) );
//
// a and b and c already expanded
[a,b,c] = apifun_expandfromsize(3,[1 2 3;4 5 6],[1 2 3;4 5 6],[7 8 9;10 11 12],2,3);
assert_equal ( a , [1 2 3;4 5 6] );
assert_equal ( b , [1 2 3;4 5 6] );
assert_equal ( c , [7 8 9;10 11 12] );
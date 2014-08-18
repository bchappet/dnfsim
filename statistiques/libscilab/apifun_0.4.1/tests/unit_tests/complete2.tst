// Copyright (C) 2008 - 2010 - DIGITEO - Michael Baudin
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

// A sample function with many checkings.
// An hypothetical function, where each argument is checked with one function
function y = myfunction ( x1 , x2 , x3 , x4, x5, x6 , x7 , x8 , x9 , x10 , x11 )
  [lhs, rhs] = argn()
  apifun_checkrhs ( "myfunction" , rhs , 11 )
  apifun_checklhs ( "myfunction" , lhs , 0 : 1 )
  //
  apifun_checkdims ( "myfunction" , x1 , "x1" , 1 , [12 13] )
  apifun_checkgreq ( "myfunction" , x2 , "x2" , 2 , 12.34 )
  apifun_checkloweq ( "myfunction" , x3 , "x3" , 3 , 12.34 )
  apifun_checkoption ( "myfunction" , x4 , "x4" , 4 , [12 34 56 78] )
  apifun_checkrange ( "myfunction" , x5 , "x5" , 5 , 12 , 34 )
  apifun_checkscalar ( "myfunction" , x6 , "x6" , 6 )
  apifun_checksquare ( "myfunction" , x7 , "x7" , 7 )
  apifun_checktype ( "myfunction" , x8 , "x8" , 8 , ["string" "constant"] )
  apifun_checkveccol ( "myfunction" , x9 , "x9" , 9 , 12 )
  apifun_checkvecrow ( "myfunction" , x10 , "x10" , 10 , 12 )
  apifun_checkvector ( "myfunction" , x11 , "x11" , 11 , 12 )
  y = []
endfunction

// Calling sequence which work
x1 = zeros(12,13);
x2 = 13;
x3 = 12;
x4 = 34;
x5 = 20;
x6 = 1;
x7 = zeros(12,12);
x8 = "foo";
x9 = zeros(12,1);
x10 = zeros(1,12);
x11 = zeros(12,1);
myfunction ( x1 , x2 , x3 , x4, x5, x6 , x7 , x8 , x9 , x10 , x11 );
y = myfunction ( x1 , x2 , x3 , x4, x5, x6 , x7 , x8 , x9 , x10 , x11 );
// Calling sequences which do not work
// Test one function after another, by modifying one argument after another
// This allows to check that the error messages are correctly formatted :
// but we do not check their content, which would require more testing script.
ierr=execstr("myfunction ( )","errcatch");
assert_equal ( ierr , 10000 );
ierr=execstr("myfunction ( ones(2,3) , x2 , x3 , x4, x5, x6 , x7 , x8 , x9 , x10 , x11 )","errcatch");
assert_equal ( ierr , 10000 );
ierr=execstr("myfunction ( x1 , -1 , x3 , x4, x5, x6 , x7 , x8 , x9 , x10 , x11 )","errcatch");
assert_equal ( ierr , 10000 );
ierr=execstr("myfunction ( x1 , x2 , 777 , x4, x5, x6 , x7 , x8 , x9 , x10 , x11 )","errcatch");
assert_equal ( ierr , 10000 );
ierr=execstr("myfunction ( x1 , x2 , x3 , -1 , x5, x6 , x7 , x8 , x9 , x10 , x11 )","errcatch");
assert_equal ( ierr , 10000 );
ierr=execstr("myfunction ( x1 , x2 , x3 , x4 , -1 , x6 , x7 , x8 , x9 , x10 , x11 )","errcatch");
assert_equal ( ierr , 10000 );
ierr=execstr("myfunction ( x1 , x2 , x3 , x4 , x5 , ones(12,12) , x7 , x8 , x9 , x10 , x11 )","errcatch");
assert_equal ( ierr , 10000 );
ierr=execstr("myfunction ( x1 , x2 , x3 , x4 , x5 , x6 , ones(2,3) , x8 , x9 , x10 , x11 )","errcatch");
assert_equal ( ierr , 10000 );
ierr=execstr("myfunction ( x1 , x2 , x3 , x4 , x5 , x6 , x7 , %t , x9 , x10 , x11 )","errcatch");
assert_equal ( ierr , 10000 );
ierr=execstr("myfunction ( x1 , x2 , x3 , x4 , x5 , x6 , x7 , x8 , zeros(1,12) , x10 , x11 )","errcatch");
assert_equal ( ierr , 10000 );
ierr=execstr("myfunction ( x1 , x2 , x3 , x4 , x5 , x6 , x7 , x8 , x9 , zeros(12,1) , x11 )","errcatch");
assert_equal ( ierr , 10000 );
ierr=execstr("myfunction ( x1 , x2 , x3 , x4 , x5 , x6 , x7 , x8 , x9 , zeros(12,1) , zeros(12,12) )","errcatch");
assert_equal ( ierr , 10000 );

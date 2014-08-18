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



// The function takes 2/3 input arguments and 1 output arguments
function y = myfunction ( varargin )
  [lhs, rhs] = argn()
  apifun_checkrhs ( "myfunction" , rhs , 2:3 )
  apifun_checklhs ( "myfunction" , lhs , 1 )
  x1 = varargin(1)
  x2 = varargin(2)
  if ( rhs >= 3 ) then
    x3 = varargin(3)
  else
    x3 = 2
  end
  y = x1 + x2 + x3
endfunction
// Calling sequences which work
y = myfunction ( 1 , 2 );
y = myfunction ( 1 , 2 , 3 );
// Calling sequences which generate an error
ierr=execstr("y = myfunction ( 1 )","errcatch");
assert_equal ( ierr , 10000 );
ierr=execstr("y = myfunction ( 1 , 2 , 3 , 4 )","errcatch");
assert_equal ( ierr , 10000 );
  //
// The function takes 2 or 4 input arguments, but not 3
function y = myfunction2 ( varargin )
  [lhs, rhs] = argn()
  apifun_checkrhs ( "myfunction" , rhs , [2 4] )
  apifun_checklhs ( "myfunction" , lhs , 1 )
  x1 = varargin(1)
  x2 = varargin(2)
  if ( rhs >= 3 ) then
    x3 = varargin(3)
    x4 = varargin(4)
  else
    x3 = 2
    x4 = 3
  end
  y = x1 + x2 + x3 + x4
endfunction
// Calling sequences which work
y = myfunction2 ( 1 , 2 );
y = myfunction2 ( 1 , 2 , 3 , 4 );
// Calling sequences which generate an error
ierr=execstr("y = myfunction2 ( 1 )","errcatch");
assert_equal ( ierr , 10000 );
ierr=execstr("y = myfunction2 ( 1 , 2 , 3 )","errcatch");
assert_equal ( ierr , 10000 );
ierr=execstr("y = myfunction2 ( 1 , 2 , 3 , 4, 5 )","errcatch");
assert_equal ( ierr , 10000 );
  //
// The function takes 2 input arguments and 0/1 output arguments.
// Notice that if the checkrhs function is not called,
// the variable x2 might be used from the user's context,
// that is, if the caller has defined the variable x2, it 
// is used in the callee.
// Here, we want to avoid this.
function y = myfunction3 ( x1 , x2 )
  [lhs, rhs] = argn()
  apifun_checkrhs ( "myfunction" , rhs , 2 )
  apifun_checklhs ( "myfunction" , lhs , [0 1] )
  y = x1 + x2
endfunction
// Calling sequences which work
y = myfunction3 ( 1 , 2 );
// Calling sequences which generate an error
ierr=execstr("y = myfunction3 ( 1 )","errcatch");
assert_equal ( ierr , 10000 );
ierr=execstr("y = myfunction3 ( 1 , 2 , 3 )","errcatch");
assert_equal ( ierr , 58 );

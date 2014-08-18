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



// The function takes 3 input arguments and 1/2 output arguments
function varargout = myfunction ( x1 , x2 , x3 )
  [lhs, rhs] = argn()
  apifun_checkrhs ( "myfunction" , rhs , 3 : 3 )
  apifun_checklhs ( "myfunction" , lhs , 1 : 2 )
  y1 = x1 + x2
  y2 = x2 + x3
  varargout(1) = y1
  if ( lhs == 2 ) then
    varargout(2) = y2
  end
endfunction
// Calling sequences which work
myfunction ( 1 , 2 , 3 );
y1 = myfunction ( 1 , 2 , 3 );
[ y1 , y2 ] = myfunction ( 1 , 2 , 3 );
// Calling sequences which generate an error
ierr=execstr("[ y1 , y2 , y3 ] = myfunction ( 1 , 2 , 3 )","errcatch");
assert_equal ( ierr , 10000 );
//
// The function takes 1 or 3 output arguments, but not 2
function varargout = myfunction2 ( x1 , x2 , x3 )
  [lhs, rhs] = argn()
  apifun_checkrhs ( "myfunction2" , rhs , 3 : 3 )
  apifun_checklhs ( "myfunction2" , lhs , [1 3] )
  y1 = x1 + x2
  y2 = x2 + x3
  y3 = x1 + x3
  varargout(1) = y1
  if ( lhs == 3 ) then
    varargout(2) = y2
    varargout(3) = y3
  end
endfunction
// Calling sequences which work
myfunction2 ( 1 , 2 , 3 );
y1 = myfunction2 ( 1 , 2 , 3 );
[ y1 , y2 , y3 ] = myfunction2 ( 1 , 2 , 3 );
// Calling sequences which generate an error
ierr=execstr("[y1 , y2] = myfunction2 ( 1 , 2 , 3 )","errcatch");
assert_equal ( ierr , 10000 );

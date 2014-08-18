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

// The function takes a string argument.
function myfunction ( x )
  apifun_checktype ( "myfunction" , x , "x" , 1 , "string" )
  disp("This is a string")
endfunction
// Calling sequences which work
myfunction ( "Scilab" );
// Calling sequences which generate an error
ierr=execstr("myfunction ( 123456 )","errcatch");
assert_equal ( ierr , 10000 );

//
// The function takes a string or a matrix of doubles argument.
function myfunction2 ( x )
  apifun_checktype ( "myfunction2" , x , "x" , 1 , [ "string" "constant" ] )
  if ( typeof(x) == "string" ) then
    disp("This is a matrix of strings")
  else
    disp("This is a matrix of doubles")
  end
endfunction
// Calling sequences which work
myfunction2 ( "Scilab" );
myfunction2 ( 123456 );
// Calling sequences which generate an error
ierr=execstr("myfunction2 ( uint8(2) )","errcatch");
assert_equal ( ierr , 10000 );


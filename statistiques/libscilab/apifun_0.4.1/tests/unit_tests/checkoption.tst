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


// The function takes a string argument, either "r" or "c".
function myfunction(x)
apifun_checkoption ( "myfunction" , x , "x" , 1 , ["r" "c"] )
disp("This is the string:" + x)
endfunction
// Calling sequences which work
myfunction ( "r" );
myfunction ( "c" );
// Calling sequences which generate an error
instr = "myfunction ( ""Scilab"" )";
ierr=execstr(instr,"errcatch");
assert_equal ( ierr , 10000 );

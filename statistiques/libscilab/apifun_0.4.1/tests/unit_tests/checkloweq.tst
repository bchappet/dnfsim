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


// The function takes an argument x such that x<=1.
function y=myfunction(x)
apifun_checkloweq ( "myfunction" , x , "x" , 1 , 1 )
y = sqrt(1-x)
endfunction
// Calling sequences which work
myfunction ( [-1.5,-2.5,-3.5] );
// Calling sequences which generate an error
instr = "myfunction ( [1.5,1] )";
ierr=execstr(instr,"errcatch");
assert_equal ( ierr , 10000 );
instr = "myfunction ( [1,-1,2.5,0] )";
ierr=execstr(instr,"errcatch");
assert_equal ( ierr , 10000 );

//
// Function name must be a string.
x = 1;
instr = "apifun_checkloweq ( 999 , x , ""x"" , 1 , 1 )";
ierr=execstr(instr,"errcatch");
assert_equal ( ierr , 10000 );
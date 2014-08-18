// Copyright (C) 2011 - DIGITEO - Michael Baudin
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


// The function takes a real of matrix of doubles, or anything for 
// which the isreal function can be called.
function y = myfunction ( x )
  apifun_checkcomplex ( "myfunction" , x , "x" , 1 )
  y = x
endfunction
// Calling sequences which work
y = myfunction ( %i );
y = myfunction ( [1;2;%i;4] );
// Calling sequences which generate an error
instr = "y = myfunction ( 1 )";
ierr=execstr(instr,"errcatch");
assert_equal ( ierr , 10000 );

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



// The function takes a 2 x 3 matrix of doubles.
function y=myfunction(x)
apifun_checksquare ( "myfunction" , x , "x" , 1 )
y = x
endfunction
// Calling sequences which work
y = myfunction ( ones(2,2) );
y = myfunction ( zeros(3,3) );
// Calling sequences which generate an error
instr = "y = myfunction ( ones(1,3) )";
ierr=execstr(instr,"errcatch");
assert_equal ( ierr , 10000 );
instr = "y = myfunction ( zeros(2,4) )";
ierr=execstr(instr,"errcatch");
assert_equal ( ierr , 10000 );

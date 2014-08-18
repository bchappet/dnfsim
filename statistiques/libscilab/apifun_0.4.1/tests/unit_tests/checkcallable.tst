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


function y=myf(varargin)
  // y=myf(x)
  // y=myf(x,a)
  [lhsnb,rhsnb]=argn()
  x = varargin(1)
  if ( rhsnb==1) then
    a = 2
  else
    a = varargin(2)
  end
  y = a*x
endfunction

function y=myalgorithm(f,x)
  apifun_checkcallable ( "myalgorithm" , f , "f" , 1 )
  if ( typeof(f)=="function" ) then
    __f__ = f
    __args__ = list()
  else
    __f__ = f(1)
    __args__ = f(2:$)
  end
  y = __f__(x,__args__(:))
endfunction
// Calling sequences which work
y = myalgorithm ( myf , 2 );
y = myalgorithm ( list(myf,3) , 2 );
// Calling sequences which generate an error
instr = "y = myalgorithm ( 1 , 2 )";
ierr=execstr(instr,"errcatch");
assert_equal ( ierr , 10000 );
//
instr = "y = myalgorithm ( list(3,4) , 2 )";
ierr=execstr(instr,"errcatch");
assert_equal ( ierr , 10000 );

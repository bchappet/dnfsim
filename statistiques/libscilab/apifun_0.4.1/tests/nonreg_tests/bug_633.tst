// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- JVM NOT MANDATORY -->

// <-- Non-regression test for bug 633 -->
//
// <-- Bugzilla URL -->
// http://forge.scilab.org/index.php/p/apifun/issues/633/
//
// <-- Short Description -->
//   apifun_checkgreq may produce thr wrong error message.

function flag = assert_equal ( computed , expected )
  if ( and ( computed==expected ) ) then
    flag = 1;
  else
    flag = 0;
  end
  if flag <> 1 then pause,end
endfunction

// /////////////////////////////////////////
// 
// apifun_checkgreq
//
// var matrix, thr matrix
//
var = [1 2 3 4 5 6];
thr = [1 2 3 4 7 6];
instr="apifun_checkgreq(""myfun"",var,""var"",2,thr)";
ierr = execstr(instr,"errcatch");
assert_equal ( ierr , 10000 );
//
// var matrix, thr scalar
//
var = [1 2 3 4 5 6];
thr = 5;
instr="apifun_checkgreq(""myfun"",var,""var"",2,thr)";
ierr = execstr(instr,"errcatch");
assert_equal ( ierr , 10000 );
//
// var scalar, thr scalar
//
var = 1;
thr = 5;
instr="apifun_checkgreq(""myfun"",var,""var"",2,thr)";
ierr = execstr(instr,"errcatch");
assert_equal ( ierr , 10000 );
//
// var scalar, thr matrix : impossible
//
// /////////////////////////////////////////
// 
// apifun_checkloweq
//
// var matrix, thr matrix
//
var = [1 2 3 4 5 6];
thr = [1 2 0 4 5 6];
instr="apifun_checkloweq(""myfun"",var,""var"",2,thr)";
ierr = execstr(instr,"errcatch");
assert_equal ( ierr , 10000 );
//
// var matrix, thr scalar
//
var = [1 2 3 4 5 6];
thr = 5;
instr="apifun_checkloweq(""myfun"",var,""var"",2,thr)";
ierr = execstr(instr,"errcatch");
assert_equal ( ierr , 10000 );
//
// var scalar, thr scalar
//
var = 5;
thr = 1;
instr="apifun_checkloweq(""myfun"",var,""var"",2,thr)";
ierr = execstr(instr,"errcatch");
assert_equal ( ierr , 10000 );

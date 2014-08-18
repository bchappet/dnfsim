// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- JVM NOT MANDATORY -->

// <-- Non-regression test for bug 898 -->
//
// <-- Bugzilla URL -->
// http://forge.scilab.org/index.php/p/apifun/issues/898/
//
// <-- Short Description -->
//   apifun_checkrange does not take input matrices

function flag = assert_equal ( computed , expected )
  if ( and ( computed==expected ) ) then
    flag = 1;
  else
    flag = 0;
  end
  if flag <> 1 then pause,end
endfunction

funname="foo";
varname="x";
instr = "apifun_checkrange(funname,[1 2 3],varname,1,0,[1 2 1])";
ierr = execstr(instr,"errcatch");
assert_equal ( ierr , 10000 );
// /////////////////////////////////////
//
// Check that var can be a matrix.
//
// The test pass.
//
funname="foo";
varname="x";
var = [1 2 3];
vmin = 0;
vmax = 4;
errmsg = apifun_checkrange(funname,var,varname,1,vmin,vmax);
assert_equal ( errmsg , [] );
//
// The test fail : vmin
//
funname="foo";
varname="x";
var = [1 2 3];
vmin = 2;
vmax = 4;
instr = "apifun_checkrange(funname,var,varname,1,vmin,vmax)";
ierr = execstr(instr,"errcatch");
assert_equal ( ierr , 10000 );
//
// The test fail : vmax
//
funname="foo";
varname="x";
var = [1 2 3];
vmin = 2;
vmax = 4;
instr = "apifun_checkrange(funname,var,varname,1,vmin,vmax)";
ierr = execstr(instr,"errcatch");
assert_equal ( ierr , 10000 );

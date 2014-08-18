// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- JVM NOT MANDATORY -->

// <-- Non-regression test for bug 606 -->
//
// <-- Bugzilla URL -->
// http://forge.scilab.org/index.php/p/apifun/issues/606/
//
// <-- Short Description -->
//   The error message in apifun_checkflint is wrong

function flag = assert_equal ( computed , expected )
  if ( and ( computed==expected ) ) then
    flag = 1;
  else
    flag = 0;
  end
  if flag <> 1 then pause,end
endfunction


//
funmat = [
  "apifun_checkoption"
  "apifun_checkgreq"
  "apifun_checkloweq"
  "apifun_checkrange"
  "apifun_checkflint"
  "apifun_checkreal"
  "apifun_checkcomplex"
  "apifun_checkdims"
  "apifun_checksquare"
  "apifun_checkvector"
  "apifun_checkvecrow"
  "apifun_checkveccol"
  "apifun_checkscalar"
  "apifun_argindefault"
  "apifun_expandvar"
  "apifun_expandfromsize"
  "apifun_keyvaluepairs"
  ];
for funname = funmat'
    instr=funname+"()";
    ierr = execstr(instr,"errcatch");
    assert_equal ( ierr , 10000 );
end

// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- JVM NOT MANDATORY -->

// <-- Non-regression test for bug 703 -->
//
// <-- Bugzilla URL -->
// http://forge.scilab.org/index.php/p/apifun/issues/703/
//
// <-- Short Description -->
//   apifun_checkgreq could not manage empty matrices.

function flag = assert_equal ( computed , expected )
  if ( and ( computed==expected ) ) then
    flag = 1;
  else
    flag = 0;
  end
  if flag <> 1 then pause,end
endfunction

errmsg = apifun_checkgreq( "foo" , [] , "myvar" , 2 , [] );
assert_equal ( errmsg , [] );
errmsg = apifun_checkloweq( "foo" , [] , "myvar" , 2 , [] );
assert_equal ( errmsg , [] );

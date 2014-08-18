// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- JVM NOT MANDATORY -->

// <-- Non-regression test for bug 741 -->
//
// <-- Bugzilla URL -->
// http://forge.scilab.org/index.php/p/apifun/issues/741/
//
// <-- Short Description -->
//   apifun_checkoption may fail if var is not a scalar

function flag = assert_equal ( computed , expected )
  if ( and ( computed==expected ) ) then
    flag = 1;
  else
    flag = 0;
  end
  if flag <> 1 then pause,end
endfunction

// Check that var, a vector of integers 
// contains only small primes.	
funname = "myfunction";
var = [1 3 5];
varname = "var";
ivar = 2;
expectedopt = [1 3 5 7 11 13 17 19];
instr = "apifun_checkoption ( funname , var , varname , ivar , expectedopt )";
ierr = execstr(instr,"errcatch");
assert_equal ( ierr , 10000 );

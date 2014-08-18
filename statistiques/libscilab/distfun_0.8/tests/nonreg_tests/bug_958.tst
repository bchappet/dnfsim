// Copyright (C) 2012 - Michael Baudin
//
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- Non-regression test for bug 958 -->
//
// <-- URL -->
// http://forge.scilab.org/index.php/p/distfun/issues/958
//
// <-- Short Description -->
// distfun_binopdf can fail

// <-- JVM NOT MANDATORY -->

computed=distfun_binopdf(2.e19,1.e20,0.2);
expected=9.9735570100358448449e-11;
assert_checkalmostequal(computed,expected,1.e-10);

// Copyright (C) 2012 - Michael Baudin
//
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- Non-regression test for bug 897 -->
//
// <-- URL -->
// http://forge.scilab.org/index.php/p/distfun/issues/897
//
// <-- Short Description -->
// distfun_fpdf can produce a Nan

// <-- JVM NOT MANDATORY -->

y = distfun_fpdf(1.e2,1.e200,1.e-200);
expected = 5e-203;
assert_checkalmostequal(y,expected,1.e-13);

// Copyright (C) 2012 - Michael Baudin
//
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- Non-regression test for bug 918 -->
//
// <-- URL -->
// http://forge.scilab.org/index.php/p/distfun/issues/918
//
// <-- Short Description -->
// distfun_chi2pdf can produce a NaN.

// <-- JVM NOT MANDATORY -->

y=distfun_chi2pdf(500,500);
expected=0.012611458092265071321;
assert_checkalmostequal(y,expected,1.e-11);
//
y=distfun_gampdf(500,250,2);
expected = 0.012611458092265071321;
assert_checkalmostequal(y,expected,1.e-11);

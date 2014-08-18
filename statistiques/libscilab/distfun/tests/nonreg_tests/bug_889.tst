// Copyright (C) 2012 - Michael Baudin
//
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- Non-regression test for bug 889 -->
//
// <-- URL -->
// http://forge.scilab.org/index.php/p/distfun/issues/889
//
// <-- Short Description -->
// distfun_chi2cdf is not accurate in the upper tail

// <-- JVM NOT MANDATORY -->

computed=distfun_chi2cdf(90,5,%f);
expected=6.719319e-18;
assert_checkalmostequal(computed,expected,1.e-6);

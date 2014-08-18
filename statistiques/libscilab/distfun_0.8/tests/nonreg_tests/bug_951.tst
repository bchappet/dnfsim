// Copyright (C) 2012 - Michael Baudin
//
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- Non-regression test for bug 951 -->
//
// <-- URL -->
// http://forge.scilab.org/index.php/p/distfun/issues/951
//
// <-- Short Description -->
// distfun_betapdf is wrong when a and b are large.

// <-- JVM NOT MANDATORY -->

p=distfun_betapdf(0.5,1.e10,1.e10);
assert_checkalmostequal(p,112837.9,1.e-5);
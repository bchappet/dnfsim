// Copyright (C) 2012 - Michael Baudin
//
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- Non-regression test for bug 976 -->
//
// <-- URL -->
// http://forge.scilab.org/index.php/p/distfun/issues/976
//
// <-- Short Description -->
// distfun_frnd can fail

// <-- JVM NOT MANDATORY -->

R = distfun_frnd(1.e10,1.e-10);
assert_checkequal(R,%inf);


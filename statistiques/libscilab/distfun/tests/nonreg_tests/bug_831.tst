// Copyright (C) 2012 - Michael Baudin
//
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- Non-regression test for bug 831 -->
//
// <-- URL -->
// http://forge.scilab.org/index.php/p/distfun/issues/831
//
// <-- Short Description -->
// distfun_invcdpoi fails with small values of p

// <-- JVM NOT MANDATORY -->
p = 1.e-15;
x = distfun_poissinv(p,1);
assert_checkequal(x,0);
//
p=0;
x=distfun_poissinv(p,1);
assert_checkequal(x,0);
//
p=0.3;
x=distfun_poissinv(p,1);
assert_checkequal(x,0);

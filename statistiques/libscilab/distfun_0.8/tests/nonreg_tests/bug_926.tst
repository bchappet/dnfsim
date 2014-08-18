// Copyright (C) 2012 - Michael Baudin
//
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- Non-regression test for bug 926 -->
//
// <-- URL -->
// http://forge.scilab.org/index.php/p/distfun/issues/926
//
// <-- Short Description -->
// distfun_poissinv fails for p=1.

// <-- JVM NOT MANDATORY -->

p=1;
lambda=3;
x=distfun_poissinv(p,lambda);
assert_checkequal(x,%inf);
//
q=0;
lambda=1;
x=distfun_poissinv(q,lambda,%f);
assert_checkequal(x,%inf);
// Copyright (C) 2012 - Michael Baudin
//
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- Non-regression test for bug 977 -->
//
// <-- URL -->
// http://forge.scilab.org/index.php/p/distfun/issues/977
//
// <-- Short Description -->
// distfun_binornd fails for large N

// <-- JVM NOT MANDATORY -->

// Test extreme value of N
N=1.e15;
pr=0.5;
x = distfun_binornd(N,pr);
assert_checktrue(x>=0);
assert_checktrue(x<=N);
assert_checkequal(x,floor(x));
assert_checkalmostequal(x,5.e14,1.e-4);

// Copyright (C) 2012 - Michael Baudin
//
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- Non-regression test for bug 982 -->
//
// <-- URL -->
// http://forge.scilab.org/index.php/p/distfun/issues/982
//
// <-- Short Description -->
// distfun_poissrnd fails for large lambda

// <-- JVM NOT MANDATORY -->

x=distfun_poissrnd(1.e15);
assert_checkalmostequal(x,1.e15,1.e-4);

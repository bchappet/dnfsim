// Copyright (C) 2012 - Michael Baudin
//
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- Non-regression test for bug 880 -->
//
// <-- URL -->
// http://forge.scilab.org/index.php/p/distfun/issues/880
//
// <-- Short Description -->
// distfun_exppdf did not check that x>=0.

// <-- JVM NOT MANDATORY -->

x = -10;
mu = 2;
computed = distfun_exppdf ( x , mu );
expected = 0;
assert_checkalmostequal ( computed , expected , 10*%eps );

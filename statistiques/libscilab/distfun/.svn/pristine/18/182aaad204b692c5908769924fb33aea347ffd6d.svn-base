// Copyright (C) 2012 - Michael Baudin
//
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- Non-regression test for bug 950 -->
//
// <-- URL -->
// http://forge.scilab.org/index.php/p/distfun/issues/950
//
// <-- Short Description -->
// distfun_gaminv can go into an infinite loop

// <-- JVM NOT MANDATORY -->
x = distfun_gaminv(0.7,%nan,1);
assert_checktrue(isnan(x));

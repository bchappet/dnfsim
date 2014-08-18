// Copyright (C) 2012 - Michael Baudin
//
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- Non-regression test for bug 874 -->
//
// <-- URL -->
// http://forge.scilab.org/index.php/p/distfun/issues/874
//
// <-- Short Description -->
// distfun_geoinv failed at p=1

// <-- JVM NOT MANDATORY -->

Xn=distfun_geoinv(1.,0.7);
assert_checkequal(Xn,%inf);

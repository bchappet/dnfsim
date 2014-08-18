// Copyright (C) 2012 - Michael Baudin
//
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- Non-regression test for bug 593 -->
//
// <-- URL -->
// http://forge.scilab.org/index.php/p/distfun/issues/593
//
// <-- Short Description -->
// distfun_chi2inv failed for q=0

// <-- JVM NOT MANDATORY -->

x=distfun_chi2inv(0,3,%f);
assert_checkequal(x,%inf);
//
x=distfun_chi2inv(1.e-100,3);
assert_checkalmostequal(x,5.2093970786780211218e-67,1.e-12);


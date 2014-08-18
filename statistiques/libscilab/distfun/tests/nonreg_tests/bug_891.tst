// Copyright (C) 2012 - Michael Baudin
//
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- Non-regression test for bug 891 -->
//
// <-- URL -->
// http://forge.scilab.org/index.php/p/distfun/issues/891
//
// <-- Short Description -->
// distfun_hygeinv fails for p=1

// <-- JVM NOT MANDATORY -->

// Test extreme values of p
M=1030;
k=515;
N=500;
//
x=distfun_hygeinv(0,M,k,N);
assert_checkequal(x,0);
//
x=distfun_hygeinv(1,M,k,N);
assert_checkequal(x,500);
//
x=distfun_hygeinv(0,M,k,N,%f);
assert_checkequal(x,500);
//
x=distfun_hygeinv(1,M,k,N,%f);
assert_checkequal(x,0);

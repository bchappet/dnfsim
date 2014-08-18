// Copyright (C) 2012 - Michael Baudin
//
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- Non-regression test for bug 875 -->
//
// <-- URL -->
// http://forge.scilab.org/index.php/p/distfun/issues/875
//
// <-- Short Description -->
// distfun_hygeinv produced a large value of x

// <-- JVM NOT MANDATORY -->

M=1030;
k=515;
N=500;
p=1;
x=distfun_hygeinv(p,M,k,N);
assert_checkequal(x,500);

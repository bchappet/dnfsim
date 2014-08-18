// Copyright (C) 2012 - Michael Baudin
//
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- Non-regression test for bug 882 -->
//
// <-- URL -->
// http://forge.scilab.org/index.php/p/distfun/issues/882
//
// <-- Short Description -->
// distfun_hygeinv is slow when M is large.

// <-- JVM NOT MANDATORY -->

//
// Benchmark
tic();
p=0.99;
M=1.e8;
k=1.e7;
N=1.e6;
x=distfun_hygeinv(p,M,k,N);
assert_checkequal(x,100695);
t=toc();
assert_checktrue(t<1.);

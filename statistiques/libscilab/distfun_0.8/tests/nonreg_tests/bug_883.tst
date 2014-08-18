// Copyright (C) 2012 - Michael Baudin
//
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- Non-regression test for bug 883 -->
//
// <-- URL -->
// http://forge.scilab.org/index.php/p/distfun/issues/883
//
// <-- Short Description -->
// distfun_hygecdf is slow when M is large.

// <-- JVM NOT MANDATORY -->

//
// Benchmark
tic();
M=1.e8;
k=1.e7;
N=1.e6;
x=N;
p=distfun_hygecdf(x,M,k,N);
assert_checkequal(p,1.);
t=toc();
assert_checktrue(t<1.);

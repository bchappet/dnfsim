// Copyright (C) 2012 - Michael Baudin
//
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- Non-regression test for bug 871 -->
//
// <-- URL -->
// http://forge.scilab.org/index.php/p/distfun/issues/871
//
// <-- Short Description -->
// distfun_hygeinv was too slow

// <-- JVM NOT MANDATORY -->

//
// Benchmark
tic();
y = distfun_hygeinv(0:0.01:1,80,50,30);
t=toc();
assert_checktrue(t<1.);


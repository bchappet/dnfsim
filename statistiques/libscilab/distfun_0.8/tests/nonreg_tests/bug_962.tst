// Copyright (C) 2012 - Michael Baudin
//
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- Non-regression test for bug 962 -->
//
// <-- URL -->
// http://forge.scilab.org/index.php/p/distfun/issues/962
//
// <-- Short Description -->
// distfun_hygepdf fails for large inputs

// <-- JVM NOT MANDATORY -->

x=20;
M=1.e10;
k=50;
N=4.e9;
p=distfun_hygepdf(x,M,k,N);
expected=0.11455855311592041956;
assert_checkalmostequal(p,expected,1.e-12);


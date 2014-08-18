// Copyright (C) 2012 - Michael Baudin
//
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- Non-regression test for bug 960 -->
//
// <-- URL -->
// http://forge.scilab.org/index.php/p/distfun/issues/960
//
// <-- Short Description -->
// distfun_poisspdf can fail for extreme arguments

// <-- JVM NOT MANDATORY -->

x=1.e100;
lambda=1.e100;
y=distfun_poisspdf(x,lambda);
assert_checkalmostequal(y,3.9894228040143270278e-51,1.e-12);


// Copyright (C) 2012 - Michael Baudin
//
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- Non-regression test for bug 776 -->
//
// <-- URL -->
// http://forge.scilab.org/index.php/p/distfun/issues/776
//
// <-- Short Description -->
// distfun_expinv fails with p = 1

// <-- JVM NOT MANDATORY -->

p = 1;
x = distfun_expinv ( p , 0.5 );
assert_checkequal ( x , %inf );
//
p = 0;
x = distfun_expinv ( p , 0.5 , %f );
assert_checkequal ( x , %inf );
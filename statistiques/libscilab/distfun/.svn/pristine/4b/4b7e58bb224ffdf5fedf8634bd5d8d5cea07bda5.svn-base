// Copyright (C) 2012 - Michael Baudin
//
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- Non-regression test for bug 771 -->
//
// <-- URL -->
// http://forge.scilab.org/index.php/p/distfun/issues/771
//
// <-- Short Description -->
// distfun_gaminv fails with p=1

// <-- JVM NOT MANDATORY -->

x = distfun_gaminv ( 1. , 1 , 2 );
assert_checkequal ( x , %inf );
//
x = distfun_gaminv ( 1. , 1 , 1 );
assert_checkequal ( x , %inf );
//
x = distfun_gaminv ( 1. , 1 , 0.5 );
assert_checkequal ( x , %inf );
//
x = distfun_gaminv ( 0. , 1 , 2 );
assert_checkequal ( x , 0 );

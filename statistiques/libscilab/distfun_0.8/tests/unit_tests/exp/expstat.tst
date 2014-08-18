// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

//
// <-- JVM NOT MANDATORY -->

//
// Check empty matrix
[M,V] = distfun_expstat ( [] );
assert_checkequal ( M , [] );
assert_checkequal ( V , [] );
//
[M,V] = distfun_expstat([1 10 100 1000]);
Ve = [1.    100.    10000.    1000000.];
Me = [1.    10.    100.    1000.];
assert_checkequal(M,Me);
assert_checkequal(V,Ve);

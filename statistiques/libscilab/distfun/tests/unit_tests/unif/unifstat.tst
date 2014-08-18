// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- JVM NOT MANDATORY -->

//
// Check empty matrix
[M,V] = distfun_unifstat ( [] , [] );
assert_checkequal ( M , [] );
assert_checkequal ( V , [] );
//
a = 1:6;
b = 7:12;
[M,V] = distfun_unifstat ( a , b );
me = [4.    5.    6.    7.    8.    9.];
ve = [3.    3.    3.    3.    3.    3.];
assert_checkalmostequal(M,me,[],1.e-6);
assert_checkalmostequal(V,ve,[],1.e-6);


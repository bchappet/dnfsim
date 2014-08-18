// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- JVM NOT MANDATORY -->
//
// Check empty matrix
[M,V] = distfun_wblstat ( [] , [] );
assert_checkequal ( M , [] );
assert_checkequal ( V , [] );
//
a = 1:3;
b = (1:3)^-1;
[M,V] = distfun_wblstat ( a , b );
me = [1.    4.    18.];
ve = [1.    80.    6156.];
assert_checkalmostequal(M,me,[],1.e-6);
assert_checkalmostequal(V,ve,[],1.e-6);

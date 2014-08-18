// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- JVM NOT MANDATORY -->
//
// Check empty matrix
[M,V] = distfun_betastat ( [] , [] );
assert_checkequal ( M , [] );
assert_checkequal ( V , [] );
//
a = 1:6;
b = (1:6)^-1;
[M,V] = distfun_betastat ( a , b );
me = [0.5,0.8,0.9,0.9411765,0.9615385,0.9729730];
ve = [0.0833333,0.0457143,0.0207692,0.0105454,0.0059649,0.0036693];
assert_checkalmostequal(M,me,[],1.e-6);
assert_checkalmostequal(V,ve,[],1.e-6);


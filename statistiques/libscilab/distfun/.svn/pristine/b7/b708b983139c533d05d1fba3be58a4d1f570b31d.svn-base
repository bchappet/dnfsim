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
[M,V] = distfun_unidstat ( [] );
assert_checkequal ( M , [] );
assert_checkequal ( V , [] );
//
N = 1:6;
[M,V] = distfun_unidstat ( N );
me = [1.    1.5    2.    2.5    3.    3.5];
ve = [0.    0.25    0.6666667    1.25    2.    2.9166667];
assert_checkalmostequal(M,me,[],1.e-6);
assert_checkalmostequal(V,ve,[],1.e-6);

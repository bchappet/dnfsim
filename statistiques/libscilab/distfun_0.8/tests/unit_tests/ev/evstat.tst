// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- JVM NOT MANDATORY -->

//
// Check empty matrix
[M,V] = distfun_evstat ( [] , [] );
assert_checkequal ( M , [] );
assert_checkequal ( V , [] );
//
[M,V]=distfun_evstat(5,2);
me = 3.8455687;
ve = 6.5797363;
assert_checkalmostequal(M,me,[],1.e-5);
assert_checkalmostequal(V,ve,[],1.e-5);
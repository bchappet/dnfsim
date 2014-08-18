// Copyright (C) 2012 - Prateek Papriwal
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

//
// Check empty matrix
[M,V] = distfun_poissstat ( [] );
assert_checkequal ( M , [] );
assert_checkequal ( V , [] );
// Test with expanded lambda
[m,v] = distfun_poissstat((1:6));
me = [ 1 2 3 4 5 6 ];
ve = [ 1 2 3 4 5 6 ];
assert_checkalmostequal(m,me);
assert_checkalmostequal(v,ve);
//
// Accuracy test
lambda = [ 11 22 33 ];
[M,V] = distfun_poissstat ( lambda );
ve = [ 11 22 33 ];
me = [ 11 22 33 ];
assert_checkalmostequal(M,me);
assert_checkalmostequal(V,ve);

// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

//
// Check empty matrix
[M,V] = distfun_ncx2stat ( [], [] );
assert_checkequal ( M , [] );
assert_checkequal ( V , [] );
//
// Test with expanded k
[m,v] = distfun_ncx2stat([1,2,3],[4,5,6]);
me = [5 7 9];
assert_checkalmostequal(m,me);
ve = [18 24 30];
assert_checkalmostequal(v,ve);

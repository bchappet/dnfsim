// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
//
// Check empty matrix
[M,V] = distfun_nctstat ( [], [] );
assert_checkequal ( M , [] );
assert_checkequal ( V , [] );
//
// Test with expanded args
[m,v] = distfun_nctstat([1,2,3],[4,5,6]);
me = [%nan    8.8622693    8.2918596];
assert_checkalmostequal(m,me);
ve = [%nan    %nan    42.245065];
assert_checkalmostequal(v,ve);

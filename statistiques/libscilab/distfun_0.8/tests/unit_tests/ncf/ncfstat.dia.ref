// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
//
// Check empty matrix
[M,V] = distfun_ncfstat ( [], [], [] );
assert_checkequal ( M , [] );
assert_checkequal ( V , [] );
//
// Test with expanded args
[m,v] = distfun_ncfstat([1,2,3],[4,5,6],[7,8,9]);
me = [16.    8.3333333    6.];
assert_checkalmostequal(m,me);
ve = [%nan    213.88889    57.];
assert_checkalmostequal(v,ve);

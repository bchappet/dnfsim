// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
//
// Check empty matrix
[M,V] = distfun_ksstat ( []);
assert_checkequal ( M , [] );
assert_checkequal ( V , [] );
//
// Test with expanded args
[m,v] = distfun_ksstat([1,2,3]);
me = [0.868731160636    0.614285694714    0.501562169447];
assert_checkalmostequal(m,me);
ve = [0.067773203964    0.033886601982    0.022591067988];
assert_checkalmostequal(v,ve);

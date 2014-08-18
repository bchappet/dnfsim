// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- JVM NOT MANDATORY -->

[M,V] = distfun_tstat ( [0.5 1 1.5 2 2.3 4] );
me=[%nan %nan 0. 0. 0. 0.];
ve=[%nan %nan %inf %inf 2.3/0.3 2];
assert_checkequal(M,me);
assert_checkalmostequal(V,ve);
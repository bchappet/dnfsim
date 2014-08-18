// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- JVM NOT MANDATORY -->

n = 10
P = [0.3 0.4 0.3]
[M,C] = distfun_mnstat(n,P)
Mexpected = [3 4 3]
Cexpected = [
2.1  -1.2  -0.9
-1.2   2.4  -1.2
-0.9  -1.2   2.1
]
assert_checkalmostequal(M,Mexpected);
assert_checkalmostequal(C,Cexpected);

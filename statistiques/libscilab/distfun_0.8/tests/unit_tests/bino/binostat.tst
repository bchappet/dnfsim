// Copyright (C) 2012 - Prateek Papriwal
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- JVM NOT MANDATORY -->

//
// Check empty matrix
//
[M,V] = distfun_binostat ( [],[] );
assert_checkequal ( M , [] );
assert_checkequal ( V , [] );

// Check with expanded N
N = [10 100 1000 10000];
Pr = 0.1;
[m,v] = distfun_binostat(N,Pr);
me = [1 10 100 1000];
ve = [0.9 9 90 900];

assert_checkalmostequal(m,me);
assert_checkalmostequal(v,ve);

//Check with expanded Pr
N = 100;
Pr = [0.1 0.2 0.3 0.4];
[m,v] = distfun_binostat(N,Pr);
me = [10 20 30 40];
ve = [9 16 21 24];

assert_checkalmostequal(m,me);
assert_checkalmostequal(v,ve);

//Check with both N and Pr expanded;

N = [10 100 1000 10000];
Pr = [0.1 0.2 0.3 0.4];
[m,v] = distfun_binostat(N,Pr);
me = [1 20 300 4000];
ve = [0.9 16 210 2400];

assert_checkalmostequal(m,me);
assert_checkalmostequal(v,ve);

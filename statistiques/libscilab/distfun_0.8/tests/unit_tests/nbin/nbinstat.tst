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
//
[M,V] = distfun_nbinstat ( [],[] );
assert_checkequal ( M , [] );
assert_checkequal ( V , [] );

// Check with expanded N
// Check with expanded R
R = [1 2 3 4 5];
P = 0.1;
[M,V] = distfun_nbinstat(R,P);
me = [9.    18.    27.    36.    45.];
ve = [90.    180.    270.    360.    450.];
assert_checkalmostequal(M,me);
assert_checkalmostequal(V,ve);

//Check with expanded P
R = 5;
P = [0.1 0.2 0.3 0.4];
[M,V] = distfun_nbinstat(R,P);
me = [45.    20.    11.666667    7.5];
ve = [450.    100.    38.888889    18.75];
assert_checkalmostequal(M,me,1.e-7);
assert_checkalmostequal(V,ve,1.e-7);

//Check with both R and P expanded
R = [1 2 3 4];
P = [0.1 0.2 0.3 0.4];
[M,V] = distfun_nbinstat(R,P);
me = [9.    8.    7.    6.];
ve = [90.    40.    23.333333    15.];
assert_checkalmostequal(M,me);
assert_checkalmostequal(V,ve);
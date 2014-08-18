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
[M,V] = distfun_tnormstat([],[],[],[]);
assert_checkequal ( M , [] );
assert_checkequal ( V , [] );
//
mu = 1:6;
sigma = 7:12;
a=2;
b=10;
[M,V] = distfun_tnormstat(mu,sigma,a,b);
me = [2.4804499    3.6919428    4.9034356    6.1149285    7.3264213    8.5379142];
ve = [2.6531718    3.4653673    4.3858554    5.4146364    6.55171    7.7970763];
assert_checkalmostequal(M,me,[],1.e-6);
assert_checkalmostequal(V,ve,[],1.e-6);


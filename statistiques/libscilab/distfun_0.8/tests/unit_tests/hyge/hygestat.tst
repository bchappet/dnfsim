// Copyright (C) 2012 - Prateek Papriwal
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

//
// Check empty matrix
[M,V] = distfun_hygestat ([],[],[]);
assert_checkequal ( M , [] );
assert_checkequal ( V , [] );

//Accuracy test
M = [50 60 70];
N = [20 30 40];
k = [10 25 35];
[M,V] = distfun_hygestat ( M,N,k );
ve= [1.9591837 3.7076271 4.3478261];
me = [4 12.5 20];
assert_checkalmostequal(M,me,[],%eps);
assert_checkalmostequal(V,ve,[],%eps);

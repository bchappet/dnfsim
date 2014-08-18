// Copyright (C) 2012 - Prateek Papriwal
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- Non-regression test for bug 11366 -->
//
// <-- URL -->
// http://bugzilla.scilab.org/show_bug.cgi?id=11366
//
// <-- Short Description -->
// distfun_invcdfbin(N,pr,Ompr,p,q) gives error when (1-pr) .^ N > p 

//
N = 162;
pr = 0.5;
p = 1.e-50;
x = distfun_binoinv(p,N,pr);
assert_checkequal(x,0);

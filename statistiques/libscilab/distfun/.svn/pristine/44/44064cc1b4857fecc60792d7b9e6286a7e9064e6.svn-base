// Copyright (C) 2012 - Michael Baudin
//
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- Non-regression test for bug 971 -->
//
// <-- URL -->
// http://forge.scilab.org/index.php/p/distfun/issues/971
//
// <-- Short Description -->
// distfun_betarnd fails for small inputs

// <-- JVM NOT MANDATORY -->

n=10000;
R=distfun_betarnd(1.e-50,1.e-50,n,1);
// With these parameters, the generator is just a 
// Binomial distribution, with parameters n=1 and pr=0.5.
assert_checktrue(and(R==0.|R==1.));
f0=size(find(R==0.),"*")/n;
f1=size(find(R==1.),"*")/n;
assert_checkalmostequal(f0,0.5,[],0.1);
assert_checkalmostequal(f1,0.5,[],0.1);


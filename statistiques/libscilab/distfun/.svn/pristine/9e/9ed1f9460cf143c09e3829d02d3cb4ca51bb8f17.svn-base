// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

//
// <-- JVM NOT MANDATORY -->

//
// Check empty matrix
[M,V] = distfun_lognstat ( [] , [] );
assert_checkequal ( M , [] );
assert_checkequal ( V , [] );
//
mu = 1:6;
sigma = (1:6)^-1;
[M,V] = distfun_lognstat ( mu , sigma );
Ve = [34.512613,19.911719,52.982223,204.65467,935.60263,4713.4711];
Me = [4.4816891,8.3728975,21.232978,56.331281,151.4113,409.07106];
assert_checkalmostequal(M,Me,1.e-7,[],"element");
assert_checkalmostequal(V,Ve,1.e-7,[],"element");
// See if we can recover the original parameters:
mu2 = log(M) - 0.5 * log(1+V./(M.^2));
sigma2 = sqrt(log(1+V./(M.^2)));
assert_checkalmostequal(mu,mu2,1.e-7,[],"element");
assert_checkalmostequal(sigma,sigma2,1.e-7,[],"element");

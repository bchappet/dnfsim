// Copyright (C) 2012 - 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// Caution : 
// This test may be sensitive to rounding errors, 
// initial guesses, number of iterations, etc...
// The tolerance may be increased for improved 
// portability of the test.

// Relative tolerance on x, on negative log-likelihood.
relativetol=1.e-4;

// Samples from Weibull distribution with 
// a=5432 and b=3.21
data = [
3303.  
3172.  
2473.  
5602.  
3109.  
4415.  
6471.  
5952.  
3945.  
3534.  
];
parmhat = distfun_wblfit(data);
expected=[4673.7712    3.5090456];
assert_checkalmostequal(parmhat,expected,relativetol);
// Check log-likelihood
nlogl=distfun_wbllike(parmhat,data);
assert_checkalmostequal(nlogl,85.799804,relativetol);


// /////////////////////////////////////
// Reference
// http://reliawiki.com
// "Appendix B: Parameter Estimation"
// ReliaSoft Corporation
// http://reliawiki.com/index.php/Appendix_B:_Parameter_Estimation#MLE_.28Maximum_Likelihood.29_Parameter_Estimation

// Time Failed (hrs)
// Stress 393 psi, 408 psi, 423 psi
x = [
3450 3300 2645
4340 3720 3100
4760 4180 3400
5320 4560 3800
5740 4920 4100
6160 5280 4400
6580 5640 4700
7140 6233 5100
8101 6840 5700
8960 7380 6400
];

// The reference results are:
// 393 psi: a=6692, b=3.8, negative-LogLik=88.14521
// 408 psi: a=5716, b=4.2, negative-LogLik=85.726858
// 423 psi: a=4774, b=4, negative-LogLik=84.45523

// We check two things :
// * the parameters are well estimated
// * the log-likelihood is lower than the reference 
// values
// At 393 psi for instance, the reference log-Lik is 
// 88.14, while we find 88.07, which is lower (lower is better).
//
//
// At 393 psi
// Search for the parameters
parmhat = distfun_wblfit(x(:,1));
expected=[6673.7072    4.1323689];
assert_checkalmostequal(parmhat,expected,relativetol);
// Check log-likelihood
nlogl=distfun_wbllike(parmhat,x(:,1));
assert_checkalmostequal(nlogl,88.079304,relativetol);
//
// At 408 psi
parmhat = distfun_wblfit(x(:,2));
expected=[5705.3691    4.5554921];
assert_checkalmostequal(parmhat,expected,relativetol);
// Check log-likelihood
nlogl=distfun_wbllike(parmhat,x(:,2));
assert_checkalmostequal(nlogl,85.66631,relativetol);
//
// At 423 psi
parmhat = distfun_wblfit(x(:,3));
expected=[4767.6531    4.2471909];
assert_checkalmostequal(parmhat,expected,relativetol);
// Check log-likelihood
nlogl=distfun_wbllike(parmhat,x(:,3));
assert_checkalmostequal(nlogl,84.421046,relativetol);

// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// Samples from Beta distribution with 
// a=12 and b=42
data = [
    0.2876148  
    0.3311889  
    0.2448739  
    0.1452694  
    0.1861828  
    0.2299041  
    0.1619109  
    0.3711106  
    0.2198700  
    0.2831889 
];
parmhat=distfun_betafitmm(data);
exact=[8.3582514    25.602994];
assert_checkalmostequal(parmhat,exact,1.e-6);
// Check that the parameters are equal to the 
// moments
a=parmhat(1);
b=parmhat(2);
[M,V]=distfun_betastat(a,b);
M_data=mean(data);
V_data=variance(data);
assert_checkalmostequal(M,M_data);
assert_checkalmostequal(V,V_data);

// Copyright (C) 2012 - 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

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
parmhat=distfun_wblfitmm(data);
exact=[4672.8025    3.3943375];
assert_checkalmostequal(parmhat,exact);
// Check that the parameters are equal to the 
// moments
a=parmhat(1);
b=parmhat(2);
[M,V]=distfun_wblstat(a,b);
M_data=mean(data);
V_data=variance(data);
assert_checkalmostequal(M,M_data);
assert_checkalmostequal(V,V_data);

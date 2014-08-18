// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// Samples from Gamma distribution with 
// a=12 and b=42
data = [
    547.53259  
    347.58207  
    539.14939  
    342.82908  
    508.80556  
    386.28445  
    420.16239  
    725.67277  
    409.08739  
    726.20491  
];
parmhat=distfun_gamfitmm(data);
exact=[12.182855    40.658043];
assert_checkalmostequal(parmhat,exact,1.e-6);
// Check that the parameters are equal to the 
// moments
a=parmhat(1);
b=parmhat(2);
[M,V]=distfun_gamstat(a,b);
M_data=mean(data);
V_data=variance(data);
assert_checkalmostequal(M,M_data);
assert_checkalmostequal(V,V_data);

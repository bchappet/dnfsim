// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

//
x=[
  -0.1695853  
  -0.3391686  
  -1.8270634  
  -0.4925348  
  -1.0064829  
  -1.2129676  
  -2.7551661  
   1.1071815  
   0.2974590  
  -1.0427193  
  -0.8688662  
   1.297122   
  -1.2573367  
   0.4608201  
   2.2521011  
  -0.5409517  
   1.0278198  
  -0.2396312  
   0.3987577  
   0.4915727  
];
pval=test1n(x);
pval_expected=0.4116643;
assert_checkalmostequal(pval,pval_expected,[],0.2);
// Get a 90% confidence level
[pval,cimean]=test1n(x,0.9);
assert_checkalmostequal(pval,pval_expected,[],0.2);
cimean_expected=[-0.6761787  -0.220982    0.2342147];
assert_checkalmostequal(pval,pval_expected,[],0.2);
assert_checkalmostequal(cimean,cimean_expected,[],0.2);
// Get a confidence interval for the standard
// deviation
[pval,cimean,cistd]=test1n(x);
cimean_expected=[-0.7719736  -0.220982    0.3300096];
cistd_expected=[0.8953225    1.1772962    1.7195254];
assert_checkalmostequal(pval,pval_expected,[],0.2);
assert_checkalmostequal(cimean,cimean_expected,[],0.2);
assert_checkalmostequal(cistd,cistd_expected,[],0.2);

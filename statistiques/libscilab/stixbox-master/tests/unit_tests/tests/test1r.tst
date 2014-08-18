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
pval=test1r(x);
pval_exp=0.4304333;
assert_checkalmostequal(pval,pval_exp,1.e-5);
// Get a 95% confidence interval for the
// difference of means
[pval,ranksum]=test1r(x);
assert_checkalmostequal(pval,pval_exp,1.e-5);
assert_checkequal(ranksum,83);

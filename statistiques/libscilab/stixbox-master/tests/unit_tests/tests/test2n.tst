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
y=[
  -2.0274201  
   0.9475826  
   0.7502237  
  -0.9210967  
   0.9956056  
  -1.1015345  
  -0.0710936  
  -3.0285333  
   0.0617530  
   1.3345596  
  -0.3686596  
   0.2730886  
   1.2726157  
   0.4565280  
  -0.7077206  
  -1.8243612  
   0.6221795  
   0.4206701  
  -0.6103377  
   0.4218018  
];
pval=test2n(x,y);
pval_exp=0.8603773;
assert_checkalmostequal(pval,pval_exp,1.e-5);
// Get a 95% confidence interval for the
// difference of means
[pval,cimean]=test2n(x,y);
assert_checkalmostequal(pval,pval_exp,1.e-5);
cimean_exp=[-0.8176671  -0.0657745    0.6861181];
assert_checkalmostequal(cimean,cimean_exp,1.e-5);
// Get a 90% confidence interval for the mean
[pval,cimean]=test2n(x,y,0.9);
assert_checkalmostequal(pval,pval_exp,1.e-5);
cimean_exp=[-0.6919652  -0.0657745    0.5604161];
assert_checkalmostequal(cimean,cimean_exp,1.e-5);
// Get a 95% confidence interval for the
// difference of standard deviations
[pval,cimean,cistd]=test2n(x,y);
assert_checkalmostequal(pval,pval_exp,1.e-5);
cimean_exp=[-0.8176671  -0.0657745    0.6861181];
assert_checkalmostequal(cimean,cimean_exp,1.e-5);
cistd_exp=[0.9598724    1.1745208    1.5136969];
assert_checkalmostequal(cistd,cistd_exp,1.e-5);
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
pval=test1b(x);
pval_expected=0.377;
assert_checkalmostequal(pval,pval_expected,[],0.2);
//
x=[
    2.7717259  
    1.0951327  
    3.376591   
    2.0969516  
    2.4033086  
    1.4364194  
    1.9181096  
    1.6810383  
    1.7574105  
    6.987126   
    3.6271423  
    1.0847829  
    1.6066543  
    10.869764  
    2.2253416  
    4.1545003  
    3.0943154  
    3.8255948  
    1.742948   
    5.7555665  
];
pval=test1b(x);
assert_checkequal(pval,0);
// Set the confidence level
pval=test1b(x,0.9);
assert_checkequal(pval,0);
// Set the number of bootstrap samples
pval=test1b(x,[],100);
assert_checkequal(pval,0);
// Get a confidence interval for the mean
[pval,cimean]=test1b(x);
assert_checkequal(pval,0);
cimean_expected=[2.3353305    3.1755212    5.280849];
assert_checkalmostequal(cimean,cimean_expected,[],0.5);
// Get a confidence interval for the standard 
// deviation
[pval,cimean,cistd]=test1b(x);
assert_checkequal(pval,0);
cimean_expected=[2.3353305    3.1755212    5.280849];
assert_checkalmostequal(cimean,cimean_expected,[],0.5);
cistd_expected=[1.6549289    2.3720602    5.6008671];
assert_checkalmostequal(cistd,cistd_expected,[],0.5);
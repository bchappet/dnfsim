// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

//
x=[
    2.  
    4.  
    4.  
    7.  
    2.  
    3.  
    3.  
    9.  
    6.  
    6.  
    6.  
    3.  
    4.  
    6.  
    8.  
    5.  
    2.  
    6.  
    5.  
    3.  
];
y=[
    5.  
    4.  
    6.  
    2.  
    6.  
    2.  
    4.  
    5.  
    5.  
    5.  
    5.  
    5.  
    6.  
    6.  
    6.  
    7.  
    6.  
    6.  
    4.  
    6.  
];
pval=test2r(x,y);
pval_exp=0.8830570;
assert_checkalmostequal(pval,pval_exp,1.e-5);
// Get a 95% confidence interval for the
// difference of means
[pval,ranksum]=test2r(x,y);
assert_checkalmostequal(pval,pval_exp,1.e-5);
ranksum_exp=194;
assert_checkalmostequal(ranksum,ranksum_exp,1.e-5);
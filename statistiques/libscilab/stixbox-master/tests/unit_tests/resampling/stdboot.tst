// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// Estimate the standard deviation of the
// empirical mean
x=[
    2.6393329  
    0.2261208  
    3.4090173  
    2.3128478  
    1.2304635  
    1.8089447  
    1.006141   
    1.2068124  
    2.5974333  
    8.182333   
    2.5544356  
    6.5075578  
    3.3702258  
    0.5537286  
    5.7919164  
    0.8986735  
    4.1781705  
    1.7169013  
    2.3771684  
    2.5330788  
];
s=stdboot(x,mean);
s_expected=0.4658638;
assert_checkalmostequal(s,s_expected,[],1.e-1);
// Get y
[s,y]=stdboot(x,mean);
assert_checkequal(size(y),[1 200]);
assert_checkalmostequal(s,s_expected,[],1.e-1);
// Set the number of resamples
[s,y]=stdboot(x,mean,1000);
assert_checkequal(size(y),[1 1000]);
assert_checkalmostequal(s,s_expected,[],1.e-1);

// With extra-arguments for T.
x=[
    8.2912182    2.2287117    3.1182913    3.6376971    0.3550386  
    3.0895254    3.8881868    5.3550829    1.3160918    2.114249   
    5.1184774    1.6550287    5.202322     0.5801470    2.0223306  
    3.3856066    1.7038062    6.1548482    3.1132784    4.0819703  
    3.5230084    5.445048     3.5855887    1.7651867    2.6310184  
    3.5019456    6.2341915    2.3523535    2.6677625    4.0841399  
    1.9520921    0.5623473    3.9270753    6.9021254    0.2607122  
    1.6987492    3.3535562    1.0952074    0.9997358    1.595806   
    0.6276884    0.5277657    3.389954     4.3570815    0.9862180  
    1.1637292    0.9759822    1.5614761    7.1913993    3.0743646  
    3.0941288    2.8031232    6.03235      9.1694942    4.2958465  
    5.9817386    8.8669225    6.8084521    3.695747     1.7427705  
    2.5834658    2.4073454    1.6163436    0.2578998    2.3889074  
    5.3550069    0.7228884    1.3846866    0.8572842    0.4604742  
    3.0172649    0.9484140    5.4828775    2.3738533    2.0100617  
    0.8349930    4.8563327    2.0983984    5.888336     1.0033768  
    3.4379126    3.22397      0.4467914    0.5415166    0.5112616  
    2.9298219    1.9448116    5.6819165    2.8990007    0.4742851  
    0.9655807    1.3797477    1.5733388    1.8917438    2.6273832  
    7.5528292    2.8676365    1.5391474    6.4917826    5.0687167  
];
s=stdboot(x,list(mean,"r"));
s_expected=[
    0.4549696  
    0.5124273  
    0.4586927  
    0.4140193  
    0.4495393  
];
assert_checkalmostequal(s,s_expected,[],1.e-1);



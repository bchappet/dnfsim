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
// Weibull Probability plot
h=scf();
[y,a,b]=distfun_wblplot(data);
delete(h);
b_expected  =    3.3349364;
a_expected  =    4688.7128;
y_expected  = [ 
0.0696799  
0.1784828  
0.3005855  
0.4396983  
0.6013396  
0.7942433  
1.033473   
1.348554   
1.8111776  
2.6984808  
];
assert_checkalmostequal(a,a_expected,1.e-5);
assert_checkalmostequal(b,b_expected,1.e-5);
assert_checkalmostequal(y,y_expected,1.e-5);

//////////////////////////////////////////////////
//
// Creating a Weibull probability plot
// http://www.qualitydigest.com/jan99/html/body_weibull.html
data = [
483.331
508.077
515.201
615.432
807.863
848.953
384.558
666.686
726.044
755.223
]
// Weibull plot
h=scf();
distfun_wblplot(data);
delete(h);
//////////////////////////////////////////////////
//
// http://weibull.com/hotwire/issue8/relbasics8.htm
data = [
10
20
30
40
50
80
]
h=scf();
distfun_wblplot(data);
delete(h);

//////////////////////////////////////////////////
//
// Test with Weibull random numbers
data = distfun_wblrnd(3,4,1000,1);
h=scf();
[y,a,b]=distfun_wblplot(data);
delete(h);
a_expected=3;
b_expected=4;
assert_checkalmostequal(a,a_expected,1.e-1);
assert_checkalmostequal(b,b_expected,1.e-1);
//
data = distfun_wblrnd(123,4,1000,1);
h=scf();
[y,a,b]=distfun_wblplot(data);
delete(h);
//////////////////////////////////////////////////
//
//
// Reference
// NIST/SEMATECH e-Handbook of Statistical Methods
// http://www.itl.nist.gov/div898/handbook/
// 5th October 2012
// 1.4.2.9. Fatigue Life of Aluminum Alloy Specimens
// http://www.itl.nist.gov/div898/handbook/eda/section4/eda4291.htm
data = [
370 1016 1235 1419 1567 1820 ..
706 1018 1238 1420 1578 1868 ..
716 1020 1252 1420 1594 1881 ..
746 1055 1258 1450 1602 1890 ..
785 1085 1262 1452 1604 1893 ..
797 1102 1269 1475 1608 1895 ..
844 1102 1270 1478 1630 1910 ..
855 1108 1290 1481 1642 1923 ..
858 1115 1293 1485 1674 1940 ..
886 1120 1300 1502 1730 1945 ..
886 1134 1310 1505 1750 2023 ..
930 1140 1313 1513 1750 2100 ..
960 1199 1315 1522 1763 2130 ..
988 1200 1330 1522 1768 2215 ..
990 1200 1355 1530 1781 2268 ..
1000 1203 1390 1540 1782 2440 ..
1010 1222 1416 1560 1792];
data=data(:);
h=scf();
[y,a,b]=distfun_wblplot(data);
delete(h);

// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

x=[
    0.5731088  
    0.5567597  
    0.6951144  
    0.6284627  
    0.6646766  
    0.0932322  
    0.8116149  
    0.8401216  
    0.2719183  
    0.7187929  
];
c=covjack(x,mean);
c_expected=0.005505529126;
assert_checkalmostequal(c,c_expected);
// Get y
[c,y]=covjack(x,mean);
assert_checkalmostequal(c,c_expected);
assert_checkequal(size(y),[1 10]);

// With extra-arguments for T.
x=[
    1.4254073    1.6884499    1.4012969    2.312359     0.6331118  
    3.3127547    1.9552435    0.4461367    2.1383587    2.0584742  
    4.1381414    1.1603499    2.0575018    2.564279     1.5429349  
    0.4446097    2.8546909    3.8129657    0.177915     3.5645813  
    3.3908827    3.113864     5.1757743    1.2022693    2.1570548  
    4.3424844    0.8636220    0.7481425    0.9018484    3.5462795  
    2.8058487    6.0101481    0.6275667    1.3840561    1.7158205  
    3.1201641    0.3259822    0.2721170    1.1321224    0.3515333  
    5.5860219    8.538204     0.8062426    2.9958361    1.1414438  
    0.6783368    1.6336897    0.2314132    1.2619232    2.2822206  
    2.7557388    1.033358     5.0131662    1.4944239    1.9130233  
    3.2139182    3.253465     1.2316916    0.0569286    0.0022447  
    0.9669743    2.3347997    5.6655815    4.2669204    0.7918427  
    0.9743810    1.3696595    1.8639659    0.4896139    2.3730863  
    3.8285409    1.9878576    3.5380315    0.7932832    0.5038676  
    2.7186821    1.7928093    9.3328156    4.874941     1.8856068  
    4.4949396    8.01871      2.4218953    4.5757676    2.1004731  
    4.8984447    4.3384929    0.8371266    0.5933142    1.7458037  
    4.7951066    9.7869758    2.1619115    1.8503415    1.9054893  
    2.6838904    9.6964835    0.5293674    6.4832144    1.0862202  
];
c=covjack(x,list(mean,"r"));
c_expected=[
   0.1124722   0.1006399  -0.0352479   0.0083356  -0.0083222  
   0.1006399   0.4737684  -0.0799208   0.1217479  -0.0096695  
  -0.0352479  -0.0799208   0.2809895   0.0547672   0.0103239  
   0.0083356   0.1217479   0.0547672   0.1527740  -0.0148831  
  -0.0083222  -0.0096695   0.0103239  -0.0148831   0.0449554  
];
assert_checkalmostequal(c,c_expected,1e-5);

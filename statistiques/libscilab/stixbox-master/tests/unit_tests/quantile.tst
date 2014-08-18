// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt


x=[
   0.4827129   0.3431706  -0.4127328    0.3843994  
  -0.7107495  -0.2547306   0.0290803    0.1386087  
  -0.7698385   1.0743628   1.0945652    0.4365680  
  -0.5913411  -0.7426987   1.609719     0.8079680  
  -2.1700554  -0.7361261   0.0069708    1.4626386  
];
// Make a column vector:
x=x(:);
p=linspace(0.1,0.9,10)';
q=quantile(x,p);
expected=[
  -0.7562686  
  -0.7290770  
  -0.5814184  
  -0.2810643  
   0.0204822  
   0.2181605  
   0.3930942  
   0.4801493  
   1.0003642  
   1.2786019  
];
assert_checkalmostequal(q,expected,1.e-5);
//
q=quantile(x,p,1);
assert_checkalmostequal(q,expected,1.e-5);
//
q=quantile(x,p,2);
expected=[
  -0.7671245  
  -0.7363452  
  -0.6112425  
  -0.3021313  
   0.0194995  
   0.2272522  
   0.4000500  
   0.5369221  
   1.0750362  
   1.4258313  
];
assert_checkalmostequal(q,expected,1.e-5);
//
q=quantile(x,p,3);
expected=[
  -0.7698385  
  -0.7361261  
  -0.5913411  
  -0.2547306  
   0.0290803  
   0.1386087  
   0.3843994  
   0.4827129  
   1.0743628  
   1.0945652  
];
assert_checkalmostequal(q,expected,1.e-5);

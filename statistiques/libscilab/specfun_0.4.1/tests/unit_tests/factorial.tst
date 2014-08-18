// Copyright (C) 2010 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
//
// <-- JVM NOT MANDATORY -->
// <-- ENGLISH IMPOSED -->


//
// Values of n for which factorial is exact.
// Exact values from Wolfram Alpha.
expected = [
    0.     1.                          
    1.     1.                          
    2.     2.                          
    3.     6.                          
    4.     24.                         
    5.     120.                        
    6.     720.                        
    7.     5040.                       
    8.     40320.                      
    9.     362880.                     
    10.    3628800.                    
    11.    39916800.                   
    12.    479001600.                  
    13.    6227020800.                 
    14.    87178291200.                
    15.    1307674368000.              
    16.    20922789888000.             
];
computed = specfun_factorial(expected(:,1));
assert_checkequal ( computed , expected(:,2) );
//
// Values of n for which factorial is approximate.
// Exact values from Wolfram Alpha.
//
expected = [
  17 355687428096000
  18 6402373705728000
  19 121645100408832000
  20 2432902008176640000
  21 51090942171709440000
  22 1124000727777607680000
  23 25852016738884976640000
  24 620448401733239439360000
  25 15511210043330985984000000
  26 403291461126605635584000000
  50 3.04140932017133780436126e64
  100 9.332621544394415268e157
  130 6.466855489220473672e219
  170 7.257415615307998967e306
];
ntests = size(expected,"r");
for k = 1 : ntests
  n = expected(k,1);
  f = expected(k,2);
  computed = specfun_factorial(n);
  if ( %f ) then
    r = ceil(abs(computed-f)/f/%eps);
    mprintf("n=%d, r=%d\n",n,r)
  end
  assert_checkalmostequal ( computed, f, 2^9*%eps );
end
//
assert_checkequal ( specfun_factorial(171) , %inf );
assert_checkequal ( specfun_factorial(1.e307) , %inf );
assert_checkequal ( specfun_factorial(%inf) , %inf );


// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 2009 - Pascal Getreuer
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
//
// <-- JVM NOT MANDATORY -->
// <-- ENGLISH IMPOSED -->

////////////////////////////////////////////////////////////////////////
// 
// Check Argument Checking
//
assert_checkerror ( "specfun_lambertw()" , "specfun_lambertw: Unexpected number of input arguments : 0 provided while the number of expected input arguments should be in the set [1 2]." );
assert_checkerror ( "specfun_lambertw(1,2,3)" , "specfun_lambertw: Unexpected number of input arguments : 3 provided while the number of expected input arguments should be in the set [1 2]." );
assert_checkerror ( "specfun_lambertw(""a"")" , "specfun_lambertw: Expected type [""constant""] for input argument z at input #1, but got ""string"" instead." );
assert_checkerror ( "specfun_lambertw(1,""a"")" , "specfun_lambertw: Expected type [""constant""] for input argument b at input #2, but got ""string"" instead." );

////////////////////////////////////////////////////////////////////////
// 
// Check robustness
computed = specfun_lambertw([]);
assert_checkequal ( computed , [] );
//
// IEEE values
computed = specfun_lambertw([+0 -0 %inf -%inf %nan] );
expected = complex([0 0 %inf %nan %nan],0);
assert_checkequal ( computed , expected );
//
computed = specfun_lambertw(%inf,(-4:4));
expected = complex([%nan %nan %nan %nan %inf %nan %nan %nan %nan],0);
assert_checkequal ( computed , expected );
//
computed = specfun_lambertw(-%inf,(-4:4));
expected = complex(%nan*ones(1,9),0);
assert_checkequal ( computed , expected );
//
computed = specfun_lambertw(%nan,(-4:4));
expected = complex([%nan %nan %nan %nan %nan %nan %nan %nan %nan],0);
assert_checkequal ( computed , expected );
//
computed = specfun_lambertw(+0,(-4:4));
expected = complex([%inf %inf %inf -%inf 0 %inf %inf %inf %inf],0);
assert_checkequal ( computed , expected );
//
computed = specfun_lambertw(-0,(-4:4));
expected = complex([%inf %inf %inf -%inf 0 %inf %inf %inf %inf],0);
assert_checkequal ( computed , expected );
//
// Basic test
c = specfun_lambertw([0 -exp(-1); %pi 1]);
e = [
    0                     -1.                   
    1.07365819479614921    0.56714329040978384  
];
assert_checkalmostequal ( c , e , %eps );
//
// Compute Wk(1), for k=-4,-3,...,4
w = specfun_lambertw(1,(-4:4)');
e = [
-3.162952738804083896D+00-%i*2.342774750375521364D+01;
-2.853581755409037690D+00-%i*1.711353553941214756D+01;
-2.401585104868003029D+00-%i*1.077629951611507053D+01;
-1.533913319793574370D+00-%i*4.375185153061898369D+00;
5.671432904097838401D-01;
-1.533913319793574370D+00+%i*4.375185153061898369D+00;
-2.401585104868003029D+00+%i*1.077629951611507053D+01;
-2.853581755409037690D+00+%i*1.711353553941214756D+01;
-3.162952738804083896D+00+%i*2.342774750375521364D+01
];
assert_checkalmostequal ( w , e , %eps );

// *** Inversion Error Test ***
// Ideally, lambertw(z,b)*exp(lambertw(z,b)) = z for any complex z
// and any integer branch index b, but this is limited by machine
// precision.  The inversion error |lambertw(z,b)*exp(lambertw(z,b)) - z|
// is small but worth minding.
//   
// Experimentation finds that the error is usually on the order of 
// |z|*1e-16 on the principal branch.  This test computes the inversion
// error over the square [-10,10]x[-10,10] in the complex plane, large
// enough to characterize the error away from the branch points at
// z = 0 and -1/e.

// Use NxN points to sample the complex plane
N = 81;    
// Sample in the square [-R,R]x[-R,R]
R = 10;    
x = linspace(-R,R,N);
y = linspace(-R,R,N);
[xx,yy] = meshgrid(x,y);
z = xx + %i*yy;

for b = -4:4
  w = specfun_lambertw(z,b);
  InvError = abs(w.*exp(w) - z);
  assert_checktrue ( max(InvError) < 1.e-13 );
end



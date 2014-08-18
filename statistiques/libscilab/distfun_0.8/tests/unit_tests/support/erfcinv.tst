
// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2008-2009 - INRIA - Michael Baudin
// Copyright (C) 2010 - INRIA - Michael Baudin
// Copyright (C) 2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt



//
// <-- JVM NOT MANDATORY -->


////////////////////////////////////////////////////////////////////////
// 
// Check Argument Checking
//
assert_checkerror ( "distfun_erfcinv()" , "distfun_erfcinv: Unexpected number of input arguments : 0 provided while the number of expected input arguments should be in the set [1]." );
assert_checkerror ( "distfun_erfcinv(1,2)" , "Wrong number of input arguments." );
assert_checkerror ( "distfun_erfcinv(""a"")" , "distfun_erfcinv: Expected type [""constant""] for input argument x at input #1, but got ""string"" instead." );
////////////////////////////////////////////////////////////////////////
// 
// Check robustness
computed = distfun_erfcinv([]);
assert_checkequal ( computed , [] );
//
// IEEE values
computed = distfun_erfcinv([%inf -%inf]);
assert_checkequal ( computed , [%nan %nan] );
//
// IEEE values
computed = distfun_erfcinv([+0 -0 %nan]);
assert_checkequal ( computed , [%inf %inf %nan] );
//
// Out-of-bounds
computed = distfun_erfcinv([-1 -2 3 4]);
assert_checkequal ( computed , [%nan %nan %nan %nan] );
//
// A complex
assert_checkerror ( "distfun_erfcinv(1+%i)","distfun_erfcinv: Wrong type for input argument #1: Real matrix expected.");
//
// A row vector
computed = distfun_erfcinv([0.1 0.2 0.3 0.9]);
expected = [
  1.163087153676674   
  0.906193802436823   
  0.732869077959217
  0.088855990494258
]';
assert_checkalmostequal ( computed , expected , 100*%eps );
//
// A column vector
computed = distfun_erfcinv([0.1 0.2 0.3 0.9]');
expected = [
  1.163087153676674   
  0.906193802436823   
  0.732869077959217
  0.088855990494258
];
assert_checkalmostequal ( computed , expected , 100*%eps );
//
// A matrix
computed = distfun_erfcinv(0.5*ones(2,3));
expected = 0.476936276204470 * ones(2,3);
assert_checkalmostequal ( computed , expected , 100*%eps );
////////////////////////////////////////////////////////////
//
// Some simple accuracy tests
// x = M*2^e
// [x M e fexact digits]
table = [
1.e-3     4611686018427388 -62  2.32675376551352466      15
1.e-6     4722366482869645 -72  3.45891073727950002      15
1.e-9     4835703278458517 -82  4.32000538491344527      15
1.e-12    4951760157141521 -92  5.04202974563905937      15
1.e-20    6646139978924579 -119 6.60158062235514256      15
1-1.e-5   9007109182748445 -53  8.86226925471926138e-6   10
1-1.e-10  9007199253840272 -53  8.86226998779502614e-11   6
1-1.e-15  9007199254740983 -53  8.85518583912372716e-16   0
1+1.e-15  4503599627370501 -52 -9.83909537680414129e-16   0
1+1.e-10  4503599627820856 -52 -8.86226998779502614e-11   6
1+1.e-5   4503644663366770 -52 -8.86226925475959380e-6   10
2-1.e-3   9002695655113622 -52 -2.32675376551354658      14
2-1.e-6   9007194751141365 -52 -3.45891073729094712      11
2-1.e-9   9007199250237392 -52 -4.32000537557531493       8
2-1.e-12  9007199254736488 -52 -5.04202109411347248       5
2         4503599627370496 -51 -%inf                     15
];
ntests = size(table,"r");
x = table(:,1);
computed = distfun_erfcinv(x);
expected = table(:,4);
digits = assert_computedigits ( computed , expected );
requireddigits = table(:,5);
assert_checktrue ( digits >= requireddigits );
if ( %f ) then
  for i = 1 : ntests
    mprintf("Test #%2d/%2d, x=%.5e, y=%.5e, e=%.5e, d=%3.1f\n", ..
      i, ntests, x(i), computed(i), expected(i) , digits(i));
  end
end





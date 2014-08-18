// =============================================================================

// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2008 - INRIA - Sabine Gaüzere
// Copyright (C) 2010-2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
// =============================================================================
// <-- JVM NOT MANDATORY -->


path = distfun_getpath (  );
exec(fullfile(path,"tests","unit_tests","testingutilities.sce"));

//
// These tests makes checks that all uniform generators are correct.
// We run the following actions : "setgen", "getgen", "setsd","getsd"
// We also check the first ten uniform numbers from each generator with a known seed.
// seedsize : size of the state for each generator
// MinInt : minimum of the uniform integer interval for random number generation
// MaxInt : maximum of the uniform integer interval for random number generation
//
NGen = 6;
generators = ["mt"   "kiss" "clcg2"    "clcg4" "fsultra" "urand"];
seedsize =   [625    4      2          4       40        1];
MaxInt =     [2^32-1 2^32-1 2147483561 2^31-2  2^32-1    2^31-1];

rtol = 1.e-1;
atol = 1.e-1;

// The number of classes in the histogram
// NC must be even.
NC = 2*12;
N=10000;

//
// The default generator must be Mersenne-Twister
S=distfun_genget();
assert_checkequal ( S , "mt" );

// The maximum integer generable with uin option
UinMax = 2147483560;

////////////////////////////////////////////////////////////////////
//
// "mt"
//
kgen = 1;
gen = "mt";
sdsize = seedsize(kgen);
distfun_genset(gen);
S=distfun_genget();
assert_checkequal ( S , gen );
//
distfun_seedset(0);
S=distfun_seedget();
assert_checkequal ( typeof(S) , "constant" );
assert_checkequal ( size(S) , [sdsize 1] );
//
distfun_seedset(123456);
S=distfun_seedget();
assert_checkequal ( typeof(S) , "constant" );
assert_checkequal ( size(S) , [sdsize 1] );
//
// Check numbers
expected = [
0.5488135    0.6027634    0.4236548    0.4375872    0.9636628    0.7917250  
0.5928446    0.8579456    0.6235637    0.2975346    0.2726563    0.8121687  
0.7151894    0.5448832    0.6458941    0.891773     0.3834415    0.5288949  
0.8442657    0.8472517    0.3843817    0.0567130    0.4776651    0.4799772  
];
distfun_seedset(0);
computed = distfun_unifrnd(0,1,4,6);
assert_checkalmostequal ( computed , expected, 1.e-6 );
//
// Check distribution of uniform numbers in [0,1[
// Check distribution of uniform integers in [A,B]
checkUniformity(kgen,rtol,atol,N,NC,UinMax);

////////////////////////////////////////////////////////////////////
//
// "kiss"
//
kgen = 2;
gen = "kiss";
sdsize = seedsize(kgen);
distfun_genset(gen);
S=distfun_genget();
assert_checkequal ( S , gen );
//
distfun_seedset(0,0,0,0);
S=distfun_seedget();
assert_checkequal ( typeof(S) , "constant" );
assert_checkequal ( size(S) , [sdsize 1] );
//
distfun_seedset(123456,123456,123456,123456);
S=distfun_seedget();
assert_checkequal ( typeof(S) , "constant" );
assert_checkequal ( size(S) , [sdsize 1] );
//
// Check numbers
expected = [
2.874450D-04    9.423555D-01    7.707249D-02    2.078324D-02    4.746445D-01    1.895302D-01  
8.538282D-01    5.493145D-01    3.200836D-01    4.775516D-01    2.245108D-01    6.637360D-01  
5.815227D-02    6.006782D-01    8.569004D-01    1.235649D-02    7.357421D-01    5.837571D-01  
5.196679D-01    2.448867D-01    2.568304D-01    4.503826D-01    9.680347D-01    5.214808D-01  
];
distfun_seedset(0,0,0,0);
computed = distfun_unifrnd(0,1,4,6);
assert_checkalmostequal ( computed , expected, 1.e-6 );
//
// Check distribution of uniform numbers in [0,1[
// Check distribution of uniform integers in [A,B]
checkUniformity(kgen,rtol,atol,N,NC,UinMax);
////////////////////////////////////////////////////////////////////
//
// "clcg2"
//
kgen = 3;
gen = "clcg2";
sdsize = seedsize(kgen);
distfun_genset(gen);
S=distfun_genget();
assert_checkequal ( S , gen );
//
distfun_seedset(1,1);
S=distfun_seedget();
assert_checkequal ( typeof(S) , "constant" );
assert_checkequal ( size(S) , [sdsize 1] );
//
distfun_seedset(123456,123456);
S=distfun_seedget();
assert_checkequal ( typeof(S) , "constant" );
assert_checkequal ( size(S) , [sdsize 1] );
//
// Check numbers
expected = [
0.9999997    0.0369445    0.2041364    0.9100817    0.6998243    0.9596867  
0.9745196    0.1617119    0.1673069    0.1117162    0.9502824    0.9149753  
0.6474839    0.6646450    0.6549574    0.2990212    0.0918107    0.4411791  
0.3330856    0.0846729    0.1288161    0.2654475    0.9023415    0.0735483  
];
distfun_seedset(1,1);
computed = distfun_unifrnd(0,1,4,6);
assert_checkalmostequal ( computed , expected, 1.e-5 );
//
// Check distribution of uniform numbers in [0,1[
// Check distribution of uniform integers in [A,B]
checkUniformity(kgen,rtol,atol,N,NC,UinMax);
////////////////////////////////////////////////////////////////////
//
// "clcg4"
//
kgen = 4;
gen = "clcg4";
sdsize = seedsize(kgen);
distfun_genset(gen);
S=distfun_genget();
assert_checkequal ( S , gen );
//
distfun_seedset(1,1,1,1);
S=distfun_seedget();
assert_checkequal ( typeof(S) , "constant" );
assert_checkequal ( size(S) , [sdsize 1] );
//
distfun_seedset(123456,123456,123456,123456);
S=distfun_seedget();
assert_checkequal ( typeof(S) , "constant" );
assert_checkequal ( size(S) , [sdsize 1] );
//
// Check numbers
expected = [
0.9999661    0.0552914    0.6345306    0.0640227    0.2885048    0.2781458  
0.6852419    0.1806991    0.8665501    0.0981421    0.2660715    0.4279616  
0.4370514    0.4956021    0.6870544    0.8501209    0.1271038    0.4554926  
0.4202952    0.2903676    0.5712601    0.4764120    0.1818799    0.3121748  
];
distfun_seedset(1,1,1,1);
computed = distfun_unifrnd(0,1,4,6);
assert_checkalmostequal ( computed , expected, 1.e-6 );
//
// Check distribution of uniform numbers in [0,1[
// Check distribution of uniform integers in [A,B]
checkUniformity(kgen,rtol,atol,N,NC,UinMax);
////////////////////////////////////////////////////////////////////
//
// "fsultra"
//
kgen = 5;
gen = "fsultra";
sdsize = seedsize(kgen);
distfun_genset(gen);
S=distfun_genget();
assert_checkequal ( S , gen );
//
distfun_seedset(1,1);
S=distfun_seedget();
assert_checkequal ( typeof(S) , "constant" );
assert_checkequal ( size(S) , [sdsize 1] );
//
distfun_seedset(123456,123456);
S=distfun_seedget();
assert_checkequal ( typeof(S) , "constant" );
assert_checkequal ( size(S) , [sdsize 1] );
//
// Check numbers
expected = [
0.3314877    0.3699260    0.4383216    0.99706      0.0577929    0.4836669  
0.5826624    0.9600475    0.2037475    0.6774254    0.4450278    0.3082941  
0.1630857    0.2033307    0.4214824    0.6372521    0.0782678    0.4409892  
0.7211611    0.1833922    0.8065496    0.6375251    0.2572713    0.8039582  
];
distfun_seedset(1,1);
computed = distfun_unifrnd(0,1,4,6);
assert_checkalmostequal ( computed , expected, 1.e-6 );
//
// Check distribution of uniform numbers in [0,1[
// Check distribution of uniform integers in [A,B]
checkUniformity(kgen,rtol,atol,N,NC,UinMax);
////////////////////////////////////////////////////////////////////
//
// "urand"
//
kgen = 6;
gen = "urand";
distfun_genset(gen);
S=distfun_genget();
assert_checkequal ( S , gen );
//
distfun_seedset(1);
S=distfun_seedget();
assert_checkequal ( S , 1 );
//
distfun_seedset(123456);
S=distfun_seedget();
assert_checkequal ( S , 123456 );
//
// Check numbers
expected = [
0.6040239    0.5321420    0.2276811    0.8979351    0.8925854    0.6928366  
0.0079647    0.4138784    0.6656067    0.8274020    0.0848163    0.6776849  
0.6643966    0.5036204    0.9694369    0.0664231    0.2566682    0.4284010  
0.9832111    0.6850569    0.0775390    0.1099766    0.6507795    0.3725794  
];
distfun_seedset(1);
computed = distfun_unifrnd(0,1,4,6);
assert_checkalmostequal ( computed , expected, 1.e-5 );
//
// Check distribution of uniform numbers in [0,1[
// Check distribution of uniform integers in [A,B]
checkUniformity(kgen,rtol,atol,N,NC,UinMax);

// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

//
// <-- JVM NOT MANDATORY -->
// <-- ENGLISH IMPOSED -->

path = distfun_getpath (  );
exec(fullfile(path,"tests","unit_tests","testingutilities.sce"));

////////////////////////////////////////////////////////////////////////
// 
// Check Argument Checking
//
assert_checkerror ( "distfun_gammainc()" , "distfun_gammainc: Unexpected number of input arguments : 0 provided while the number of expected input arguments should be in the set [2 3]." );
assert_checkerror ( "distfun_gammainc(1)" , "distfun_gammainc: Unexpected number of input arguments : 1 provided while the number of expected input arguments should be in the set [2 3]." );
assert_checkerror ( "distfun_gammainc(""a"",2)" , "distfun_gammainc: Expected type [""constant""] for input argument x at input #1, but got ""string"" instead." );
assert_checkerror ( "distfun_gammainc(1,""a"")" , "distfun_gammainc: Expected type [""constant""] for input argument a at input #2, but got ""string"" instead." );

//
// Show vectorized support
//
// Show expansion of a
x = [1 2 3;4 5 6];
a = 2;
computed = distfun_gammainc(x,a);
expected = [
    2.642411176571152764D-01    5.939941502901617820D-01    8.008517265285441944D-01  
    9.084218055563291205D-01    9.595723180054872570D-01    9.826487347633354741D-01  
];
assert_checkalmostequal ( computed , expected , 10 * %eps );
//
// Show expansion of x
x = 2;
a = [1 2 3;4 5 6];
computed = distfun_gammainc(x,a);
expected = [
    8.646647167633872977D-01    5.939941502901617820D-01    3.233235838169365439D-01  
    1.428765395014529316D-01    5.265301734371115316D-02    1.656360848061444804D-02  
];
assert_checkalmostequal ( computed , expected , 10 * %eps );
//
// Check error when size of a and x do not match
x = [1 2 3 4 5 6];
a = [1 2 3;4 5 6];
assert_checkerror ( "computed = distfun_gammainc(x,a);" , "apifun_expandvar: Expected 1 rows in input argument #2, but found 2 rows instead." );

//
// Test accuracy of distfun_gammainc
//
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","support","gammainc.dataset.csv");
table = readCsvDataset(dataset);
precision = 1.e-9;
nt = size(table,"r");
for k = 1 : nt
    x = table(k,1);
    a = table(k,2);
    p = table(k,3);
    q = table(k,4);
    computedP = distfun_gammainc ( x , a );
    assert_checkalmostequal ( computedP , p , precision );
    computedQ = distfun_gammainc ( x , a , %f );
    assert_checkalmostequal ( computedQ , q , precision );
    if ( %f ) then
        dp = assert_computedigits ( computedP , p );
        dq = assert_computedigits ( computedQ , q );
        mprintf("Test #%d/%d: P Digits = %.1f, Q Digits = %.1f\n",k,nt,dp,dq);
    end
end
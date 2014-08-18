// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2009 - 2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- JVM NOT MANDATORY -->

path = distfun_getpath (  );
exec(fullfile(path,"tests","unit_tests","testingutilities.sce"));

//
// Consistency Checks
//
a=5;
b=6;
x=[
    0.2673181  
    0.3725816  
    0.4516942  
    0.5324996  
    0.6457841  
];
rtol=1.e-12;
CheckCDF("distfun_betacdf",list(distfun_betacdf,a,b),x,rtol);

//
// Check bounds of x
instr = "computed = distfun_betacdf(-1,2,3)";
msg = "distfun_betacdf: Expected that all entries of input argument x at input #1 are in the range [0,1], but entry #1 is equal to -1.";
assert_checkerror(instr,msg);
//
// Check bounds of x
instr = "computed = distfun_betacdf(2,2,3)";
msg = "distfun_betacdf: Expected that all entries of input argument x at input #1 are in the range [0,1], but entry #1 is equal to 2.";
assert_checkerror(instr,msg);

// Check when a=0
p=distfun_betacdf ( 0.2,0.,2,%t );
assert_checkequal(p,1.);

//
// Test accuracy of distfun_betacdf
//
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","beta","beta.dataset.csv");
table = readCsvDataset(dataset);
precision = 1.e-14;
nt = size(table,"r");
for k = 1 : nt
    x = table(k,1);
    a = table(k,2);
    b = table(k,3);
    p = table(k,5);
    q = table(k,6);
    computedP = distfun_betacdf ( x , a , b );
    assert_checkalmostequal ( computedP , p , precision );
    computedQ = distfun_betacdf ( x , a , b , %f );
    assert_checkalmostequal ( computedQ , q , precision );
    if ( %f ) then
        dp = assert_computedigits ( computedP , p );
        dq = assert_computedigits ( computedQ , q );
        mprintf("Test #%d/%d: P Digits = %.1f, Q Digits = %.1f\n",k,nt,dp,dq);
    end
end

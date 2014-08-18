// Copyright (C) 2014 - Michael Baudin
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
CheckCDF("distfun_wblcdf",list(distfun_wblcdf,a,b),x,rtol);

//
// Check bounds of x
instr = "computed = distfun_wblcdf(-1,2,3)";
msg = "distfun_wblcdf: Expected that all entries of input argument x at input #1 are greater or equal than 0, but entry #1 is equal to -1.";
assert_checkerror(instr,msg);

//
// Test accuracy of distfun_wblcdf
//
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","wbl","wbl.dataset.csv");
table = readCsvDataset(dataset);
precision = 1.e-15;
nt = size(table,"r");
for k = 1 : nt
    x = table(k,1);
    a = table(k,2);
    b = table(k,3);
    p = table(k,5);
    q = table(k,6);
    computedP = distfun_wblcdf ( x , a , b );
    assert_checkalmostequal ( computedP , p , precision );
    computedQ = distfun_wblcdf ( x , a , b , %f );
    assert_checkalmostequal ( computedQ , q , precision );
    if ( %f ) then
        dp = assert_computedigits ( computedP , p );
        dq = assert_computedigits ( computedQ , q );
        mprintf("Test #%d/%d: P Digits = %.1f, Q Digits = %.1f\n",k,nt,dp,dq);
    end
end

// Copyright (C) 2012 - Michael Baudin
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
a=4;
b=6;
x=[
    4.2  
    4.6  
    5.   
    5.4  
    5.8  
];
rtol=1.e-12;
CheckCDF("distfun_unifcdf",list(distfun_unifcdf,a,b),x,rtol);

// See upper tail
p = distfun_unifcdf ( 1.7, 1., 2. );
assert_checkalmostequal ( p , 0.7 , %eps );
q = distfun_unifcdf ( 1.7, 1., 2. , %f );
assert_checkalmostequal ( q , 0.3 , %eps );

//
// Test accuracy
//
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","unif","uniform.dataset.csv");
table = readCsvDataset(dataset);
precision = 1.e-12;
ntests = size(table,"r");
for i = 1 : ntests
    x = table(i,1);
    a = table(i,2);
    b = table(i,3);
    p = table(i,5);
    q = table(i,6);
    computed = distfun_unifcdf ( x , a , b );
    assert_checkalmostequal ( computed , p , precision );
    if ( %f ) then
        d = assert_computedigits ( computed , p );
        mprintf("Test #%d/%d: Digits = %.1f\n",i,ntests,d);
    end
    computed = distfun_unifcdf ( x , a,b , %f );
    assert_checkalmostequal ( computed , q , precision );
end
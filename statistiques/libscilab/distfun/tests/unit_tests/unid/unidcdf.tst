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
N=4;
x=[1 2 3 4];
rtol=1.e-12;
CheckCDF("distfun_unidcdf",list(distfun_unidcdf,N),x,rtol);

// See upper tail
p = distfun_unidcdf ( 3, 4 );
assert_checkalmostequal ( p , 0.75 , %eps );
q = distfun_unidcdf ( 3, 4 , %f );
assert_checkalmostequal ( q , 0.25 , %eps );

//
// Test accuracy
//
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","unid","unid.dataset.csv");
table = readCsvDataset(dataset);
precision = 1.e-12;
ntests = size(table,"r");
for i = 1 : ntests
    x = table(i,1);
    N = table(i,2);
    p = table(i,4);
    q = table(i,5);
    computed = distfun_unidcdf ( x , N );
    assert_checkalmostequal ( computed , p , precision );
    if ( %f ) then
        d = assert_computedigits ( computed , p );
        mprintf("Test #%d/%d: Digits = %.1f\n",i,ntests,d);
    end
    computed = distfun_unidcdf ( x , N , %f );
    assert_checkalmostequal ( computed , q , precision );
end

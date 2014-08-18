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
v1=5;
v2=6;
delta=10;
x=[
0.2937283
0.6067423
0.9765364
1.560462
3.1075117
];
CheckPDF("distfun_ncfpdf",list(distfun_ncfpdf,v1,v2,delta),x);
rtol=1.e-9;
CheckPDFvsCDF(list(distfun_ncfpdf,v1,v2,delta),list(distfun_ncfcdf,v1,v2,delta),x,rtol);

//
// Test accuracy
//
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","ncf","ncf.dataset.csv");
table = readCsvDataset(dataset);
precision = 1.e-8;
atol=1.e-3;
nt = size(table,"r");
for k = 1 : nt
    x = table(k,1);
    v1 = table(k,2);
    v2 = table(k,3);
    delta = table(k,4);
    p = table(k,5);
    computed = distfun_ncfpdf ( x, v1, v2, delta );
    assert_checkalmostequal ( computed, p, precision,atol );
    if ( %f ) then
        d = assert_computedigits ( computed , p );
        mprintf("Test #%d/%d: Digits = %.1f\n",k,nt,d);
    end
end

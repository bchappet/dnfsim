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
v=5;
delta=10;
x=[
-1.475884   
-0.5594296  
0.         
0.5594296  
1.475884   
];
CheckPDF("distfun_nctpdf",list(distfun_nctpdf,v,delta),x);
rtol=1.e-9;
CheckPDFvsCDF(list(distfun_nctpdf,v,delta),list(distfun_nctcdf,v,delta),x,rtol);

//
// Test accuracy
//
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","nct","nct.dataset.csv");
table = readCsvDataset(dataset);
precision = 1.e-8;
atol=1.e-6;
nt = size(table,"r");
for k = 1 : nt
    x = table(k,1);
    v = table(k,2);
    delta = table(k,3);
    p = table(k,4);
    computed = distfun_nctpdf ( x, v, delta );
    assert_checkalmostequal ( computed, p, precision,atol );
    if ( %f ) then
        d = assert_computedigits ( computed , p );
        mprintf("Test #%d/%d: Digits = %.1f\n",k,nt,d);
    end
end

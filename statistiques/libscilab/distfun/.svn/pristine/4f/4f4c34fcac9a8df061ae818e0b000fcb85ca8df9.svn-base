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
v=5;
x=[
-1.475884   
-0.5594296  
0.         
0.5594296  
1.475884   
];
CheckPDF("distfun_tpdf",list(distfun_tpdf,v),x);
rtol=1.e-9;
CheckPDFvsCDF(list(distfun_tpdf,v),list(distfun_tcdf,v),x,rtol);

//
// Test accuracy
//
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","t","t.dataset.csv");
table = readCsvDataset(dataset);
precision = 100*%eps;
nt = size(table,"r");
for k = 1 : nt
    x = table(k,1);
    v = table(k,2);
    p = table(k,3);
    computed = distfun_tpdf ( x , v );
    if (%f) then
        d = assert_computedigits ( computed , p );
        mprintf("Test #%d/%d: Digits = %.1f\n",k,nt,d);
    end
    assert_checkalmostequal ( computed , p , precision );
end

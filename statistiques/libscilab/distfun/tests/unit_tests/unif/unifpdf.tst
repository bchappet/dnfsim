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
CheckPDF("distfun_unifpdf",list(distfun_unifpdf,a,b),x);
rtol=1.e-9;
CheckPDFvsCDF(list(distfun_unifpdf,a,b),list(distfun_unifcdf,a,b),x,rtol);

//
// Test the accuracy
//
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","unif","uniform.dataset.csv");
table = readCsvDataset ( dataset );
precision = 10*%eps;
ntests = size(table,"r");
for k = 1 : ntests
    x = table(k,1);
    a = table(k,2);
    b = table(k,3);
    y = table(k,4);
    computed = distfun_unifpdf ( x , a , b );
    assert_checkalmostequal ( computed , y , precision );
end


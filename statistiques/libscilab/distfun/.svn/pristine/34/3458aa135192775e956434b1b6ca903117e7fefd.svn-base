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
CheckPDF("distfun_unidpdf",list(distfun_unidpdf,N),x);
rtol=1.e-9;
// Do not include x=1, to prevent the wrong input 
// value x=0 to distfun_unidcdf during differentiation
x=[2 3 4]; 
CheckPDFvsCDF(list(distfun_unidpdf,N),list(distfun_unidcdf,N),x,rtol,%f);

//
// Test the accuracy
//
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","unid","unid.dataset.csv");
table = readCsvDataset ( dataset );
precision = 10*%eps;
ntests = size(table,"r");
for k = 1 : ntests
    x = table(k,1);
    N = table(k,2);
    y = table(k,3);
    computed = distfun_unidpdf ( x , N );
    assert_checkalmostequal ( computed , y , precision );
end

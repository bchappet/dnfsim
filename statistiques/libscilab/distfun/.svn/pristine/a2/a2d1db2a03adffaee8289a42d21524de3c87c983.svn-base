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
R=10;
P=0.7;
x=[
5.  
6.  
7.  
8.  
9.  
];
CheckPDF("distfun_binopdf",list(distfun_nbinpdf,R,P),x);
rtol=1.e-14;
CheckPDFvsCDF(list(distfun_nbinpdf,R,P),list(distfun_nbincdf,R,P),x,rtol,%f);

// Accuracy tests with data present in binocdf.R.dataset.csv
//
precision = 1.e-11;
path = distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","nbin","nbin.dataset.csv");
table = readCsvDataset(dataset);
ntests = size(table,"r");
for i = 1 : ntests
    x = table(i,1);
    R = table(i,2);
    P = table(i,3);
    expected = table(i,4);
    computed = distfun_nbinpdf(x,R,P);
    assert_checkalmostequal ( computed , expected , precision );
    if ( %f ) then
        d = assert_computedigits ( computed , expected );
        mprintf("Test #%d/%d: Digits = %.1f\n",i,ntests,d);
    end
end

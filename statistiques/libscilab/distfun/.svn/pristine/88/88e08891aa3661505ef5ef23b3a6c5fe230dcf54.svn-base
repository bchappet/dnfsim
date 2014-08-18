// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt


//
// <-- JVM NOT MANDATORY -->

path = distfun_getpath (  );
exec(fullfile(path,"tests","unit_tests","testingutilities.sce"));

//
// Consistency Checks
//
a=2;
b=3;
x=[
    exp(2.1)
    exp(2.2)
    exp(2.3)
    exp(2.4)
    exp(2.5)
    exp(2.9)
];
CheckPDF("distfun_logupdf",list(distfun_logupdf,a,b),x);
rtol=1.e-6;
CheckPDFvsCDF(list(distfun_logupdf,a,b),list(distfun_logucdf,a,b),x,rtol);

//
// Check accuracy
//
precision = 1000*%eps;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","logu","logu.dataset.csv");
table = readCsvDataset(dataset);
ntests = size(table,"r");
for i = 1 : ntests
    x = table(i,1);
    a = table(i,2);
    b = table(i,3);
    y = table(i,4);
    computed = distfun_logupdf ( x , a , b );
    assert_checkalmostequal ( computed , y , precision );
    if ( %f ) then
        d = assert_computedigits ( computed , y );
        mprintf("Test #%d/%d: Digits = %.1f\n",i,ntests,d);
    end
end


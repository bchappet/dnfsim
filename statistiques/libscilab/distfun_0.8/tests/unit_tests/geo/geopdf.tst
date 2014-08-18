// Copyright (C) 2012 - Prateek Papriwal
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
pr=0.1;
x=[
1.   
3.   
6.   
11.  
21.  
];
CheckPDF("distfun_geopdf",list(distfun_geopdf,pr),x);
rtol=1.e-13;
CheckPDFvsCDF(list(distfun_geopdf,pr),list(distfun_geocdf,pr),x,rtol,%f);

//
// Accuracy tests with data present in geo.dataset.csv
//
precision = 1.e-13;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","geo","geo.dataset.csv");
table = readCsvDataset(dataset);
ntests = size(table,"r");
for i = 1 : ntests
    x = table(i,1);
    Pr = table(i,2);
    p = table(i,3);
    computed = distfun_geopdf(x,Pr);
    assert_checkalmostequal ( computed , p , precision );
    if ( %f ) then
        d = assert_computedigits ( computed , p );
        mprintf("Test #%d/%d: Digits = %.1f\n",i,ntests,d);
    end
end

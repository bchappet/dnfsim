// Copyright (C) 2014 - Michael Baudin
//
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
rtol=1.e-12;
CheckCDF("distfun_binocdf",list(distfun_nbincdf,R,P),x,rtol);

// Accuracy test using data in nbincdf.R.dataset.csv file
precision = 1.e-11;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","nbin","nbin.dataset.csv");
table = readCsvDataset(dataset);
ntests = size(table,"r");
for i = 1 : ntests
    if (i==38) then
        // This is a two hard test for binoinv
        continue
    end
    x = table(i,1);
    R = table(i,2);
    P = table(i,3);
    p = table(i,5);
    q = table(i,6);
    computed = distfun_nbincdf(x,R,P);
    assert_checkalmostequal ( computed , p , precision );
    if ( %f ) then
        d = assert_computedigits ( computed , p );
        mprintf("Test #%d/%d: Digits = %.1f\n",i,ntests,d);
    end
    computed = distfun_nbincdf(x,R,P,%f);
    assert_checkalmostequal ( computed , q , precision );
end

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
lambda=80;
x=[
69.  
75.  
80.  
85.  
92.  
];
CheckPDF("distfun_poisspdf",list(distfun_poisspdf,lambda),x);
rtol=1.e-11;
CheckPDFvsCDF(list(distfun_poisspdf,lambda),list(distfun_poisscdf,lambda),x,rtol,%f);

//
// Accuracy tests with data present in poisspdf.yalta.dataset.csv
//
precision = 1.e-5;
path = distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","poiss","poisspdf.yalta.dataset.csv");
table = readCsvDataset(dataset);
ntests = size(table,"r");
for i = 1 : ntests
    x = table(i,1);
    lambda = table(i,2);
    expected = table(i,3);
    computed = distfun_poisspdf(x,lambda);
    assert_checkalmostequal ( computed , expected , precision );
end

//
// Accuracy tests
//
precision = 1.e-5;
path = distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","poiss","poisson.dataset.csv");
table = readCsvDataset(dataset);
ntests = size(table,"r");
for i = 1 : ntests
    x = table(i,1);
    lambda = table(i,2);
    expected = table(i,3);
    computed = distfun_poisspdf(x,lambda);
    assert_checkalmostequal ( computed , expected , precision );
end


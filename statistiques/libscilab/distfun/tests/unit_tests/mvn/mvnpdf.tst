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
// Test accuracy
//
mu=[12,-31];
sigma=[3.0,0.5;0.5,1.0];
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","mvn","mvn.dataset.csv");
table = readCsvDataset(dataset);
precision = 1000*%eps;
ntests = size(table,"r");
x=zeros(1,2);
for i = 1 : ntests
    x(1) = table(i,1);
    x(2) = table(i,2);
    expected = table(i,3);
    computed = distfun_mvnpdf(x,mu,sigma);
    assert_checkalmostequal ( computed , expected , precision );
    if ( %f ) then
        d = assert_computedigits ( computed , expected );
        mprintf("Test #%d/%d: Digits = %.1f\n",i,ntests,d);
    end
end

// In dimension 5
x=[11,-30,311,-4320];
mu=[12,-31,312,-4321];
sigma = [
 3.0  0.5  0.8  0.1
 0.5  1.0  0.9  0.2
 0.8  0.9  7.5  0.3
 0.1  0.2  0.3  5.1
];
y=distfun_mvnpdf(x,mu,sigma);
expected=7.95851005D-04;
assert_checkalmostequal ( y , expected , 1.e-7 );

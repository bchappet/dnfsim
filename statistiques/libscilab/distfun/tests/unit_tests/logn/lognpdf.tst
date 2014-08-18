// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2011 - INRIA - Michael Baudin
// Copyright (C) 2011 - DIGITEO - Michael Baudin
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
mu=3;
sigma=2;
x=[
    1.5478964  
    7.0371199  
    20.085537  
    57.32868   
    260.63035  
];
CheckPDF("distfun_lognpdf",list(distfun_lognpdf,mu,sigma),x);
rtol=1.e-7;
CheckPDFvsCDF(list(distfun_lognpdf,mu,sigma),list(distfun_logncdf,mu,sigma),x,rtol);

//
// Check accuracy
//
precision = 1000*%eps;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","logn","logn.dataset.csv");
table = readCsvDataset(dataset);
ntests = size(table,"r");
for i = 1 : ntests
    x = table(i,1);
    mu = table(i,2);
    sigma = table(i,3);
    y = table(i,4);
    computed = distfun_lognpdf ( x , mu , sigma );
    assert_checkalmostequal ( computed , y , precision );
    if ( %f ) then
        d = assert_computedigits ( computed , y );
        mprintf("Test #%d/%d: Digits = %.1f\n",i,ntests,d);
    end
end


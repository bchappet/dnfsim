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
rtol=1.e-12;
x=[
-1.475884   
-0.5594296  
0.         
0.5594296  
1.475884   
];
CheckCDF("distfun_tcdf",list(distfun_tcdf,v),x,rtol);

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
    p = table(k,4);
    q = table(k,5);
    computed = distfun_tcdf ( x , v );
    assert_checkalmostequal ( computed , p , precision );
    computed = distfun_tcdf ( x , v , %f );
    assert_checkalmostequal ( computed , q , precision );
end


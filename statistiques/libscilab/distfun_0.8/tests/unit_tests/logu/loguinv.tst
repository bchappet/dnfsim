// Copyright (C) 2013 - Michael Baudin
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
a=5;
b=6;
p=linspace(0.1,0.9,5);
rtol=1.e-12;
CheckInverseCDF("distfun_loguinv",list(distfun_loguinv,a,b),p,rtol);

//
// Test accuracy
//
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","logu","logu.dataset.csv");
table = readCsvDataset(dataset);
precision = 1000*%eps;
ntests = size(table,"r");
for i = 1 : ntests
    if (i==1 | i==ntests) then
        // The function is not invertible for those x
        continue
    end
    x = table(i,1);
    a = table(i,2);
    b = table(i,3);
    p = table(i,5);
    q = table(i,6);
    if (p<q) then
        computed = distfun_loguinv ( p , a , b );
    else
        computed = distfun_loguinv ( q , a , b , %f );
    end
    assert_checkalmostequal ( computed , x , precision );
end

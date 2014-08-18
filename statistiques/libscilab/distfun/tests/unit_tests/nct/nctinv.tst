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
v=5;
delta=10;
rtol=1.e-12;
p=linspace(0.1,0.9,5);
CheckInverseCDF("distfun_nctinv",list(distfun_nctinv,v,delta),p,rtol);

//
// Test accuracy
//
precision = 1.;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","nct","nct.dataset.csv");
table = readCsvDataset(dataset);
nt = size(table,"r");
for k = 1 : nt
    x = table(k,1);
    v = table(k,2);
    delta = table(k,3);
    p = table(k,5);
    q = table(k,6);
    if (p<q) then
        xcomputed = distfun_nctinv ( p , v ,delta);
    else
        xcomputed = distfun_nctinv ( q , v ,delta, %f );
    end
    assert_checkalmostequal ( x , xcomputed , precision );
end

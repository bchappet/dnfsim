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
k=50;
rtol=1.e-12;
p=linspace(0.1,0.9,5);
CheckInverseCDF("distfun_chi2inv",list(distfun_chi2inv,k),p,rtol);

//
// Test accuracy
//
precision = 1.e-12;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","chi2","chi2.dataset.csv");
table = readCsvDataset(dataset);
nt = size(table,"r");
for i = 1 : nt
    x = table(i,1);
    k = table(i,2);
    p = table(i,4);
    q = table(i,5);
    if (p<q) then
        xcomputed = distfun_chi2inv ( p , k );
    else
        xcomputed = distfun_chi2inv ( q , k , %f );
    end
    assert_checkalmostequal ( x , xcomputed , precision );
end

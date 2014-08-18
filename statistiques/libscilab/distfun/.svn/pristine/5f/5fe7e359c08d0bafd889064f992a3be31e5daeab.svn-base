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
k=50;
delta=10;
rtol=1.e-12;
p=linspace(0.1,0.9,5);
CheckInverseCDF("distfun_ncx2inv",list(distfun_ncx2inv,k, delta ),p,rtol);

//
// Test accuracy
// The CDF is not accurate : the inverse CDF is not accurate neither.
// http://forge.scilab.org/index.php/p/distfun/issues/1400/
//
precision = 1;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","ncx2","ncx2.dataset.csv");
table = readCsvDataset(dataset);
nt = size(table,"r");
for i = 1 : nt
    x = table(i,1);
    k = table(i,2);
    delta = table(i,3);
    p = table(i,5);
    q = table(i,6);
    if (p<q) then
        xcomputed = distfun_ncx2inv ( p , k , delta );
    else
        xcomputed = distfun_ncx2inv ( q , k , delta , %f );
    end
    assert_checkalmostequal ( x , xcomputed , precision );
end

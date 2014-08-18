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
n=5;
rtol=1.e-12;
p=linspace(0.1,0.9,5);
CheckInverseCDF("distfun_ksinv",list(distfun_ksinv,n),p,rtol);

//
// Test accuracy
//
precision = 1.e-11;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","ks","ks.dataset.csv");
table = readCsvDataset(dataset);
nt = size(table,"r");
for k = 1 : nt
    x = table(k,1);
    n = table(k,2);
    q = table(k,3);
    p = 1-q;
    if (p<q) then
        xcomputed = distfun_ksinv ( p , n);
    else
        xcomputed = distfun_ksinv ( q , n, %f );
    end
    assert_checkalmostequal ( x , xcomputed , precision );
    if (%f) then
        dx=assert_computedigits(x,xcomputed);
        mprintf("Test #%d/%d: X Digits = %.1f\n",k,nt,dx);
    end
end

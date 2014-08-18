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
p=linspace(0.1,0.9,5);
CheckInverseCDF("distfun_tinv",list(distfun_tinv,v),p,rtol);

//
// Test accuracy
//
precision = 100*%eps;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","t","t.dataset.csv");
table = readCsvDataset(dataset);
nt = size(table,"r");
for k = 1 : nt
    x = table(k,1);
    v = table(k,2);
    p = table(k,4);
    q = table(k,5);
    // 
    if (~isnan(p)) then
        if (p<q) then
            xcomputed = distfun_tinv ( p , v );
            assert_checkalmostequal ( x , xcomputed , precision );
        else
            xcomputed = distfun_tinv ( q , v , %f );
            assert_checkalmostequal ( x , xcomputed , precision );
        end
    end
end

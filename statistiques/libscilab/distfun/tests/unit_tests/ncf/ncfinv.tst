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
v1=5;
v2=6;
delta=10;
rtol=1.e-12;
p=linspace(0.1,0.9,5);
CheckInverseCDF("distfun_ncfinv",list(distfun_ncfinv,v1,v2,delta),p,rtol);

//
// Test accuracy
// The CDF is not accurate : inversion is not accurate neither.
// http://forge.scilab.org/index.php/p/distfun/issues/1404/
//
precision = 1.;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","ncf","ncf.dataset.csv");
table = readCsvDataset(dataset);
nt = size(table,"r");
for k = 1 : nt
    x = table(k,1);
    v1 = table(k,2);
    v2 = table(k,3);
    delta = table(k,4);
    p = table(k,6);
    q = table(k,7);
    // If v1 or v2 are not finite, the 
    // function is not invertible
    if (v1<%inf & v2<%inf) then
        if (p<q) then
            xcomputed = distfun_ncfinv ( p , v1 , v2 ,delta);
        else
            xcomputed = distfun_ncfinv ( q , v1 , v2 ,delta, %f );
        end
        assert_checkalmostequal ( x , xcomputed , precision );
    end
end
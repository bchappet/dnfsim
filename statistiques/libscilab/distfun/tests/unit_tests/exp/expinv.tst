// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2011 - INRIA - Michael Baudin
// Copyright (C) 2011 - DIGITEO - Michael Baudin
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
mu=50;
rtol=1.e-12;
p=linspace(0.1,0.9,5);
CheckInverseCDF("distfun_expinv",list(distfun_expinv,mu),p,rtol);

//
// See upper tail, compare with R
x = distfun_expinv ( 0.8 , 1/2 );
assert_checkalmostequal ( x , 0.80471895621705025192 , 10*%eps );
x = distfun_expinv ( 0.8 , 1/2 , %f );
assert_checkalmostequal ( x , 0.11157177565710485467 , 10*%eps );
x = distfun_expinv ( 1.e-20 , 1/2 , %f );
assert_checkalmostequal ( x , 23.025850929940457235 , 10*%eps );
//
// See http://forge.scilab.org/index.php/p/distfun/issues/780/
x = distfun_expinv(1.e-20,1);
assert_checkalmostequal ( x , 1.e-20 , %eps);

//
// Test accuracy
//
precision = 100*%eps;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","exp","exp.dataset.csv");
table = readCsvDataset(dataset);
nt = size(table,"r");
for k = 1 : nt
    x = table(k,1);
    mu = table(k,2);
    p = table(k,4);
    q = table(k,5);
    if (p<q) then
        xcomputed = distfun_expinv ( p , mu );
        assert_checkalmostequal ( x , xcomputed , precision );
    else
        xcomputed = distfun_expinv ( q , mu , %f );
        assert_checkalmostequal ( x , xcomputed , precision );
    end
end

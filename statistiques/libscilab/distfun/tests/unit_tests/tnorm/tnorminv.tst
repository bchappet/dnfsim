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
mu=5;
sigma=6;
a=4.5;
b=7;
p=linspace(0.1,0.9,5);
rtol=1.e-12;
CheckInverseCDF("distfun_tnorminv",list(distfun_tnorminv,mu,sigma,a,b),p,rtol);

//
// Test accuracy
//
precision = 100*%eps;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","tnorm","tnorm.dataset.csv");
table = readCsvDataset(dataset);
nt = size(table,"r");
for k = 1 : nt
    x = table(k,1);
    mu = table(k,2);
    sigma = table(k,3);
    a = table(k,4);
    b = table(k,5);
    p = table(k,7);
    q = table(k,8);
    if (p<q) then
        computed = distfun_tnorminv ( p , mu , sigma ,a,b);
    else
        computed = distfun_tnorminv ( q , mu , sigma ,a,b, %f );
    end
    assert_checkalmostequal ( computed , x , precision );
end


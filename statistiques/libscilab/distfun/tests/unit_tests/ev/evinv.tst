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
p=linspace(0.1,0.9,5);
rtol=1.e-12;
CheckInverseCDF("distfun_evinv",list(distfun_evinv,mu,sigma),p,rtol);

//
// Test accuracy
//
precision = 100*%eps;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","ev","ev.dataset.csv");
table = readCsvDataset(dataset);
nt = size(table,"r");
for k = 1 : nt
    x = table(k,1);
    mu = table(k,2);
    sigma = table(k,3);
    p = table(k,5);
    q = table(k,6);
    if (p<q) then
        computed = distfun_evinv ( p , mu , sigma );
	else
        computed = distfun_evinv ( q , mu , sigma , %f );
	end
    assert_checkalmostequal ( computed , x , precision );
end


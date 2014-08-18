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
lambda = 30;
rtol=1.e-12;
p=linspace(0.1,0.9,5);
CheckInverseCDF("distfun_poissinv",list(distfun_poissinv,lambda),p,rtol);

// Accuracy tests
//
precision = 1.e-11;
path = distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","poiss","poisson.dataset.csv");
table = readCsvDataset(dataset);
ntests = size(table,"r");
for i = 1 : ntests
    if (i==32) then
        // See bug http://forge.scilab.org/index.php/p/distfun/issues/961
        continue
    end
    x = table(i,1);
    lambda = table(i,2);
    p = table(i,4);
    q = table(i,5);
    if (p<q) then
        computed = distfun_poissinv(p,lambda);
        if (computed==0) then
            assert_checkequal ( x , computed );
        else
            pxm = distfun_poisscdf(computed-1,lambda);
            px = distfun_poisscdf(computed,lambda);
            assert_checktrue ( pxm<p*(1+precision) );
            assert_checktrue ( p<=px*(1+precision) );
        end
    else
        computed = distfun_poissinv(q,lambda,%f);
        if (computed==0) then
            assert_checkequal ( x , computed );
        else
            qxm = distfun_poisscdf(computed-1,lambda,%f);
            qx = distfun_poisscdf(computed,lambda,%f);
            assert_checktrue ( qxm*(1+precision)>q );
            assert_checktrue ( q*(1+precision)>=qx );
        end
    end
end

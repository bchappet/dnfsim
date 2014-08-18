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
N=50;
pr=0.7;
rtol=1.e-12;
p=linspace(0.1,0.9,5);
CheckInverseCDF("distfun_binoinv",list(distfun_binoinv,N,pr),p,rtol);

// Accuracy test
precision = 1.e-12;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","bino","binomiale.dataset.csv");
table = readCsvDataset(dataset);
ntests = size(table,"r");
for i = 1 : ntests
    if (i==38) then
        // This is a too hard test for binoinv
        continue
    end
    x = table(i,1);
    N = table(i,2);
    pr = table(i,3);
    p = table(i,5);
    q = table(i,6);
    if (pr==0&x<N) then
        // The function is not invertible 
        // for this particular input.
        continue
    end
    if (p<q) then
        computed = distfun_binoinv(p,N,pr);
        if (computed>0) then
            pxm = distfun_binocdf(computed-1,N,pr);
            assert_checktrue ( pxm<=p*(1+precision) );
        end
        px = distfun_binocdf(computed,N,pr);
        assert_checktrue ( p<=px*(1+precision) );
    else
        computed = distfun_binoinv(q,N,pr,%f);
        if (computed>0) then
            qxm = distfun_binocdf(computed-1,N,pr,%f);
            assert_checktrue ( qxm*(1+precision)>=q );
        end
        qx = distfun_binocdf(computed,N,pr,%f);
        assert_checktrue ( q*(1+precision)>=qx );
    end
    // Compute number of significant digits
    if ( %f ) then
        d = assert_computedigits ( computed , x );
        mprintf("Test #%d/%d: x=%s, Digits = %.1f\n",i,ntests,string(x),d);
    end
end

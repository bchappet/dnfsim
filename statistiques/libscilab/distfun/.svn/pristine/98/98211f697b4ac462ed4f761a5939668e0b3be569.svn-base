// Copyright (C) 2012 - 2014 - Michael Baudin
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
a=5;
b=6;
rtol=1.e-12;
p=linspace(0.1,0.9,5);
CheckInverseCDF("distfun_gaminv",list(distfun_gaminv,a,b),p,rtol);

//
// Accuracy test
// Numerical values computed from R
//
precision = 1.e-12;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","gam","gamma.dataset.csv");
table = readCsvDataset(dataset);
ntests = size(table,"r");
for i = 1 : ntests
    x = table(i,1);
    shape = table(i,2);
    scale = table(i,3);
    p = table(i,5);
    q = table(i,6);
    if (q<1.e-90 & q>0) then
        mprintf("Skip test #%d, x=%e, p=%e, q=%e\n",i,x,p,q)
        continue
    end
    if (p<1.e-90 & p>0) then
        mprintf("Skip test #%d, x=%e, p=%e, q=%e\n",i,x,p,q)
        continue
    end
    if (q==0 & x<%inf) then
        mprintf("Skip test #%d, x=%e, p=%e, q=%e\n",i,x,p,q)
        continue
    end
    if (p<q) then
        computed = distfun_gaminv(p,shape,scale);
        assert_checkalmostequal ( computed , x , precision );
    else
        computed = distfun_gaminv(q,shape,scale,%f);
        assert_checkalmostequal ( computed , x , precision );
    end
    if ( %f ) then
        d = assert_computedigits ( computed , x );
        mprintf("Test #%d/%d: Digits = %.1f\n",i,ntests,d);
    end
end

//
// See upper tail, compare with R
p = distfun_gaminv(1.e-20,3,5,%f);
assert_checkalmostequal(p, 266.7488703551273943,10*%eps);

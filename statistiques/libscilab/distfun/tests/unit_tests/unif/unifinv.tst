// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2009 - 2011 - DIGITEO - Michael Baudin
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
a=30;
b=45.2;
rtol=1.e-12;
p=linspace(0.1,0.9,5);
CheckInverseCDF("distfun_unifinv",list(distfun_unifinv,a,b),p,rtol);

// See upper tail
x=distfun_unifinv(1.e-15,1.,2.);
assert_checkalmostequal ( x , 1. );
x=distfun_unifinv(1.e-15,1.,2.,%f);
assert_checkalmostequal ( x , 2. );

//
// Test accuracy
//
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","unif","uniform.dataset.csv");
table = readCsvDataset(dataset);
precision = 1.e-12;
nt = size(table,"r");
for k = 1 : nt
    x = table(k,1);
    a = table(k,2);
    b = table(k,3);
    p = table(k,5);
    q = table(k,6);
    if (p<q) then
        computed = distfun_unifinv ( p , a , b );
    else
        computed = distfun_unifinv ( q , a , b , %f );
    end
    if ( %f ) then
        digits = assert_computedigits ( computed , x );
        digits = floor(digits);
        if (p<q) then
            mprintf("Test #%3d/%3d: Digits p=%.g, X= %d\n",k,nt,p,digits);
        else
            mprintf("Test #%3d/%3d: Digits q=%.g, X= %d\n",k,nt,q,digits);
        end
    end
    assert_checkalmostequal ( computed , x , precision );
end


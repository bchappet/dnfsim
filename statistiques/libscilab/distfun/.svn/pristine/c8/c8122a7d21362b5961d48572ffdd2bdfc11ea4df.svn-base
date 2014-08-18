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
N=30;
rtol=1.e-12;
p=linspace(0.,1.,10);
CheckInverseCDF("distfun_unidinv",list(distfun_unidinv,N),p,rtol);

// See upper tail
x=distfun_unidinv(1/3,30,%t);
assert_checkalmostequal ( x , 10 );
x=distfun_unidinv(1-1/3,30,%f);
assert_checkalmostequal ( x , 10 );

//
// Test accuracy
//
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","unid","unid.dataset.csv");
table = readCsvDataset(dataset);
precision = 1.e-12;
nt = size(table,"r");
for k = 1 : nt
    x = table(k,1);
    N = table(k,2);
    p = table(k,4);
    q = table(k,5);
    if (x<1 | x>4) then
        // Skip these tests : inversion is not 
        // possible here.
        continue
    end
    if (p<q) then
        computed = distfun_unidinv ( p , N );
    else
        computed = distfun_unidinv ( q , N , %f );
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


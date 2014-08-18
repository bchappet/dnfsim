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
n=5;
rtol=1.e-12;
x=[0.1 0.2 0.3 0.4 0.5];
CheckCDF("distfun_kscdf",list(distfun_kscdf,n),x,rtol);

//
// Test accuracy
// See http://forge.scilab.org/index.php/p/distfun/issues/1404/
// The min of p and q can be wrong.
// The max of p and q is accurate up to 6 digits.
//
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","ks","ks.dataset.csv");
table = readCsvDataset(dataset);
digitsmin = 0;
nt = size(table,"r");
for k = 1 : nt
    x = table(k,1);
    n = table(k,2);
    q = table(k,3);
    computedP = distfun_kscdf ( x, n );
    computedQ = distfun_kscdf ( x, n, %f );
    dp = assert_computedigits ( computedP , 1-q );
    dq = assert_computedigits ( computedQ , q );
    assert_checktrue( dp+dq>digitsmin );
    if ( %t ) then
        mprintf("Test #%d/%d: P Digits = %.1f, Q Digits = %.1f\n",k,nt,dp,dq);
    end
end

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
x=[
0.2937283
0.6067423
0.9765364
1.560462
3.1075117
];
CheckCDF("distfun_ncfcdf",list(distfun_ncfcdf,v1,v2,delta),x,rtol);

//
// Test accuracy
// See http://forge.scilab.org/index.php/p/distfun/issues/1404/
// The min of p and q can be wrong.
// The max of p and q is accurate up to 6 digits.
//
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","ncf","ncf.dataset.csv");
table = readCsvDataset(dataset);
digitsmin = 5;
nt = size(table,"r");
for k = 1 : nt
    x = table(k,1);
    v1 = table(k,2);
    v2 = table(k,3);
    delta = table(k,4);
    p = table(k,6);
    q = table(k,7);
    computedP = distfun_ncfcdf ( x , v1 , v2, delta );
    computedQ = distfun_ncfcdf ( x , v1 , v2 , delta, %f );
    dp = assert_computedigits ( computedP , p );
    dq = assert_computedigits ( computedQ , q );
    assert_checktrue( dp+dq>digitsmin );
    if ( %f ) then
        mprintf("Test #%d/%d: P Digits = %.1f, Q Digits = %.1f\n",k,nt,dp,dq);
    end
end
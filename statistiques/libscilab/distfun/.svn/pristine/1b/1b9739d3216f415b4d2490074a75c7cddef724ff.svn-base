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
v=5;
delta=10;
rtol=1.e-12;
x=[
-1.475884   
-0.5594296  
0.         
0.5594296  
1.475884   
];
CheckCDF("distfun_nctcdf",list(distfun_nctcdf,v,delta),x,rtol);

//
// Test accuracy
//
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","nct","nct.dataset.csv");
table = readCsvDataset(dataset);
digitsmin=5;
nt = size(table,"r");
for k = 1 : nt
    x = table(k,1);
    v = table(k,2);
    delta = table(k,3);
    p = table(k,5);
    q = table(k,6);
    computedP = distfun_nctcdf ( x , v , delta );
    computedQ = distfun_nctcdf ( x , v , delta , %f );
    dp = assert_computedigits ( computedP , p );
    dq = assert_computedigits ( computedQ , q );
    assert_checktrue( dp+dq>digitsmin );
    if ( %f ) then
        mprintf("Test #%d/%d: P Digits = %.1f, Q Digits = %.1f\n",k,nt,dp,dq);
    end
end

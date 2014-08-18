// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - Michael Baudin
//
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
pr=0.1;
x=[
1.   
3.   
6.   
11.  
21.  
];
rtol=1.e-12;
CheckCDF("distfun_geocdf",list(distfun_geocdf,pr),x,rtol);

//
// Accuracy test using data in geo.dataset.csv file
precision = 1.e-13;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","geo","geo.dataset.csv");
table = readCsvDataset(dataset);
ntests = size(table,"r");
for i = 1 : ntests
    x = table(i,1);
    Pr = table(i,2);
    p = table(i,4);
    q = table(i,5);
    computed = distfun_geocdf(x,Pr);
    assert_checkalmostequal ( computed , p , precision );
    // Compute number of significant digits
    if ( %f ) then
        d = assert_computedigits ( computed , p );
        mprintf("Test #%d/%d: Digits = %.1f\n",i,ntests,d);
    end
    computed = distfun_geocdf(x,Pr,%f);
    assert_checkalmostequal ( computed , q , precision );
end


//
// Check upper tail
p = distfun_geocdf(3,0.1);
lt_expected = 0.3439;
assert_checkalmostequal(p,lt_expected,10.^-7);

q = distfun_geocdf(3,0.1,%f);
ut_expected = 0.6561;
assert_checkalmostequal(q,ut_expected,10.^-7);

// Check upper tail
p = distfun_geocdf(100,0.5,%f);
expected = 3.944304526105059027D-31;
assert_checkalmostequal(p,expected);
//
// Check extreme probability
// See http://forge.scilab.org/index.php/p/distfun/issues/774/
p = distfun_geocdf(1,1.e-20);
assert_checkalmostequal(p,2e-20);


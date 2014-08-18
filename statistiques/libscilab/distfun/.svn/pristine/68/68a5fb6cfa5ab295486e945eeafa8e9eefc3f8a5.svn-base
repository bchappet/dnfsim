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
a=5;
b=6;
x=[
0.2673181  
0.3725816  
0.4516942  
0.5324996  
0.6457841  
];
CheckPDF("distfun_wblpdf",list(distfun_wblpdf,a,b),x);
rtol=1.e-8;
CheckPDFvsCDF(list(distfun_wblpdf,a,b),list(distfun_wblcdf,a,b),x,rtol);

//
// Check bounds of x
instr = "computed = distfun_wblpdf(-1,2,3)";
msg = "distfun_wblpdf: Expected that all entries of input argument x at input #1 are greater or equal than 0, but entry #1 is equal to -1.";
assert_checkerror(instr,msg);
//
// Test the accuracy of distfun_wblpdf
//
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","wbl","wbl.dataset.csv");
table = readCsvDataset ( dataset );
precision = 100*%eps;
ntests = size(table,"r");
for k = 1 : ntests
    x = table(k,1);
    a = table(k,2);
    b = table(k,3);
    y = table(k,4);
    computed = distfun_wblpdf ( x , a , b );
    assert_checkalmostequal ( computed , y , precision );
    if ( %f ) then
        d = assert_computedigits ( computed , y );
        mprintf("Test #%d/%d: Digits = %.1f\n",k,ntests,d);
    end
end


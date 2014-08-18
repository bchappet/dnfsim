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
a=5;
b=6;
x=[
    0.2673181  
    0.3725816  
    0.4516942  
    0.5324996  
    0.6457841  
];
CheckPDF("distfun_betapdf",list(distfun_betapdf,a,b),x);
rtol=1.e-9;
CheckPDFvsCDF(list(distfun_betapdf,a,b),list(distfun_betacdf,a,b),x,rtol);

//
// Check bounds of x
instr = "computed = distfun_betapdf(-1,2,3)";
msg = "distfun_betapdf: Expected that all entries of input argument x at input #1 are in the range [0,1], but entry #1 is equal to -1.";
assert_checkerror(instr,msg);
//
// Check bounds of x
instr = "computed = distfun_betapdf(2,2,3)";
msg = "distfun_betapdf: Expected that all entries of input argument x at input #1 are in the range [0,1], but entry #1 is equal to 2.";
assert_checkerror(instr,msg);
//
// Test the accuracy of distfun_betapdf
//
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","beta","beta.dataset.csv");
table = readCsvDataset ( dataset );
precision = 10*%eps;
ntests = size(table,"r");
for k = 1 : ntests
  x = table(k,1);
  a = table(k,2);
  b = table(k,3);
  y = table(k,4);
  computed = distfun_betapdf ( x , a , b );
  assert_checkalmostequal ( computed , y , precision );
end


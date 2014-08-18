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
v1=5;
v2=6;
x=[
0.2937283
0.6067423
0.9765364
1.560462
3.1075117
];
CheckPDF("distfun_fpdf",list(distfun_fpdf,v1,v2),x);
rtol=1.e-9;
CheckPDFvsCDF(list(distfun_fpdf,v1,v2),list(distfun_fcdf,v1,v2),x,rtol);

//
// Test accuracy
//
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","f","f.dataset.csv");
table = readCsvDataset(dataset);
precision = 1.e-10;
nt = size(table,"r");
for k = 1 : nt
  x = table(k,1);
  v1 = table(k,2);
  v2 = table(k,3);
  p = table(k,4);
  computed = distfun_fpdf ( x , v1 , v2 );
  assert_checkalmostequal ( computed , p , precision );
end

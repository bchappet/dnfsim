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
x=[0.2 0.3 0.4 0.5 0.6];
CheckPDF("distfun_kspdf",list(distfun_kspdf,n),x);
rtol=1.e-4;
CheckPDFvsCDF(list(distfun_kspdf,n),list(distfun_kscdf,n),x,rtol);

//
// Test accuracy : no reference value for the PDF.
//

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
// Check empty matrix
[M,V] = distfun_fstat ( [] , [] );
assert_checkequal ( M , [] );
assert_checkequal ( V , [] );

// Test with scalar v1 and v2
[M,V] = distfun_fstat(7,5);
Me = 1.666666666666666741D+00;
Ve = 7.936507936507936734D+00;
assert_checkalmostequal(M,Me,1.e-12);
assert_checkalmostequal(V,Ve,1.e-12);

// Test with expanded v1 and v2
[M,V] = distfun_fstat(1:5,1:5);
Me = [%nan  %nan  3.0000  2.0000  1.6667];
Ve = [%nan  %nan  %nan  %nan  8.8889];
assert_checkalmostequal(M,Me,1.e-4);
assert_checkalmostequal(V,Ve,1.e-4);

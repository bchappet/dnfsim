// Copyright (C) 2012 - Prateek Papriwal
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

//
// Check empty matrix
[M,V] = distfun_geostat ( [] );
assert_checkequal ( M , [] );
assert_checkequal ( V , [] );
//
//Check with expanded Pr
[m,v] = distfun_geostat(1 ./(1:6));
me = [ 0  1.0000  2.0000  3.0000  4.0000  5.0000];
ve = [ 0  2.0000  6.0000  12.0000  20.0000  30.0000];
assert_checkalmostequal(m,me);
assert_checkalmostequal(v,ve);
//////
Pr=[0.11 0.22 0.33];

[M,V] = distfun_geostat ( Pr );
ve= [73.553719 16.115702 6.1524334 ];
me = [8.0909091 3.5454545 2.030303 ];
assert_checkalmostequal(M,me,[],1.e-6);
assert_checkalmostequal(V,ve,[],1.e-6);


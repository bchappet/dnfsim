// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

//
// <-- JVM NOT MANDATORY -->

//
// Check empty matrix
[M,V] = distfun_logustat ( [] , [] );
assert_checkequal ( M , [] );
assert_checkequal ( V , [] );
//
a = 1:6;
b = 2:7;
[M,V] = distfun_logustat ( a , b );
Ve = [1.7884147,13.214696,97.644133,721.49798,5331.189,39392.455];
Me = [4.6707743,12.696481,34.512613,93.815009,255.01563,693.20436];
assert_checkalmostequal(M,Me,1.e-7,[],"element");
assert_checkalmostequal(V,Ve,1.e-7,[],"element");

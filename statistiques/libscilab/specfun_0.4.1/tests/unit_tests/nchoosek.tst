// Copyright (C) 2010 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
//
// <-- JVM NOT MANDATORY -->
// <-- ENGLISH IMPOSED -->


assert_checkequal ( specfun_nchoosek ( 4 , 1 ) , 4 );
assert_checkequal ( specfun_nchoosek ( 5 , 0 ) , 1 );
assert_checkequal ( specfun_nchoosek ( 5 , 1 ) , 5 );
assert_checkequal ( specfun_nchoosek ( 5 , 2 ) , 10 );
assert_checkequal ( specfun_nchoosek ( 5 , 3 ) , 10 );
assert_checkequal ( specfun_nchoosek ( 5 , 4 ) , 5 );
assert_checkequal ( specfun_nchoosek ( 5 , 5 ) , 1 );
assert_checkalmostequal ( specfun_nchoosek ( 10000 , 134 ) , 2.050083865033972676e307 , 1.e-10 );
assert_checkequal ( specfun_nchoosek (10,0:10) , [1,10,45,120,210,252,210,120,45,10,1] );
assert_checkequal ( specfun_nchoosek (1:6,0:5) , 1:6 );
//
// Check error cases
assert_checkerror ( "specfun_nchoosek ( 17 , 18 )" , "specfun_nchoosek: For at least one entry, we do not have k<=n." );
assert_checkerror ( "specfun_nchoosek ( 17 , -1 )" , "specfun_nchoosek: Expected that all entries of input argument k at input #2 are in the range [0,Inf], but entry #1 is equal to -1." );
assert_checkerror ( "specfun_nchoosek ( 1.5 , 0.5 )" , "specfun_nchoosek: Expected floating point integer for input argument n at input #1, but entry #1 is equal to 1.5." );


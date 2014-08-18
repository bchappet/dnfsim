// Copyright (C) 2012 - Michael Baudin
//
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- Non-regression test for bug 779 -->
//
// <-- URL -->
// http://forge.scilab.org/index.php/p/distfun/issues/779
//
// <-- Short Description -->
// distfun_norminv fails when p = 1

// <-- JVM NOT MANDATORY -->

p = 1;
x = distfun_norminv ( p ,0,1);
assert_checkequal ( x , %inf );
//
p = 0;
x = distfun_norminv ( p ,0,1);
assert_checkequal ( x , -%inf );
//
p = 0;
x = distfun_norminv ( p , 0, 1 , %f );
assert_checkequal ( x , %inf );

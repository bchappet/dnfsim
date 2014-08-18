// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- Non-regression test for bug 9976 -->
//
// <-- URL -->
// http://bugzilla.scilab.org/show_bug.cgi?id=9976
//
// <-- Short Description -->
// The inverse cdfbet function is sometimes wrong.

//
a = 1;
b = 2;
p = 0;
x = distfun_betainv ( p , a , b );
x_expected = 0;
assert_checkequal(x,0);


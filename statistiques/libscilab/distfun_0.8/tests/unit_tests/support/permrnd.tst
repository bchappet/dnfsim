// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

distfun_seedset(1);

//
R=distfun_permrnd((1:3)');
expected=[
    2.  
    3.  
    1. 
];
assert_checkequal(R,expected);
//
R=distfun_permrnd((1:3)',4);
expected=[
    1.    2.    3.    3.  
    2.    3.    1.    2.  
    3.    1.    2.    1. 
];
assert_checkequal(R,expected);

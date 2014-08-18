// Copyright (C) 2012 - Michael Baudin
//
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- Non-regression test for bug 912 -->
//
// <-- URL -->
// http://forge.scilab.org/index.php/p/distfun/issues/912
//
// <-- Short Description -->
// distfun_seedset does not accept vectors.

// <-- JVM NOT MANDATORY -->

//
// CLCG2 : distfun_seedset does not accept 1 single argument.
distfun_genset("clcg2");
S = [
1365122261
1003762612
];
distfun_seedset(S);
distfun_seedset(S(1),S(2));
//
// CLCG4 : distfun_seedset does not accept 1 single argument
distfun_genset("clcg4");
S = [
    329142372.   
    1376789889.  
    460143638.   
    1842127081.
    ];
distfun_seedset(S);
distfun_seedset(S(1),S(2),S(3),S(4));
//
// KISS : distfun_seedset does not accept 1 single argument.
distfun_genset("kiss");
S=[];
S(1)=1482409747;
S(2)=513556993;
S(3)=3780943645;
S(4)=1686363620;
distfun_seedset(S);
distfun_seedset(S(1),S(2),S(3),S(4));

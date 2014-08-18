// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

pr=0.7;
N=10000;
R=distfun_geornd(pr,1,N);
scf();
H = distfun_inthisto(R);
assert_checkequal(size(H,"c"),2);
assert_checktrue(H(:,1)>=0.);
assert_checktrue(H(:,2)>=0.);
assert_checktrue(H(:,2)<=1.);
assert_checkequal(floor(H(:,1)),H(:,1));
assert_checkequal(sum(H(:,2)),1.);
//
scf();
H = distfun_inthisto(R,%f);
assert_checkequal(size(H,"c"),2);
assert_checktrue(H(:,1)>=0.);
assert_checktrue(H(:,2)>=0.);
assert_checktrue(H(:,2)<=N);
assert_checkequal(floor(H),H);
assert_checkequal(sum(H(:,2)),N);
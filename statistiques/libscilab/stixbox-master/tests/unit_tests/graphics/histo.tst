// Copyright (C) 2012-2013 - Michael Baudin
// Copyright (C) 2010 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt


x=distfun_chi2rnd(3,1000,1);
scf();
[h,edge]=histo(x);
assert_checkequal ( typeof(h) , "constant" );
assert_checkequal ( and(h>=0) , %t );
assert_checkequal ( typeof(edge) , "constant" );
//
// Sets the number of classes
clf(); 
[h,edge]=histo(x,10);
assert_checkequal ( typeof(h) , "constant" );
assert_checkequal ( size(h) , [1 10] );
assert_checkequal ( and(h>=0) , %t );
assert_checkequal ( typeof(edge) , "constant" );
assert_checkequal ( size(edge) , [1 11] );
//
// See without scaling
clf(); 
histo(x,[],%f);
// See with scaling
clf(); 
histo(x,[],%f);
//
// See various colors and styles
clf(); 
histo(x,[],[],1);
clf(); 
histo(x,[],[],2);
clf(); 
histo(x,[],[],3);
//
X = distfun_unifrnd(0,1,100,1);
edges = 0:0.2:1.; 
clf();
histo(X,edges);

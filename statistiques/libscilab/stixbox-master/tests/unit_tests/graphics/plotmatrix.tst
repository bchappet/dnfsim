// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// Example 1
// Plot Y versus X
m=1000;
x1=distfun_unifrnd(0,1,m,1);
x2=distfun_unifrnd(0,1,m,1);
x3=distfun_unifrnd(0,1,m,1);
y1=2*x1.*x2+x3;
y2=-3*x1+x2.^2-2*x3;
y3=sin(x1)-3*x2+3*x3;
x=[x1,x2,x3];
y=[y1,y2,y3];
//
xlabels=["X1","X2","X3"];
ylabels=["Y1","Y2","Y3"];
// No labels
scf();
plotmatrix(x,y);
// With labels (Figure 1)
scf();
plotmatrix(x,y,"xlabels",xlabels,"ylabels",ylabels);
// Without XY value labels
scf();
plotmatrix(x,y,"valuelabels",%f);
// Without XY value labels, and XY labels
scf();
plotmatrix(x,y,"valuelabels",%f,..
"xlabels",xlabels,"ylabels",ylabels);
// Set the point size
scf();
plotmatrix(x,y,"ptsize",1);
// With red crosses
scf();
plotmatrix(x,y,"symbol","rx");
//
// Example 2
// Plot Y versus X
m=1000;
x1=distfun_normrnd(0,1,m,1);
x2=distfun_unifrnd(-1,1,m,1);
y1=x1.^2+x2;
y2=-3*x1+x2.^2;
y3=x1-3*exp(x2);
x=[x1,x2];
y=[y1,y2,y3];
//
xlabels=["X1","X2"];
ylabels=["Y1","Y2","Y3"];
// No labels
scf();
plotmatrix(x,y);
// With labels, and red circles
scf();
plotmatrix(x,y,"xlabels",xlabels,"ylabels",ylabels,..
"symbol","ro");
//
// Example 3
// Plot X versus X
m=1000;
x1=distfun_unifrnd(0,1,m,1);
x2=distfun_unifrnd(0,1,m,1);
x3=distfun_unifrnd(0,1,m,1);
y1=2*x1.*x2+x3;
y2=-3*x1+x2.^2-2*x3;
y3=sin(x1)-3*x2+3*x3;
y=[y1,y2,y3];
//
ylabels=["Y1","Y2","Y3"];
// No labels
scf();
plotmatrix(y);
// With labels (Figure 2)
scf();
plotmatrix(y,"xlabels",ylabels);
// With labels, without value labels
scf();
plotmatrix(y,"xlabels",ylabels,"valuelabels",%f);
// With labels, without value labels, with red circles
scf();
plotmatrix(y,"xlabels",ylabels,"valuelabels",%f,..
"symbol","ro");
// With labels, without value labels, with red dots,
// with symbols of size 1
scf();
plotmatrix(y,"xlabels",ylabels,"valuelabels",%f,..
"symbol","r.","ptsize",1);
// With the histogram
scf();
plotmatrix(y,"histogram",%t);
// With the histogram, and the labels
scf();
plotmatrix(y,"histogram",%t,"xlabels",ylabels);
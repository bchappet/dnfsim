// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

m = 12;
x = [
distfun_normrnd(0,1,m,1)
distfun_normrnd(2,1,m,1)
distfun_normrnd(4,1,m,1)
];
y = [
distfun_normrnd(0,1,m,1)
distfun_normrnd(4,1,m,1)
distfun_normrnd(3,1,m,1)
];
S = [
ones(m,1)
2*ones(m,1)
3*ones(m,1)
];
// Only red squares
scf();
plotsym(x,y);
xtitle("","X","Y");
// With 3 colors, and 3 shapes
scf();
plotsym(x,y,S);
xtitle("","X","Y");
// With squares ("s"), triangles ("s"),
// circles ("c"), in blue ("b")
scf();
plotsym(x,y,S,"stc","b")
xtitle("","X","Y");
// Change the colormap
h=scf();
plotsym(x,y,S);
h.color_map=autumncolormap(3);
// Set the symbol size
scf();
plotsym(x,y,S,[],[],0.5);
//
// Salary Survey
// Source: Chatterjee, S. and Hadi, A. S. (1988), p. 88 
[x,txt] = getdata(3);
scf();
// Sex (1 = male, 0 = female)
// Add +1, to get : 2=male, 1=female, 
// which maps to symbols.
gender=x(:,2)+1;
// Scale performance (from 1 to 5)
// into a size from 0.2 to 2.
perf=2*x(:,5)/5;
plotsym(x(:,6),x(:,3),gender,[],[],perf);
xtitle("Females:red square, Males:blue triangle, Size is performance",..
"Monthly salary ($)","Number of years with the company");
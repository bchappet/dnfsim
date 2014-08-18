// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// Create a matrix bubble chart
n=5;
R=rand(n,n);
h=scf();
xtitle("Random numbers [0,1]")
bubblematrix(1:n,1:n,R,-2,0.5)

// A matrix of bubbles
Z = [
53.    35.    34.    34.    28.    28.    19.
53.    24.    19.    15.    4.     9.     0.
53.    16.    10.    6.     3.     0.     0.
53.    13.    7.     0.     0.     0.     0.
53.    10.    0.     0.     0.     0.     0.
53.    8.     0.     0.     0.     0.     0.
];
X=1:6;
Y=1:7;
scf();
bubblematrix(X,Y,Z);
// Set the color (2="blue"), the radius (=0.5).
h=scf();
bubblematrix(X,Y,Z,2,0.4);
xtitle("Area of bubbles: Z","X","Y")
h.children.axes_visible=["off","off","off"];
// Draw X axis
drawaxis(x=X,y=0.2,dir="d",tics="v",val=string(X))
// Draw Y axis
drawaxis(x=0.3,y=Y,dir="l",tics="v",val=string(Y))
//
// Reference
// http://www.stat.yale.edu/Courses/1997-98/101/catdat.htm
weightLabels=["<150" "150-175" "175-200" ">200"]
eventLabels=[
"LW_double_sculls"
"LW_four"
"coxswain"
"eight"
"four"
"pair"
"quad"
"single_sculls"
];
R=[
0.    0.    1.    0.    0.    0.    0.    0.
2.    4.    0.    0.    0.    0.    0.    0.
0.    0.    0.    4.    1.    1.    0.    0.
0.    0.    0.    4.    3.    1.    4.    1.
];
// Set the color (2="blue"), the radius (=0.5).
h=scf();
m=size(R,"c");
n=size(R,"r");
bubblematrix(1:n,1:m,R,2,0.2);
xtitle("Area=Number of Athletes","Weigth Cat.","Event")
h.children.axes_visible=["off","off","off"];
// Draw X axis
drawaxis(x=1:n,y=0.25,dir="d",tics="v",val=weightLabels)
// Draw Y axis
drawaxis(x=0.8,y=1:m,dir="l",tics="v",val=eventLabels)

//
// A regular matrix of bubbles
nx=20;
ny=20;
X=linspace(0,2*%pi,nx);
Y=linspace(0,2*%pi,ny);
Z=cos(X)'*sin(Y);
Z1=max(Z,0);
Z2=max(-Z,0);
h=scf();
bubblematrix(1:nx,1:ny,Z1,-2,0.5);
bubblematrix(1:nx,1:ny,Z2,-3,0.5);
xtitle("Area: z=cos(x)sin(y), blue:z>0, green:z<0","x","y")

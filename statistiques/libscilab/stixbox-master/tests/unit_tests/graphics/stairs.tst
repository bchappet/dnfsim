// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// stairs(y), y row vector
x = linspace(0,4*%pi,40);
y = sin(x);
scf();
stairs(y);

// stairs(y), y column vector
x = linspace(0,4*%pi,40)';
y = sin(x);
scf();
stairs(y);

// stairs(x,y)
x = linspace(0,4*%pi,40);
y = sin(x);
scf();
stairs(x,y);

// Plot the empirical CDF of exponential
// random numbers
n=100;
y=1:n;
y=y/n;
lambda=1;
x=gsort(rexpweib(n,lambda),"g","i");
scf();
stairs(x,y);

// stairs(y), y a matrix
// Plot multiple data series
x = linspace(0,4*%pi,50)';
y = [0.5*cos(x), 2*cos(x)];
scf();
stairs(y);

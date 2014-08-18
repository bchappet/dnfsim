// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function checkdensity(X,f,xi,atol)
    // Compare the empirical histogram and the density
    N=size(X,"*")
    n = ceil(4*sqrt(sqrt(N)));
    // The edges of the classes
    edges=linspace(-3,3,n);
    // Compute, and normalize, the histogram
    [ind,y]=dsearch(X,edges);
    y=y./(N *(edges(2:$)-edges(1:$-1)));
    // Compute the centers of the classes
    centers=(edges(1:$-1)+edges(2:$))/2;
    // Compare the density and the histogram
    for i=1:n-1
        j=find(xi>centers(i),1);
        assert_checkalmostequal(y(i),f(j),[],atol);
    end
endfunction

N=1000;
distfun_seedset(0);
X=distfun_normrnd(0,1,N,1);
[f,xi,u]=ksdensity(X);
// This is because sigma=1
uexpected=1.06*N^(-1/5);
assert_checkalmostequal(u,uexpected,1.e-1);
assert_checkequal(size(f),[100 1]);
assert_checkequal(size(xi),[100 1]);
atol=0.1;
checkdensity(X,f,xi,atol);

// Set the kernel width
N=1000;
distfun_seedset(0);
X=distfun_normrnd(0,1,N,1);
[f,xi,u]=ksdensity(X,"width",0.5);
assert_checkequal(u,0.5);
atol=0.2;
checkdensity(X,f,xi,atol);

// Set the number of points
N=1000;
distfun_seedset(0);
X=distfun_normrnd(0,1,N,1);
[f,xi,u]=ksdensity(X,"npoints",500);
assert_checkequal(size(f),[500 1]);
assert_checkequal(size(xi),[500 1]);
atol=0.1;
checkdensity(X,f,xi,atol);

// Set the kernel
N=1000;
atol=0.2;
distfun_seedset(0);
X=distfun_normrnd(0,1,N,1);
//
[f,xi,u]=ksdensity(X,"kernel","normal");
checkdensity(X,f,xi,atol);
//
[f,xi,u]=ksdensity(X,"kernel","triangle");
checkdensity(X,f,xi,atol);
//
[f,xi,u]=ksdensity(X,"kernel","biweight");
checkdensity(X,f,xi,atol);
//
[f,xi,u]=ksdensity(X,"kernel","epanechnikov");
checkdensity(X,f,xi,atol);

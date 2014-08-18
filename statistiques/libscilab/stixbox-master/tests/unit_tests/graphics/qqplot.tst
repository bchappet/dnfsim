// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt


// Experiment A
x = distfun_poissrnd(10,100,1);
y = distfun_poissrnd(5,100,1);
scf();
qqplot(x,y);
// Change the symbol
scf();
qqplot(x,y,"r.");

// Experiment B
// Check a sample of uniform numbers:
// against normal numbers:
// uniform is obviously non-normal.
n=10000;
x=distfun_unifrnd(0,1,n,1);
p=linspace(0.01,0.99,100)';
q=quantile(x,p);
y=distfun_norminv(p,0,1);
scf();
qqplot(y,q,"bo");
xtitle("U(0,1) - n=10 000",..
"Normal Quantile","Uniform Quantile");

// Experiment C
// Check a sample of exponential numbers
// against normal numbers:
// exp is obviously non-normal.
n=10000;
x=distfun_exprnd(1,n,1);
y=distfun_normrnd(0,1,n,1);
scf();
qqplot(x,y,"bo");
xtitle("Exp(1) - n=10 000",..
"Normal Quantile","Exp Quantile");

// Experiment D
// Check two sample of normal numbers:
// it matches.
n=10000;
x1=distfun_normrnd(0,1,n,1);
x2=distfun_normrnd(0,2,n,1);
scf();
qqplot(x1,x2,"bo");
xtitle("n=10 000",..
"Normal(0,1)","Normal(0,2)");

// Experiment E
// Reference
// "Fitting data into probability distributions"
// Tasos Alexandridis
// Generate data that follow an
// exponential distribution with mu = 4
values = distfun_exprnd(4,100,1);
// Generate random Gaussian noise N(0,1)
noise = distfun_normrnd(0,1,100,1);
// Add noise to the exponential distributed data
// so as to look more realistic
realdata = values + abs(noise);
// Estimate the parameter
paramhat=mean(values)
syntheticData = distfun_exprnd(4.9918,100,1);
scf();
qqplot(realdata,syntheticData);

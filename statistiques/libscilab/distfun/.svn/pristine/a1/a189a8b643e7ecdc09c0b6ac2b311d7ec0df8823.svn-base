// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2009 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

//
// Reference
// Numerical Methods in Finance - Brandimante - 2002
// 4.4 Variance reduction techniques
// 4.4.1 Anthitetic variates
//
mprintf("This script illustrates how we can \n");
mprintf("use variance reduction techniques with\n");
mprintf("antithetic random variables.\n");
mprintf("Depending on the sign of the covariance cov(f(U),f(1-U)),\n");
mprintf("the variance of the estimate can be reduced (example 1),\n");
mprintf("or increased (example 2)...\n");
//
// Example 4.5
//
mprintf("\n\n");
mprintf("Integrate f(x)=exp(x) on [0,1].\n");
//
x = linspace(0,1,1000);
y = exp(x);
scf();
plot(x,y,"b-");
xtitle("Exponential","X","Y");
//
mprintf("With naive Monte-Carlo experiment\n");
N = 100;
mprintf("N=%d\n",N);
distfun_seedset(0);
U = distfun_unifrnd(0,1,1,N);
X = exp(U);
I=mean(X);
mprintf("I=%f\n",I);
S = variance(X);
z = distfun_norminv(0.95,0,1);
H = z * sqrt(S/N);
mprintf("90%% confidence interval: [%f,%f]\n",I-H, I+H);
coeffofvar = H/I;
mprintf("Coef. of var.: %f\n",coeffofvar);

// 
mprintf("With antithetic variables (U,1-U)\n");
N = 100;
Na = floor(N/2);
mprintf("N=%d\n",N);
distfun_seedset(0);
U1 = distfun_unifrnd(0,1,1,Na);
U2 = 1-U1;
X1 = exp(U1);
X2 = exp(U2);
X = 0.5*(X1+X2);
I=mean(X);
mprintf("I=%f\n",I);
S = variance(X);
z = distfun_norminv(0.95,0,1);
H = z * sqrt(S/N);
mprintf("90%% confidence interval: [%f,%f]\n",I-H, I+H);
coeffofvar = H/I;
mprintf("Coef. of var.: %f\n",coeffofvar);

C = cov ( X1 , X2 );
mprintf("Covariance Matrix(X1,X2):\n")
disp(C);

//
// Example 4.6
//
mprintf("\n\n");
mprintf("Integrate h(x) ""triangle"" on [0,1].\n");
//
function y = h(x)
  y = zeros(x)
  y(x<0) = 0
  k = find(x>=0 & x<=0.5)
  y(k) = 2*x(k)
  k = find(x>=0.5 & x<=1.)
  y(k) = 2-2*x(k)
  y(x>1) = 0
endfunction
//
x = linspace(0,1,1000);
y = h(x);
scf();
plot(x,y,"b-");
xtitle("Triangle","X","Y");
//
mprintf("With naive Monte-Carlo experiment\n");
N = 100;
mprintf("N=%d\n",N);
distfun_seedset(0);
U = distfun_unifrnd(0,1,1,N);
X = h(U);
I=mean(X);
mprintf("I=%f\n",I);
S = variance(X);
z = distfun_norminv(0.95,0,1);
H = z * sqrt(S/N);
mprintf("90%% confidence interval: [%f,%f]\n",I-H, I+H);
coeffofvar = H/I;
mprintf("Coef. of var.: %f\n",coeffofvar);

// 
mprintf("With antithetic variables (U,1-U)\n");
N = 100;
Na = floor(N/2);
mprintf("N=%d\n",N);
distfun_seedset(0);
U1 = distfun_unifrnd(0,1,1,Na);
U2 = 1-U1;
X1 = h(U1);
X2 = h(U2);
X = 0.5*(X1+X2);
I=mean(X);
mprintf("I=%f\n",I);
S = variance(X);
z = distfun_norminv(0.95,0,1);
H = z * sqrt(S/N);
mprintf("90%% confidence interval: [%f,%f]\n",I-H, I+H);
coeffofvar = H/I;
mprintf("Coef. of var.: %f\n",coeffofvar);
//
C = cov ( X1 , X2 );
mprintf("Covariance Matrix(X1,X2):\n")
disp(C)
//

endfunction 
demoVarianceReduction();
clear demoVarianceReduction

//
// Load this script into the editor
//
filename = "varianceReduction.sce";
dname = get_absolute_file_path(filename);
editor ( fullfile(dname,filename) );

// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- JVM NOT MANDATORY -->

path = distfun_getpath (  );
exec(fullfile(path,"tests","unit_tests","testingutilities.sce"));

// Multinomial distribution
// Reference
// http://en.wikipedia.org/wiki/Multinomial_distribution

//
// Test the distribution of random numbers
//
rtol = 1.e-1;
atol = 1.e-1;

// The number of classes in the histogram
// NC must be even.
m=10000;

// Set the seed to always get the same random numbers
distfun_seedset(0);


n=2;
P=[0.1,0.2,0.7];
R=distfun_mnrnd(n,P,m);
[M,C]=distfun_mnstat(n,P);
assert_checkalmostequal ( mean(R,"r") , M , rtol , atol );
assert_checkalmostequal ( test_cov(R) , C , rtol , atol );



rtol = 1.e-2;
atol = 1.e-1;

// The number of classes in the histogram
// NC must be even.
NC = 2*12;
m=10000;

// Set the seed to always get the same random numbers
distfun_seedset(0);

// Consider the following experiment. 
// For each experiment, we perform n trials.
// For each trial, there are 3 outcomes, 
// with probabilities 
// p1=0.2
// p2=0.4
// p3=1-p1-p2=0.4
// Then the random variable R(1:n,i) in {0,1,2,...,n} is the 
// number of outcomes in each category, for i=1,2,...,n. 
m = 100000;
n = 10;
P = [0.2,0.4,0.4];
k = size(P,"*");
R=distfun_mnrnd(n,P,m);
assert_checkequal(size(R),[m k]);
assert_checktrue(R>=0);
assert_checktrue(R<=n);
assert_checkequal(sum(R,"c"),n*ones(m,1));
M = mean(R,"r");
C = test_cov(R);
[Mexpected,Cexpected] = distfun_mnstat(n,P);
assert_checkalmostequal(M,Mexpected,1.e-1,[],"element");
assert_checkalmostequal(C,Cexpected,1.e-1,[],"element");
//
// Compute empirical PDF
EmpiricalPDF = [];
for j1 = 0 : n
    for j2 = 0 : n
        EmpiricalPDF(j1+1,j2+1) = length(find(R(:,1)==j1 & R(:,2)==j2));
    end
end
EmpiricalPDF = EmpiricalPDF./m;
//
// Compute theoretical PDF
TheoricPDF = zeros(n+1,n+1);
for j1 = 0 : n
    for j2 = 0 : n
        j3 = max(n-j1-j2,0);
        if (j1+j2+j3<>n) then
            continue
        end
        x = [j1 j2 j3]';
        TheoricPDF(j1+1,j2+1) = distfun_mnpdf(x',n,P);
    end
end
//
atol = 0.05*max(TheoricPDF);
assert_checkalmostequal(EmpiricalPDF,TheoricPDF,[],atol,"element");

if (%f) then
    X = 0:n;
    h = scf();
    nbrows=floor(sqrt(n));
    nbcols=ceil(sqrt(n));
    for j1 = 0:n
        subplot(nbrows,nbcols,j1+1)
        plot(X,EmpiricalPDF(j1+1,:),"bo-"); // Empirical Histogram
        plot(X,TheoricPDF(j1+1,:),"rox-"); // Theoretical Histogram
        legend(["Data","PDF"]);
        xtitle("Distribution of X : X1="+string(j1),"X2","P");
        g=gca();
        g.data_bounds(2,2)=max(TheoricPDF);
    end
end

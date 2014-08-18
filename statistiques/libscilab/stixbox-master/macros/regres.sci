// Copyright (C) 2012-2013 - Michael Baudin
// Copyright (C) 2012 - Maria Christopoulou 
// Copyright (C) 2009 - Yann Collette
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [B,bint,r,rint,stats,fullstats] = regres(varargin)
    // Multiple linear regression
    //
    // Calling Sequence
    //   B = regres(y,X)
    //   B = regres(y,X,level)
    //   [B,bint] = regres(...)
    //   [B,bint,r] = regres(...)
    //   [B,bint,r,rint] = regres(...)
    //   [B,bint,r,rint,stats] = regres(...)
    //   [B,bint,r,rint,stats,fullstats] = regres(...)
    //
    // Parameters
    // y : a m-by-1 matrix of doubles, the responses.
    // X : a m-by-n matrix of doubles, the inputs, where m is the number of observations and n is the number of variables.
    // level : a 1-by-1 matrix of doubles, the confidence level (default level=0.05, meaning that the default level is 95%). level is expected to be in the range [0.,0.5]
    // B : a n-by-1 matrix of doubles, the coefficients of the linear model.
    // bint : a 2-by-2 matrix of doubles, intervals with confidence level. The column bin(:,1) are the lower bounds and bin(:,2) are the upper bounds.
    // r : a m-by-1 matrix of doubles, the residuals y-B(1)-B(2)*x
    // stats : a 4-by-1 matrix of doubles, the statistics. stats(1) is the R2 statistics, stats(2) is the F statistics, stats(3) is the p-value of the F statistics, stats(4) is an estimate of the error variance.
    // fullstats : a struct, the statistics, see below for details. 
    //
    // Description
    // This function computes a linear model with 
    // n independent variables x1, x2, ..., xn 
    // which best fit the data in the least squares sense. 
    //
    // The linear model we compute is 
    //
    // y = B(1)*x1 + B(2)*x2 +...+ B(n)*xn.
    //
    // The fields in <literal>fullstats</literal> are :
    // <itemizedlist>
    // <listitem> <para>
    // fullstats.RegressionSS: the sum of squares of the regression
    // </para> </listitem>
    // <listitem> <para>
    // fullstats.RegressionDof: the number of degrees of freedom of the regression
    // </para> </listitem>
    // <listitem> <para>
    // fullstats.RegressionMean: the mean of the sum of squares of the regression
    // </para> </listitem>
    // <listitem> <para>
    // fullstats.ResidualSS: the sum of squares of the residuals
    // </para> </listitem>
    // <listitem> <para>
    // fullstats.ResidualDof: the number of degrees of freedom of the residuals
    // </para> </listitem>
    // <listitem> <para>
    // fullstats.ResidualMean: the mean of the sum of squares of the residuals
    // </para> </listitem>
    // <listitem> <para>
    // fullstats.F: the F statistics
    // </para> </listitem>
    // <listitem> <para>
    // fullstats.pval : the p-value corresponding to the F statistics
    // </para> </listitem>
    // <listitem> <para>
    // fullstats.Bstddev: a (n+1)-by-1 matrix of doubles, the standard deviation of B
    // </para> </listitem>
    // <listitem> <para>
    // fullstats.R2: the R-squared statistics
    // </para> </listitem>
    // </itemizedlist>
    //
    // The residual r is :
    // <screen>
    // r == y-X*B
    // </screen>
    //
    // Assuming that X and y are column vectors, 
    // the simplest way to perform a simple linear regression 
    // is to use the statement
    // <screen>
    // B = regres(y,[ones(X),X])
    // </screen>
    // This adds a column of 1s, so that the model has a constant 
    // term.
    //
    // The statistics fullstats.R2 is in the range [0,1]. 
    // On output, the coefficient of determination fullstats.R2 
    // measures the proportion of the variation in the response variables 
    // that is explained by the different input values. 
    // A value of fullstats.R2 near 1 indicates a good fit, while 
    // a value near zero indicates a poor fit. 
    //
    // The F statistics tests the null hypothesis 
    // B(1)==B(2)==...==B(n) agains the alternative 
    // hypothesis that there is one nonzero B(i), for 
    // some i=1,2,...,n. 
    // The F test does not tell which of the B(i) is nonzero, 
    // but only that one of them is linearly related 
    // to the response.
    // When the P-value for the F test statistic is small (e.g. 
    // less than 0.001), this provides an evidence against 
    // the null hypothesis. 
    // In other words, if the P-value is small, 
    // therefore one of the parameters B(i) is linearly related 
    // to the response. 
    //
    // Caution! If we are interested in F-statistics and 
    // its p-value, then the X input argument should contain a 
    // column of 1s, so that the model has a constant term.
    //
    // Compatibility note
    // 
    // This function is mostly compatible with Matlab, 
    // except that Matlab has no <literal>fullstats</literal> output argument, 
    // which allows to have much more outputs than 
    // the entries in <literal>stat</literal>. 
    //
    // In R, the feature is provided by the <literal>lm</literal> function and 
    // the function <literal>anova</literal> prints the analysis of 
    // variance table. 
    // In this toolbox, this feature is provided by the 
    // <literal>regresprint</literal> function, 
    // which uses the fields in the <literal>fullstats</literal> variable. 
    // This would not have been possible with the <literal>stats</literal> 
    // variable alone.
    //
    // The <literal>regres</literal> function name was chosen because 
    // <literal>regress</literal> already exists in Scilab, but Scilab's 
    // <literal>regress</literal> is not Matlab-compatible, and has much 
    // less features.
    //
    // Examples
    // // A simple linear regression
    // X = [
    //     57.   
    //     64.   
    //     69.   
    //     82.   
    //     92.   
    //     111.  
    //     114.  
    //     132.  
    //     144.  
    //     146.  
    // ];
    // Y = [
    //     121.  
    //     129.  
    //     140.  
    //     164.  
    //     188.  
    //     217.  
    //     231.  
    //     264.  
    //     289.  
    //     294.  
    // ];
    // B = regres(Y,[ones(X),X])
    // expected = [4.8222564;1.9671389];
    // // Visually check the result:
    // scf();
    // plot(X,Y,"bo");
    // L = B(1)+B(2)*X;
    // plot(X,L,"r-")
    // legend(["Data","Linear Fit"]);
    // xtitle("Linear Regression","X","Y");
    //
    // // Compute a 95% confidence interval.
    // // Visually check the results. 
    // // Visually check the residuals. 
    // // Reference : [2]
    // Height = [
    // 1.47	1.50	1.52	1.55	1.57	..
    // 1.60	1.63	1.65	1.68	1.70	..
    // 1.73	1.75	1.78	1.80	1.83
    // ];
    // Mass =[
    // 52.21	53.12	54.48	55.84	57.20	..
    // 58.57	59.93	61.29	63.11	64.47	..
    // 66.28	68.10	69.92	72.19	74.46
    // ];
    // Height = Height(:);
    // Mass = Mass(:);
    // [B,bint] = regres(Mass,[ones(Height),Height])
    // Bexpected = [
    // -39.062
    // 61.272
    // ];
    // Bintexpected = [
    // -45.4 -32.7
    // 57.4 65.1
    // ];
    // // Visually check the result:
    // scf();
    // plot(Height,Mass,"bo");
    // L = B(1)+B(2)*Height;
    // plot(Height,L,"r-")
    // legend(["Data","Linear Fit"]);
    // xtitle("Linear Regression","Height","Mass");
    // // Compute the residuals
    // [B,bint,r] = regres(Mass,[ones(Height),Height]);
    // // Visually see the residuals
    // scf();
    // plot(Height,r,"bo");
    // xtitle("Linear Regression","Height","Residuals");
    // // A quadratic fit would be better:
    // // we see a quadratic pattern in the residuals. 
    //
    // // Longley.dat contains 1 Response Variable y, 
    // // 6 Predictor Variables x and 16 Observations.
    // // Source : [4]
    // X = [
    // 83.0 234289 2356 1590 107608 1947
    // 88.5 259426 2325 1456 108632 1948
    // 88.2 258054 3682 1616 109773 1949
    // 89.5 284599 3351 1650 110929 1950
    // 96.2 328975 2099 3099 112075 1951
    // 98.1 346999 1932 3594 113270 1952
    // 99.0 365385 1870 3547 115094 1953
    // 100.0 363112 3578 3350 116219 1954
    // 101.2 397469 2904 3048 117388 1955
    // 104.6 419180 2822 2857 118734 1956
    // 108.4 442769 2936 2798 120445 1957
    // 110.8 444546 4681 2637 121950 1958
    // 112.6 482704 3813 2552 123366 1959
    // 114.2 502601 3931 2514 125368 1960
    // 115.7 518173 4806 2572 127852 1961
    // 116.9 554894 4007 2827 130081 1962
    // ];
    // y = [
    // 60323
    // 61122
    // 60171
    // 61187
    // 63221
    // 63639
    // 64989
    // 63761
    // 66019
    // 67857
    // 68169
    // 66513
    // 68655
    // 69564
    // 69331
    // 70551
    // ];
    // [B,bint,r,rint,stats,fullstats] = regres(y,X)
    // // Print an analysis of variance table
    // scidoe_regressprint(fullstats)
    //
    // // Create an example with known coefficients
    // grand("setsd",0);
    // // The number of observations
    // N = 100;
    // // The exact coefficients
    // Bexact = [2000;3;4]
    // // The input 1
    // X1min = 50;
    // X1max = 150;
    // X1=grand(N,1,"uin",X1min,X1max);
    // X1=gsort(X1,"g","i");
    // // The input 2
    // X2min = -500;
    // X2max = -200;
    // X2=grand(N,1,"uin",X2min,X2max);
    // X2=gsort(X2,"g","i");
    // // Make the observations
    // X = [ones(X1),X1,X2];
    // // Compute the ouput from the input
    // y = Bexact(1)+Bexact(2)*X1+Bexact(3)*X2;
    // // Add a normal noise to the output
    // sigma = 50;
    // e = grand(N,1,"nor",0,sigma);
    // y = int(y + e);
    // // Linear regression
    // B = regres(y,X)
    // // Compare B (exact) with B (estimate)
    // [B,Bexact]
    // L = B(1)+B(2)*X(:,2)+B(3)*X(:,3);
    // // Check visually
    // scf();
    // plot(y,L,"bo");
    // plot(y,y,"r-");
    // xtitle("Linear Regression","Data","Linear Fit");
    //
    // // A short example of univariate linear regression
    // X = rand(30,1,"n");
    // Y = 2+3*X+rand(30,1,"n");
    // scf();
    // plot(X,Y,"bo")
    // B = scidoe_regress(Y,[ones(X),X]);
    // plot(X,B(1)*ones(X)+B(2)*X,"r-")
    // // To see which points are there, please use:
    // // identify(X,Y,1)
    // // Click middle button to leave.
    //
    // Authors
    // Copyright (C) 2012-2013 - Michael Baudin
    // Copyright (C) 2012 - Maria Christopoulou 
    // Copyright (C) 2009 - Yann Collette
    //
    // Bibliography
    // [1] "Introduction to probability and statistics for engineers and scientists.", Third Edition, Sheldon Ross, Elsevier Academic Press, 2004
    // [2] http://en.wikipedia.org/wiki/Linear_regression
    // [3] Octave's regres, William Poetra Yoga Hadisoeseno
    // [4] http://www.itl.nist.gov/div898/strd/lls/data/LINKS/DATA/Longley.dat

    // Check number of input arguments
    [lhs,rhs] = argn();
    apifun_checkrhs("regres",rhs,2:3);
    apifun_checklhs("regres",lhs,1:6);
    //
    y = varargin(1)
    X = varargin(2)
    level = apifun_argindefault ( varargin , 3 , 1.-0.95 )
    //
    // Check type
    apifun_checktype("regres",X,"X",1,"constant");
    apifun_checktype("regres",y,"y",2,"constant");
    apifun_checktype("regres",level,"level",3,"constant");
    //
    // Check size
    [m,n] = size(X)
    n = n - 1
    apifun_checkdims("regres",y,"y",2,[m 1]);
    apifun_checkscalar("regres",level,"level",3);
    //
    // Check content
    apifun_checkrange("regres",level,"level",3,0,0.5);
    //
    // Proceed...
    // Workaround for the lack of accuracy of backslash
    // See http://bugzilla.scilab.org/show_bug.cgi?id=11379
    // We should use :
    // B = X\y
    // but pinv (which uses the SVD) is more 
    // accurate in some cases.
    [U,S,V] = svd(X,"e");
    S = diag(S)
    tol = max(size(X)) * S(1) * %eps
    // Rank
    rnk=size(find(S>tol),"*")
    if rnk == 0 then
        IA = zeros(X');
    else
        V = V(:,1:rnk)
        U = U(:,1:rnk)
        IS = diag(ones(rnk,1)./S(1:rnk))
        IA = V* IS *U'
    end
    B = IA*y
    // Compute residuals
    r = y-X*B
    // Create fullstats
    fullstats = struct()
    // Residual 
    Ymean = mean(y)
    fullstats.RegressionSS = sum((X*B-Ymean).^2)
    fullstats.RegressionDof = n
    fullstats.RegressionMean = fullstats.RegressionSS/fullstats.RegressionDof
    // Regression 
    fullstats.ResidualSS = sum(r.^2)
    fullstats.ResidualDof = m-n-1
    fullstats.ResidualMean = fullstats.ResidualSS/fullstats.ResidualDof
    // F statistics
    fullstats.F = fullstats.RegressionMean/fullstats.ResidualMean
    // Compute the p-value
    Q=distfun_fcdf(fullstats.F,n,m-n-1,%f)
    fullstats.pval = Q
    // The covariance matrix : C
    // CC is mathematically equal to inv(X'*X)
    CC = V*IS.^2*V'
    C = fullstats.ResidualMean*diag(CC)
    // The standard deviation of B
    fullstats.Bstddev = sqrt(C)
    // Compute R-squared :
    // the coefficient of determination.
    SYY = sum((y-Ymean).^2)
    fullstats.R2 = 1-fullstats.ResidualSS/SYY
    // Compute 100*(1-level) confidence interval
    level = 0.05
    Q = level/2
    T=distfun_tinv(Q,m-n-1,%f)
    Bsig = T*sqrt (C)
    bint(:,1) = B-Bsig
    bint(:,2) = B+Bsig
    // Compute confidence interval on r
    dof1 = m-n-2
    h = diag(X*IA)
    dr=T*sqrt ((fullstats.ResidualSS*(1-h)-(r.^2))/dof1)
    rint(:,1)=r-dr
    rint(:,2)=r+dr
    // Create stats
    stats(1) = fullstats.R2
    stats(2) = fullstats.F
    stats(3) = fullstats.pval
    stats(4) = fullstats.ResidualMean
endfunction

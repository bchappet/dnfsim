// Copyright (C) 2013 - Michael Baudin
// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function qqplot(x,y,ps)
    // Create a QQ-plot
    //  
    // Calling Sequence
    //   qqplot(x,y)
    //   qqplot(x,y,ps)
    //             
    // Parameters
    // x : a n-by-1 matrix of doubles
    // y : a n-by-1 matrix of doubles
    // ps : a 1-by-1 matrix of strings, the plot symbol (default="*")
    //
    // Description
    // If two distributions are the same (or possibly linearly
    // transformed) the points should form an approximately straight
    // line. 
    //
    // A red solid line joining the first and third quartiles of x and y 
    // is plot. 
    // This is a linear fit of the order statistics of the two samples.
    // An extrapolation of this line below the first and over the third
    // quantile is performed, and plot as a red dotted line.
    //
    // The qqplot function is partly compatible with Matlab.
    //
    // Examples
    // // Experiment A
    // x = distfun_poissrnd(10,100,1);
    // y = distfun_poissrnd(5,100,1);
    // scf();
    // qqplot(x,y);
    // // Change the symbol
    // scf();
    // qqplot(x,y,"r.");
    // 
    // // Experiment B
    // // Check a sample of uniform numbers:
    // // against normal numbers:
    // // uniform is obviously non-normal.
    // n=10000;
    // x=distfun_unifrnd(0,1,n,1);
    // p=linspace(0.01,0.99,100)';
    // q=quantile(x,p);
    // y=distfun_norminv(p,mean(x),sqrt(variance(x)));
    // scf();
    // qqplot(y,q,"bo");
    // xtitle("U(0,1) - n=10 000",..
    // "Normal Quantile","Uniform Quantile");
    // 
    // // Experiment C
    // // Check a sample of exponential numbers 
    // // against normal numbers:
    // // exp is obviously non-normal.
    // n=10000;
    // x=distfun_exprnd(1,n,1);
    // y=distfun_normrnd(0,1,n,1);
    // scf();
    // qqplot(x,y,"bo");
    // xtitle("Exp(1) - n=10 000",..
    // "Exp Quantile","Normal Quantile");
    // 
    // // Experiment D
    // // Check two sample of normal numbers:
    // // it matches.
    // n=10000;
    // x1=distfun_normrnd(0,1,n,1);
    // x2=distfun_normrnd(0,2,n,1);
    // scf();
    // qqplot(x1,x2,"bo");
    // xtitle("n=10 000",..
    // "Normal(0,1)","Normal(0,2)");
    //
    // // Experiment E
    // // Reference
    // // "Fitting data into probability distributions"
    // // Tasos Alexandridis
    // // Generate data that follow an 
    // // exponential distribution with mu = 4
    // values = distfun_exprnd(4,100,1);
    // // Generate random Gaussian noise N(0,1)
    // noise = distfun_normrnd(0,1,100,1);
    // // Add noise to the exponential distributed data 
    // // so as to look more realistic
    // realdata = values + abs(noise);
    // // Estimate the parameter
    // paramhat=mean(values)
    // syntheticData = distfun_exprnd(4.9918,100,1);
    // scf();
    // qqplot(realdata,syntheticData);
    //
    // Authors
    // Copyright (C) 2013 - Michael Baudin
    // Copyright (C) 2010 - DIGITEO - Michael Baudin
    // Copyright (C) 1993 - 1995 - Anders Holtsberg

    [nargout,nargin] = argn(0)

    if nargin<3 then
        ps = '*';
    end
    %v = x
    if min(size(%v))==1 then 
        %v=gsort(%v)
    else 
        %v=gsort(%v,'r')
    end
    x = %v($:-1:1,:);
    %v = y
    if min(size(%v))==1 then 
        %v=gsort(%v)
    else 
        %v=gsort(%v,'r')
    end
    y = %v($:-1:1,:);
    plot(x,y,ps);
    // Add the 0.25-0.75 quantile line
    p=linspace(0.01,0.99,100)';
    q1=quantile(x,p);
    q2=quantile(y,p);
    plot([q1(25),q1(75)],[q2(25),q2(75)],"r-")
    a=(q2(25)-q2(75))/(q1(25)-q1(75))
    b=q2(25)-a*q1(25)
    y1=a*x(1)+b
    plot([x(1),q1(25)],[y1,q2(25)],"r-.")
    y2=a*x($)+b
    plot([q1(75),x($)],[q2(75),y2],"r-.")
endfunction

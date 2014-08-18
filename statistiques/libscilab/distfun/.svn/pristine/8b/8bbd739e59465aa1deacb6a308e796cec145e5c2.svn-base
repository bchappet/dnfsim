// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function demo_betarejection()

    // Example of rejection sampling. 
    // Reference
    // Karl Sigman. Acceptance-rejection method, 2007. 
    // Professor Karl Sigman’s Lecture Notes on Monte Carlo Simulation, 
    // Columbia University, 
    // http://www.columbia.edu/~ks20/4703-Sigman/4703-07-Notes-ARM.pdf.

    // See how to generate Beta(a,b) random variables, 
    // when a,b>1 using a rejection algorithm 
    // and a instrumental uniform distribution.

    function x=mybetarejectrnd(a,b)
        m=(a-1)/(a+b-2); // The mode, if a,b>1
        c=distfun_betapdf(m,a,b);
        while (%t)
            y=distfun_unifrnd(0,1)
            u=distfun_unifrnd(0,1)
            f=distfun_betapdf(y,a,b)
            // g=distfun_unifpdf(y,0,1) = 1
            if (u<f/c) then
                x=y
                break
            end
        end
    endfunction
    scf();

    // 1. Check the rejection algorithm
    subplot(1,2,1)
    a=2;
    b=5;
    m=(a-1)/(a+b-2); // The mode, if a,b>1
    c=distfun_betapdf(m,a,b);
    mprintf("Beta(%.0f,%.0f) distribution\n",a,b)
    mprintf("Mode x=%f\n",m)
    mprintf("Maximum f(x)=%f\n",c)
    x=linspace(0,1);
    y=distfun_betapdf(x,a,b);
    plot(x,y);
    plot(x,c*ones(x),"r-")
    plot([m m],[0 c],"r-")
    plot(m,c,"g*")
    legend(["Beta(a,b)","Maximum"]);
    strtitle=msprintf("Beta(%.0f,%.0f), N=%d outcomes",a,b,R);
    xtitle(strtitle,"X","Density")
    R=500;
    mprintf("Generating %d samples...\n\n",R)
    x=zeros(R);
    for i=1:R
        x(i)=mybetarejectrnd(a,b);
    end
    limits=linspace(0,1,20);
    histplot(limits,x);

    // 2. See accepted/rejected points
    subplot(1,2,2)
    a=2;
    b=5;
    x=linspace(0,1);
    y=distfun_betapdf(x,a,b);
    plot(x,y);
    plot(x,c*ones(x),"r-")
    legend(["PDF","Maximum"]);
    R=100; // Number of repetitions
    mprintf("Generating %d samples...\n",R)
    m=(a-1)/(a+b-2); // The mode, if a,b>1
    c=distfun_betapdf(m,a,b);
    strtitle=msprintf("Beta(%.0f,%.0f), %d outcomes",a,b,R);
    xtitle(strtitle,"X","Density")
    // Initialize
    nbaccept=0;
    nbreject=0;
    x=zeros(R);
    for i=1:R
        while (%t)
            y=distfun_unifrnd(0,1);
            u=distfun_unifrnd(0,1);
            f=distfun_betapdf(y,a,b);
            if (u<f/c) then
                plot(y,u*c,"go");
                nbaccept=nbaccept+1;
                x(i)=y;
                break;
            else
                nbreject=nbreject+1;
                plot(y,u*c,"rx");
            end
        end
    end
    limits=linspace(0,1,10);
    histplot(limits,x);
    nbtotal=nbaccept+nbreject;
    mprintf("Nb Accepted=%d\n",nbaccept)
    mprintf("Nb Rejected=%d\n",nbreject)
    mprintf("Total=%d\n",nbtotal)
    mprintf("C=%f\n",c)
    mprintf("Mean Iterations=%.2f\n",nbtotal/R)
endfunction 
demo_betarejection();
clear demo_betarejection

//
// Load this script into the editor
//
filename = "beta-reject.sce";
dname = get_absolute_file_path(filename);
editor ( fullfile(dname,filename) );

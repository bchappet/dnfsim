// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// Experiment the Metropolis-Hasting algorithm

// Reference
// http://en.wikipedia.org/wiki/Metropolis%E2%80%93Hastings_algorithm
// 
// The Metropolis-Hastings Algorithm
// COMPSCI 3016: Computational Cognitive Science
// Dan Navarro & Amy Perfors
// University of Adelaide
// 2010
// http://health.adelaide.edu.au/psychology/ccs/docs/ccs-class/technote_metropolishastings.pdf

function demometropolis()

    function y=mynonstandardf(x)
        y=exp(-x.^2).*(2+sin(5*x)+sin(2*x))
    endfunction
    C=intg(-3,3,mynonstandardf);
    x=linspace(-3,3);
    y=mynonstandardf(x);
    plot(x,y/C)
    xtitle("A nonstandard PDF","X","Density")

    //
    function [x,acceptrate]=metropolis(sigma,nbiter)
        xprev=distfun_normrnd(0,sigma); // Initial point
        pprev=mynonstandardf(xprev); // Initial density
        x=zeros(nbiter);
        a2=1;
        nbaccept=0;
        for i=1:nbiter
            xcand=distfun_normrnd(xprev,sigma); // Candidate
            // Evaluate likelihood ratio
            pcand=mynonstandardf(xcand);
            a1=pcand/pprev;
            //qprev=distfun_normpdf(xprev,xcand,sigma);
            //qcand=distfun_normpdf(xcand,xprev,sigma);
            //a2=qprev/qcand; // a2=1 in this special case (check it !)
            a=a1*a2;
            if (a>=1) then
                //mprintf("a=%f, Accept x=%f\n",a,xcand);
                // Accept
                nbaccept=nbaccept+1;
                x(i)=xcand;
                xprev=xcand;
                pprev=pcand;
            else
                // Accept with probability a
                binox=distfun_binornd(1,a);
                if (binox==1) then
                    //mprintf("a=%f, Accept x=%f\n",a,xcand);
                    nbaccept=nbaccept+1;
                    x(i)=xcand;
                    xprev=xcand;
                    pprev=pcand;
                else
                    //mprintf("a=%f, Reject x=%f\n",a,xcand);
                    x(i)=xprev;
                end
            end
        end
        acceptrate=nbaccept/nbiter;
        mprintf("Metropolis-Hastings, Sigma=%.2f, N=%d, Rate=%.2f", ..
        sigma, nbiter, acceptrate);
    endfunction

    function plotmetropolis(nbiter,sigma)
        t=linspace(-3,3);
        y=mynonstandardf(t);
        [x,acceptrate]=metropolis(sigma,nbiter);
        edges=linspace(-3,3,20)
        histplot(edges,x);
        plot(t,y/C);
        legend(["Data","PDF"]);
        strtitle=msprintf("Metropolis-Hastings, Sigma=%.2f, N=%d, Rate=%.2f", ..
        sigma, nbiter, acceptrate);
        xtitle(strtitle,"X","Density")
    endfunction

    // 
    scf();
    sigma=1; // Width of the proposal distribution
    nbiter=10000;
    plotmetropolis(nbiter,sigma)

    // 
    scf();
    subplot(2,2,1)
    plotmetropolis(1000,1)
    subplot(2,2,2)
    plotmetropolis(1000,2)
    subplot(2,2,3)
    plotmetropolis(1000,10)
    subplot(2,2,4)
    plotmetropolis(1000,0.1)
endfunction
demometropolis();
clear demometropolis

//
// Load this script into the editor
//
filename = "metropolis-hastings.sce";
dname = get_absolute_file_path(filename);
editor ( fullfile(dname,filename) );

// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// The Box-Muller method
// to generate standard normal variables.
// Reference
// http://en.wikipedia.org/wiki/Box%E2%80%93Muller_transform
// G. E. P. Box and Mervin E. Muller, 
// A Note on the Generation of Random Normal Deviates, 
// The Annals of Mathematical Statistics (1958), 
// Vol. 29, No. 2 pp. 610–611

function demoBoxMuller()
    // Plot 2*R standard normal numbers
    R=1000;
    x=zeros(2*R);
    for i=1:R
        u=distfun_unifrnd(0,1,[1 2]);
        t=2*%pi*u(1);
        r=sqrt(-2*log(u(2)));
        x(i)=r*cos(t);
        x(i+R)=r*sin(t);
    end

    scf();
    histo(x,[],[],1); // Requires Stixbox
    x=linspace(-3,3);
    y=distfun_normpdf(x,0,1);
    plot(x,y)
    strtitle=msprintf("Box-Muller algorithm : %d numbers",2*R)
    xtitle(strtitle,"X","Density");
    legend(["Data","PDF"]);
endfunction 
demoBoxMuller();
clear demoBoxMuller

//
// Load this script into the editor
//
filename = "boxmuller.sce";
dname = get_absolute_file_path(filename);
editor ( fullfile(dname,filename) );

// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2009 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function demoLatticeUrand()

    mprintf("See how the Urand random number generator\n")
    mprintf("generates points on a lattice\n")
    mprintf("Wait: this demo requires a large number of points\n")
    mprintf("before the lattice appear...\n")
    mprintf("See the comments in this demo for more details.\n")
    //
    // Generate uniform numbers in [0,1]^2
    // Keep only those in [0,t]^2,
    // where t = 0.001.
    // We stop when we have N=4000 such points.
    // We proceed by blocks of NBlock points (X1,X2).
    // The probability P((X1,X2) in [0,t])=t^2.
    // Therefore, each trial produces 
    // NBlock*t^2 new points.
    // For example, with NBlock=1.e7 and t=0.001, this is 10 new points 
    // for each experiment.
    // This is slow...
    // We see how "urand" produces points on a lattice.
    //
    stacksize("max");
    sz=stacksize();
    nfree = sz(1)-sz(2);
    NBlock = floor(nfree/100);
    RFull = [];
    N = 4000;
    t = 0.001;
    mprintf("t = %e\n",t);
    mprintf("Nblock = %d\n",NBlock);
    mprintf("N = %d\n",N);
    i = 1;
    h = scf();
    xtitle("Uniform Pseudo-RNG : ""urand""","X1","X2")
    distfun_genset("urand");
    Ntotal = 0;
    eTotal = 0;
    while (%t)
        mprintf("Block #%d, Total gen.=2^%.3f\n",i,eTotal);
        Ntotal = Ntotal + NBlock;
        eTotal = log2(Ntotal);
        R = distfun_unifrnd(0,1,NBlock,2);
        k = find(R(:,1)<t & R(:,2)<t);
        nbnew = size(k,"r");
        R = R(k,:);
        RFull = [RFull;R];
        nfull = size(RFull,"r");
        mprintf("  New = %d, Total=%d/%d points in [0,t)^2\n",...
        nbnew,nfull,N)
        if (nfull>N) then
            break
        end
        i = i+1;
        if ( R<> [] ) then
            drawlater();
            plot(R(:,1),R(:,2),"b.");
            h.children.children.children.mark_size=1;
            drawnow();
        end
    end

endfunction 
demoLatticeUrand();
clear demoLatticeUrand

//
// Load this script into the editor
//
filename = "lattice_urand.sce";
dname = get_absolute_file_path(filename);
editor ( fullfile(dname,filename) );

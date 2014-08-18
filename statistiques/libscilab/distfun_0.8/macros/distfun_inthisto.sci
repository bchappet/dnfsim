// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
//
function H = distfun_inthisto(varargin)
    // Discrete histogram
    //
    // Calling Sequence
    //   distfun_inthisto(data)
    //   distfun_inthisto(data,nrmlz)
    //   H = distfun_inthisto(...)
    //
    // Parameters
    //   data : a n-by-1 or 1-by-n matrix of doubles, integer value, the discrete data.
    //   nrmlz : a 1-by-1 matrix of booleans, true to normalize the histogram, false to plot the unnormalized histogram (default nrmlz=%t)
    //   H : a n-by-2 matrix of doubles. H(:,1) is the X coordinate of the histogram and contains the unique data entries. H(:,2) is the Y coordinate of the histogram. If nrmlz is false, H(:,2) is the number of occurences of the corresponding value in H(:,1). If nrmlz is true, H(:,2) is the fraction of the data equal to the corresponding value in H(:,1).
    //
    // Description
    //   Plots the empirical histogram of a set of discrete values. 
    //
    //   The data is not normalized, then the entries in H(:,2) have 
    //   integer values in the range {0,1,2,...,n} and sum(H(:,2))==n. 
    //   In this case, H(i,2) is the number of times H(i,1)==data, 
    //   where i=1,2,...,n.
    //
    //   The data is normalized, then the entries in H(:,2) are real values in the 
    //   range [0,1] and sum(H(:,2))==1. 
    //   In this case, H(i,2) is the number of times H(i,1)==data divided by n, 
    //   where i=1,2,...,n.
    //
    //   The difference with the histplot function is that histplot
    //   is well suited for real values, while distfun_inthisto 
    //   well suited for integer values. 
    //   More preciselly, the classes in distfun_inthisto are 
    //   the unique entries in the data, while histplot 
    //   considers intervals. 
    //
    // Examples
    // pr=0.7;
    // N=10000;
    // R=distfun_geornd(pr,1,N);
    // scf();
    // T = distfun_inthisto(R)
    // scf();
    // T = distfun_inthisto(R,%f)
    //
    // // Compare with PDF
    // pr=0.7;
    // N=1000;
    // R=distfun_geornd(pr,1,N);
    // scf();
    // T = distfun_inthisto(R)
    // x=0:6;
    // y = distfun_geopdf(x,pr);
    // plot(x,y,"ro-")
    // legend(["N=1000","PDF"])
    // xlabel("X")
    // ylabel("P")
    // title("Geometric Distribution pr=0.7")
    //
    // See also
    //  tabul
    //  bar
    //  histplot
    // 
    // Authors
    // Copyright (C) 2012 - Michael Baudin

    [lhs,rhs] = argn();
    apifun_checkrhs("distfun_inthisto",rhs,1:2)
    apifun_checklhs("distfun_inthisto",lhs,0:1)

    data = varargin(1)
    nrmlz = apifun_argindefault(varargin,2,%t)
    //
    // Check type
    apifun_checktype("distfun_inthisto",data,"data",1,"constant")
    apifun_checktype("distfun_inthisto",nrmlz,"nrmlz",2,"boolean")
    //
    // Check size
    apifun_checkvector("distfun_inthisto",data,"data",1)
    apifun_checkscalar("distfun_inthisto",nrmlz,"nrmlz",2)
    //
    // Check content
    apifun_checkflint("distfun_inthisto",data,"data",1)

    data=data(:)
    H=tabul(data,"i");
    if (nrmlz) then
        n=size(data,"*")
        H(:,2)=H(:,2)/n;
    end
    bar(H(:,1),H(:,2));
endfunction

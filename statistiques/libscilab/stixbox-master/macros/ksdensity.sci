// Copyright (C) 2013 - Michael Baudin
// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [f,xi,u]=ksdensity(varargin)
    // Kernel smoothing density estimate
    //  
    // Calling Sequence
    //   ksdensity(x)
    //   ksdensity(x,xi)
    //   ksdensity(x,xi,"kernel",akernel)
    //   ksdensity(x,xi,"npoints",npoints)
    //   ksdensity(x,xi,"support",support)
    //   ksdensity(x,xi,"width",width)
    //   f=ksdensity(...)
    //   [f,xi]=ksdensity(...)
    //   [f,xi,u]=ksdensity(...)
    //
    // Parameters
    // x : a n-by-1 matrix of doubles, the data
    // xi : a npoints-by-1 matrix of doubles, the points where the density is estimated
    // u,width : a 1-by-1 matrix of doubles, positive, the width of the kernel (default=Silverman's rule).
    // support : a 1-by-1 matrix of string, the support (default="unbounded"). If support is "unbounded", all real values are possible. If support is "positive", only positive values are possible. 
    // akernel : a 1-by-1 matrix of strings, the type of kernel (default="normal"). Available values are "normal", "biweight", "triangle", "epanechnikov".
    // npoints : a 1-by-1 matrix of doubles, positive, the number of points in the density estimate (default=100)
    // f : a npoints-by-1 matrix of doubles, the density estimate
    //
    // Description
    // Compute a kernel density estimate of the data. 
    //
    // The Silverman rule for the width u of the kernel is:
    //
    // <latex>
    // u=1.06\hat{\sigma} n^{-1/5},
    // </latex>
    //
    // where <latex>\hat{\sigma}</latex> is the empirical standard 
    // deviation, and n is the size of the sample.
    //
    // On output, <literal>xi</literal> and <literal>f</literal> contain 
    // the kernel density estimate of the data. 
    // For i=1,2,...,npoints, f(i) is the estimate 
    // of the probability density at xi(i).
    //
    // Examples
    // X=distfun_normrnd(0,1,1000,1);
    // [f,xi,u]=ksdensity(X);
    // gh=scf();
    // histo(X,[],[],1);
    // plot(xi,f,"r-");
    // xtitle("Kernel density estimate","X","Density");
    // legend(["Data","PDF estimate"]);
    //
    // // Set the kernel width
    // X=distfun_normrnd(0,1,1000,1);
    // [f,xi,u]=ksdensity(X,"width",0.5);
    // scf();
    // histo(X,[],[],1);
    // plot(xi,f,"r-");
    //
    // // Set the number of points
    // X=distfun_normrnd(0,1,1000,1);
    // [f,xi,u]=ksdensity(X,"npoints",500);
    // scf();
    // histo(X,[],[],1);
    // plot(xi,f,"r-");
    //
    // // Set the kernel
    // scf();
    // X=distfun_normrnd(0,1,1000,1);
    // //
    // subplot(2,2,1);
    // histo(X,[],[],1);
    // [f,xi,u]=ksdensity(X,"kernel","normal");
    // plot(xi,f,"r-");
    // xtitle("Gaussian Density Estimate","X","Density")
    // legend(["Data","PDF estimate"]);
    // //
    // subplot(2,2,2);
    // histo(X,[],[],1);
    // [f,xi,u]=ksdensity(X,"kernel","epanechnikov");
    // plot(xi,f,"r-");
    // xtitle("Epanechnikov Density Estimate","X","Density")
    // legend(["Data","PDF estimate"]);
    // //
    // subplot(2,2,3);
    // histo(X,[],[],1);
    // [f,xi,u]=ksdensity(X,"kernel","biweight");
    // plot(xi,f,"r-");
    // xtitle("Biweight Density Estimate","X","Density")
    // legend(["Data","PDF estimate"]);
    // //
    // subplot(2,2,4);
    // histo(X,[],[],1);
    // [f,xi,u]=ksdensity(X,"kernel","triangle");
    // plot(xi,f,"r-");
    // xtitle("Triangular Density Estimate","X","Density")
    // legend(["Data","PDF estimate"]);
    //
    // // Set the kernel width
    // X=distfun_normrnd(0,1,1000,1);
    // scf();
    // //
    // [f,xi,u]=ksdensity(X,"width",0.1);
    // subplot(2,2,1)
    // histo(X,[],[],1);
    // plot(xi,f,"r-");
    // xtitle("width=0.1")
    // //
    // [f,xi,u]=ksdensity(X,"width",0.25);
    // subplot(2,2,2)
    // histo(X,[],[],1);
    // plot(xi,f,"r-");
    // xtitle("width=0.25")
    // //
    // [f,xi,u]=ksdensity(X,"width",0.5);
    // subplot(2,2,3)
    // histo(X,[],[],1);
    // plot(xi,f,"r-");
    // xtitle("width=0.5")
    // //
    // [f,xi,u]=ksdensity(X,"width",1.);
    // subplot(2,2,4)
    // histo(X,[],[],1);
    // plot(xi,f,"r-");
    // xtitle("width=1.")
    //
    // Authors
    // Copyright (C) 2013 - Michael Baudin
    // Copyright (C) 2010 - DIGITEO - Michael Baudin
    // Copyright (C) 1993 - 1995 - Anders Holtsberg

    [lhs,rhs]=argn()
    apifun_checkrhs ( "ksdensity" , rhs , 1:5 )
    apifun_checklhs ( "ksdensity" , lhs , 0:3 )
    f=[];
    xi=[];
    //
    x=varargin(1)
    xi=apifun_argindefault(varargin,2,[])
    if (typeof(xi)=="string") then
        xi=[]
    end
    //
    n = size(x,"*");
    //
    //
    // First string arg. = start of options
    startOptions=0
    for i=1:rhs
        if (typeof(varargin(i))=="string") then
            startOptions=i
            break
        end
    end
    if (startOptions==0) then
        startOptions=rhs+1
    end
    //
    // 1. Set the defaults
    default.kernel = "normal"
    default.npoints = 100
    default.support = "unbounded"
    udefault=1.0600000000000001*st_deviation(x)*(n^(-1/5));
    default.width = udefault
    //
    // 2. Manage (key,value) pairs
    options=apifun_keyvaluepairs (default,varargin(startOptions:$))
    //
    // 3. Get parameters
    akernel=options.kernel
    npoints=options.npoints
    support = options.support
    width = options.width
    //
    // Check Type
    apifun_checktype ( "ksdensity" , x , "x" , 1 , "constant" )
    if (xi<>[]) then
        apifun_checktype ( "ksdensity" , xi , "xi" , 2 , "constant" )
    end
    apifun_checktype ( "ksdensity" , akernel , "kernel" , startOptions , "string" )
    apifun_checktype ( "ksdensity" , npoints , "npoints" , startOptions , "constant" )
    apifun_checktype ( "ksdensity" , support , "support" , startOptions , "string" )
    apifun_checktype ( "ksdensity" , width , "width" , startOptions , "constant" )
    //
    // Check Size
    apifun_checkvector ( "ksdensity" , x , "x" , 1 )
    if (xi<>[]) then
        apifun_checkvector ( "ksdensity" , xi , "xi" , 2 )
    end
    apifun_checkscalar ( "ksdensity" , akernel , "kernel" , startOptions )
    apifun_checkscalar ( "ksdensity" , npoints , "npoints" , startOptions )
    apifun_checkscalar ( "ksdensity" , support , "support" , startOptions )
    apifun_checkscalar ( "ksdensity" , width , "width" , startOptions )
    //
    // Check Content
    apifun_checkoption ( "ksdensity" , akernel , "akernel" , startOptions , ..
    ["normal","biweight","triangle","epanechnikov"] )
    apifun_checkgreq ( "ksdensity" , npoints , "npoints" , startOptions,1)
    apifun_checkflint ( "ksdensity" , npoints , "npoints" , startOptions )
    tiny=number_properties("tiny")
    apifun_checkoption ( "ksdensity" , support , "support" , startOptions , ..
    ["unbounded","positive"] )
    apifun_checkgreq ( "ksdensity" , width , "width" , startOptions,tiny)
    //
    if (support=="positive"&or(x<0)) then
        error(msprintf(gettext("%s: There is a negative element in X."),"ksdensity"));
    end
    //
    u=width
    //
    mn1 = min(x);
    mx1 = max(x);
    mn = mn1-(mx1-mn1)/3;
    mx = mx1+(mx1-mn1)/3;
    if (xi==[]) then
        xi = linspace(mn,mx,npoints)';
    end
    d = xi(2)-xi(1);
    xh = zeros(xi);
    xa = (x-mn)/(mx-mn)*npoints;
    for i = 1:n
        il = floor(xa(i));
        a = xa(i)-il;
        xh(il+[1,2]) = xh(il+[1,2])+[1-a,a]';
    end

    // --- Compute -------------------------------------------------

    xk = ((-npoints:npoints-1)')*d;
    if akernel=="normal" then
        // Normal
        K = exp(-0.5*(xk/u).^2);
    elseif akernel=="epanechnikov" then
        // Epanechnikov
        K = max(0,1-(xk/u).^2/5);
    elseif akernel=="biweight" then
        // Biweight
        c = sqrt(1/7);
        K = (1-(xk/u)*c.^2).^2 .* (bool2s((1-abs(xk/u*c))>0));
    elseif akernel=="triangle" then
        // Triangular
        c = sqrt(1/6);
        K = max(0,1-abs(xk/u*c));
    end
    K = K/(sum(K,"m")*d*n);
    %v=size(xh)
    f = fft(fft(fftshift(K),-1) .* fft([xh;zeros(%v(1),%v(2))],-1),1);
    f = real(f(1:npoints));

    if (support=="positive") then
        m = sum(bool2s(xi<0));
        f(m+(1:m)) = f(m+(1:m))+f(m:-1:1);
        f(1:m) = 0
        xi(m+[0,1]) = [0,0];
    end
endfunction

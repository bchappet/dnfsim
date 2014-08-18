// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function distfun_plotintcdf(varargin)
    // Plots an integer CDF.
    //
    // Calling Sequence
    //   distfun_plotintcdf(x,p)
    //   distfun_plotintcdf(x,p,colorspec)
    //   distfun_plotintcdf(x,p,colorspec,legendspec)
    //   
    // Parameters
    //   x : a n-by-1 matrix of doubles, the outcomes.
    //   p : a n-by-nbplot matrix of doubles, the probabilities, where nbplot is the number of plots and n is the number of outcomes. The entry p(j,k) is the probability that X<x(j) for the k-th CDF plot, for j=1,2,...,n and k=1,2,...,nbplot.
    //   colorspec : a 1-by-nbplot matrix of strings, the color of each CDF plot. The k-th plot has the color colorspec(k), for k=1,2,...,nbplot.The available colors are: "r" "g" "b" "c" "m" "y" "k" "w" (default colorspec=[], i.e. the default color of the plot function).
    //   legendspec : a 1-by-nbplot matrix of strings, the legend of each CDF plot. The k-th plot has the legend legendspec(k), for k=1,2,...,nbplot.
    //
    // Description
    //   Plots a discrete (integer) Cumulative Distribution Function. 
    //   The CDF of a discrete random variable is discontinuous, 
    //   with jumps at each point. 
    //   More precisely, it is continuous from the right. 
    //   This is why a dedicated plotting function was created. 
    //
    //   For the k-th CDF and the j-th outcome, the line starts from 
    //   x(j) to x(j+1), at ordinate p(j,k), for j=1,2,...,n and 
    //   k=1,2,...,nbplot.
    //   On the left of the line, a full circle is plotted, meaning that 
    //
    //   <latex>
    //   \begin{eqnarray}
    //   P\left(X\leq x(j)\right)=p(j,k). 
    //   \end{eqnarray}
    //   </latex>
    //
    //   On the right of the line, an empty circle is plotted, meaning that 
    //
    //   <latex>
    //   \begin{eqnarray}
    //   P\left(X<x(j+1)\right)=p(j,k). 
    //   \end{eqnarray}
    //   </latex>
    //
    //
    // Examples
    // // Plot the geometric distribution
    // x=(0:11)';
    // pr=0.2;
    // p=distfun_geocdf(x,pr)
    // scf();
    // distfun_plotintcdf(x,p)
    //
    // // Plot several geometric distributions
    // x=(0:11)';
    // p1=distfun_geocdf(x,0.2);
    // p2=distfun_geocdf(x,0.5);
    // p3=distfun_geocdf(x,0.8);
    // legendspec=["pr=0.2" "pr=0.5" "pr=0.8"];
    // scf();
    // distfun_plotintcdf(x,[p1,p2,p3],["r" "b" "g"],legendspec)
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Poisson_distribution
    // 
    // Authors
    // Copyright (C) 2012 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_plotintcdf",rhs,2:4)
    apifun_checklhs("distfun_plotintcdf",lhs,0:1)

    x=varargin(1)
    p=varargin(2)
    colorspec=apifun_argindefault ( varargin , 3 , [] )
    legendspec=apifun_argindefault ( varargin , 4 , [] )
    //
    // Check type
    apifun_checktype("distfun_poisspdf",x,"x",1,"constant")
    apifun_checktype("distfun_poisspdf",p,"p",2,"constant")
    if (colorspec<>[]) then
        apifun_checktype("distfun_poisspdf",colorspec,"colorspec",3,"string")
    end
    if (legendspec<>[]) then
        apifun_checktype("distfun_poisspdf",legendspec,"legendspec",4,"string")
    end
    //
    // Check size
    nx=size(x,"*")
    apifun_checkdims("distfun_poisspdf",x,"x",1,[nx 1])
    nbplot=size(p,"c")
    apifun_checkdims("distfun_poisspdf",p,"p",2,[nx nbplot])
    if (colorspec<>[]) then
        apifun_checkvector("distfun_poisspdf",colorspec,"colorspec",3,nbplot)
    end
    if (legendspec<>[]) then
        apifun_checkvector("distfun_poisspdf",legendspec,"legendspec",4,nbplot)
    end
    //
    // Check content
    apifun_checkgreq("distfun_poisspdf",x,"x",1,0)
    apifun_checkrange("distfun_poisspdf",p,"p",2,0,1)
    if (colorspec<>[]) then
        availc=["r" "g" "b" "c" "m" "y" "k" "w"]
        for k=1:nbplot
            varname="colorspec("+string(k)+")"
            apifun_checkoption("distfun_poisspdf",colorspec(k),varname,3,availc)
        end
    end
    //
    // Loop over the curves
    // This loop is organized so that the last legend call
    // associates the right legend to the right curve. 
    // Hence, the most internal loop is done on the curves, 
    // so that the legend function associate each string in 
    // legendspec to the plots 1, 2,..., nbplot, 
    // wisely ignoring the remaining plots.
    drawlater()
    //
    for k=1:nbplot
        linespec1=colorspec(k)+"-"
        plot([x(:);x($)+1],[p(:,k);p($,k)],linespec1);
        g=gce()
        g.children(1).polyline_style=2
    end
    for k=1:nbplot
        linespec2=colorspec(k)+"."
        plot(x(:),p(:,k),linespec2);
    end
    for k=1:nbplot
        linespec3=colorspec(k)+"o"
        plot(x(:)+1,p(:,k),linespec3);
    end
    xtitle("CDF","x","$P(X\leq x)$");
    if (legendspec<>[]) then
        // We put the legend in upper left, because 
        // the CDF always has the S shape. 
        // Therefore, the default legend position, i.e. 
        // upper right, hides a part of the plot.
        legend(legendspec,"in_upper_left")
    end
    drawnow()
endfunction

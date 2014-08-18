// Copyright (C) 2013 - 2014 - Michael Baudin
// Copyright (C) 2012 - Scilab Enterprises - Adeline CARNIS
// Copyright (C) 2010 - Samuel Gougeon
// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
// Copyright (C) 1998 - 2000 - Mathematique Universite de Paris-Sud - Jean Coursol
// Copyright (C) Bruno Pincon
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [h,edge]=histo(varargin)
    // Plot a  histogram
    //  
    // Calling Sequence
    //   histo(x)
    //   histo(x)
    //   histo(x,n)
    //   histo(x,n)
    //   histo(x,n,scale)
    //   histo(x,n,scale,sym)
    //   h=histo(...)
    //   [h,edge]=histo(...)
    //             
    // Parameters
    // x : a 1-by-p or p-by-1 matrix of doubles, the data
    // n : a 1-by-1 matrix of doubles, the approximate number of bins (default n = ceil(log2(p)+1), where p is the size of x. This is Sturges' rule.). If n is not a scalar, it contains the edges of the classes, in increasing order.
    // scale : a boolean, set to %t to have the area 1 under the histogram instead of area n.  (default scale = %f means no scaling)
    // sym : a 1-by-1 matrix of doubles or string, the integer (positive or negative) of the color of the plot (default sym=[]). Negative is for a mark, positive is for a color.
    // egde : a 1-by-(n+1) matrix of doubles, the edges of the classes of the histogram
    // h : a 1-by-n matrix of doubles, h(i) is the number of values in x that belong to [edge(i), edge(i+1)[ 
    //
    // Description
    // Compute and plots a histogram of the x.
    //
    // Any input argument equal to the empty matrix is replaced by its default value.
    //
    // The advantage of the stixbox/histo function over the 
    // Scilab/histplot function is that the number of classes n can be automatically 
    // computed in histo, while it is the first argument of histplot.
    //
    // Examples
    // x=distfun_chi2rnd(3,1000,1);
    // scf();
    // histo(x);
    // xtitle("Chi-Square random numbers","X","Frequency")
    //
    // // Set the number of classes
    // scf();
    // histo(x,10);
    //
    // // Set the edges
    // scf();
    // X=distfun_unifrnd(0,1,1000,1);
    // edges = 0:0.2:1.; 
    // histo(X,edges);
    //
    // // See without scaling
    // scf(); 
    // histo(x,[],%f);
    // // See with scaling
    // scf(); 
    // histo(x,[],%t);
    //
    // // See various colors and styles
    // scf();
    // histo(x,[],[],1);
    // scf();
    // histo(x,[],[],2);
    // scf();
    // histo(x,[],[],3);
    //
    // Authors
// Copyright (C) 2013 - 2014 - Michael Baudin
// Copyright (C) 2012 - Scilab Enterprises - Adeline CARNIS
// Copyright (C) 2010 - Samuel Gougeon
// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
// Copyright (C) 1998 - 2000 - Mathematique Universite de Paris-Sud - Jean Coursol
// Copyright (C) Bruno Pincon

    [lhs,rhs]=argn()
    apifun_checkrhs ( "histo" , rhs , 1:4 )
    apifun_checklhs ( "histo" , lhs , 0:2 )
    //
    x=varargin ( 1 )
    p=size(x,'*')
    ndefault=ceil(log2(p)+1)
    n=apifun_argindefault(varargin,2,ndefault)
    scale=apifun_argindefault(varargin,3,%f)
    sym=apifun_argindefault(varargin,4,[])
    //
    // Check Type
    apifun_checktype ( "histo" , x , "x" , 1 , "constant" )
    apifun_checktype ( "histo" , n , "n" , 2 , "constant" )
    apifun_checktype ( "histo" , scale , "scale" , 3 , "boolean" )
    apifun_checktype ( "histo" , sym , "sym" , 4 , ["constant","string"] )
    //
    // Check Size
    apifun_checkvector ( "histo" , x , "x" , 1 )
    apifun_checkvector ( "histo" , n , "n" , 2 )
    apifun_checkscalar ( "histo" , scale , "scale" , 3 )
    if (sym<>[]) then
        apifun_checkscalar ( "histo" , sym , "sym" , 4 )
    end

    // Compute the limits
    if (size(n,"*")>1) then
        // n represents the edges.
        edge = matrix(n,1,-1)   // force row form
        if min(diff(edge)) <= 0 then
            error(msprintf(gettext("%s: Wrong values for input argument #%d: Elements must be in increasing order.\n"),"histo",2))
        end
        n = length(edge)-1
    else
        // n is the number of classes.
        // Check Content
        apifun_checkgreq ( "histo" , n , "n" , 2 , 1 )
        apifun_checkflint ( "histo" , n , "n" , 2 )

        minx = min(x);
        maxx = max(x);
        if (minx == maxx) then
            minx = minx - floor(n/2); 
            maxx = maxx + ceil(n/2);
        end
        edge = linspace(minx, maxx, n+1);
    end

    //
    // Compute the histogram
    [ind , h] = dsearch(x, edge)
    //
    // Normalize
    if scale then 
        h=h ./ (p *(edge(2:$)-edge(1:$-1)))
    end
    //
    // Create the polyline
    //    X = [x1 x1 x2 x2 x2 x3 x3 x3  x4 ...   xn xn+1 xn+1]'
    //    Y = [0  y1 y1 0  y2 y2 0  y3  y3 ... 0 yn yn   0 ]'
    X = [edge(1);edge(1);matrix([1;1;1]*edge(2:n),-1,1);edge(n+1);edge(n+1)]
    // BUG#1885
    // We start the histplot line to %eps rather than 0
    // So when switching to logarithmic mode we do not fall
    // in log(0) special behaviour.
    Y = [matrix([%eps;1;1]*h,-1,1);%eps]
    //
    // Plot
    if (sym==[]) then
        plot2d(X,Y)
    else
        plot2d(X,Y,style=sym)
    end
endfunction

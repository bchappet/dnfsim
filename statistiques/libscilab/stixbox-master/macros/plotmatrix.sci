// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
function plotmatrix(varargin)
    // Plot an X vx Y scatter plot matrix
    //
    // Calling Sequence
    //   plotmatrix(x)
    //   plotmatrix(x,y)
    //   plotmatrix(x,y,key1,value1,key2,value2,...)
    //   plotmatrix(x,y,"xlabels",xlabels)
    //   plotmatrix(x,y,"ylabels",ylabels)
    //   plotmatrix(x,y,"ptsize",ptsize)
    //   plotmatrix(x,y,"valuelabels",valuelabels)
    //   plotmatrix(x,y,"symbol",symbol)
    //   plotmatrix(x,"histogram",histogram)
    //   plotmatrix(x,"nbclasses",nbclasses)
    //   plotmatrix(x,"histoYlabel",histoYlabel)
    //
    // Parameters
    //   x : a n-by-ninput matrix of doubles, the x datas
    //   y : a n-by-noutput matrix of doubles, the y datas
    //   xlabels : a ninput-by-1 matrix of strings, the x labels (default="")
    //   ylabels : a noutput-by-1 matrix of strings, the y labels (default="")
    //   ptsize : a 1-by-1 matrix of doubles, integer value, positive, the number pixels for the dots (default ptsize=2)
    //   valuelabels : a 1-by-1 matrix of booleans, set to true to print the x and y value labels (default=%t)
    //   symbol : a 1-by-1 matrix of strings, the point symbols (default="b.")
    //   histogram : a 1-by-1 matrix of booleans, set to true to print the histogram (default=%t)
    //   nbclasses : a 1-by-1 matrix of doubles, integer value, positive, the number of classes in the histogram (default nbclasses=[])
    //   histoYlabel : a 1-by-1 matrix of strings, the Y-label in the histogram (default histoYlabel="Frequency")
    //
    // Description
    // Plots a matrix of scatter plots representing the 
    // dependencies of Y vs X: plots the columns of Y versus the columns of X. 
    // This creates a rectangular matrix of plots with ninput columns and 
    // noutput rows.
    //
    // If only X is specified, plots the columns of X versus the columns of X, 
    // and replaces the diagonal with histograms. 
    // This creates a square matrix of plots with ninput columns and ninput rows.
    // The plotmatrix function is partly compatible with Matlab.
    //
    // Examples
    // // Example 1
    // // Plot Y versus X
    // m=1000;
    // x1=distfun_unifrnd(0,1,m,1);
    // x2=distfun_unifrnd(0,1,m,1);
    // x3=distfun_unifrnd(0,1,m,1);
    // y1=2*x1.*x2+x3;
    // y2=-3*x1+x2.^2-2*x3;
    // y3=sin(x1)-3*x2+3*x3;
    // x=[x1,x2,x3];
    // y=[y1,y2,y3];
    // //
    // xlabels=["X1","X2","X3"];
    // ylabels=["Y1","Y2","Y3"];
    // // No labels
    // scf();
    // plotmatrix(x,y);
    // // With labels (Figure 1)
    // scf();
    // plotmatrix(x,y,"xlabels",xlabels,"ylabels",ylabels);
    // // Without XY value labels
    // scf();
    // plotmatrix(x,y,"valuelabels",%f);
    // // Without XY value labels, and XY labels
    // scf();
    // plotmatrix(x,y,"valuelabels",%f,..
    // "xlabels",xlabels,"ylabels",ylabels);
    // // Set the point size
    // scf();
    // plotmatrix(x,y,"ptsize",1);
    // // With red crosses
    // scf();
    // plotmatrix(x,y,"symbol","rx");
    // // 
    // // Example 2
    // // Plot Y versus X
    // m=1000;
    // x1=distfun_normrnd(0,1,m,1);
    // x2=distfun_unifrnd(-1,1,m,1);
    // y1=x1.^2+x2;
    // y2=-3*x1+x2.^2;
    // y3=x1-3*exp(x2);
    // x=[x1,x2];
    // y=[y1,y2,y3];
    // //
    // xlabels=["X1","X2"];
    // ylabels=["Y1","Y2","Y3"];
    // // No labels
    // scf();
    // plotmatrix(x,y);
    // // With labels, and red circles
    // scf();
    // plotmatrix(x,y,"xlabels",xlabels,"ylabels",ylabels,..
    // "symbol","ro");
    // // 
    // // Example 3
    // // Plot X versus X
    // m=1000;
    // x1=distfun_unifrnd(0,1,m,1);
    // x2=distfun_unifrnd(0,1,m,1);
    // x3=distfun_unifrnd(0,1,m,1);
    // y1=2*x1.*x2+x3;
    // y2=-3*x1+x2.^2-2*x3;
    // y3=sin(x1)-3*x2+3*x3;
    // y=[y1,y2,y3];
    // //
    // ylabels=["Y1","Y2","Y3"];
    // // No labels
    // scf();
    // plotmatrix(y);
    // // With labels (Figure 2)
    // scf();
    // plotmatrix(y,"xlabels",ylabels);
    // // With labels, without value labels
    // scf();
    // plotmatrix(y,"xlabels",ylabels,"valuelabels",%f);
    // // With labels, without value labels, with red circles
    // scf();
    // plotmatrix(y,"xlabels",ylabels,"valuelabels",%f,..
    // "symbol","ro");
    // // With labels, without value labels, with red dots, 
    // // with symbols of size 1
    // scf();
    // plotmatrix(y,"xlabels",ylabels,"valuelabels",%f,..
    // "symbol","r.","ptsize",1);
    // // With the histogram
    // scf();
    // plotmatrix(y,"histogram",%t);
    // // With the histogram, and the labels
    // scf();
    // plotmatrix(y,"histogram",%t,"xlabels",ylabels);
    // 
    // Authors
    // Copyright (C) 2013 - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "plotmatrix" , rhs , 1:12 )
    apifun_checklhs ( "plotmatrix" , lhs , 0:1 )
    //
    x = varargin ( 1 )
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
    // Decide if we are in X/Y mode or in X/X mode
    if (startOptions==2) then
        plotmode="XX"
    else
        plotmode="XY"
    end
    //
    if (plotmode=="XX") then
        y=x
    else
        y=apifun_argindefault(varargin,2,x)
    end
    //
    // 1. Set the defaults
    default.xlabels = []
    default.ylabels = []
    default.ptsize = 2
    default.valuelabels = %t
    default.symbol = "b."
    default.histogram = %t
    default.nbclasses = []
    default.histoYlabel = "Frequency"
    //
    // 2. Manage (key,value) pairs
    options=apifun_keyvaluepairs (default,varargin(startOptions:$))
    //
    // 3. Get parameters
    xlabels=options.xlabels
    ylabels=options.ylabels
    ptsize = options.ptsize
    valuelabels = options.valuelabels
    symbol = options.symbol
    histogram = options.histogram
    nbclasses = options.nbclasses
    histoYlabel = options.histoYlabel
    //
    // Check Type
    apifun_checktype ( "plotmatrix" , x , "x" , 1 , "constant" )
    apifun_checktype ( "plotmatrix" , y , "y" , 2 , "constant" )
    if (xlabels<>[]) then
        apifun_checktype ( "plotmatrix" , xlabels , "xlabels" , startOptions , "string" )
    end
    if (ylabels<>[]) then
        apifun_checktype ( "plotmatrix" , ylabels , "ylabels" , startOptions , "string" )
    end
    apifun_checktype ( "plotmatrix" , ptsize , "ptsize" , startOptions , "constant" )
    apifun_checktype ( "plotmatrix" , valuelabels , "valuelabels" , startOptions , "boolean" )
    apifun_checktype ( "plotmatrix" , symbol , "symbol" , startOptions , "string" )
    apifun_checktype ( "plotmatrix" , histogram , "histogram" , 2 , "boolean" )
    if (nbclasses<>[]) then
        apifun_checktype ( "plotmatrix" , nbclasses , "nbclasses" , 2 , "constant" )
    end
    apifun_checktype ( "plotmatrix" , histoYlabel , "histoYlabel" , 2 , "string" )
    //
    // Check Size
    n=size(x,"r");
    ninput=size(x,"c")
    noutput=size(y,"c")
    //
    apifun_checkdims ( "plotmatrix" , x , "x" , 1 , [n ninput] )
    apifun_checkdims ( "plotmatrix" , y , "y" , 2 , [n noutput] )
    if (xlabels<>[]) then
        apifun_checkvector ( "plotmatrix" , xlabels , "xlabels" , startOptions , ninput )
    end
    if (ylabels<>[]) then
        apifun_checkvector ( "plotmatrix" , ylabels , "ylabels" , startOptions , noutput )
    end
    apifun_checkscalar ( "plotmatrix" , ptsize , "ptsize" , startOptions )
    apifun_checkscalar ( "plotmatrix" , valuelabels , "valuelabels" , startOptions )
    apifun_checkscalar ( "plotmatrix" , symbol , "symbol" , startOptions )
    apifun_checkscalar ( "plotmatrix" , histogram , "histogram" , 2 )
    if (nbclasses<>[]) then
        apifun_checkscalar ( "plotmatrix" , nbclasses , "nbclasses" , 2 )
    end
    apifun_checkscalar ( "plotmatrix" , histoYlabel , "histoYlabel" , 2 )
    //
    // Check Content
    apifun_checkgreq ( "plotmatrix" , ptsize , "ptsize" , startOptions , 1 )
    apifun_checkflint ( "plotmatrix" , ptsize , "ptsize" , startOptions )
    if (nbclasses<>[]) then
        apifun_checkgreq ( "plotmatrix" , nbclasses , "nbclasses" , 2 , 1 )
        apifun_checkflint ( "plotmatrix" , nbclasses , "nbclasses" , 2 )
    end
    //
    if (plotmode=="XY") then
        iserr=%f
        if (xlabels<>[]&ylabels==[]) then
            iserr=%t
        end
        if (xlabels==[]&ylabels<>[]) then
            iserr=%t
        end
        if (iserr) then
            errmsg=gettext("%s: xlabels and ylabels are both expected.")
            error(msprintf(errmsg,"plotmatrix"))
        end
    end
    //
    for j=1:noutput
        for i=1:ninput
            p=(j-1)*ninput + i;
            subplot(ninput,noutput,p);
            if (i==j&plotmode=="XX") then
                if (histogram) then
                    if (nbclasses==[]) then
                        histo(x(:,i))
                    else
                        histo(x(:,i),nbclasses)
                    end
                    if (xlabels<>[]) then
                        xtitle("",xlabels(i),histoYlabel)
                    else
                        xtitle("","",histoYlabel)
                    end
                else
                    if (xlabels<>[]) then
                        xstring(0.5,0.5,xlabels(i))
                    end
                end
            else
                plot(x(:,i),y(:,j),symbol);
                if (xlabels<>[]&ylabels<>[]) then
                    xtitle("",xlabels(i),ylabels(j))
                else
                    xtitle("","","")
                end
                e=gce();
                if (~valuelabels) then
                    e.auto_ticks(1:3)="off";
                end
                e.children.children.mark_size=ptsize;
            end
        end
    end
endfunction

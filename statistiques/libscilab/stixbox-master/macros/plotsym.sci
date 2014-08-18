// Copyright (C) 2013 - Michael Baudin
// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function plotsym(varargin)
    // Plot with symbols
    //  
    // Calling Sequence
    // plotsym(x,y)
    // plotsym(x,y,s)
    // plotsym(x,y,s,symtable,clrtable)
    // plotsym(x,y,s,symtable,clrtable,symsize)
    //
    // Parameters
    // x : a n-by-1 matrix of doubles, the x-coordinate of the data
    // y : a n-by-1 matrix of doubles, the y-coordinate of the data
    // s : a n-by-1 matrix of doubles, integer value, >=1, the symbol of the data
    // symtable : a n-by-1 matrix of string, the symbol table (default="stcidlr")
    // clrtable : a n-by-1 matrix of string, the color table (default="rbgcmywk"). If a single color is given (e.g.clrtable="r"), then the same color is used for all symbols.
    // symsize :  a 1-by-1 or n-by-1 matrix of doubles, integer value, the symbol sizes (default=ones(n,1)). If a scalar is given, the same size is used for all symbols.
    //
    // Description
    // Create a scatter plot with various symbols, colors and sizes.
    //
    //  The argument s is vector that contains category marker that
    //  is plotted with symbol from text string symtable, i.e element i is
    //  plotted with symbol symtable(s(i)).
    //
    // <literal>symtable</literal> is a string with content:
    // <screen> 
    //  "s"  square (1)
    //  "t"  triangle (2)
    //  "c"  circle (3)
    //  "d"  diamond (4)
    //  "i"  inverted triangle (5)
    //  "l"  left triangle (6)
    //  "r"  right triangle (7)
    // </screen> 
    //
    // <literal>clrtable</literal> is a matrix of strings with content:
    // <screen> 
    //  "r"  red (1)
    //  "b"  blue (2)
    //  "g"  green (3)
    //  "c"  cyan (4)
    //  "m"  magenta (5)
    //  "y"  yellow (6)
    //  "w"  white (7)
    //  "k"  black (8)
    // </screen> 
    // 
    // The input <literal>symsize</literal> defines the symbol size relative to the 
    // default size which is 1.
    // It may be a scalar, which is then applied to all symbols, or a
    // vector, one for each symbol. 
    //
    // There must be at least as many symbols in <literal>symboltable</literal>
    // as there are different symbols in <literal>s</literal>.
    // If there are not enough symbols, the error
    //
    // <screen>
    // Not enough symbols in symboltable.
    // </screen>
    //
    // is produced.
    //
    // Any argument equal to the empty matrix [] is replaced 
    // by its default value.
	//
	// Caution : the symsize argument can be used to convey information, 
	// but this might be leading to wrong results. 
	// If this parameter is used as the radius of a circle, the area 
	// depends on the square of the radius. 
	// Hence, the area is a nonlinear function of the radius, 
	// which might lead to false conclusions: larger values of the radius 
	// leads to much larger values of the area. 
	// To do this, please use the bubblechart function instead.
    //
    // Examples
    // m = 12;
    // x = [
    // distfun_normrnd(0,1,m,1)
    // distfun_normrnd(2,1,m,1)
    // distfun_normrnd(4,1,m,1)
    // ];
    // y = [
    // distfun_normrnd(0,1,m,1)
    // distfun_normrnd(4,1,m,1)
    // distfun_normrnd(3,1,m,1)
    // ];
    // S = [
    // ones(m,1)
    // 2*ones(m,1)
    // 3*ones(m,1)
    // ];
    // // Only red squares
    // scf();
    // plotsym(x,y);
    // xtitle("","X","Y");
    // // With 3 colors, and 3 shapes
    // scf();
    // plotsym(x,y,S);
    // xtitle("","X","Y");
    // // With squares ("s"), triangles ("s"), 
    // // circles ("c"), in blue ("b")
    // scf();
    // plotsym(x,y,S,"stc","b")
    // xtitle("","X","Y");
    // // Change the colormap
    // h=scf();
    // plotsym(x,y,S);
    // h.color_map=autumncolormap(3);
    // // Set the symbol size
    // scf();
    // plotsym(x,y,S,[],[],0.5);
    //
    // // Salary Survey
    // // Source: Chatterjee, S. and Hadi, A. S. (1988), p. 88 
    // [x,txt] = getdata(3);
    // scf();
    // // Sex (1 = male, 0 = female)
    // // Add +1, to get : 2=male, 1=female, 
    // // which maps to symbols.
    // gender=x(:,2)+1;
    // // Scale performance (from 1 to 5)
    // // into a size from 0.2 to 2.
    // perf=2*x(:,5)/5;
    // plotsym(x(:,6),x(:,3),gender,[],[],perf);
    // xtitle("Females:red square, Males:blue triangle, Size is performance",..
    // "Monthly salary ($)","Number of years with the company");
    //
    // Authors
    // Copyright (C) 2013 - Michael Baudin
    // Copyright (C) 2010 - DIGITEO - Michael Baudin
    // Copyright (C) 1993 - 1995 - Anders Holtsberg

    [lhs,rhs]=argn()
    apifun_checkrhs ( "plotsym" , rhs , 2:7 )
    apifun_checklhs ( "plotsym" , lhs , 0:1 )
    //
    // Manage the arguments
    // Set symboltable, colortable, symbol, clr, symsize
    x=varargin(1)
    y=varargin(2)
    n =size(x,"*");
    symbol=apifun_argindefault(varargin,3,ones(n,1))
    symboltable=apifun_argindefault(varargin,4,"stcidlr")
    colortable=apifun_argindefault(varargin,5,"rbgcmywk")
    symsize=apifun_argindefault(varargin,6,ones(n,1))
    //
    // Check Type
    apifun_checktype ( "plotsym" , x , "x" , 1 , "constant" )
    apifun_checktype ( "plotsym" , y , "y" , 2 , "constant" )
    apifun_checktype ( "plotsym" , symbol , "symbol" , 3 , "constant" )
    apifun_checktype ( "plotsym" , symboltable , "symboltable" , 4 , "string" )
    apifun_checktype ( "plotsym" , colortable , "colortable" , 5 , "string" )
    apifun_checktype ( "plotsym" , symsize , "symsize" , 6 , "constant" )
    //
    // Check Size
    apifun_checkdims( "plotsym" , x , "x" , 1 , [n 1])
    apifun_checkdims( "plotsym" , y , "y" , 2 , [n 1])
    apifun_checkdims ( "plotsym" , symbol , "symbol" , 3, [n 1] )
    nunique=size(unique(symbol),"*")
    if (length(symboltable)<nunique) then
        error(msprintf(gettext("%s: Not enough symbols in symboltable."),"plotsym"))
    end
    if (size(symsize,"*")<>1) then
    apifun_checkdims ( "plotsym" , symsize , "symsize" , 6, [n 1] )
    end
    //
    // Check content
    apifun_checkrange ( "plotsym" , symbol , "symbol" , 3 , 1,n)
    apifun_checkflint ( "plotsym" , symbol , "symbol" , 3 )
    tiny=number_properties("tiny")
    apifun_checkgreq ( "plotsym" , symsize , "symsize" , 6 , tiny)
    //
    // If clr is undefined
    if length(colortable)<max(symbol) then 
        colortable=colortable+part(colortable,ones(1,max(symbol)- ...
        length(colortable)))
    end
    clr = part(colortable,symbol);
    c=clr;
    clr=[]
    for i=1:length(c)
        select part(c,i)
        case "r" then
            clr=[clr;color("red")]
        case "b" then
            clr=[clr;color("blue")]
        case "g" then
            clr=[clr;color("green")]
        case "c" then
            clr=[clr;color("cyan")]
        case "b" then
            clr=[clr;color("blue")]
        case "m" then
            clr=[clr;color("maroon")]
        case "y" then
            clr=[clr;color("yellow")]
        case "w" then
            clr=[clr;color("white")]
        case "k" then
            clr=[clr;color("black")]
        else
            error(msprintf(gettext("%s: Unknown color: ""%s"""),"plotsym",part(c,i)))
        end
    end
    //
    symbol = part(symboltable,symbol);
    if size(symsize,"*")==1 then
        symsize = symsize(ones(n,1));
    end
    drawlater()
    //
    xx0=floor(min(x));
    xx1=ceil(max(x));
    yy0=floor(min(y));
    yy1=ceil(max(y));  
    ax=gca();
    ax.axes_visible="on";
    ax.data_bounds=[xx0,yy0;xx1,yy1];
    sx = max(x)-min(x);
    sy = max(y)-min(y);
    //
    P=[0.12 0.12 0.76 0.76];
    P = P(3)/P(4);
    dx = sx/25/P/1.3;
    dy = sy/25;
    //
    // Configure all shapes
    // Square
    Ss = [-1,1,1,-1;-1,-1,1,1]/4;
    // Diamond
    Ds = [-1,0,1,0;0,-1,0,1]/2/sqrt(2);
    // Triangle
    Ts = [-sqrt(3)/2,0,sqrt(3)/2;0.5,-1,0.5]/sqrt(3)/1.5;
    // Inverse triangle
    Is = [-sqrt(3)/2,0,sqrt(3)/2;-0.5,1,-0.5]/sqrt(3)/1.5;
    // Left triangle
    Ls = [0.5,-1,0.5;-sqrt(3)/2,0,sqrt(3)/2]/sqrt(3)/1.5;
    // Right triangle
    Rs = [-0.5,1,-0.5;-sqrt(3)/2,0,sqrt(3)/2]/sqrt(3)/1.5;
    // Circle
    Cs = [sin(%pi*(0:31)/16);cos(%pi*(0:31)/16)]/%pi;
    //
    // Use x, y, symbol, symsize, clr to plot the data
    nbs=length(symbol)
    symbolmatrix=strsplit(symbol,1:nbs-1)
    for i = 1:n
        si = symbolmatrix(i);
        //
        if si=="s" then
            sym = Ss;
        elseif si=="d" then
            sym = Ds;
        elseif si=="t" then
            sym = Ts;
        elseif si=="i" then
            sym = Is;
        elseif si=="l" then
            sym = Ls;
        elseif si=="r" then
            sym = Rs;
        elseif si=="c" then
            sym = Cs;
        else
            error(msprintf(gettext("%s: Unknown symbol: ""%s"""),"plotsym",si))
        end
        XX=x(i)+dx*symsize(i)*sym(1,:);
        YY=y(i)+dy*symsize(i)*sym(2,:);

        if prod(size(clr)) <> 1 then
            xfpolys(XX',YY',clr(i));
        else
            xfpolys(XX',YY',clr);
        end
    end
    drawnow()
endfunction


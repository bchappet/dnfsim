// Copyright (C) 2013 - Michael Baudin
// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function v=identify(varargin)
    // Identify points on a plot with mouse clicks
    //  
    // Calling Sequence
    //   v=identify(x,y)
    //   v=identify(x,y,plotsymbol)
    //   v=identify(x,y,plotsymbol,textvector)
    //             
    // Parameters
    // x : a n-by-1 matrix of doubles
    // y : a n-by-1 matrix of doubles
    // plotsymbol : a 1-by-1 matrix of strings, the plot symbol (default = "*")
    // textvector : a n-by-1 matrix of strings, or a n-by-1 matrix of doubles, the labels (default = 1:n)
    // v : a n-by-1 matrix of doubles, the indices of selected points
    //
    // Description
    // This routine plots x versus y and waits for mouse clicks
    // to identify points. 
    // This allows to interactively identify the points in a plot.
    //
    // Click with left button on points and end with middle button. 
    //
    // The <literal>plotsymbol</literal> argument is used to specify 
    // the way the points are plotted, as given by the 
    // <literal>LineSpec</literal> argument of <literal>plot</literal>. 
    // In other words, the statement used to plot the points is 
    //
    // <screen>
    // plot(x,y,plotsymbol)
    // </screen>
    //
    // If no point is selected, the function returns [].
    //
    // Examples
    // x = distfun_normrnd(0,1,30,1);
    // y = 2+3*x+distfun_normrnd(0,1,30,1);
    // scf();
    // v=identify(x,y)
    //
    // // Set the plot symbol
    // scf();
    // v=identify(x,y,"bo")
    //
    // // Use a text vector
    // x = distfun_normrnd(0,1,10,1);
    // y = 2+3*x+distfun_normrnd(0,1,10,1);
    // textvector=strsplit("abcdefghij")
    // scf();
    // identify(x,y,"bo",textvector)
    //
    // // With a matrix of strings
    // x = distfun_normrnd(0,1,4,1);
    // y = 2+3*x+distfun_normrnd(0,1,4,1);
    // textvector=[
    // "john"
    // "paul"
    // "george"
    // "ringo"
    // ];
    // scf();
    // identify(x,y,"bo",textvector)
    //
    // // With textvector a vector of doubles
    // x = distfun_normrnd(0,1,4,1);
    // y = 2+3*x+distfun_normrnd(0,1,4,1);
    // textvector=10:14;
    // scf();
    // identify(x,y,"bo",textvector)
    //
    // Authors
    // Copyright (C) 2013 - Michael Baudin
    // Copyright (C) 2010 - DIGITEO - Michael Baudin
    // Copyright (C) 1993 - 1995 - Anders Holtsberg

    [lhs,rhs] = argn();
    apifun_checkrhs("identify",rhs,2:4);
    apifun_checklhs("identify",lhs,0:1);
    //
    x=varargin(1)
    y=varargin(2)
    n=size(x,"*")
    plotsymbol = apifun_argindefault ( varargin , 3 , "*" )
    textvector = apifun_argindefault ( varargin , 4 , 1:n )
    //
    // Check type
    apifun_checktype("identify",x,"x",1,"constant");
    apifun_checktype("identify",y,"y",2,"constant");
    apifun_checktype("identify",plotsymbol,"plotsymbol",3,"string");
    apifun_checktype("identify",textvector,"textvector",4,["constant" "string"]);
    //
    // Check size
    apifun_checkvector("identify",x,"x",1,n);
    apifun_checkvector("identify",y,"y",2,n);
    //
    // Check content
    // Nothing to do
    //
    // Proceed...
    v=[];
    x=x(:)
    y=y(:)
    plot(x,y,plotsymbol)
    //
    cx = cov(x);
    cy = cov(y);
    v = [];
    B = 1;
    while B==1|B==4 then
        xinfo(msprintf(gettext("%s : Click left to get a point, click middle to leave."),"identify"))
        [B,xc,yc]=xclick();
        B=B+1;
        if B==1|B==4 then
            d = (x-xc).^2/cx+(y-yc).^2/cy;
            if min(size(d))==1 then 
                [d,i]=gsort(d)
            else 
                [d,i]=gsort(d,'r')
            end
            i=i($)
            v = [v;i];
            if type(textvector)==10 then
                xstring(xc,yc,textvector(i,:));
            else
                xstring(xc,yc,sprintf(" %g",textvector(i)));
            end
        end
    end
    xinfo("")
endfunction

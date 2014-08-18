// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function bubblechart(varargin)
    // Plot a bubble chart
    //
    // Calling Sequence
    //   bubblechart(data)
    //   bubblechart(data,legen)
    //   bubblechart(data,legen,fill)
    //   bubblechart(data,legen,fill,maxR)
    //   bubblechart(data,legen,fill,maxR,scale)
    //
    // Parameters
    //   data : a n-by-3 matrix of doubles, the X, Y and Z data
    //   legen : a n-by-1 matrix of strings, the labels of each data (default legend=[])
    //   fill : a 1-by-1 or n-by-1 matrix of doubles, integer value, the color (default fill=4)
    //   maxR : a 1-by-1 matrix of doubles, positive, the maximum radius (default maxR=1)
    //   scale : a boolean, set to %f to disable scaling (default scale=%t)
    //
    // Description
    // Plots a bubble chart for the data. 
    // For each row i in data, a colored bubble with center 
    // x=data(i,1), y=x=data(i,2) is plotted. 
    // The radius of the i-th bubble is computed depending 
    // on z=data(i,3), so that the area of the bubble is 
    // proportionnal to z.
    //
    // The radius are scaled, so that the bubbles have a radius proportionnal 
    // to maxR. 
    // Reduce this parameter if the bubbles are too large,  
    // hiding other bubbles. 
    //
    // If scale is true, then scales the circles into ellipses, 
    // so that the X/Y scaling makes the ellipse look like 
    // a circle.
    // Otherwise, really create circles. 
    // In this case, we recommend to set the isoview option to "on".
    //
    // Any argument equal to the empty matrix [] is 
    // replaced by its default value.
    //
    // Comments on this type of graphics
    //
    // The goal of this function is to be able to plot a 3D data 
    // into a 2D plot. 
    // This avoids the need for 3D histograms, which can lead 
    // to false conclusion because of the perspective error :
    // a bar which is in the front of the picture may 
    // look larger than a bar which is in the back of the picture, 
    // even if its absolute value is smaller. 
    // In a 2D bubble chart, a bubble with a larger 
    // area has a larger Z-value, without a possibility 
    // of confusion.
    //
    // If the color is used, a 4D data can be presented 
    // in a 2D plot. 
    // But be warned that some users do not perceive the color :
    // this is "color blindness" (8% of males, 0.5% of females, 
    // according to Wikipedia [2]).
    //
    // One problem solved by the bubblechart function is that 
    // the area of the bubble is proportionnal to the Z value. 
    // This provides a linear relationship between the information 
    // and the data. 
    // If, instead, the radius was used, then the relationship 
    // between the area and Z would be nonlinear, which can potentially 
    // lead to wrong conclusion. 
    //
    // A potential problem with bubble charts is that they 
    // consume a significant amount of ink when we print them. 
    // This is why the default value of the fill argument was 
    // chosen to be as nice as possible, with a reasonable 
    // level of ink.
    //
    // Examples
    // // Source: [7]
    // // "Life Expectancy", "Fertility Rate", "Population"
    // data=[ 
    // 80.66, 1.67, 33739900
    // 79.84, 1.36, 81902307
    // 78.6,  1.84, 5523095
    // 72.73, 2.78, 79716203
    // 80.05, 2,    61801570
    // 72.49, 1.7,  73137148
    // 68.09, 4.77, 31090763
    // 81.55, 2.96, 7485600
    // 68.6,  1.54, 141850000
    // 78.09, 2.05, 307007000
    // ];
    // legen=[
    // 'CAN'
    // 'DEU'
    // 'DNK'
    // 'EGY'
    // 'GBR'
    // 'IRN'
    // 'IRQ'
    // 'ISR'
    // 'RUS'
    // 'USA'
    // ];
    // //
    // // Just the data
    // h=scf();
    // xtitle("","Life Expectancy", "Fertility Rate")
    // bubblechart(data)
    // //
    // // Disable scaling, set isoview on.
    // h=scf();
    // xtitle("","Life Expectancy", "Fertility Rate")
    // bubblechart(data,[],[],[],%f)
    // h.children.isoview="on";
    // //
    // // One single color, with datatips
    // h=scf();
    // xtitle("","Life Expectancy", "Fertility Rate")
    // bubblechart(data,legen,2)
    // //
    // // Different colors, with datatips
    // h=scf();
    // h.color_map = rainbowcolormap(10);
    // xtitle("","Life Expectancy", "Fertility Rate")
    // bubblechart(data,legen,1:10)
    // //
    // // With a separate legend
    // h=scf();
    // h.color_map = rainbowcolormap(10);
    // xtitle("","Life Expectancy", "Fertility Rate")
    // bubblechart(data,[],1:10)
    // legend(legen,"in_upper_right");
    //
    // // Change the color depending on the world zone
    // h=scf();
    // xtitle("","Life Expectancy", "Fertility Rate")
    // // 1 = CAN, USA (North America)
    // // 2 = Europe (DEU, DNK, GBR, RUS)
    // // 3 = Arab/Persian/Hebrew (EGY,IRN,IRQ,ISR)
    // h.color_map = rainbowcolormap(3);
    // fill=[
    // 1
    // 2
    // 2
    // 3
    // 2
    // 3
    // 3
    // 3
    // 2
    // 1
    // ];
    // bubblechart(data,legen,fill)
    // //
    // // Without contours on the bubbles
    // h=scf();
    // xtitle("","Life Expectancy", "Fertility Rate")
    // h.color_map = rainbowcolormap(3);
    // bubblechart(data,legen,-fill)
    //
    // // Reference : [3]
    // data =[
    // 14 12200 15
    // 20 60000 23
    // 18 24400 10
    // ];
    // h=scf();
    // xtitle("Industry Market Share Study","Number of products",...
    // "Sales");
    // h.color_map = rainbowcolormap(3);
    // legen=string(1:3);
    // bubblechart(data,legen)
    //
    // // Reference : [4]
    // data =[
    // 10 10 100
    // 5 5 75
    // 8 5 65
    // 3 2 60
    // 5 3 50
    // 1 2 35
    // ];
    // legen=[
    // "Smith"
    // "West"
    // "Miller"
    // "Carlson"
    // "Redmond"
    // "Dillar"
    // ];
    // h=scf();
    // xtitle("Salary Study","Years with Firm",...
    // "% Salary Increase");
    // h.color_map = rainbowcolormap(6);
    // bubblechart(data,legen,1:6)
    // //
    // // With a separate legend
    // h=scf();
    // xtitle("Salary Study","Years with Firm",...
    // "% Salary Increase");
    // h.color_map = rainbowcolormap(6);
    // bubblechart(data,[],1:6)
    // legend(legen,"in_upper_left");
    //
    // // Reference [5-6]
    // legen=[
    // "Harvard University"
    // "Yale University"
    // "University of Texas"
    // "Princeton University"
    // "Stanford University"
    // "MIT"
    // "University of Michigan"
    // "Columbia University"
    // "Northwestern University"
    // "Texas A&M"
    // ];
    // data=[
    // 52652  31728080000 21000
    // 52700  19374000000 11875
    // 9816   17148649000 51112
    // 54780  17109508000 7859 
    // 52860  16502606000 19945
    // 40460  9712628000  10894
    // 25204  7834752000  42716
    // 59208  7789578000  28221
    // 40223  7182745000  20284
    // 19035  6999517000  49861
    // ];
    // // Scale the Undergraduate tuition
    // data(:,1)=data(:,1)/1.e3;
    // // Scale the endowment funds
    // data(:,2)=data(:,2)/1.e9;
    // // Scale the Number of Students
    // data(:,3)=data(:,3)/1.e3;
    // // The color is the rank
    // h=scf();
    // xtitle("Area of bubble : Number of Students Fall 2011",..
    // "Undergraduate tuition ($ Thousands)",..
    // "2011 endowment funds ($ Billions)")
    // h.color_map = rainbowcolormap(10);
    // tags=string((1:10)');
    // bubblechart(data,tags,1:10,4)
    // legend(tags+":"+legen,"in_upper_left");
    // 
    // Authors
    // Copyright (C) 2013 - Michael Baudin
    //
    // Bibliography
    // [1] Bubble chart, http://en.wikipedia.org/wiki/Bubble_chart
    // [2] Color blindness, http://en.wikipedia.org/wiki/Color_blindness
    // [3] Creating a bubble chart, http://office.microsoft.com/en-us/excel-help/creating-a-bubble-chart-HA001117076.aspx
    // [4] Add data labels to your Excel bubble charts, Mary Ann Richardson, 2008, http://www.techrepublic.com/blog/msoffice/add-data-labels-to-your-excel-bubble-charts/513
    // [5] Bubble Chart of America’s 10 Richest Colleges, Posted on May 10, 2012 by George F Huhn, http://www.bubblechartpro.com/bubble-chart-of-americas-10-richest-colleges/
    // [6] The 10 Richest Colleges in America, http://finance.yahoo.com/news/the-10-richest-colleges-in-america.html
    // [7] Google Chart Tools, Visualization: Bubble Chart, https://developers.google.com/chart/interactive/docs/gallery/bubblechart
    // 
    function bounds=plotEllipse(x,y,a,b,fill)
        // Plot an ellipse
        //
        // Calling Sequence
        //   bounds=plotEllipse(x,y,a,b,fill)
        //
        // Parameters
        //   x : a 1-by-1 matrix of doubles, the x coordinate of the center
        //   y : a 1-by-1 matrix of doubles, the y coordinate of the center
        //   a : a 1-by-1 matrix of doubles, the x semi axis
        //   b : a 1-by-1 matrix of doubles, the y semi axis
        //   fill : a 1-by-1 matrix of doubles, integer value, the color
        //   bounds : a 2-by-2 matrix of doubles, [xmin,ymin;xmax,ymax] of the circle
        //
        // Description
        // Plots an ellipse with center (x,y) and semi axes a and b
        // The color "fill" is the argument of xfpoly: 
        // see the help of xfpoly for details.
        //
        // Examples
        // h=scf();
        // h.children.isoview="on";
        // plotEllipse(0,0,2,2,1);
        // plotEllipse(5,5,1,1,2);
        // plotEllipse(2,-1,3,3,3);
        // drawaxis(x=-3:2:7,y=-4,dir='d',tics='v'); // X-axis
        // drawaxis(x=-3,y=-4:2:6,dir='l',tics='v'); // Y-axis
        // 
        // Authors
        // Copyright (C) 2013 - Michael Baudin

        n=100
        t=2*%pi*(0:n-1)/n
        xv=x+a*sin(t)
        yv=y+b*cos(t)
        xfpoly(xv,yv,fill);
        //
        // Cannot set datatips with xfarc, 
        // although it does prints an ellipse:
        //xset("color",fill)
        //xfarc(x,y,2*R,2*R,0,364*64)
        //
        bounds=[
        min(xv) min(yv)
        max(xv) max(yv)
        ]
    endfunction


    [lhs,rhs]=argn()
    apifun_checkrhs ( "bubblechart" , rhs , 1:5 )
    apifun_checklhs ( "bubblechart" , lhs , 0:1 )
    //
    data = varargin ( 1 )
    n=size(data,"r")
    legen = apifun_argindefault(varargin,2,[])
    fill = apifun_argindefault(varargin,3,4)
    maxR = apifun_argindefault(varargin,4,1)
    scale = apifun_argindefault(varargin,5,%t)
    //
    // Check Type
    apifun_checktype ( "bubblechart" , data , "data" , 1 , "constant" )
    if (legen<>[]) then
        apifun_checktype ( "bubblechart" , legen , "legen" , 2 , "string" )
    end
    apifun_checktype ( "bubblechart" , fill , "fill" , 3 , "constant" )
    apifun_checktype ( "bubblechart" , maxR , "maxR" , 4 , "constant" )
    apifun_checktype ( "bubblechart" , scale , "scale" , 5 , "boolean" )
    //
    // Check Size
    apifun_checkdims ( "bubblechart" , data , "data" , 1 , [n 3] )
    if (legen<>[]) then
        apifun_checkvector ( "bubblechart" , legen , "legen" , 2 , n )
    end
    if (size(fill,"*")<>1) then
        apifun_checkvector ( "bubblechart" , fill , "fill" , 3 , n )
    end
    apifun_checkscalar ( "bubblechart" , maxR , "maxR" , 4 )
    apifun_checkscalar ( "bubblechart" , scale , "scale" , 5 )
    //
    // Check Content
    apifun_checkgreq ( "bubblechart" , maxR , "maxR" , 4 , 0 )
    //
    n=size(legen,"*");
    X=data(:,1)
    Y=data(:,2)
    Z=data(:,3)
    // Set 1.e-308 to entries of Z which are zero.
    // This prints a zero bubble, but makes 
    // the code compute a nonzero radius.
    tiny = number_properties("tiny")
    Z(Z==0)=tiny
    // Generate an error for negative values
    apifun_checkgreq ( "bubblechart" , Z , "Z" , 1 , tiny )
    n=size(data,"r")
    //
    fill=fill(:)
    if (size(fill,"*")==1) then
        fill=repmat(fill,n,1)
    end
    // A: the area covered by the (X,Y) data
    A=(max(Y)-min(Y))/(max(X)-min(X))
    // The area, pi*R(i)^2, is proportionnal to Z(i)
    R=sqrt(Z/%pi)
    R=R/max(R)
    R=sqrt(A)*maxR*R
    if (scale) then
        // Make so that b/a=A, without changing the area 
        // of the ellipse = pi*a*b
        b=R*sqrt(A)
        a=R/sqrt(A)
        // The area is pi*a*b=pi*R^2
    else
        a=R
        b=R
    end
    h=gcf()
    bounds=[
    %inf %inf
    -%inf -%inf
    ]
    for i=1:n
        ibounds=plotEllipse(X(i),Y(i),a(i),b(i),fill(i));
        bounds(1,1)=min(bounds(1,1),ibounds(1,1))
        bounds(2,1)=max(bounds(2,1),ibounds(2,1))
        bounds(1,2)=min(bounds(1,2),ibounds(1,2))
        bounds(2,2)=max(bounds(2,2),ibounds(2,2))
    end
    // Put the datatips later, so that we 
    // can have both datatips, and consistent legend, if needed.
    if (legen<>[]) then
        for i=1:n
            p=h.children.children($-i+1);
            t=datatipCreate(p,1);
            t.children(2).text=legen(i);
        end
    end
    plot(mean(X),mean(Y)); // Get dynamic axes
    h.children.data_bounds= bounds
    datatipRedraw();
endfunction

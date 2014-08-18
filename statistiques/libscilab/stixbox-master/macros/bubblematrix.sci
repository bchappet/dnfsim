// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function bubblematrix(varargin)
    // Plot a bubble chart
    //
    // Calling Sequence
    //   bubblematrix(X,Y,Z)
    //   bubblematrix(X,Y,Z,fill)
    //   bubblematrix(X,Y,Z,fill,maxR)
    //   bubblematrix(X,Y,Z,fill,maxR,scale)
    //
    // Parameters
    //   X : a nx-by-1 or 1-by-nx matrix of doubles, the X coordinates
    //   Y : a ny-by-1 or 1-by-ny matrix of doubles, the Y coordinates
    //   Z : a nx-by-ny matrix of doubles, the Z coordinates
    //   fill : a 1-by-1 or n-by-1 matrix of doubles, integer value, the color (default fill=4)
    //   maxR : a 1-by-1 matrix of doubles, positive, the maximum radius (default maxR=1)
    //   scale : a boolean, set to %f to disable scaling (default scale=%t)
    //
    // Description
    // Plots a matrix of bubbles for the data. 
    // For each row i=1,2,...,nx, and each column j=1,2,...,ny, 
    // a colored bubble with center (X(i),Y(i)) is plotted. 
    // The radius of the i-th bubble is computed depending 
    // on Z(i,j), so that the area of the bubble is 
    // proportionnal to Z.
    //
    // The radius are scaled, so that the bubbles have a radius proportionnal 
    // to maxR. 
    // Reduce this parameter if the bubbles are too large, perhaps 
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
    // Examples
    // // A matrix of bubbles
    // Z = [
    // 53.    35.    34.    34.    28.    28.    19.  
    // 53.    24.    19.    15.    4.     9.     0.   
    // 53.    16.    10.    6.     3.     0.     0.   
    // 53.    13.    7.     0.     0.     0.     0.   
    // 53.    10.    0.     0.     0.     0.     0.   
    // 53.    8.     0.     0.     0.     0.     0.   
    // ];
    // X=1:6;
    // Y=1:7;
    // // With default settings
    // scf();
    // bubblematrix(X,Y,Z);
    // // Set the color (2="blue"), the radius (=0.5).
    // h=scf();
    // bubblematrix(X,Y,Z,2,0.4);
    // xtitle("Area of bubbles: Z","X","Y")
    // h.children.axes_visible=["off","off","off"];
    // // Draw X axis
    // drawaxis(x=X,y=0.2,dir="d",tics="v",val=string(X))
    // // Draw Y axis
    // drawaxis(x=0.3,y=Y,dir="l",tics="v",val=string(Y))
    //
    // // Bubbles with random area
    // n=5;
    // R=distfun_unifrnd(0,1,n,n);
    // h=scf();
    // xtitle("Random numbers [0,1]")
    // bubblematrix(1:n,1:n,R,-2,0.5)
    //
    // // Reference : [1]
    // weightLabels=["<150" "150-175" "175-200" ">200"]
    // eventLabels=[
    // "LW_double_sculls"
    // "LW_four"
    // "coxswain"
    // "eight"
    // "four"
    // "pair"
    // "quad"
    // "single_sculls"
    // ];
    // R=[
    // 0.    0.    1.    0.    0.    0.    0.    0.  
    // 2.    4.    0.    0.    0.    0.    0.    0.  
    // 0.    0.    0.    4.    1.    1.    0.    0.  
    // 0.    0.    0.    4.    3.    1.    4.    1.  
    // ];
    // // Print the table
    // disp([["";eventLabels],[weightLabels;string(R')]])
    // // Set the color (2="blue"), the radius (=0.5).
    // h=scf();
    // m=size(R,"c")
    // n=size(R,"r")
    // bubblematrix(1:n,1:m,R,2,0.2);
    // xtitle("Area=Number of Athletes","Weigth Cat.","Event")
    // h.children.axes_visible=["off","off","off"];
    // // Draw X axis
    // drawaxis(x=1:n,y=0.25,dir="d",tics="v",val=weightLabels)
    // // Draw Y axis
    // drawaxis(x=0.8,y=1:m,dir="l",tics="v",val=eventLabels)
    // 
    // //
    // // A regular matrix of bubbles
    // nx=20;
    // ny=20;
    // X=linspace(0,2*%pi,nx);
    // Y=linspace(0,2*%pi,ny);
    // Z=cos(X)'*sin(Y);
    // Z1=max(Z,0);
    // Z2=max(-Z,0);
    // h=scf();
    // bubblematrix(1:nx,1:ny,Z1,-2,0.5);
    // bubblematrix(1:nx,1:ny,Z2,-3,0.5);
    // xtitle("Area: z=cos(x)sin(y), blue:z>0, green:z<0","x","y")
    // 
    // Authors
    // Copyright (C) 2013 - Michael Baudin
    //
    // Bibliography
    // [1] Categorical Data, Michelle Lacey, Yale University, Department of Statistics, http://www.stat.yale.edu/Courses/1997-98/101/catdat.htm
    // 

    [lhs,rhs]=argn()
    apifun_checkrhs ( "bubblematrix" , rhs , 3:6 )
    apifun_checklhs ( "bubblematrix" , lhs , 0:1 )
    //
    X = varargin ( 1 )
    Y = varargin ( 2 )
    Z = varargin ( 3 )
    nx=size(Z,"r")
    ny=size(Z,"c")
    fill = apifun_argindefault(varargin,4,4)
    maxR = apifun_argindefault(varargin,5,1)
    scale = apifun_argindefault(varargin,6,%t)
    //
    // Check Type
    apifun_checktype ( "bubblematrix" , X , "X" , 1 , "constant" )
    apifun_checktype ( "bubblematrix" , Y , "Y" , 2 , "constant" )
    apifun_checktype ( "bubblematrix" , Z , "Z" , 3 , "constant" )
    apifun_checktype ( "bubblematrix" , fill , "fill" , 4 , "constant" )
    apifun_checktype ( "bubblematrix" , maxR , "maxR" , 5 , "constant" )
    apifun_checktype ( "bubblematrix" , scale , "scale" , 6 , "boolean" )
    //
    // Check Size
    apifun_checkvector ( "bubblematrix" , X , "X" , 1 , nx )
    apifun_checkvector ( "bubblematrix" , Y , "Y" , 2 , ny )
    apifun_checkdims ( "bubblechart" , Z , "Z" , 3 , [nx ny] )
    if (size(fill,"*")<>1) then
        apifun_checkvector ( "bubblematrix" , fill , "fill" , 4 , nx*ny )
    end
    apifun_checkscalar ( "bubblematrix" , maxR , "maxR" , 5 )
    apifun_checkscalar ( "bubblematrix" , scale , "scale" , 6 )
    //
    // Check Content
    apifun_checkgreq ( "bubblematrix" , Z , "Z" , 3 , 0 )
    tiny=number_properties("tiny")
    apifun_checkgreq ( "bubblematrix" , maxR , "maxR" , 5 , tiny )
    //
    X=X(:)
    Y=Y(:)
    [XX,YY]=meshgrid(X,Y);
    data=zeros(nx*ny,3)
    data(:,1)=XX(:)
    data(:,2)=YY(:)
    Z=Z'
    data(:,3)=Z(:)
    //
    legen=[]
    bubblechart(data,legen,fill,maxR,scale)
endfunction

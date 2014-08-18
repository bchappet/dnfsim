// Copyright (C) 2013 - Michael Baudin
// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 2006 - INRIA - Serge Steer
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function stairs(varargin)
    // Stairstep graph
    //
    // Calling Sequence
    //   stairs(y)
    //   stairs(x,y)
    //
    // Parameters
    //   x : a matrix of doubles
    //   y : a matrix of doubles
    //
    // Description
    // <literal>stairs(y)</literal> plots the stairstep graph of y.
    // <itemizedlist>
    //   <listitem><para>
    //   If y is a vector, the x-axis ranges from 1 to length(y).
    //   </para></listitem>
    //   <listitem><para>
    //   If y is a matrix, the x-axis ranges from 1 to the number of rows 
    //   in y.
    //   The number of columns in y is the number of stairstep graphs 
    //   to plot.
    //   </para></listitem>
    // </itemizedlist>
    //
    // <literal>stairs(x,y)</literal> plots the stairstep graph of x and y.
    //
    // Examples
    // // stairs(y), y a row vector
    // x = linspace(0,4*%pi,40);
    // y = sin(x);
    // scf();
    // stairs(y)
    //
    // // stairs(y), y a matrix
    // // Plot several stairstep graphs
    // x = linspace(0,4*%pi,50)';
    // y = [0.5*cos(x), 2*cos(x)];
    // scf();
    // stairs(y);
    //
    // // stairs(x,y)
    // x = linspace(0,4*%pi,40);
    // y = sin(x);
    // scf();
    // stairs(x,y)
    //
    // // Plot the empirical CDF of exponential 
    // // random numbers
    // n=10000;
    // y=1:n;
    // y=y/n;
    // lambda=1;
    // x=gsort(rexpweib(n,lambda),"g","i");
    // scf();
    // stairs(x,y);
    //
    // Authors
    // Copyright (C) 2013 - Michael Baudin
    // Copyright (C) 2010 - DIGITEO - Michael Baudin
    // Copyright (C) 2006 - INRIA - Serge Steer
    //

    [lhs,rhs]=argn()
    apifun_checkrhs ( "stairs" , rhs , 1:2 )
    apifun_checklhs ( "stairs" , lhs , 0:1 )
    //
    if (rhs==1) then
        y=varargin(1)
        if ((size(y,"r")==1)|(size(y,"c")==1)) then
            // y is a (row or column)vector
            // Make it column
            y=y(:)
            n=size(y,"r")
            x=(1:n)'
        else
            // y is a matrix
            n=size(y,"r")
            x=(1:n)'
        end
    else
        x = varargin ( 1 )
        y = varargin ( 2 )
        x=x(:)
    end
    //
    f=gcf();
    D=f.immediate_drawing;
    f.immediate_drawing='off';
    plot2d(x,y)
    e=gce();
    e.children.polyline_style=2;
    f.immediate_drawing=D;
endfunction

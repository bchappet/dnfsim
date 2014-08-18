// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_trnd(varargin)
    // T random numbers
    //
    // Calling Sequence
    //   x = distfun_trnd ( v )
    //   x = distfun_trnd ( v , [m,n] )
    //   x = distfun_trnd ( v , m , n )
    //
    // Parameters
    //   v : a matrix of doubles, the number of degrees of freedom, v>0. 
    //   m : a 1-by-1 matrix of floating point integers, the number of rows of x
    //   n : a 1-by-1 matrix of floating point integers, the number of columns of x
    //   x: a matrix of doubles, the random numbers, in the interval [0,1].
    //
    // Description
    //   Generates random variables from the T distribution.
    //
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Examples
    // // Produce 6 random numbers from T distribution
    // // and degrees of freedom from 1 to 6
    // x=distfun_trnd(1:6)
    //
    // // Compare empirical distribution and density
    // v=12;
    // N=10000;
    // h=scf();
    // x=distfun_trnd(v,1,N);
    // histplot(40,x);
    // h.children.children(1).children.background=-2;
    // [M,V]=distfun_tstat(v);
    // x=linspace(M-3*sqrt(V),M+3*sqrt(V),1000);
    // y=distfun_tpdf(x,v);
    // plot(x,y,"r-");
    // xtitle("T Random Numbers","X","Frequency")
    // legend(["Empirical","Density"]);
    //
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
    // Copyright (C) 2012 - 2014 - Michael Baudin

endfunction

// Copyright (C) 2013 - 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_evrnd ( varargin )
    // Extreme value (Gumbel) random numbers
    //
    // Calling Sequence
    //   x = distfun_evrnd ( mu , sigma )
    //   x = distfun_evrnd ( mu , sigma , [m,n] )
    //   x = distfun_evrnd ( mu , sigma , m , n )
    //
    // Parameters
    //   mu : a matrix of doubles, the average
    //   sigma : a matrix of doubles, the standard deviation. sigma>0.
    //   m : a 1-by-1 matrix of floating point integers, the number of rows of x
    //   n : a 1-by-1 matrix of floating point integers, the number of columns of x
    //   x: a matrix of doubles, the random numbers.
    //
    // Description
    //   Generates random variables from the Extreme value (Gumbel) distribution function.
    //   This is the minimum Gumbel distribution.
    //
    //   Any scalar input argument is expanded to a matrix of doubles of 
    //   the same size as the other input arguments.
    //
    // To get max-Gumbel random numbers:
    //<screen>
    // y = -distfun_evrnd(-mu,sigma,m,n)
    //</screen>
    // 
    // Examples
    // // Test both mu and sigma expanded
    // computed = distfun_evrnd(1,2,[5,5])
    //
    // // Plot Gumbel random numbers
    // N=1000;
    // x=linspace(-20,5,N);
    // y1=distfun_evpdf(x,0.5,2.);
    // x=distfun_evrnd(0.5,2,10000,1);
    // scf();
    // xtitle("Gumbel distribution","x","Density");
    // plot(x,y1)
    // histplot(20,x);
    // legend(["PDF","Data"],"in_upper_left");
    // 
    // // Compare with CDF
    // x=gsort(x,"g","i");
    // n=size(x,"*");
    // p=distfun_evcdf(x,0.5,2,%t);
    // scf();
    // plot(x,(1:n)'/n,"r-");
    // plot(x,p,"b-");
    // legend(["Empirical","CDF"],"in_upper_left");
    // xtitle("Gumbel distribution","x","P(X<x)");
    //
    // Authors
    // Copyright (C) 2013 - 2014 - Michael Baudin

endfunction


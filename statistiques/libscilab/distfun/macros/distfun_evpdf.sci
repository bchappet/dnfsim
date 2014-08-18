// Copyright (C) 2013 - 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function y = distfun_evpdf ( varargin )
    // Extreme value (Gumbel) PDF
    //
    // Calling Sequence
    //   y=distfun_evpdf(x,mu,sigma)
    //
    // Parameters
    //   x : a matrix of doubles, the outcome
    //   mu : a matrix of doubles, the location
    //   sigma : a matrix of doubles, the scale. sigma>0.
    //   y : a matrix of doubles, the density
    //
    // Description
    //   Computes the probability distribution function of the Extreme value (Gumbel) function. 
    //   This is the minimum Gumbel distribution.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles of the same size as the other input arguments.
    //
    //   The function definition is:
    //
    //<latex>
    //\begin{eqnarray}
    //f(x,\mu,\sigma) = \frac{1}{\sigma} \exp(z-\exp(z))
    //\end{eqnarray}
    //</latex>
    //
    // where
    //
    //<latex>
    //\begin{eqnarray}
    //z=\frac{x-\mu}{\sigma}
    //\end{eqnarray}
    //</latex>
    //
    // To get the max-Gumbel PDF:
    //<screen>
    // y = distfun_evpdf(-x,-mu,sigma)
    //</screen>
    //
    // Examples
    // computed = distfun_evpdf ( [-1 1] , 0 , 1 )
    // expected = [0.2546464 0.1793741];
    //
    // // Plot the Gumbel PDF
    // N=1000;
    // x=linspace(-20,5,N);
    // y1= distfun_evpdf(x,-0.5,2.);
    // y2= distfun_evpdf(x,-1.0,2.);
    // y3= distfun_evpdf(x,-1.5,3.);
    // y4= distfun_evpdf(x,-3.0,4.);
    // scf();
    // xtitle("Gumbel","x","Density");
    // plot(x,y1,"r-")
    // plot(x,y2,"g-")
    // plot(x,y3,"b-")
    // plot(x,y4,"c-")
    // leg(1)="$\mu=-0.5,\beta=2.0$";
    // leg(2)="$\mu=-1.0,\beta=2.0$";
    // leg(3)="$\mu=-1.5,\beta=3.0$";
    // leg(4)="$\mu=-3.0,\beta=4.0$";
    // legend(leg,"in_upper_left");
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Gumbel_distribution
    // NIST/SEMATECH e-Handbook of Statistical Methods, 
    // http://www.itl.nist.gov/div898/handbook/
    // http://www.itl.nist.gov/div898/handbook/eda/section3/eda366g.htm
    //
    // Authors
    //   Copyright (C) 2013 - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_evpdf" , rhs , 3 )
    apifun_checklhs ( "distfun_evpdf" , lhs , 0:1 )
    //
    x = varargin ( 1 )
    mu = varargin (2)
    sigma = varargin (3)
    //
    // Check Type
    apifun_checktype ( "distfun_evpdf" , x , "x" , 1 , "constant" )
    apifun_checktype ( "distfun_evpdf" , mu , "mu" , 2 , "constant" )
    apifun_checktype ( "distfun_evpdf" , sigma , "sigma" , 3 , "constant" )
    //
    // Check Size
    // Nothing to to
    //
    // Check Content
    tiny = number_properties("tiny")
    apifun_checkgreq ( "distfun_evpdf" , sigma , "sigma" , 3 , tiny )
    //
    [ x , mu , sigma ] = apifun_expandvar ( x , mu , sigma )
    //
    y=distfun_pdfev(x,mu,sigma)
endfunction


// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2011 - INRIA - Michael Baudin
// Copyright (C) 2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt


function y = distfun_exppdf ( varargin )
    // Exponential PDF
    //
    // Calling Sequence
    //   y = distfun_exppdf ( x , mu )
    //
    // Parameters
    // x: a matrix of doubles
    // mu : a matrix of doubles, the average. mu>0
    // y: a matrix of doubles, the density
    //
    // Description
    //   This function computes the Exponential PDF.
    //
    //   Any scalar input argument is expanded to a matrix of doubles of the same size 
    //   as the other input arguments.
    //
    // Any optional input argument equal to the empty matrix will be set to its
    // default value.
    //
    // The exponential distribution with average mu has density
    //
    // <latex>
    // \begin{eqnarray}
    // f(x) = \frac{1}{\mu} e^{\frac{-x}{\mu}}
    // \end{eqnarray}
    // </latex>
    //
    // for x >= 0 and is zero if x<0.
    //
    // Compatibility note. 
    // 
    // Notice that mu, the average, is the inverse of the rate. 
    // Other computing languages (including R), use 1/mu as the 
    // parameter of the exponential distribution.
    // The calling sequence
    //
    // distfun_exppdf(x,mu)
    // 
    // corresponds to the R calling sequence:
    //
    // dexp(x,1/mu)
    //
    // Examples
    // // http://en.wikipedia.org/wiki/Exponential_distribution
    // scf();
    // x = linspace(0,5,1000);
    // y = distfun_exppdf ( x , 2 );
    // plot(x,y, "r-" );
    // y = distfun_exppdf ( x , 1 );
    // plot(x,y, "m-" );
    // y = distfun_exppdf ( x , 2/3 );
    // plot(x,y, "c-" );
    // xtitle("Exponential PDF","x","y");
    // legend(["mu=2","mu=1","mu=2/3"]);
    //
    // // For negative inputs, the probability is 
    // // zero
    // distfun_exppdf(-10,2)
    //
    // Authors
    //   Copyright (C) 2008-2011 - INRIA - Michael Baudin
    //   Copyright (C) 2012 - Michael Baudin
    //
    // Bibliography
    // Wikipedia, Exponential distribution function, http://en.wikipedia.org/wiki/Exponential_distribution

    [lhs, rhs] = argn()
    apifun_checkrhs ( "distfun_exppdf" , rhs , 2 )
    apifun_checklhs ( "distfun_exppdf" , lhs , 0:1 )
    //
    x = varargin(1)
    mu = varargin(2)
    //
    // Check type
    apifun_checktype ( "distfun_exppdf" , x , "x" , 1 , "constant" )
    apifun_checktype ( "distfun_exppdf" , mu , "mu" , 2 , "constant" )
    //
    // Check size : OK
    //
    // Check content
    tiny = number_properties("tiny")
    apifun_checkgreq ( "distfun_exppdf" , mu , "mu" , 2 , tiny )
    //
    // Proceed ...
    [ x , mu ] = apifun_expandvar ( x , mu )
    //
    y=distfun_pdfexp(x,mu)
endfunction


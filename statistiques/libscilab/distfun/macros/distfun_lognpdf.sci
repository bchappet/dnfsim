// Copyright (C) 2011 - 2012 - Michael Baudin
// Copyright (C) 2011 - INRIA - Michael Baudin
// Copyright (C) 2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt


function y = distfun_lognpdf ( varargin )
    // Lognormal PDF
    //
    // Calling Sequence
    //   y = distfun_lognpdf ( x , mu , sigma )
    //
    // Parameters
    // x: a matrix of doubles
    // mu: a matrix of doubles, the mean of the underlying normal variable.
    // sigma: a matrix of doubles, the variance of the underlying normal variable. sigma>0.
    // y: a matrix of doubles, the density
    //
    // Description
    //   This function computes the Lognormal PDF.
    //
    //   Any scalar input argument is expanded to a matrix of doubles of the same size 
    //   as the other input arguments.
    //
    // Any optional input argument equal to the empty matrix will be set to its
    // default value.
    //
    // The Lognormal distribution with parameters mu and sigma has density
    //
    //<latex>
    //\begin{eqnarray}
    //f(x) = 
    // \left\{
    // \begin{array}{l}
    // \frac{1}{\sigma x \sqrt{2 \pi}} \exp\left(-\frac{(\ln(x) - \mu)^2}{2 \sigma^2}\right), \textrm{ if } x> 0, \\
    // 0, \textrm{ otherwise.}
    // \end{array}
    // \right.
    //\end{eqnarray}
    //</latex>
    //
    // Examples
    // // See Dider Pelat
    // // "Bases et méthodes pour le traitement de données"
    // scf();
    // x = linspace ( 0 , 5 , 1000 );
    // y = distfun_lognpdf ( x , 0.0 , 1.0 );
    // plot ( x , y );
    // xtitle("The log-normale PDF","x","f");
    //
    // // See wikipedia
    //
    // // Examples from wikipedia
    // scf();
    // x = linspace ( 0 , 5 , 1000 );
    // y = distfun_lognpdf ( x , 0.0 , 10 );
    // plot ( x , y , "k" );
    // y = distfun_lognpdf ( x , 0.0 , 3/2 );
    // plot ( x , y , "b" );
    // y = distfun_lognpdf ( x , 0.0 , 1 );
    // plot ( x , y , "g" );
    // y = distfun_lognpdf ( x , 0.0 , 1/2 );
    // plot ( x , y , "y" );
    // y = distfun_lognpdf ( x , 0.0 , 1/4 );
    // plot ( x , y , "r" );
    // legend ( ["s=10" "s=3/2" "s=1" "s=1/2" "s=1/4"] );
    // xtitle("Log-normal PDF","x","y");
    //
    // Authors
    // Copyright (C) 2012 - Michael Baudin
    // Copyright (C) 2011 - DIGITEO - Michael Baudin
    // Copyright (C) 2008-2011 - INRIA - Michael Baudin
    //
    // Bibliography
    // Dider Pelat, "Bases et méthodes pour le traitement de données", section 8.2.8, "Loi log-normale".
    // Wikipedia, Lognormal probability distribution function, http://en.wikipedia.org/wiki/File:Lognormal_distribution_PDF.png
    // Wikipedia, Lognormal cumulated distribution function, http://en.wikipedia.org/wiki/File:Lognormal_distribution_CDF.png

    [lhs, rhs] = argn()
    apifun_checkrhs ( "distfun_lognpdf" , rhs , 3 )
    apifun_checklhs ( "distfun_lognpdf" , lhs , 0:1 )
    //
    x = varargin(1)
    mu = varargin(2)
    sigma = varargin(3)
    //
    // Check type
    apifun_checktype ( "distfun_lognpdf" , x , "x" , 1 , "constant" )
    apifun_checktype ( "distfun_lognpdf" , mu , "mu" , 2 , "constant" )
    apifun_checktype ( "distfun_lognpdf" , sigma , "sigma" , 3 , "constant" )
    //
    // Check size : OK
    //
    // Check content
    tiny = number_properties("tiny")
    apifun_checkgreq ( "distfun_lognpdf" , sigma , "sigma" , 3 , tiny )
    //
    // Proceed ...
    [ x , mu , sigma ] = apifun_expandvar ( x , mu , sigma )
    //
    y = distfun_pdflogn(x,mu,sigma)
endfunction


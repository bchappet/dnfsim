// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2009 - 2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function y = distfun_normpdf ( varargin )
    // Normal PDF
    //
    // Calling Sequence
    //   y=distfun_normpdf(x,mu,sigma)
    //
    // Parameters
    //   x : a matrix of doubles, the outcome
    //   mu : a matrix of doubles, the mean
    //   sigma : a matrix of doubles, the standard deviation. sigma>0.
    //   y : a matrix of doubles, the density
    //
    // Description
    //   Computes the probability distribution function of the Normal (Laplace-Gauss) function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles of the same size as the other input arguments.
    //
    //   The function definition is:
    //
    //<latex>
    //\begin{eqnarray}
    //f(x,\mu,\sigma) = \frac{1}{\sigma\sqrt{2\pi}} \exp\left(\frac{-(x-\mu)^2}{2\sigma^2}\right)
    //\end{eqnarray}
    //</latex>
    //
    // Examples
    //   computed = distfun_normpdf ( [-1 1] , 0 , 1 )
    //   expected = [ 0.241970724519143   0.241970724519143 ];
    //   //
    //   // Check expansion of mu and expansion of sigma
    //   computed = distfun_normpdf ( [1 2 3] , 1.0 , 2.0 )
    //   expected = [ ..
    //    0.199471140200716, ..
    //    0.176032663382150, ..
    //    0.120985362259572 ..
    // ];
    //   // Check with expanded arguments
    //   computed = distfun_normpdf ( [1 2 3] , [1 1 1], [2 2 2] )
    //
    // // Plot the function
    // mu = [0 0 0 -2];
    // sigma2 = [0.2 1.0 5.0 0.5];
    // cols = [1 2 3 4];
    // nf = size(cols,"*");
    // lgd = [];
    // scf();
    // for k = 1 : nf
    //   x = linspace(-5,5,1000);
    //   y = distfun_normpdf ( x , mu(k) , sqrt(sigma2(k)) );
    //   plot(x,y)
    //   str = msprintf("mu=%s, sigma^2=%s",..
    //     string(mu(k)),string(sigma2(k)));
    //   lgd($+1) = str;
    // end
    // h = gcf();
    // for k = 1 : nf
    //   hk = h.children.children.children(nf - k + 1);
    //   hk.foreground = cols(k);
    // end
    // legend(lgd);
    // xtitle("Normal PDF","x","y")
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Normal_distribution
    //
    // Authors
    //   Copyright (C) 2009 - 2011 - DIGITEO - Michael Baudin
    //   Copyright (C) 2012 - Michael Baudin


    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_normpdf" , rhs , 3 )
    apifun_checklhs ( "distfun_normpdf" , lhs , 0:1 )
    //
    x = varargin ( 1 )
    mu = varargin (2)
    sigma = varargin (3)
    //
    // Check Type
    apifun_checktype ( "distfun_normpdf" , x , "x" , 1 , "constant" )
    apifun_checktype ( "distfun_normpdf" , mu , "mu" , 2 , "constant" )
    apifun_checktype ( "distfun_normpdf" , sigma , "sigma" , 3 , "constant" )
    //
    // Check Size
    // Nothing to to
    //
    // Check Content
    tiny = number_properties("tiny")
    apifun_checkgreq ( "distfun_normpdf" , sigma , "sigma" , 3 , tiny )
    //
    [ x , mu , sigma ] = apifun_expandvar ( x , mu , sigma )
    y = distfun_pdfnorm(x,mu,sigma)
endfunction


// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2011 - INRIA - Michael Baudin
// Copyright (C) 2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt



function y = distfun_erfcinv ( x )
    // Inverse erfc function
    //
    // Calling Sequence
    //   y = distfun_erfcinv ( x )
    //
    // Parameters
    // x: a matrix of doubles, in the range [0,2].
    // y: a matrix of doubles
    //
    // Description
    //   This function computes the inverse of the erfc function.
    //   This function is ill-conditioned when x is close to 1 or 
    //   when x is close to 2: this might lead to inaccurate results.
    //
    // The implementation is based on cdfnor, which suffers from 
    // inaccuracies when x is close to 0.5:
    // http://bugzilla.scilab.org/show_bug.cgi?id=9030
    //
    // Examples
    // distfun_erfcinv(1.e-3)
    // expected = 2.326753765513525
    //
    // // Plot the function
    // scf();
    // x = linspace(1.e-5,2-1.e-5,1000);
    // y = distfun_erfcinv(x);
    // plot(x,y);
    //
    // // This implementation is accurate, even if x is small:
    // distfun_erfcinv ( 10^-20 ) 
    // expected = 6.60158062235514256
    // // By contrast, the mathematically correct erfinv(1-x)
    // // formula gives poor results:
    // x = 10^-20
    // erfinv(1-x)
    // expected = 6.60158062235514256
    //
    // // erfcinv is ill-conditioned when x is close to 1.
    // distfun_erfcinv(1-1.e-15)
    // expected = 8.855185839123727168e-16
    //
    // // erfcinv is ill-conditioned when x is close to 2.
    // distfun_erfcinv(2-1.e-12)
    // expected = -5.04202109411347248
    //
    // Authors
    // Copyright (C) 2008-2011 - INRIA - Michael Baudin
    //
    // Bibliography
    // Dider Pelat, "Bases et méthodes pour le traitement de données", section 8.2.8, "Loi log-normale".
    // Wikipedia, Lognormal probability distribution function, http://en.wikipedia.org/wiki/File:Lognormal_distribution_PDF.png
    // Wikipedia, Lognormal cumulated distribution function, http://en.wikipedia.org/wiki/File:Lognormal_distribution_CDF.png

    [lhs, rhs] = argn()
    apifun_checkrhs ( "distfun_erfcinv" , rhs , 1 )
    apifun_checklhs ( "distfun_erfcinv" , lhs , 0:1 )
    //
    // Check type
    apifun_checktype ( "distfun_erfcinv" , x , "x" , 1 , "constant" )
    //
    // Check size : OK
    //
    // Check content
    knan=find(isnan(x))
    knonan=find(~isnan(x))
    if ( ~isreal(x) ) then
        error(msprintf(gettext("%s: Wrong type for input argument #%d: Real matrix expected.\n"),"distfun_erfcinv",1));
    end
    // 
    // Proceed ...
    if ( x==[] ) then
        y = []
        return
    end
    //
    [nrows,ncols]=size(x)
    nx = nrows*ncols
    //
    // Make x as a column matrix
    x=x(:)
    y=zeros(nx,1)
    //
    // Process Nans
    y(knan) = %nan
    //
    // Process out-of-bounds
    k = find(x<0 | x>2)
    y(k)=%nan
    //
    // Process zeros
    k = find( x==0 )
    y(k)=%inf
    //
    // Process 2
    k = find( x==2 )
    y(k)=-%inf
    //
    // Process non-Nans, non-zeros, non-two, non-out-of-bounds
    k = find( ~isnan(x) & x>0 & x<2 )
    mu=zeros(nx,1)
    sigma=ones(nx,1)
    p = x/2
    q = 1-x/2
    y(k) = -distfun_invnorm(p(k),mu(k),sigma(k),%t)/sqrt(2)
    //
    // Make a matrix again
    y=matrix(y,nrows,ncols)
endfunction


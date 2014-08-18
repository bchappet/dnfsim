// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
//

function x = distfun_ksinv(varargin)
    // Kolmogorov-Smirnov Inverse CDF
    //
    // Calling Sequence
    //   x = distfun_ksinv(p,N)
    //   x = distfun_ksinv(p,N,lowertail)
    //
    // Parameters
    //   p : a matrix of doubles, the probability. Must be in the range [0,1].
    //   N : a matrix of doubles , the number of observations. N belongs to the set {1,2,3,4,.......,2147483647}
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   x : a matrix of doubles, the outcome, x in [0,1]
    //
    // Description
    //   Computes the Inverse cumulative distribution function of 
    //   the Kolmogorov-Smirnov distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Examples
    // x = distfun_ksinv(.6284796154565043,10)
    // expected = 0.274
    //
    // // Kolmogorov-Smirnov Test
    // // http://www.itl.nist.gov/div898/handbook/eda/section3/eda35g.htm
    // n=1000;
    // x=distfun_normrnd(0,1,[1 n]);
    // // Does x comes from the Normal distribution ?
    // x=gsort(x,"g","i");
    // y=distfun_normcdf(x,0,1);
    // i=1:n;
    // Dplus=max(y-(i-1)/n);
    // Dminus=max(i/n-y);
    // D=max(Dminus,Dplus);
    // mprintf("Test statistic D=%f\n",D)
    // // Threshold
    // alpha=0.05;
    // Dalpha=distfun_ksinv(alpha,n,%f);
    // mprintf("Significance level =%f\n",alpha)
    // mprintf("Critical value =%f\n",Dalpha)
    // mprintf("Critical region: Reject H0 if D>%f\n",Dalpha)
    // if (D>Dalpha) then
    //     mprintf("Reject H0.\n")
    // else
    //     mprintf("Accept H0.\n")
    // end
    // // We should accept 95% of the time
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Kolmogorov%E2%80%93Smirnov_test
    //
    // Authors
    // Copyright (C) 2014 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_ksinv",rhs,2:3)
    apifun_checklhs("distfun_ksinv",lhs,0:1)

    p = varargin(1)
    N = varargin(2)
    lowertail = apifun_argindefault(varargin,3,%t)
    //
    // Check type
    apifun_checktype("distfun_ksinv",p,"p",1,"constant")
    apifun_checktype("distfun_ksinv",N,"N",2,"constant")
    apifun_checktype("distfun_ksinv",lowertail,"lowertail",3,"boolean")
    //
    // Check size
    apifun_checkscalar("distfun_ksinv",lowertail,"lowertail",4)
    //
    // Check Content
    apifun_checkrange("distfun_ksinv",p,"p",1,0,1)
    apifun_checkgreq("distfun_ksinv",N,"N",2,1)
    apifun_checkflint("distfun_ksinv",N,"N",2)
    apifun_checkloweq("distfun_ksinv",N,"N",2,2147483647)

    [p,N] = apifun_expandvar(p,N)
    x = distfun_invks(p,N,lowertail)
endfunction

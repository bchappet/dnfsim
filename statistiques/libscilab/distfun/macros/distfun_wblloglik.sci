// Copyright (C) 2012 - 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function nlogl = distfun_wblloglik(params,data)
    // Weibull negative log-likelihood
    //
    // Calling Sequence
    //   nlogl = distfun_wblloglik(params,data)
    //
    // Parameters
    //   data : a matrix of doubles, the data
    //   params : a 1-by-2 matrix of doubles, the parameters of the Weibull distribution. params(1) is a, params(2) is b.
    //   nlogl : a 1-by-1 matrix of doubles, the negative log-likelihood
    //
    // Description
    // Computes the negative log-likelihood depending on the data, 
    // assuming that is Weibull distributed.
    //
    // The negative log-likelihood function is :
    //
    // -sum(log(PDF(params,data)))
    //
    // where PDF is the probability distribution function, 
    // which depends on the parameters and the data. 
    //
    // Examples
    // // Samples from Weibull distribution with 
    // // a=5432 and b=3.21
    // data = [
    // 3303.  
    // 3172.  
    // 2473.  
    // 5602.  
    // 3109.  
    // 4415.  
    // 6471.  
    // 5952.  
    // 3945.  
    // 3534.  
    // ]
    // nlogl = distfun_wblloglik([4672 3.5],data)
    // exact=85.799862
    //
    // Authors
    //   Copyright (C) 2012 - 2014 - Michael Baudin
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Likelihood_function

    a = params(1)
    b = params(2)
    if (a==0) then
        nlogL = %inf
        return
    end
    if (b==0) then
        nlogL = %inf
        return
    end
    ypdf = distfun_wblpdf(data,a,b)
    k = find(ypdf>0)
    t = -%inf*ones(data)
    t(k) = log(ypdf(k))
    nlogl = -sum(t)
endfunction


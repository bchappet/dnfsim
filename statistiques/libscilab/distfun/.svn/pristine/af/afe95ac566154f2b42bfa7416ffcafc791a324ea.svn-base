// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function parmhat=distfun_binofitmm(data)
    // Binomial parameter estimates with method of moments
    //
    // Calling Sequence
    //   parmhat = distfun_binofitmm( data )
    //
    // Parameters
    //   data : a matrix of doubles, the data, in the set {0,1,2,3,...}.
    //   parmhat : a 1-by-2 matrix of doubles, the parameters of the Binomial distribution. parmhat(1) is N, parmhat(2) is pr.
    //
    // Description
    // Estimates the parameters of the Binomial distribution 
    // with method of moments. 
    // In other words, finds the parameters so that 
    // the mean and variance of the distribution are 
    // equal to the empirical mean and empirical variance of the 
    // data. 
    //
    // The implementation uses direct inversion. 
    // The exact solution may generate a non integer N :
    // the estimated N is the integer nearest to the exact solution. 
    // Moreover, this may be lower than 1 : in this case, 
    // the estimate is set to 1.
    //
    // Examples
    // // Samples from Binomial distribution with 
    // // N=12 and pr=0.42
    // data = [7 3 7 7 3 8 7 4 6 4]
    // parmhat = distfun_binofitmm(data)
    // N=parmhat(1);
    // pr=parmhat(2);
    // // Compare the (mean,variance) of the 
    // // distribution against the data : 
    // // must be close, but cannot be equal 
    // // because N must be integer.
    // [M,V]=distfun_binostat(N,pr)
    // M_data=mean(data)
    // V_data=variance(data)
    //
    // // Error : We must have more than one data.
    // // parmhat = distfun_binofitmm(0)
    //
    // // Error : The mean must be nonzero.
    // // parmhat = distfun_binofitmm([0 0])
    //
    // // Error : The estimated pr is lower or equal than zero
    // // parmhat = distfun_binofitmm(1:7)
    // // parmhat = distfun_binofitmm(1:8)
    //
    // Authors
    //   Copyright (C) 2014 - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_binofitmm" , rhs , 1 )
    apifun_checklhs ( "distfun_binofitmm" , lhs , 0:1 )
    //
    // Check type
    apifun_checktype ( "distfun_binofitmm" , data , "data" , 1 , "constant" )
    //
    // Check size
    apifun_checkvector ( "distfun_binofitmm" , data , "data" , 1 )
    //
    // Check content
    apifun_checkgreq( "distfun_binofitmm" , data , "data" , 1 , 0 )
    apifun_checkflint( "distfun_binofitmm" , data , "data" , 1 )

    // 
    // 1. Estimate the empirical coefficient of variation 
    // of the data
    n=size(data,"*")
    if (n==1) then
        errmsg = msprintf(gettext("%s: Wrong size for input argument #%d: Must have more than one data."), "distfun_binofitmm",1);
        error(errmsg)
    end
    m = mean(data)
    v = variance(data)
    if (m==0) then
        errmsg = msprintf(gettext("%s: Cannot estimate pr when mean is zero."), "distfun_binofitmm");
        error(errmsg)
    end
    // 
    // 2. Estimate N and pr
    pr=1-(n-1)*v/n/m
    if (pr<=0) then
        errmsg = msprintf(gettext("%s: Cannot estimate N when pr is lower or equal to zero."), "distfun_binofitmm");
        error(errmsg)
    end
    N=m/pr
    N=round(N)
    N=max(N,1)
    //
    parmhat=[N,pr]
endfunction

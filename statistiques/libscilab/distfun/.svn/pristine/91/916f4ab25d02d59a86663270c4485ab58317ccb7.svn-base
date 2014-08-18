// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function parmhat=distfun_betafitmm(data)
    // Beta parameter estimates with method of moments
    //
    // Calling Sequence
    //   parmhat = distfun_betafitmm( data )
    //
    // Parameters
    //   data : a matrix of doubles, the data, in the interval [0,1].
    //   parmhat : a 1-by-2 matrix of doubles, the parameters of the Beta distribution. parmhat(1) is a, parmhat(2) is b.
    //
    // Description
    // Estimates the parameters of the Beta distribution 
    // with method of moments. 
    // In other words, finds the parameters so that 
    // the mean and variance of the distribution are 
    // equal to the empirical mean and empirical variance of the 
    // data. 
    //
    // The implementation is based on direct inversion of the moments. 
    //
    // On output, we have a>=0 and b>=0.
    // If the exact a or b is lower than zero, the estimate is set to 
    // zero.
    // If required, a or b is set to zero. 
    // In this case, the moments are not matched anymore. 
    //
    // Examples
    // // Samples from Beta distribution with 
    // // a=12 and b=42
    // data = [
    //    0.2876148  
    //    0.3311889  
    //    0.2448739  
    //    0.1452694  
    //    0.1861828  
    //    0.2299041  
    //    0.1619109  
    //    0.3711106  
    //    0.2198700  
    //    0.2831889  
    // ]
    // parmhat = distfun_betafitmm(data)
    // a=parmhat(1);
    // b=parmhat(2);
    // // Compare the (mean,variance) of the 
    // // distribution against the data : 
    // // must be equal.
    // [M,V]=distfun_betastat(a,b)
    // M_data=mean(data)
    // V_data=variance(data)
    //
    // // Error : variance is zero
    // // parmhat = distfun_betafitmm([1 1])
    //
    // Authors
    //   Copyright (C) 2014 - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_betafitmm" , rhs , 1 )
    apifun_checklhs ( "distfun_betafitmm" , lhs , 0:1 )
    //
    // Check type
    apifun_checktype ( "distfun_betafitmm" , data , "data" , 1 , "constant" )
    //
    // Check size
    apifun_checkvector ( "distfun_betafitmm" , data , "data" , 1 )
    //
    // Check content
    apifun_checkrange( "distfun_betafitmm" , data , "data" , 1 , 0, 1 )

    n=size(data,"*")
    if (n==1) then
        errmsg = msprintf(gettext("%s: Wrong size for input argument #%d: Must have more than one data."), "distfun_betafitmm",1);
        error(errmsg)
    end
    // 
    // 1. Estimate the mean and variance 
    // of the data
    m = mean(data)
    v = variance(data)
    if (v==0) then
        errmsg = msprintf(gettext("%s: Cannot estimate parameters when variance is zero."), "distfun_betafitmm");
        error(errmsg)
    end
    // 
    // 2. Solve
    a=m^2*(1-m)/v-m
    b=m*(1-m)^2/v-(1-m)
    // 
    a=max(a,0)
    b=max(b,0)
    //
    parmhat=[a,b]
endfunction

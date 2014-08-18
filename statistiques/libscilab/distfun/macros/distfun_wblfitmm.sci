// Copyright (C) 2012 - 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function parmhat=distfun_wblfitmm(data)
    // Weibull parameter estimates with method of moments
    //
    // Calling Sequence
    //   parmhat = distfun_wblfitmm( data )
    //
    // Parameters
    //   data : a matrix of doubles, the data, data>=0
    //   parmhat : a 1-by-2 matrix of doubles, the parameters of the Weibull distribution. parmhat(1) is a, parmhat(2) is b.
    //
    // Description
    // Estimates the parameters of the Weibull distribution 
    // with method of moments. 
    // In other words, finds the parameters so that 
    // the mean and variance of the distribution are 
    // equal to the empirical mean and empirical variance of the 
    // data. 
    //
    // The implementation is based on the fact that the 
    // coefficient of variation of the Weibull distribution
    // only depends on b. 
    // The algorithm first searches for b, using a 
    // zero solver. 
    // Then the parameter a is computed depending on b.
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
    // parmhat = distfun_wblfitmm(data)
    // a=parmhat(1);
    // b=parmhat(2);
    // // Compare the (mean,variance) of the 
    // // distribution against the data : 
    // // must be equal.
    // [M,V]=distfun_wblstat(a,b)
    // M_data=mean(data)
    // V_data=variance(data)
    //
    // Authors
    //   Copyright (C) 2012 - 2014 - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_wblfitmm" , rhs , 1 )
    apifun_checklhs ( "distfun_wblfitmm" , lhs , 0:1 )
    //
    // Check type
    apifun_checktype ( "distfun_wblfitmm" , data , "data" , 1 , "constant" )
    //
    // Check size
    apifun_checkvector ( "distfun_wblfitmm" , data , "data" , 1 )
    //
    // Check content
    apifun_checkgreq( "distfun_wblfitmm" , data , "data" , 1 , 0 )

    function y = wblcvzero(b,cv)
        // Weibull coefficient of variation
        g1 = gamma(1+1 ./b).^2
        g2 = gamma(1+2 ./b)
        wblcv = sqrt(g1./(g2-g1))
        // The function for the zero solver, which must solve
        // wblcvzero(b)=0 for b.
        y = wblcv-cv
    endfunction

    n=size(data,"*")
    if (n==1) then
        errmsg = msprintf(gettext("%s: Wrong size for input argument #%d: Must have more than one data."), "distfun_wblfitmm",1);
        error(errmsg)
    end
    // 
    // 1. Estimate the empirical coefficient of variation 
    // of the data
    m = mean(data)
    v = variance(data)
    cv = m/sqrt(v)
    // 
    // 2. Estimate b starting from the approximate b.
    x0=1 // A rough estimate
    b=fsolve(x0,list(wblcvzero,cv))
    // 
    // 3. Estimate a
    a = m/gamma(1+1/b)
    //
    parmhat=[a,b]
endfunction

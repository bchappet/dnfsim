// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function parmhat=distfun_gamfitmm(data)
    // Gamma parameter estimates with method of moments
    //
    // Calling Sequence
    //   parmhat = distfun_gamfitmm( data )
    //
    // Parameters
    //   data : a matrix of doubles, the data, data>=0.
    //   parmhat : a 1-by-2 matrix of doubles, the parameters of the Gamma distribution. parmhat(1) is a, parmhat(2) is b.
    //
    // Description
    // Estimates the parameters of the Gamma distribution 
    // with method of moments. 
    // In other words, finds the parameters so that 
    // the mean and variance of the distribution are 
    // equal to the empirical mean and empirical variance of the 
    // data. 
    //
    // The implementation is based on direct inversion of the moments. 
    //
    // Examples
    // // Samples from Gamma distribution with 
    // // a=12 and b=42
    // data = [
    //    547.53259  
    //    347.58207  
    //    539.14939  
    //    342.82908  
    //    508.80556  
    //    386.28445  
    //    420.16239  
    //    725.67277  
    //    409.08739  
    //    726.20491
    // ]
    // parmhat = distfun_gamfitmm(data)
    // a=parmhat(1);
    // b=parmhat(2);
    // // Compare the (mean,variance) of the 
    // // distribution against the data : 
    // // must be equal.
    // [M,V]=distfun_gamstat(a,b)
    // M_data=mean(data)
    // V_data=variance(data)
    //
    // Authors
    //   Copyright (C) 2014 - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_gamfitmm" , rhs , 1 )
    apifun_checklhs ( "distfun_gamfitmm" , lhs , 0:1 )
    //
    // Check type
    apifun_checktype ( "distfun_gamfitmm" , data , "data" , 1 , "constant" )
    //
    // Check size
    apifun_checkvector ( "distfun_gamfitmm" , data , "data" , 1 )
    //
    // Check content
    apifun_checkgreq( "distfun_gamfitmm" , data , "data" , 1 , 0 )

    n=size(data,"*")
    if (n==1) then
        errmsg = msprintf(gettext("%s: Wrong size for input argument #%d: Must have more than one data."), "distfun_wblfitmm",1);
        error(errmsg)
    end
    // 
    // 1. Estimate the mean and variance 
    // of the data
    m = mean(data)
    v = variance(data)
    // 
    // 2. Solve
    a=m^2/v
    b=v/m
    //
    parmhat=[a,b]
endfunction

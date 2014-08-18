// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function parmhat=distfun_uniffitmm(data)
    // Uniform parameter estimates with method of moments
    //
    // Calling Sequence
    //   parmhat = distfun_uniffitmm( data )
    //
    // Parameters
    //   data : a matrix of doubles, the data
    //   parmhat : a 1-by-2 matrix of doubles, the parameters of the Uniform distribution. parmhat(1) is a, parmhat(2) is b.
    //
    // Description
    // Estimates the parameters of the Uniform distribution 
    // with method of moments. 
    // In other words, finds the parameters so that 
    // the mean and variance of the distribution are 
    // equal to the empirical mean and empirical variance of the 
    // data. 
    //
    // The implementation uses direct inversion. 
    //
    // Examples
    // // Samples from Uniform distribution with 
    // // a=12 and b=42
    // data = [
    //   36.441711  
    //   16.06431   
    //   39.173758  
    //   37.050258  
    //   15.809604  
    //   41.066033  
    //   39.401276  
    //   18.631021  
    //   30.970777  
    //   21.245012  
    // ]
    // parmhat = distfun_uniffitmm(data)
    // a=parmhat(1);
    // b=parmhat(2);
    // // Compare the (mean,variance) of the 
    // // distribution against the data : 
    // // must be equal.
    // [M,V]=distfun_unifstat(a,b)
    // M_data=mean(data)
    // V_data=variance(data)
    //
    // Authors
    //   Copyright (C) 2014 - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_uniffitmm" , rhs , 1 )
    apifun_checklhs ( "distfun_uniffitmm" , lhs , 0:1 )
    //
    // Check type
    apifun_checktype ( "distfun_uniffitmm" , data , "data" , 1 , "constant" )
    //
    // Check size
    apifun_checkvector ( "distfun_uniffitmm" , data , "data" , 1 )

    n=size(data,"*")
    if (n==1) then
        errmsg = msprintf(gettext("%s: Wrong size for input argument #%d: Must have more than one data."), "distfun_uniffitmm",1);
        error(errmsg)
    end
    // 
    // 1. Estimate the empirical coefficient of variation 
    // of the data
    m = mean(data)
    v = variance(data)
    // 
    // 2. Estimates a and b
    s=sqrt(3)*sqrt(v)
    a=m-s
    b=m+s
    //
    parmhat=[a,b]
endfunction

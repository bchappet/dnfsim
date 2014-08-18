// Copyright (C) 2012 - 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function parmhat = distfun_wblfit(data)
    // Weibull parameter estimates
    //
    // Calling Sequence
    //   parmhat = distfun_wblfit( data )
    //
    // Parameters
    //   data : a matrix of doubles, the data, data>=0
    //   parmhat : a 1-by-2 matrix of doubles, the parameters of the Weibull distribution
    //
    // Description
    // Estimates the parameters of the Weibull distribution 
    // with maximum likelihood method.
    // The parameters are chosen so that they minimize the 
    // negative log-likelihood function. 
    //
    // The implementation first estimates the parameters 
    // by the method of moments. 
    // This first estimate is then used a the starting 
    // guess for the optimization algorithm. 
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
    // parmhat = distfun_wblfit(data)
    // expected=[4673.7712    3.5090456]
    //
    // Authors
    //   Copyright (C) 2012 - 2014 - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_wblfit" , rhs , 1 )
    apifun_checklhs ( "distfun_wblfit" , lhs , 0:1 )
    //
    // Check type
    apifun_checktype ( "distfun_wblfit" , data , "data" , 1 , "constant" )
    //
    // Check size
    apifun_checkvector ( "distfun_wblfit" , data , "data" , 1 )
    //
    // Check content
    apifun_checkgreq( "distfun_wblfit" , data , "data" , 1 , 0 )

    // 1. Estimate the parameters by the 
    // method of moments. 
    parmhat=distfun_wblfitmm(data)
    // 2. Use this as a starting point for 
    // likelihood method
    costf=list(distfun_wblmlecost,data,%f);
    [fopt,parmhat]=optim(costf,parmhat)
endfunction


function [f, g, ind] = distfun_wblmlecost(params,ind,data,verbose)
    if (verbose) then
        mprintf("a=%s,b=%s\n",string(params(1)),string(params(2)))
    end
    if (params(1)<=0 | params(2)<=0) then
        ind=-1
        f = %nan
        g = %nan*ones(params)
        return
    end
    f = distfun_wbllike ( params , data );
    h = 1.e-8
    g= derivative ( list(distfun_wbllike,data) , params(:) , h );
    if (verbose) then
        mprintf("    f=%s,||g||=%s\n",string(f),string(norm(g)))
    end
endfunction

// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [M,V] = distfun_logustat ( a, b )
    // LogUniform mean and variance
    //
    // Calling Sequence
    //   M = distfun_logustat ( a , b )
    //   [M,V] = distfun_logustat ( a , b )
    //
    // Parameters
    // a: a matrix of doubles, the minimum of the underlying uniform variable. 
    // b: a matrix of doubles, the maximum of the underlying uniform variable. b>a.
    //   M : a matrix of doubles, the mean
    //   V : a matrix of doubles, the variance
    //
    // Description
    //   Computes statistics from the LogUniform distribution.
    //
    // The mean and variance of the LogUniform distribution are
    //
    //<latex>
    //\begin{eqnarray}
    // M&=&\frac{\exp(b)-\exp(a)}{b-a} \\
    // V&=&\frac{1}{2} \frac{\exp(b)^2-\exp(a)^2}{b-a}-M^2
    //\end{eqnarray}
    //</latex>
    //
    //   Any scalar input argument is expanded to a matrix of doubles of the same size as the other input arguments.
    //
    // Examples
    // a = 1:6;
    // b = 2:7;
    // [M,V]=distfun_logustat(a,b)
    //
    // // Check random samples
    // R=distfun_logurnd(10000,1,3,5);
    // [mean(R),M]
    // [variance(R),V]
    //
    // Authors
    //   Copyright (C) 2013 - Michael Baudin
    
    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_logustat" , rhs , 2 )
    apifun_checklhs ( "distfun_logustat" , lhs , 0:2 )
    //
    // Check type
    apifun_checktype ( "distfun_logustat" , a , "a" , 1 , "constant" )
    apifun_checktype ( "distfun_logustat" , b , "b" , 2 , "constant" )
    //
    // Check content
    apifun_checkgreq ( "distfun_logustat" , b , "b" , 2 , a )  
    //
    [ a , b ] = apifun_expandvar ( a , b )
    //
    if (a==[]) then
        M=[]
        V=[]
        return
    end
    //
    M=(exp(b)-exp(a))./(b-a)
    V=0.5*(exp(b)^2-exp(a)^2)./(b-a)-M^2
endfunction


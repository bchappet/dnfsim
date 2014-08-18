// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function p = distfun_unifcdf ( varargin )
    //   Uniform CDF
    //
    // Calling Sequence
    //   p = distfun_unifcdf ( x , a , b )
    //   p = distfun_unifcdf ( x , a , b , lowertail )
    //
    // Parameters
    //   x : a matrix of doubles, the outcome
    //   a : a matrix of doubles, the lower bound
    //   b : a matrix of doubles, the upper bound (with a<=b)
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   p : a matrix of doubles, the probability.
    //
    // Description
    //   Computes the cumulated probability distribution function of 
    //   the Uniform distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    //   The function definition is:
    //
    //<latex>
    //\begin{eqnarray}
    //f(x,a,b) = 
    // \left\{
    // \begin{array}{l}
    // 0, \textrm{ if } x\leq a, \\
    // \frac{x-a}{b-a}, \textrm{ if } x\in[a,b], \\
    // 1, \textrm{ if } x\geq b.
    // \end{array}
    // \right.
    //\end{eqnarray}
    //</latex>
    //
    // Examples
    // // Test expanded arguments
    // c = distfun_unifcdf ( [1 2 3] , [1 1 1] , [4 5 6] )
    // e = [ 0. 0.25 0.4]
    //
    // // Plot the function
    // a=2;
    // b=5;
    // scf();
    // x = linspace(1,6,1000);
    // y = distfun_unifcdf(x , a, b);
    // plot(x,y);
    // xtitle("Uniform CDF","x","$P(X\leq x)$");
    //
    // // See upper tail
    // p = distfun_unifcdf ( 1.7, 1., 2. )
    // q = distfun_unifcdf ( 1.7, 1., 2. , %f )
    // p+q
    //
    // Authors
    //   Copyright (C) 2012 - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_unifcdf" , rhs , 3:4 )
    apifun_checklhs ( "distfun_unifcdf" , lhs , 0:1 )
    //
    x = varargin ( 1 )
    a = varargin ( 2 )
    b = varargin ( 3 )
    lowertail = apifun_argindefault ( varargin , 4 , %t )
    //
    apifun_checktype ( "distfun_unifcdf" , x , "x" , 1 , "constant" )
    apifun_checktype ( "distfun_unifcdf" , a , "a" , 2 , "constant" )
    apifun_checktype ( "distfun_unifcdf" , b , "b" , 3 , "constant" )
    apifun_checktype ( "distfun_unifcdf" , lowertail , "lowertail" , 4 , "boolean" )
    //
    // Check size : OK
    apifun_checkscalar ( "distfun_unifcdf" , lowertail , "lowertail" , 4 )
    //
    [ x , a , b ] = apifun_expandvar ( x , a , b )
    //
    // Check content
    apifun_checkgreq ( "distfun_unifcdf" , b , "b" , 3 , a )
    //
    if ( x == [] ) then 
        p = []
        return
    end
    p=distfun_cdfunif(x,a,b,lowertail)
endfunction


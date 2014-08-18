// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function p = distfun_unidcdf ( varargin )
    //   Uniform Discrete CDF
    //
    // Calling Sequence
    //   p = distfun_unidcdf ( x , N )
    //   p = distfun_unidcdf ( x , N , lowertail )
    //
    // Parameters
    //   x : a matrix of doubles, the outcome
    //   N : a matrix of doubles, the upper bound (with N>=1)
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   p : a matrix of doubles, the probability.
    //
    // Description
    //   Computes the cumulated probability distribution function of 
    //   the Uniform Discrete distribution function.
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
    // 0, \textrm{ if } x\leq 1, \\
    // \frac{\lfloor x\rfloor}{N}, \textrm{ if } x\in[1,N], \\
    // 1, \textrm{ if } x\geq N.
    // \end{array}
    // \right.
    //\end{eqnarray}
    //</latex>
    //
    // Examples
    // // Test expanded arguments
    // c = distfun_unidcdf ( [1 2 3] , [4 5 6] )
    // e = [ 0. 0.25 0.4]
    //
    // // Plot the function
    // N=5;
    // scf();
    // x = 1:N;
    // y = distfun_unidcdf(x , N);
    // plot(x,y,"ro-");
    // h.children.data_bounds(:,2)=[0;1];
    // xtitle("Uniform Discrete CDF (N=5)","x","$P(X\leq x)$");
    //
    // // See upper tail
    // p = distfun_unidcdf ( 3, 7 )
    // q = distfun_unidcdf ( 3, 7 , %f )
    // p+q
    //
    // Authors
    //   Copyright (C) 2014 - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_unidcdf" , rhs , 2:3 )
    apifun_checklhs ( "distfun_unidcdf" , lhs , 0:1 )
    //
    x = varargin ( 1 )
    N = varargin ( 2 )
    lowertail = apifun_argindefault ( varargin , 3 , %t )
    //
    apifun_checktype ( "distfun_unidcdf" , x , "x" , 1 , "constant" )
    apifun_checktype ( "distfun_unidcdf" , N , "N" , 2 , "constant" )
    apifun_checktype ( "distfun_unidcdf" , lowertail , "lowertail" , 3 , "boolean" )
    //
    // Check size : OK
    apifun_checkscalar ( "distfun_unidcdf" , lowertail , "lowertail" , 3 )
    //
    [ x , N ] = apifun_expandvar ( x , N )
    //
    // Check content
    apifun_checkflint("distfun_unidcdf" , x , "x" , 1)
    apifun_checkgreq ( "distfun_unidcdf" , x , "x" , 1 , 1 )
    apifun_checkloweq ( "distfun_unidcdf" , x , "x" , 1 , N )
    apifun_checkgreq ( "distfun_unidcdf" , N , "N" , 2 , 1 )
    //
    if ( x == [] ) then 
        p = []
        return
    end
    p=(floor(x))./N
    p(x<1)=0
    p(x>N)=1
    if (~lowertail) then
        p=1-p
    end
endfunction


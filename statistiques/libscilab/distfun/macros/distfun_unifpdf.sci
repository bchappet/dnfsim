// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function y = distfun_unifpdf ( varargin )
    //   Uniform PDF
    //
    // Calling Sequence
    //   y = distfun_unifpdf ( x , a , b )
    //
    // Parameters
    //   x : a matrix of doubles, the outcome
    //   a : a matrix of doubles, the lower bound
    //   b : a matrix of doubles, the upper bound (with a<=b)
    //   y : a matrix of doubles, the density.
    //
    // Description
    //   Computes the probability distribution function of 
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
    // 0, \textrm{ if } x< a, \\
    // \frac{1}{b-a}, \textrm{ if } x\in[a,b], \\
    // 1, \textrm{ if } x> b.
    // \end{array}
    // \right.
    //\end{eqnarray}
    //</latex>
    //
    // Examples
    // // Test expanded arguments
    // c = distfun_unifpdf ( [1 2 3] , [1 1 1] , [4 5 6] )
    // e = [ 1/3 0.25 0.2]
    //
    // // Plot the function
    // a=2;
    // b=5;
    // scf();
    // x = linspace(1,6,1000);
    // y = distfun_unifpdf(x , a, b);
    // plot(x,y);
    // xtitle("Uniform PDF","x","y");
    //
    // Authors
    //   Copyright (C) 2012 - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_unifpdf" , rhs , 3 )
    apifun_checklhs ( "distfun_unifpdf" , lhs , 0:1 )
    //
    x = varargin ( 1 )
    a = varargin ( 2 )
    b = varargin ( 3 )
    //
    apifun_checktype ( "distfun_unifpdf" , x , "x" , 1 , "constant" )
    apifun_checktype ( "distfun_unifpdf" , a , "a" , 2 , "constant" )
    apifun_checktype ( "distfun_unifpdf" , b , "b" , 3 , "constant" )
    //
    // Check size : OK
    //
    [ x , a , b ] = apifun_expandvar ( x , a , b )
    //
    // Check content
    apifun_checkgreq ( "distfun_unifpdf" , b , "b" , 3 , a )
    if ( x == [] ) then 
        y = []
        return
    end
    //
    y = distfun_pdfunif(x,a,b)
endfunction


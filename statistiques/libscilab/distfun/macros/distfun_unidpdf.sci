// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function y = distfun_unidpdf ( varargin )
    //   Uniform Discrete PDF
    //
    // Calling Sequence
    //   y = distfun_unidpdf ( x , N )
    //
    // Parameters
    //   x : a matrix of doubles, the outcome, in the set {1,...,N}
    //   N : a matrix of doubles, integer value, the integer upper bound (with N>=1)
    //   y : a matrix of doubles, the density.
    //
    // Description
    //   Computes the probability distribution function of 
    //   the Uniform discrete distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    //   The function definition is:
    //
    // <latex>
    // \begin{eqnarray}
    // f(x,N) = 
    //  \left\{
    //  \begin{array}{l}
    //  0, \textrm{ if } x< 1, \\
    //  \frac{1}{N}, \textrm{ if } x\in[1,N], \\
    //  1, \textrm{ if } x> N.
    //  \end{array}
    //  \right.
    // \end{eqnarray}
    // </latex>
    //
    // Examples
    // // Test expanded arguments
    // c = distfun_unidpdf ( 1:9 , 9 )
    // e = ones(1,9)./9
    //
    // // Plot the function
    // N=5;
    // scf();
    // x = 1:N;
    // y = distfun_unidpdf(x , N);
    // plot(x,y,"ro-");
    // h.children.data_bounds(:,2)=[0;1];
    // xtitle("Uniform Discrete PDF (N=5)","x","y");
    //
    // Authors
    //   Copyright (C) 2014 - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_unidpdf" , rhs , 2 )
    apifun_checklhs ( "distfun_unidpdf" , lhs , 0:1 )
    //
    x = varargin ( 1 )
    N = varargin ( 2 )
    //
    apifun_checktype ( "distfun_unidpdf" , x , "x" , 1 , "constant" )
    apifun_checktype ( "distfun_unidpdf" , N , "N" , 2 , "constant" )
    //
    // Check size : OK
    //
    [ x , N ] = apifun_expandvar ( x , N )
    //
    // Check content
    apifun_checkflint("distfun_unidpdf" , x , "x" , 1)
    apifun_checkgreq ( "distfun_unidpdf" , x , "x" , 1 , 1 )
    apifun_checkloweq ( "distfun_unidpdf" , x , "x" , 1 , N )
    apifun_checkgreq ( "distfun_unidpdf" , N , "N" , 2 , 1 )
    apifun_checkflint("distfun_unidpdf" , N , "N" , 2)
    if ( x == [] ) then 
        y = []
        return
    end
    //
    y=ones(x)./N
    y(x<1)=0
    y(x>N)=0
endfunction


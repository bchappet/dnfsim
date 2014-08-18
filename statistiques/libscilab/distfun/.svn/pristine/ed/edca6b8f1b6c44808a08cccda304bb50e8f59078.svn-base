// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function y = distfun_logupdf ( varargin )
    // LogUniform PDF
    //
    // Calling Sequence
    //   y = distfun_logupdf ( x , a , b )
    //
    // Parameters
    // x: a matrix of doubles
    // a: a matrix of doubles, the minimum of the underlying uniform variable. 
    // b: a matrix of doubles, the maximum of the underlying uniform variable. b>a.
    // y: a matrix of doubles, the density
    //
    // Description
    //   This function computes the LogUniform PDF.
    //
    //   Any scalar input argument is expanded to a matrix of doubles of the same size 
    //   as the other input arguments.
    //
    // Any optional input argument equal to the empty matrix will be set to its
    // default value.
    //
    // The LogUniform distribution with parameters a and b has density
    //
    //<latex>
    //\begin{eqnarray}
    //f(x) = \frac{1}{x} \frac{1}{b-a}
    //\end{eqnarray}
    //</latex>
    //
    // if x in [exp(a),exp(b)].
    //
    // Examples
    // y=distfun_logupdf(exp(4),3,5)
    //
    // // Compare random numbers with PDF
    // a=2;
    // b=3.5;
    // N=10000;
    // x=linspace(exp(a),exp(b),1000);
    // y=distfun_logupdf(x,a,b);
    // r=distfun_logurnd(a,b,N,1);
    // scf();
    // histplot(20,r)
    // plot(x,y)
    // xtitle("Log-Uniform","X","");
    // legend(["Data","PDF"]);
    //
    // Authors
    // Copyright (C) 2013 - Michael Baudin

    [lhs, rhs] = argn()
    apifun_checkrhs ( "distfun_logupdf" , rhs , 3 )
    apifun_checklhs ( "distfun_logupdf" , lhs , 0:1 )
    //
    x = varargin(1)
    a = varargin(2)
    b = varargin(3)
    //
    // Check type
    apifun_checktype ( "distfun_logupdf" , x , "x" , 1 , "constant" )
    apifun_checktype ( "distfun_logupdf" , a , "a" , 2 , "constant" )
    apifun_checktype ( "distfun_logupdf" , b , "b" , 3 , "constant" )
    //
    // Check size : OK
    [ x , a , b ] = apifun_expandvar ( x , a , b )
    //
    // Check content
    apifun_checkgreq ( "distfun_logucdf" , b , "b" , 2 , a )  
    //
    if (x==[]) then
        y=[]
        return
    end
    //
    // Proceed ...
    i=find(x<exp(b) & x>exp(a))
    y=zeros(x)
    y(i)=1 ./(b(i)-a(i))./x(i)
endfunction


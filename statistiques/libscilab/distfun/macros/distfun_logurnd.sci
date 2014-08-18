// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_logurnd ( varargin )
    // LogUniform random numbers
    //
    // Calling Sequence
    //   x = distfun_logurnd ( a , b )
    //   x = distfun_logurnd ( a , b , [m,n] )
    //   x = distfun_logurnd ( a , b , m , n )
    //
    // Parameters
    //   a: a matrix of doubles, the minimum of the underlying uniform variable. 
    //   b: a matrix of doubles, the maximum of the underlying uniform variable. b>a.
    //   m : a 1-by-1 matrix of floating point integers, the number of rows of x
    //   n : a 1-by-1 matrix of floating point integers, the number of columns of x
    //   x: a matrix of doubles, the positive random numbers.
    //
    // Description
    //   Generates random variables from the LogUniform distribution function.
    //
    //   Any scalar input argument is expanded to a matrix of doubles of 
	//   the same size as the other input arguments.
    // 
    // Examples
    // // Test both a and b expanded
    // computed = distfun_logurnd(1:6,2:7)
    //
    // // Test a expansion
    // computed = distfun_logurnd(1:6,7)
    //
    // // Test b expansion
    // computed = distfun_logurnd(0,1:6)
    //
    // // Test with v
    // computed = distfun_logurnd(0,1,[3 2])
    //
    // // Test with m, n
    // computed = distfun_logurnd(0,1,3,2)
    //
    // // Make a plot of the actual distribution of the numbers
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
    //   Copyright (C) 2013 - Michael Baudin

    // Load Internals lib
    path = distfun_getpath (  )
    internallib  = lib(fullfile(path,"macros","internals"))

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_logurnd" , rhs , 2:4 )
    apifun_checklhs ( "distfun_logurnd" , lhs , 0:1 )
    //
    a = varargin(1)
    b = varargin(2)
    //
    // Check type
    apifun_checktype ( "distfun_logurnd" , a , "a" , 1 , "constant" )
    apifun_checktype ( "distfun_logurnd" , b , "b" , 2 , "constant" )
    if ( rhs == 3 ) then
        v = varargin(3)
    end
    if ( rhs == 4 ) then
        m = varargin(3)
        n = varargin(4)
    end
    //
    // Check size : OK
    //
    // Check v, m, n
    distfun_checkvmn ( "distfun_logurnd" , 3 , varargin(3:$) )
    //
    [a,b] = apifun_expandfromsize ( 2 , varargin(1:$) )
    //
    // Check content
    apifun_checkgreq ( "distfun_logurnd" , b , "b" , 2 , a )  
    //
    if (a==[]) then
        x=[]
        return
    end
    m = size(a,"r")
    n = size(a,"c")
    u=distfun_unifrnd(a,b,m,n)
    x=exp(u)
endfunction


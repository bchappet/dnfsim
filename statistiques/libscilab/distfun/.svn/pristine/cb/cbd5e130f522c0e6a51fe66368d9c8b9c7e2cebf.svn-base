// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function p = distfun_logucdf ( varargin )
    // LogUniform CDF
    //
    // Calling Sequence
    //   p = distfun_logucdf ( x , a , b )
    //   p = distfun_logucdf ( x , a , b , lowertail )
    //
    // Parameters
    // x: a matrix of doubles
    // a: a matrix of doubles, the minimum of the underlying uniform variable. 
    // b: a matrix of doubles, the maximum of the underlying uniform variable. b>a.
    // lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    // p: a matrix of doubles, the probability
    //
    // Description
    //   This function computes the LogUniform Cumulated Density Function.
    //
    //   Any scalar input argument is expanded to a matrix of doubles of the same size 
    //   as the other input arguments.
    //
    // Any optional input argument equal to the empty matrix will be set to its
    // default value.
    //
    // Examples
    // p=distfun_logucdf(exp(4),3,10)
    // pexpected = 0.4087797
    //
    // a=2;
    // b=3.5;
    // N=10000;
    // x=linspace(exp(a),exp(b),1000);
    // y=distfun_logucdf(x,a,b);
    // r=distfun_logurnd(a,b,N,1);
    // r=gsort(r,"g","i");
    // scf();
    // plot(r,(1:N)./N,"r-");
    // plot(x,y,"b-")
    // xtitle("Log-Uniform","X","");
    // legend(["Data","CDF"]);
    //
    // // Check upper tail
    // p=distfun_logucdf(exp(4),3,4,%f) // 0.8571429
    //
    // Authors
    // Copyright (C) 2013 - Michael Baudin

    [lhs, rhs] = argn()
    apifun_checkrhs ( "distfun_logucdf" , rhs , 3:4 )
    apifun_checklhs ( "distfun_logucdf" , lhs , 0:1 )
    //
    x = varargin(1)
    a = varargin(2)
    b = varargin(3)
    lowertail = apifun_argindefault ( varargin , 4 , %t )
    //
    // Check type
    apifun_checktype ( "distfun_logucdf" , x , "x" , 1 , "constant" )
    apifun_checktype ( "distfun_logucdf" , a , "a" , 2 , "constant" )
    apifun_checktype ( "distfun_logucdf" , b , "b" , 3 , "constant" )
    apifun_checktype ( "distfun_logucdf" , lowertail , "lowertail" , 4 , "boolean" )
    //
    // Check size : OK
    //
    [ x , a , b ] = apifun_expandvar ( x , a , b )
    // Check content
    apifun_checkgreq ( "distfun_logucdf" , b , "b" , 2 , a )  
    //
    // Proceed ...
    //
    if (x==[]) then
        p=[]
        return
    end
    //
    if (lowertail) then
        p=zeros(x)
        i=find(x<=exp(b) & x>=exp(a))
        p(i)=(log(x(i))-a(i))./(b(i)-a(i))
        p(x>exp(b))=1
    else
        p=zeros(x)
        i=find(x<=exp(b) & x>=exp(a))
        p(i)=(b(i)-log(x(i)))./(b(i)-a(i))
        p(x<exp(a))=1
    end
endfunction

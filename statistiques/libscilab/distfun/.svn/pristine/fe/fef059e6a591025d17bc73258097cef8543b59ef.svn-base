// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
//

function p = distfun_kscdf(varargin)
    // Kolmogorov-Smirnov CDF
    //
    // Calling Sequence
    //   p = distfun_kscdf(x,N)
    //   p = distfun_kscdf(x,N,lowertail)
    //
    // Parameters
    //   x : a matrix of doubles, the outcome. x in [0,1]
    //   N : a matrix of doubles , the number of observations. N belongs to the set {1,2,3,4,.......,2147483647}
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   p : a matrix of doubles, the probability.
    //
    // Description
    //   Computes the cumulative distribution function of 
    //   the Kolmogorov-Smirnov distribution function.
    //
    // Computes
    //
    // <latex>
    // \begin{eqnarray}
    // K(n,x) = Prob(D_n < x),
    // \end{eqnarray}
    // </latex>
    //
    // where
    //
    // <latex>
    // \begin{eqnarray}
    // D_n = max_{1\leq i\leq n} \left( u_i-\frac{i-1}{n} , \frac{i}{n}-u_i \right)
    // \end{eqnarray}
    // </latex>
    //
    //   with
    //
    // <latex>
    // \begin{eqnarray}
    // 0\leq u_1<u_2,...<u_n \leq 1
    // \end{eqnarray}
    // </latex>
    //
    //   a set of n independent uniform [0,1)
    //   random variables sorted into increasing order.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Examples
    // computed = distfun_kscdf(0.274,10)
    // expected = .6284796154565043
    //
    // //Plot the function
    // scf();
    // x = linspace(0.,0.5);
    // p1=distfun_kscdf(x,5);
    // p2=distfun_kscdf(x,10);
    // p3=distfun_kscdf(x,20);
    // plot(x,p1,"r-");
    // plot(x,p2,"b-");
    // plot(x,p3,"g-");
    // legend(["N=5","N=10","N=20"],2);
    // xtitle("Kolmogorov Smirnov CDF","x","$P(D_n\leq x)$")
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Kolmogorov%E2%80%93Smirnov_test
    // Evaluating Kolmogorov’s Distribution, George Marsaglia, Wai Wan Tsang, Journal of Statistical Software, Vol. 8, Issue 18, Nov 2003
    //
    // Authors
    // Copyright (C) 2014 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_kscdf",rhs,2:3)
    apifun_checklhs("distfun_kscdf",lhs,0:1)

    x = varargin(1)
    N = varargin(2)
    lowertail = apifun_argindefault(varargin,3,%t)
    //
    // Check type
    apifun_checktype("distfun_kscdf",x,"x",1,"constant")
    apifun_checktype("distfun_kscdf",N,"N",2,"constant")
    apifun_checktype("distfun_kscdf",lowertail,"lowertail",3,"boolean")
    //
    // Check size
    apifun_checkscalar("distfun_kscdf",lowertail,"lowertail",3)
    //
    // Check content
    apifun_checkrange("distfun_kscdf",x,"x",1,0,1)
    apifun_checkgreq("distfun_kscdf",N,"N",2,1)
    apifun_checkflint("distfun_kscdf",N,"N",2)
    apifun_checkloweq("distfun_kscdf",N,"N",2,2147483647)

    if (x == []) then
        p=[]
        return
    end
    [x,N] = apifun_expandvar(x,N)
    p = distfun_cdfks(x,N,lowertail)
endfunction

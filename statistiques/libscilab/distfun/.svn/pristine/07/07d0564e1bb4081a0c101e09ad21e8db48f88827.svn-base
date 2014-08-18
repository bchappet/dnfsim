// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function y = distfun_hygepdf(varargin)
    // Hypergeometric PDF
    //
    // Calling Sequence
    //   y = distfun_hygepdf(x,M,k,N)
    //   
    // Parameters
    //   x : a matrix of doubles, the number of successful draws in the experiment. x belongs to the set [0,min(k,N)]
    //   M : a matrix of doubles, the total size of the population. M belongs to the set {0,1,2,...}
    //   k : a matrix of doubles, the number of successful states in the population. k belongs to the set {0,1,2,...,M}
    //   N : a matrix of doubles, the total number of draws in the experiment. N belongs to the set {0,1,2,...,M}
    //   y : a matrix of doubles, the probability density.
    //
    // Description
    //   Computes the probability distribution function of 
    //   the Hypergeometric distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    //   The function definition is:
    //
    //<latex>
    //\begin{eqnarray}
    //f(x,M,k,N) = \frac{\binom{k}{x} \binom{M-k}{N-x}}{\binom{M}{N}}
    //\end{eqnarray}
    //</latex>
    //
    // where <latex>\binom{M-k}{N-x}</latex> is zero if <literal>N-x>M-k</literal>.
    //
    // From the definition of the PDF, the input arguments must be so that:
    // x<=N, x<=k, k<=M and N<=M.
    //
    // If one of these conditions is not satisfied, 
    // an error is generated.
    //
    // If x<N-M+k, then the probability is zero. 
    //
    // Analysis of the random variable. 
    //
    // Consider an urn with M balls. 
    // In this urn, k balls are successes, and M-k balls 
    // are failures. 
    // We randomly pick a ball in this urn, see if 
    // it is a success or a failure, and 
    // replace the ball in the urn. 
    // We repeat this experiment N times and let X be 
    // the number of successful draws. 
    // Then X has an hypergeometric distribution, 
    // with parameters M, k and N.
    //
    // Compatibility note. 
    // 
    // This function is compatible with Matlab, but 
    // not compatible with R.
    // The calling sequence
    //
    // distfun_hygepdf(x,M,k,N)
    // 
    // corresponds to the R calling sequence:
    //
    // dhyper(x,k,M-k,N)
    //
    // Range of the input
    //
    // It might happen that the user has a input argument x which 
    // is either too small (e.g. x=-10) or too large (e.g. x=M+1). 
    // In this case, the distfun_hygepdf function generates an error. 
    // This situation may reveal a bug in the data. 
    // This feature may be a problem for some users 
    // which would rather want to compute a zero 
    // probability. 
    // In this case, it may be required 
    // to prepare x for distfun_hygepdf. 
    // See the "Restrict x to the acceptable range." 
    // example below.
    // The same situation can happen with distfun_hygecdf.
    //
    // Examples
    // // Tests with all the arguments scalar
    // computed = distfun_hygepdf(20,80,50,30)
    // expected = 0.1596136
    //
    // // Test with x expanded
    // computed = distfun_hygepdf([20 17],80,50,30)
    // expected = [0.1596136 0.1329349]
    //
    // // Test with M expanded
    // computed = distfun_hygepdf(20,[80 100],50,30)
    // expected = [0.1596136 0.0164823]
    //
    // // Test with x,N expanded
    // computed = distfun_hygepdf([20 17],80,[50 60],30)
    // expected = [0.1596136 0.0033836]
    //
    // // Test with all the arguments expanded
    // copmuted = distfun_hygepdf([20 17 15],[100 80 90],[50 60 70],[30 20 18])
    // expected = [0.0164823 0.1248637 0.2170354]
    //
    // // Plot the function
    // scf();
    // x = 0:30;
    // y = distfun_hygepdf(x,80,50,30);
    // plot(x,y,"ro-");
    // xtitle("Hypergeometric PDF","x","P(x)");
    // legend("M=80,k=50,N=30");
    //
    // // If x is not in the range, then an error is generated.
    // // Generates an error:
    // // distfun_hygepdf(-20,80,50,30)
    // // distfun_hygepdf(40,80,50,30)
    //
    // // Restrict x to the acceptable range.
    // M=80;
    // k=50;
    // N=30;
    // x=(-10:100);
    // x=min(x,N,k);
    // x=max(x,0);
    // p=distfun_hygepdf(x,M,k,N);
    // plot(x,p,"o");
    // xtitle("Hypergeometric PDF","x","P(X=x)");
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Hypergeometric_distribution
    //
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
    // Copyright (C) 2012 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_hygepdf",rhs,4)
    apifun_checklhs("distfun_hygepdf",lhs,0:1)
    x = varargin(1)
    M = varargin(2)
    k = varargin(3)
    N = varargin(4)
    //
    // Check type
    apifun_checktype("distfun_hygepdf",x,"x",1,"constant")
    apifun_checktype("distfun_hygepdf",M,"M",2,"constant")
    apifun_checktype("distfun_hygepdf",k,"k",3,"constant")
    apifun_checktype("distfun_hygepdf",N,"N",4,"constant")

    //
    // Check content
    apifun_checkgreq("distfun_hygepdf",x,"x",1,0)
    apifun_checkgreq("distfun_hygepdf",M,"M",2,0)
    apifun_checkgreq("distfun_hygepdf",k,"k",3,0)
    apifun_checkgreq("distfun_hygepdf",N,"N",4,0)
    //
    apifun_checkflint("distfun_hygepdf",x,"x",1)
    apifun_checkflint("distfun_hygepdf",M,"M",2)
    apifun_checkflint("distfun_hygepdf",k,"k",3)
    apifun_checkflint("distfun_hygepdf",N,"N",4)
    //
    [x,M,k,N] = apifun_expandvar(x,M,k,N)
    //
    y=distfun_pdfhyge(x,M,k,N)
endfunction

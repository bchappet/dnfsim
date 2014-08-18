// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2012 - Prateek Papriwal
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function y = distfun_binopdf(varargin)
    // Binomial PDF
    //
    // Calling Sequence
    // y = distfun_binopdf(x,N,pr) 
    //
    // Parameters
    //   x : a matrix of doubles, the number of successes. x belongs to the set {0,1,2,3,...,N}
    //   N : a matrix of doubles , the total number of binomial trials. N belongs to the set {1,2,3,4,.......}
    //   pr : a matrix of doubles, the probability of success in a Bernoulli trial. pr in [0,1].
    //   y : a matrix of doubles, the probability density.
    //
    // Description
    //   Computes the probability distribution function of 
    //   the Binomial distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    //    The function definition is:
    //
    //<latex>
    //\begin{eqnarray}
    //f(x,N,pr) = \binom{N}{x} p_r^x (1-p_r)^{N-x}
    //\end{eqnarray}
    //</latex>
    //
    // Analysis of the random variable. 
    //
    // Assume that we perform a Bernoulli trial, where 
    // the probability of success is pr and the probability 
    // of failure is 1-pr. 
    // Each time we make the experiment, we replace the 
    // ball in the urn, i.e. this is an experiment with 
    // replacement. 
    // We repeat this experiment N times. 
    // Let X be the number of successes. 
    // Then the random variable X has a binomial distribution with parameters 
    // N and pr.
    //
    // Instead, when the sampling is done without replacement, 
    // the hypergeometric distribution must be 
    // considered. 
    // However, when X is much smaller than N, then 
    // the binomial distribtion is a good approximation. 
    //
    //Examples
    // // Check with x scalar, N scalar, pr scalar
    //y = distfun_binopdf(0,200,0.02)
    //expected = 0.0175879
    //
    // // Check with expanded x
    //computed = distfun_binopdf([5 15],100,0.05)
    //expected = [0.1800178 0.0000988]
    //
    // // Check with expanded N
    //computed = distfun_binopdf(5,[50 100],0.05)
    //expected = [0.0658406 0.1800178]
    //
    // // Check with two arguments expanded 
    //computed = distfun_binopdf([5 10],[50 100],0.05)
    //expected = [0.0658406 0.0167159]
    //
    // // Check with all the arguments expanded
    //computed = distfun_binopdf([5 10],[50 100],[0.05 0.1])
    //expected = [0.0658406 0.1318653]
    //
    // // Check y = distfun_binopdf(x,N,pr) with large value of N
    // computed = distfun_binopdf(2,1000,0.5)
    // expected = 4.66165177442386078D-296
    //
    // // Plot the function
    // scf();
    // N1 = 20;
    // x = 0:N1;
    // y1 = distfun_binopdf(x,N1,0.5);
    // plot(x,y1,"bo-")
    // N2 = 20;
    // x = 0:N2;
    // y2 = distfun_binopdf(x,N2,0.7);
    // plot(x,y2,"go-")
    // N3 = 40;
    // x = 0:N3;
    // y3 = distfun_binopdf(x,N3,0.5);
    // plot(x,y3,"ro-")
    // legend(["pr=0.5, N=20","pr=0.7, N=20","pr=0.5, N=40"]);
    // xtitle("Binomial PDF","x","P(X=x)")
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Binomial_distribution
    // http://forge.scilab.org/index.php/p/specfun/source/tree/HEAD/macros/specfun_nchoosek.sci
    // Boost C++ librairies, Binomial Coefficients, 2006 , 2007, 2008, 2009, 2010 John Maddock, Paul A. Bristow, Hubert Holin, Xiaogang Zhang, Bruno Lalande, Johan Råde, Gautam Sewani and Thijs van den Berg
    //
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
    // Copyright (C) 2012 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_binopdf",rhs,3)
    apifun_checklhs("distfun_binopdf",lhs,0:1)
    //
    x = varargin(1)
    N = varargin(2)
    pr = varargin(3)
    //
    // Check type
    apifun_checktype("distfun_binopdf",x,"x",1,"constant")
    apifun_checktype("distfun_binopdf",N,"N",2,"constant")
    apifun_checktype("distfun_binopdf",pr,"pr",3,"constant")
    //
    // Check size : nothing to do
    //
    [x,N,pr] = apifun_expandvar(x,N,pr)
    if (x == []) then
        y=[]
        return
    end
    // Check content
    apifun_checkflint("distfun_binopdf",x,"x",1)
    apifun_checkgreq("distfun_binopdf",x,"x",1,0)
    apifun_checkloweq("distfun_binopdf",x,"x",1,N)
    apifun_checkflint("distfun_binopdf",N,"N",2)
    apifun_checkgreq("distfun_binopdf",N,"N",2,1)
    apifun_checkgreq("distfun_binopdf",pr,"pr",3,0)
    apifun_checkloweq("distfun_binopdf",pr,"pr",3,1)
    //
    y = distfun_pdfbino(x,N,pr)
endfunction

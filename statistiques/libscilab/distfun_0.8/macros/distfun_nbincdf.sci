// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
//

function p = distfun_nbincdf(varargin)
    // Negative Binomial CDF
    //
    // Calling Sequence
    //   p = distfun_nbincdf(x,R,P)
    //   p = distfun_nbincdf(x,R,P,lowertail)
    //
    // Parameters
    //   x: a matrix of doubles, the extra trials for R successes, in the set {0,1,2,3,...}.
    //   R : a matrix of doubles, the number of successes. R belongs to the set {0,1,2,3,4,.......}
    //   P : a matrix of doubles, the probability of getting success in a Bernoulli trial. P in [0,1].
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   p : a matrix of doubles, the probability.
    //
    // Description
    //   Computes the cumulative distribution function of 
    //   the Negative Binomial distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Examples
    // // Check with x scalar, R scalar, P scalar
    // computed = distfun_nbincdf(10,4,0.5)
    // expected = 0.9713135
    //
    // // Check with expanded x
    // computed = distfun_nbincdf([5 10],4,0.5)
    // expected = [0.7460937    0.9713135]
    //
    // // Check with expanded R
    // computed = distfun_nbincdf(5,[5 10],0.5)
    // expected = [0.6230469    0.1508789]
    //
    // // Check with expanded P
    // computed = distfun_nbincdf(5,4,[0.5 0.7])
    // expected = [0.7460937    0.9747052]
    //
    // // Check with two arguments expanded 
    // computed = distfun_nbincdf([5 10],[5 7],0.5)
    // expected = [0.6230469    0.8338470]
    //
    // // Check with all the arguments expanded
    // computed = distfun_nbincdf([5 10],[4 7],[0.5 0.6])
    // expected = [0.7460937    0.9651873]
    //
    // //Plot the function
    // scf();
    // x = (0:20)';
    // p1=distfun_nbincdf(x,20,0.5);
    // p2=distfun_nbincdf(x,20,0.7);
    // p3=distfun_nbincdf(x,40,0.5);
    // legendspec=["P=0.5, N=20","P=0.7, N=20","P=0.5, N=40"];
    // distfun_plotintcdf(x,[p1,p2,p3],["b" "g" "r"],legendspec);
    // xtitle("Negative Binomial CDF")
    //
    // //check upper tail
    //p = distfun_nbincdf(3,5,0.5)
    //lt_expected = 0.3632813
    //
    //q = distfun_nbincdf(3,5,0.5,%f)
    //ut_expected = 0.6367187
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Binomial_distribution
    //
    // Authors
    // Copyright (C) 2014 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_nbincdf",rhs,3:4)
    apifun_checklhs("distfun_nbincdf",lhs,0:1)

    x = varargin(1)
    R = varargin(2)
    P = varargin(3)
    lowertail = apifun_argindefault(varargin,4,%t)
    //
    // Check type
    apifun_checktype("distfun_nbincdf",x,"x",1,"constant")
    apifun_checktype("distfun_nbincdf",R,"R",2,"constant")
    apifun_checktype("distfun_nbincdf",P,"P",3,"constant")
    apifun_checktype("distfun_nbincdf",lowertail,"lowertail",4,"boolean")
    //
    // Check size
    apifun_checkscalar("distfun_nbincdf",lowertail,"lowertail",4)
    //
    // Check content
    apifun_checkflint("distfun_nbincdf",x,"x",1)
    apifun_checkgreq("distfun_nbincdf",x,"x",1,0)
    apifun_checkflint("distfun_nbincdf",R,"R",2)
    apifun_checkgreq("distfun_nbincdf",R,"R",2,1)
    apifun_checkrange("distfun_nbincdf",P,"P",3,0.,1.)

    if (x == []) then
        p=[]
        return
    end
    [ x , R , P ] = apifun_expandvar ( x , R , P )
    //
    p=distfun_cdfnbn(x,R,P,lowertail)
endfunction

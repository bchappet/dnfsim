// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
//

function p = distfun_binocdf(varargin)
    // Binomial CDF
    //
    // Calling Sequence
    //   p = distfun_binocdf(x,N,pr)
    //   p = distfun_binocdf(x,N,pr,lowertail)
    //
    // Parameters
    //   x : a matrix of doubles, the number of successes. x belongs to the set {0,1,2,3,...,N}
    //   N : a matrix of doubles , the total number of binomial trials . N belongs to the set {1,2,3,4,.......}
    //   pr : a matrix of doubles,  the probability of success in a Bernoulli trial. pr in [0,1].
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   p : a matrix of doubles, the probability.
    //
    // Description
    //   Computes the cumulative distribution function of 
    //   the Binomial distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Examples
    // // Check with x scalar, N scalar, pr scalar
    // computed = distfun_binocdf(100,162,0.5)
    // expected = 0.9989567
    //
    // // Check with expanded x
    // computed = distfun_binocdf([5 15],100,0.05)
    // expected = [0.6159991 0.9999629]
    //
    // // Check with expanded N
    // computed = distfun_binocdf(5,[50 100],0.05)
    // expected = [0.9622238 0.6159991]
    //
    // // Check with expanded pr
    // computed = distfun_binocdf(5,50,[0.05 0.1])
    // expected = [0.9622238 0.6161230]
    //
    // // Check with two arguments expanded 
    // computed = distfun_binocdf([5 10],[50 100],0.05)
    // expected = [0.9622238 0.9885276]
    //
    // // Check with all the arguments expanded
    // computed = distfun_binocdf([5 10],[50 100],[0.05 0.1])
    // expected = [0.9622238 0.5831555]
    //
    // //Plot the function
    // scf();
	// x = (0:20)';
	// p1=distfun_binocdf(x,20,0.5);
	// p2=distfun_binocdf(x,20,0.7);
	// p3=distfun_binocdf(x,40,0.5);
	// legendspec=["pr=0.5, N=20","pr=0.7, N=20","pr=0.5, N=40"];
	// distfun_plotintcdf(x,[p1,p2,p3],["b" "g" "r"],legendspec);
	// xtitle("Binomial CDF")
    //
    // //check upper tail
    //p = distfun_binocdf(3,10,0.1)
    //lt_expected = 0.9872048
    //
    //q = distfun_binocdf(3,10,0.1,%f)
    //ut_expected = 0.0127952
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Binomial_distribution
    //
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
	// Copyright (C) 2012 - Michael Baudin
    
    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_binocdf",rhs,3:4)
    apifun_checklhs("distfun_binocdf",lhs,0:1)
    
    x = varargin(1)
    N = varargin(2)
    pr = varargin(3)
    lowertail = apifun_argindefault(varargin,4,%t)
    //
    // Check type
    apifun_checktype("distfun_binocdf",x,"x",1,"constant")
    apifun_checktype("distfun_binocdf",N,"N",2,"constant")
    apifun_checktype("distfun_binocdf",pr,"pr",3,"constant")
    apifun_checktype("distfun_binocdf",lowertail,"lowertail",4,"boolean")
    //
	// Check size
    apifun_checkscalar("distfun_binocdf",lowertail,"lowertail",4)
    //
    // Check content
    apifun_checkflint("distfun_binocdf",x,"x",1)
    apifun_checkgreq("distfun_binocdf",x,"x",1,0)
    apifun_checkloweq("distfun_binocdf",x,"x",1,N)
    apifun_checkgreq("distfun_binocdf",N,"N",2,1)
    apifun_checkflint("distfun_binocdf",N,"N",2)
    apifun_checkrange("distfun_binocdf",pr,"pr",3,0,1)
    
    if (x == []) then
        p=[]
        return
    end
    [x,N,pr] = apifun_expandvar(x,N,pr)
    p = distfun_cdfbino(x,N,pr,lowertail)
endfunction

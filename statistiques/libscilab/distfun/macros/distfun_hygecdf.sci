// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
//
function p = distfun_hygecdf(varargin)
    // Hypergeometric CDF
    //
    // Calling Sequence
    //   p = distfun_hygecdf(x,M,k,N)
    //   p = distfun_hygecdf(x,M,k,N,lowertail)
    //   
    // Parameters
    //   x : a matrix of doubles, the number of successful draws in the experiment. x belongs to the set [0,min(k,N)]
    //   M : a matrix of doubles, the total size of the population. M belongs to the set {0,1,2,3........}
    //   k : a matrix of doubles, the number of successful states in the population. k belongs to the set {0,1,2,3,.......M-1,M}
    //   N : a matrix of doubles, the total number of draws in the experiment. N belongs to the set {0,1,2,3.......M-1,M}
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   p : a matrix of doubles, the probability.
    //
    // Description
    //   Computes the cumulative distribution function of 
    //   the Hypergeometric distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Examples
    // // Tests with all the arguments scalar
    // computed = distfun_hygecdf(20,80,50,30)
    // expected = 0.7974774
    //
    // // Test with x expanded
    // computed = distfun_hygecdf([20 17],80,50,30)
    // expected = [0.7974774 0.2746181]
    //
    // // Test with M expanded
    // computed = distfun_hygecdf(20,[80 100],50,30)
    // expected = [0.7974774 0.9921915]
    //
    // // Test with x,N expanded
    // computed = distfun_hygecdf([20 17],80,[50 60],30)
    // expected = [0.7974774 0.0041404]
    //
    // // Test with all the arguments expanded
    // copmuted = distfun_hygecdf([20 17 15],[100 80 90],[50 60 70],[30 20 18])
    // expected = [0.9921915 0.9375322 0.8279598]
    //
    // // See upper tail
    // p = distfun_hygecdf(20,80,50,30)
    // lt_expected = 0.7974774
    // q = distfun_hygecdf(20,80,50,30,%f)
    // ut_expected = 0.2025226
    // p+q
    //
    // // Plot the function
    // scf();
    // x = (0:30)';
    // y = distfun_hygecdf(x,80,50,30);
    // distfun_plotintcdf(x,y);
    // xtitle("Hypergeometric CDF");
    // legend("M=80,k=50,N=30","in_upper_left");
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Hypergeometric_distribution
    //
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
    // Copyright (C) 2012 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_hygecdf",rhs,4:5)
    apifun_checklhs("distfun_hygecdf",lhs,0:1)
    x = varargin(1)
    M = varargin(2)
    k = varargin(3)
    N = varargin(4)
    lowertail = apifun_argindefault(varargin,5,%t)
    //
    // Check type
    apifun_checktype("distfun_hygecdf",x,"x",1,"constant")
    apifun_checktype("distfun_hygecdf",M,"M",2,"constant")
    apifun_checktype("distfun_hygecdf",k,"k",3,"constant")
    apifun_checktype("distfun_hygecdf",N,"N",4,"constant")
    apifun_checktype("distfun_hygecdf",lowertail,"lowertail",5,"boolean")
    //
    // Check size
    apifun_checkscalar("distfun_hygecdf",lowertail,"lowertail",5)
    //
    // Check content
    apifun_checkgreq("distfun_hygecdf",x,"x",1,0)
    apifun_checkgreq("distfun_hygecdf",M,"M",2,0)
    apifun_checkgreq("distfun_hygecdf",k,"k",3,0)
    apifun_checkgreq("distfun_hygecdf",N,"N",4,0)
    //
    apifun_checkflint("distfun_hygepdf",x,"x",1)
    apifun_checkflint("distfun_hygepdf",M,"M",2)
    apifun_checkflint("distfun_hygepdf",k,"k",3)
    apifun_checkflint("distfun_hygepdf",N,"N",4)
    //
    if (x == []) then
        p=[]
        return
    end
    //
    [x,M,k,N] = apifun_expandvar(x,M,k,N)
    //
    myloweq("distfun_hygepdf",x,"x",1,N) // x<=N	
    myloweq("distfun_hygepdf",x,"x",1,k) // x<=k
    myloweq("distfun_hygepdf",k,"k",2,M) // k<=M
    myloweq("distfun_hygepdf",N,"N",4,M) // N<=M
    //
    p=distfun_cdfhyge(x,M,k,N,lowertail)
endfunction
function myloweq( funname , var , varname , ivar , thr )
    // Workaround for bug http://forge.scilab.org/index.php/p/apifun/issues/867/
    // Caution:
    // This function assumes that var and thr are matrices with
    // same size.
    // Expand the arguments before calling it.
    if ( or ( var > thr ) ) then
        k = find ( var > thr ,1)
        errmsg = msprintf(gettext("%s: Wrong input argument %s at input #%d. Entry %s(%d) is equal to %s but should be lower than %s."),funname,varname,ivar,varname,k,string(var(k)),string(thr(k)));
        error(errmsg);
    end
endfunction

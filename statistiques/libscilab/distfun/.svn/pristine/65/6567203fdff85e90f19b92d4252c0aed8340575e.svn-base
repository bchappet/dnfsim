// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
//
function x = distfun_hygeinv(varargin)
    //  Hypergeometric Inverse CDF
    //
    // Calling Sequence
    //   x = distfun_hygeinv(p,M,k,N)
    //   x = distfun_hygeinv(p,M,k,N,lowertail)
    //
    // Parameters
    //   p : a matrix of doubles, the probability. Must be in the range [0,1].
    //   M : a matrix of doubles, the total size of the population. M belongs to the set {0,1,2,3........}
    //   k : a matrix of doubles, the number of success states in the population. k belongs to the set {0,1,2,3,.......M-1,M}
    //   N : a matrix of doubles, the total number of draws in the experiment. N belongs to the set {0,1,2,3.......M-1,M}
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then considers P(X<=x) otherwise P(X>x).
    //   x : a matrix of doubles, the number of successful draws in the experiment. x belongs to the set [0,min(k,N)]
    //
    // Description
    //   Computes the Inverse cumulative distribution function of 
    //   the Hypergeometric distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Examples
    // 
    // // Test with all the arguments scalar
    // x = distfun_hygeinv(0.2,80,50,30)
    // expected = 17
    // x = distfun_hygeinv(0.8,80,50,30,%f)
    // expected = 17
    //
    // // Test with expanded p
    // x = distfun_hygeinv([0.2 0.9],80,50,30)
    // expected = [17 21]
    //
    // // Test with expanded p,k
    // x = distfun_hygeinv([0.2 0.9],80,50,[30 35])
    // expected = [17 25]
    //
    // // Test with all the arguments expanded
    // x = distfun_hygeinv([0.2 0.9],[80 100],[50 60],[30 35])
    // expected = [17 24]
    //
    // // Test with small values of p
    // x = distfun_hygeinv(1.e-8,80,50,30)
    // expected = 7
    // x = distfun_hygeinv(1-1.e-8,80,50,30,%f)
    // expected = 7
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Hypergeometric_distribution
    //
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
    // Copyright (C) 2012 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_hygeinv",rhs,4:5)
    apifun_checklhs("distfun_hygeinv",lhs,0:1)

    p = varargin(1)
    M = varargin(2)
    k = varargin(3)
    N = varargin(4)
    lowertail = apifun_argindefault(varargin,5,%t)

    //
    // Check type
    apifun_checktype("distfun_hygeinv",p,"p",1,"constant")
    apifun_checktype("distfun_hygeinv",M,"M",2,"constant")
    apifun_checktype("distfun_hygeinv",k,"k",3,"constant")
    apifun_checktype("distfun_hygeinv",N,"N",4,"constant")
    apifun_checktype("distfun_hygeinv",lowertail,"lowertail",5,"boolean")
    //
    // Check size
    apifun_checkscalar("distfun_hygeinv",lowertail,"lowertail",5)
    //
    // Check content
    apifun_checkrange("distfun_hygeinv",p,"p",1,0,1)
    apifun_checkgreq("distfun_hygeinv",M,"M",2,0)
    apifun_checkgreq("distfun_hygeinv",k,"k",3,0)
    apifun_checkgreq("distfun_hygeinv",N,"N",4,0)
    //
    [p,M,k,N] = apifun_expandvar(p,M,k,N)
    //
    if (p == []) then
        x = []
        return
    end
    //
    myloweq("distfun_hygeinv",k,"k",2,M) // k<=M
    myloweq("distfun_hygeinv",N,"N",3,M) // N<=M
    //
    x = distfun_invhyge(p,M,k,N,lowertail)
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

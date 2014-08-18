// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function y = distfun_kspdf(varargin)
    // Kolmogorov-Smirnov PDF
    //
    // Calling Sequence
    // y = distfun_kspdf(x,N) 
    //
    // Parameters
    //   x : a matrix of doubles, the outcome. x in [0,1]
    //   N : a matrix of doubles , the number of observations. N belongs to the set {1,2,3,4,.......,2147483647}
    //   y : a matrix of doubles, the probability density.
    //
    // Description
    //   Computes the probability distribution function of 
    //   the Kolmogorov-Smirnov distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Examples
    // // Check with x scalar, N scalar, pr scalar
    //y = distfun_kspdf(0.274,10)
    //expected = 0.0175879
    //
    // // Plot the function
    // scf();
    // x = linspace(0,1);
    // y1 = distfun_kspdf(x,5);
    // plot(x,y1,"b-")
    // y2 = distfun_kspdf(x,10);
    // plot(x,y2,"g-")
    // y3 = distfun_kspdf(x,20);
    // plot(x,y3,"r-")
    // legend(["N=5","N=10","N=20"]);
    // xtitle("Kolmogorov-Smirnov PDF","x","P(X=x)")
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Kolmogorov%E2%80%93Smirnov_test
    //
    // Authors
    // Copyright (C) 2014 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_kspdf",rhs,2)
    apifun_checklhs("distfun_kspdf",lhs,0:1)
    //
    x = varargin(1)
    N = varargin(2)
    //
    // Check type
    apifun_checktype("distfun_kspdf",x,"x",1,"constant")
    apifun_checktype("distfun_kspdf",N,"N",2,"constant")
    //
    // Check size : nothing to do
    //
    [x,N] = apifun_expandvar(x,N)
    if (x == []) then
        y=[]
        return
    end
    // Check content
    apifun_checkrange("distfun_kscdf",x,"x",1,0,1)
    apifun_checkflint("distfun_kspdf",N,"N",2)
    apifun_checkgreq("distfun_kspdf",N,"N",2,1)
    apifun_checkloweq("distfun_kspdf",N,"N",2,2147483647)
    //
    y = distfun_pdfks(x,N)
endfunction

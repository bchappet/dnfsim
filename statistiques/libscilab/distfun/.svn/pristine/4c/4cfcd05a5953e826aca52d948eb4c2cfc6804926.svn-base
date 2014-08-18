// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function y = distfun_poisspdf(varargin)
    // Poisson PDF
    //
    // Calling Sequence
    //   y = distfun_poisspdf(x,lambda)
    //   
    // Parameters
    //   x : a matrix of doubles, the number of occurrences of events. x belongs to the set {0,1,2,3,.....}
    //   lambda : a matrix of doubles, the average rate of occurrence. lambda>0.
    //   y : a matrix of doubles, the probability density.
    //
    // Description
    //   Computes the probability distribution function of 
    //   the poisson distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    //   The function definition is:
    //
    //<latex>
    //\begin{eqnarray}
    //f(x,\lambda) = \frac{\lambda^x e^{-\lambda}}{x!}
    //\end{eqnarray}
    //</latex>
    //
    // Note : x belongs to the set {0,1,2,3,...}.
    //
    // Examples
    //
    // //Test with x scalar, lambda scalar
    // p = distfun_poisspdf(0,2)
    // expected = 0.1353353;
    //
    // //Test with x expanded, lambda scalar
    // computed = distfun_poisspdf([0 3],2)
    // expected = [0.1353353 0.1804470];
    //
    // //Test with x scalar, lambda expanded
    // computed = distfun_poisspdf(3,[2 4])
    // expected = [0.1804470 0.1953668];
    //
    // //Test with both the arguments expanded
    //
    // computed = distfun_poisspdf([3 4 8],[5 8 2])
    // expected = [0.1403739 0.0572523 0.0008593];
    //
    // //Plot the function
    // scf();
    // x = 0:20;
    // y = distfun_poisspdf(x,1);
    // plot(x,y,"ro-");
    // y1 = distfun_poisspdf(x,4);
    // plot(x,y1,"go-");
    // y2 = distfun_poisspdf(x,10);
    // plot(x,y2,"bo-");
    // xtitle("Poisson PDF","x","y");
    // legend(["lambda=1","lambda=4","lambda=10"]);
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Poisson_distribution
    // 
    // Authors
    // Copyright (C) 2012 - Prateek Papriwal
    // Copyright (C) 2012 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_poisspdf",rhs,2)
    apifun_checklhs("distfun_poisspdf",lhs,0:1)
    
    x=varargin(1)
    lambda=varargin(2)
    //
    // Check type
    apifun_checktype("distfun_poisspdf",x,"x",1,"constant")
    apifun_checktype("distfun_poisspdf",lambda,"lambda",2,"constant")
    //
    // Check content
    apifun_checkgreq("distfun_poisspdf",x,"x",1,0)
    apifun_checkflint("distfun_poisspdf",x,"x",1)
    tiny = number_properties("tiny")
    apifun_checkgreq("distfun_poisspdf",lambda,"lambda",2,tiny)
    
    [x,lambda] = apifun_expandvar(x,lambda)
    y = distfun_pdfpoiss(x,lambda)    
endfunction

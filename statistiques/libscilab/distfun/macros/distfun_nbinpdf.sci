// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function y = distfun_nbinpdf(varargin)
    // Negative Binomial PDF
    //
    // Calling Sequence
    // y = distfun_nbinpdf(x,R,P) 
    //
    // Parameters
    //   x: a matrix of doubles, the extra trials for R successes, in the set {0,1,2,3,...}.
    //   R : a matrix of doubles, the number of successes. R belongs to the set {0,1,2,3,4,.......}
    //   P : a matrix of doubles, the probability of getting success in a Bernoulli trial. P in [0,1].
    //   y : a matrix of doubles, the probability density.
    //
    // Description
    //   Computes the probability distribution function of 
    //   the Negative Binomial distribution function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    //    The function definition is:
    //
    // <latex>
    // \begin{eqnarray}
    // f(x,R,P) = \frac{\Gamma(x+R)}{x!\Gamma(R)} P^R (1-P)^x
    // \end{eqnarray}
    // </latex>
    //
    // Analysis of the random variable. 
    //
    // Consider successive random trials, each having a constant 
    // probability P of success. 
    // The number of extra trials we perform in order to observe a given 
    // number R of successes has a negative binomial distribution. 
    //
    // Examples
    // // Check with x scalar, R scalar, P scalar
    // p = distfun_nbinpdf(5,2,0.2)
    // expected = 0.0786432
    //
    // // Check with expanded x
    // computed = distfun_nbinpdf([5 7],5,0.5)
    // expected = [0.1230469    0.0805664]
    //
    // // Check with expanded R
    // computed = distfun_nbinpdf(5,[5 7],0.5)
    // expected = [0.1230469    0.1127930]
    //
    // // Check with two arguments expanded 
    // computed = distfun_nbinpdf([5 10],[5 7],0.5)
    // expected = [0.1230469    0.0610962]
    //
    // // Check with all the arguments expanded
    // computed = distfun_nbinpdf([5 10],[5 20],[0.5 0.7])
    // expected = [0.1230469    0.0943745]
    //
    // // Check y = distfun_nbinpdf(x,R,P) with large value of R
    // computed = distfun_nbinpdf(2,1000,0.5)
    // expected = 1.16D-296
    //
    // // Plot the function
    // scf();
    // x = 0:60;
    // y1 = distfun_nbinpdf(x,20,0.5);
    // plot(x,y1,"bo-")
    // y2 = distfun_nbinpdf(x,20,0.7);
    // plot(x,y2,"go-")
    // y3 = distfun_nbinpdf(x,40,0.5);
    // plot(x,y3,"ro-")
    // legend(["P=0.5, R=20","P=0.7, R=20","P=0.5, R=40"]);
    // xtitle("Negative Binomial PDF","x","P(X=x)")
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Negative_binomial_distribution
    //
    // Authors
    // Copyright (C) 2014 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_nbinpdf",rhs,3)
    apifun_checklhs("distfun_nbinpdf",lhs,0:1)
    //
    x = varargin(1)
    R = varargin(2)
    P = varargin(3)
    //
    // Check type
    apifun_checktype("distfun_nbinpdf",x,"x",1,"constant")
    apifun_checktype("distfun_nbinpdf",R,"R",2,"constant")
    apifun_checktype("distfun_nbinpdf",P,"P",3,"constant")
    //
    // Check size : nothing to do
    //
    [x,R,P] = apifun_expandvar(x,R,P)
    if (x == []) then
        y=[]
        return
    end
    // Check content
    apifun_checkflint("distfun_nbinpdf",x,"x",1)
    apifun_checkgreq("distfun_nbinpdf",x,"x",1,0)
    apifun_checkflint("distfun_nbinpdf",R,"R",2)
    apifun_checkgreq("distfun_nbinpdf",R,"R",2,1)
    apifun_checkrange("distfun_nbinpdf",P,"P",3,0.,1.)
    //
    y = distfun_pdfnbn(x,R,P)
endfunction

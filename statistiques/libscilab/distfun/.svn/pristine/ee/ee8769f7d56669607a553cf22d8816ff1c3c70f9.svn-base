// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function y = distfun_mvnpdf ( varargin )
    // Normal PDF
    //
    // Calling Sequence
    //   y=distfun_mvnpdf(x,mu,sigma)
    //
    // Parameters
    // x : a n-by-d matrix of doubles
    // mu : a 1-by-d matrix of doubles, the mean
    // sigma : a d-by-d matrix of doubles, the covariance matrix
    // y : a n-by-1 matrix of doubles, the density
    //
    // Description
    //   Computes the probability distribution function of 
    //   the Multivariate Normal (Laplace-Gauss) function.
    //   
    //   Any scalar input argument is expanded to a matrix of doubles of the same size as the other input arguments.
    //
    //   The function definition is:
    //
    //<latex>
    //\begin{eqnarray}
    //f(x,\mu,\sigma) = \frac{1}{(2\pi})^{d/2}} |\Sigma|^{-1/2} \exp\left(-(x-\mu)^T\Sigma^{-1}(x-\mu)\right)
    //\end{eqnarray}
    //</latex>
    //
    // Examples
    // x=[13.2,-33.7]
    // mu=[12 -31]
    // sigma = [
    //  3.0  0.5
    //  0.5  1.0
    // ]
    // y=distfun_mvnpdf(x,mu,sigma)
    //
    // // Plot the iso values of PDF
    // // A wrapper for contouring
    // function y=mvnpdfC(x1,x2,mu,sigma)
    //    x=[x1',x2']
    //    y=distfun_mvnpdf(x,mu,sigma)
    // endfunction
    // x1=linspace(-6,18,100);
    // x2=linspace(-35,-27,100);
    // levels=[0.1 0.05 0.02 0.005 0.001];
    // contour(x1,x2,mvnpdfC,levels)
    // legend(["Data","PDF"]);
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Multivariate_normal_distribution
    //
    // Authors
    //   Copyright (C) 2014 - Michael Baudin

endfunction


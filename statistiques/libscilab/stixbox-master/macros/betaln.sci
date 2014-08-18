// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function y=betaln(z,w)
    // Logarithm of Beta Function
    //
    // Calling Sequence
    //   L = betaln(z,w)
    //
    // Parameters
    //   z : a matrix of doubles, the first shape parameter, z>=0.
    //   w : a matrix of doubles, the second shape parameter, w>=0.
    //   L : a matrix of doubles, the function value.
    //
    // Description
    //   Computes the logarithme of the Beta function.
    //
    // The logarithm of the Beta function is defined by 
    //
    //<latex>
    //\begin{eqnarray}
    //\log(B(z,w)) = \log(\Gamma(z)) + \log(\Gamma(w)) - \log(\Gamma(z+w))
    //\end{eqnarray}
    //</latex>
    //
    // Examples
    // x=510
    // betaln(x,x)
    //
    // Authors
    //   Copyright (C) 2014 - Michael Baudin

    y = gammaln(z)+gammaln(w)-gammaln(z+w);
endfunction 

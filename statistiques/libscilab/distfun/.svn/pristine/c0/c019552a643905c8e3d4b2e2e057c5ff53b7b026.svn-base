//   Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function y=distfun_betainc(varargin)
    // Regularized Incomplete Beta function
    //
    // Calling Sequence
    //   y = distfun_betainc(x,z,w)
    //   y = distfun_betainc(x,z,w,lowertail)
    //
    // Parameters
    //   x : a matrix of doubles, the outcome. Should be in the [0,1] interval. If not, an error is generated.
    //   z : a matrix of doubles, the first shape parameter, z>=0.
    //   w : a matrix of doubles, the second shape parameter, w>=0.
    //   lowertail : a 1-by-1 matrix of booleans, the tail (default lowertail=%t). If lowertail is true (the default), then computes the integral from 0 to x otherwise from x to 1.
    //   y : a matrix of doubles, the output, y in [0,1].
    //
    // Description
    //   Computes the regularized Incomplete Beta function.
    //
    // The Beta function is defined by 
    //
    //<latex>
    //\begin{eqnarray}
    //B(z,w) = \int_0^1 t^{z-1} (1-t)^{w-1} dt = \frac{\Gamma(z)\Gamma(w)}{\Gamma(z+w)}
    //\end{eqnarray}
    //</latex>
    //
    // The incomplete Beta function is defined by 
    //
    //<latex>
    //\begin{eqnarray}
    //B(x,z,w) = \int_0^x t^{z-1} (1-t)^{w-1} dt
    //\end{eqnarray}
    //</latex>
    //
    // if x is in [0,1].
    //
    // The (lower tail) regularized incomplete Beta function 
    // is defined by 
    //
    //<latex>
    //\begin{eqnarray}
    //I_x(z,w) = \frac{B(x,z,w)}{B(z,w)}
    //\end{eqnarray}
    //</latex>
    //
    // if x is in [0,1].
    //
    // The upper tail regularized incomplete Beta function 
    // is defined by 
    //
    //<latex>
    //\begin{eqnarray}
    //I_x^u(z,w) = \frac{B^u(x,z,w)}{B(z,w)}
    //\end{eqnarray}
    //</latex>
    //
    // if x is in [0,1] where the upper tail of the 
	// incomplete Beta function is 
    //
    //<latex>
    //\begin{eqnarray}
    //B^u(x,z,w) = \int_x^1 t^{z-1} (1-t)^{w-1} dt.
    //\end{eqnarray}
    //</latex>
	//
	// The sum of the lower and upper tail is equal to 1 :
    //
    //<latex>
    //\begin{eqnarray}
    //I_x(z,w) + I_x^u(z,w) = 1
    //\end{eqnarray}
    //</latex>
    //
    // Examples
    // distfun_betainc(.5,(0:10)',3)
    //
    // // Plot the function
    // z = 1;
    // w = 2;
    // x=linspace(0,1,100);
    // y = distfun_betainc(x,z,w);
    // plot(x,y)
    // xtitle("Regularized Incomplete Beta Function, z=1, w=2","x","Ix(z,w)");
    //
    // Authors
    //   Copyright (C) 2014 - Michael Baudin
    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_betainc" , rhs , 3:4 )
    apifun_checklhs ( "distfun_betainc" , lhs , 0:1 )
    //
    x = varargin(1)
    z = varargin(2)
    w = varargin(3)
    lowertail = apifun_argindefault ( varargin , 4 , %t )
    //
    y=distfun_betacdf(x,z,w,lowertail)
endfunction 

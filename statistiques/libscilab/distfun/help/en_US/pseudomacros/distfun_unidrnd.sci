// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_unidrnd ( varargin )
    // Uniform Discrete random numbers
    //
    // Calling Sequence
    //   x = distfun_unidrnd ( N )
    //   x = distfun_unidrnd ( N , [m,n] )
    //   x = distfun_unidrnd ( N , m , n )
    //
    // Parameters
    //   N : a matrix of doubles, integer value, the integer upper bound (with N>=1)
    //   m : a 1-by-1 matrix of floating point integers, the number of rows of x
    //   n : a 1-by-1 matrix of floating point integers, the number of columns of x
    //   x: a matrix of doubles, the random numbers in the set {1,...,N}.
    //
    // Description
    //   Generates integer random variables from the Uniform discrete distribution function.
    //
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Examples
    // // Use x = distfun_unidrnd ( N )
    // x=distfun_unidrnd(7)
    // x=distfun_unidrnd(2:7)
    //  
    //  // Check x = distfun_unidrnd ( N , v )
    //  x = distfun_unidrnd(2,[1 5])
    //  x = distfun_unidrnd(2,[3 2])
    //  
    //  // See x = distfun_unidrnd (N,m,n)
    //  x = distfun_unidrnd(3,1,2)
    //
    // // Check mean and variance for x = distfun_unidrnd ( N )
    //  m = 1000;
    //  N = 2:7;
    //  [M,V] = distfun_unidstat(N) 
    //  for i = 1:m
    //  computed(i,1:6) = distfun_unidrnd(N);
    //  end
    //  Mx = mean(computed, "r" )
    //  Vx = variance(computed, "r" )
    //  
    //  // Make a plot of the actual distribution of the numbers
    //  N = 10;
    //  h=scf();
    //  x = distfun_unidrnd(N,1,1000);
    //  distfun_inthisto(x);
    //  h.children.children(1).children.background=-2;
    //  x=1:N;
    //  y=distfun_unidpdf(x,N);
    //  plot(x,y,"ro-")
    //  legend(["Data","PDF"]);
    //  xtitle("Uniform random numbers","X","Density");
    //
    // Authors
    //   Copyright (C) 2014 - Michael Baudin

endfunction

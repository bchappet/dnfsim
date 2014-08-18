// Copyright (C) 2012 - 2014 - Michael Baudin
// Copyright (C) 2012 - Prateek Papriwal
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt


function x = distfun_geornd(varargin)
    // Geometric random numbers
    //
    // Calling Sequence
    //   x = distfun_geornd(pr)
    //   x = distfun_geornd(pr,[m,n])
    //   x = distfun_geornd(pr,m,n)
    //
    // Parameters
    //   pr : a matrix of doubles, the probability of getting success in a Bernoulli trial. pr in (0,1].
    //   m : a 1-by-1 matrix of floating point integers, the number of rows of x
    //   n : a 1-by-1 matrix of floating point integers, the number of columns of x
    //   x: a matrix of doubles, the random numbers in the set {0,1,2,3,...}.
    //
    // Description
    //   Generates random variables from the Geometric distribution function.
    //
    //   Any scalar input argument is expanded to a matrix of doubles 
    //   of the same size as the other input arguments.
    //
    // Note - The output argument x belongs to the set {0,1,2,3,...}. 
    // This is not compatible with the <literal>grand(m,n,"geom",p)</literal> function in Scilab v5, 
    // where the choice is the set {1,2,3,...}. 
    // In other words, the calling sequence 
    //
    //<screen>
    // x = grand(m,n,"geom",pr)
    //</screen>
    //
    // is equivalent to 
    //
    //<screen>
    // x = distfun_geornd(pr,m,n) + 1
    //</screen>
    //
    // Examples
    // // Test with expanded pr
    // x = distfun_geornd(1 ./(1:6))
    //
    // // Check expansion of pr in x = distfun_geornd(pr)
    // N = 10;
    // x = distfun_geornd(1 ./(1:6))
    //
    // // Check x = distfun_geornd(pr,v)
    // x = distfun_geornd(0.2,[4 5])
    //
    // // Check mean and variance
    // N = 5000;
    // pr = 0.3;
    // x = distfun_geornd(pr,[1 N]);
    // RM = mean(x)
    // RV = variance(x)
    // [M,V] = distfun_geostat(pr)
    //
    // // Check actual distribution
    // h=scf();
    // pr=0.7;
    // N=10000;
    // x=distfun_geornd(pr,1,N);
    // Rmax=max(x);
    // distfun_inthisto(x);
    // h.children.children(1).children.background=-2;
    // M=distfun_geostat(pr);
    // x=0:Rmax;
    // y=distfun_geopdf(x,pr);
    // plot(x,y,"ro-");
    // xtitle("Geometric Random Numbers","X","Density")
    // legend(["Empirical","Density"]);
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Geometric_distribution
    // 
    // Authors
    // Copyright (C) 2012 - 2014 - Michael Baudin
    // Copyright (C) 2012 - Prateek Papriwal

endfunction

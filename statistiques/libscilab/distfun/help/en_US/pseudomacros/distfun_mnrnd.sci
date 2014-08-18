// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_mnrnd(varargin)
    // Multinomial random numbers
    //
    // Calling Sequence
    //   x = distfun_mnrnd(n,P)
    //   x = distfun_mnrnd(n,P,m)
    //
    // Parameters
    //   n : a 1-by-1 matrix of doubles, the number of balls
    //   P : a 1-by-k matrix of doubles, the probabilities of each category
    //   m : a 1-by-1 matrix of doubles, the total number of trials (default m=1). m must be in the set {1,2,3,4,.......}.
    //   x: a m-by-k matrix of doubles, the random numbers, in the set {0,1,2,3,...}.
    //
    // Description
    //   Generates n observations from the Multinomial distribution. 
    //
    //   x is of size m-by-k, 
    //   each column x(:,j) represents a category, 
    //   each row x(i,:) contains an outcome and
    //   x(i,j) the number of events falling 
    //   in category j for the i-th observation.
    //
    // On output, we have :
    //
    // <programlisting>
    // sum(x(i,:)) = n
    // </programlisting>
    //
    // i.e. after n trials, the total number of successes 
    // in all categories is n. 
    //
    // Examples
    // // With 2 balls in 3 categories.
    // x=distfun_mnrnd(2,[0.1,0.2,0.7])
    //
    // // With 2 balls in 3 categories : do this 10 times.
    // x=distfun_mnrnd(2,[0.1,0.2,0.7],10)
    // 
    // // Plot empirical distribution of 
    // // trinomial random numbers
    // P = [1/2 1/3 1/6]; // Outcome probabilities
    // n = 6; // Sample size
    // m=100000;
    // x=distfun_mnrnd(n,P,m);
    // scf();
    // for x2=0:n
    //     subplot(3,3,x2+1)
    //     // Get the outcomes equal to x2
    //     data=x(x(:,1)==x2,2);
    //     // Plot normalized histogram
    //     H=tabul(data,"i");
    //     H(:,2)=H(:,2)/m;
    //     bar(H(:,1),H(:,2));
    //     xlabel("X1")
    //     ylabel("P")
    //     xtitle("X2="+string(x2))
    //     g=gca();
    //     g.data_bounds(:,1)=[-0.5;n];
    //     g.data_bounds(:,2)=[0;0.15];
    // end
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Multinomial_distribution
    //
    // Authors
    // Copyright (C) 2014 - Michael Baudin

endfunction

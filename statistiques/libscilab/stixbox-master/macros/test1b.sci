// Copyright (C) 2013 - Michael Baudin
// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [pval,cimean,cistd]=test1b(x,c,b)
    // Bootstrap test that mean equals zero
    //  
    // Calling Sequence
    // pval=test1b(x)
    // pval=test1b(x,c)
    // pval=test1b(x,c,b)
    // [pval,cimean]=test1b(...)
    // [pval,cimean,cistd]=test1b(...)
    //
    // Parameters
    // x : a m-by-n matrix of doubles
    // c : a 1-by-1 matrix of doubles, c in [0,1], the confidence level for the confidence intervals (default=0.95)
    // b : a 1-by-1 matrix of doubles, b>=1, the number of bootstrap samples (default=2000)
    // pval : a 1-by-1 matrix of doubles, the probability that the mean is zero
    // cimean : a 1-by-3 matrix of doubles, the confidence interval for the mean
    // cistd : a 1-by-3 matrix of doubles, the confidence interval for the standard deviation
    //
    // Description
    // Performs the bootstrap t test for the equality of the mean to zero 
    // and computes confidence interval for the mean.
    //
    // Another name for the bootstrap t is studentized bootstrap.
    //
    // The confidence intervals are of the form 
    // <screen>
    // [LeftLimit, PointEstimate, RightLimit]
    // </screen>
    //
    // Examples
    // x=distfun_normrnd(0,1,20,1);
    // pval=test1b(x) // pval is close to 1
    //
    // x=distfun_normrnd(10,1,20,1);
    // pval=test1b(x) // pval is close to 0
    //
    // x=distfun_chi2rnd(3,20,1);
    // pval=test1b(x)
    // // Set the confidence level
    // pval=test1b(x,0.9)
    // // Set the number of bootstrap samples
    // pval=test1b(x,[],100)
    // // Get a confidence interval for the mean
    // [pval,cimean]=test1b(x)
    // // Get a confidence interval for the standard 
    // // deviation
    // [pval,cimean,cistd]=test1b(x)
    //
    // Authors
    // Copyright (C) 2013 - Michael Baudin
    // Copyright (C) 2010 - DIGITEO - Michael Baudin
    // Copyright (C) 1993 - 1995 - Anders Holtsberg

    pval=[];
    cimean=[];
    cistd=[];
    [nargout,nargin] = argn(0)
    //
    x = x(:);
    if nargin<2 then
        c = 0.95;
    end
    if nargin<3 then
        b = 2000;
    end
    n = size(x,"*")
    m = sum(x)/n;
    s = stdev(x);
    //
    xB = zeros(n,b);
    U=distfun_unifrnd(0,1,n*b,1)
    J = ceil(U*n);
    xB(:) = x(J);
    mB = mtlb_mean(xB);
    sB = stdev(xB,"r");
    z = (mB-m) ./ sB;
    t = quantile(z,[(1-c)/2,1-(1-c)/2]);
    cimean = [m-t(2)*s,m,m-t(1)*s];
    //
    tt = m/s;
    if tt>0 then
        pval = 2*sum((mB-tt*sB)>=m)/b;
    else
        pval = 2*sum((mB-tt*sB)<=m)/b;
    end

    if nargout>2 then
        d = quantile(sB/s,[(1-c)/2,1-(1-c)/2]);
        cistd = [s/d(2),s,s/d(1)];
    end

endfunction

// Copyright (C) 2013 - Michael Baudin
// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
// Copyright (C) 1998 - 2000 - Mathematique Universite de Paris-Sud - Jean Coursol
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [pval,cimean,cistd]=test1n(x,c)
    // Test that mean equals zero (Normal)
    //  
    // Calling Sequence
    // pval=test1n(x)
    // pval=test1n(x,c)
    // [pval,cimean]=test1n(...)
    // [pval,cimean,cistd]=test1n(...)
    //
    // Parameters
    // x : a m-by-n matrix of doubles
    // c : a 1-by-1 matrix of doubles, c in [0,1], the confidence level for the confidence intervals (default=0.95)
    // b : a 1-by-1 matrix of doubles, b>=1, the number of bootstrap samples (default=2000)
    // pval : a 1-by-1 matrix of doubles, probability that Student variate based on x is as far from 0 as it is actually, or further away under hypothesis that theoretical mean is 0.
    // cimean : a 1-by-3 matrix of doubles, the confidence interval for the mean
    // cistd : a 1-by-3 matrix of doubles, the confidence interval for the standard deviation
    //
    // Description
    // Assume that the sample is normal, test and compute confidence 
    // intervals.
    //
    // The confidence intervals are of the form 
    // <screen>
    // [LeftLimit, PointEstimate, RightLimit]
    // </screen>
    //
    // Examples
    // x=distfun_normrnd(0,1,20,1);
    // pval=test1n(x)
    // // Get a 95% confidence interval for the mean
    // [pval,cimean]=test1n(x)
    // // Get a 90% confidence interval for the mean
    // [pval,cimean]=test1n(x,0.9)
    // // Get a 95% confidence interval for the standard 
    // // deviation
    // [pval,cimean,cistd]=test1n(x)
    //
    // Authors
    // Copyright (C) 2013 - Michael Baudin
    // Copyright (C) 2010 - DIGITEO - Michael Baudin
    // Copyright (C) 1993 - 1995 - Anders Holtsberg
    // Copyright (C) 1998 - 2000 - Mathematique Universite de Paris-Sud - Jean Coursol

    pval=[];
    cimean=[];
    cistd=[];
    [nargout,nargin] = argn(0)
    // 
    x = x(:);
    if nargin<2 then
        c = 0.95;
    end
    n = mtlb_length(x);
    m = sum(x)/size(x,'*');
    s = sqrt(variance(x));
    T = m/s*sqrt(n);
    pt=distfun_tcdf(abs(T),n-1)
    pval = (1-pt)*2;
    t = distfun_tinv(1-(1-c)/2,n-1);
    cimean = [m-t*s/sqrt(n),m,m+t*s/sqrt(n)];
    chsq1=distfun_chi2inv(1-(1-c)/2,n-1)
    chsq2=distfun_chi2inv((1-c)/2,n-1)
    cistd = s*[sqrt((n-1)/chsq1),1,sqrt((n-1)/chsq2)];
endfunction

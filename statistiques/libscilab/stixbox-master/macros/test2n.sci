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

function [pval,cimean,cisigma]=test2n(x,y,c)
    // Tests two normal samples with equal variance
    //  
    // Calling Sequence
    // pval=test2n(x,y)
    // pval=test2n(x,y,c)
    // [pval,cimean]=test2n(...)
    // [pval,cimean,cisigma]=test2n(...)
    //
    // Parameters
    // x : a n-by-1 matrix of doubles
    // y : a n-by-1 matrix of doubles
    // c : a 1-by-1 matrix of doubles, the confidence level for the confidence intervals (default 0.95). 
    // pval  : a 1-by-1 matrix of doubles, the probability that Student statistic based on x and y is as far from 0 as it is actually, or further away under hypothesis that theoretical means are equal.
    // cimean : a 1-by-3 matrix of doubles, the confidence interval for the difference of means.
    // cisigma : a 1-by-3 matrix of doubles, the confidence interval for the standard deviation.
    //
    // Description
    // Tests and confidence intervals based on two normal samples
    // with equal variance.
    //
    // The confidence intervals are of the form 
    // <screen>
    // [LeftLimit, PointEstimate, RightLimit]
    // </screen>
    //
    // Examples
    // x=distfun_normrnd(0,1,20,1);
    // y=distfun_normrnd(0,1,20,1);
    // pval=test2n(x,y)
    // // Get a 95% confidence interval for the 
    // // difference of means
    // [pval,cimean]=test2n(x,y)
    // // Get a 90% confidence interval for the mean
    // [pval,cimean]=test2n(x,y,0.9)
    // // Get a 95% confidence interval for the 
    // // difference of standard deviations
    // [pval,cimean,cistd]=test2n(x,y)
    //
    // Authors
    // Copyright (C) 2013 - Michael Baudin
    // Copyright (C) 2010 - DIGITEO - Michael Baudin
    // Copyright (C) 1993 - 1995 - Anders Holtsberg
    // Copyright (C) 1998 - 2000 - Mathematique Universite de Paris-Sud - Jean Coursol

    pval=[];
    cimean=[];
    cisigma=[];
    [nargout,nargin] = argn(0)
    //
    x = x(:);
    y = y(:);
    if nargin<3 then
        c = 0.95;
    end
    nx = mtlb_length(x);
    ny = mtlb_length(y);
    mx = sum(x)/size(x,'*');
    my = sum(y)/size(x,'*');
    m = mx-my;
    degreef = nx+ny-2;
    s = sqrt(((nx-1)*variance(x)+(ny-1)*variance(y))/degreef);
    d = s*sqrt(1/nx+1/ny);
    T = m/d;
    pt=distfun_tcdf(abs(T),degreef)
    pval = (1-pt)*2;
    t = distfun_tinv(1-(1-c)/2,degreef);
    cimean = [m-t*d,m,m+t*d];
    chsq1=distfun_chi2inv(1-(1-c)/2,degreef)
    chsq2=distfun_chi2inv((1-c)/2,degreef)
    cisigma = s*[sqrt(degreef/chsq1),1,sqrt(degreef/chsq2)];
endfunction

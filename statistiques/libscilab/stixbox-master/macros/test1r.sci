// Copyright (C) 2013 - Michael Baudin
// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [pval,r]=test1r(x)
    // Test for median equals 0 using rank test
    //  
    // Calling Sequence
    // pval=test1r(x)
    // [pval,ranksum]=test1r(x)
    //
    // Parameters
    // x : a n-by-1 matrix of doubles
    // pval : a 1-by-1 matrix of doubles, the two-sided p-value
    // ranksum : a 1-by-1 matrix of doubles, the rank sum statistics
    // 
    // Description
    // Test for median equals 0 using rank test.
    //
    // This is called the Wilcoxon signed rank test. It is two sided.
    //
    // If you want a one sided alternative then divide pval by 2.
    //
    // Examples
    // x=distfun_normrnd(0,1,20,1);
    // pval=test1r(x)
    // [pval,ranksum]=test1r(x)
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Wilcoxon_signed-rank_test
    //
    // Authors
    // Copyright (C) 2013 - Michael Baudin
    // Copyright (C) 2010 - DIGITEO - Michael Baudin
    // Copyright (C) 1993 - 1995 - Anders Holtsberg

    pval=[];
    r=[];
    //
    x = x(:);
    x = x(find(x~=0)');
    n = mtlb_length(x);
    F = zeros(1,ceil((n*(n+1)/2+1)/2));
    F(1) = 1;
    for i = 1:n
        B = [0.5,zeros(1,i-1),0.5];
        F = mtlb_filter(B,1,F);
    end

    s = x>0;
    [x,I] = gsort(abs(x))
    x = x($:-1:1)
    I = I($:-1:1)
    J = s(I);
    J = find(J)';
    r = 1:n;
    r = sum(r(J));
    r = min(r,n*(n+1)/2-r);
    pval = min(2*sum(F(1:r+1)),1);
endfunction

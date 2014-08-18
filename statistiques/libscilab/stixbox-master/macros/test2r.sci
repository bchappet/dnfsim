// Copyright (C) 2013 - Michael Baudin
// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [pval,r]=test2r(x,y)
    // Test location equality of two samples using rank test
    //  
    // Calling Sequence
    // pval=test2r(x,y)
    // [pval,ranksum]=test2r(x,y)
    //
    // Parameters
    // x : a nx-by-1 matrix of doubles
    // y : a ny-by-1 matrix of doubles
    // pval : a 1-by-1 matrix of doubles, the two-sided p-value
    // ranksum : a 1-by-1 matrix of doubles, the rank sum statistics
    // 
    // Description
    // Test location equality of two samples using rank test.
    //
    // This is the Wilcoxon-Mann-Whitney test. 
    // It is two sided.
    //
    // If you want a one sided alternative then divide pval by 2.
    //
    // Examples
    // x=distfun_binornd(10,0.5,20,1);
    // y=distfun_binornd(10,0.5,20,1);
    // pval=test2r(x,y)
    // [pval,ranksum]=test2r(x,y)
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Mann%E2%80%93Whitney_U
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
    y = y(:);
    y = y(find(y~=0)');
    n = mtlb_length(x);
    m = mtlb_length(y);
    mP = ceil((m*n+1)/2);
    P = zeros(mP,n+1);
    P(1,:) = P(1,:)+1;
    for i = 1:m
        for j = 1:n
            P(:,j+1) = (P(:,j+1)*i+[zeros(min(i,mP),1);P(1:mP-i,j)]*j)/(i+j);
        end
    end

    P = P(:,n+1);

    // --- count number of y's less than x's ---

    [z,I] = gsort([x;y])
    z = z($:-1:1)
    I = I($:-1:1)
    R = 1:m+n;
    r = sum(R(find(I<=n)'));
    ranksum = r;

    // --- and the propability under the null hypothesis of that
    //     outcome or worse is ...

    r = r-n*(n+1)/2;
    r = min(r,m*n-r);
    pval = min(2*sum(P(1:r+1)),1);
endfunction

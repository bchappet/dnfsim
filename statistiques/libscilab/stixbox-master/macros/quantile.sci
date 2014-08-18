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

function q=quantile(x,p,method)
    // Empirical quantile
    //  
    // Calling Sequence
    //   q=quantile(x,p)
    //   q=quantile(x,p,method)
    //             
    // Parameters
    // x : a n-by-1 matrix of doubles
    // p : a m-by-1 matrix of doubles, the probabilities
    // method : a 1-by-1 matrix of doubles, available values are method=1,2,3 (default=1)
    // q : a m-by-1 matrix of doubles, the quantiles. q(i)is greater than p(i) percents of the values in x
    //
    // Description
    // The empirical quantile of the sample x, a value
    //that is greater than p percent of the values in x
    // If input x is a matrix then the quantile is 
    // computed for  every column. 
    // If p is a vector then q is a matrix, each line contain 
    // the quantiles  computed for a value of p.
    //
    // The empirical quantile is computed by one of three ways
    // determined by a third input argument (with default 1).
    //
    // <itemizedlist>
    // <listitem><para>
    //  method=1. Interpolation so that F(X_(k)) == (k-0.5)/n.
    // </para></listitem>
    // <listitem><para>
    // method=2. Interpolation so that F(X_(k)) == k/(n+1).
    // </para></listitem>
    // <listitem><para>
    // method=3. Based on the empirical distribution.
    // </para></listitem>
    // </itemizedlist>
    //
    // Examples
    // x=[
    //  0.4827129   0.3431706  -0.4127328    0.3843994  
    // -0.7107495  -0.2547306   0.0290803    0.1386087  
    // -0.7698385   1.0743628   1.0945652    0.4365680  
    // -0.5913411  -0.7426987   1.609719     0.8079680  
    // -2.1700554  -0.7361261   0.0069708    1.4626386  
    // ];
    // // Make a column vector:
    // x=x(:);
    // p=linspace(0.1,0.9,10)';
    // q=quantile(x,p) // Same as : q=quantile(x,p,1)
    // // Check the property
    // p(1)
    // length(find(x<q(1)))/length(x)
    // p(5)
    // length(find(x<q(5)))/length(x)
    // q=quantile(x,p,2)
    // q=quantile(x,p,3)
    //
    // Authors
    // Copyright (C) 2013 - Michael Baudin
    // Copyright (C) 2010 - DIGITEO - Michael Baudin
    // Copyright (C) 1993 - 1995 - Anders Holtsberg
    // Copyright (C) 1998 - 2000 - Mathematique Universite de Paris-Sud - Jean Coursol
    //
    q=[];
    [nargout,nargin] = argn(0)

    if nargin<3 then
        method = 1;
    end
    if min(size(x))==1 then
        x = x(:);
        %v1=size(p)
        q = zeros(%v1(1),%v1(2));
    else
        q = zeros(size(p,'*'),size(x,2));
    end
    if min(size(p))>1 then
        error('Not matrix p input');
    end
    if or(p>1|p<0) then
        error('Input p is not probability');
    end

    %v = x
    if min(size(%v))==1 then 
        %v=gsort(%v)
    else 
        %v=gsort(%v,'r')
    end
    x = %v($:-1:1,:);
    p = p(:);
    n = size(x,1);
    if method==3 then
        i=ceil(min(max(1,p*n),n))
        qq = x(i)
    else
        x = [x(1,:);x;x(n,:)];
        if method==2 then
            // This method is from Hjort's Computer
            // intensive statistical methods page 102
            i = p*(n+1)+1;
        else
            // Method 1
            i = p*n+1.5;
        end
        iu = ceil(i);
        il = floor(i);
        d = (i-il)*ones(1,size(x,2));
        qq = x(il,:) .* (1-d)+x(iu,:) .* d;
    end
    q(:) = qq;
endfunction

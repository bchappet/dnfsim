// Copyright (C) 2012-2013 - Michael Baudin
// Copyright (C) 2009-2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function C=cov(varargin)
    // Covariance matrix
    //
    // Calling Sequence
    //   C=cov(x)
    //   C=cov(x,0)
    //   C=cov(x,1)
    //   C=cov(x,y)
    //   C=cov(x,y,0)
    //   C=cov(x,y,1)
    //
    // Parameters
    // x: a nobs-by-1 or nobs-by-nvar matrix of doubles
    // y: a nobs-by-1 or nobs-by-nvar matrix of doubles
    // C: a square matrix of doubles, the empirical covariance
    //
    // Description
    // If x is a nobs-by-1 matrix, 
    // then <literal>cov(x)</literal> returns the variance of x, 
    // normalized by nobs-1. 
    //
    // If x is a nobs-by-nvar matrix, 
    // then <literal>cov(x)</literal> returns the nvar-by-nvar covariance matrix of the 
    // columns of x, normalized by nobs-1. 
    // Here, each column of x is a variable and 
    // each row of x is an observation.
    //
    // If x and y are two nobs-by-1 matrices, 
    // then <literal>cov(x,y)</literal> returns the 2-by-2 covariance matrix of x and 
    // y, normalized by nobs-1, where nobs is the number of observations.
    //
    // <literal>cov(x,0)</literal> is the same as <literal>cov(x)</literal> and 
    // <literal>cov(x,y,0)</literal> is the same as <literal>cov(x,y)</literal>. 
    // In this case, if the population is from a normal distribution, 
    // then C is the best unbiased estimate of the covariance matrix. 
    //
    // <literal>cov(x,1)</literal> and <literal>cov(x,y,1)</literal> normalize by nobs.
    // In this case, C is the second moment matrix of the 
    // observations about their mean. 
    //
    // The covariance of X and Y is defined by 
    //
    // <latex>
    // Cov(X,Y)=E((X-E(X))(Y-E(Y))^T)
    // </latex>
    //
    // where E is the expectation.
    //
    // This function is compatible with Matlab.
    //
    // Examples
    // x = [1;2];
    // y = [3;4];
    // C=cov(x,y)
    // expected = [0.5,0.5;0.5,0.5]
    // C=cov([x,y])
    //
    // x = [230;181;165;150;97;192;181;189;172;170];
    // y = [125;99;97;115;120;100;80;90;95;125];
    // expected = [
    // 1152.4556,-88.911111
    // -88.911111,244.26667
    // ]
    // C=cov(x,y)
    // C=cov([x,y])
    //
    // // Source [3]
    // A = [
    // 4.0 2.0 0.60
    // 4.2 2.1 0.59
    // 3.9 2.0 0.58
    // 4.3 2.1 0.62
    // 4.1 2.2 0.63
    // ];
    // S = [
    // 0.025 0.0075 0.00175
    // 0.0075 0.007 0.00135
    // 0.00175 0.00135 0.00043
    // ];
    // C = cov (A)
    // 
    // Authors
    // Copyright (C) 2012-2013 - Michael Baudin
    // Copyright (C) 2009-2010 - DIGITEO - Michael Baudin
    // Copyright (C) 1993 - 1995 - Anders Holtsberg
    //
    // Bibliography
    // [1] http://en.wikipedia.org/wiki/Covariance_matrix
    // [2] "Introduction to probability and statistics for engineers and scientists.", Sheldon Ross
    // [3] NIST/SEMATECH e-Handbook of Statistical Methods, 6.5.4.1. Mean Vector and Covariance Matrix, http://www.itl.nist.gov/div898/handbook/pmc/section5/pmc541.htm


    [lhs,rhs]=argn()
    apifun_checkrhs ( "cov" , rhs , 1:3 )
    apifun_checklhs ( "cov" , lhs , 0:1 )
    //
    if (rhs==1) then
        x = varargin(1)
        //
        // Check type
        apifun_checktype ( "cov" , x , "x" , 1 , "constant" )
        nobs = size(x,"r")
        r = 1/(nobs-1)
        A = x
    elseif (rhs==2) then
        //
        x = varargin(1)
        y = varargin(2)
        //
        // Check type
        apifun_checktype ( "cov" , x , "x" , 1 , "constant" )
        apifun_checktype ( "cov" , y , "y" , 2 , "constant" )
        //
        // Check size
        nobs = size(x,"r")
        if (size(y,"*")==1) then
            apifun_checkoption ( "cov" , y , "y" , 2, [0 1])
            if (y==1) then
                r = 1/nobs
                A = x
            elseif (y==0) then
                r = 1/(nobs-1)
                A = x
            end
        else
            apifun_checkdims ( "cov" , x , "x" , 1, [nobs 1] )
            apifun_checkdims ( "cov" , y , "y" , 2, [nobs 1] )
            r = 1/(nobs-1)
            A = [x,y]
        end
    elseif (rhs==3) then
        //
        x = varargin(1)
        y = varargin(2)
        nrmlztn = varargin(3)
        //
        // Check type
        apifun_checktype ( "cov" , x , "x" , 1 , "constant" )
        apifun_checktype ( "cov" , y , "y" , 2 , "constant" )
        apifun_checktype ( "cov" , nrmlztn , "nrmlztn" , 3 , "constant" )
        //
        // Check size
        nobs = size(x,"r")
        apifun_checkdims ( "cov" , x , "x" , 1, [nobs 1] )
        apifun_checkdims ( "cov" , y , "y" , 2, [nobs 1] )
        apifun_checkscalar ( "cov" , nrmlztn , "nrmlztn" , 3)
        //
        // Check content
        apifun_checkoption ( "cov" , nrmlztn , "nrmlztn" , 3, [0 1])
        A = [x,y]
        if (nrmlztn==1) then
            r = 1/nobs
        else
            r = 1/(nobs-1)
        end
    end
    //
    // Compute with A in the general case
    nvar = size(A,"c")
    nobs = size(A,"r")
    for i = 1 : nvar
        A(:,i) = A(:,i) - mean(A(:,i))
    end
    C = zeros(nvar,nvar)
    for i = 1 : nvar
        C(i,i) = A(:,i)'*A(:,i)*r
        for j = i+1 : nvar
            C(i,j) = A(:,i)'*A(:,j)*r
            C(j,i) = C(i,j)
        end
    end
endfunction

// Copyright (c) 2013 - Michael Baudin
// Copyright (c) 2010 - DIGITEO - Michael Baudin
// Copyright (c) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [c,y]=covboot(varargin)
    // Bootstrap estimate of the variance of a parameter estimate.
    //  
    // Calling Sequence
    // c=covboot(x,T)
    // c=covboot(x,T,b)
    // [c,y]=covboot(...)
    //
    // Parameters
    // x : a matrix of doubles
    // T : a function or a list, the function which computes the empirical estimate from x. 
    // b : a 1-by-1 matrix of doubles, the number of resamples (default b=200)
    // c : a matrix of doubles, the variance-covariance matrix
    // y : a m-by-b matrix of doubles, the parameter estimates of the resamples, where m is the size of the parameter estimate.
    //
    // Description
    // Computes the T(x) many times using resampled data and
    // uses the result to compute an estimate of the variance
    // of T(x) assuming that x is a representative sample from
    // the underlying distribution of x. 
    // 
    // The function T must have the following header:
    // <screen>
    // p=T(x)
    // </screen>
    // where <literal>x</literal> is the sample or the resample 
    // and <literal>p</literal> is a m-by-1 matrix of doubles. 
    // In the case where the parameter estimate has a more general 
    // shape (e.g. 2-by-2), the shape of <literal>p</literal> is reshaped 
    // into a column vector with <literal>m</literal> components. 
    //
    // See "T and extra arguments" for details on how to pass extra-arguments 
    // to T.
    //
    // If T is multidimensional
    // then the covariance matrix is estimated. 
    // 
    // Examples
    // // Case where the parameter to estimate is univariate:
    // // estimate a mean
    // n = 20;
    // x=distfun_chi2rnd(3,n,1);
    // mean(x)
    // c=covboot(x,mean)
    // c=covboot(x,mean,50000)
    // [c,y]=covboot(x,mean);
    // size(y)
    //
    // // Case where the parameter to estimate is multivariate:
    // // estimate a covariance
    // n = 20;
    // X1=distfun_chi2rnd(3,n,1);
    // X2=distfun_unifrnd(0,1,n,1);
    // X3=distfun_normrnd(0,1,n,1);
    // x = [X1,X2+X3];
    // cov(x)
    // c=covboot(x,cov)
    // [c,y]=covboot(x,cov);
    // size(y)
    //
    // // Test with a user-defined function.
    // function y=mymean(x)
    //     y=mean(x)
    // endfunction
    // n = 20;
    // x=distfun_chi2rnd(3,n,1);
    // c=covboot(x,mymean)
    // 
    // // With extra-arguments for T.
    // x=distfun_chi2rnd(3,20,5);
    // mean(x,"r")
    // c=covboot(x,list(mean,"r"))
    //
    // // With extra-arguments for T, and 
    // // multivariante case
    // x=distfun_chi2rnd(3,20,5);
    // c=covboot(x,list(mean,"r"))
    //
    // Authors
    // Copyright (C) 2013 - Michael Baudin
    // Copyright (C) 2010 - DIGITEO - Michael Baudin
    // Copyright (C) 1993 - 1995 - Anders Holtsberg

    [lhs,rhs] = argn();
    apifun_checkrhs("covboot",rhs,2:3);
    apifun_checklhs("covboot",lhs,0:2);
    //
    x=varargin(1)
    T=varargin(2)
    b=apifun_argindefault(varargin,3,200)
    //
    // Check type
    apifun_checktype("covboot",x,"x",1,"constant");
    apifun_checktype("covboot",T,"T",2,["function","list"]);
    apifun_checktype("covboot",b,"b",3,"constant");
    //
    // Check size
    apifun_checkscalar("covboot",b,"b",3);
    //
    // Check content
    apifun_checkgreq("covboot",b,"b",3,1);
    apifun_checkflint("covboot",b,"b",3);
    if (typeof(T)=="list") then
        apifun_checktype("covboot",T(1),"T(1)",2,"function");
    end
    //
    c=[];
    y=[];

    if min(size(x))==1 then   
        x = x(:); 
    end
    if (typeof(T)=="list") then
        T__fun__=T(1)
        T__args__=T(2:$)
    else
        T__fun__=T
        T__args__=list()
    end
    
    [n,nx] = size(x);
    xb = rboot(x);
    s = T__fun__(x,T__args__(:));
    y = [s(:),zeros(size(s,"*"),b-1)];
    xb_all = rboot(x,b-1);
    for i = 2:b
        xb = xb_all(:,(i-2)*nx+1:(i-1)*nx);
        yy = T__fun__(xb,T__args__(:));
        y(:,i) = yy(:);
    end
    c = cov(y')*b/(b-1);
endfunction

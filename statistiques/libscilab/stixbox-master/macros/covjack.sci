// Copyright (c) 2013 - Michael Baudin
// Copyright (c) 2010 - DIGITEO - Michael Baudin
// Copyright (c) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [c,y]=covjack(x,T)
    // Jackknife estimate of the variance of a parameter estimate.
    //  
    // Calling Sequence
    // c=covjack(x,T)
    // [c,y]=covjack(...)
    //
    // Parameters
    // x : a matrix of doubles
    // T : a function which computes the empirical estimate from x
    // c : a 1-by-1 matrix of doubles, the estimate of the covariance matrix
    // y : a m-by-n matrix of doubles, the parameter estimates of the resamples, where m is the size of the parameter estimate and n is the length of x.
    //
    // Description
    // The function computes T(x) with one observation removed at a
    // time and uses the result to compute an estimate of the variance 
    // of T(x) assuming that x is a representative sample from
    // the underlying distribution of x. 
    //
    // If T is multidimensional then the covariance matrix is estimated.
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
    // Note that the jackknife
    // method does not work for some functions T that are not smooth
    // enough, the median being one example.
    //
    // Examples
    // x=distfun_unifrnd(0,1,100,1);
    // c=covjack(x,mean)
    // // Get y
    // [c,y]=covjack(x,mean);
    // size(y)
    // 
    // // With extra-arguments for T.
    // x=distfun_chi2rnd(3,20,5);
    // mean(x,"r")
    // c=covjack(x,list(mean,"r"))
    //
    // Authors
    // Copyright (c) 2013 - Michael Baudin
    // Copyright (c) 2010 - DIGITEO - Michael Baudin
    // Copyright (c) 1993 - 1995 - Anders Holtsberg

    [lhs,rhs] = argn();
    apifun_checkrhs("covjack",rhs,2);
    apifun_checklhs("covjack",lhs,0:2);
    //
    // Check type
    apifun_checktype("covjack",x,"x",1,"constant");
    apifun_checktype("covjack",T,"T",2,["function","list"]);
    //
    // Check size
    // Nothing to do
    //
    // Check content
    if (typeof(T)=="list") then
        apifun_checktype("covjack",T(1),"T(1)",2,"function");
    end
    //
    c=[];
    y=[];
    //
    if (typeof(T)=="list") then
        T__fun__=T(1)
        T__args__=T(2:$)
    else
        T__fun__=T
        T__args__=list()
    end
    //
    if min(size(x))==1 then
        x = x(:);
    end

    n = size(x,1);

    xmi = x(2:n,:);
    s = T__fun__(xmi,T__args__(:));
    y = [s(:),zeros(size(s,"*"),n-1)];
    for i = 2:n
        xmi = x([1:i-1,i+1:n],:);
        yy = T__fun__(xmi,T__args__(:));
        y(:,i) = yy(:);
    end
    c = cov(y')*((n-1)^2)/n;
endfunction

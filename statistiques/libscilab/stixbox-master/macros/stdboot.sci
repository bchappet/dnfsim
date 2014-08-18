// Copyright (C) 2013 - Michael Baudin
// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [s,y]=stdboot(varargin)
    // Bootstrap estimate of the parameter standard deviation.
    //  
    // Calling Sequence
    // s=stdboot(x,T)
    // s=stdboot(x,T,b)
    // [s,y]=stdboot(...)
    //
    // Parameters
    // x : a matrix of doubles
    // T : a function or a list, the function which computes the empirical estimate from x. 
    // b : a 1-by-1 matrix of doubles, the number of bootstrap resamples (default b=200)
    // s : a 1-by-1 matrix of doubles, the estimate of the standard deviation
    // y : a 1-by-b matrix of doubles, the values of T of the resamples
    //
    // Description
    // Jackknife estimate of the standard deviation of the parameter 
    // estimate T(x).
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
    // The function is equal to 
    // <screen>
    // sqrt(diag(covboot(x,T)))
    // </screen>
    //
    // Examples
    // // Estimate the standard deviation of the 
    // // empirical mean
    // n = 20;
    // x=distfun_chi2rnd(3,n,1);
    // s=stdboot(x,mean)
    // // Get y
    // [s,y]=stdboot(x,mean);
    // size(y)
    // // Set the number of resamples 
    // [s,y]=stdboot(x,mean,1000);
    // size(y)
    //
    // // With extra-arguments for T.
    // x=distfun_chi2rnd(3,20,5);
    // mean(x,"r")
    // s=stdboot(x,list(mean,"r"))
    //
    // Authors
    // Copyright (C) 2013 - Michael Baudin
    // Copyright (C) 2010 - DIGITEO - Michael Baudin
    // Copyright (C) 1993 - 1995 - Anders Holtsberg

    [lhs,rhs] = argn();
    apifun_checkrhs("stdboot",rhs,2:3);
    apifun_checklhs("stdboot",lhs,0:2);
    //
    x=varargin(1)
    T=varargin(2)
    b=apifun_argindefault(varargin,3,200)
    //
    // Check type
    apifun_checktype("stdboot",x,"x",1,"constant");
    apifun_checktype("stdboot",T,"T",2,["function","list"]);
    apifun_checktype("stdboot",b,"b",3,"constant");
    //
    // Check size
    apifun_checkscalar("stdboot",b,"b",3);
    //
    // Check content
    apifun_checkgreq("stdboot",b,"b",3,1);
    apifun_checkflint("stdboot",b,"b",3);
    if (typeof(T)=="list") then
        apifun_checktype("stdboot",T(1),"T(1)",2,"function");
    end
    //
    [c,y]=covboot(x,T,b)
    s = sqrt(diag(c));
endfunction

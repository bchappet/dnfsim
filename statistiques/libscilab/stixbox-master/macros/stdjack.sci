// Copyright (C) 2013 - Michael Baudin
// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [s,y]=stdjack(x,T)
    // Jackknife estimate of the standard deviation of a parameter estimate.
    //  
    // Calling Sequence
    // s=stdjack(x,T)
    // [s,y]=stdjack(x,T)
    //
    // Parameters
    // x : a matrix of doubles
    // T : a function or a list, the function which computes the empirical estimate from x. 
    // s : a 1-by-1 matrix of doubles, the estimate of the standard deviation
    // y : a 1-by-n matrix of doubles, the values of T of the resamples, where n is the size of the parameter estimate
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
    // sqrt(diag(covjack(x,T)))
    // </screen>
    //
    // Examples
    // n = 20;
    // x=distfun_chi2rnd(3,n,1);
    // s=stdjack(x,mean)
    // // Get y
    // [s,y]=stdjack(x,mean);
    // size(y)
    //
    // // With extra arguments
    // x=distfun_chi2rnd(3,50,5);
    // mean(x,"r")
    // [s,y]=stdjack(x,list(mean,"r"));
    // size(y)
    //
    // Authors
    // Copyright (C) 2013 - Michael Baudin
    // Copyright (C) 2010 - DIGITEO - Michael Baudin
    // Copyright (C) 1993 - 1995 - Anders Holtsberg

    [lhs,rhs] = argn();
    apifun_checkrhs("stdjack",rhs,2:3);
    apifun_checklhs("stdjack",lhs,0:2);
    //
    // Check type
    apifun_checktype("stdjack",x,"x",1,"constant");
    apifun_checktype("stdjack",T,"T",2,["function","list"]);
    //
    // Check size
    // Nothing to do
    //
    // Check content
    if (typeof(T)=="list") then
        apifun_checktype("stdjack",T(1),"T(1)",2,"function");
    end
    //
    [c,y]=covjack(x,T)
    s = sqrt(diag(c));
endfunction

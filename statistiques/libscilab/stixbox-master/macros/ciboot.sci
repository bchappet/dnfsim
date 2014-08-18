// Copyright (C) 2013 - Michael Baudin
// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [ci,y]=ciboot(varargin)
    // Bootstrap confidence intervals
    //  
    // Calling Sequence
    // ci=ciboot(x,T)
    // ci=ciboot(x,T,method)
    // ci=ciboot(x,T,method,c)
    // ci=ciboot(x,T,method,c,b)
    // [ci,y]=ciboot(...)
    //
    // Parameters
    // x : a matrix of doubles
    // T : a function or a list, the function which computes the empirical estimate from x. 
    // method : a 1-by-1 matrix of doubles, integer, the method to use. Available methods are 1,2,3,4,5,6 (default method=5). See below for details.
    // c : a 1-by-1 matrix of doubles, the confidence level (default c=0.9, which corresponds to a 90% confidence interval)
    // b : a 1-by-1 matrix of doubles, the number of bootstrap resamples (default b=200)
    // ci : a m-by-3 matrix of doubles, the confidence interval for the parameter estimate. ci(:,1) is the lower bound, ci(:,2) is the estimate, ci(:,3) is the upper bound. Each row in ci represents a component of the parameter estimate.
    // y : a m-by-b matrix of doubles, the parameter estimates of the resamples
    //
    // Description
    //  Compute a bootstrap confidence interval for T(x) with level
    //  c. 
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
    // The available values of <literal>method</literal> are the following. 
    // <itemizedlist>
    //   <listitem><para>
    //     method=1.  Normal approximation (std is bootstrap).
    //   </para></listitem>
    //   <listitem><para>
    //     method=2.  Simple bootstrap principle (bad, don't use). 
    //   </para></listitem>
    //   <listitem><para>
    //     method=3.  Studentized, std is computed via jackknife.
    //     If T is the mean function, you may use the <literal>test1b</literal> 
    //     function, which is faster.
    //   </para></listitem>
    //   <listitem><para>
    //     method=4.  Studentized, std is 30 samples' bootstrap.
    //   </para></listitem>
    //   <listitem><para>
    //     method=5.  Efron's percentile method (default).
    //   </para></listitem>
    //   <listitem><para>
    //     method=6.  Efron's percentile method with bias correction (BC).
    //   </para></listitem>
    // </itemizedlist>
    //
    //  Often T(x) is a single number (e.g. the mean) but it may also
    //  be a vector or a even a matrix (e.g. the covariance matrix). 
    //  Every row of the result ci is of the form
    //  <screen>
    //      [LeftLimit, PointEstimate, RightLimit]
    //  </screen>
    //  and the corresponding element of T(x) is found by noting
    //  that 
    //  <screen>
    //  t = T(x); 
    //  t = t(:); 
    //  </screen>
    //  is used in the routine. 
    //
    // Examples
    // // Estimate a 90% confidence interval for 
    // // the empirical standard deviation
    // n = 20;
    // x=distfun_chi2rnd(3,n,1);
    // s=stdev(x)
    // ci=ciboot(x,stdev)
    // [ci,y] = ciboot(x,stdev,[],0.9,1000);
    // size(y)
    //
    //  x = distfun_unifrnd(0,1,13,2)
    // // Estimate the covariance
    //  C = cov(x)
    // // Estimate a 90% confidence interval
    //  ci = ciboot(x,cov)
    //
    // // Test all methods
    // n = 20;
    // x=distfun_chi2rnd(3,n,1);
    // ci=ciboot(x,mean,1)
    // ci=ciboot(x,mean,2)
    // ci=ciboot(x,mean,3)
    // ci=ciboot(x,mean,4)
    // ci=ciboot(x,mean,5)
    // ci=ciboot(x,mean,6)
    //
    // // With extra-arguments for T.
    // x=distfun_chi2rnd(3,20,5);
    // s=stdev(x,"r")
    // ci=ciboot(x,list(stdev,"r"))
    //
    // See also
    // stdboot
    // stdjack
    //
    // Authors
    // Copyright (C) 2013 - Michael Baudin
    // Copyright (C) 2010 - DIGITEO - Michael Baudin
    // Copyright (C) 1993 - 1995 - Anders Holtsberg

    [lhs,rhs] = argn();
    apifun_checkrhs("ciboot",rhs,2:5);
    apifun_checklhs("ciboot",lhs,0:2);
    //
    x=varargin(1)
    T=varargin(2)
    method=apifun_argindefault(varargin,3,5)
    c=apifun_argindefault(varargin,4,0.9)
    b=apifun_argindefault(varargin,5,200)
    //
    // Check type
    apifun_checktype("ciboot",x,"x",1,"constant");
    apifun_checktype("ciboot",T,"T",2,["function","list"]);
    apifun_checktype("ciboot",method,"method",3,"constant");
    apifun_checktype("ciboot",c,"c",4,"constant");
        apifun_checktype("ciboot",b,"b",5,"constant");
    //
    // Check size
    apifun_checkscalar("ciboot",method,"method",3);
    apifun_checkscalar("ciboot",c,"c",4);
        apifun_checkscalar("ciboot",b,"b",5);
    //
    // Check content
    if (typeof(T)=="list") then
        apifun_checktype("ciboot",T(1),"T(1)",2,"function");
    end
    apifun_checkoption("ciboot",method,"method",3,1:6);
    apifun_checkrange("ciboot",c,"c",4,0,1);
        apifun_checkgreq("ciboot",b,"b",5,1);
        apifun_checkflint("ciboot",b,"b",5);
    //
    ci=[];
    y=[];
    //
    if min(size(x))==1 then
        x = x(:);
    end
    alpha = (1-c)/2;
    [n,nx] = size(x);

    if (typeof(T)=="list") then
        T__fun__=T(1)
        T__args__=T(2:$)
    else
        T__fun__=T
        T__args__=list()
    end

    // === 1 ================================================

    if method==1 then
        if b==[] then
            b = 500;
        end
        s = stdboot(x,T,b)
        s = s(:);
        t0 = T__fun__(x,T__args__(:));
        t0 = t0(:);
        z = distfun_norminv(1-(1-c)/2,0,1);
        ci = [t0-z*s,t0,t0+z*s];
        return

    end

    // === 2 5 6 ==============================================

    if method==2|method==5|method==6 then
        if b==[] then
            b = 500;
        end
        [s,y] = stdboot(x,T,b)
        t0 = T__fun__(x,T__args__(:));
        t0 = t0(:);
        if method==2|method==5 then
            q = quantile(y',[alpha,1-alpha]',1);
            if method==2 then
                ci = [2*t0-q(2,:)',t0,2*t0-q(1,:)'];
            else
                ci = [q(1,:)',t0,q(2,:)'];
            end
        else
            J = (bool2s(y<t0)+bool2s(y<=t0))/2;
            z0 = distfun_norminv(mtlb_sum(J)/max(size(J)),0,1);
            z=distfun_norminv([alpha,1-alpha]',0,1)
            bet = distfun_normcdf(z+2*z0,0,1);
            q = quantile(y',bet,1);
            ci = [q(1,:)',t0,q(2,:)'];
        end
        return

    end

    // === 3 4 ================================================

    if method==3|method==4 then
        if b==[] then
            b = 200;
        end
        xb = x;
        t0 = T__fun__(xb,T__args__(:));
        s0 = stdboot(xb,T,b);
        t0 = t0(:);
        s0 = s0(:);
        y = zeros(mtlb_length(t0(:)),b);
        xb_all=rboot(x,b)
        ncols=size(x,"c")
        for i = 1:b
            xb = xb_all(:,(i-1)*ncols+1:i*ncols);
            tb = T__fun__(xb,T__args__(:));
            if method==3 then
                sb=stdjack(xb,T)
            else
                sb=stdboot(xb,T,30)
            end
            tb = tb(:);
            sb = sb(:);
            y(:,i) = (tb-t0) ./ sb;
        end
        q = quantile(y',[alpha,1-alpha]',1);
        ci = [t0-s0 .* q(2,:)',t0,t0-s0 .* q(1,:)'];
        return

    end
endfunction

// Copyright (C) 2012 - 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [y,a,b] = distfun_wblplot(data)
    // Weibull plot
    //
    // Calling Sequence
    //   distfun_wblplot(data)
    //   y = distfun_wblplot(data)
    //   [y,a,b] = distfun_wblplot(data)
    //
    // Parameters
    //   data : a n-by-1 matrix of doubles, the data
    //   y : a n-by-1 matrix of doubles, -log(1-p), where p is the 
    //   empirical CDF of the sorted data (see below for details)
    //   a : a 1-by-1 matrix of doubles, the first estimated parameter 
    //   of the Weibull distribution
    //   b : a 1-by-1 matrix of doubles, the second estimated parameter 
    //   of the Weibull distribution
    //
    // Description
    // Plots the data vs its probability with log-log axes, assuming that is is 
    // Weibull distributed.
    //
    // In the Weibull plot, we plot x against -log(1-p), 
    // where p is the empirical cumulated probability and x is the 
    // data. 
    // If the data is Weibull distributed, the points 
    // are approximately on a line (see below), and this is why we 
    // add the regression line on the plot. 
    // The coefficients of the Weibull distribution can then be 
    // estimated from the coefficients of the regression. 
    //
    // For a Weibull CDF, we have:
    //
    //<latex>
    // \begin{eqnarray}
    // b\log(x)-b\log(a)=\log(-\log(1-p))
    // \end{eqnarray}
    //</latex>
    //
    // where p is the cumulated distribution function 
    // and a and b are the parameters of the Weibull distribution. 
    // We notice that this is a linear relationship between 
    // log(x) and log(-log(1-p)). 
    //
    // The data is first sorted in increasing order, 
    // then the empirical cumulated probability p is computed 
    // from 
    //
    //<latex>
    // \begin{eqnarray}
    // p=\frac{i-0.3}{n+0.4}, 
    // \end{eqnarray}
    //</latex>
    //
    // where i=1,2,...,n is the rank of the data, 
    // and n is the number of values. 
    //
    // Then the data is plotted in logarithmic 
    // scale, and the best linear fit is computed 
    // by regression. 
    // The estimated coefficients a and b are 
    // then inferred from the best linear fit.
    //
    // Examples
    // // Reference
    // // NIST/SEMATECH e-Handbook of Statistical Methods
    // // http://www.itl.nist.gov/div898/handbook/
    // // 5th October 2012
    // // 1.4.2.9. Fatigue Life of Aluminum Alloy Specimens
    // // http://www.itl.nist.gov/div898/handbook/eda/section4/eda4291.htm
    // data = [
    // 370 1016 1235 1419 1567 1820 ..
    // 706 1018 1238 1420 1578 1868 ..
    // 716 1020 1252 1420 1594 1881 ..
    // 746 1055 1258 1450 1602 1890 ..
    // 785 1085 1262 1452 1604 1893 ..
    // 797 1102 1269 1475 1608 1895 ..
    // 844 1102 1270 1478 1630 1910 ..
    // 855 1108 1290 1481 1642 1923 ..
    // 858 1115 1293 1485 1674 1940 ..
    // 886 1120 1300 1502 1730 1945 ..
    // 886 1134 1310 1505 1750 2023 ..
    // 930 1140 1313 1513 1750 2100 ..
    // 960 1199 1315 1522 1763 2130 ..
    // 988 1200 1330 1522 1768 2215 ..
    // 990 1200 1355 1530 1781 2268 ..
    // 1000 1203 1390 1540 1782 2440 ..
    // 1010 1222 1416 1560 1792];
    // data=data(:);
    // [y,a,b]=distfun_wblplot(data);
    // disp([a,b])
    //
    // // Test with random numbers
    // data = distfun_wblrnd(123,4,1000,1);
    // scf();
    // [y,a,b]=distfun_wblplot(data);
    // disp([a,b])
    //
    // Bibliography
    // Probability Plot, NIST/SEMATECH e-Handbook of Statistical Methods, 5th October 2012, section 1.3.3.22, http://www.itl.nist.gov/div898/handbook/eda/section3/probplot.htm

    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_wblplot" , rhs , 1 )
    apifun_checklhs ( "distfun_wblplot" , lhs , 0:3 )
    //
    // Check type
    apifun_checktype ( "distfun_wblplot" , data , "data" , 1 , "constant" )
    //
    // Check size
    apifun_checkvector ( "distfun_wblplot" , data , "data" , 1 )

    data=data(:)
    n = size(data,"*")
    data=gsort(data,"g","i")
    // Compute the Empirical CDF
    order=(1:n)'
    p = (order-0.3)./(n+0.4)
    y = -specfun_log1p(-p)
    h=gcf();
    plot(data,y,"bo")
    h.children.log_flags="lln"
    // Make the grid visible
    h.children.grid(1)=1
    h.children.grid(2)=1
    xtitle("Weibull probability plot","Data","Probability")
    // Compute a best fit
    coefs=regress(log(data),log(y))
    z = coefs(1)+coefs(2).*log(data)
    plot(data,exp(z),"r-")
    legend(["Data","Best fit"])
    h.children.data_bounds(2,2)=1
    // Get the estimates of a and b
    b=coefs(2)
    a=exp(-coefs(1)/b)
endfunction


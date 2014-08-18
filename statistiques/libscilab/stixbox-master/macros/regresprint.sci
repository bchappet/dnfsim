// Copyright (C) 2012-2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function regresprint(fullstats)
    // Print linear regression
    //
    // Calling Sequence
    //   regresprint(fullstats)
    //
    // Parameters
    // fullstats : a struct, the statistics, see below for details. 
    //
    // Description
    // This function prints the statistics of a linear model with 
    // n independent variables x1, x2, ..., xn 
    // which best fit the data in the least squares sense. 
    // The <literal>fullstats</literal> data structure is the output of the 
    // scidoe_regress or scidoe_multilinreg functions.
    //
    // Examples
    // // Longley.dat contains 1 Response Variable y, 6 Predictor Variables x 
    // // and 16 Observations.
    // // Source : [4]
    // X = [
    // 83.0 234289 2356 1590 107608 1947
    // 88.5 259426 2325 1456 108632 1948
    // 88.2 258054 3682 1616 109773 1949
    // 89.5 284599 3351 1650 110929 1950
    // 96.2 328975 2099 3099 112075 1951
    // 98.1 346999 1932 3594 113270 1952
    // 99.0 365385 1870 3547 115094 1953
    // 100.0 363112 3578 3350 116219 1954
    // 101.2 397469 2904 3048 117388 1955
    // 104.6 419180 2822 2857 118734 1956
    // 108.4 442769 2936 2798 120445 1957
    // 110.8 444546 4681 2637 121950 1958
    // 112.6 482704 3813 2552 123366 1959
    // 114.2 502601 3931 2514 125368 1960
    // 115.7 518173 4806 2572 127852 1961
    // 116.9 554894 4007 2827 130081 1962
    // ];
    // Y = [
    // 60323
    // 61122
    // 60171
    // 61187
    // 63221
    // 63639
    // 64989
    // 63761
    // 66019
    // 67857
    // 68169
    // 66513
    // 68655
    // 69564
    // 69331
    // 70551
    // ];
    // [B,bint,r,rint,stats,fullstats] = scidoe_multilinreg(Y,[ones(Y),X]);
    // regresprint(fullstats)
    //
    // Authors
    // Copyright (C) 2012-2013 - Michael Baudin
    //
    // Bibliography
    // [1] "Introduction to probability and statistics for engineers and scientists.", Third Edition, Sheldon Ross, Elsevier Academic Press, 2004
    // [2] http://en.wikipedia.org/wiki/Linear_regression
    // [3] Octave's regress, William Poetra Yoga Hadisoeseno
    // [4] http://www.itl.nist.gov/div898/strd/lls/data/LINKS/DATA/Longley.dat

    // Check number of input arguments
    [lhs,rhs] = argn();
    apifun_checkrhs("regresprint",rhs,1);
    apifun_checklhs("regresprint",lhs,0:1);
    //
    // Check type
    apifun_checktype("regresprint",fullstats,"fullstats",1,"st");
    //
    // Check size : nothing to do
    //
    // Check content : nothing to do
    //
    // Proceed...
    fmtmax = max(format())
    fmtstr = msprintf("%ds",fmtmax)
    fmttmplate = msprintf("%%%s %%%s %%%s %%%s %%%s",..
    fmtstr,fmtstr,fmtstr,fmtstr,fmtstr)
    mprintf("Analysis of Variance Table\n");
    fmt = "%-12s"+fmttmplate+"\n"
    mprintf(fmt,..
    "Source","Degrees of","Sums of","Mean","F","P");
    mprintf(fmt,..
    "Of Var.","Freedom","Squares","Squares","Stat.","Value");
    mprintf(fmt,..
    "Regression",..
    string(fullstats.RegressionSS),..
    string(fullstats.RegressionDof),..
    string(fullstats.RegressionMean),..
    string(fullstats.F),..
    string(fullstats.pval));
    mprintf(fmt,..
    "Residual",..
    string(fullstats.ResidualSS),..
    string(fullstats.ResidualDof),..
    string(fullstats.ResidualMean),..
    "", ..
    "");
endfunction

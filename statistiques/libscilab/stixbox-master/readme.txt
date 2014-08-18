Stixbox

Purpose
-------

Stixbox is a statistics toolbox which provides datasets, 
statistical tests and plotting facilities. 

Features
--------

 * Cumulated Distribution Functions
   * pks : Kolmogorov Smirnov distribution function 
 * Random Numbers
   * rexpweib : Random numbers from the exponential or weibull distributions 
 * Datasets
   * getdata : Famous datasets 
 * Graphics
   * histo : plot a histogram 
   * identify : Identify points on a plot by clicking with the mouse 
   * plotsym : Plot with symbols 
   * plotmatrix : Plot an X vx Y scatter plot matrix
   * qqnorm : Normal probability paper 
   * qqplot : Plot empirical quantile vs empirical quantile 
   * stairs : Plots the Empirical Cumulated Distribution.
 * Logistic Regression
   * lodds : Log odds function 
   * loddsinv : inverse of log odd 
   * logitfit : Fit a logistic regression model 
 * Miscellaneous
   * betainc : Incomplete beta function 
   * betaln : Logarithm of beta function 
   * cov : Covariance matrix 
   * ksdensity : Kernel smoothing density estimate
   * quantile : Empirical quantile (percentile). 
   * regres : Multiple linear regression
   * regresprint : Print linear regression
 * Polynomials
   * polyfit : Polynomial curve fitting 
   * polyval : Polynomial evaluation 
 * Resampling Techniques
   * ciboot : Various bootstrap confidence intervals 
   * covboot : Bootstrap estimate of the variance of a parameter estimate 
   * covjack : Jackknife estimate of the variance of a parameter estimate 
   * rboot : Simulate a bootstrap resample from a sample 
   * stdboot : Bootstrap estimate of the parameter standard deviation 
   * stdjack : Jackknife estimate of the standard deviation of a parameter estimate 
 * Tests, confidence intervals and model estimation
   * ciquant : Nonparametric confidence interval for quantile 
   * cmpmod : Compare linear submodel versus larger one 
   * kstwo : Kolmogorov-Smirnov statistic from two samples 
   * lsfit : Fit a multiple regression normal model 
   * lsselect : Select a predictor subset for regression 
   * test1b : Bootstrap t test and confidence interval for the mean 
   * test1n : Tests and confidence intervals based on a normal sample 
   * test1r : Test for median equals 0 using rank test 
   * test2n : Tests and confidence intervals based on two normal samples with common variance 

Dependencies 
------------

 * This module depends on Scilab (v>=5.4).
 * This module depends on the "apifun" module.
 * This module depends on the "helptbx" module.
 * This module depends on the "distfun" module (v>=0.6).
 * This module depends on the "linalg" module (v>=0.3.1).
 * This module depends on the "makematrix" module (v>=0.4)

TODO
-----

 * Create unit tests for all functions.
 * Add examples where necessary.
 * Put the help content into the Scilab macros, and generate the help with help_from_sci.
 * Add a warnobsolete for the std functions.
 * Merge the stixbox/cov and Scilab/covar functions.
 * Remove the emulation functions mtlb_*.
 * Use apifun where appropriate.
 * Update the stixtest.dem script. 
   Display a listbox with all the available distribution functions instead 
   of letting the user type "Enter" in the console.
 * What is the difference between Stixbox/histo and Scilab/histplot ? 
   Stixbox/histo returns the number of items in each class, and not Scilab/histplot. 
   In Scilab/histplot, the number of classes is mandatory, it is optional in Stixbox/histo.
 * What is the difference between std and st_deviation ?
 * Separate rexpweib into rexp and rweib


Bibliography
------------

http://www.maths.lth.se/matstat/stixbox/

Authors
-------

The Scilab toolbox stixbox is derived from the matlab stixbox 1.1 version 

* Copyright (C) 2012-2014 - Michael Baudin
* Copyright (C) 2010-2011 - DIGITEO - Michael Baudin
* Copyright (C) 2008 - INRIA - Allan CORNET
* Copyright (C) 2006 - INRIA - Serge Steer
* Copyright (C) 2001 - ENPC - Jean-Philippe Chancelier
* Copyright (C) Paris Sud University.
* Copyright (C) INRIA - Maurice Goursat
* Copyright (C) 2000 - Anders Holtsberg

Licence
-------

This toolbox is released under the CeCILL_V2 licence :

http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt


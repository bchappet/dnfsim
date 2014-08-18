Distribution Functions toolbox

Purpose
-------

The goal of this toolbox is to provide accurate distribution functions. 
The provided functions are designed to be compatible with Matlab.
This toolbox currently provides more that 120 functions.

The goals of this toolbox are the following.
 * All functions are tested with tables (actually, csv datasets).
   The tests includes accuracy tests, so that the accuracy 
   should by from 13 to 15 significant digits in most cases.
 * For each distribution, we have 
   * the probability distribution function (PDF)
   * the cumulated distribution function (CDF)
   * the inverse CDF
   * the random number generator
   * the statistics (mean and variance)
 * The CDF provides the upper and the lower tail of the 
   distribution, for accuracy reasons. 
 * The uniform random numbers are of high quality.
   The default is to use the Mersenne-Twister generator.   
 * Each function has a consistent help page.
   This removes confusions in the meaning 
   of the parameters and clarifies the differences 
   with other computing languages (e.g. R).
   
The design is similar to Matlab's distribution functions. 
A significant difference with Matlab's function is that both 
the upper and lower tails are available in "distfun", while 
Matlab only provides the lower tail. 
Hence, "distfun" should provide a better accuracy when 
probabilities close to 1 are computed (e.g. p=0.9999). 

Why is the distfun toolbox useful ?
----------------------------------

The differences with Scilab and other tools is that a consistent 
set of functions is provided. 

 * Scilab currently does not provide the PDFs. 
Users may write their own functions: this is not as easy as it 
seems, and may lead to very innaccurate results if floating point 
issues are ignored. 
 * Scilab does not provide a consistent sets of functions: 
the CDF and the random number generators are provided in two 
different toolboxes, with no consistency. 
 * Scilab requires that we provide the argument Q 
(which is mathematically equal to 1-P), no matter if we 
want the lower or the upper tail. 
 * The inverse CDF functions in Scilab do not manage extreme 
values of p (i.e. zero or one). 
 * Inverse discrete distributions produces doubles with 
fractional values instead of integer values (e.g. 1.3234887 
instead of 2, with the Poisson distribution, for example). 
 * The cdf* functions allows to compute one parameter from 
the others, which is unnecessary for the parameters of the 
distribution (which is computed, in practice, either with the moments methods, 
or with the maximum likelyhood function, for example). 
 * The difference with Stixbox is that the current function are 
tested, as accurate as we could, with consistent help pages.
   
Features
--------

For each distribution x, we provide five functions :
 * distfun_xcdf — x CDF
 * distfun_xinv — x Inverse CDF
 * distfun_xpdf — x PDF
 * distfun_xrnd — x random numbers
 * distfun_xstat — x mean and variance

Distributions available :
 * Beta (with x=beta)
 * Binomial (with x=bino)
 * Chi-Squared (with x=chi2)
 * Extreme Value (with x=ev)
 * Exponential (with x=exp)
 * F (with x=f)
 * Gamma (with x=gam)
 * Geometric (with x=geo)
 * Hypergeometric (with x=hyge)
 * LogNormal (with x=logn)
 * LogUniform (with x=logu)
 * Multinomial (with x=mn)
 * Multivariate Normal (with x=mvn)
 * Negative Binomial (with x=nbin)
 * Noncentral F (with x=ncf)
 * Noncentral T (with x=nct)
 * Noncentral Chi-Squared (with x=ncx2)
 * Normal (with x=norm)
 * Poisson (with x=poi)
 * T (with x=t)
 * Truncated Normal (with x=tnorm)
 * Uniform Discrete (with x=unid)
 * Uniform (with x=unif)
 * Weibull (with x=wbl)

Tutorial
 * distfun_tutorial — A tutorial of the Distfun toolbox.
 * distfun_plots — A collection of distribution function plots.

Support
 * distfun_erfcinv — Inverse erfc function
 * distfun_genericpdf — Compute the PDF from the CDF.
 * distfun_getpath — Returns path of current module
 * distfun_inthisto — Discrete histogram
 * distfun_permrnd — Random permutation
 * distfun_plotintcdf -  Plots an integer CDF
 * distfun_verboseset — Set verbose mode.

Random Number Generator
 * rng_overview — An overview of the Random Number Generators of the Distfun toolbox.
 * distfun_genget — Get the current random number generator
 * distfun_genset — Set the current random number generator
 * distfun_seedget — Get the current state of the current random number generator
 * distfun_seedset — Set the current state of the current random number generator
 * distfun_streamget — Get the current stream
 * distfun_streaminit — Initializes the current stream
 * distfun_streamset — Set the current stream

Dependencies
------------

 * This module depends on Scilab >= v5.4.0.
 * This module depends on the helptbx module (to update the help pages).
 * This module depends on the apifun module (>= v0.4).
 * This module depends on the specfun module (>=v0.1).

TODO
----

 * Import the functions from Stixbox:
   * Cumulated Distribution Functions
     * pks : Kolmogorov Smirnov distribution function 
   * Random Numbers
     * rexpweib : Random numbers from the exponential or weibull distributions 
 * Check inverse beta for x in [0,1.e-50]
 * See if the failures of computations of Shape parameter of 
   the CDF Gamma distribution of the bug reports :
   http://bugzilla.scilab.org/show_bug.cgi?id=8031
   http://bugzilla.scilab.org/show_bug.cgi?id=8030
   can occur with the inverse CDF.
   Now the computation of Shape with Brent-Bus-Dekker algorithm is 
   not available anymore. 
   Only the Inverse CDF computation of X matters : 
   can the bug be safely ignored ?
 * Check accuracy of Normal distribution near 0.5:
   http:#bugzilla.scilab.org/show_bug.cgi?id=8032

Authors
-------

 * Copyright (C) 2012-2014 - Michael Baudin
 * Copyright (C) 2012 - Prateek Papriwal
 * Copyright (C) 2011 - DIGITEO - Michael Baudin
 * Copyright (C) 2008 - 2011 - INRIA - Michael Baudin
 * Copyright (C) 2008 - John Burkardt
 * Copyright (C) 2002, 2004, 2005 - Bruno Pincon
 * Copyright (C) 1997, 1999 - Makoto Matsumoto and Takuji Nishimura
 * Copyright (C) 1999 - G. Marsaglia
 * Copyright (C) 1994 - Barry W. Brown, James Lovato, Kathy Russell (DCDFLIB)
 * Copyright (C) 1992 - Arif Zaman
 * Copyright (C) 1992 - George Marsaglia
 * Copyright (C) 1973 - Cleve B. Moler
 * Copyright (C) 1973 - Michael A. Malcolm
 * Copyright (C) 1973 - Richard Brent
 * Copyright (C) Jean-Philippe Chancelier
 * Copyright (C) Luc Devroye
 * Copyright (C) Pierre Lecuyer

Licence
------

This toolbox is released under the CeCILL_V2 licence :

http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

Bibliography
------

 * http://people.sc.fsu.edu/~burkardt/m_src/prob/
 * The accuracy of statistical distributions in Microsoft Excel 2007 - A. Talha Yalta, Computational Statistics and Data Analysis 52 (2008) 4579?4586
 * Fixing Statistical Errors in Spreadsheet Software: The Cases of Gnumeric and Excel Export - B. D. Mc Cullough, CSDA Statistical Software Newsletter - 2004
 * On the Accuracy of Statistical Distributions in Microsoft Excel 97 - Leo Knusel, SSNinCSDA 26, 375-379, January 1998
 * Comparison of mathematical programs for data analysis - Edition 5.04, Stefan Steinhaus 
 * http://afni.nimh.nih.gov/pub/dist/src/cdflib/


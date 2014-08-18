Cumulated Distribution Functions library
Non-uniform random numbers

Purpose
-------

This library provides sources for the Probability Distribution 
Function (PDF), Cumulated Distribution Function (CDF), 
Inverse CDF and Random Numbers of several distributions,.

The provided PDF, CDF and Inverse CDF are partly adapted 
from DCDFLIB :

    (1) Beta
    (2) Binomial
    (3) Chi-square
    (4) Noncentral Chi-square
    (5) F
    (6) Noncentral F
    (7) Gamma
    (8) Negative Binomial
    (9) Normal
    (10) Poisson
    (11) Student's t
    (12) Hypergeometric
    (13) Log-Normal
    (14) Uniform
    (15) Exponential
    (16) Geometric

The following functions calculates any one parameter of the distribution given 
values for the others :

    "cdft.c"
    "cdfpoi.c"
    "cdfnor.c"
    "cdfnbn.c"
    "cdfgam.c"
    "cdffnc.c"
    "cdff.c"
    "cdfchn.c"
    "cdfchi.c"
    "cdfbin.c"
    "cdfbet.c"

The following functions only compute the CDF :

    "cumt.c"
    "cumpoi.c"
    "cumnor.c"
    "cumnbn.c"
    "cumgam.c"
    "cumfnc.c"
    "cumf.c"
    "cumchn.c"
    "cumchi.c"
    "cumbin.c"
    "cumbet.c"

The following functions only compute the PDF:
    "exppdf.c"
    "tpdf.c"
    "geopdf.c"
    "betapdf.c"
    "lognpdf.c"
    "gammapdf.c"
    "fpdf.c"
    "binopdf.c"
    "unifpdf.c"
    "normpdf.c"
    "poisspdf.c"
    "chi2pdf.c"

The following files generate non uniform random 
numbers :

    "genbet.c"   
    "genchi.c"
    "genexp.c"
    "genf.c"
    "gengam.c"
    "genmn.c"
    "genmul.c"
    "gennch.c"
    "gennf.c"
    "gennor.c"
    "genprm.c"
    "genrand.c"
    "genunf.c"
    "ignbin.c"   
    "igngeom.c"  
    "ignnbn.c"
    "ignpoi.c"   
    "logp1.c"
    "lnp1m1.c"
    "setgmn.c"
    "sdot.c"
    "snorm.c"
    "sexpo.c"
    "sgamma.c"
    "spofa.c"

History
--------
These sources were originally in Fortran 77. 
We translated these files into C with f2c and 
manually tuned the files to remove the dependency to f2c.
We added the PDFs, which were not in the original library. 
We added other distributions, such as :
 * Uniform
 * Geometric
 * Hypergeometric
 * Exponential
 * Log-Normale
We fixed many numerical errors, mainly for extreme arguments, 
such as e.g. x=+/-Inf, p=0, q=0, etc... 
These numerical bug fixes include small or large values of the inputs, 
such as x=1.e100 or x=1.e-100.
Modifications also include :
 * Removal of static parameters to adapt to IEEE 754 standard for doubles.
 * Bug fixes near the extreme range of p, i.e. near zero or near one.
 * Renamed some functions to clarify their purpose (e.g. rexp_ into 
   cdflib_expm1).
 * Clarified the private and the public API.
 * Removed the unnecessary computations, for example, the mean 
   of a normal distribution could be computed depending on 
   X, SIGMA, P and Q. 
   This is unnecessary in practice, since parameter fitting is 
   done by other methods, such as the moments method 
   (where SIGMA is also unknown) or the maximum likelihood method 
   (which is completely different).

The non uniform random numbers sources were 
re-designed from the "Randlib" module, 
designed by Bruno Pincon.

Authors
-------

 * Copyright (C) 1973 - CLEVE B. MOLER
 * Copyright (C) 1973 - MICHAEL A. MALCOLM
 * Copyright (C) 1992 - Arif Zaman
 * Copyright (C) 1992 - George Marsaglia
 * Copyright (C) 1994 - Barry W. Brown, James Lovato, Kathy Russell (DCDFLIB)
 * Copyright (C) 1997, 1999 - Makoto Matsumoto and Takuji Nishimura
 * Copyright (C) 1999 - G. Marsaglia
 * Copyright (C) 2002 - 2005 - Bruno Pincon
 * Copyright (C) 2008 - 2009 - INRIA - Michael Baudin
 * Copyright (C) 2010 - 2011 - DIGITEO - Michael Baudin
 * Copyright (C) 2012 - 2014 - Michael Baudin
 * Copyright (C) Luc Devroye
 * Copyright (C) Pierre Lecuyer * Probably many others ...

Licence
------

This toolbox must be used under the terms of the 
GNU Lesser General Public License license :

http://www.gnu.org/copyleft/lesser.html

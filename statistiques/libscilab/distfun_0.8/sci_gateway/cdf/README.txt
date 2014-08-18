Distribution Functions toolbox

Gateway for CDF and Inverse CDF of distributions

Purpose
-------

This gateway provides basic features for the Distribution Functions 
toolbox for Scilab. 
This gateway connects to a set of sources for the Cumulated Distribution 
Function (CDF) and Inverse CDF of several distributions.

These gateways exist but are not documented: they are "private" not designed 
to be directly used by the end user. 
Essentially, this is an intermediate state: when the toolbox is "finished", 
all functions should be provided directly with gateways.

More details are provided in README.cdf.txt for the grand functions.
An overview of the gateways is in the gw_distfun.h header.

CDF of distributions
--------------------

This is the list of files associated with the CDF and Inverse CDF 
of various distributions :

CDF functions:

    "sci_distfun_cdfbet.c"
    "sci_distfun_cdfbin.c"
    "sci_distfun_cdfchi.c"
    "sci_distfun_cdfchn.c"
    "sci_distfun_cdff.c"
    "sci_distfun_cdffnc.c"
    "sci_distfun_cdfgam.c"
    "sci_distfun_cdfnbn.c"
    "sci_distfun_cdfnor.c"
    "sci_distfun_cdfpoi.c"
    "sci_distfun_cdft.c"
    "sci_distfun_cdfhyge.c"

Inverse CDF functions:

    "sci_distfun_invcdfpoi.c"
    "sci_distfun_invcdfbet.c"
    "sci_distfun_invcdfbin.c"
    "sci_distfun_invcdfchi.c"
    "sci_distfun_invcdfchn.c"
    "sci_distfun_invcdff.c"
    "sci_distfun_invcdft.c"
    "sci_distfun_invcdffnc.c"
    "sci_distfun_invcdfgam.c"
    "sci_distfun_invcdfnbn.c"
    "sci_distfun_invcdfnor.c"
	"sci_distfun_invcdfhyge.c"

Utilities
-------

    "gw_distfun_support.c"      

History
--------

The sources in DCDFLIB were originally in Fortran 77. 
We translated these files into C with f2c and 
manually tuned the files, essentially to remove the 
dependency to f2c.

Functions
---------
	
The provided CDF and Inverse CDF are from DCDFLIB :

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

The provided CDF and Inverse CDF is from othdist:

    (12) Hypergeometric

Here is a detailed description of the provided CDF and Inverse CDF:

    (1) Beta
CDF :
P=distfun_cdfbet(X,Y,A,B)
[P,Q]=distfun_cdfbet(X,Y,A,B)

Inverse CDF:
X=distfun_invcdfbet(A,B,P,Q)
[X,Y]=distfun_invcdfbet(A,B,P,Q)

    (2) Binomial
CDF:
P=distfun_cdfbin(S,Xn,Pr,Ompr)
[P,Q]=distfun_cdfbin(S,Xn,Pr,Ompr)

Inverse CDF:
S=distfun_invcdfbin(Xn,Pr,Ompr,P,Q)

    (3) Chi-square
CDF:
P=distfun_cdfchi(X,Df)
[P,Q]=distfun_cdfchi(X,Df)

Inverse CDF:
X=distfun_invcdfchi(Df,P,Q);

    (4) Noncentral Chi-square
CDF:
P=distfun_cdfchn(X,Df,Pnonc)
[P,Q]=distfun_cdfchn(X,Df,Pnonc)

Inverse CDF:
X=distfun_invcdfchn(Df,Pnonc,P,Q);

    (5) F
CDF:
P=distfun_cdff(F,Dfn,Dfd)
[P,Q]=distfun_cdff(F,Dfn,Dfd)

Inverse CDF:
F=distfun_invcdff(Dfn,Dfd,P,Q);

    (6) Noncentral F
CDF:
P=distfun_cdffnc(F,Dfn,Dfd,Pnonc)
[P,Q]=distfun_cdffnc(F,Dfn,Dfd,Pnonc)

Inverse CDF:
F=distfun_cdffnc(Dfn,Dfd,Pnonc,P,Q);

    (7) Gamma
CDF:
P=distfun_cdfgam(X,Shape,Rate)
[P,Q]=distfun_cdfgam(X,Shape,Rate)

Inverse CDF:
X=distfun_invcdfgam(Shape,Rate,P,Q)

    (8) Negative Binomial
CDF:
P=distfun_cdfnbn(S,Xn,Pr,Ompr)
[P,Q]=distfun_cdfnbn(S,Xn,Pr,Ompr)

Inverse CDF:
S=distfun_invcdfnbn(Xn,Pr,Ompr,P,Q)

    (9) Normal
CDF:
P=distfun_cdfnor(X,Mean,Std)
[P,Q]=distfun_cdfnor(X,Mean,Std)

Inverse CDF:
X=distfun_invcdfnor(Mean,Std,P,Q)

    (10) Poisson
CDF:
P=distfun_cdfpoi(S,Lambda)
[P,Q]=distfun_cdfpoi(S,Lambda)

Inverse CDF:
S=distfun_invcdfpoi(Lambda,P,Q)

    (11) Student's t
CDF:
P=distfun_cdft(T,Df)
[P,Q]=distfun_cdft(T,Df)

Inverse CDF:
T=distfun_invcdft(Df,P,Q)

    (12) Hypergeometric
CDF:
P=distfun_cdfhyge(x,M,k,N,lowertail)

Inverse CDF:
x=distfun_invcdfhyge(p,M,k,N,lowertail)

Authors
-------

 * Copyright (C) 2012 - Michael Baudin
 * Copyright (C) 2011 - DIGITEO - Michael Baudin
 * Copyright (C) 2008 - 2011 - INRIA - Michael Baudin
 * Copyright (C) 2010 - DIGITEO - Michael Baudin
 * Copyright (C) 2002, 2004, 2005 - Bruno Pincon
 * Probably many others ...

Licence
------

This toolbox is released under the CeCILL_V2 licence :

http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

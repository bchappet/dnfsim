
// Copyright (C) 2012 - 2014 - Michael Baudin
// Copyright (C) 2011 - Digiteo - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

//
// gw_distfuncdf.h
//   Header for the DISTFUN CDF gateway.
//
#ifndef __SCI_GW_DISTFUNCDF_H__
#define __SCI_GW_DISTFUNCDF_H__

// Uniform distribution
int sci_distfun_cdfunif(char* fname,unsigned long l);
int sci_distfun_invunif(char* fname,unsigned long l);
int sci_distfun_pdfunif(char* fname,unsigned long l);

// T distribution
int sci_distfun_cdft(char* fname,unsigned long l);
int sci_distfun_invt(char* fname,unsigned long l);
int sci_distfun_pdft(char* fname,unsigned long l);

// Beta distribution
int sci_distfun_cdfbeta(char* fname,unsigned long l);
int sci_distfun_invbeta(char* fname,unsigned long l);
int sci_distfun_pdfbeta(char* fname,unsigned long l);

// Binomial distribution
int sci_distfun_cdfbino(char* fname,unsigned long l);
int sci_distfun_invbino(char* fname,unsigned long l);
int sci_distfun_pdfbino(char* fname,unsigned long l);

// Chi distribution
int sci_distfun_cdfchi2(char* fname,unsigned long l);
int sci_distfun_invchi2(char* fname,unsigned long l);
int sci_distfun_pdfchi2(char* fname,unsigned long l);

// F distribution
int sci_distfun_cdff(char* fname,unsigned long l);
int sci_distfun_invf(char* fname,unsigned long l);
int sci_distfun_pdff(char* fname,unsigned long l);

// Gamma distribution
int sci_distfun_cdfgam(char* fname,unsigned long l);
int sci_distfun_invgam(char* fname,unsigned long l);
int sci_distfun_pdfgam(char* fname,unsigned long l);

// Normale distribution
int sci_distfun_cdfnorm(char* fname,unsigned long l);
int sci_distfun_invnorm(char* fname,unsigned long l);
int sci_distfun_pdfnorm(char* fname,unsigned long l);

// Poisson distribution
int sci_distfun_cdfpoiss(char* fname,unsigned long l);
int sci_distfun_invpoiss(char* fname,unsigned long l);
int sci_distfun_pdfpoiss(char* fname,unsigned long l);

// Hypergeometric distribution
int sci_distfun_cdfhyge(char* fname,unsigned long l);
int sci_distfun_invhyge(char* fname,unsigned long l);
int sci_distfun_pdfhyge(char* fname,unsigned long l);

// Exponential distribution
int sci_distfun_pdfexp(char* fname,unsigned long l);
int sci_distfun_cdfexp(char* fname,unsigned long l);
int sci_distfun_invexp(char* fname,unsigned long l);

// Geometric distribution
int sci_distfun_pdfgeo(char* fname,unsigned long l);
int sci_distfun_cdfgeo(char* fname,unsigned long l);
int sci_distfun_invgeo(char* fname,unsigned long l);

// Log-Normale distribution
int sci_distfun_pdflogn(char* fname,unsigned long l);
int sci_distfun_cdflogn(char* fname,unsigned long l);
int sci_distfun_invlogn(char* fname,unsigned long l);

// Non-Central Chi distribution
int sci_distfun_invchn(char* fname,unsigned long l);
int sci_distfun_cdfchn(char* fname,unsigned long l);

// Non-Central F distribution
int sci_distfun_invfnc(char* fname,unsigned long l);
int sci_distfun_cdffnc(char* fname,unsigned long l);

// Negative Binomiale distribution
int sci_distfun_invnbn(char* fname,unsigned long l);
int sci_distfun_cdfnbn(char* fname,unsigned long l);
int sci_distfun_pdfnbn(char* fname,unsigned long l);

// Noncentral T distribution
int sci_distfun_cdfnct(char* fname,unsigned long l);
int sci_distfun_invnct(char* fname,unsigned long l);
int sci_distfun_pdfnct(char* fname,unsigned long l);

// Multivariate Normal
int sci_distfun_pdfmvn(char* fname,unsigned long l);

// Weibull distribution
int sci_distfun_cdfwbl(char* fname,unsigned long l);
int sci_distfun_invwbl(char* fname,unsigned long l);
int sci_distfun_pdfwbl(char* fname,unsigned long l);

// Extreme Value distribution
int sci_distfun_cdfev(char* fname,unsigned long l);
int sci_distfun_invev(char* fname,unsigned long l);
int sci_distfun_pdfev(char* fname,unsigned long l);

// Regularized Incomplete Gamma Function
int sci_distfun_incgamma(char* fname,unsigned long l);

#endif /* __SCI_GW_DISTFUNCDF_H__ */

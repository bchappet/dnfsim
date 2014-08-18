
// Copyright (C) 2012 - 2014 - Michael Baudin
// Copyright (C) 2011 - Digiteo - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

//
// gw_distfunrnd.h
//   Header for the DISTFUN rnd gateway.
//
#ifndef __SCI_GW_DISTFUNGRAND_H__
#define __SCI_GW_DISTFUNGRAND_H__

// Non-Uniform Random number generators
int sci_distfun_rndbeta(char *fname,unsigned long fname_len);
int sci_distfun_rndf(char *fname,unsigned long fname_len);
int sci_distfun_rndmul(char *fname,unsigned long fname_len);
int sci_distfun_rndgam(char *fname,unsigned long fname_len);
int sci_distfun_rndnorm(char *fname,unsigned long fname_len);
int sci_distfun_rndunf(char *fname,unsigned long fname_len);
int sci_distfun_rnduin(char *fname,unsigned long fname_len);
int sci_distfun_rndprm(char *fname,unsigned long fname_len);
int sci_distfun_rndnbn(char *fname,unsigned long fname_len);
int sci_distfun_rndbino(char *fname,unsigned long fname_len);
int sci_distfun_rndmn(char *fname,unsigned long fname_len);
int sci_distfun_rndmarkov(char *fname,unsigned long fname_len);
int sci_distfun_rndnch(char *fname,unsigned long fname_len);
int sci_distfun_rndnf(char *fname,unsigned long fname_len);
int sci_distfun_rndchi2(char *fname,unsigned long fname_len);
int sci_distfun_rndexp(char *fname,unsigned long fname_len);
int sci_distfun_rndpoiss(char *fname,unsigned long fname_len);
int sci_distfun_rndlogn(char *fname,unsigned long fname_len);
int sci_distfun_rndnct(char *fname,unsigned long fname_len);
int sci_distfun_rndwbl(char *fname,unsigned long fname_len);
int sci_distfun_rndev(char *fname,unsigned long fname_len);

#endif /* __SCI_GW_DISTFUNGRAND_H__ */

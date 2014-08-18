// Copyright (C) 2012 - 2014 - Michael Baudin
// Copyright (C) 2011 - DIGITEO - Michael Baudin
// Copyright (C) 2008 - INRIA
// Copyright (C) 2012 - Prateek Papriwal
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#ifndef _CDFLIB_H_
#define _CDFLIB_H_

#ifdef _MSC_VER
#if LIBCDFLIB_EXPORTS 
#define CDFLIB_IMPORTEXPORT __declspec (dllexport)
#else
#define CDFLIB_IMPORTEXPORT __declspec (dllimport)
#endif
#else
#define CDFLIB_IMPORTEXPORT
#endif

#undef __BEGIN_DECLS
#undef __END_DECLS
#ifdef __cplusplus
# define __BEGIN_DECLS extern "C" {
# define __END_DECLS }
#else
# define __BEGIN_DECLS /* empty */
# define __END_DECLS /* empty */
#endif

__BEGIN_DECLS

// Startup the library.
//
// Description
// This function must be called prior to any call 
// to any function in the library.
//
void cdflib_startup();

//////////////////////////////////////////////////////
//
// All the function below have the same behavior.
// They return a integer status which says if the computation 
// performed well : 
//  * returns CDFLIB_OK if the function can be computed.
//  * returns CDFLIB_ERROR in case of error.
//
// This is the status of a function which performs correctly.
static int CDFLIB_OK = 0;

// The state of a function which generates an error.
static int CDFLIB_ERROR = 1;

//////////////////////////////////////////////////////
// To compute P(X<=x)
static int CDFLIB_LOWERTAIL = 1;

// To compute P(X>x)
static int CDFLIB_UPPERTAIL = 0;

//////////////////////////////////////////////////////
// To compute log(f(x))
static int CDFLIB_LOGCOMPUTE = 1;

// To compute f(x)
static int CDFLIB_LOGNOT = 0;
////////////////////////////////////

// Configure the function which prints messages.
// This function must be called before calling any 
// function.
void cdflib_messageSetFunction ( void (* f)(char * message) );

// Configure the function which generates random numbers uniform in [0,1).
// This function must be called at least once.
void cdflib_randSetFunction ( double (* f)(void) );

// Configure the function which generates random integers uniform in [a,b].
// This function must be called before at least once.
void cdflib_randIntegerInRangeSetFunction ( double (* f)(double a, double b) );

/////////////////////////////////////////////////////////////////////////
//
// Noncentral F Distribution
//
/*     Formula  26.6.20   of   Abramowitz   and   Stegun,  Handbook  of */
/*     Mathematical  Functions (1966) is used to compute the cumulative */
/*     distribution function. */

/*                            WARNING */
/*     The computation time  required for this  routine is proportional */
/*     to the noncentrality  parameter  (PNONC).  Very huge  values of */
/*     this parameter can consume immense  computer resources.  This is */
/*     why the search range is bounded by 10,000. */

// Noncentral F PDF
//   
// Parameters
// Input:
//   x : x is real, x>=0.
//   Dfn : numerator degrees of freedom, Dfn>0 (can be non integer).
//   Dfd : denominator degrees of freedom, Dfd>0 (can be non integer).
//   Pnonc : the noncentrality parameter, Pnonc>=0
// Output:
//   y : the probability density.
//
// Description
//   Computes the probability distribution function of 
//   the Noncentral F distribution function.
//
//   The function definition is:
//
// TODO
//
// Examples
/* 
int status;
double y;
double expected;
status = cdflib_ncfpdf(5, 4, 12, 0.3,&y);
expected = 0.011247305243159637;
*/ 
// Bibliography
// http://en.wikipedia.org/wiki/Noncentral_F-distribution
int cdflib_ncfpdf(double x, double dfn, double dfd, double phonc, double *y);

// Noncentral F CDF
//   
// Parameters
// Input:
//   x : the outcome, x>=0
// Output:
//   p : the probability, P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
//
// Examples
/*
double p;
int status;
double expected;
status = cdflib_ncfcdf(5, 4, 12, 0.3, CDFLIB_LOWERTAIL, &p);
expected = 0.98319765219863320;
*/
int cdflib_ncfcdf(double x, double dfn, double dfd, double phonc, int lowertail, double *p);

// Noncentral Noncentral F Inverse CDF
//   
// Parameters
// Input:
//   p : the probability, p in [0,1], P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
// Output:
//   x : the outcome, x>=0
//
// Examples
/* 
int status;
double x;
double expected;
status = cdflib_ncfinv(0.7, 4, 12, 0.3,CDFLIB_LOWERTAIL,&x);
expected = 1.4786561151681499;
*/ 
int cdflib_ncfinv(double p, double dfn, double dfd, double phonc, int lowertail, double *x);

// Generate Noncentral F Random Variables
//
// Method
/*     Directly generates ratio of noncentral numerator chisquare variate */
/*     to central denominator chisquare variate. */
int cdflib_ncfrnd (double v1, double v2, double xnonc, double *x);

/////////////////////////////////////////////////////////////////////////
//
// Noncentral Chi-Square Distribution
//

/*                              Method */
/*     Formula  26.4.25   of   Abramowitz   and   Stegun,  Handbook  of */
/*     Mathematical  Functions (1966) is used to compute the cumulative */
/*     distribution function. */
/*     Computation of other parameters involve a seach for a value that */
/*     produces  the desired  value  of P.   The search relies  on  the */
/*     monotinicity of P with the other parameter. */
/*                            WARNING */
/*     The computation time  required for this  routine is proportional */
/*     to the noncentrality  parameter  (PNONC).  Very huge  values of */
/*     this parameter can consume immense  computer resources.  This is */
/*     why the search range is bounded by 10,000. */

// Noncentral Chi-Square PDF
//   
// Parameters
// Input:
//   x : the outcome, x>=0
//   df : the number of degrees of freedom. df>0 (can be non-integer)
//   pnonc : a matrix of doubles, the noncentrality parameter, pnonc>=0
// Output:
//   y : the probability density.
//
// Description
//   Computes the probability distribution function of 
//   the Chi-square distribution function.
//
//   The function definition is:
//
// TODO
//
// Examples
/* 
int status;
double y;
double expected;
status = cdflib_chnpdf(9,5,4,&y);
expected = TODO;
*/ 
// Bibliography
// http://en.wikipedia.org/wiki/Noncentral_chi-squared_distribution
int cdflib_chnpdf(double x, double k, double pnonc, double *y);

// Noncentral Chi-Square CDF
//   
// Parameters
// Input:
//   x : the outcome, x>=0
// Output:
//   p : the probability, P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
//
// Examples
/* 
int status;
double p;
double expected;
status = cdflib_chncdf(9,4,5,CDFLIB_LOWERTAIL,&p);
expected = 0.56923667955740609;
*/ 
int cdflib_chncdf(double x, double df, double pnonc, int lowertail, double *p);

// Noncentral Chi-Square Inverse CDF
//   
// Parameters
// Input:
//   p : the probability, p in [0,1], P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
// Output:
//   x : the outcome, x>=0
//
// Examples
/* 
int status;
double x;
double expected;
status = cdflib_chninv(0.7,4,5,CDFLIB_LOWERTAIL,&x);
expected = 11.007826582193562;
*/ 
int cdflib_chninv(double p, double df, double pnonc, int lowertail, double *x);

// Generate Noncentral Chi Random Variables
// Method
/*     Uses fact that  noncentral chisquare  is  the  sum of a  chisquare */
/*     deviate with k-1  degrees of freedom plus the  square of a normal */
/*     deviate with mean sqrt(XNONC) and standard deviation 1. */
int cdflib_chnrnd (double df, double pnonc, double *x);

//////////////////////////////////////////////////////
// To compute enable verbose mode
static int CDFLIB_VERBOSEOFF = 0;

// To compute disable verbose mode
static int CDFLIB_VERBOSEON = 1;

// Configure the verbose mode.
void cdflib_verboseset(int verbosemode);

/////////////////////////////////////////////////////////////////////////
//
// Negative Binomial Distribution
//

/*                              Method */
/*     Formula   26.5.26   of   Abramowitz  and  Stegun,  Handbook   of */
/*     Mathematical Functions (1966) is used  to  reduce calculation of */
/*     the cumulative distribution  function to that of  an  incomplete */
/*     beta. */

// Computes the Negative Binomial PDF
//
// Input:
//   x: a doubles, the extra trials for R successes, in the set {0,1,2,3,...}.
//   R : a doubles, the number of successes. R belongs to the set {0,1,2,3,4,.......}
//   Pr : a doubles, the probability of getting success in a Bernoulli trial. Pr in [0,1].
// Output:
//   p, output : the probability P(X=x)
//
// Description
//
//    The function definition is:
//
// <latex>
// \begin{eqnarray}
// f(x,R,P) = \frac{\Gamma(x+R)}{x!\Gamma(R)} P^R (1-P)^x
// \end{eqnarray}
// </latex>
//
// Analysis of the random variable. 
//
// Consider successive random trials, each having a constant 
// probability P of success. 
// The number of extra trials we perform in order to observe a given 
// number R of successes has a negative binomial distribution. 
//
// Examples
/*
double x=5;
double R=2;
double Pr=0.2;
double expected = 0.0786432;
int status;
double p;
status = cdflib_nbnpdf(x, R, Pr, &p);
*/
int cdflib_nbnpdf(double x, double R, double Pr, double *p);

// Computes the Negative Binomial CDF
//
// Input:
//   x: a doubles, the extra trials for R successes, in the set {0,1,2,3,...}.
//   R : a doubles, the number of successes. R belongs to the set {0,1,2,3,4,.......}
//   Pr : a doubles, the probability of getting success in a Bernoulli trial. Pr in [0,1].
//   lowertail : the tail
// Output:
//   p, output : the probability P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL.
//
// Examples
/*
double x=3;
double R=5;
double Pr=0.5;
double expected = 0.3632813;
int status;
double p;
int lowertail = CDFLIB_LOWERTAIL;
status = cdflib_nbncdf(x, R, Pr, lowertail, &p);
*/
int cdflib_nbncdf(double x, double R, double Pr, int lowertail, double *p);

// Computes the Negative Binomial Inverse CDF
//
// Input:
//   p, output : the probability P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL.
//   R : a doubles, the number of successes. R belongs to the set {0,1,2,3,4,.......}
//   Pr : a doubles, the probability of getting success in a Bernoulli trial. Pr in [0,1].
//   lowertail : the tail
// Output:
//   x: a doubles, the extra trials for R successes, in the set {0,1,2,3,...}.
//
// Examples
/*
double p=0.21;
double R=10;
double Pr=0.5;
double expected = 6;
int status;
int lowertail = CDFLIB_LOWERTAIL;
double x;
status = cdflib_nbninv(p, R, Pr, lowertail, &x);
*/
int cdflib_nbninv(double p, double R, double Pr, int lowertail, double *x);

// Generate Negative Binomial Random Variables
// Function
/*     Generates a single random deviate from a negative binomial */
/*     distribution. */
// Arguments
/*     N  : Required number of events. (N > 0) */
/*     P  : The probability of an event during a Bernoulli trial. (0.0 < P < 1.0) */
// Method
/*     Algorithm from page 480 of */
/*     Devroye, Luc */
/*     Non-Uniform Random Variate Generation.  Springer-Verlag, */
/*     New York, 1986. */
int cdflib_nbnrnd (int n, double p, int *x);


/////////////////////////////////////////////////////////////////////////
//
// Hypergeometric Distribution
//

// Computes the Hypergeometric PDF
//
// Input:
//   x : the number of successful draws in the experiment. x belongs to the set [0,min(k,N)]
//   M : the total size of the population. M belongs to the set {0,1,2,3........}
//   k : the number of successful states in the population. k belongs to the set {0,1,2,3,.......M-1,M}
//   N : the total number of draws in the experiment. N belongs to the set {0,1,2,3.......M-1,M}
// Output:
//   p, output : the probability P(X=x).
//
// Description
//   The function definition is:
//
//f(x,M,k,N) = \frac{\binom{k}{x} \binom{M-k}{N-x}}{\binom{M}{N}}
//
// with the convention that $\binom{M-k}{N-x}$ is zero if <literal>N-x > M-k</literal>.
//
// From the definition of the PDF, the input arguments must be so that:
// x<=N
// x<=k
// k<=M
// N<=M
//
// If one of these conditions is not satisfied, 
// an error is generated.
//
// The implementation uses the fact that:
//
// f(x)=binomialpdf(x,k,p)*binomialpdf(N-x,M-k,p)/binomialpdf(N,M,p)
//
// with p=N/M.
//
// Examples
/* 
int status;
double y;
status = cdflib_hygepdf(20,80,50,30,&y);
expected = 0.1596136;
*/
//
// References
// http://svn.r-project.org/R/trunk/src/nmath/dhyper.c
int cdflib_hygepdf(double x, double M, double k, double N, double *p);
int cdflib_hygeCheckX(char * fname, double x, double M, double k, double N);
int cdflib_hygeCheckParams(char * fname, double M, double k, double N);

// Computes the hypergeometric CDF
//
// Input:
//   lowertail : the tail. 
// Output:
//   p : the probability, P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL.
//
// Examples
/* 
int status;
double y;
status = cdflib_hygecdf(20,80,50,30,CDFLIB_LOWERTAIL,&y);
expected = 0.7974774;
*/
int cdflib_hygecdf(double x, double M, double k, double N, int lowertail, double *p);

// Computes the inverse hypergeometric CDF
//
// Input:
//   p : the probability, p in [0,1], P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL.
//   lowertail : the tail. 
// Output:
//   x : the number of successful draws in the experiment. x belongs to the set [0,min(k,N)]
//
// Examples
/*
status = cdflib_hygeinvcdf(0.2,80,50,30,CDFLIB_LOWERTAIL,&y)
expected = 17
*/
int cdflib_hygeinv(double p, double M, double k, double N, int lowertail, double *x);

// Returns hypergeometric outcomes
//
// Output:
//   x : the number of successful draws in the experiment. x belongs to the set [0,min(k,N)]
//
// Description
// Invert the hypergeometric CDF.
//
// Examples
/* 
double x;
int status;
status = cdflib_hygeinvcdf(80,50,30,&x);
expected = 17
*/
int cdflib_hygernd (double M, double k, double N, double *x);

/////////////////////////////////////////////////////////////////////////
//
// Gamma Distribution
//

/*                              Method */

/*     Cumulative distribution function (P) is calculated directly by */
/*     the code associated with: */

/*     DiDinato, A. R. and Morris, A. H. Computation of the  incomplete */
/*     gamma function  ratios  and their  inverse.   ACM  Trans.  Math. */
/*     Softw. 12 (1986), 377-393. */

// Gamma PDF
//
// Parameters
// Input:
//   x : the outcome, x>=0
//   a : the shape parameter, a>0.
//   b : the scale parameter, b>0.
// Output:
//   y : the density
//
// Description
//   Computes the Gamma probability distribution function.
//
//   The function definition is:
//
//f(x,a,b) = \frac{1}{b^a\Gamma(a)} x^{a-1} \exp\left(-\frac{x}{b}\right)
//
// Compatibility note. 
// 
// This function is compatible with Matlab, but 
// not compatible with R. 
// Indeed, notice that b, the scale, is the inverse of the rate. 
// Other computing languages (including R), use 1/b as the 
// second parameter of the Gamma distribution.
//
// Examples
/* 
int status;
double y;
double expected;
status = distfun_gampdf(1,1,5,&y);
expected = 1.637461506155963586D-01;
*/
int cdflib_gammapdf( double x , double a , double b , double * y );

// Gamma CDF
//
// Parameters
// Input:
//   x : the outcome
// Output:
//   p : the probability, P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
//
// Examples
/*
int status;
double p;
double expected;
status = cdflib_gamcdf(1,2,3,CDFLIB_LOWERTAIL,&p);
expected = 0.0446249192349476506148;
*/
int cdflib_gamcdf(double x, double a, double b, int lowertail, double *p );

// Gamma Inverse CDF
//
// Parameters
// Input:
//   p : the probability, p in [0,1], P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
// Output:
//   x : the outcome
//
// Examples
/*
int status;
double x;
double expected;
status = cdflib_gaminv(0.5,2,3,CDFLIB_LOWERTAIL,&x);
expected = 5.0350409700499820786490;
*/
int cdflib_gaminv(double p, double a, double b, int lowertail, double *x);

// Generate Gamma Random Variables
// Method
/*     Renames cdflib_sgamma from TOMS as slightly modified by BWB to use RANF */
/*     instead of SUNIF. */
/* Case A >= 1.0: */
/*     Ahrens, J.H. and Dieter, U. */
/*     Generating Gamma Variates by a */
/*     Modified Rejection Technique. */
/*     Comm. ACM, 25,1 (Jan. 1982), 47 - 54. */
/*     Algorithm GD */
/*     JJV altered the following to reflect cdflib_sgamma argument ranges */
/* Case 0.0 < A < 1.0: */
/*     Ahrens, J.H. and Dieter, U. */
/*     Computer Methods for Sampling from Gamma, */
/*     Beta, Poisson and Binomial Distributions. */
/*     Computing, 12 (1974), 223-246/ */
/*     Adapted algorithm GS. */
int cdflib_gamrnd(double a, double b, double *x);

/////////////////////////////////////////////////////////////////////////
//
// F Distribution
//

/*                              Method */
/*     Formula   26.6.2   of   Abramowitz   and   Stegun,  Handbook  of */
/*     Mathematical  Functions (1966) is used to reduce the computation */
/*     of the  cumulative  distribution function for the  F  variate to */
/*     that of an incomplete beta. */

// F PDF
//   
// Parameters
// Input:
//   x : x is real and x>=0.
//   v1 : numerator degrees of freendom, v1>0. 
//   v2 : denominator degrees of freendom, v2>0.
// Output:
//   y : the probability density.
//
// Description
//   Computes the probability distribution function of 
//   the f distribution function.
//   
//   Any scalar input argument is expanded to a matrix of doubles 
//   of the same size as the other input arguments.
//
// The F distribution has density
//
// f(x) = \frac{\sqrt{\frac{(v_1 x)^{v_1} v_2^{v_2}}{(v_1 x+v_2)^{v_1+v_2}}}}{x B\left(\frac{v_1}{2},\frac{v_2}{2}\right)}
//
// Examples
/*
int status;
double y;
double expected;
status = cdflib_fpdf(3,4,5,&y);
expected = 0.0681795538575894199962;
*/
//
// Bibliography
// Catherine Loader, http://svn.r-project.org/R/trunk/src/nmath/df.c 
//
int cdflib_fpdf(double x,double v1,double v2, double * y);

// F CDF
//   
// Parameters
// Input:
//   x : x is real and x>=0.
// Output:
//   p : the probability, P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
//
// Examples
/* 
int status;
double p;
double expected;
status = cdflib_fcdf(3,4,5,CDFLIB_LOWERTAIL,&p);
expected = 0.8702965153994906000179;
*/
int cdflib_fcdf(double x, double v1, double v2, int lowertail, double *p);

// F Inverse CDF
//   
// Parameters
// Input:
//   p : the probability, p in [0,1], P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
// Output:
//   x : x is real and x>=0.
//
// Examples
/* 
int status;
double x;
double expected;
status = cdflib_finv(0.7,4,5,CDFLIB_LOWERTAIL,&x);
expected = 1.6286329978911637805794;
*/
int cdflib_finv(double p, double v1, double v2, int lowertail, double *x);

// Generate F Random Variables
//
// Method
/*     Directly generates ratio of chisquare variates */
int cdflib_frnd(double v1, double v2, double *x);

/////////////////////////////////////////////////////////////////////////
//
// Binomial Distribution
//

/*                              Method */

/*     Formula  26.5.24    of   Abramowitz  and    Stegun,  Handbook   of */
/*     Mathematical   Functions (1966) is   used  to reduce the  binomial */
/*     distribution  to  the  cumulative incomplete    beta distribution. */

// Binomial PDF
//
// Parameters
// Input:
//   x : the number of Bernoulli trials after in which success occurs . x belongs to the set {0,1,2,3,...,N}. 
//       x has integer value.
//   n : the total number of binomial trials . N belongs to the set {1,2,3,4,.......}
//       n has integer value.
//   pr : the probability of success in a Bernoulli trial 
// Output:
//   y : the probability density.
//
// Description
//   Computes the probability distribution function of 
//   the Binomial distribution function.
//   
//   Any scalar input argument is expanded to a matrix of doubles 
//   of the same size as the other input arguments.
//
//    The function definition is:
//
//f(x,n,pr) = \binom{n}{x} p_r^x (1-p_r)^{n-x}
//
//Examples
/* 
int status;
double y;
double expected;
status = cdflib_binopdf(3,200,0.02,&y);
expected = 0.1963468373887976292647;
*/
int cdflib_binopdf(double x, double n, double p, double * y);

// Binomial CDF
//
// Parameters
// Input:
//   x : the number of Bernoulli trials after in which success occurs . x belongs to the set {0,1,2,3,...,N}. 
//       x has integer value.
// Output:
//   p : the probability, P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
//
//Examples
/* 
int status;
double p;
double expected;
status = cdflib_binocdf(3,200,0.02,CDFLIB_LOWERTAIL,&p);
expected = 0.4314949731615046468747;
*/
int cdflib_binocdf(double x, double n, double pr, int lowertail, double *p);

// Binomial Inverse CDF
//
// Parameters
// Input:
//   p : the probability, p in [0,1], P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
// Output:
//   x : the number of Bernoulli trials after in which success occurs . x belongs to the set {0,1,2,3,...,N}. 
//       x has integer value.
//
//Examples
/* 
int status;
double x;
double expected;
status = cdflib_binoinv(0.9,200,0.02,CDFLIB_LOWERTAIL,&x);
expected = 7.;
*/
int cdflib_binoinv(double p, double n, double pr, int lowertail, double *x);

// Generate Binomial Random Variables
/*
If n<=2147483647, uses :
Method
This is algorithm BTPE from:
Kachitvichyanukul, V. and Schmeiser, B. W.
Binomial Random Variate Generation.
Communications of the ACM, 31, 2
(February, 1988) 216.

If n is larger, invert the CDF.
*/
int cdflib_binornd (double n, double pr, double *x);

/////////////////////////////////////////////////////////////////////////
//
// Normal Distribution
//

/*                              Method */

/*     A slightly modified version of ANORM from */

/*     Cody, W.D. (1993). "ALGORITHM 715: SPECFUN - A Portabel FORTRAN */
/*     Package of Special Function Routines and Test Drivers" */
/*     acm Transactions on Mathematical Software. 19, 22-32. */

/*     is used to calulate the  cumulative standard normal distribution. */

/*     The rational functions from pages  90-95  of Kennedy and Gentle, */
/*     Statistical  Computing,  Marcel  Dekker, NY,  1980 are  used  as */
/*     starting values to Newton's Iterations which compute the inverse */
/*     standard normal.  Therefore no  searches  are necessary for  any */
/*     parameter. */

/*     For X < -15, the asymptotic expansion for the normal is used  as */
/*     the starting value in finding the inverse standard normal. */
/*     This is formula 26.2.12 of Abramowitz and Stegun. */

// Normal PDF
//
// Parameters
// Input:
//   x : the outcome
//   mu : the mean 
//   sigma : the standard deviation, sigma > 0
// Output:
//   y : the density
//
// Description
//   Computes the probability distribution function 
// of the Normal (Laplace-Gauss) function.
//
//   The function definition is:
//
//f(x,\mu,\sigma) = \frac{1}{\sigma\sqrt{2\pi}} \exp\left(\frac{-(x-\mu)^2}{2\sigma^2}\right)
//
// Examples
/* 
int status;
double y;
double expected;
status = cdflib_normpdf ( 2 , 1.0 , 2.0, &y );
expected = 0.176032663382150;
*/
int cdflib_normpdf(double x, double mu, double sigma, double *y);

// Normal CDF
//
// Parameters
// Input:
//   x : the outcome
// Output:
//   p : the probability, P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
//
// Examples
/* 
int status;
double p;
double expected;
status = cdflib_normcdf ( 2 , 1.0 , 2.0, CDFLIB_LOWERTAIL, &p );
expected = 0.6914624612740130071842;
*/
int cdflib_normcdf(double x, double mu, double sigma, int lowertail, double *p);

// Normal Inverse CDF
//
// Parameters
// Input:
//   p : the probability, p in [0,1], P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
// Output:
//   x : the outcome
//
// Examples
/* 
int status;
double x;
double expected;
status = cdflib_norminv ( 0.7 , 1.0 , 2.0, CDFLIB_LOWERTAIL, &x );
expected = 2.0488010254160813339297;
*/
int cdflib_norminv(double p, double mu, double sigma, int lowertail, double *x);

// Generate Normal Random Variables
// Method
/*     Renames cdflib_snorm from TOMS as slightly modified by BWB to use RANF */
/*     instead of SUNIF. */
/*     Ahrens, J.H. and Dieter, U. */
/*     Extensions of Forsythe's Method for Random */
/*     Sampling from the Normal Distribution. */
/*     Math. Comput., 27,124 (Oct. 1973), 927 - 937. */
int cdflib_normrnd (double mu, double sigma, double *x);

/////////////////////////////////////////////////////////////////////////
//
// Uniform Distribution
//

// Computes the Uniform PDF
//
// Parameters
// Input:
//   x : the outcome
//   a : the lower bound
//   b : the upper bound
// Output:
//   y : the density.
//
// Description
//   Computes the probability distribution function of 
//   the Uniform distribution function.
//   
//   Any scalar input argument is expanded to a matrix of doubles 
//   of the same size as the other input arguments.
//
//   The function definition is:
//
//f(x,a,b) = 1/(b-a), if x\in[a,b],
//           0,       otherwise.
//
// Examples
// int status;
// double y;
//  status = cdflib_unifpdf ( 4, 3, 6, &y );
//  yexpected = 0.33333;
//
int cdflib_unifpdf(double x, double a, double b, double *y);
int cdflib_unifCheckX(char * fname , double x);
int cdflib_unifCheckParams(char * fname , double a, double b);

// Computes the Uniform CDF
//
// Input:
//   lowertail : the tail. 
// Output:
//   p : the probability, P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL.
//
// Examples
// int status;
// double y;
//  status = cdflib_unifcdf ( 2, 1, 5, CDFLIB_LOWERTAIL, &y );
//  pexpected = 0.25;
//
int cdflib_unifcdf(double x, double a, double b, int lowertail, double *p);

// Computes the Uniform Inverse CDF
//
// Input:
//   p : the probability, p in [0,1], P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL.
//   lowertail : the tail. 
// Output:
//   x : the outcome.
//
// Examples
// int status;
// double x;
//  status = cdflib_unifinv ( 0.7, 1., 2., CDFLIB_LOWERTAIL, &x );
//  xexpected = 1.7;
//
int cdflib_unifinv(double p, double a, double b, int lowertail, double *x);

// Generate Uniform Random Variables
//
/*               GeNerate Uniform DOUBLE PRECISION between LOW and HIGH */
// Function
/*     Generates a DOUBLE PRECISION uniformly distributed between LOW and HIGH. */
// Arguments
/*     a : Low bound (exclusive) */
/*     b : High bound (exclusive) */
int cdflib_unifrnd(double a, double b, double *x);

/////////////////////////////////////////////////////////////////////////
//
// Chi-Squared Distribution
//

/*                              Method */

/*     Formula    26.4.19   of Abramowitz  and     Stegun, Handbook  of */
/*     Mathematical Functions   (1966) is used   to reduce the chisqure */
/*     distribution to the incomplete distribution. */


// Chi-Squared PDF
//   
// Parameters
// Input:
//   x : the outcome, x>=0
//   k : the number of degrees of freedom. k>0 (can be non-integer)
// Output:
//   y : the probability density.
//
// Description
//   Computes the probability distribution function of 
//   the Chi-square distribution function.
//
//   The function definition is:
//
//f(x,k) = \frac{1}{2^{\frac{k}{2}}\Gamma\left(\frac{k}{2}\right)} x^{\frac{k}{2}-1} \exp\left(-\frac{x}{2}\right)
//
// Examples
/* 
int status;
double y;
double expected;
status = cdflib_chi2pdf(4,5,&y);
expected = 0.1439759107018347694673;
*/ 
// Bibliography
// http://en.wikipedia.org/wiki/Chi-squared_distribution
int cdflib_chi2pdf(double x, double k, double *y);

// Chi-Squared CDF
//   
// Parameters
// Input:
//   x : the outcome, x>=0
// Output:
//   p : the probability, P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
//
// Examples
/* 
int status;
double p;
double expected;
status = cdflib_chi2cdf(4,5,CDFLIB_LOWERTAIL,&p);
expected = 0.4505840486472180161925;
*/ 
int cdflib_chi2cdf(double x, double k, int lowertail, double *p);

// Chi-Squared Inverse CDF
//   
// Parameters
// Input:
//   p : the probability, p in [0,1], P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
// Output:
//   x : the outcome, x>=0
//
// Examples
/* 
int status;
double x;
double expected;
status = cdflib_chi2inv(0.7,5,CDFLIB_LOWERTAIL,&x);
expected = 6.0644299841548976459649;
*/ 
int cdflib_chi2inv(double p, double k, int lowertail, double *x);

// Generate Chi Squared Random Variables
//
// Arguments
/*     k : Degrees of freedom of the chisquare, k>0 */
// Method
/*     Uses relation between chisquare and gamma. */
int cdflib_chi2rnd (double k, double *x);

/////////////////////////////////////////////////////////////////////////
//
// Geometric Distribution
//

// Geometric PDF
//   
// Parameters
// Input:
//   x : the number of Bernoulli trials after which the first success occurs. x belongs to the set {0,1,2,3,.....}
//       x must have an integer value. If not, an error is generated.
//   pr : the probability of success in a Bernoulli trial. pr in (0,1]
// Output:
//   y : the probability density.
//
// Description
//   Computes the probability distribution function of 
//   the Geometric distribution function.
//   
//   The function definition is:
//
//f(x,pr) = pr\left(1-pr\right)^x
//
// Compatibility Note : x belongs to the set {0,1,2,3,...}. 
// This choice is compatible with Matlab and R. 
// This is different from Scilab v5 grand(m,n,"geom"), which 
// uses {1,2,3,...}. 
//
// Examples
// int status;
// double y;
// status = cdflib_geopdf(3,0.5,&y);
// yexpected = 0.0625;
//
// Bibliography
// http://en.wikipedia.org/wiki/Geometric_distribution
int cdflib_geopdf(double x, double pr, double *y);
int cdflib_geoCheckParams(char * fname, double pr);
int cdflib_geoCheckX(char * fname, double x);

// Geometric CDF
//
// Parameters
// Input:
//     lowertail: the tail.
// Output:
//     p: the probability, P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
//
// Examples
// int status;
// double p;
// status = cdflib_geocdf(3,0.5, CDFLIB_LOWERTAIL, &p);
// expected = 0.9375;
//
int cdflib_geocdf(double x, double pr, int lowertail, double *p);

// Geometric InverseCDF
//
// Parameters
// Input:
//     p: the probability, p in [0,1], P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
//     lowertail: the tail.
// Output:
//     x: the outcome
//
// Examples
// int status;
// double x;
// status = cdflib_geoinv(0.999,0.5, CDFLIB_LOWERTAIL, &x);
// xexpected = 9;
//
int cdflib_geoinv(double p, double pr, int lowertail, double *x);

// Generate Geometric Random Variables
/*
*  METHOD 
*     inversion of the cdf leads to :
*
*     (1)   X = floor( log(1-u) / log(1-p) )
*
*     u being a random deviate from U[0,1).
*
*     by taking into account that 1-u follows also U(0,1)) this may be 
*     replaced with X = ceil( log(u) / log(1-p) )-1 or floor(log(u)/log(1-p))
*     which needs less work. But as ranf() provides number in [0,1[ , 0 may be 
*     gotten and these formulae may give then +oo.
* 
*/
int cdflib_geornd(double pr, double *x);

/////////////////////////////////////////////////////////////////////////
//
// T Distribution
//

/*                              Method */

/*     Formula  26.5.27  of   Abramowitz   and  Stegun,   Handbook   of */
/*     Mathematical Functions  (1966) is used to reduce the computation */
/*     of the cumulative distribution function to that of an incomplete */
/*     beta. */

// T PDF
//
// Parameters
// Input:
//   x : the outcome.
//   v : the number of degrees of freedom, v>0. 
// Output:
//   y : the density
//
// Description
//   Computes the T probability distribution function.
//
// The function definition is:
//
//f(x,v) = \frac{\Gamma\left(\frac{v+1}{2}\right)}{\Gamma\left(\frac{v}{2}\right)} \frac{1}{\sqrt{v\pi}} \frac{1}{\left(1+\frac{x^2}{v}\right)^{\frac{v+1}{2}}}
//
// Examples
/* 
int status;
double y;
double expected;
status = cdflib_tpdf(-3, 2, &y);
expected = 0.0274101222343421414840;
*/
int cdflib_tpdf(double x, double v, double *y);

// T CDF
//
// Parameters
// Input:
//   x : the outcome.
// Output:
//   p : the probability, p in [0,1], P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
//
// Examples
/* 
int status;
double p;
double expected;
status = cdflib_tcdf(-3, 2, CDFLIB_LOWERTAIL, &p);
expected = 0.0477329831333545562266;
*/
int cdflib_tcdf(double x, double v, int lowertail, double *p);

// T Inverse CDF
//
// Parameters
// Input:
//   p : the probability, p in [0,1], P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
// Output:
//   x : the outcome.
//
// Examples
/* 
int status;
double x;
double expected;
status = cdflib_tinv(0.7, 2, CDFLIB_LOWERTAIL, &x);
expected = 0.6172133998483675387803;
*/
int cdflib_tinv(double p, double v, int lowertail, double *x);

// Generate T Random Variables
// Function
/*     Generates a single random deviate from a T distribution */
// Arguments
/*     v : the number of degrees of freedom, v>0. */
int cdflib_trnd(double v, double *x);

/////////////////////////////////////////////////////////////////////////
//
// Noncentral T Distribution
//

/*                                Method */

/*     Upper tail    of  the  cumulative  noncentral t is calculated usin */
/*     formulae  from page 532  of Johnson, Kotz,  Balakrishnan, Coninuou */
/*     Univariate Distributions, Vol 2, 2nd Edition.  Wiley (1995) */

// Noncentral T PDF
//
// Parameters
// Input:
//   x : the outcome.
//   DF : the number of degrees of freedom, DF>0. 
//   PNONC : a matrix of doubles, the noncentrality parameter, PNONC is real
// Output:
//   y : the density
//
// Description
//   Computes the Noncentral T probability distribution function.
//
// The function definition is:
//
//TODO
//
// Examples
/* 
int status;
double y;
double expected;
double PNONC=10;
status = cdflib_nctpdf(-3, 2, PNONC, &y);
expected = TODO;
*/
int cdflib_nctpdf(double x, double DF, double PNONC, double *y);

// Noncentral T CDF
//
// Parameters
// Input:
//   x : the outcome.
// Output:
//   p : the probability, p in [0,1], P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
//
// Examples
/* 
int status;
double p;
double expected;
double PNONC=10;
status = cdflib_tcdf(-3, 2, PNONC, CDFLIB_LOWERTAIL, &p);
expected = 0.0477329831333545562266;
*/
int cdflib_nctcdf(double x, double DF, double PNONC, int lowertail, double *p);

// Noncentral T Inverse CDF
//
// Parameters
// Input:
//   p : the probability, p in [0,1], P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
// Output:
//   x : the outcome.
//
// Examples
/* 
int status;
double x;
double expected;
double PNONC=10;
status = cdflib_nctinv(0.7, 2, PNONC, CDFLIB_LOWERTAIL, &x);
expected = 16.768450077819693;
*/
int cdflib_nctinv(double p, double DF, double PNONC, int lowertail, double *x);

// Generate Noncentral T Random Variables
// Function
/*     Generates a single random deviate from a Noncentral T distribution */
// Arguments
/*     v : the number of degrees of freedom, v>0. */
// Method : 
// Compute (z+PNONC)/sqrt(t/DF)
// where 
// z ~ Normal(0,1)
// t ~ Noncentral Chi-Square(DF)
int cdflib_nctrnd(double DF, double PNONC, double *x);

/////////////////////////////////////////////////////////////////////////
//
// Poisson Distribution
//

/*                              Method */

/*     Formula   26.4.21  of   Abramowitz  and   Stegun,   Handbook  of */
/*     Mathematical Functions (1966) is used  to reduce the computation */
/*     of  the cumulative distribution function to that  of computing a */
/*     chi-square, hence an incomplete gamma function. */

// Poisson PDF
//   
// Parameters
// Input:
//   x : the number of occurrences of events. x belongs to the set {0,1,2,3,.....}.
//       x must have an integer value.
//   lambda : the average rate of occurrence. We must have lambda>0.
// Output:
//   y : the probability density.
//
// Description
//   Computes the probability distribution function of 
//   the poisson distribution function.
//
//   The function definition is:
//
//f(x,\lambda) = \frac{\lambda^x e^{-\lambda}}{x!}
//
// Note : x belongs to the set {0,1,2,3,...}.
//
// Examples
/*
int status;
double y;
double expected;
status = cdflib_poisspdf(0,2,&y);
expected = 0.1353352832366127023178;
*/
// Bibliography
// http://en.wikipedia.org/wiki/Poisson_distribution
int cdflib_poisspdf(double x, double lambda, double *y);
int cdflib_poissCheckX(char * fname, double x);
int cdflib_poissCheckParams(char * fname, double lambda);

// Poisson CDF
//   
// Parameters
// Input:
//   x : the outcome
// Output:
//   p : the probability, p in [0,1], P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
//
// Examples
/*
int status;
double p;
double expected;
status = cdflib_poisscdf(1,2,CDFLIB_LOWERTAIL,&p);
expected = 0.4060058497098381069534;
*/
int cdflib_poisscdf(double x, double lambda, int lowertail, double *p);

// Poisson Inverse CDF
//   
// Parameters
// Input:
//   p : the probability, p in [0,1], P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
// Output:
//   x : the outcome
//
// Examples
/*
int status;
double x;
double expected;
status = cdflib_poissinv(0.7,2,CDFLIB_LOWERTAIL,&x);
expected = 3.;
*/
int cdflib_poissinv(double p, double lambda, int lowertail, double *x);

// Generate Poisson Random Variables
/*
Method
Renames KPOIS from TOMS as slightly modified by BWB to use RANF
instead of SUNIF.

References
AHRENS, J.H. AND DIETER, U.
COMPUTER GENERATION OF POISSON DEVIATES
FROM MODIFIED NORMAL DISTRIBUTIONS.
ACM TRANS. MATH. SOFTWARE, 8,2 (JUNE 1982), 163 - 179.
(SLIGHTLY MODIFIED VERSION OF THE PROGRAM IN THE ABOVE ARTICLE)
*/
int cdflib_poissrnd (double lambda, double *x);

/////////////////////////////////////////////////////////////////////////
//
// Log-Normal Distribution
//

// Log-Normal PDF
//
// Parameters
// Input:
//     x: the outcome
//     mu: the mean of the underlying normal variable.
//     sigma: the variance of the underlying normal variable, sigma>0.
// Output:
//     y: the density
//
// Description
//   This function computes the Lognormal PDF.
//
//   Any scalar input argument is expanded to a matrix of doubles of the same size 
//   as the other input arguments.
//
// The Lognormal distribution with parameters mu and sigma has density
//
// f(x) = \frac{1}{\sigma x \sqrt{2 \pi}} \exp\left(-\frac{(\ln(x) - \mu)^2}{2 \sigma^2}\right), 
// if x> 0, and zero otherwise.
//
// Examples
// int status;
// double y;
// status = cdflib_lognpdf ( 2 , 0.0 , 1.0 , &y);
// expected = 0.156874019278981119;
//
// Bibliography
// Dider Pelat, "Bases et méthodes pour le traitement de données", section 8.2.8, "Loi log-normale".
// Wikipedia, Lognormal distribution, http://en.wikipedia.org/wiki/Log-normal_distribution
int cdflib_lognpdf(double x, double mu, double sigma, double *y);
int cdflib_lognCheckParams(char * fname, double mu, double sigma);
int cdflib_lognCheckX(char * fname, double x);

// Log-Normal CDF
//
// Parameters
// Input:
//     x: the outcome
// Output:
//     p: the probability, P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
//
// Examples
// int status;
// double p;
// status = cdflib_logncdf(2., 3., 10., CDFLIB_LOWERTAIL, &p);
// expected = 0.4087797;
int cdflib_logncdf(double x, double mu, double sigma, int lowertail, double *p);

// Log-Normal Inverse CDF
//
// Parameters
// Input:
//     p: the probability, p in [0,1], P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
// Output:
//     x: the outcome
//
// Examples
// int status;
// double x;
// status = cdflib_logninv(0.6, 1., 2., CDFLIB_LOWERTAIL, &x);
// expected = 4.5117910634839439865;
int cdflib_logninv(double p, double mu, double sigma, int lowertail, double *x);

// Generate Log-Normal Random Variables
// Function
/*     Generates a single random deviate from a log-normal distribution */
// Arguments
/*     mu : the mean of the underlying normal variable. */
/*     sigma : the standard deviation of the underlying normal variable. sigma>0. */
int cdflib_lognrnd(double mu, double sigma, double *x);

/////////////////////////////////////////////////////////////////////////
//
// Exponential Distribution
//

// Exponential PDF
//
// Parameters
// Input:
//     x: the outcome
//     mu : the average. mu>0
// Output:
//     y: the density
//
// Description
//   This function computes the Exponential PDF.
//
//   Any scalar input argument is expanded to a matrix of doubles of the same size 
//   as the other input arguments.
//
// Any optional input argument equal to the empty matrix will be set to its
// default value.
//
// The exponential distribution with average mu has density
//
// f(x) = \frac{1}{\mu} e^{\frac{-x}{\mu}}
//
// for x >= 0 and is zero if x<0.
//
// Compatibility note. 
// 
// Notice that mu, the average, is the inverse of the rate. 
// Other computing languages (including R), use 1/mu as the 
// parameter of the exponential distribution.
//
// Examples
// double y;
// int status;
// status=cdflib_exppdf(3,2,&y);
// expected=0.111565080074214909;
//
// Bibliography
// Wikipedia, Exponential distribution function, http://en.wikipedia.org/wiki/Exponential_distribution
int cdflib_exppdf(double x, double mu, double *y);
int cdflib_expCheckX(char * fname, double x);
int cdflib_expCheckParams(char * fname, double mu);

// Exponential CDF
//
// Parameters
// Input:
//     x: the outcome
// Output:
//     p: the probability, P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
//
// Examples
// double p;
// int status;
// status=cdflib_expcdf(2.,1./3.,CDFLIB_LOWERTAIL,&p);
// expected=9.975212478233337e-1;
//
int cdflib_expcdf(double x, double mu, int lowertail, double *p);

// Exponential Inverse CDF
//
// Parameters
// Input:
//     p: the probability, p in [0,1], P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
// Output:
//     x: the outcome
//
// Examples
// double x;
// int status;
// status=cdflib_expinv(0.7,2.,CDFLIB_LOWERTAIL,&x);
// expected=2.4079456;
//
int cdflib_expinv(double p, double mu, int lowertail, double *x);

// Generate Exponential Random Variables
//
// Arguments
/*     mu : The mean of the exponential distribution from which */
/*            a random deviate is to be generated. (mu >= 0) */
// Method
/* Ahrens, J.H. and Dieter, U. */
/* Computer Methods for Sampling From the */
/* Exponential and Normal Distributions. */
/* Comm. ACM, 15,10 (Oct. 1972), 873 - 882. */
int cdflib_exprnd (double mu, double *x);

/////////////////////////////////////////////////////////////////////////
//
// Beta Distribution
//

// Method :
/*     DiDinato, A. R. and Morris,  A.   H.  Algorithm 708: Significant */
/*     Digit Computation of the Incomplete  Beta  Function Ratios.  ACM */
/*     Trans. Math.  Softw. 18 (1993), 360-373. */

// Beta PDF
//
// Parameters
// Input:
//   x : the outcome. Should be in the [0,1] interval.
//   a : the first shape parameter. a > 0
//   b : the second shape parameter. b > 0
// Output:
//   y : the density
//
// Description
//   Computes the Beta probability distribution function.
//
// Let us denote by B the Beta function. The function definition is:
//
//f(x,a,b) = \frac{1}{B(a,b)} x^{a-1} (1-x)^{b-1}
//
// if x is in [0,1]. 
//
// Examples
/*
int status;
double y;
double expected;
status = cdflib_betapdf ( 0.1 , 2 , 3 , &y);
expected = 0.9720;
*/ 
// Bibliography
// Catherine Loader, http://svn.r-project.org/R/trunk/src/nmath/dbeta.c
//
int cdflib_betapdf(double x, double a, double b, double *y);

// Beta CDF
//
// Parameters
// Input:
//   x : the outcome, x in [0,1]
// Output:
//   p : the probability, P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
//
// Examples
/*
int status;
double p;
double expected;
status = cdflib_betacdf( 0.1, 2, 3, CDFLIB_LOWERTAIL, &p);
expected = 0.0523;
 */
int cdflib_betacdf(double x, double a, double b, int lowertail, double *p);

// Beta Inverse CDF
//
// Parameters
// Input:
//   p : the probability, p in [0,1], P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
// Output:
//   x : the outcome. 
//
// Examples
/*
int status;
double x;
double expected;
status = cdflib_betainv ( 0.5, 2, 3, CDFLIB_LOWERTAIL, &x);
expected = 0.3857275681323895644148;
 */
int cdflib_betainv(double p, double a, double b, int lowertail, double *x);

// Generate Beta Random Variables
/*
Method
R. C. H. Cheng
Generating Beta Variatew with Nonintegral Shape Parameters
Communications of the ACM, 21:317-322  (1978)
(Algorithms BB and BC)
*/
int cdflib_betarnd (double a, double b, double *x);

//////////////////////////////////////////////////////////////////////

// Generate Multivariate Normal Random Variables
// Arguments
/*     PARM : Parameters needed to generate multivariate normal */
/*               deviates (MEANV and Cholesky decomposition of */
/*               COVM). Set by a previous call to cdflib_setgmn. */
/*               1 : 1                - size of deviate, P */
/*               2 : P + 1            - mean vector */
/*               P+2 : P*(P+3)/2 + 1  - upper half of cholesky */
/*                                       decomposition of cov matrix */
/*               DOUBLE PRECISION PARM(*) */
/*     X    <-- Vector deviate generated. */
/*              DOUBLE PRECISION X(P) */
/*     WORK <: Scratch array */
/*               DOUBLE PRECISION WORK(P) */
// Method
/*     1) Generate P independent standard normal deviates - Ei ~ N(0,1) */
/*     2) Using Cholesky decomposition find A s.t. trans(A)*A = COVM */
/*     3) trans(A)E + MEANV ~ N(MEANV,COVM) */
//
// Examples
/*
	int d=4;
	double * mean = NULL;
	double * sigma = NULL;
	double * work = NULL;
	double * x;
	double * choleskyFactors = NULL;
	int status;
	int i;
	int j;
	int n=5;

	cdflibsetup();

	// mu=[12,-31,312,-4321]; 
	// sigma = [
	//  3.0  0.5  0.8  0.1
	//  0.5  1.0  0.9  0.2
	//  0.8  0.9  7.5  0.3
	//  0.1  0.2  0.3  5.1
	// ];
	// 
	// chol(sigma)
	//  1.7320508    0.2886751    0.4618802    0.0577350  
	//  0.           0.9574271    0.8007572    0.1914854  
	//  0.           0.           2.5778779    0.0465499  
	//  0.           0.           0.           2.2489627  

	work=(double *) malloc(d*sizeof(double));
	mean=(double *) malloc(d*sizeof(double));
	sigma=(double *) malloc(d*d*sizeof(double));
	x=(double *) malloc(d*sizeof(double));
	choleskyFactors=(double *) malloc(d*(d+1)/2*sizeof(double));
	mean[0]=12;
	mean[1]=-31;
	mean[2]=312;
	mean[3]=-4321;
	sigma[0]=3.0;
	sigma[1]=0.5; 
	sigma[2]=0.8; 
	sigma[3]=0.1;
	sigma[4]=0.5; 
	sigma[5]=1.0; 
	sigma[6]=0.9; 
	sigma[7]=0.2;
	sigma[8]=0.8; 
	sigma[9]=0.9; 
	sigma[10]=7.5; 
	sigma[11]=0.3;
	sigma[12]=0.1; 
	sigma[13]=0.2; 
	sigma[14]=0.3; 
	sigma[15]=5.1;
	status=cdflib_mvnsetup (d, mean, sigma, choleskyFactors);
	for ( i=0 ; i < n ; i++)
	{
		status=cdflib_mvnrnd(d,mean,choleskyFactors,work,x);
		if ( status == CDFLIB_ERROR)
		{
			return 0;
		}
		// Copy the results into i-th row of Y
		for ( j=0 ; j < d ; j++)
		{
			printf ("%f ", x[j]);
		}
		printf ("\n");
	}
	//
	free(choleskyFactors);
	free(work);
	free(x);
	free(mean);
	free(sigma);
*/ 
int cdflib_mvnrnd (int p, double * mean, double *choleskyFactors, double *x, double *work);

// SET Generate Multivariate Normal random deviate 
// Function
/*      Decompose the covariance matrix into its Cholesky factors */
// Arguments
/*     mean : Mean vector of multivariate normal distribution. */
/*               DOUBLE PRECISION mean(P) */
/*     sigma   <: (Input) Covariance   matrix    of  the  multivariate */
/*                 normal distribution.  This routine uses only the */
/*                 (1:P,1:P) slice of COVM, but needs to know LDCOVM. */
/*                 (Output) Destroyed on output */
/*                 DOUBLE PRECISION sigma(P,P) */
/*     P     : Dimension of the normal, or length of MEANV. */
/*     choleskyFactors <-- Array of parameters needed to generate multivariate */
/*              normal deviates (P, MEANV and Cholesky decomposition */
/*              of COVM). */
/*              DOUBLE PRECISION choleskyFactors(P*(P+1)/2) */
// Output
// Returns CDFLIB_OK if OK. 
// Returns CDFLIB_ERROR in case of error.
int cdflib_mvnsetup (int d, double *mean, double *sigma, double *choleskyFactors);

// Multivariate Normal PDF
//
// Parameters
// Input:
//   d : the dimension of the space
//   x : array(d), the outcome
//   mean : array(d), the mean
//   choleskyFactors : array(d*(d+1)/2), the Cholesky factors of the 
//                     covariance matrix, in packed format
//   work : array(d), a temporary working array
// Output:
//   y : the density
//
// Description
//   Computes the Multivariate Normal probability distribution function.
//
// Output
// Returns CDFLIB_OK if OK. 
// Returns CDFLIB_ERROR in case of error.
//
// Examples
/*
	int d=4;
	double * mean = NULL;
	double * sigma = NULL;
	double * work = NULL;
	double * x;
	double * choleskyFactors = NULL;
	int status;
	int n=5;
	double y;
	double expected=7.95851005e-04;

	cdflibsetup();

	// mu=[12,-31,312,-4321]; 
	// sigma = [
	//  3.0  0.5  0.8  0.1
	//  0.5  1.0  0.9  0.2
	//  0.8  0.9  7.5  0.3
	//  0.1  0.2  0.3  5.1
	// ];
	// 
	// chol(sigma)
	//  1.7320508    0.2886751    0.4618802    0.0577350  
	//  0.           0.9574271    0.8007572    0.1914854  
	//  0.           0.           2.5778779    0.0465499  
	//  0.           0.           0.           2.2489627  

	work=(double *) malloc(d*sizeof(double));
	mean=(double *) malloc(d*sizeof(double));
	sigma=(double *) malloc(d*d*sizeof(double));
	x=(double *) malloc(d*sizeof(double));
	choleskyFactors=(double *) malloc(d*(d+1)/2*sizeof(double));
	mean[0]=12;
	mean[1]=-31;
	mean[2]=312;
	mean[3]=-4321;
	sigma[0]=3.0;
	sigma[1]=0.5; 
	sigma[2]=0.8; 
	sigma[3]=0.1;
	sigma[4]=0.5; 
	sigma[5]=1.0; 
	sigma[6]=0.9; 
	sigma[7]=0.2;
	sigma[8]=0.8; 
	sigma[9]=0.9; 
	sigma[10]=7.5; 
	sigma[11]=0.3;
	sigma[12]=0.1; 
	sigma[13]=0.2; 
	sigma[14]=0.3; 
	sigma[15]=5.1;
	status=cdflib_mvnsetup (d, mean, sigma, choleskyFactors);
	x[0]=11;
	x[1]=-30;
	x[2]=311;
	x[3]=-4320;
	status=cdflib_mvnpdf(d, x, mean, choleskyFactors, work, &y);
	printf ("x=%f %f %f %f\n", x[0], x[1], x[2], x[3]);
	printf ("y=%e (expected=%e)\n", y,expected);
	//
	free(choleskyFactors);
	free(work);
	free(x);
	free(mean);
	free(sigma);
*/ 
//
int cdflib_mvnpdf(int d, double * x, double * mean, double * choleskyFactors, double * work, double * y);

//////////////////////////////////////////////////////////////////////

// Generate Multinomial Random Variables
// Arguments
/*     N : Number of events that will be classified into one of */
/*           the categories 1..NCAT */
/*     P : Vector of probabilities.  P(i) is the probability that */
/*           an event will be classified into category i.  Thus, P(i) */
/*           must be [0,1]. Only the first NCAT-1 P(i) must be defined */
/*           since P(NCAT) is 1.0 minus the sum of the first */
/*           NCAT-1 P(i). */
/*                         DOUBLE PRECISION P(NCAT-1) */
/*     NCAT : Number of categories.  Length of P and IX. */
/*                         INTEGER NCAT */
/*     IX <-- Observation from multinomial distribution.  All IX(i) */
/*            will be nonnegative and their sum will be N. */
/*                         INTEGER IX(NCAT) */
// Method
/*     Algorithm from page 559 of */
/*     Devroye, Luc */
/*     Non-Uniform Random Variate Generation.  Springer-Verlag, */
/*     New York, 1986. */
//
// Examples
/*
TODO
*/ 
int cdflib_mnrnd (int n, double *p, int ncat, int *ix);

//////////////////////////////////////////////////////////////////////

// Generate Random Permutations
/*               GENerate random PeRMutation of xarray */
// Arguments
/*     XARRAY <: On output XARRAY is a random permutation of its */
/*                 value on input */
/*                         DOUBLE PRECISION XARRAY( LARRAY ) */
/*     LARRAY <: Length of XARRAY */
int cdflib_genprm (double *xarray, int larray);

/////////////////////////////////////////////////////////////////////////
//
// Weibull Distribution
//

// Weibull PDF
//
// Parameters
// Input:
//   x : the outcome, x>=0
//   a : the scale parameter. a > 0
//   b : the shape parameter. b > 0
// Output:
//   y : the density
//
// Description
//   Computes the Weibull probability distribution function.
//
// The function definition is:
//
// f(x,a,b)=(b/a) (x/a)^(b-1) exp(-(x/a)^b)
//
// if x>=0.
//
// Examples
/*
int status;
double y;
double expected;
status = cdflib_wblpdf(2,3,4,&y);
expected = 0.324248813154871;
*/ 
// Bibliography
// http://en.wikipedia.org/wiki/Weibull_distribution
//
int cdflib_wblpdf(double x, double a, double b, double *y);

// Weibull CDF
//
// Parameters
// Input:
//   x : the outcome, x in [0,1]
// Output:
//   p : the probability, P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
//
// Examples
/*
int status;
double p;
double expected;
status = cdflib_wblcdf(2,3,4,CDFLIB_LOWERTAIL,&p);
expected = 0.179245191701732;
 */
int cdflib_wblcdf(double x, double a, double b, int lowertail, double *p);

// Weibull Inverse CDF
//
// Parameters
// Input:
//   p : the probability, p in [0,1], P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
// Output:
//   x : the outcome. 
//
// Examples
/*
int status;
double x;
double expected;
status = cdflib_wblinv (0.7,3,4,CDFLIB_LOWERTAIL,&x);
expected = 3.14250099586292;
 */
int cdflib_wblinv(double p, double a, double b, int lowertail, double *x);

// Generate Weibull Random Variables
/*
Method : Essentially invert the CDF (but do not call cdflib_wblinv).
Example

int status;
double x;
status = cdflib_wblrnd (3,4,&x);
*/
int cdflib_wblrnd (double a, double b, double *x);

/////////////////////////////////////////////////////////////////////////
//
// Extreme Value Distribution
//

// Extreme Value PDF
//
// Parameters
// Input:
//   x : the outcome
//   mu : the mean 
//   sigma : the standard deviation, sigma > 0
// Output:
//   y : the density
//
// Description
//   Computes the probability distribution function 
// of the Extreme Value function.
//
//   The function definition is:
//
//f(x,\mu,\sigma) = \frac{1}{\sigma} \exp(z-\exp(z))
//
// where z=\frac{x-\mu}{\sigma}.
//
// Examples
/* 
TODO
*/
int cdflib_evpdf(double x, double mu, double sigma, double *y);

// Extreme Value CDF
//
// Parameters
// Input:
//   x : the outcome
// Output:
//   p : the probability, P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
//
// Examples
/* 
TODO
*/
int cdflib_evcdf(double x, double mu, double sigma, int lowertail, double *p);

// Extreme Value Inverse CDF
//
// Parameters
// Input:
//   p : the probability, p in [0,1], P(X<=x) if lowertail=CDFLIB_LOWERTAIL, 
//                       P(X>x) if lowertail=CDFLIB_UPPERTAIL
// Output:
//   x : the outcome
//
// Examples
/* 
TODO
*/
int cdflib_evinv(double p, double mu, double sigma, int lowertail, double *x);

// Generate Extreme Value Random Variables
// Method
// Invert the CDF (but does not call cdflib_evinv).
int cdflib_evrnd (double mu, double sigma, double *x);

//////////////////////////////////////////////////////////////////////

// Incomplete Gamma Function Ratio

// incgam_incgam --
// -------------------------------------------------------------
// Calculation of the incomplete incgam_gamma functions ratios P(a,x)
// and Q(a,x).
// -------------------------------------------------------------
// Inputs:
//   a ,    argument of the functions
//   x ,    argument of the functions
// Outputs:
//   p,     function P(a,x)
//   q,     function Q(a,x)  
//   ierr , error flag
//          ierr=0;, computation succesful
//          ierr=1, overflow/underflow problems. The function values 
//          (P(a,x) and Q(a,x)) are set to zero.
// ----------------------------------------------------------------------
// Authors:
//  Amparo Gil    (U. Cantabria, Santander, Spain)
//                 e-mail: amparo.gil@unican.es
//  Javier Segura (U. Cantabria, Santander, Spain)
//                 e-mail: javier.segura@unican.es
//  Nico M. Temme (CWI, Amsterdam, The Netherlands)
//                 e-mail: nico.temme@cwi.nl
// -------------------------------------------------------------
//  References: "Efficient and accurate algorithms for 
//  the computation and inversion of the incomplete incgam_gamma function ratios",    
//  A. Gil, J. Segura and N.M. Temme, SIAM Journal on Scientific Computing 34(6) (2012)
// -------------------------------------------------------------------
// Examples
/* 
double a=1;
double x=2;
double p; 
double q;
int ierr
double p_expected=0.86466471676338730;
double q_expected=0.13533528323661270;
double ierr_expected=0;
incgam_incgam(a, x, &p, &q, &ierr);
*/
void incgam_incgam(double a, double x, double *p, double *q, int *ierr);

// incgam_invincgam --
// -------------------------------------------------------------
// incgam_invincgam computes xr in the equations P(a,xr)=p and Q(a,xr)=q
// with a as a given positive parameter.
// In most cases, we invert the equation with cdflib_min(p,q)
// -------------------------------------------------------------
// Inputs:
//   a ,    argument of the functions
//   p,     function P(a,x)
//   q,     function Q(a,x)  
// Outputs:
//   xr   , soluction of the equations P(a,xr)=p and Q(a,xr)=q
//          with a as a given positive parameter.
//   xini,  initial value for the Newton
//   nit  , number of iterations in the Newton
//   ierr , error flag
//          ierr=0;,  computation succesful
//          ierr=-1, Error : overflow problem in the computation of one of the 
//                   incgam_gamma factors before starting the Newton iteration.
//                   The initial approximation to the root is given
//                   as output.
//          ierr=-2, Warning : the number of iterations in the Newton method
//                   reached the upper limit N=15. The last value
//                   obtained for the root is given as output.
// ------------------------------------------------------------------
// Examples
/* 
double a=1;
double p=0.1; 
double q=0.9;
double xr;
int ierr;
double x_expected=0.10536051565782635;
double ierr_expected=0;
incgam_invincgam(a, p, q, &xr, &ierr);

Known failures - Case 1

double a=0.5;
double p=1.; 
double q=1.79762504374667411e-219;
double xr; // Nan
int ierr;
incgam_invincgam(a, p, q, &xr, &ierr);

Known failures - Case 2
In this case, the code loops around x=1+/-eps.

double q=1.572992070502851891e-01;
double p=1-q;
double a=0.5;
double xr; // 0.99999999999998990
int ierr; // -2
incgam_invincgam(a, p, q, &xr, &ierr);

*/
void incgam_invincgam(double a, double p, double q, double * xr, int * ierr);

// incgam_checkincgam --
// Inputs:
//   a,     argument of the functions
//   x,     argument of the functions
// Outputs:
//   check   , error in the inversion
// Description
// Checks the relative accuracy in the recursions :
// Q(a+1,x)=Q(a,x)+x^a*exp(-x)/Gamma(a+1)
// P(a+1,x)=P(a,x)-x^a*exp(-x)/Gamma(a+1)
//
// Examples
/* 
double a=2;
double x=3;
double check;
double check_expected=0.;
check=incgam_checkincgam(a,x);
*/
double incgam_checkincgam(double a, double x);

// incgam_testincgam --
// A test function.
// Examples
/* 
incgam_testincgam();
*/
void incgam_testincgam();

// Error function
//
// Parameters
// Input:
//   x : x in (-inf,inf)
//   erfcc : if erfcc==1, computes the complementary error function
//   expo : if expo==1, computes the scaled error function
// Output:
//   y : the function value
//
// Examples
/*
double y;
y=incgam_errorfunction(-1,0,0); // erf(-1)=-0.8427008
y=incgam_errorfunction(-1,1,0); // erfc(-1)=1.8427008
y=incgam_errorfunction(-1,0,1); // erfx(-1)=TODO
*/
double incgam_errorfunction (double x, int erfcc, int expo);

// Error function
//
// Parameters
// Input:
//   x : x in (-inf,inf)
// Output:
//   y : the function value, in [-1,1]
//
// Description
// Computes 
//
// f(x)=1/sqrt(2*pi) int_0^x exp(-t^2) dt
//
// Examples
/*
double y=cdflib_erf(1); // 0.84270079294971478
*/
// Bibliography
// http://en.wikipedia.org/wiki/Error_function
double cdflib_erf(double x);

// Complementary Error function
//
// Parameters
// Input:
//   x : x in (-inf,inf)
// Output:
//   y : the function value, in [0,2]
//
// Description
// Computes 
//
// f(x)=1-erf(x)
//
// Examples
/*
double y=cdflib_erfc(1); // 0.15729920705028516
*/
// Bibliography
// http://en.wikipedia.org/wiki/Error_function
double cdflib_erfc(double x);

// Scaled Complementary Error function
//
// Parameters
// Input:
//   x : x in (-inf,inf)
// Output:
//   y : the function value, in [0,2]
//
// Description
// Computes 
//
// f(x)=exp(x^2)*erfc(x)
//
// Examples
/*
double y=cdflib_erfcx(5); // 0.11070463773306863
*/
// Bibliography
// http://en.wikipedia.org/wiki/Error_function
double cdflib_erfcx(double x);

// Evaluates the regularized incomplete Beta function.
//
// Parameters
// Input:
//   a : the first parameter, a>=0
//   b : the first parameter, b>=0
//   x : the current point, x in [0,1]
//   y : 1-x, y in [0,1]
//   p : the function value
//   q : the complementary function value, q=1-p
// Output:
//   y : the function value
//
// Description
// The Beta function is defined by 
//
// B(A,B)= int_0^1 t^(a-1)*(1-t)^(b-1) dt
//
// The incomplete Beta function is defined by 
//
// B(x,A,B)= int_0^x t^(a-1)*(1-t)^(b-1) dt
//
// The regularized incomplete Beta function is defined by 
//
// bratio(x,A,B) = B(x,A,B)/B(a,B)
//
// where B(a,b) is the Beta function.
// 
// On output we have : 
// p  = bratio(x,A,B)
// q  = 1 - bratio(x,A,B)
//
/*     IERR IS A VARIABLE THAT REPORTS THE STATUS OF THE RESULTS. */
/*     IF NO INPUT ERRORS ARE DETECTED THEN IERR IS SET TO 0 AND */
/*     p AND q ARE COMPUTED. OTHERWISE, IF AN ERROR IS DETECTED, */
/*     THEN p AND q ARE ASSIGNED THE VALUE 0 AND IERR IS SET TO */
/*     ONE OF THE FOLLOWING VALUES ... */
/*        IERR = 1  IF A OR B IS NEGATIVE */
/*        IERR = 2  IF A = B = 0 */
/*        IERR = 3  IF X < 0 OR X > 1 */
/*        IERR = 4  IF Y < 0 OR Y > 1 */
/*        IERR = 5  IF X + Y .NE. 1 */
/*        IERR = 6  IF X = A = 0 */
/*        IERR = 7  IF Y = B = 0 */
/*
Example
	double a=1;
	double b=2;
	double x=0.1;
	double y=0.9;
	double p; // 0.18999999999999950
	double q; // 0.81000000000000050
	int ierr; // 0
	cdflib_bratio(a, b, x, y, &p, &q, &ierr);
*/
void cdflib_bratio(double a, double b, double x, 
	double y, double *p, double *q, int *ierr);

//////////////////////////////////////////////////////////////////////

__END_DECLS


#endif /** _CDFLIB_H_   **/

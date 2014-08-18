// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#ifndef _CDFLIBPRIVATE_H_
#define _CDFLIBPRIVATE_H_

// warning C4996: 'sprintf': This function or variable may be unsafe. 
// Consider using sprintf_s instead. To disable deprecation, 
// use _CRT_SECURE_NO_WARNINGS. See online help for details.
#ifdef _MSC_VER
#pragma warning( disable : 4996 )
#endif

#define cdflib_min(a,b) ((a) <= (b) ? (a) : (b))
#define cdflib_max(a,b) ((a) >= (b) ? (a) : (b))
#define TRUE_ (1)
#define FALSE_ (0)

#define Abs(x) ( ( (x) >= 0) ? (x) : -( x) )

#define CDFLIB_MSGFLOATGREQ "%s: Wrong value for input argument ""%s"": Must be >= %e.\n"
#define CDFLIB_MSGFLOATGREATER "%s: Wrong value for input argument ""%s"": Must be > %e.\n"
#define CDFLIB_MSGFLOATLOWEQ "%s: Wrong value for input argument ""%s"": Must be <= %e.\n"
#define CDFLIB_MSGFLOATLOWER "%s: Wrong value for input argument ""%s"": Must be < %e.\n"
#define CDFLIB_MSGFLOATVALUEINT "%s: Wrong value for input argument ""%s"": Must have integer value.\n"
#define CDFLIB_MSGINTGREQ "%s: Wrong value for input argument ""%s"": Must be >= %d.\n"
#define CDFLIB_MSGINTGREATER "%s: Wrong value for input argument ""%s"": Must be > %d.\n"
#define CDFLIB_MSGINTLOWEQ "%s: Wrong value for input argument ""%s"": Must be <= %d.\n"
#define CDFLIB_MSGINTLOWER "%s: Wrong value for input argument ""%s"": Must be < %d.\n"
#define CDFLIB_MSGGENERIC "%s: Wrong value for input argument ""%s"": We must have ""%s"".\n"

// If this function is non-NULL, it is used to print messages out.
// It can be configure with the public function "cdflib_messageSetFunction".
// If the function is not configured, the messages are printed in the console.
extern void (* cdflib_messagefunction)(char * message);

// If this function is non-NULL, it is used to print messages out.
// It can be configure with the public function "cdflib_messageSetFunction".
// If the function is not configured, the messages are printed in the console.
extern void (* cdflib_messagefunction)(char * message);

// Print error messages
void cdflib_messageprint(char * msg);

// The default mode of the verbose (OFF)
static int cdflib_verbose=0;

// Print a "Iteration: %d" message, if verbose mode is enabled.
void cdflib_printiter(char * fname, int iteration);

// Computes ap^bp
double cdflib_powdd(double ap, double bp);

// Returns a double with the same magnitude as a, 
// and the same sign as b.
double cdflib_dsign(double a, double b);

// Rounds a double toward zero, to an integer.
double cdflib_dint(double x);

// Rounds to the nearest integer.
int cdflib_nearestint(double x);

////////////////////////////////////////////////////////////////
//
// Log-related functions

/* COMPUTATION OF  X - 1 - LN(X) */
double cdflib_rlog(double x);

/* EVALUATION OF THE FUNCTION X - LN(1 + X) */
double cdflib_rlog1(double x);

////////////////////////////////////////////////////////////////
//
// GAMMA-related functions

/* EVALUATION OF LN(GAMMA(1 + A)) FOR -0.2 <= A <= 1.25 */
// Duplicate of incgam_lngam1 ?
double cdflib_gamln1(double a);

/* EVALUATION OF  DEL(A0) + DEL(B0) - DEL(A0 + B0)  WHERE */
/* LN(GAMMA(A)) = (A - 0.5)*LN(A) - A + 0.5*LN(2*PI) + DEL(A). */
/* IT IS ASSUMED THAT A0 >= 8 AND B0 >= 8. */
double cdflib_bcorr(double a0, double b0);

/*     EVALUATION OF EXP(-X)*X**A/GAMMA(A) */
// This is X*(derivative of the incomplete Gamma function w.r.t. X)
double cdflib_rcomp(double a, double x);

/* EVALUATION OF THE DIGAMMA FUNCTION */
// This is the derivative of LN(GAMMA(X))
double cdflib_psi1(double xx);

/* EVALUATION OF THE FUNCTION LN(GAMMA(A + B)) */
/* FOR 1 <= A <= 2  AND  1 <= B <= 2 */
double cdflib_gsumln(double a, double b);

/* COMPUTATION OF 1/GAMMA(A+1) - 1  FOR -0.5 <= A <= 1.5 */
double cdflib_gam1(double a);

//
////////////////////////////////////////////////////////////////

/* IF L = 0 THEN  EXPARG(L) = THE LARGEST POSITIVE W FOR WHICH */
/* EXP(W) CAN BE COMPUTED. */
/* IF L IS NONZERO THEN  EXPARG(L) = THE LARGEST NEGATIVE W FOR */
/* WHICH THE COMPUTED VALUE OF EXP(W) IS NONZERO. */
double cdflib_exparg(int l);

/* EVALUATION OF EXP(MU + X) */
double cdflib_esum(int mu, double x);

/* Double precision NoRmal distribution INVerse */
/* Returns X  such that CUMNOR(X)  =   P,  i.e., the  integral from - */
/* infinity to X of (1/SQRT(2*PI)) EXP(-U*U/2) dU is P */
double cdflib_dinvnr(double p, double q);

/* EVALUATION OF THE FUNCTION EXP(X) - 1 */
double cdflib_expm1(double x);

/* EVALuate a PoLynomial at X */
/*     returns */
/* A(1) + A(2)*X + ... + A(N)*X**(N-1) */
double cdflib_devlpl(double *a, int n, double *x);

////////////////////////////////////////////////////////////////
//
// BETA-related functions
//

/* EVALUATION OF X**A*Y**B/BETA(A,B) */
// If Y=1-X, this is X*Y*(derivative of the incomplete Beta function w.r.t. X)
double cdflib_brcomp(double a, double b, double x, double y);

/* EVALUATION OF THE LOGARITHM OF THE BETA FUNCTION */
/* LN(BETA(A,B)) */
double cdflib_betaln(double a0, double b0);

//
////////////////////////////////////////////////////////////////

/* Computes A**N - 1, THE LARGEST MAGNITUDE where */
/* A, THE BASE. */
/* N, THE NUMBER OF BASE-A DIGITS. */
int cdflib_largestint(void);

// The current floating point base
int cdflib_radix();

// Returns the minimum floating point exponent.
int cdflib_emin();

// Returns the maximum floating point exponent.
int cdflib_emax();

/////////////////////////////////////////////////////
//
// Floating point parameters
//

// Returns the relative tolerance.
// This is twice the machine epsilon.
// i.e. ~2.e-16.
double cdflib_doubleEps(void);

// Returns the absolute tolerance.
// This is the underflow threshold.
// i.e. 1.e-308.
double cdflib_doubleTiny(void);

// This is the overflow threshold.
// i.e. 1.e308.
double cdflib_doubleHuge(void);

// This is the positive infinite, i.e. INF.
double cdflib_infinite(void);

// Returns a Nan.
double cdflib_nan();

// Returns 0 if x is a NAN.
double cdflib_isnan(double x);

// Checks the value of an integer parameter.
//
// Arguments
// fname : the name of the function
// param : the parameter
// paramname : the name of the parameter
// minparam : the minimum value of the parameter
// maxparam : the maximum value of the parameter
//
// Description
// Checks that param is in the range 
// {minparam, minparam+1, ..., maxparam}.
// If this is OK, then returns CDFLIB_OK.
// If not returns CDFLIB_ERROR.
int cdflib_checkrangeint(char * fname, int param, 
	char * paramname, int minparam, int maxparam);

// Checks the value of a double parameter.
//
// Description
// Checks that param>=minparam and param<=maxparam. 
// Otherwise, this is the same as cdflib_checkrangeint.
int cdflib_checkrangedouble(char * fname, double param, 
	char * paramname, double minparam, double maxparam);

// Checks the value of p
//
// Arguments
// fname : the name of the function
// p : the p-value
// pname : the name of p
//
// Description
// Checks that p in [0,1].
// If this is OK, then returns CDFLIB_OK.
// If not returns CDFLIB_ERROR.
int cdflib_checkp(char * fname, double p, char * pname);

// Checks the values of p and q
//
// Arguments
// fname : the name of the function
// p : the p-value
// pname : the name of p
// q : mathematically equal to 1-p, but may be numerically different if p is close to 1
// qname : the name of q
//
// Description
// Checks that p in [0,1], and q in [0,1], and p+q==1 and p+q>0.
// More precisely, checks that abs(p+q-1)<=3*eps, where 
// eps is the machine epsilon. 
// If this is OK, then returns CDFLIB_OK.
// If not returns CDFLIB_ERROR.
int cdflib_checkpq(char * fname, double p, char * pname, double q, char * qname);

// Checks that x+y==1 and x+y>0.
//
// Arguments
// fname : the name of the function
// x : the value of x
// y : the value of y
// paramname : the name of the formal sum "x+y"
//
// Description
// More precisely, checks that abs(x+y-1)<=3*eps, where 
// eps is the machine epsilon. 
// If this is OK, then returns CDFLIB_OK.
// If not returns CDFLIB_ERROR.
int cdflib_checksumtoone(char * fname, double x, double y, char * paramname);

// Checks that value>minparam (double)
//
// Arguments
// fname : the name of the function
// param : the parameter
// paramname : the name of the parameter
// minparam : the lowerbound
//
// Description
// If this is OK, then returns CDFLIB_OK.
// If not returns CDFLIB_ERROR.
int cdflib_checkgreaterthan(char * fname, double param, 
	char * paramname, double minparam);

// Checks that value>minparam (int)
int cdflib_checkintgreaterthan(char * fname, int param, 
	char * paramname, int minparam);

// Checks that value>=minparam (double)
//
// Arguments
// fname : the name of the function
// param : the parameter
// paramname : the name of the parameter
// minparam : the lower bound
//
// Description
// If this is OK, then returns CDFLIB_OK.
// If not returns CDFLIB_ERROR.
int cdflib_checkgreqthan(char * fname, double param, 
	char * paramname, double minparam);

// Checks that value>=minparam (int)
int cdflib_checkintgreqthan(char * fname, int param, 
	char * paramname, int minparam);

// Checks that value<=maxparam.
//
// Arguments
// fname : the name of the function
// param : the parameter
// paramname : the name of the parameter
// maxparam : the upper bound
//
// Description
// If this is OK, then returns CDFLIB_OK.
// If not returns CDFLIB_ERROR.
int cdflib_checkloweqthan(char * fname, double param, 
	char * paramname, double maxparam);
int cdflib_checkintloweqthan(char * fname, int param, 
	char * paramname, int maxparam);

// Computes the zero function for inverse CDF computation.
//
// Arguments
// p : the probability
// q : the complementary probability (mathematically equal to 1-p)
// cum : the value of P(X<x)
// ccum : the value of P(X>x)
//
// Description
// If p<q, then computes cum-p.
// If not, then computes ccum-q.
double cdflib_computefx(double p, double q, double cum, double ccum);

//   Computes the probability distribution function of 
//   the Binomial distribution.
//
// Arguments
// x : the number of successes 
// n : the number of Bernouilli experiments
// p : the probability of success
// q : the probability of failure (mathematically, we have q=1-p)
// computelog : if computelog==CDFLIB_LOGCOMPUTE, then returns log(y), otherwise returns y.
//
// Description
// Computes
//
//f(x) = nchoosek(n,x)* p^x * q^(n-x)
//
// Returns the probability that X=x.
//
// Allows to have a non-integer value of x and N. 
//
// The implementation uses the following principles.
//
// We have:
// beta(x+1,n-x+1)=gamma(x+1)*gamma(n-x+1)/gamma(n+2)
// But gamma(n+2)=(n+1)*gamma(n)
// which implies
// beta(x+1,n-x+1)=gamma(x+1)*gamma(n-x+1)/(n+1)/gamma(n+1)
// Therefore:
// gamma(x+1)*gamma(n-x+1)/gamma(n+1) = (n+1)*beta(x+1,n-x+1)
//
// Hence,
//
// nchoosek(n,x)=1/(n+1)/beta(x+1,n-x+1)
//
// All in all, we have :
// f(x)= p^x*(1-p)^(n-x)/beta(x+1,n-x+1)/(n+1)
// This can be written:
// f(x)= p^(x+1)*(1-p)^(n-x+1)/beta(x+1,n-x+1)/(n+1)/p/(1-p)
// Let a=x+1 and b=n-x+1. This is:
// f(x)= p^a*(1-p)^b/beta(a,b)/(n+1)/p/(1-p)
// So we can use cdflib_brcomp which computes:
// t=X**A*Y**B/BETA(A,B)
// with X=p, Y=1-p, A=a, B=b.
// Then, f(x)= t/(n+1)/p/(1-p).
// 
// Bibliography
// http://svn.r-project.org/R/trunk/src/nmath/dbinom.c
double cdflib_binopdfraw(double x,double n,double p,double q, int computelog);

// Print a -Unable to invert message-
//
// Arguments
// fname : the function name
// bound : the achieved bound
// paramname : the parameter which was searched for
//
// Description
// Just prints a message.
void cdflib_unabletoinvert(char * fname, double bound, char * paramname);

// Check the tail option of a function
//
// Arguments
// fname : the function name
// lowertail : the tail option
//
// Description
// Produces an error if lowertail is both different from 
// CDFLIB_UPPERTAIL and CDFLIB_LOWTAIL.
int cdflib_checklowertail(char * fname, int lowertail);

// Check that a double has an integer value.
//
// Arguments
// fname : the function name
// x : the value
// paramname : the name of the parameter
//
// Description
// Produces an error if x has a nonzero fractional part, i.e. 
// has not an integer value.
int cdflib_checkIntValue(char * fname, double x, char * paramname);

//////////////////////////////////////////////////////////
// 
// Kolmogorov-Smirnov Distribution
//
int cdflib_ksCheckParams(char * fname, int n);
int cdflib_ksCheckX(char * fname, double x);

//////////////////////////////////////////////////////////
// 
// F Distribution
//
int cdflib_fCheckParams(char * fname, double v1, double v2);
int cdflib_fCheckX(char * fname, double x);

// cdflib_cumf
/*                    CUMulative F distribution */
/*     Computes  the  integral from  0  to  X of  the f-density  with DFN */
/*     and DFD degrees of freedom. */
/*                              Arguments */
/*     X --> Upper limit of integration of the f-density. */
/*     DFN --> Degrees of freedom of the numerator sum of squares. */
/*     DFD --> Degrees of freedom of the denominator sum of squares. */
/*     CUM <-- Cumulative f distribution. */
/*     CCUM <-- Compliment of Cumulative f distribution. */
/*                              Method */
/*     Formula  26.5.28 of  Abramowitz and   Stegun   is  used to  reduce */
/*     the cumulative F to a cumulative beta distribution. */
/*                              Note */
/*     If X is less than or equal to 0, 0 is returned. */
void cdflib_cumf(double x, double v1, double v2, double *cum, double *ccum, int *status);

//////////////////////////////////////////////////////////
// 
// Chi-square Distribution
//
int cdflib_chiCheckParams(char * fname, double df);
int cdflib_chiCheckX(char * fname, double x);

// cdflib_cumchi --
/*             CUMulative of the CHi-square distribution */
/*     Calculates the cumulative chi-square distribution. */
/*                              Arguments */
/*     X       --> Upper limit of integration of the */
/*                 chi-square distribution. */
/*     DF      --> Degrees of freedom of the */
/*                 chi-square distribution. */
/*     CUM <-- Cumulative chi-square distribution. */
/*     CCUM <-- Compliment of Cumulative chi-square distribution. */
/*                              Method */
/*     Calls incomplete gamma function (CUMGAM) */
void cdflib_cumchi(double x, double df, double *cum, double *ccum, int * status);

//////////////////////////////////////////////////////////
// 
// Normal Distribution
//
int cdflib_norCheckParams(char * fname, double mean, double sd);
int cdflib_norCheckX(char * fname, double x);

// cdflib_cumnor --
/*     Computes the cumulative  of    the standard normal   distribution,   i.e., */
/*     the integral from -infinity to x of */
/*          (1/sqrt(2*pi)) exp(-u*u/2) du */
/*     X --> Upper limit of integration. */
/*     cum <-- Cumulative normal distribution. */
/*     CCUM <-- Compliment of Cumulative normal distribution. */
/*     Renaming of function ANORM from: */
/*     Cody, W.D. (1993). "ALGORITHM 715: SPECFUN - A Portable FORTRAN */
/*     Package of Special Function Routines and Test Drivers" */
/*     acm Transactions on Mathematical Software. 19, 22-32. */
/*     with slight modifications to return ccum and to deal with */
/*     machine constants. */
void cdflib_cumnor(double x, double *cum, double *ccum, int *status);

//////////////////////////////////////////////////////////
// 
// Beta Distribution
//
int cdflib_betCheckParams(char* fname, double a, double b);
int cdflib_betCheckXY(char* fname, double x, double y);
int cdflib_betCheckX(char* fname, double x);

// cdflib_cumbet --
/*          Double precision cUMulative incomplete BETa distribution */
/*     Calculates the cdf to X of the incomplete beta distribution */
/*     with parameters a and b.  This is the integral from 0 to x */
/*     of (1/B(a,b))*f(t)) where f(t) = t**(a-1) * (1-t)**(b-1) */
/*                              Arguments */
/*     X --> Upper limit of integration. */
/*     Y --> 1 - X. */
/*     A --> First parameter of the beta distribution. */
/*     B --> Second parameter of the beta distribution. */
/*     CUM <-- Cumulative incomplete beta distribution. */
/*     CCUM <-- Compliment of Cumulative incomplete beta distribution. */
/*                              Method */
/*     Calls the routine BRATIO. */
/*                                   References */
/*     Didonato, Armido R. and Morris, Alfred H. Jr. (1992) Algorithim */
/*     708 Significant Digit Computation of the Incomplete Beta Function */
/*     Ratios. ACM ToMS, Vol.18, No. 3, Sept. 1992, 360-373. */
void cdflib_cumbet(double x, double y, double a, double b, double *cum, double *ccum, int *status);

//////////////////////////////////////////////////////////
// 
// Non-Central Chi-Square Distribution
//
int cdflib_chnCheckParams(char * fname, double df, double pnonc);
int cdflib_chnCheckX(char * fname, double x);

// cdflib_cumchn --
/*             CUMulative of the Non-central CHi-square distribution */
/*     Calculates     the       cumulative      non-central    chi-square */
/*     distribution, i.e.,  the probability   that  a   random   variable */
/*     which    follows  the  non-central chi-square  distribution,  with */
/*     non-centrality  parameter    PNONC  and   continuous  degrees   of */
/*     freedom DF, is less than or equal to X. */
/*                              Arguments */
/*     X       --> Upper limit of integration of the non-central */
/*                 chi-square distribution. */
/*     DF      --> Degrees of freedom of the non-central */
/*                 chi-square distribution. */
/*     PNONC   --> Non-centrality parameter of the non-central */
/*                 chi-square distribution. */
/*     CUM <-- Cumulative non-central chi-square distribution. */
/*     CCUM <-- Compliment of Cumulative non-central chi-square distribut */
/*                              Method */
/*     Uses  formula  26.4.25   of  Abramowitz  and  Stegun, Handbook  of */
/*     Mathematical    Functions,  US   NBS   (1966)    to calculate  the */
/*     non-central chi-square. */
void cdflib_cumchn(double x, double df, double pnonc,double *cum, double *ccum, int *status);

//////////////////////////////////////////////////////////
// 
// Binomial Distribution
//
int cdflib_binCheckParams(char * fname, double n, double pr);
int cdflib_binCheckX(char * fname, double n, double x);

// cdflib_cumbin --
/*                    CUmulative BINomial distribution */
/*                              Function */
/*     Returns the probability   of 0  to  X  successes in  XN   binomial */
/*     trials, each of which has a probability of success, PBIN. */
/*                              Arguments */
/*     X --> The upper limit of cumulation of the binomial distribution. */
/*     XN --> The number of binomial trials. */
/*     PBIN --> The probability of success in each binomial trial. */
/*     OMPR --> 1 - PBIN */
/*     CUM <-- Cumulative binomial distribution. */
/*     CCUM <-- Compliment of Cumulative binomial distribution. */
/*                              Method */
/*     Formula  26.5.24    of   Abramowitz  and    Stegun,  Handbook   of */
/*     Mathematical   Functions (1966) is   used  to reduce the  binomial */
/*     distribution  to  the  cumulative    beta distribution. */
void cdflib_cumbin(double x, double xn, double pr, double ompr, double *cum, double *ccum, int *status);

//////////////////////////////////////////////////////////
// 
// Negative Binomial Distribution
//
int cdflib_nbnCheckX(char * fname, double x);
int cdflib_nbnCheckParams(char * fname, double R, double Pr);

// cdflib_cumnbn --
/*                    CUmulative Negative BINomial distribution */
/*                              Function */
/*     Returns the probability that it there will be X or fewer failures */
/*     before there are XN successes, with each binomial trial having */
/*     a probability of success PR. */
/*     Prob(# failures = X | XN successes, PR)  = */
/*                        ( XN + X - 1 ) */
/*                        (            ) * PR^XN * (1-PR)^X */
/*                        (      X     ) */
/*                              Arguments */
/*     X --> The number of failures */
/*     XN --> The number of successes */
/*     PR --> The probability of success in each binomial trial. */
/*     OMPR --> 1 - PR */
/*     CUM <-- Cumulative negative binomial distribution. */
/*     CCUM <-- Compliment of Cumulative negative binomial distribution. */
/*                              Method */
/*     Formula  26.5.26    of   Abramowitz  and    Stegun,  Handbook   of */
/*     Mathematical   Functions (1966) is   used  to reduce the  negative */
/*     binomial distribution to the cumulative beta distribution. */
void cdflib_cumnbn(double x, double xn, double pr, double ompr, double *cum, double *ccum, int * status);

//////////////////////////////////////////////////////////
// 
// T Distribution
//
int cdflib_tCheckX(char * fname, double x);
int cdflib_tCheckParams(char * fname, double v);

// cdflib_cumt --

/*     Computes the integral from -infinity to X of the t-density. */
/*                              Arguments */
/*     X --> Upper limit of integration of the t-density. */
/*     DF --> Degrees of freedom of the t-distribution. */
/*     CUM <-- Cumulative t-distribution. */
/*     CCUM <-- Compliment of Cumulative t-distribution. */
/*                              Method */
/*     Formula 26.5.27   of     Abramowitz  and   Stegun,    Handbook  of */
/*     Mathematical Functions  is   used   to  reduce the  t-distribution */
/*     to an incomplete beta. */
void cdflib_cumt(double x, double v, double *cum, double *ccum, int *status);

//////////////////////////////////////////////////////////
// 
// Poisson Distribution
//

int cdflib_poissCheckParams(char * fname, double lambda);
int cdflib_poissCheckX(char * fname, double x);

// cdflib_cumpoi
/*                    CUMulative POIsson distribution */
/*     Returns the  probability  of  S   or  fewer events in  a   Poisson */
/*     distribution with mean XLAM. */
/*                              Arguments */
/*     S --> Upper limit of cumulation of the Poisson. */
/*     XLAM --> Mean of the Poisson distribution. */
/*     CUM <-- Cumulative poisson distribution. */
/*     CCUM <-- Compliment of Cumulative poisson distribution. */
/*                              Method */
/*     Uses formula  26.4.21   of   Abramowitz and  Stegun,  Handbook  of */
/*     Mathematical   Functions  to reduce   the   cumulative Poisson  to */
/*     the cumulative chi-square distribution. */
/* ********************************************************************** */
void cdflib_cumpoi(double x, double xlam, double *cum, double *ccum, int *status);

//////////////////////////////////////////////////////////
// 
// Non-Central F Distribution
//
int cdflib_fncCheckX(char * fname, double x);
int cdflib_fncCheckParams(char * fname, double dfn, double dfd, double phonc);

// cdflib_cumfnc --
/*     COMPUTES NONCENTRAL F DISTRIBUTION WITH DFN AND DFD */
/*     DEGREES OF FREEDOM AND NONCENTRALITY PARAMETER PNONC */
/*                              Arguments */
/*     X --> UPPER LIMIT OF INTEGRATION OF NONCENTRAL F IN EQUATION */
/*     DFN --> DEGREES OF FREEDOM OF NUMERATOR */
/*     DFD -->  DEGREES OF FREEDOM OF DENOMINATOR */
/*     PNONC --> NONCENTRALITY PARAMETER. */
/*     CUM <-- CUMULATIVE NONCENTRAL F DISTRIBUTION */
/*     CCUM <-- COMPLIMENT OF CUMMULATIVE */
/*                              Method */
/*     USES FORMULA 26.6.20 OF REFERENCE FOR INFINITE SERIES. */
/*     SERIES IS CALCULATED BACKWARD AND FORWARD FROM J = LAMBDA/2 */
/*     (THIS IS THE TERM WITH THE LARGEST POISSON WEIGHT) UNTIL */
/*     THE CONVERGENCE CRITERION IS MET. */
/*     FOR SPEED, THE INCOMPLETE BETA FUNCTIONS ARE EVALUATED */
/*     BY FORMULA 26.5.16. */
/*               REFERENCE */
/*     HANDBOOD OF MATHEMATICAL FUNCTIONS */
/*     EDITED BY MILTON ABRAMOWITZ AND IRENE A. STEGUN */
/*     NATIONAL BUREAU OF STANDARDS APPLIED MATEMATICS SERIES - 55 */
/*     MARCH 1965 */
/*     P 947, EQUATIONS 26.6.17, 26.6.18 */
void cdflib_cumfnc(double x, double dfn, double dfd, double pnonc, double *cum, double *ccum, int *status);

//////////////////////////////////////////////////////////
// 
// Gamma Distribution
//
int cdflib_gamCheckParams(char * fname, double shape, double rate);
int cdflib_gamCheckX(char * fname, double x);

// cdflib_cumgam --
/*           Double precision cUMulative incomplete GAMma distribution */
/*     Computes   the  cumulative        of    the     incomplete   gamma */
/*     distribution, i.e., the integral from 0 to X of */
/*          (1/GAM(A))*EXP(-T)*T**(A-1) DT */
/*     where GAM(A) is the complete gamma function of A, i.e., */
/*          GAM(A) = integral from 0 to infinity of */
/*                    EXP(-T)*T**(A-1) DT */
/*                              Arguments */
/*     X --> The upper limit of integration of the incomplete gamma. */
/*     A --> The shape parameter of the incomplete gamma. */
/*     CUM <-- Cumulative incomplete gamma distribution. */
/*     CCUM <-- Compliment of Cumulative incomplete gamma distribution. */
void cdflib_cumgam(double x, double a, double *cum, double *ccum, int * status);

//////////////////////////////////////////////////////////
// 
// Non-Central T Distribution
//

int cdflib_nctCheckX(char * fname, double x);
int cdflib_nctCheckParams(char * fname, double DF, double PNONC);

// cdflib_cumnct --
/*                 CUMulative Non-Central T-distribution */
/*     Computes the integral from -infinity to T of the non-central */
/*     t-density. */
/*                              Arguments */
/*     T --> Upper limit of integration of the non-central t-density. */
/*                                                  T is DOUBLE PRECISION */
/*     DF --> Degrees of freedom of the non-central t-distribution. */
/*                                                  DF is DOUBLE PRECISIO */
/*     PNONC --> Non-centrality parameter of the non-central t distibutio */
/*                                                  PNONC is DOUBLE PRECI */
/*     CUM <-- Cumulative t-distribution. */
/*                                                  CCUM is DOUBLE PRECIS */
/*     CCUM <-- Compliment of Cumulative t-distribution. */
/*                                                  CCUM is DOUBLE PRECIS */
/*                              Method */
/*     Upper tail    of  the  cumulative  noncentral t   using */
/*     formulae from page 532  of Johnson, Kotz,  Balakrishnan, Coninuous */
/*     Univariate Distributions, Vol 2, 2nd Edition.  Wiley (1995) */
/*     This implementation starts the calculation at i = lambda, */
/*     which is near the largest Di.  It then sums forward and backward. */
void cdflib_cumnct(double t, double df, double pnonc,double *cum, double *ccum, int *status);

//////////////////////////////////////////////////////////
//
// If this function is non-NULL, it is used to generate uniform random numbers in [0,1).
// It can be configure with the public function "cdflib_randSetFunction".
extern double (* cdflib_randfunction)();

// If this function is non-NULL, it is used to generate uniform random integers in [a,b].
// It can be configure with the public function "cdflib_randIntegerInRangeSetFunction".
extern double (* cdflib_randIntegerInRange)(double a, double b);

// If this function is non-NULL, it is used to print messages out.
// It can be configure with the public function "unifrng_msgsetfunction".
// If the function is not configured, the messages are printed in the console.
extern void (* cdflib_messagefunction)(char * message);

// The function which generates random numbers uniform in [0,1).
double cdflib_randgenerate();

// The function which generates random integers uniform in [a,b].
double cdflib_generateIntegerInRange(double a, double b);

// Standard Exponential Random Variable
double cdflib_sexpo ();

// Standard Gamma Random Variable
double cdflib_sgamma (double a);

// Generate Standard Normal Random Variables
double cdflib_snorm();

//
// Elementary Functions
//

// Compute LN(1 + x) accurately for small x.
double cdflib_log1p(double x);

//  Compute   v = log ( (1 + s)/(1 - s) )  
// for small s, this is for |s| < SLIM = 0.20
double cdflib_lnp1m1(double s);

//
// Numerical Linear Algebra
//

/*  -- LAPACK routine (version 3.3.1) -- */
/*  -- LAPACK is a software package provided by Univ. of Tennessee,    -- */
/*  -- Univ. of California Berkeley, Univ. of Colorado Denver and NAG Ltd..-- */
/*  -- April 2011                                                      -- */

/*  Purpose */
/*  ======= */

/*  DPPTRF computes the Cholesky factorization of a real symmetric */
/*  positive definite matrix A stored in packed format. */

/*  The factorization has the form */
/*     A = U**T * U,  if UPLO = 'U', or */
/*     A = L  * L**T,  if UPLO = 'L', */
/*  where U is an upper triangular matrix and L is lower triangular. */

/*  Arguments */
/*  ========= */

/*  UPLO    (input) CHARACTER*1 */
/*          = 'U':  Upper triangle of A is stored; */
/*          = 'L':  Lower triangle of A is stored. */

/*  N       (input) INTEGER */
/*          The order of the matrix A.  N >= 0. */

/*  AP      (input/output) DOUBLE PRECISION array, dimension (N*(N+1)/2) */
/*          On entry, the upper or lower triangle of the symmetric matrix */
/*          A, packed columnwise in a linear array.  The j-th column of A */
/*          is stored in the array AP as follows: */
/*          if UPLO = 'U', AP(i + (j-1)*j/2) = A(i,j) for 1<=i<=j; */
/*          if UPLO = 'L', AP(i + (j-1)*(2n-j)/2) = A(i,j) for j<=i<=n. */
/*          See below for further details. */

/*          On exit, if INFO = 0, the triangular factor U or L from the */
/*          Cholesky factorization A = U**T*U or A = L*L**T, in the same */
/*          storage format as A. */

/*  INFO    (output) INTEGER */
/*          = 0:  successful exit */
/*          < 0:  if INFO = -i, the i-th argument had an illegal value */
/*          > 0:  if INFO = i, the leading minor of order i is not */
/*                positive definite, and the factorization could not be */
/*                completed. */
/*  ===================================================================== */
void dpptrf_(char *uplo, int *n, double *ap, int * info, int uplo_len);

//   DTPSV  solves one of the systems of equations   
//      A*x = b,   or   A'*x = b,   
//   where b and x are n element vectors and A is an n by n unit, or   
//   non-unit, upper or lower triangular matrix, supplied in packed form.   
//   No test for singularity or near-singularity is included in this   
//   routine. Such tests must be performed before calling this routine.   
//   Parameters   
//   ==========   
//   UPLO   - CHARACTER*1.   
//            On entry, UPLO specifies whether the matrix is an upper or   
//            lower triangular matrix as follows:   
//               UPLO = 'U' or 'u'   A is an upper triangular matrix.   
//               UPLO = 'L' or 'l'   A is a lower triangular matrix.   
//            Unchanged on exit.   
//   TRANS  - CHARACTER*1.   
//            On entry, TRANS specifies the equations to be solved as   
//            follows:   
//               TRANS = 'N' or 'n'   A*x = b.   
//               TRANS = 'T' or 't'   A'*x = b.   
//               TRANS = 'C' or 'c'   A'*x = b.   
//            Unchanged on exit.   
//   DIAG   - CHARACTER*1.   
//            On entry, DIAG specifies whether or not A is unit   
//            triangular as follows:   
//               DIAG = 'U' or 'u'   A is assumed to be unit triangular.   
//               DIAG = 'N' or 'n'   A is not assumed to be unit   
//                                   triangular.   
//            Unchanged on exit.   
//   N      - INTEGER.   
//            On entry, N specifies the order of the matrix A.   
//            N must be at least zero.   
//            Unchanged on exit.   
//   AP     - DOUBLE PRECISION array of DIMENSION at least   
//            ( ( n*( n + 1 ) )/2 ).   
//            Before entry with  UPLO = 'U' or 'u', the array AP must   
//            contain the upper triangular matrix packed sequentially,   
//            column by column, so that AP( 1 ) contains a( 1, 1 ),   
//            AP( 2 ) and AP( 3 ) contain a( 1, 2 ) and a( 2, 2 )   
//            respectively, and so on.   
//            Before entry with UPLO = 'L' or 'l', the array AP must   
//            contain the lower triangular matrix packed sequentially,   
//            column by column, so that AP( 1 ) contains a( 1, 1 ),   
//            AP( 2 ) and AP( 3 ) contain a( 2, 1 ) and a( 3, 1 )   
//            respectively, and so on.   
//            Note that when  DIAG = 'U' or 'u', the diagonal elements of   
//            A are not referenced, but are assumed to be unity.   
//            Unchanged on exit.   
//   X      - DOUBLE PRECISION array of dimension at least   
//            ( 1 + ( n - 1 )*abs( INCX ) ).   
//            Before entry, the incremented array X must contain the n   
//            element right-hand side vector b. On exit, X is overwritten   
//            with the solution vector x.   
//   INCX   - INTEGER.   
//            On entry, INCX specifies the increment for the elements of   
//            X. INCX must not be zero.   
//            Unchanged on exit.   
void dtpsv_(char *uplo, char *trans, char *diag, int *n, 
	double *ap, double *x, int *incx);

// Dot product.
double ddot_(int *n, double *dx, int *incx, double *dy, 
	int *incy);

////////////////////////////////////////////////////////////////
//
// Functions from incgam

// Startup the incgam library.
void incgam_startup();

// Computes ln(Gamma(x)), for x>0.
//
// Description
// For large values of x, Gamma(x) overflows. 
// More precisely, for x>~172, we have fl(Gamma(x))=INF,
// where fl(y) is the floating point representation of y.
// The ln(Gamma(x)) function allows to evaluate Gamma(x) for 
// larger values of x. 
// More precisely, for x>~1.e306, we have fl(ln(Gamma(x))=INF,
// which is much larger.
//
// The ln(Gamma(x)) function is ill conditionned for 
// x=1 and x=2. 
// This is because ln(Gamma(1))=ln(Gamma(2))=0, 
// and the derivative of the function is nonzero at these values 
// of x.
/*
Example
double y=incgam_loggam(10.); // 12.801827480081471
*/
double incgam_loggam(double x);

// Euler function Gamma(x), x>0
//
// Description
// The function is defined by
//
// Gamma(x)=int_0^INF x^(t-1) exp(-x) dx
//
// Bibliography
// http://en.wikipedia.org/wiki/Gamma_function
//
/*
Example
double y=incgam_gamma(10.);// 362880.
*/
double incgam_gamma(double x);

//
////////////////////////////////////////////////////////////////

#endif /** _CDFLIBPRIVATE_H_   **/

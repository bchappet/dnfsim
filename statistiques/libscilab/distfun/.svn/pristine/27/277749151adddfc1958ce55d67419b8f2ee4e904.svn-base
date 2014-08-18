// Copyright (C) 2012 - Michael Baudin
// Copyright (C) Sabine Gaüzère
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#include <math.h>
#include <stdio.h>

#include "cdflib.h"
#include "cdflib_private.h" 
#include "brent.h"

// Binomial Distribution

int cdflib_binCheckParams(char * fname, double n, double pr)
{
	int status;
	// N has integer value
	status=cdflib_checkIntValue(fname, n, "N");
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	/*     N > 0 */
	status=cdflib_checkgreaterthan(fname, n, "N", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	//     PR in [0,1]
	status=cdflib_checkp(fname, pr, "pr");
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return status;
}
int cdflib_binCheckX(char * fname, double x, double n)
{
	int status;
	// X has integer value
	status=cdflib_checkIntValue(fname, x, "x");
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	/*     X >=0 and X<=N */
	status = cdflib_checkgreqthan(fname, x, "X", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status = cdflib_checkloweqthan(fname, x, "X", n);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return status;
}

int cdflib_binocdf(double x, double n, double pr, int lowertail, double *p)
{
	int status;
	double ompr;
	double q;

	status = cdflib_binCheckX("cdflib_binocdf", x, n);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Check n, pr, ompr
	status=cdflib_binCheckParams("cdflib_binocdf",n,pr);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	ompr=1-pr;
	/*     Calculating P and Q */
	if (cdflib_isnan(x) || cdflib_isnan(n) || cdflib_isnan(pr))
	{
		*p = x+n+pr;
		status = CDFLIB_OK;
		return status;
	}
	cdflib_cumbin(x, n, pr, ompr, p, &q, &status);
	if (status==CDFLIB_ERROR)
	{
		return status;
	}
	if (lowertail==CDFLIB_UPPERTAIL)
	{
		*p=q;
	}
	status = CDFLIB_OK;
	return status;
}
int cdflib_binoinv(double p, double n, double pr, int lowertail, double *x)
{
	int status;
	double ompr;
	double q;

	double fx;
	double cum, ccum;

	double rtol = cdflib_doubleEps();
	int iteration;
	int inversionlabel;

	// P in [0,1]
	status=cdflib_checkp("cdflib_binoinv", p, "p");
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Check n, pr
	status=cdflib_binCheckParams("cdflib_binoinv",n,pr);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Compute the couple (p,q) from (p,lowertail)
	if (lowertail==CDFLIB_LOWERTAIL)
	{
		q=1-p;
	}
	else
	{
		q=p;
		p=1-q;
	}
	ompr=1-pr;
	/*     Calculating X */
	if (cdflib_isnan(p) || cdflib_isnan(q) || cdflib_isnan(n) || cdflib_isnan(pr))
	{
		*x = p+q+n+pr;
		status = CDFLIB_OK;
		return status;
	}
	if (p==0)
	{
		*x = 0;
		status = CDFLIB_OK;
		return status;
	}
	if (q==0)
	{
		*x = n;
		status = CDFLIB_OK;
		return status;
	}
	//
	// For small values of p
	*x = 0;
	cdflib_cumbin(*x, n, pr, ompr, &cum, &ccum, &status);
	if (status==CDFLIB_ERROR)
	{
		return status;
	}
	fx = cdflib_computefx(p, q, cum, ccum);
	if (p<=cum)
	{
		// The value of p is too small to be 
		// attained by the CDF.
		// The solution is X=0
		status = CDFLIB_OK;
		return status;
	}
	//
	*x = 0.;
	inversionlabel = 0;
	iteration=0;
	while (1)
	{
	    zero_rc ( 0., n, rtol, x, &inversionlabel, fx );
		if (inversionlabel<0) 
		{
			break;
		}
		cdflib_cumbin(*x, n, pr, ompr, &cum, &ccum, &status);
		if (status==CDFLIB_ERROR)
		{
			return status;
		}
		fx = cdflib_computefx(p, q, cum, ccum);
		if (inversionlabel==0) 
		{
			break;
		}
		iteration=iteration+1;
	}
	if (inversionlabel == 0) 
	{
		status = CDFLIB_OK;
		*x=ceil(*x);
	}
	else
	{
		cdflib_unabletoinvert("cdflib_binoinv", *x, "x");
		status = CDFLIB_ERROR;
	}
	cdflib_printiter("cdflib_binoinv", iteration);
	return status;
}

int cdflib_binopdf(double x, double n, double pr, double * y)
{
	double q;
	int status;
    //
    // Check arguments
	status = cdflib_binCheckX("cdflib_binopdf", x, n);
	if (status!=CDFLIB_OK)
	{
		return ( status );
	}
	status = cdflib_binCheckParams("cdflib_binopdf", n, pr);
	if (status!=CDFLIB_OK)
	{
		return ( status );
	}
    //
    // Perform
	q=1-pr;
	*y = cdflib_binopdfraw(x, n, pr, q, 0);
	return ( CDFLIB_OK );	
}

double cdflib_binopdfraw(double x,double n,double p,double q, int computelog)
{
    double y;
	double a;
	double b;
	double t;
	if (p==0)
	{
		if (x==0)
		{
			y = 1.;
		}
		else
		{
			y = 0.;
		}
		return y;
	}
	if (q==0)
	{
		if (x==n)
		{
			y = 1.;
		}
		else
		{
			y = 0.;
		}
		return y;
	}
	//
	a=x+1;
	b=n-x+1;
	t=cdflib_brcomp(a, b, p, q);
	y=t/(n+1)/p/q;
	if (computelog==CDFLIB_LOGCOMPUTE)
	{
		y=log(y);
	}
	return y;
}

int cdflib_binorndbase(int n,double pr, int *x);

int cdflib_binornd(double n, double pr, double *x)
{
	int status;
	int intn;
	int intx;
	double U;
	status = cdflib_binCheckParams("cdflib_binornd", n, pr);
	if (status!=CDFLIB_OK)
	{
		return ( status );
	}
	if (n<=2147483647)
	{
		intn=(int) n;
		status=cdflib_binorndbase(intn, pr, &intx);
		*x=intx;
	}
	else
	{
		U = cdflib_randgenerate();
		status=cdflib_binoinv(U, n, pr, CDFLIB_LOWERTAIL, x);
	}
	return CDFLIB_OK;
}


/*
This source code was taken in the project "freemat"(BSD license)
This source code was modified by Gaüzère Sabine according to the 
modifications done by JJV
*/

/*
SUBROUTINE BTPEC(N,PP,ISEED,JX)
BINOMIAL RANDOM VARIATE GENERATOR
MEAN < 30 -- INVERSE CDF
MEAN >= 30 -- ALGORITHM BTPE:  ACCEPTANCE-REJECTION VIA
FOUR REGION COMPOSITION.  THE FOUR REGIONS ARE A TRIANGLE
(SYMMETRIC IN THE CENTER), A PAIR OF PARALLELOGRAMS (ABOVE
THE TRIANGLE), AND EXPONENTIAL LEFT AND RIGHT TAILS.
BTPE REFERS TO BINOMIAL-TRIANGLE-PARALLELOGRAM-EXPONENTIAL.
BTPEC REFERS TO BTPE AND "COMBINED."  THUS BTPE IS THE
RESEARCH AND BTPEC IS THE IMPLEMENTATION OF A COMPLETE
USABLE ALGORITHM.
REFERENCE:  VORATAS KACHITVICHYANUKUL AND BRUCE SCHMEISER,
"BINOMIAL RANDOM VARIATE GENERATION,"
COMMUNICATIONS OF THE ACM, FORTHCOMING
WRITTEN:  SEPTEMBER 1980.
LAST REVISED:  MAY 1985, JULY 1987
REQUIRED SUBPROGRAM:  RAND() -- A UNIFORM (0,1) RANDOM NUMBER
GENERATOR
ARGUMENTS
N : NUMBER OF BERNOULLI TRIALS            (INPUT)
PP : PROBABILITY OF SUCCESS IN EACH TRIAL (INPUT)
ISEED:  RANDOM NUMBER SEED                (INPUT AND OUTPUT)
JX:  RANDOMLY GENERATED OBSERVATION       (OUTPUT)
VARIABLES
PSAVE: VALUE OF PP FROM THE LAST CALL TO BTPEC
NSAVE: VALUE OF N FROM THE LAST CALL TO BTPEC
XNP:  VALUE OF THE MEAN FROM THE LAST CALL TO BTPEC
P: PROBABILITY USED IN THE GENERATION PHASE OF BTPEC
FFM: TEMPORARY VARIABLE EQUAL TO XNP + P
M:  INTEGER VALUE OF THE CURRENT MODE
FM:  FLOATING POINT VALUE OF THE CURRENT MODE
XNPQ: TEMPORARY VARIABLE USED IN SETUP AND SQUEEZING STEPS
P1:  AREA OF THE TRIANGLE
C:  HEIGHT OF THE PARALLELOGRAMS
XM:  CENTER OF THE TRIANGLE
XL:  LEFT END OF THE TRIANGLE
XR:  RIGHT END OF THE TRIANGLE
AL:  TEMPORARY VARIABLE
XLL:  RATE FOR THE LEFT EXPONENTIAL TAIL
XLR:  RATE FOR THE RIGHT EXPONENTIAL TAIL
P2:  AREA OF THE PARALLELOGRAMS
P3:  AREA OF THE LEFT EXPONENTIAL TAIL
P4:  AREA OF THE RIGHT EXPONENTIAL TAIL
U:  A U(0,P4) RANDOM VARIATE USED FIRST TO SELECT ONE OF THE
FOUR REGIONS AND THEN CONDITIONALLY TO GENERATE A VALUE
FROM THE REGION
V:  A U(0,1) RANDOM NUMBER USED TO GENERATE THE RANDOM VALUE
(REGION 1) OR TRANSFORMED INTO THE VARIATE TO ACCEPT OR
REJECT THE CANDIDATE VALUE
IX:  INTEGER CANDIDATE VALUE
X:  PRELIMINARY CONTINUOUS CANDIDATE VALUE IN REGION 2 LOGIC
AND A FLOATING POINT IX IN THE ACCEPT/REJECT LOGIC
K:  ABSOLUTE VALUE OF (IX-M)
F:  THE HEIGHT OF THE SCALED DENSITY FUNCTION USED IN THE
ACCEPT/REJECT DECISION WHEN BOTH M AND IX ARE SMALL
ALSO USED IN THE INVERSE TRANSFORMATION
R: THE RATIO P/Q
G: CONSTANT USED IN CALCULATION OF PROBABILITY
MP:  MODE PLUS ONE, THE LOWER INDEX FOR EXPLICIT CALCULATION
OF F WHEN IX IS GREATER THAN M
IX1:  CANDIDATE VALUE PLUS ONE, THE LOWER INDEX FOR EXPLICIT
CALCULATION OF F WHEN IX IS LESS THAN M
I:  INDEX FOR EXPLICIT CALCULATION OF F FOR BTPE
AMAXP: MAXIMUM ERROR OF THE LOGARITHM OF NORMAL BOUND
YNORM: LOGARITHM OF NORMAL BOUND
ALV:  NATURAL LOGARITHM OF THE ACCEPT/REJECT VARIATE V
X1,F1,Z,W,Z2,X2,F2, AND W2 ARE TEMPORARY VARIABLES TO BE
USED IN THE FINAL ACCEPT/REJECT TEST
QN: PROBABILITY OF NO SUCCESS IN N TRIALS
REMARK
IX AND JX COULD LOGICALLY BE THE SAME VARIABLE, WHICH WOULD
SAVE A MEMORY POSITION AND A LINE OF CODE.  HOWEVER, SOME
COMPILERS (E.G.,CDC MNF) OPTIMIZE BETTER WHEN THE ARGUMENTS
ARE NOT INVOLVED.
ISEED NEEDS TO BE DOUBLE PRECISION IF THE IMSL ROUTINE
GGUBFS IS USED TO GENERATE UNIFORM RANDOM NUMBER, OTHERWISE
TYPE OF ISEED SHOULD BE DICTATED BY THE UNIFORM GENERATOR
*/
int cdflib_binorndbase(int n,double pr, int *x)
{
	// psave : any negative initial value allows to make the algorithm work
	static double psave = -1;
	static int nsave = -214748365;
	static int i,ix1,k,m,mp,T1;
	static double al,alv,amaxp,c,f,f1,f2,ffm,fm,g,p,p1,p2,p3,p4,q,qn,r,u,v,w,w2,xx,x1,
		x2,xl,xll,xlr,xm,xnp,xnpq,xr,ynorm,z,z2;

	// *****DETERMINE APPROPRIATE ALGORITHM AND WHETHER SETUP IS NECESSARY
	if(pr != psave) {
		goto S10;
	}
	if(n != nsave) {
		goto S20;
	}
	if(xnp < 30.0) {
		goto S150;
	}
	goto S30;
S10:
	/*
	*****SETUP, PERFORM ONLY WHEN PARAMETERS CHANGE
	*/
	/*   JJV added the argument checker - involved only renaming 10
	JJV and 20 to the checkers and adding checkers
	JJV Only remaining problem - if called initially with the
	JJV initial values of psave and nsave, it will hang
	*/
	psave = pr;
	p = cdflib_min(psave,1.0-psave);
	q = 1.0-p;
S20:
	xnp = n * p;
	nsave = n;
	if(xnp < 30.0) {
		goto S140;
	}
	ffm = xnp+p;
	m = (int)ffm;
	fm = m;
	xnpq = xnp*q;
	p1 = (int) (2.195*sqrt(xnpq)-4.6*q)+0.5;
	xm = fm+0.5;
	xl = xm-p1;
	xr = xm+p1;
	c = 0.134+20.5/(15.3+fm);
	al = (ffm-xl)/(ffm-xl*p);
	xll = al*(1.0+0.5*al);
	al = (xr-ffm)/(xr*q);
	xlr = al*(1.0+0.5*al);
	p2 = p1*(1.0+c+c);
	p3 = p2+c/xll;
	p4 = p3+c/xlr;
S30:
	/*
	*****GENERATE VARIATE
	*/
	u = cdflib_randgenerate()*p4;
	v = cdflib_randgenerate();
	/*
	TRIANGULAR REGION
	*/
	if(u > p1) {
		goto S40;
	}
	*x = (int)(xm-p1*v+u);
	goto S170;
S40:
	/*
	PARALLELOGRAM REGION
	*/
	if(u > p2) {
		goto S50;
	}
	xx = xl+(u-p1)/c;
	v = v*c+1.0-Abs(xm-xx)/p1;
	if(v > 1.0 || v <= 0.0) {
		goto S30;
	}
	*x = (int)xx;
	goto S70;
S50:
	/*
	LEFT TAIL
	*/
	if(u > p3) {
		goto S60;
	}
	*x = (int)(xl+log(v)/xll);
	if(*x < 0) {
		goto S30;
	}
	v *= ((u-p2)*xll);
	goto S70;
S60:
	/*
	RIGHT TAIL
	*/
	*x = (int)(xr-log(v)/xlr);
	if(*x > n) {
		goto S30;
	}
	v *= ((u-p3)*xlr);
S70:
	/*
	*****DETERMINE APPROPRIATE WAY TO PERFORM ACCEPT/REJECT TEST
	*/
	k = Abs(*x-m);
	if(k > 20 && k < xnpq/2-1) {
		goto S130;
	}
	/*
	EXPLICIT EVALUATION
	*/
	f = 1.0;
	r = p/q;
	g = (n+1)*r;
	T1 = m-*x;
	if(T1 < 0) 
	{
		goto S80;
	}
	else if(T1 == 0) 
	{
		goto S120;
	}
	else  
	{
		goto S100;
	}
S80:
	mp = m+1;
	for(i=mp; i<=*x; i++) {
		f *= (g/i-r);
	}
	goto S120;
S100:
	ix1 = *x+1;
	for(i=ix1; i<=m; i++) {
		f /= (g/i-r);
	}
S120:
	if(v <= f) {
		goto S170;
	}
	goto S30;
S130:
	/*
	SQUEEZING USING UPPER AND LOWER BOUNDS ON ALOG(F(X))
	*/
	amaxp = k/xnpq*((k*(k/3.0+0.625)+0.1666666666666)/xnpq+0.5);
	ynorm = -(k*k/(2.0*xnpq));
	alv = log(v);
	if(alv < ynorm-amaxp) {
		goto S170;
	}
	if(alv > ynorm+amaxp) {
		goto S30;
	}
	/*
	STIRLING'S FORMULA TO MACHINE ACCURACY FOR
	THE FINAL ACCEPTANCE/REJECTION TEST
	*/
	x1 = *x+1.0;
	f1 = fm+1.0;
	z = n+1.0-fm;
	w = n-*x+1.0;
	z2 = z*z;
	x2 = x1*x1;
	f2 = f1*f1;
	w2 = w*w;
	if(alv <= xm*log(f1/x1)+(n-m+0.5)*log(z/w)+(*x-m)*log(w*p/(x1*q))+(13860.0-
		(462.0-(132.0-(99.0-140.0/f2)/f2)/f2)/f2)/f1/166320.0+(13860.0-(462.0-
		(132.0-(99.0-140.0/z2)/z2)/z2)/z2)/z/166320.0+(13860.0-(462.0-(132.0-
		(99.0-140.0/x2)/x2)/x2)/x2)/x1/166320.0+(13860.0-(462.0-(132.0-(99.0
		-140.0/w2)/w2)/w2)/w2)/w/166320.0) {
			goto S170;
	}
	goto S30;
S140:
	/*
	INVERSE CDF LOGIC FOR MEAN LESS THAN 30
	*/
	qn = pow((double)q,(double)n);
	r = p/q;
	g = r*(n+1);
S150:
	*x = 0;
	f = qn;
	u = cdflib_randgenerate();
S160:
	if(u < f) {
		goto S170;
	}
	if(*x > 110) {
		goto S150;
	}
	u -= f;
	*x += 1;
	f *= (g/(*x)-r);
	goto S160;
S170:
	if(psave > 0.5) {
		*x = n-*x;
	}
	return CDFLIB_OK;
}

void cdflib_cumbin(double x, double xn, double pr, 
	double ompr, double *cum, double *ccum, int *status)
{
	if (x < xn) {
		cdflib_cumbet(pr, ompr, x + 1., xn - x, ccum, cum, status);
		return;
	} 
	else
	{
		*cum = 1.;
		*ccum = 0.;
		*status=CDFLIB_OK;
	}
	return;
}

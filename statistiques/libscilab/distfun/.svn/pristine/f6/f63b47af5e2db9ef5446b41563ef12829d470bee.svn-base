// Copyright (C) 2012 - Michael Baudin
// Copyright (C) Gaüzère Sabine
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#include <math.h>

#include "cdflib.h"
#include "cdflib_private.h" 
#include "brent.h"

// Poisson Distribution

int cdflib_poisscdf(double x, double lambda, int lowertail, double *p)
{
	int status;
	double q;
	status=cdflib_poissCheckX("cdflib_poisscdf",x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Check LAMBDA
	status=cdflib_poissCheckParams("cdflib_poisscdf",lambda);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Calculating P
	if (cdflib_isnan(x) || cdflib_isnan(lambda))
	{
		*p = x+lambda;
		status = CDFLIB_OK;
		return status;
	}
	cdflib_cumpoi(x, lambda, p, &q, &status);
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

int cdflib_poissinv(double p, double lambda, int lowertail, double *x)
{
	int status;
	double fx;
	double cum, ccum;
	double atol = cdflib_doubleTiny();
	double infinite = cdflib_infinite();
	int iteration;
	double b;
	double q;
	int inversionlabel;

	// P in [0,1]
	status=cdflib_checkp("cdflib_poissinv", p, "p");
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Check LAMBDA
	status=cdflib_poissCheckParams("cdflib_poissinv",lambda);
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
	// Calculating X
	if (cdflib_isnan(p) || cdflib_isnan(lambda))
	{
		*x = p+lambda;
		status = CDFLIB_OK;
		return status;
	}
	//
	*x = 0;
	cdflib_cumpoi(*x, lambda, &cum, &ccum, &status);
	if (status==CDFLIB_ERROR)
	{
		return status;
	}
	if (p<=cum)
	{
		// The value of p is too small to be 
		// attained by the CDF.
		// The solution is X=0
		status = CDFLIB_OK;
		return status;
	}
	//
	if (q==0.)
	{
		// p=1 and q=0.
		// The solution is X=INF.
		*x=infinite;
		status = CDFLIB_OK;
		return status;
	}
	//
	// Step A: Find a rough estimate of an interval.
	// Compute a "small" b, so that f(b) changes sign.
	b=1;
	iteration=0;
	while (1)
	{
		cdflib_cumpoi(b, lambda, &cum, &ccum, &status);
		if (status==CDFLIB_ERROR)
		{
			return status;
		}
		fx = cdflib_computefx(p, q, cum, ccum);
		if (p <= q && fx>0)
		{
			break;
		}
		if (p > q && fx<0)
		{
			break;
		}
		b=b*1.e10;
		iteration=iteration+1;
	}
	// Step B: Refine this estimate.
	inversionlabel = 0;
	while (1)
	{
	    zero_rc ( 0., b, atol, x, &inversionlabel, fx );
		if (inversionlabel<0) 
		{
			break;
		}
		cdflib_cumpoi(*x, lambda, &cum, &ccum, &status);
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
		*x=ceil(*x);
		status = CDFLIB_OK;
	}
	else
	{
		cdflib_unabletoinvert("cdflib_poissinv", *x, "x");
		status = CDFLIB_ERROR;
	}
	cdflib_printiter("cdflib_poissinv", iteration);
	return status;
}

// Computes the Poisson PDF
int cdflib_poisspdf(double x, double lambda, double *y)
{
	double t;
	int status;
	//
	// Check arguments
	//
	status=cdflib_poissCheckX("cdflib_poisspdf",x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_poissCheckParams("cdflib_poisspdf",lambda);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	//
	// Proceed...
	if (x==0)
	{
		*y=exp(-lambda);
	}
	else
	{
		t=cdflib_rcomp(x, lambda);
		*y=t/x;
	}
	return ( CDFLIB_OK );
}

int cdflib_poissCheckX(char * fname, double x)
{
	int status;

	// X has integer value
	status=cdflib_checkIntValue(fname, x, "x");
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	/*     X >= 0 */
	status=cdflib_checkgreqthan(fname, x, "x", 0);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return CDFLIB_OK;
}

int cdflib_poissCheckParams(char * fname, double lambda)
{
	int status;
	/*     LAMBDA > 0 */
	status=cdflib_checkgreaterthan(fname, lambda, "lambda", 0);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return CDFLIB_OK;
}

/*
This source code was taken in the project "freemat" (BSD license)
*/
/*
lambda=MEAN lambda OF THE POISSON DISTRIBUTION
OUTPUT: cdflib_poissrnd=SAMPLE FROM THE POISSON-(lambda)-DISTRIBUTION
lambdaprev=PREVIOUS lambda, lambdaold=lambda AT LAST EXECUTION OF STEP P OR B.
TABLES: COEFFICIENTS A0-A7 FOR STEP F. FACTORIALS FACT
COEFFICIENTS A(K) - FOR PX = FK*V*V*SUM(A(K)*V**K)-DEL
SEPARATION OF CASES A AND B
*/
int cdflib_poissrnd(double lambda, double *x)
{
	static double a0 = -0.5;
	static double a1 = 0.3333333;
	static double a2 = -0.2500068;
	static double a3 = 0.2000118;
	static double a4 = -0.1661269;
	static double a5 = 0.1421878;
	static double a6 = -0.1384794;
	static double a7 = 0.125006;
	static double lambdaold = -1.0E37;
	static double lambdaprev = -1.0E37;
	static double fact[10] = {
		1.0,1.0,2.0,6.0,24.0,120.0,720.0,5040.0,40320.0,362880.0
	};
	static int j,k,kflag,l,m,ll;     
	// JJV I added a variable 'll' here - it is the 'l' for CASE A
	static double b1,b2,c,c0,c1,c2,c3,d,del,difmuk,e,fk,fx,fy,g,omega,p,p0,px,py,q,s,
		t,u,v,y,xx,pp[35];
	double R;
	double z;
	// This is 1/sqrt(2*%pi)
	double c1_SQRT_2PI = 0.3989422804014326779399;
	// This is 1/24
	double c1_24 = 0.0416666666666666666666666666666666666666666666666666;
	// This is 1/7
	double c1_7=0.1428571428571428571428571428571428571428571428571428;
	double c1_12=0.0833333333333333333333333333333333333333333333333333;

	if(lambda == lambdaprev) {
		goto S10;
	}
	if(lambda < 10.0) {
		goto S120;
	}
	/*
	CASE  A. (RECALCULATION OF S,D,L IF lambda HAS CHANGED)
	*/
	//   JJV This is the case where I changed 'l' to 'll'
	//   JJV Here 'll' is set once and used in a comparison once
	lambdaprev = lambda;
	s = sqrt(lambda);
	d = 6.0* lambda * lambda;
	/*
	THE POISSON PROBABILITIES PK EXCEED THE DISCRETE NORMAL
	PROBABILITIES FK WHENEVER K >= M(lambda). L=IFIX(lambda-1.1484)
	IS AN UPPER BOUND TO M(lambda) FOR ALL lambda >= 10 .
	*/
	ll = (int) (lambda-1.1484);
S10:
	/*
	STEP N. NORMAL SAMPLE - cdflib_snorm(IR) FOR STANDARD NORMAL DEVIATE
	*/
	z=cdflib_snorm();
	g = lambda+s*z;
	if(g < 0.0) {
		goto S20;
	}
	R = floor(g);
	/*
	STEP I. IMMEDIATE ACCEPTANCE IF R IS LARGE ENOUGH
	*/
	if(R >= ll) {
		*x=R;
		return CDFLIB_OK;
	}
	/*
	STEP S. SQUEEZE ACCEPTANCE - SUNIF(IR) FOR (0,1)-SAMPLE U
	*/
	fk = R;
	difmuk = lambda-fk;
	u = cdflib_randgenerate();
	if(d*u >= difmuk*difmuk*difmuk) {
		*x=R;
		return CDFLIB_OK;
	}
S20:
	/*
	STEP P. PREPARATIONS FOR STEPS Q AND H.
	(RECALCULATIONS OF PARAMETERS IF NECESSARY)
	.3989423=(2*PI)**(-.5)  .416667E-1=1./24.  .1428571=1./7.
	THE QUANTITIES B1, B2, C3, C2, C1, C0 ARE FOR THE HERMITE
	APPROXIMATIONS TO THE DISCRETE NORMAL PROBABILITIES FK.
	C=.1069/lambda GUARANTEES MAJORIZATION BY THE 'HAT'-FUNCTION.
	*/
	if(lambda == lambdaold) {
		goto S30;
	}
	lambdaold = lambda;
	omega = c1_SQRT_2PI/s;
	b1 = c1_24/ lambda;
	b2 = 0.3*b1*b1;
	c3 = c1_7*b1*b2;
	c2 = b2-15.0*c3;
	c1 = b1-6.0*b2+45.0*c3;
	c0 = 1.0-b1+3.0*b2-15.0*c3;
	c = 0.1069/ lambda;
S30:
	if(g < 0.0) {
		goto S50;
	}
	/*
	'SUBROUTINE' F IS CALLED (KFLAG=0 FOR CORRECT RETURN)
	*/
	kflag = 0;
	goto S70;
S40:
	/*
	STEP Q. QUOTIENT ACCEPTANCE (RARE CASE)
	*/
	if(fy-u*fy <= py*exp(px-fx)) {
		*x=R;
		return CDFLIB_OK;
	}
S50:
	/*
	STEP E. EXPONENTIAL SAMPLE - cdflib_sexpo() FOR STANDARD EXPONENTIAL
	DEVIATE E AND SAMPLE T FROM THE LAPLACE 'HAT'
	(IF T <= -.6744 THEN PK < FK FOR ALL lambda >= 10.)
	*/
	e = cdflib_sexpo();
	u = cdflib_randgenerate();
	u += (u-1.0);
	t = 1.8+cdflib_dsign(e,u);
	if(t <= -0.6744) {
		goto S50;
	}
	R = floor(lambda+s*t);
	fk = R;
	difmuk = lambda-fk;
	/*
	'SUBROUTINE' F IS CALLED (KFLAG=1 FOR CORRECT RETURN)
	*/
	kflag = 1;
	goto S70;
S60:
	/*
	STEP H. HAT ACCEPTANCE (E IS REPEATED ON REJECTION)
	*/
	if(c*Abs(u) > py*exp(px+e)-fy*exp(fx+e)) {
		goto S50;
	}
	*x=R;
	return CDFLIB_OK;
S70:
	/*
	STEP F. 'SUBROUTINE' F. CALCULATION OF PX,PY,FX,FY.
	CASE R .LT. 10 USES FACTORIALS FROM TABLE FACT
	*/
	if(R >= 10) {
		goto S80;
	}
	px = - lambda;
	py = pow(lambda,R)/ fact[(int)R];
	goto S110;
S80:
	/*
	CASE R .GE. 10 USES POLYNOMIAL APPROXIMATION
	A0-A7 FOR ACCURACY WHEN ADVISABLE
	.8333333E-1=1./12.  .3989423=(2*PI)**(-.5)
	*/
	del = c1_12/fk;
	del -= (4.8*del*del*del);
	v = difmuk/fk;
	if(Abs(v) <= 0.25) {
		goto S90;
	}
	px = fk*log(1.0+v)-difmuk-del;
	goto S100;
S90:
	px = fk*v*v*(((((((a7*v+a6)*v+a5)*v+a4)*v+a3)*v+a2)*v+a1)*v+a0)-del;
S100:
	py = c1_SQRT_2PI/sqrt(fk);
S110:
	y = (0.5-difmuk)/s;
	xx = y*y;
	fx = -0.5*xx;
	fy = omega*(((c3*xx+c2)*xx+c1)*xx+c0);
	if(kflag <= 0) {
		goto S40;
	}
	goto S60;
S120:
	/*
	CASE  B. (START NEW TABLE AND CALCULATE P0 IF NECESSARY)
	*/
	lambdaprev = -1.0E37;
	m = cdflib_max(1L,(int) (lambda));
	l = 0;
	p = exp(- lambda);
	q = p0 = p;
S130:
	/*
	STEP U. UNIFORM SAMPLE FOR INVERSION METHOD
	*/
	u = cdflib_randgenerate();
	R = 0;
	if(u <= p0) {
		*x=R;
		return CDFLIB_OK;
	}
	/*
	STEP T. TABLE COMPARISON UNTIL THE END PP(L) OF THE
	PP-TABLE OF CUMULATIVE POISSON PROBABILITIES
	(0.458=PP(9) FOR lambda=10)
	*/
	if(l == 0) {
		goto S150;
	}
	j = 1;
	if(u > 0.458) {
		j = cdflib_min(l,m);
	}
	for(k=j; k<=l; k++) {
		if(u <= *(pp+k-1)) {
			goto S180;
		}
	}
	if(l == 35) {
		goto S130;
	}
S150:
	/*
	STEP C. CREATION OF NEW POISSON PROBABILITIES P
	AND THEIR CUMULATIVES Q=PP(K)
	*/
	l += 1;
	for(k=l; k<=35; k++) {
		p = p* lambda/(double)k;
		q += p;
		*(pp+k-1) = q;
		if(u <= q) {
			goto S170;
		}
	}
	l = 35;
	goto S130;
S170:
	l = k;
S180:
	R = k;
	*x=R;
	return CDFLIB_OK;
}

void cdflib_cumpoi(double x, double xlam, double *cum, double *ccum, int *status)
{
	double df, chi;
	df = (x + 1.) * 2.;
	chi = xlam * 2.;
	cdflib_cumchi(chi, df, ccum, cum, status);
	return;
}

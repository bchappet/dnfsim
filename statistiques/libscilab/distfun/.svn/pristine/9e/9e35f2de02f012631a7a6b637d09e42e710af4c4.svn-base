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

// Beta Distribution

int cdflib_betCheckParams(char* fname, double a, double b)
{
	int status;
	/*     A >= 0 */
	status=cdflib_checkgreqthan(fname, a, "a", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}

	/*     B >= 0 */
	status=cdflib_checkgreqthan(fname, b, "b", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return CDFLIB_OK;
}

int cdflib_betCheckXY(char* fname, double x, double y)
{
	/*     X, Y, X + Y */
	int status;
	status=cdflib_checkpq(fname, x, "x", y, "y");
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return CDFLIB_OK;
}
int cdflib_betCheckX(char* fname, double x)
{
	/*     X in [0,1] */
	int status;
	status=cdflib_checkrangedouble(fname, x, "x", 0., 1.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return CDFLIB_OK;
}

int cdflib_betainv(double p, double a, double b, int lowertail, double *x)
{
	int status;
	int qporq;
	double fx;
    double cum, ccum;
	double atol = cdflib_doubleTiny();
	double q;
	double y;
	int iteration;
	int inversionlabel;

	// P in [0,1]
	status=cdflib_checkp("cdflib_betainv", p, "p");
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Check A, B
	status=cdflib_betCheckParams("cdflib_betainv",a, b);
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
	/*     Select the minimum of P or Q */
	qporq = p <= q;
	/*     Calculating X and Y */
	if ( p == 0. ) 
	{
		*x = 0.;
		status = CDFLIB_OK;
		return status;
	}
	if ( q == 0. ) 
	{
		*x = 1.;
		status = CDFLIB_OK;
		return status;
	}
	if (cdflib_isnan(p) || cdflib_isnan(q) || cdflib_isnan(a) || cdflib_isnan(b))
	{
		*x = p+q+a+b;
		status = CDFLIB_OK;
		return status;
	}
	if (qporq)
	{
		y = 1. - *x;
	}
	inversionlabel = 0;
	iteration=0;
	fx=0.; // This prevents an unnecessary runtime warning.
	while (1)
	{
		if (qporq)
		{
			zero_rc ( 0., 1., atol, x, &inversionlabel, fx );
			y = 1. - *x;
		}
		else
		{
			zero_rc ( 0., 1., atol, &y, &inversionlabel, fx );
			*x = 1. - y;
		}
		if (inversionlabel<0) 
		{
			break;
		}
		cdflib_cumbet(*x, y, a, b, &cum, &ccum, &status);
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
	}
	else
	{
		cdflib_unabletoinvert("cdflib_betainv", *x, "x");
		status = CDFLIB_ERROR;
	}
	cdflib_printiter("cdflib_betainv", iteration);
	return status;
}
int cdflib_betacdf(double x, double a, double b, int lowertail, double *p)
{
	int status;
	double q;
	double y;

	status=cdflib_betCheckX("cdflib_betcdf",x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_betCheckParams("cdflib_betcdf",a, b);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	/*     Calculating P and Q */
	if (cdflib_isnan(x) || cdflib_isnan(a) || cdflib_isnan(b))
	{
		*p = x+a+b;
		status = CDFLIB_OK;
		return status;
	}
	// Compute y
	y=1-x;
	cdflib_cumbet(x, y, a, b, p, &q, &status);
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

// Beta PDF
int cdflib_betapdf(double x, double a, double b, double *y)
{
	double lny;
	int status;
	double z;
	double t1, t2, t3;
	double infinite=cdflib_infinite();
	//
	// Check arguments
	status=cdflib_betCheckX("cdflib_betapdf",x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_betCheckParams("cdflib_betapdf",a,b);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	//
	// Compute t1=(a-1)*log(x)
	//
	if (x==0)
	{
		if (a<1)
		{
			*y=infinite;
			return (CDFLIB_OK);
		}
		else if (a>1)
		{
			*y=0;
			return (CDFLIB_OK);
		}
		else
		{
			t1=0;
		}
	}
	else
	{
		t1=(a-1)*log(x);
	}
	//
	// Compute t2=(b-1)*log(1-x)
	//
	if (x==1)
	{
		if (b<1)
		{
			*y=infinite;
			return (CDFLIB_OK);
		}
		else if (b>1)
		{
			*y=0;
			return (CDFLIB_OK);
		}
		else
		{
			t2=0;
		}
	}
	else
	{
		z=-x;
		t2=(b-1)*cdflib_log1p(z);
	}
	if (a>2 && b>2)
	{
		// Evaluate (a+b-1)*binompdf(a-1,a+b-2,x)
		lny = log(a+b-1) + cdflib_binopdfraw(a-1,a+b-2,x,1-x,1);
	}
	else
	{
		// Evaluate (a-1)*log(x) + (b-1)*log(1-x) - log(beta(a,b))
		t3=cdflib_betaln(a, b);
		lny = t1+t2-t3;
	}
	*y = exp ( lny );
	return ( CDFLIB_OK );
}

/*
This source code was taken in the project "freemat" (BSD license)
This source code was modified by Gaüzère Sabine according to the 
modifications done by JJV
*/
int cdflib_betarnd(double a, double b, double *x)
{
	// expmax is so that exp(expmax)=huge
	double expmax = 709.78271289338397309621;
	double tiny = cdflib_doubleTiny();
	double huge = cdflib_doubleHuge();
	static double olda = -1;
	static double oldb = -1;

	static double aa,alpha,bb,beta,delta,gamma,k1,k2,r,s,t,u1,u2,v,w,y,z;
	static int qsame;
	double c1=1.3888900168240070343E-2;
	double c2=4.1666701436042785645E-2;
	double c3=0.77777802944183349609;
	double c4=1.3862943649291992188;
	int status;

	status=cdflib_betCheckParams("cdflib_betarnd",a,b);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	qsame = olda == a && oldb == b;
	if(!qsame) {
		olda = a;
		oldb = b;
	}
	if(!(cdflib_min(a,b) > 1.0)) {
		goto S100;
	}
	/*
	Alborithm BB
	Initialize
	*/
	if(qsame) {
		goto S30;
	}
	aa = cdflib_min(a,b);
	bb = cdflib_max(a,b);
	alpha = aa+bb;
	beta = sqrt((alpha-2.0)/(2.0*aa*bb-alpha));
	gamma = aa+1.0/beta;
S30:
S40:
	u1 = cdflib_randgenerate();
	/*
	Step 1
	*/
	u2 = cdflib_randgenerate();
	v = beta*log(u1/(1.0-u1));
	if(v > expmax) {
		goto S55;
	}
	w= exp(v);
	if(w > (huge/aa)) {
		goto S55;
	}
	w *= aa;
	goto S60;

S55:
	w = huge;
S60:
	z = pow(u1,2.0)*u2;
	r = gamma*v-c4;
	s = aa+r-w;
	/*
	Step 2
	*/
	if(s+2.6094379425048828125 >= 5.0*z) {
		goto S70;
	}
	/*
	Step 3
	*/
	t = log(z);
	if(s > t) {
		goto S70;
	}
	/*
	Step 4
	*/
	//    JJV added checker to see if log(alpha/(bb+w)) will 
	//    JJV overflow.  If so, we count the log as -INF, and
	//    JJV consequently evaluate conditional as true, i.e.
	//    JJV the algorithm rejects the trial and starts over
	//    JJV May not need this here since ALPHA > 2.0
	if(alpha/(bb+w) < tiny) {
		goto S40;
	}
	if(r+alpha*log(alpha/(bb+w)) < t) {
		goto S40;
	}
S70:
	/*
	Step 5
	*/
	if(a == aa) 
	{
		*x = w/(bb+w);
		return CDFLIB_OK;
	}
	else
	{
		*x = bb/(bb+w);
		return CDFLIB_OK;
	}
S100:
	/*
	Algorithm BC
	Initialize
	*/
	if(qsame) {
		goto S110;
	}
	aa = cdflib_max(a,b);
	bb = cdflib_min(a,b);
	alpha = aa+bb;
	beta = 1.0/bb;
	delta = 1.0+aa-bb;
	k1 = delta*(c1+c2*bb)/(aa*beta-c3);
	k2 = 0.25+(0.5+0.25/delta)*bb;
S110:
S120:
	u1 = cdflib_randgenerate();
	/*
	Step 1
	*/
	u2 = cdflib_randgenerate();
	if(u1 >= 0.5) {
		goto S130;
	}
	/*
	Step 2
	*/
	y = u1*u2;
	z = u1*y;
	if(0.25*u2+z-y >= k1) {
		goto S120;
	}
	goto S170;
S130:
	/*
	Step 3
	*/
	z = pow(u1,2.0)*u2;
	if(!(z <= 0.25)) {
		goto S160;
	}
	v = beta*log(u1/(1.0-u1));

	//  JJV instead of checking v > expmax at top, I will check
	//  JJV if aa < 1, then check the appropriate values

	if(aa > 1.0) {
		goto S135;
	}
	//  JJV A < 1 so it can help out if EXP(V) would overflow
	if(v > expmax) {
		goto S132;
	}
	w = aa*exp(v);
	goto S200;
S132:
	w = v + log(aa);
	if(w > expmax) {
		goto S140;
	}
	w = exp(w);
	goto S200;

	//  JJV in this case A > 1
S135:
	if(v > expmax) {
		goto S140;
	}
	w = exp(v);
	if(w > huge/aa) {
		goto S140;
	}
	w *= aa;
	goto S200;
S140:
	w = huge;
	goto S200;
S160:
	if(z >= k2) {
		goto S120;
	}
S170:
	/*
	Step 4
	Step 5
	*/
	v = beta*log(u1/(1.0-u1));
	//  JJV same kind of checking as above
	if(aa > 1.0) {
		goto S175;
	}
	//  JJV A < 1 so it can help out if EXP(V) would overflow
	if(v > expmax) {
		goto S172;
	}
	w = aa*exp(v);
	goto S190;
S172:
	w = v + log(aa);
	if(w > expmax) {
		goto S180;
	}
	w = exp(w);
	goto S190;
	//  JJV in this case A > 1
S175:
	if(v > expmax) {
		goto S180;
	}
	w = exp(v);
	if(w > huge/aa) {
		goto S180;
	}
	w *= aa;
	goto S190;

S180:
	w = huge;

	//  JJV here we also check to see if log overlows; if so, we treat it
	//  JJV as -INF, which means condition is true, i.e. restart
S190:
	if(alpha/(bb+w) < tiny) {
		goto S120;
	}
	if(alpha*(log(alpha/(bb+w))+v)-c4 < log(z)) {
		goto S120;
	}
S200:
	/*
	Step 6
	*/
	if(aa == a) 
	{
		*x = w/(bb+w);
		return CDFLIB_OK;
	}
	else
	{
		*x = bb/(bb+w);
		return CDFLIB_OK;
	}
}

void cdflib_cumbet(double x, double y, double a, 
	double b, double *cum, double *ccum, int *status)
{
	int ierr;
	char buffer [1024];

	if (x <= 0.) {
		*cum = 0.;
		*ccum = 1.;
		*status=CDFLIB_OK;
		return;
	}
	if (y <= 0.) {
		*cum = 1.;
		*ccum = 0.;
		*status=CDFLIB_OK;
		return;
	}
	cdflib_bratio(a, b, x, y, cum, ccum, &ierr);
	if (ierr==0)
	{
		*status=CDFLIB_OK;
	}
	else
	{
		*status=CDFLIB_ERROR;
		sprintf (buffer, "%s: Unable to evaluate Incomplete Beta function at a=%e, b=%e, x=%e, y=%e","cdflib_cumbet",a,b,x,y);
		cdflib_messageprint(buffer);
	}
	return;
}


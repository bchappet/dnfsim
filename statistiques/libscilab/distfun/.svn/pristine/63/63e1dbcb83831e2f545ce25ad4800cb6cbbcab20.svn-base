// Copyright (C) 2012 - 2014 - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#include <math.h>
#include <stdio.h>

#include "cdflib.h"
#include "cdflib_private.h" 
#include "brent.h"

// F Distribution

int cdflib_fCheckParams(char * fname, double v1, double v2)
{
	int status;
	/*     V1 > 0 */
	status=cdflib_checkgreaterthan(fname, v1, "v1", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}

	/*     V2 > 0 */
	status=cdflib_checkgreaterthan(fname, v2, "v2", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return CDFLIB_OK;
}
int cdflib_fCheckX(char * fname, double x)
{
	int status;
	/*     X >=0 */
	status=cdflib_checkgreqthan(fname, x, "x", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return CDFLIB_OK;
}

int cdflib_fcdf(double x, double v1, double v2, int lowertail, double *p)
{

	int status;
	double q;

	// Check X
	status=cdflib_fCheckX("cdflib_fcdf",x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}

	// Check V1, V2
	status=cdflib_fCheckParams("cdflib_fcdf",v1, v2);
	if (status!=CDFLIB_OK)
	{
		return status;
	}

	if (cdflib_isnan(x) || cdflib_isnan(v1) || cdflib_isnan(v2))
	{
		*p = x+v1+v2;
		status = CDFLIB_OK;
		return status;
	}
	cdflib_cumf(x, v1, v2, p, &q, &status);
	if (status==CDFLIB_ERROR)
	{
		return status;
	}
	status = CDFLIB_OK;
	if (lowertail==CDFLIB_UPPERTAIL)
	{
		*p=q;
	}
	return status;
}
int cdflib_finv(double p, double v1, double v2, int lowertail, double *x)
{

	int status;
	double fx;
	double cum, ccum;

	double atol = cdflib_doubleTiny();
	double infinite = cdflib_infinite();
	double q;

	int iteration;
	double b;
	int inversionlabel;

	// P in [0,1]
	status=cdflib_checkp("cdflib_finv", p, "p");
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Check V1, V2
	status=cdflib_fCheckParams("cdflib_finv", v1, v2);
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
	if (q==0)
	{
		*x = infinite;
		status = CDFLIB_OK;
		return status;
	}
	if (cdflib_isnan(p) || cdflib_isnan(q) || cdflib_isnan(v1) || cdflib_isnan(v2))
	{
		*x = p+q+v1+v2;
		status = CDFLIB_OK;
		return status;
	}
	/*     Calculating X */
	//
	// Step A: Find a rough estimate of an interval.
	// Compute a "small" b, so that f(b) changes sign.
	b=cdflib_doubleTiny();
	iteration=0;
	while (1)
	{
		cdflib_cumf(b, v1, v2, &cum, &ccum, &status);
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
		cdflib_cumf(*x, v1, v2, &cum, &ccum, &status);
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
		cdflib_unabletoinvert("cdflib_finv", *x, "x");
		status = CDFLIB_ERROR;
	}
	cdflib_printiter("cdflib_finv", iteration);
	return status;
}

int cdflib_fpdf(double x,double v1,double v2, double * y)
{
	double f;
	double p;
	double q;
	int status;
	double inf=cdflib_infinite();	
	//
	// Check arguments.
	status=cdflib_fCheckX("cdflib_fpdf",x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// 
	status=cdflib_fCheckParams("cdflib_fpdf",v1, v2);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	//
	// Perform...
	if ((v1==inf) & (v2==inf))
	{
		if (x==1)
		{
			*y = inf;
			return CDFLIB_OK;
		} 
		else
		{
			*y = 0;
			return CDFLIB_OK;
		}
	}
	if (x==0)
	{
		if (v1<2)
		{
			*y = inf;
			return CDFLIB_OK;
		}
		else if (v1==2)
		{
			*y = 1.;
			return CDFLIB_OK;
		}
		else
		{
			// v1>2
			*y = 0.;
			return CDFLIB_OK;
		}
	}
	
    if (v1 > 1e14) 
	{
        // includes +Inf: code below is inaccurate there
        status = cdflib_gammapdf(1 / x, v2/2, 2 /v2, y);
		if (status==CDFLIB_ERROR)
		{	
			return CDFLIB_ERROR;
		}
        *y = *y /(x*x);
		return CDFLIB_OK;
    }
    // General case
    f = 1 /(v2+x*v1);
    q = v2*f;
    p = x*v1*f;
    //
    if (v1>=2)
	{
        f = v1*q/2;
        *y = cdflib_binopdfraw((v1-2)/2, (v1+v2-2)/2, p, q,0);
    }
    else
	{
        f = v1*v1*q / (2*p*(v1+v2));
        *y = cdflib_binopdfraw(v1/2, (v1+v2)/2, p, q,0);
    }
    *y = f*(*y);
    return CDFLIB_OK;
}

int cdflib_frnd(double v1, double v2, double *x)
{
	double d1;
	static double xden, xnum;
	int status;
	status=cdflib_fCheckParams("cdflib_fpdf",v1, v2);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	d1 = v1 / 2.;
	xnum = cdflib_sgamma(d1) * 2. / v1;
	d1 = v2 / 2.;
	xden = cdflib_sgamma(d1) * 2. / v2;
	*x = xnum / xden;
	return CDFLIB_OK;
}

void cdflib_cumf(double x, double dfn, double dfd, double *cum, double *ccum, int *status)
{
	double d1, d2;
	double xx, yy;
	int ierr;
	double prod, dsum;
	double infinite = cdflib_infinite();
	char buffer [1024];
	if (x <= 0.) 
	{
		*cum = 0.;
		*ccum = 1.;
		*status=CDFLIB_OK;
		return;
	}
	if ( (dfd==infinite) & (dfn==infinite) )
	{
		if (x<1)
		{
			*cum = 0.;
			*ccum = 1.;
			*status=CDFLIB_OK;
			return;
		} 
		else if (x==1)
		{
			*cum = 0.5;
			*ccum = 0.5;
			*status=CDFLIB_OK;
			return;
		}
		else
		{
			// f>1
			*cum = 1.;
			*ccum = 0.;
			*status=CDFLIB_OK;
			return;
		}
	}
	prod = dfn * x;
	/*     XX is such that the incomplete beta with parameters */
	/*     DFD/2 and DFN/2 evaluated at XX is 1 - CUM or CCUM */
	/*     YY is 1 - XX */
	/*     Calculate the smaller of XX and YY accurately */
	dsum = dfd + prod;
	xx = dfd / dsum;
	if (xx > .5) 
	{
		yy = prod / dsum;
		xx = 1. - yy;
	} 
	else 
	{
		yy = 1. - xx;
	}
	d1 = dfd * .5;
	d2 = dfn * .5;
	cdflib_bratio(d1, d2, xx, yy, ccum, cum, &ierr);
	if (ierr==0)
	{
		*status=CDFLIB_OK;
	}
	else
	{
		*status=CDFLIB_ERROR;
		sprintf (buffer, "%s: Unable to evaluate Incomplete Beta function at a=%e, b=%e, x=%e, y=%e","cdflib_cumf",d1,d2,xx,yy);
		cdflib_messageprint(buffer);
	}
	return;
}

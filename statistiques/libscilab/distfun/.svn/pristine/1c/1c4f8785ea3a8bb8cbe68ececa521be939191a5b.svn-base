// Copyright (C) 2012 - 2014 - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#include <math.h>
#include <stdio.h>

#include "cdflib.h"
#include "cdflib_private.h" 

// Gamma Distribution

int cdflib_gamCheckX(char * fname, double x)
{
	int status;

	/*     X >=0 */
	status=cdflib_checkgreqthan(fname, x, "x", 0);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return CDFLIB_OK;
}
int cdflib_gamCheckParams(char * fname, double a, double b)
{
	int status;

	/*     A > 0 */
	status=cdflib_checkgreaterthan(fname, a, "a", 0);
	if (status!=CDFLIB_OK)
	{
		return status;
	}

	/*     B > 0 */
	status=cdflib_checkgreaterthan(fname, b, "b", 0);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return CDFLIB_OK;
}

int cdflib_gaminv(double p, double a, double b, int lowertail, double *x)
{

	int status;

	/* Local variables */
	double xx;
	int ierr;
	double q;
	char buffer [1024];

	double infinite = cdflib_infinite();

	// P in [0,1]
	status=cdflib_checkp("cdflib_gaminv", p, "p");
	if (status!=CDFLIB_OK)
	{
		return status;
	}

	// Check A, B
	status=cdflib_gamCheckParams("cdflib_gaminv", a, b);
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

	/*     Computing X */
	if (q==0)
	{
		*x = infinite;
		status = CDFLIB_OK;
		return status;
	}
	if (p==0)
	{
		*x = 0;
		status = CDFLIB_OK;
		return status;
	}
	if (cdflib_isnan(p) || cdflib_isnan(q) || cdflib_isnan(a) || cdflib_isnan(b))
	{
		*x = p+q+a+b;
		status = CDFLIB_OK;
		return status;
	}
	incgam_invincgam(a, p, q, &xx, &ierr);
	if (ierr==-1) 
	{
		sprintf (buffer, "%s: Unable to evaluate Inverse Gamma CDF at a=%e, p=%e.\n","cdflib_gaminv",a,p);
		cdflib_messageprint(buffer);
		status = CDFLIB_ERROR;
		return status;
	} 
	// ierr==-2 happens sometimes, but this is not a problem.
	*x = xx * b;
	status = CDFLIB_OK;
	return status;
}

int cdflib_gamcdf(double x, double a, double b, int lowertail, double *p)
{
	int status;

	double xrate;
	double infinite = cdflib_infinite();
	double q;

	// Check X
	status=cdflib_gamCheckX("cdflib_gamcdf",x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}

	// Check A, B
	status=cdflib_gamCheckParams("cdflib_gamcdf",a, b);
	if (status!=CDFLIB_OK)
	{
		return status;
	}

	/*     Calculating P and Q */
	if (x==infinite)
	{
		if (lowertail==CDFLIB_LOWERTAIL)
		{
			*p=1.;
		}
		else
		{
			*p=0.;
		}
		status = CDFLIB_OK;
		return status;
	}
	if (cdflib_isnan(x) || cdflib_isnan(a) || cdflib_isnan(b))
	{
		*p = x+a+b;
		status = CDFLIB_OK;
		return status;
	}
	xrate = x / b;
	cdflib_cumgam(xrate, a, p, &q, &status);
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

int cdflib_gamrnd(double a, double b, double *x)
{
	int status;
	status=cdflib_gamCheckParams("cdflib_gamrnd",a, b);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	*x = cdflib_sgamma(a) * b;
	return CDFLIB_OK;
}

int cdflib_gammapdf( double x , double a , double b, double * y )
{
	double logy;
	int status;
	double infinite = cdflib_infinite();
	//
	// Check arguments
	//
	status=cdflib_gamCheckX("cdflib_gammapdf",x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_gamCheckParams("cdflib_gammapdf",a, b);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
    //
	if (x==infinite)
	{
		*y=0.;
		return CDFLIB_OK;
	}
    // x<>0
    if (x!=0)
	{
        logy = (a-1)*log(x) - x/b - a*log(b) - incgam_loggam(a);
        *y=exp(logy);
	    return ( CDFLIB_OK );
	}
    //
    // x=0, a=1
    if ((x==0) & (a==1))
	{
        *y=1/b;
	    return ( CDFLIB_OK );
	}
    //
    // x=0, a<1
    if ((x==0)&(a<1))
	{
        *y=cdflib_infinite();
	    return ( CDFLIB_OK );
	}
	*y = 0.;
	return ( CDFLIB_OK );
}
void cdflib_cumgam(double x, double a, double *cum, double *ccum, int * status)
{
	int ierr;
	char buffer [1024];

	if (x <= 0.) 
	{
		*cum = 0.;
		*ccum = 1.;
	}
	else
	{
		incgam_incgam(a, x, cum, ccum, &ierr);
		if (ierr==0)
		{
			*status=CDFLIB_OK;
		}
		else
		{
			sprintf (buffer, "%s: Unable to evaluate Regularized Incomplete Gamma function at a=%e, x=%e","cdflib_cumgam",a,x);
			cdflib_messageprint(buffer);
			*status=CDFLIB_ERROR;
		}
	}
	return;
}


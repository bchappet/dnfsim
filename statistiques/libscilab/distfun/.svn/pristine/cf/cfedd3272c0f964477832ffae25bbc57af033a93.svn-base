// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#include "cdflib.h"
#include "cdflib_private.h" 

#include <math.h>

// Weibull distribution

int cdflib_wblCheckParams(char* fname, double a, double b)
{
	int status;
	/*     A > 0 */
	status=cdflib_checkgreaterthan(fname, a, "a", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}

	/*     B > 0 */
	status=cdflib_checkgreaterthan(fname, b, "b", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return CDFLIB_OK;
}

int cdflib_wblCheckX(char* fname, double x)
{
	/*     X >=0 */
	int status;
	status=cdflib_checkgreqthan(fname, x, "x", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return CDFLIB_OK;
}

int cdflib_wblrnd(double a, double b, double * x)
{
	int status;
	double u;
	double log1p;
	status=cdflib_wblCheckParams("cdflib_wblrnd",a,b);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	u = cdflib_randgenerate();
	log1p=cdflib_log1p(-u);
	*x=a*pow(-log1p,1/b);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return CDFLIB_OK;
}

int cdflib_wblcdf(double x, double a, double b, int lowertail, double * p)
{
	// The Weibull CDF is 
	// f(x,a,b)=1-exp(-(x/a)^b)
	// for x>=0, a>0, b>0.
	int status;
	double logp;
	status=cdflib_wblCheckX("cdflib_wblcdf",x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_wblCheckParams("cdflib_wblcdf",a,b);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	if (x==0.)
	{
		if (lowertail==CDFLIB_LOWERTAIL)
		{
			*p=0.;
		}
		else
		{
			*p=1.;
		}
		return CDFLIB_OK;
	}
	logp = -pow((x/a),b);
	if (lowertail==CDFLIB_LOWERTAIL)
	{
		*p = -cdflib_expm1(logp);
	}
	else
	{
		*p = exp(logp);
	}
	return CDFLIB_OK;
}

int cdflib_wblpdf(double x, double a, double b, double * y)
{
	// The Weibull PDF is 
	// f(x,a,b)=(b/a) (x/a)^(b-1) exp(-(x/a)^b)
	// for x>=0, a>0, b>0.
	int status;
	double logy;
	double inf=cdflib_infinite();
	status=cdflib_wblCheckX("cdflib_wblpdf",x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_wblCheckParams("cdflib_wblpdf",a,b);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	if (b==1)
	{
		// Degenerates to the Exponential
		status=cdflib_exppdf(x, a, y);
		return status;
	}
	if (x==0.)
	{
		if (b<1.0)
		{
			*y=inf;
		}
		else
		{
			// b>1 (since b==1 is managed upper)
			*y=0.;
		}
		return CDFLIB_OK;
	}
	if (x==inf)
	{
		*y=0.;
		return CDFLIB_OK;
	}
	logy = log(b)-log(a)+(b-1)*(log(x)-log(a))-pow((x/a),b);
	*y = exp(logy);
	return CDFLIB_OK;
}

int cdflib_wblinv(double p, double a, double b, int lowertail, double * x)
{
	int status;
	double logp;
	double inf=cdflib_infinite();
	double log1p;
	// P in [0,1]
	status=cdflib_checkp("cdflib_wblinv", p, "p");
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_wblCheckParams("cdflib_wblinv",a,b);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	if (lowertail==CDFLIB_LOWERTAIL)
	{
		if (p==1)
		{
			*x=inf;
		}
		else
		{
			log1p=cdflib_log1p(-p);
			*x=a*pow(-log1p,1/b);
		}
		status=CDFLIB_OK;
	}
	else
	{
		if (p==0)
		{
			*x=inf;
		}
		else
		{
			logp=log(p);
			*x=a*pow(-logp,1/b);
		}
		status=CDFLIB_OK;
	}
	return CDFLIB_OK;
}

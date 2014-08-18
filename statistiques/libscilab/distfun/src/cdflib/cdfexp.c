// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#include <math.h>
#include <stdio.h>

#include "cdflib.h" 
#include "cdflib_private.h" 

// Exponential PDF
int cdflib_exppdf(double x, double mu, double *y)
{
	double lny;
	int status;
	//
	// Check arguments
	//
	status=cdflib_expCheckX("cdflib_exppdf",x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_expCheckParams("cdflib_exppdf",mu);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	//
	// Proceed...
	if (x<0)
	{
		*y=0.;
	}
	else
	{
		lny = -x/mu - log(mu);
		*y = exp(lny);
	}
	return ( CDFLIB_OK );
}

int cdflib_expCheckParams(char * fname, double mu)
{
	int status;

	/*     MU > 0 */
	status=cdflib_checkgreaterthan(fname, mu, "mu", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return CDFLIB_OK;
}
int cdflib_expCheckX(char * fname, double x)
{
	// Nothing to do.
	return CDFLIB_OK;
}
// Exponential CDF
int cdflib_expcdf(double x, double mu, int lowertail, double *p)
{
	int status;
	//
	// Check arguments
	//
	status=cdflib_expCheckX("cdflib_expcdf",x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_expCheckParams("cdflib_expcdf",mu);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_checklowertail("cdflib_expcdf", lowertail);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	//
	// Proceed...
	if (lowertail==CDFLIB_LOWERTAIL)
	{
		if (x<=0)
		{
			*p=0.;
		}
		else
		{
			*p = -cdflib_expm1 ( -x/mu );
		}
	}
	else
	{
		if (x<=0)
		{
			*p=1.;
		}
		else
		{
			*p = exp(-x/mu);
		}
	}
	return ( CDFLIB_OK );
}
// Exponential Inverse CDF
int cdflib_expinv(double p, double mu, int lowertail, double *x)
{
	int status;
	double infinite=cdflib_infinite();
	//
	// Check arguments
	//
	status=cdflib_checkrangedouble("cdflib_expinv", p, "p", 0., 1.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_expCheckParams("cdflib_expinv",mu);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_checklowertail("cdflib_expinv", lowertail);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	//
	// Proceed...
	if (lowertail==CDFLIB_LOWERTAIL)
	{
		if (p==1)
		{
			*x = infinite;
		}
		else
		{
			*x = - mu*cdflib_log1p(-p);
		}
	}
	else
	{
		if (p==0)
		{
			*x = infinite;
		}
		else
		{
			*x = - mu*log(p);
		}
	}
	return ( CDFLIB_OK );		
}

int cdflib_exprnd(double mu, double *x)
{
	int status;
	status=cdflib_expCheckParams("cdflib_exppdf",mu);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	*x=cdflib_sexpo();
	*x = (*x) * mu;
	return CDFLIB_OK;
}


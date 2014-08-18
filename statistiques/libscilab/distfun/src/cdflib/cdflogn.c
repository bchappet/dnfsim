// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#include "cdflib.h"
#include "cdflib_private.h"
#include <math.h>

int cdflib_lognrnd(double mu, double sigma, double *x)
{
	int status;
	status=cdflib_lognCheckParams("cdflib_lognrnd",mu, sigma);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_normrnd(mu, sigma, x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	*x=exp(*x);
	return CDFLIB_OK;
}

// Computes the Log Normal PDF
int cdflib_lognpdf(double x, double mu, double sigma, double *y)
{
	double lny;
	double c;
	// sq2pi = sqrt(2*%pi)
	double sq2pi=2.506628274631000241612;
	int status;
	//
	// Check arguments
	//
	status=cdflib_lognCheckX("cdflib_lognpdf",x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_lognCheckParams("cdflib_lognpdf",mu, sigma);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	//
	// Proceed...
	if ( x<=0 )
	{
		*y = 0;
	}
	else
	{
		c = (log(x) - mu) / sigma;
		lny = -0.5 * c * c - log(sigma*x*sq2pi);
		*y = exp ( lny );
	}
	return ( CDFLIB_OK );
}

int cdflib_lognCheckParams(char * fname, double mu, double sigma)
{
	int status;
	// Nothing to do for mu

	/*     SIGMA > 0 */
	status=cdflib_checkgreaterthan(fname, sigma, "sigma", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return CDFLIB_OK;
}
int cdflib_lognCheckX(char * fname, double x)
{
	// Nothing to do...
	return CDFLIB_OK;
}

// Computes the Log Normal CDF
int cdflib_logncdf(double x, double mu, double sigma, int lowertail, double *p)
{
	int status;
	double z;
	double q;
	double infinite=cdflib_infinite();
	//
	// Check arguments
	//
	status=cdflib_lognCheckX("cdflib_logncdf",x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_lognCheckParams("cdflib_logncdf",mu, sigma);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_checklowertail("cdflib_logncdf", lowertail);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	//
	// Proceed...
	if ( x<=0 )
	{
		if (lowertail==CDFLIB_LOWERTAIL)
		{
			*p = 0.;
		}
		else
		{
			*p = 1.;
		}
	}
	else if (x==infinite)
	{
		if (lowertail==CDFLIB_LOWERTAIL)
		{
			*p = 1.;
		}
		else
		{
			*p = 0.;
		}
	}
	else
	{
		z=(log(x) - mu)/sigma;
		cdflib_cumnor(z, p, &q, &status);
		if (status==CDFLIB_ERROR)
		{
			return status;
		}
		if ( lowertail==CDFLIB_UPPERTAIL )
		{
			*p=q;
		}
	}
	return CDFLIB_OK;
}
// Computes the Log Normal Inverse CDF
int cdflib_logninv(double p, double mu, double sigma, int lowertail, double *x)
{
	int status;
	double z;
	double q;
	double infinite=cdflib_infinite();
	//
	// Check arguments
	//
	status=cdflib_checkrangedouble("cdflib_logninv", p, "p", 0., 1.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_lognCheckParams("cdflib_logninv",mu, sigma);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_checklowertail("cdflib_logninv", lowertail);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	//
	// Proceed...
	if (lowertail==CDFLIB_LOWERTAIL)
	{
		if (p==0.)
		{
			z=-infinite;
		}
		else if (p==1.)
		{
			z=infinite;
		}
		else
		{
			q=1.-p;
			z = cdflib_dinvnr(p, q);
		}
	}
	else
	{
		if (p==0)
		{
			z=infinite;
		}
		else if (p==1.)
		{
			z=-infinite;
		}
		else
		{
			q=1.-p;
			z = cdflib_dinvnr(q, p);
		}
	}
	*x=exp(sigma*z+mu);
	return CDFLIB_OK;
}

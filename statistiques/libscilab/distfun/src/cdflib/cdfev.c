// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#include "cdflib.h"
#include "cdflib_private.h" 

#include <math.h>

// Extreme Value distribution

int cdflib_evCheckParams(char* fname, double mu, double sigma)
{
	int status;
	// mu : nothing to do
	/*     sigma > 0 */
	status=cdflib_checkgreaterthan(fname, sigma, "sigma", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return CDFLIB_OK;
}

int cdflib_evCheckX(char* fname, double x)
{
	// Nothing to do
	return CDFLIB_OK;
}

int cdflib_evrnd(double mu, double sigma, double * x)
{
	int status;
	double u;
	status=cdflib_evCheckParams("cdflib_evrnd",mu,sigma);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	u=cdflib_randgenerate();
	*x=mu+sigma*log(-log(u));
	return CDFLIB_OK;
}

int cdflib_evcdf(double x, double mu, double sigma, int lowertail, double * p)
{
	int status;
	double z;
	status=cdflib_evCheckX("cdflib_evcdf",x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_evCheckParams("cdflib_evcdf",mu,sigma);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	z=(x-mu)/sigma;
	if (lowertail==CDFLIB_LOWERTAIL)
	{
		*p=-cdflib_expm1(-exp(z));
	}
	else
	{
		*p=exp(-exp(z));
	}
	return CDFLIB_OK;
}

int cdflib_evpdf(double x, double mu, double sigma, double * y)
{
	int status;
	double inf=cdflib_infinite();
	double z;
	status=cdflib_evCheckX("cdflib_evpdf",x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_evCheckParams("cdflib_evpdf",mu,sigma);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	if ((x==inf) || (x==-inf))
	{
		*y=0;
		return CDFLIB_OK;
	}
	z=(x-mu)/sigma;
	*y=exp(z-exp(z))/sigma;
	return CDFLIB_OK;
}

int cdflib_evinv(double p, double mu, double sigma, int lowertail, double * x)
{
	int status;
	double z;
	// P in [0,1]
	status=cdflib_checkp("cdflib_evinv", p, "p");
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_evCheckParams("cdflib_evinv",mu,sigma);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	if (lowertail==CDFLIB_LOWERTAIL)
	{
        z=log(-cdflib_log1p(-p));
	}
    else
	{
        z=log(-log(p));
	}
    *x=mu+sigma*z;
	return CDFLIB_OK;
}

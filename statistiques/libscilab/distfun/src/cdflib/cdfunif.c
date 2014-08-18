// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#include <math.h>
#include <stdio.h>

#include "cdflib.h" 
#include "cdflib_private.h" 

// Computes the Uniform PDF
int cdflib_unifpdf(double x, double a, double b, double *y)
{
	int status;
	//
	// Check arguments
	//
	status=cdflib_unifCheckX("cdflib_unifpdf",x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_unifCheckParams("cdflib_unifpdf",a, b);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	//
	// Proceed...
	if (x<a)
	{
		*y=0.;
	}
	else if (x>b)
	{
		*y=0.;
	}
	else
	{		
		*y = 1/(b-a);
	}
	return ( CDFLIB_OK );
}
int cdflib_unifCheckParams(char * fname , double a, double b)
{
	int status;
	status=cdflib_checkloweqthan(fname, a, "a", b);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return ( CDFLIB_OK );
}
int cdflib_unifCheckX(char * fname , double x)
{
	// Nothing to do...
	return ( CDFLIB_OK );
}
// Computes the Uniform CDF
int cdflib_unifcdf(double x, double a, double b, int lowertail, double *p)
{
	int status;
	//
	// Check arguments
	//
	status=cdflib_unifCheckX("cdflib_unifcdf",x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_unifCheckParams("cdflib_unifcdf",a, b);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_checklowertail("cdflib_unifcdf", lowertail);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	//
	// Proceed...
	if (x<a)
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
	else if (x>b)
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
		// (a<=x & x<=b)
		if (lowertail==CDFLIB_LOWERTAIL)
		{
			*p = (x-a)/(b-a);
		}
		else
		{
			*p = (x-b)/(a-b);
		}
	}
	return ( CDFLIB_OK );
}
// Computes the Uniform Inverse CDF
int cdflib_unifinv(double p, double a, double b, int lowertail, double *x)
{
	int status;
	//
	// Check arguments
	//
	status=cdflib_checkrangedouble("cdflib_unifinv", p, "p", 0., 1.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_unifCheckParams("cdflib_unifinv",a, b);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_checklowertail("cdflib_unifinv", lowertail);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	//
	// Proceed...
	if (lowertail==CDFLIB_LOWERTAIL)
	{
		*x = a + p*(b-a);
	}
	else
	{
		*x = b + p*(a-b);
	}
	return ( CDFLIB_OK );
}

int cdflib_unifrnd(double a, double b, double *x)
{
	int status;
	double u;
	status=cdflib_unifCheckParams("cdflib_unifrnd",a, b);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	u=cdflib_randgenerate();
	*x = a + (b - a) * u;
	return CDFLIB_OK;
}


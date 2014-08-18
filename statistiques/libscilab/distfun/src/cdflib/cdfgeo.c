// Copyright (C) 2012 - Michael Baudin
// Copyright (C) Bruno Pincon
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#include <math.h>
#include <stdio.h>

#include "cdflib.h" 
#include "cdflib_private.h" 

// Geometric PDF
int cdflib_geopdf(double x, double pr, double *y)
{
	double lny;
	double dblx=(double) x;
	double minusp;
	double ln1mp;
	int status;
	//
	// Check arguments
	//
	status=cdflib_geoCheckParams("cdflib_geopdf",pr);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_geoCheckX("cdflib_geopdf",x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	//
	// Proceed...
	// Computes log(1-pr)
	minusp = -pr;
	ln1mp = cdflib_log1p(minusp);
	// Compute log(y)
	lny = log(pr)+dblx*ln1mp;
	*y = exp(lny);
	return ( CDFLIB_OK );
}
int cdflib_geoCheckParams(char * fname, double pr)
{
	int status;
	// PR in [0,1]
	status=cdflib_checkrangedouble(fname, pr, "pr", 0., 1.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return ( CDFLIB_OK );
}
int cdflib_geoCheckX(char * fname, double x)
{
	int status;
	// X>=0
	status=cdflib_checkgreqthan(fname, x, "x", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// X has integer value
	status=cdflib_checkIntValue(fname, x, "x");
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return ( CDFLIB_OK );
}

// Geometric CDF
int cdflib_geocdf(double x, double pr, int lowertail, double *p)
{
	int status;
	double t;
	//
	// Check arguments
	//
	status=cdflib_geoCheckX("cdflib_geocdf",x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_geoCheckParams("cdflib_geocdf",pr);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_checklowertail("cdflib_geocdf", lowertail);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	//
	// Proceed...
    t = cdflib_log1p(-pr) * (x+1);
    if (lowertail==CDFLIB_LOWERTAIL)
	{
        *p = -cdflib_expm1(t);
	}
    else
	{
        *p = exp(t);
	}
	return ( CDFLIB_OK );
}

// Geometric Inverse CDF
int cdflib_geoinv(double p, double pr, int lowertail, double *x)
{
	int status;
	double infinite=cdflib_infinite();
	//
	// Check arguments
	//
	status=cdflib_checkrangedouble("cdflib_geoinv", p, "p", 0., 1.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_geoCheckParams("cdflib_geoinv",pr);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_checklowertail("cdflib_geoinv", lowertail);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	//
	// Proceed...
    if (lowertail==CDFLIB_LOWERTAIL)
	{
        if (p<1)
		{
			*x = floor(cdflib_log1p(-p) / cdflib_log1p(-pr));
		}
		else
		{
			// p==1
			*x = infinite;
		}
	}
    else
	{
        if (p>0)
		{
			*x = floor(log(p) / cdflib_log1p(-pr));
		}
		else
		{
			// p==0
			*x = infinite;
		}
	}
	return ( CDFLIB_OK );
}

int cdflib_geornd(double pr, double *x)
{
	double ln_1_m_p;
	double u;
	int status;
	double logu;

	status=cdflib_geoCheckParams("cdflib_geornd",pr);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	if ( pr == 1 ) 
	{
		*x=1.0;
		return CDFLIB_OK;
	}
	ln_1_m_p = cdflib_log1p(-pr);
	u = -cdflib_randgenerate();
	logu = cdflib_log1p(u);
	*x = floor( logu/ln_1_m_p);
	return CDFLIB_OK;
}      


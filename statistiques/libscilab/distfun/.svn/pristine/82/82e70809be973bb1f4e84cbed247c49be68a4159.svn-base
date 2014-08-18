// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#include <math.h>

#include "cdflib.h"
#include "cdflib_private.h" 

double cdflib_log1p(double x)
{
	// Uses Kahan's trick.
	double u = 1.+x;
	double y;
	double inf=cdflib_infinite();
	if (u == 1.)
	{
		y=x;
	}
	else if (x==inf)
	{
		// The formula below is indefinite when x=INF.
		// But log(1+INF)=INF.
		y=inf;
	}
	else
	{
		y=log(u)*x/(u-1.);
	}
	return y;
}


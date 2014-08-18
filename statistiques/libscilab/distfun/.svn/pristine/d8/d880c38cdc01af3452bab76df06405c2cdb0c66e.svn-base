// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#include <math.h>

#include "cdflib.h"
#include "cdflib_private.h" 

double cdflib_erf(double x)
{
	return incgam_errorfunction (x, 0, 0);
}

double cdflib_erfc(double x)
{
	return incgam_errorfunction (x, 1, 0);
}

double cdflib_erfcx(double x)
{
	double t;
	double y;
	t=exp(x*x);
	y=cdflib_erfc(x);
	y=t*y;
	return y;
}


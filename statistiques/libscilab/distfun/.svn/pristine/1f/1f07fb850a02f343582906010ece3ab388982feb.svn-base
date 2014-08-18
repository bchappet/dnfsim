// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#include "cdflib.h"
#include "cdflib_private.h"

int cdflib_genprm(double *xarray, int larray)
{
	int i__1;
	double d__1;

	static int i__;
	static double elt;
	static int iwhich;
	static double llarray;

	// TODO : check the arguments

	/* Parameter adjustments */
	--xarray;

	/* Function Body */
	llarray = (double) (larray);
	i__1 = larray;
	for (i__ = 1; i__ <= i__1; ++i__) {
		d__1 = (double) i__;
		iwhich = (int) cdflib_generateIntegerInRange(d__1, llarray);
		elt = xarray[iwhich];
		xarray[iwhich] = xarray[i__];
		xarray[i__] = elt;
	}
	return CDFLIB_OK;
}


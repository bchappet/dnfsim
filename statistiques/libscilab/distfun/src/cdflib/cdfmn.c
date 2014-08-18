// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#include "cdflib.h"

int cdflib_mnrnd(int n, double *p, int ncat, int *ix)
{
	int i__1;

	static int i__;
	static double sum;
	static int icat;
	static double prob;
	static int ntot;
	double xbino;
	int status;

	// TODO : check the arguments

	/*     Initialize variables */
	/* Parameter adjustments */
	--ix;
	--p;

	/* Function Body */
	ntot = n;
	sum = 1.;
	i__1 = ncat;
	for (i__ = 1; i__ <= i__1; ++i__) {
		ix[i__] = 0;
	}
	/*     Generate the observation */
	i__1 = ncat - 1;
	for (icat = 1; icat <= i__1; ++icat) {
		prob = p[icat] / sum;
		status = cdflib_binornd((double) ntot, prob, &xbino);
		if (status==CDFLIB_ERROR) {
			return status;
		}
		ix[icat]=(int) xbino;
		ntot -= ix[icat];
		if (ntot <= 0) {
			return CDFLIB_OK;
		}
		sum -= p[icat];
	}
	ix[ncat] = ntot;
	return CDFLIB_OK;
}


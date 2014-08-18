/*
* Copyright (C)  2012 - 2014 - Michael Baudin
* Copyright (C)  2001 - Bruno Pincon
* Copyright (C) ENPC - jpc@cermics.enpc.fr
*
* This file must be used under the terms of the CeCILL.
* This source file is licensed as described in the file COPYING, which
* you should have received as part of this distribution.  The terms
* are also available at
* http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
*
*/

#include <string.h>
#include <math.h>

// From Distfun:
#include "gwsupport.h"
#include "cdflib.h"
#include "gw_distfunrnd.h"
#include "gw_distfunrnd_support.h" 

// From Scilab:
#include "api_scilab.h"
#include "stack-c.h"

// mygennbn --
//
// Generate a random variable from Negative Binomial distribution.
//
// Arguments
// p (input): the probability
// x (output): the outcome
//
// Note
// Encapsulates status=cdflib_nbnrnd(p,&x), 
// so that it has the calling sequence 
// required by distfun_computeRandgenAB.
// The difference is that
// * Y is a (double) instead of a (int).
// * N is a (double) instead of a (int).
int mygennbn (double  N, double p, double *x)
{
	int integerx;
	int integerN;
	int status;
	
	integerN = (int) N;
	status = cdflib_nbnrnd(integerN,p,&integerx);
	*x = (double) integerx;
	return status;
}


int sci_distfun_rndnbn(char *fname,unsigned long fname_len)
{
	int rRows,rCols;
	int ma, na;
	int mb, nb;
	int readFlag;
	double * pa = NULL;
	double * pb = NULL;

	CheckInputArgument(pvApiCtx,2,4);
	CheckOutputArgument(pvApiCtx,1,1);

    // Arg #1 : a>=0
	readFlag = gwsupport_GetMatrixOfDoublesGreaterOrEqualThanZero( fname, 1, &pa, &ma, &na);
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	// Check that a has integer value
	readFlag = gwsupport_CheckDoubleMatrixHasIntegerValue (fname, 1, pa, ma, na);
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
    // Arg #2 : b
	readFlag = gwsupport_GetRealMatrixOfDoubles( fname, 2, &pb, &mb, &nb);
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	// Check that b in [0,1]
	readFlag = gwsupport_CheckDoubleMatrixInRange (fname, 2, pb, mb, nb, 0., 1.);
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	// Get [m,n], if any.
	readFlag = distfun_GetMNV_AB ( fname, *getNbInputArgument(pvApiCtx), &rRows, &rCols );
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
    // Compute the result (if necessary, expand the arguments a and b)
	readFlag = distfun_computeRandgenAB (fname, ma, na, pa, mb, nb, pb, rRows, rCols, mygennbn );
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	LhsVar(1) = Rhs+1;

	return 0;
}

/*
* Copyright (C)  2012 - 2014 - Michael Baudin
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

int sci_distfun_rndt(char *fname,unsigned long fname_len)
{
	int rRows,rCols;
	int readFlag;
	int ma, na;

	double * pa = NULL;

	CheckInputArgument(pvApiCtx,1,3);
	CheckOutputArgument(pvApiCtx,1,1);

    // Arg #1 : V > 0
	readFlag = gwsupport_GetMatrixOfDoublesGreaterThanZero (fname, 1, &pa, &ma, &na);
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	// Get [m,n], if any.
	readFlag = distfun_GetMNV_A ( fname, *getNbInputArgument(pvApiCtx), &rRows, &rCols );
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
    // Compute the result (if necessary, expand the argument df)
	readFlag = distfun_computeRandgenA (fname, ma, na, pa, rRows, rCols, cdflib_trnd);
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	LhsVar(1) = Rhs+1;

	return 0;
}

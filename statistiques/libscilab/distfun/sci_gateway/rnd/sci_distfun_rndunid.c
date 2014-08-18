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
#include "unifrng.h"

// From Scilab:
#include "api_scilab.h"
#include "Scierror.h"
#include "localization.h"
#include "stack-c.h"

int mygenintrange (double N, double *x)
{
	int status;
	// Check N
	if ( N > 2147483561 )
	{
		Scierror(999,_("%s: Wrong value for input argument #%d: ""%s"" expected .\n"),"distfun_uinrnd",1,"N <= 2147483561");
		status = CDFLIB_ERROR;
		return status;
	}
	if ( N < 1 )
	{
		Scierror(999,_("%s: Wrong value for input argument #%d: Must be >= %d.\n"),"distfun_uinrnd",1,1);
		status = CDFLIB_ERROR;
		return status;
	}
	*x=unifrng_generateIntegerInRange(1, N);
	status = CDFLIB_OK;
	return status;
}

int sci_distfun_rndunid(char *fname,unsigned long fname_len)
{
	int ma, na;
	int rRows,rCols;
	int readFlag;
	
	double * pa = NULL;

	CheckInputArgument(pvApiCtx,1,3);
	CheckOutputArgument(pvApiCtx,1,1);

    // Arg #1 : a
	readFlag = gwsupport_GetRealMatrixOfDoubles( fname, 1, &pa, &ma, &na);
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	readFlag = gwsupport_CheckDoubleMatrixHasIntegerValue (fname, 1, pa, ma, na );
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	readFlag = gwsupport_CheckDoubleMatrixGreaterOrEqual (fname, 1, pa, ma, na, 1. );
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	//
	// Get [m,n], if any.
	readFlag = distfun_GetMNV_A ( fname, *getNbInputArgument(pvApiCtx), &rRows, &rCols );
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
    // Compute the result (if necessary, expand the arguments a)
	readFlag = distfun_computeRandgenA (fname, ma, na, pa, rRows, rCols, mygenintrange );
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	LhsVar(1) = Rhs+1;
	return 0;
}

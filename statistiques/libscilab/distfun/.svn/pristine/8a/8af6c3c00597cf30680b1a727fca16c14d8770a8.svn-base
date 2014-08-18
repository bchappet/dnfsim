
/*
* Copyright (C)  2014 - Michael Baudin
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

int gw_distfunrnd_ksrnd(double n, double *x)
{
	int in;
	int status;
	in=(int) n;
	status=cdflib_ksrnd(in,x);
	return status;
}

int sci_distfun_rndks(char *fname,unsigned long fname_len)
{
	int rRows,rCols;
	int mN, nN;
	int readFlag;
	double * pN = NULL;

	CheckInputArgument(pvApiCtx,1,3);
	CheckOutputArgument(pvApiCtx,1,1);

	// Arg #1 : N>=1
	readFlag = gwsupport_GetRealMatrixOfDoubles( fname, 1, &pN, &mN, &nN);
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	readFlag = gwsupport_CheckDoubleMatrixHasNofractpart ( fname, 1, pN, mN , nN );
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	readFlag = gwsupport_CheckDoubleMatrixGreaterOrEqual (fname, 1, pN, mN, nN, 1.0);
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
    // Compute the result (if necessary, expand the argument N)
	readFlag = distfun_computeRandgenA (fname, mN, nN, pN, rRows, rCols, gw_distfunrnd_ksrnd );
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	LhsVar(1) = Rhs+1;

	return 0;
}

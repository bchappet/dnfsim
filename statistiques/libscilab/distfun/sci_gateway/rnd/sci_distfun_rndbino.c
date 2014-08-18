
/*
* Copyright (C)  2012 - Michael Baudin
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


int sci_distfun_rndbino(char *fname,unsigned long fname_len)
{
	int rRows,rCols;
	int mN, nN;
	int mpr, npr;
	int readFlag;
	double * pN = NULL;
	double * ppr = NULL;

	CheckInputArgument(pvApiCtx,2,4);
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
	// Arg #2 : pr
	readFlag = gwsupport_GetRealMatrixOfDoubles( fname, 2, &ppr, &mpr, &npr);
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	readFlag = gwsupport_CheckDoubleMatrixInRange (fname, 2, ppr, mpr, npr, 0.0, 1.0);
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
    // Compute the result (if necessary, expand the arguments N and pr)
	readFlag = distfun_computeRandgenAB (fname, mN, nN, pN, mpr, npr, ppr, rRows, rCols, cdflib_binornd );
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	LhsVar(1) = Rhs+1;

	return 0;
}

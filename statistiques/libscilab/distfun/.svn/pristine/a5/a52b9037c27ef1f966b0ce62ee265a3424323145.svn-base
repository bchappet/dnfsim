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
#include "cdflib.h"

// From Scilab:
#include "api_scilab.h"
#include "stack-c.h"


int sci_distfun_rndhyge(char *fname,unsigned long fname_len)
{
	int rRows,rCols;
	int mM, nM;
	int mk, nk;
	int mN, nN;
	int readFlag;
	double * pM = NULL;
	double * pk = NULL;
	double * pN = NULL;

	CheckInputArgument(pvApiCtx,3,5);
	CheckOutputArgument(pvApiCtx,1,1);

	// Arg #1 : M>=0
	readFlag = gwsupport_GetMatrixOfDoublesGreaterOrEqualThanZero( fname, 1, &pM, &mM, &nM);
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	readFlag = gwsupport_CheckDoubleMatrixHasIntegerValue ( fname, 1, pM, mM , nM );
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	// Arg #2 : k>=0
	readFlag = gwsupport_GetMatrixOfDoublesGreaterOrEqualThanZero( fname, 2, &pk, &mk, &nk);
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	readFlag = gwsupport_CheckDoubleMatrixHasIntegerValue ( fname, 2, pk, mk , nk );
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	// Arg #3 : N>=0
	readFlag = gwsupport_GetMatrixOfDoublesGreaterOrEqualThanZero( fname, 3, &pN, &mN, &nN);
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	readFlag = gwsupport_CheckDoubleMatrixHasIntegerValue ( fname, 3, pN, mN , nN );
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	// Get [m,n], if any.
	readFlag = distfun_GetMNV_ABC ( fname, *getNbInputArgument(pvApiCtx), &rRows, &rCols );
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
    // Compute the result (if necessary, expand the arguments N and pr)
	readFlag = distfun_computeRandgenABC (fname, mM, nM, pM, mk, nk, pk, mN, nN, pN, rRows, rCols, cdflib_hygernd );
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	LhsVar(1) = Rhs+1;

	return 0;
}

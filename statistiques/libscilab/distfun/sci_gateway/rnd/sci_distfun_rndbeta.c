/*
* Copyright (C)  2012 - 2014 Michael Baudin
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

int sci_distfun_rndbeta(char *fname,unsigned long fname_len)
{
	int rRows,rCols;
	int ma, na;
	int mb, nb;
	int readFlag;
	double * pa = NULL;
	double * pb = NULL;

	CheckInputArgument(pvApiCtx,2,4);
	CheckOutputArgument(pvApiCtx,1,1);

    // Arg #1 : a >= 0
	readFlag = gwsupport_GetMatrixOfDoublesGreaterOrEqualThanZero( fname, 1, &pa, &ma, &na);
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
    // Arg #2 : b >= 0
	readFlag = gwsupport_GetMatrixOfDoublesGreaterOrEqualThanZero( fname, 2, &pb, &mb, &nb);
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
	readFlag = distfun_computeRandgenAB (fname, ma, na, pa, mb, nb, pb, rRows, rCols, cdflib_betarnd );
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	LhsVar(1) = Rhs+1;

	return 0;
}

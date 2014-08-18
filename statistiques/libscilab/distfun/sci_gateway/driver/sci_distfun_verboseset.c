/*
* Copyright (C) 2012 - Michael Baudin
*
* This file must be used under the terms of the CeCILL.
* This source file is licensed as described in the file COPYING, which
* you should have received as part of this distribution.  The terms
* are also available at
* http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
*
*/

/*--------------------------------------------------------------------------*/
#include <string.h>

// From Distfun:
#include "gwsupport.h"
#include "cdflib.h"

// From Scilab:
#include "api_scilab.h"
#include "stack-c.h"
#include "Scierror.h"
#include "localization.h"

/*--------------------------------------------------------------------------*/
/*

y = distfun_verboseset(verbosemode) 

*/
/*--------------------------------------------------------------------------*/
int sci_distfun_verboseset(char* fname,unsigned long l)
{

	int iverbose;
	int* piAddr = NULL;
	int iType   = 0;
	int iRet    = 0;
	SciErr sciErr;
	int ivar=1;

	CheckInputArgument(pvApiCtx,1,1);
	CheckOutputArgument(pvApiCtx,1,1);

	sciErr = getVarAddressFromPosition(pvApiCtx, ivar, &piAddr);
	if(sciErr.iErr)
	{
		printError(&sciErr, 0);
		return 0;
	}
	if(isBooleanType(pvApiCtx, piAddr))
	{
		if(isScalar(pvApiCtx, piAddr))
		{
			iRet = getScalarBoolean(pvApiCtx, piAddr, &iverbose);
		}
		else
		{
			Scierror(999,_("%s: Wrong size for input argument #%d: %d-by-%d matrix expected.\n"),
				fname, ivar , 1, 1 );
			return 0;
		}
	}
	else if(isEmptyMatrix(pvApiCtx, piAddr))
	{
		// lowertail is empty: set the default value
		iverbose=CDFLIB_VERBOSEOFF;
	}
	else
	{
		Scierror(999,_("%s: Wrong type for argument %d: Boolean matrix expected.\n"),
			fname, ivar );
		return 0;
	}
	cdflib_verboseset(iverbose);
	LhsVar(1) = Rhs;
	return 0;
}
/*--------------------------------------------------------------------------*/

/*
* Copyright (C) 2012 - Michael Baudin
* Copyright (C) 2006-2008 - INRIA - 
* Copyright (C) 2010 - DIGITEO - Allan CORNET
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

// From distfun
#include "gwsupport.h"
#include "gw_distfuncdf.h"
#include "cdflib.h"
#include "gw_distfuncdf_support.h"

// From Scilab:
#include "api_scilab.h"
#include "stack-c.h"
#include "Scierror.h"
#include "localization.h"

/*--------------------------------------------------------------------------*/
/*

POIsson Inverse CDF

Calling sequences:

X=distfun_invpoiss(P,lambda,lowertail)

*/
/*--------------------------------------------------------------------------*/
int sci_distfun_invpoiss(char* fname,unsigned long l)
{
	int readFlag;

	int rowsLambda = 0, colsLambda = 0;
	int rowsP = 0, colsP = 0;
	int rowsX = 0, colsX = 0;

	double * lrLambda = NULL;
	double * lrP = NULL;
	double * lrX = NULL;

	int status = 0;
	int i;
	int ilowertail;

	CheckInputArgument(pvApiCtx,3,3);
	CheckOutputArgument(pvApiCtx,1,1);

	// Arg #1 : P
	readFlag = distfun_GetMatrixP( fname, 1, -1, -1, &lrP, &rowsP, &colsP);
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	// Arg #2 : lambda
	readFlag = distfun_GetSizedRealMatrixOfDoublesGreaterOrEqualThanZero( fname, 2, rowsP, colsP, &lrLambda, &rowsLambda , &colsLambda );
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	// Arg #3 : lowertail
	readFlag = distfun_GetIlowertail( fname, 3, &ilowertail);
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	// Create LHS : X
	rowsX = rowsLambda;
	colsX = colsLambda;
	iAllocMatrixOfDouble ( Rhs + 1 , rowsX , colsX , &lrX );
	LhsVar(1) = Rhs+1;
	// Fill X
	status = CDFLIB_OK;
	for ( i=0 ; i < rowsX*colsX; i++)
	{
		status=cdflib_poissinv(lrP[i], lrLambda[i], ilowertail, lrX+i);

		if (status != CDFLIB_OK)
		{
			distfun_defaultInvCDFError( fname, i );
			break;
		}
	}

	return 0;
}
/*--------------------------------------------------------------------------*/

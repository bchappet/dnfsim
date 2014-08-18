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

Gamma PDF

Calling sequences :

y = distfun_pdfgam ( x , a , b )

*/
/*--------------------------------------------------------------------------*/
int sci_distfun_pdfgam(char* fname,unsigned long l)
{
	int readFlag;
	int status;

	int rowsX = 0, colsX = 0;
	int rowsShape = 0, colsShape = 0;
	int rowsRate = 0, colsRate = 0;
	int rowsy = 0, colsy = 0;

	double * lrX = NULL;
	double * lrShape = NULL;
	double * lrRate = NULL;
	double * lry = NULL;

	int i;

	CheckInputArgument(pvApiCtx,3,3);
	CheckOutputArgument(pvApiCtx,1,1);

	// Arg #1 : X
	readFlag = distfun_GetSizedRealMatrixOfDoublesGreaterOrEqualThanZero( fname, 1, -1, -1, &lrX, &rowsX , &colsX );
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	// Arg #2 : Shape > 0
	readFlag = distfun_GetSizedRealMatrixOfDoublesGreaterThanZero( fname, 2, rowsX, colsX, &lrShape, &rowsShape , &colsShape );
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	// Arg #3 : Rate > 0
	readFlag = distfun_GetSizedRealMatrixOfDoublesGreaterThanZero( fname, 3, rowsX, colsX, &lrRate, &rowsRate , &colsRate );
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	// Create LHS : y
	rowsy = rowsX;
	colsy = colsX;
	iAllocMatrixOfDouble ( Rhs + 1 , rowsy , colsy , &lry );
	LhsVar(1) = Rhs+1;
	// Fill y
	status = CDFLIB_OK;
	for ( i=0 ; i < rowsX*colsX; i++)
	{
		status = cdflib_gammapdf( lrX[i] , lrShape[i], lrRate[i], lry+i );
		if (status != CDFLIB_OK)
		{
			distfun_defaultPDFError( fname, i );
			break;
		}
	}
	return 0;
}
/*--------------------------------------------------------------------------*/

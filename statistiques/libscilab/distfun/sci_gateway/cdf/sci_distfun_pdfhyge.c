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

Hypergeometric PDF

Calling sequences :

y=distfun_pdfhyge(x,M,k,N)

*/
/*--------------------------------------------------------------------------*/
int sci_distfun_pdfhyge(char* fname,unsigned long l)
{

	int readFlag;

	int rowsx = 0, colsx = 0;
	int rowsM = 0, colsM = 0;
	int rowsk = 0, colsk = 0;
	int rowsN = 0, colsN = 0;
	int rowsy = 0, colsy = 0;

	double * lrx = NULL;
	double * lrM = NULL;
	double * lrk = NULL;
	double * lrN = NULL;
	double * lry = NULL;

	int status = 0;

	int i;

	CheckInputArgument(pvApiCtx,4,4);
	CheckOutputArgument(pvApiCtx,1,1);

	// Arg #1 : x
	readFlag = distfun_GetSizedRealMatrixOfDoublesGreaterOrEqualThanZero( fname, 1, -1, -1, &lrx, &rowsx , &colsx );
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	// Arg #2 : M
	readFlag = distfun_GetSizedRealMatrixOfDoublesGreaterOrEqualThanZero( fname, 2, rowsx, colsx, &lrM, &rowsM , &colsM );
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	// Arg #3 : k
	readFlag = distfun_GetSizedRealMatrixOfDoublesGreaterOrEqualThanZero( fname, 3, rowsx, colsx, &lrk, &rowsk , &colsk );
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	// Arg #4 : N
	readFlag = distfun_GetSizedRealMatrixOfDoublesGreaterOrEqualThanZero( fname, 4, rowsx, colsx, &lrN, &rowsN , &colsN );
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	// Create LHS : y
	rowsy = rowsx;
	colsy = colsx;
	iAllocMatrixOfDouble ( Rhs + 1 , rowsy , colsy , &lry );
	LhsVar(1) = Rhs+1;
	// Fill y
	// Returns 1 if the PDF can be computed.
	// Returns 0 in case of error.
	status=CDFLIB_OK;
	for ( i=0 ; i < rowsx*colsx; i++)
	{
		//
		// Convert x, M, k, N into integers.
		// Check that the doubles have no fractional part.
		//
		status=cdflib_hygepdf(lrx[i], lrM[i], lrk[i], lrN[i], lry+i);
		if (status != CDFLIB_OK)
		{
			distfun_defaultPDFError( fname, i );
			break;
		}
	}
	return 0;
}
/*--------------------------------------------------------------------------*/

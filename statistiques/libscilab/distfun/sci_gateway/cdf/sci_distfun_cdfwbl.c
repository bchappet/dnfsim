/*
* Copyright (C) 2014 - Michael Baudin
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

Weibull CDF

Calling sequences :

P=distfun_cdfwbl(X,A,B,lowertail)

*/
/*--------------------------------------------------------------------------*/
int sci_distfun_cdfwbl(char* fname,unsigned long l)
{

	int readFlag;

	int rowsX = 0, colsX = 0;
	int rowsA = 0, colsA = 0;
	int rowsB = 0, colsB = 0;
	int rowsP = 0, colsP = 0;

	double * lrX = NULL;
	double * lrA = NULL;
	double * lrB = NULL;
	double * lrP = NULL;

	int status = 0;

	int ilowertail;
	int i;

	CheckInputArgument(pvApiCtx,4,4);
	CheckOutputArgument(pvApiCtx,1,1);

	// Arg #1 : X
	readFlag = distfun_GetSizedRealMatrixOfDoublesGreaterOrEqualThanZero( fname, 1, -1, -1, &lrX, &rowsX , &colsX );
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	// Arg #2 : A>0
	readFlag = distfun_GetSizedRealMatrixOfDoublesGreaterThanZero( fname, 2, rowsX, colsX, &lrA, &rowsA , &colsA );
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	// Arg #3 : B>0
	readFlag = distfun_GetSizedRealMatrixOfDoublesGreaterThanZero( fname, 3, rowsX, colsX, &lrB, &rowsB , &colsB );
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	// Arg #4 : lowertail
	readFlag = distfun_GetIlowertail( fname, 4, &ilowertail);
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}

	// Create LHS : P
	rowsP = rowsX;
	colsP = colsX;
	iAllocMatrixOfDouble ( Rhs + 1 , rowsP , colsP , &lrP );
	LhsVar(1) = Rhs+1;
	// Fill P
	status = CDFLIB_OK;
	for ( i=0 ; i < rowsX*colsX; i++)
	{
		status=cdflib_wblcdf(lrX[i], lrA[i], lrB[i], ilowertail, lrP+i);

		if (status != CDFLIB_OK)
		{
			distfun_defaultCDFError( fname, i );
			break;
		}
	}
	return 0;
}
/*--------------------------------------------------------------------------*/

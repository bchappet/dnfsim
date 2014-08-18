/*
* Copyright (C) 2012 - Michael Baudin
* Copyright (C) 2006-2008 - INRIA - 
* Copyright (C) 2010 - DIGITEO - Allan CORNET
*
* This file lambdast be used under the terms of the CeCILL.
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

Poisson PDF

Calling sequences:

y=distfun_pdfpoiss(x,lambda)

*/
/*--------------------------------------------------------------------------*/
int sci_distfun_pdfpoiss(char* fname,unsigned long l)
{
	int readFlag;

	int rowsX = 0, colsX = 0;
	int rowslambda = 0, colslambda = 0;
	int rowsy = 0, colsy = 0;

	double * lrX = NULL;
	double * lrlambda = NULL;
	double * lry = NULL;

	int status = 0;

	int i;

	CheckInputArgument(pvApiCtx,2,2);
	CheckOutputArgument(pvApiCtx,0,1);

	// Arg #1 : X>=0
	readFlag = distfun_GetSizedRealMatrixOfDoublesGreaterOrEqualThanZero( fname, 1, -1, -1, &lrX, &rowsX , &colsX );
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	// Arg #2 : lambda>0
	readFlag = distfun_GetSizedRealMatrixOfDoublesGreaterThanZero( fname, 2, rowsX, colsX, &lrlambda, &rowslambda , &colslambda );
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
		status=cdflib_poisspdf(lrX[i], lrlambda[i], lry+i);
		if (status != CDFLIB_OK)
		{
			distfun_defaultPDFError( fname, i );
			break;
		}
	}
	return 0;
}
/*--------------------------------------------------------------------------*/

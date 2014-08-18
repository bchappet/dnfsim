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

Weibull PDF

Calling sequences:

y=distfun_pdfwbl(x,a,b)

*/
/*--------------------------------------------------------------------------*/
int sci_distfun_pdfwbl(char* fname,unsigned long l)
{
	int readFlag;

	int rowsX = 0, colsX = 0;
	int rowsa = 0, colsa = 0;
	int rowsb = 0, colsb = 0;
	int rowsy = 0, colsy = 0;

	double * lrX = NULL;
	double * lra = NULL;
	double * lrb = NULL;
	double * lry = NULL;

	int status = 0;

	int i;

	CheckInputArgument(pvApiCtx,3,3);
	CheckOutputArgument(pvApiCtx,0,1);

	// Arg #1 : X
	readFlag = gwsupport_GetRealMatrixOfDoubles( fname, 1, &lrX, &rowsX , &colsX );
	if(readFlag==GWSUPPORT_ERROR)
	{
		return 0;
	}
	// Arg #2 : a
	readFlag = distfun_GetSizedRealMatrixOfDoublesGreaterThanZero( fname, 2, rowsX, colsX, &lra, &rowsa , &colsa );
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	// Arg #3 : b
	readFlag = distfun_GetSizedRealMatrixOfDoublesGreaterThanZero( fname, 3, rowsX, colsX, &lrb, &rowsb , &colsb );
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
		status=cdflib_wblpdf(lrX[i], lra[i], lrb[i], lry+i);
		if (status != CDFLIB_OK)
		{
			distfun_defaultPDFError( fname, i );
			break;
		}
	}
	return 0;
}
/*--------------------------------------------------------------------------*/

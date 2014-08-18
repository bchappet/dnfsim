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

F PDF

Calling sequence :

y = distfun_pdff(x,v1,v2)

*/
/*--------------------------------------------------------------------------*/
int sci_distfun_pdff(char* fname,unsigned long l)
{
	int readFlag;

	int rowsx = 0, colsx = 0;
	int rowsv1 = 0, colsv1 = 0;
	int rowsv2 = 0, colsv2 = 0;
	int rowsy = 0, colsy = 0;

	double * lrx = NULL;
	double * lrv1 = NULL;
	double * lrv2 = NULL;
	double * lry = NULL;

	int status = 0;

	int i;

	CheckInputArgument(pvApiCtx,3,3);
	CheckOutputArgument(pvApiCtx,1,1);

	// Arg #1 : x
	readFlag = distfun_GetSizedRealMatrixOfDoublesGreaterOrEqualThanZero( fname, 1, -1, -1, &lrx, &rowsx , &colsx );
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	// Arg #2 : v1
	readFlag = distfun_GetSizedRealMatrixOfDoublesGreaterOrEqualThanZero( fname, 2, rowsx, colsx, &lrv1, &rowsv1 , &colsv1 );
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	// Arg #3 : v2
	readFlag = distfun_GetSizedRealMatrixOfDoublesGreaterOrEqualThanZero( fname, 3,  rowsx, colsx, &lrv2, &rowsv2 , &colsv2 );
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
	status = CDFLIB_OK;
	for ( i=0 ; i < rowsx*colsx; i++)
	{
		status = cdflib_fpdf(lrx[i],lrv1[i],lrv2[i], lry+i);
		if (status != CDFLIB_OK)
		{
			distfun_defaultPDFError( fname, i );
			break;
		}
	}
	return 0;
}
/*--------------------------------------------------------------------------*/

/*
* Copyright (C) 2012 - 2014 - Michael Baudin
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

Negative BiNomiale CDF

Calling sequences :

P=distfun_cdfnbn(X,R,Pr,lowertail)

*/
/*--------------------------------------------------------------------------*/
int sci_distfun_cdfnbn(char* fname,unsigned long l)
{
	int readFlag;

	int rowsX = 0, colsX = 0;
	int rowsR = 0, colsR = 0;
	int rowsPr = 0, colsPr = 0;
	int rowsP = 0, colsP = 0;

	double * lrX = NULL;
	double * lrR = NULL;
	double * lrPr = NULL;
	double * lrP = NULL;
	int ilowertail;

	int status = 0;

	int i;

	CheckInputArgument(pvApiCtx,4,4);
	CheckOutputArgument(pvApiCtx,1,2);

	// Arg #1 : X
	readFlag = distfun_GetSizedRealMatrixOfDoublesGreaterOrEqualThanZero( fname, 1, -1, -1, &lrX, &rowsX , &colsX );
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	// Arg #2 : R
	readFlag = distfun_GetSizedRealMatrixOfDoublesGreaterOrEqualThanZero( fname, 2, rowsX, colsX, &lrR, &rowsR , &colsR );
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	// Arg #3 : Pr
	readFlag = distfun_GetMatrixP( fname, 3, rowsX, colsX, &lrPr, &rowsPr, &colsPr);
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
	// Create LHS : P, Q
	rowsP = rowsX;
	colsP = colsX;
	iAllocMatrixOfDouble ( Rhs + 1 , rowsP , colsP , &lrP );
	LhsVar(1) = Rhs+1;
	// Fill P
	status = CDFLIB_OK;
	for ( i=0 ; i < rowsX*colsX; i++)
	{
		status=cdflib_nbncdf(lrX[i], lrR[i], lrPr[i], ilowertail, lrP+i);

		if (status != CDFLIB_OK)
		{
			distfun_defaultCDFError( fname, i );
			break;
		}
	}
	return 0;
}
/*--------------------------------------------------------------------------*/

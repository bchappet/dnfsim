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

Uniform Inverse CDF

Calling sequences:

X=distfun_invunif(P,a,b,lowertail)

*/
/*--------------------------------------------------------------------------*/
int sci_distfun_invunif(char* fname,unsigned long l)
{
	int readFlag;

	int rowsa = 0, colsa = 0;
	int rowsb = 0, colsb = 0;
	int rowsP = 0, colsP = 0;
	int rowsX = 0, colsX = 0;

	double * lra = NULL;
	double * lrb = NULL;
	double * lrP = NULL;
	double * lrX = NULL;

	int status = 0;

	int i;
	int ilowertail;

	CheckInputArgument(pvApiCtx, 4, 4);
	CheckOutputArgument(pvApiCtx, 0, 1);

	// Arg #1 : P
	readFlag = distfun_GetMatrixP( fname, 1,  -1, -1, &lrP, &rowsP , &colsP);
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	// Arg #2 : a
	readFlag = distfun_GetSizedRealMatrixOfDoubles( fname, 2, rowsP, colsP, &lra, &rowsa , &colsa );
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	// Arg #3 : b
	readFlag = distfun_GetSizedRealMatrixOfDoubles( fname, 3, rowsP, colsP, &lrb, &rowsb , &colsb );
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
	// Create LHS : X
	rowsX = rowsP;
	colsX = colsP;
	iAllocMatrixOfDouble ( Rhs + 1 , rowsX , colsX , &lrX );
	LhsVar(1) = Rhs+1;
	// Fill X
	status = CDFLIB_OK;
	for ( i=0 ; i < rowsP*colsP; i++)
	{
		status=cdflib_unifinv(lrP[i], lra[i], lrb[i], ilowertail, lrX+i);
		if (status != CDFLIB_OK)
		{
			distfun_defaultInvCDFError( fname, i );
			break;
		}
	}

	return 0;
}
/*--------------------------------------------------------------------------*/
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
#include "Scierror.h"
#include "localization.h"

/*--------------------------------------------------------------------------*/
/*

Uniform CDF

Calling sequences:

p=distfun_cdfunif(x,a,b,lowertail)

*/
/*--------------------------------------------------------------------------*/
int sci_distfun_cdfunif(char* fname,unsigned long l)
{
	int readFlag;

	int rowsX = 0, colsX = 0;
	int rowsa = 0, colsa = 0;
	int rowsb = 0, colsb = 0;
	int rowsP = 0, colsP = 0;

	double * lrX = NULL;
	double * lra = NULL;
	double * lrb = NULL;
	double * lrP = NULL;

	int status = 0;
	int* piAddr = NULL;
	int ilowertail;	
	int i;

	CheckInputArgument(pvApiCtx, 4, 4);
	CheckOutputArgument(pvApiCtx, 0, 1);

	// Arg #1 : X
	readFlag = gwsupport_GetRealMatrixOfDoubles( fname, 1, &lrX, &rowsX , &colsX );
	if(readFlag==GWSUPPORT_ERROR)
	{
		return 0;
	}
	// Arg #2 : a
	readFlag = distfun_GetSizedRealMatrixOfDoubles( fname, 2, rowsX, colsX, &lra, &rowsa , &colsa );
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	// Arg #3 : b
	readFlag = distfun_GetSizedRealMatrixOfDoubles( fname, 3, rowsX, colsX, &lrb, &rowsb , &colsb );
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
		status=cdflib_unifcdf(lrX[i], lra[i], lrb[i], ilowertail, lrP+i);
		if (status != CDFLIB_OK)
		{
			Scierror(999,_("%s: Cannot evaluate Uniform CDF.\n"),fname);
			break;
		}
	}
	return 0;
}
/*--------------------------------------------------------------------------*/

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

Log-Normale CDF

Calling sequences:

p=distfun_cdflogn(x,mu,sigma,lowertail)

*/
/*--------------------------------------------------------------------------*/
int sci_distfun_cdflogn(char* fname,unsigned long l)
{
	int readFlag;

	int rowsX = 0, colsX = 0;
	int rowsmu = 0, colsmu = 0;
	int rowssigma = 0, colssigma = 0;
	int rowsP = 0, colsP = 0;

	double * lrX = NULL;
	double * lrmu = NULL;
	double * lrsigma = NULL;
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
	// Arg #2 : mu
	readFlag = distfun_GetSizedRealMatrixOfDoubles( fname, 2, rowsX, colsX, &lrmu, &rowsmu , &colsmu );
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	// Arg #3 : sigma
	readFlag = distfun_GetSizedRealMatrixOfDoubles( fname, 3, rowsX, colsX, &lrsigma, &rowssigma , &colssigma );
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
		status=cdflib_logncdf(lrX[i], lrmu[i], lrsigma[i], ilowertail, lrP+i);
		if (status != CDFLIB_OK)
		{
			Scierror(999,_("%s: Cannot evaluate Log-Normale CDF.\n"),fname);
			break;
		}
	}
	return 0;
}
/*--------------------------------------------------------------------------*/
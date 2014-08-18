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

Log-Normale Inverse CDF

Calling sequences:

X=distfun_invlogn(P,mu,sigma,lowertail)

*/
/*--------------------------------------------------------------------------*/
int sci_distfun_invlogn(char* fname,unsigned long l)
{
	int readFlag;

	int rowsmu = 0, colsmu = 0;
	int rowssigma = 0, colssigma = 0;
	int rowsP = 0, colsP = 0;
	int rowsX = 0, colsX = 0;

	double * lrmu = NULL;
	double * lrsigma = NULL;
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
	// Arg #2 : mu
	readFlag = distfun_GetSizedRealMatrixOfDoubles( fname, 2, rowsP, colsP, &lrmu, &rowsmu , &colsmu );
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	// Arg #3 : sigma
	readFlag = distfun_GetSizedRealMatrixOfDoubles( fname, 3, rowsP, colsP, &lrsigma, &rowssigma , &colssigma );
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
		status=cdflib_logninv(lrP[i], lrmu[i], lrsigma[i], ilowertail, lrX+i);
		if (status != CDFLIB_OK)
		{
			distfun_defaultInvCDFError( fname, i );
			break;
		}
	}

	return 0;
}
/*--------------------------------------------------------------------------*/

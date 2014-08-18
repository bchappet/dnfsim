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

Kolmogorov Smirnov Inverse CDF

Calling sequences :

X=distfun_invks(P,N,lowertail)

*/
/*--------------------------------------------------------------------------*/
int sci_distfun_invks(char* fname,unsigned long l)
{
	int readFlag;

	int rowsN = 0, colsN = 0;
	int rowsP = 0, colsP = 0;
	int rowsX = 0, colsX = 0;

	double * lrN = NULL;
	double * lrP = NULL;
	double * lrX = NULL;

	int status = 0;

	int i;
	int ilowertail;
	int Ni;

	CheckInputArgument(pvApiCtx,3,3);
	CheckOutputArgument(pvApiCtx,1,1);

	// Arg #1 : P
	readFlag = distfun_GetMatrixP( fname, 1, -1, -1, &lrP, &rowsP, &colsP);
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	// Arg #2 : N
	readFlag = distfun_GetSizedRealMatrixOfDoublesGreaterOrEqualThanZero( fname, 2, rowsP, colsP, &lrN, &rowsN , &colsN );
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	readFlag = gwsupport_CheckDoubleMatrixHasNofractpart ( fname, 2, lrN, rowsN , colsN );
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	// Arg #3 : lowertail
	readFlag = distfun_GetIlowertail( fname, 3, &ilowertail);
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
	for ( i=0 ; i < rowsX*colsX; i++)
	{
		status=gwsupport_Double2IntegerArgument (fname, 2, lrN[i], &Ni);
		if(readFlag==GWSUPPORT_ERROR)
		{
			return 0;
		}
		status=cdflib_ksinv(lrP[i], Ni, ilowertail, lrX+i);

		if (status != CDFLIB_OK)
		{
			distfun_defaultInvCDFError( fname, i );
			break;
		}
	}

	return 0;
}
/*--------------------------------------------------------------------------*/

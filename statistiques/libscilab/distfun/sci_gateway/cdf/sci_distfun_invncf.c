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

Non-central F Inverse CDF

Calling sequences :

X=distfun_invncf(P,Dfn,Dfd,Pnonc,lowertail);

*/
/*--------------------------------------------------------------------------*/
int sci_distfun_invncf(char* fname,unsigned long l)
{
	int readFlag;

	int rowsDfn = 0, colsDfn = 0;
	int rowsDfd = 0, colsDfd = 0;
	int rowsPnonc = 0, colsPnonc = 0;
	int rowsP = 0, colsP = 0;
	int rowsX = 0, colsX = 0;

	double * lrDfn = NULL;
	double * lrDfd = NULL;
	double * lrPnonc = NULL;
	double * lrP = NULL;
	double * lrX = NULL;

	int status = 0;
	int ilowertail;
	int i;

	CheckInputArgument(pvApiCtx,5,5);
	CheckOutputArgument(pvApiCtx,1,1);

	// Arg #1 : P
	readFlag = distfun_GetMatrixP( fname, 1, -1, -1, &lrP, &rowsP, &colsP);
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	// Arg #2 : Dfn
	readFlag = distfun_GetSizedRealMatrixOfDoublesGreaterOrEqualThanZero( fname, 2, rowsP, colsP, &lrDfn, &rowsDfn , &colsDfn );
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	// Arg #3 : Dfd
	readFlag = distfun_GetSizedRealMatrixOfDoublesGreaterOrEqualThanZero( fname, 3, rowsP, colsP, &lrDfd, &rowsDfd , &colsDfd );
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	// Arg #4 : Pnonc
	readFlag = distfun_GetSizedRealMatrixOfDoublesGreaterOrEqualThanZero( fname, 4,  rowsP, colsP, &lrPnonc, &rowsPnonc , &colsPnonc );
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	// Arg #5 : lowertail
	readFlag = distfun_GetIlowertail( fname, 5, &ilowertail);
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
	for ( i=0 ; i < rowsDfn*colsDfn; i++)
	{
		status=cdflib_ncfinv(lrP[i], lrDfn[i], lrDfd[i], lrPnonc[i],ilowertail,lrX+i);

		if (status != CDFLIB_OK)
		{
			distfun_defaultInvCDFError( fname, i );
			break;
		}
	}

	return 0;
}
/*--------------------------------------------------------------------------*/

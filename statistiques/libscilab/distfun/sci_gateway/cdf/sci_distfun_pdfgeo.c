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

Geometric PDF

Calling sequences:

y=distfun_pdfgeo(x,pr)

*/
/*--------------------------------------------------------------------------*/
int sci_distfun_pdfgeo(char* fname,unsigned long l)
{
	int readFlag;

	int rowsx = 0, colsx = 0;
	int rowspr = 0, colspr = 0;
	int rowsy = 0, colsy = 0;

	double * lrx = NULL;
	double * lrpr = NULL;
	double * lry = NULL;

	int status = 0;

	int i;

	CheckInputArgument(pvApiCtx,2,2);
	CheckOutputArgument(pvApiCtx,0,1);

	// Arg #1 : X
	readFlag = gwsupport_GetRealMatrixOfDoubles( fname, 1, &lrx, &rowsx , &colsx );
	if(readFlag==GWSUPPORT_ERROR)
	{
		return 0;
	}
	// Arg #2 : pr
	readFlag = distfun_GetMatrixP( fname, 2, rowsx, colsx, &lrpr, &rowspr , &colspr	);
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
		status=cdflib_geopdf(lrx[i], lrpr[i], lry+i);
		if (status != CDFLIB_OK)
		{
			distfun_defaultPDFError( fname, i );
			break;
		}
	}
	return 0;
}
/*--------------------------------------------------------------------------*/

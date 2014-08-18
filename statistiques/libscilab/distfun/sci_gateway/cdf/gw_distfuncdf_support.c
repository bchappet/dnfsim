
// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2008 - INRIA - Michael Baudin
// Copyright (C) 2009 - Digiteo - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#include <limits.h>

#include "api_scilab.h"
#include "stack-c.h" 
#include "Scierror.h"
#include "localization.h"

#include "gwsupport.h" 
#include "gw_distfuncdf_support.h" 


int distfun_GetMatrixP( char * fname, int ivar , int rowsExpected, int colsExpected ,
	double** lrP, int * rowsP , int * colsP	)
{
	int readFlag = 0;

	readFlag = gwsupport_GetRealMatrixOfDoubles( fname, ivar, lrP, rowsP , colsP );
	if(readFlag==GWSUPPORT_ERROR)
	{
		return DISTFUNCDFGW_ERROR;
	}
	if ( rowsExpected > 0 )
	{
		readFlag = gwsupport_CheckSize ( fname , ivar, rowsExpected , colsExpected , *rowsP , *colsP );
		if (readFlag==GWSUPPORT_ERROR)
		{
			return DISTFUNCDFGW_ERROR;
		}
	}
	readFlag = gwsupport_CheckDoubleMatrixInRange (fname, ivar, *lrP, *rowsP , *colsP, 0., 1.);
	if (readFlag==GWSUPPORT_ERROR)
	{
		return DISTFUNCDFGW_ERROR;
	}
	return DISTFUNCDFGW_OK;
}

void distfun_defaultCDFError( char * fname, int index )
{
	Scierror(999,_("%s: Cannot evaluate CDF for entry (%d).\n"), fname,index+1);
	return;
}

void distfun_defaultInvCDFError( char * fname, int index )
{
	Scierror(999,_("%s: Cannot evaluate Inverse CDF for entry (%d).\n"), fname,index+1);
	return;
}

void distfun_defaultPDFError( char * fname, int index )
{
	Scierror(999,_("%s: Cannot evaluate PDF for entry (%d).\n"), fname,index+1);
	return;
}

int distfun_GetMatricesPQ( char * fname, int ivar, int rowsExpected, int colsExpected ,
	double** lrP, int * rowsP , int * colsP, 
	double** lrQ, int * rowsQ , int * colsQ )
{
	int readFlag;

	// Arg #ivar : P
	readFlag = distfun_GetMatrixP( fname, ivar, rowsExpected, colsExpected, lrP, rowsP , colsP );
	if(readFlag==0)
	{
		return DISTFUNCDFGW_ERROR;
	}
	// Arg #(ivar+1) : Q
	readFlag = distfun_GetMatrixP( fname, ivar+1, rowsExpected, colsExpected, lrQ, rowsQ , colsQ );
	if(readFlag==0)
	{
		return DISTFUNCDFGW_ERROR;
	}
	// Check P+Q ~ 1
	readFlag = distfun_CheckXYEqualOne (fname, *lrP, *lrQ, *rowsP, *colsP);
	if (readFlag==0)
	{
		return DISTFUNCDFGW_ERROR;
	}
	return DISTFUNCDFGW_OK;
}

int distfun_CheckXYEqualOne (char *fname, double* lrX, double* lrY, int rowsX , int colsX)
{
	double x, y;
	double xy;
	int i;
	double eps;

	eps = dlamch_("p", (long int)1);

	for ( i=0 ; i < rowsX*colsX; i++)
	{
		x = *(lrX + i );
		y = *(lrY + i );
		xy = x+y;
		if (fabs(xy-1.) > 3*eps )
		{
			Scierror(999,_("%s: X + Y not equal to 1.\n"),fname);
			return DISTFUNCDFGW_ERROR;
		}
	}
	return DISTFUNCDFGW_OK;
}

int distfun_GetSizedRealMatrixOfDoubles( char * fname, int ivar , int rowsExpected, int colsExpected ,
	double** _pdblReal, int * rowsA , int * colsA	)
{
	int readFlag;

	readFlag = gwsupport_GetRealMatrixOfDoubles( fname, ivar , _pdblReal, rowsA , colsA	);
	if(readFlag==0)
	{
		return DISTFUNCDFGW_ERROR;
	}
	if ( rowsExpected > 0 )
	{
		readFlag = gwsupport_CheckSize ( fname , ivar, rowsExpected , colsExpected , *rowsA , *colsA );
		if (readFlag==0)
		{
			return DISTFUNCDFGW_ERROR;
		}
	}
	return DISTFUNCDFGW_OK;
}

int distfun_GetSizedRealMatrixOfDoublesGreaterOrEqualThanZero( char * fname, int ivar , int rowsExpected, int colsExpected ,
	double** _pdblReal, int * rowsA , int * colsA	)
{
	int readFlag;

	readFlag = gwsupport_GetMatrixOfDoublesGreaterOrEqualThanZero( fname, ivar , _pdblReal, rowsA , colsA	);
	if(readFlag==0)
	{
		return DISTFUNCDFGW_ERROR;
	}
	if ( rowsExpected > 0 )
	{
		readFlag = gwsupport_CheckSize ( fname , ivar, rowsExpected , colsExpected , *rowsA , *colsA );
		if (readFlag==0)
		{
			return DISTFUNCDFGW_ERROR;
		}
	}
	return DISTFUNCDFGW_OK;
}

int distfun_GetSizedRealMatrixOfDoublesGreaterThanZero( char * fname, int ivar , int rowsExpected, int colsExpected ,
	double** _pdblReal, int * rowsA , int * colsA	)
{
	int readFlag;

	readFlag = gwsupport_GetMatrixOfDoublesGreaterThanZero( fname, ivar , _pdblReal, rowsA , colsA	);
	if(readFlag==0)
	{
		return DISTFUNCDFGW_ERROR;
	}
	if ( rowsExpected > 0 )
	{
		readFlag = gwsupport_CheckSize ( fname , ivar, rowsExpected , colsExpected , *rowsA , *colsA );
		if (readFlag==0)
		{
			return DISTFUNCDFGW_ERROR;
		}
	}
	return DISTFUNCDFGW_OK;
}

int distfun_GetIlowertail( char * fname, int ivar , int* ilowertail)
{
	int* piAddr = NULL;
	int iType   = 0;
	int iRet    = 0;
	SciErr sciErr;
	
	sciErr = getVarAddressFromPosition(pvApiCtx, ivar, &piAddr);
	if(sciErr.iErr)
	{
		printError(&sciErr, 0);
		return DISTFUNCDFGW_ERROR;
	}
	if(isBooleanType(pvApiCtx, piAddr))
	{
		if(isScalar(pvApiCtx, piAddr))
		{
			iRet = getScalarBoolean(pvApiCtx, piAddr, ilowertail);
		}
		else
		{
			Scierror(999,_("%s: Wrong size for input argument #%d: %d-by-%d matrix expected.\n"),
				fname, ivar , 1, 1 );
			return DISTFUNCDFGW_ERROR;
		}
	}
	else if(isEmptyMatrix(pvApiCtx, piAddr))
	{
		// lowertail is empty: set the default value
		*ilowertail=1;
	}
	else
	{
		Scierror(999,_("%s: Wrong type for argument %d: Boolean matrix expected.\n"),
			fname, ivar );
		return DISTFUNCDFGW_ERROR;
	}
	return DISTFUNCDFGW_OK;
}



// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2008 - INRIA - Michael Baudin
// Copyright (C) 2009 - Digiteo - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#include <limits.h>

// From Scilab
#include "api_scilab.h"
#include "stack-c.h" 
#include "Scierror.h"
#include "localization.h"
#include "warningmode.h"
#include "sciprint.h"

#include "gwsupport.h" 

int gwsupport_CheckSize ( char * fname , int ivar , int expected_nrows , int expected_ncols , 
	int actual_nrows , int actual_ncols )
{
	if ( expected_nrows != actual_nrows )
	{
		Scierror(999,_("%s: Wrong size for input argument #%d: %d-by-%d matrix expected.\n"),
			fname, ivar , expected_nrows, expected_ncols );
		return GWSUPPORT_ERROR;
	} 
	if ( expected_ncols != actual_ncols )
	{
		Scierror(999,_("%s: Wrong size for input argument #%d: %d-by-%d matrix expected.\n"),
			fname, ivar , expected_nrows, expected_ncols );
		return GWSUPPORT_ERROR;
	}
	return GWSUPPORT_OK;
}

int gwsupport_CheckVectorSize ( char * fname , int ivar , int expected_n, int actual_nrows , int actual_ncols )
{
	if ( actual_nrows != 1 && actual_ncols !=1 )
	{
		Scierror(999,_("%s: Wrong size for input argument #%d: %d-by-%d matrix expected.\n"),
			fname, ivar , expected_n, 1 );
		return GWSUPPORT_ERROR;
	}
	if ( actual_nrows*actual_ncols != expected_n )
	{
		Scierror(999,_("%s: Wrong size for input argument #%d: %d-by-%d matrix expected.\n"),
			fname, ivar , expected_n, 1 );
		return GWSUPPORT_ERROR;
	}
	return GWSUPPORT_OK;
}

int gwsupport_AllocateLhsMatrixOfDoubles ( int ovar , int _piRows , int _piCols , double ** _pdblReal )
{
	SciErr sciErr;
	sciErr = allocMatrixOfDouble(pvApiCtx, Rhs + ovar, _piRows, _piCols, _pdblReal);
	if(sciErr.iErr)
	{
		printError(&sciErr, 0);
		return GWSUPPORT_ERROR;
	}
	LhsVar(ovar) = Rhs+ovar;
	return GWSUPPORT_OK;
}

int gwsupport_GetRealMatrixOfDoubles( char * fname, int ivar, double** _pdblReal, int * _piRows , int * _piCols )
{
	int iType = 0;
	int iComplex = 0;
	SciErr sciErr;
	int *_piAddress;

	sciErr = getVarAddressFromPosition(pvApiCtx, ivar, &_piAddress);
	if(sciErr.iErr)
	{
		printError(&sciErr, 0);
		return GWSUPPORT_ERROR;
	}
	sciErr = getMatrixOfDouble(pvApiCtx, _piAddress, _piRows, _piCols, _pdblReal);
	if(sciErr.iErr)
	{
		printError(&sciErr, 0);
		return GWSUPPORT_ERROR;
	}
	return GWSUPPORT_OK;
}
int gwsupport_CheckDoubleMatrixInRange (char *fname, int ivar, double* _pdblReal, int _piRows , int _piCols, 
	double mindouble, double maxdouble)
{
	int status;
	double t;
	int i;

	for ( i=0 ; i < _piRows*_piCols; i++)
	{
		t = *(_pdblReal + i );
		status = gwsupport_CheckDoubleInRange (fname, ivar, t, mindouble, maxdouble);
		if (status != GWSUPPORT_OK)
		{
			Scierror(999,_("%s: Wrong value for argument #%d: Wrong entry at index (%d). Must be in [%e,%e]\n"),fname, ivar, i+1, mindouble, maxdouble );
			return GWSUPPORT_ERROR;
		}
	}
	return GWSUPPORT_OK;
}

int gwsupport_CheckDoubleMatrixGreaterOrEqual (char *fname, int ivar, double* _pdblReal, int _piRows , int _piCols, 
	double mindouble)
{
	double t;
	int i;
	int status;

	for ( i=0 ; i < _piRows*_piCols; i++)
	{
		t = *(_pdblReal + i );
		status = gwsupport_CheckDoubleGreaterOrEqual (fname, ivar, t, mindouble);
		if ( status != GWSUPPORT_OK )
		{
			Scierror(999,_("%s: Wrong value for argument #%d: Wrong entry at index (%d). Must be >=%e.\n"),fname, ivar, i+1, mindouble );
			return GWSUPPORT_ERROR;
		}
	}
	return GWSUPPORT_OK;
}

int gwsupport_CheckDoubleGreaterOrEqual (char *fname, int ivar, double value, double mindouble)
{
	if ( value < mindouble )
	{
		Scierror(204,_("%s: Wrong value for input argument #%d: Must be >= %e.\n"),fname, ivar, mindouble);
		return GWSUPPORT_ERROR;
	}
	return GWSUPPORT_OK;
}

int gwsupport_CheckDoubleMatrixGreaterThan (char *fname, int ivar, double* _pdblReal, int _piRows , int _piCols, 
	double mindouble)
{
	double t;
	int i;

	for ( i=0 ; i < _piRows*_piCols; i++)
	{
		t = *(_pdblReal + i );
		if ( t <= mindouble )
		{
			Scierror(204,_("%s: Wrong value for input argument #%d: Wrong entry at index (%d). Must be > %e.\n"),fname, ivar, i+1, mindouble);
			return GWSUPPORT_ERROR;
		}
	}
	return GWSUPPORT_OK;
}

int gwsupport_CheckDoubleInRange (char *fname, int ivar, double mydouble, double mindouble, double maxdouble)
{
	if ( mydouble > maxdouble )
	{
		Scierror(204,_("%s: Wrong value for input argument #%d: Must be <= %e.\n"),fname, ivar, maxdouble);
		return GWSUPPORT_ERROR;
	}
	if ( mydouble < mindouble )
	{
		Scierror(204,_("%s: Wrong value for input argument #%d: Must be >= %e.\n"),fname, ivar, mindouble);
		return GWSUPPORT_ERROR;
	}
	return GWSUPPORT_OK;
}

int gwsupport_GetMatrixOfDoublesGreaterOrEqualThanZero( char * fname, int ivar , double** _pdblReal, int * rowsA , int * colsA	)
{
	int status;

	status = gwsupport_GetRealMatrixOfDoubles( fname, ivar, _pdblReal, rowsA , colsA );
	if(status != GWSUPPORT_OK)
	{
		return GWSUPPORT_ERROR;
	}
	status = gwsupport_CheckDoubleMatrixGreaterOrEqual (fname, ivar, *_pdblReal, *rowsA , *colsA, 0.);
	if (status != GWSUPPORT_OK)
	{
		return GWSUPPORT_ERROR;
	}
	return GWSUPPORT_OK;
}

int gwsupport_GetMatrixOfDoublesGreaterThanZero( char * fname, int ivar , double** _pdblReal, int * rowsA , int * colsA	)
{
	int status;

	status = gwsupport_GetRealMatrixOfDoubles( fname, ivar, _pdblReal, rowsA , colsA );
	if(status != GWSUPPORT_OK)
	{
		return GWSUPPORT_ERROR;
	}
	status = gwsupport_CheckDoubleMatrixGreaterThan (fname, ivar, *_pdblReal, *rowsA , *colsA, 0.);
	if (status != GWSUPPORT_OK)
	{
		return GWSUPPORT_ERROR;
	}
	return GWSUPPORT_OK;
}
int gwsupport_Double2IntegerArgument ( char * fname , int ivar , double dvalue , int * ivalue )
{
	int status;

	status = gwsupport_CheckDoubleHasIntegerValue ( fname , ivar , dvalue );
	if ( status != GWSUPPORT_OK ) 
	{
		return GWSUPPORT_ERROR;
	}
	*ivalue = (int) dvalue;	
	return GWSUPPORT_OK;
}
int gwsupport_CheckDoubleMatrixHasIntegerValue ( char * fname , int ivar , double * p, int nrows , int ncols )
{
	int i;
	int status;

	for ( i=0 ; i < nrows*ncols ; i++)
	{
		status = gwsupport_CheckDoubleHasIntegerValue ( fname, ivar, *(p+i) );
		if ( status != GWSUPPORT_OK)
		{ 
			Scierror(999,_("%s: Wrong value for argument #%d: Wrong entry at index (%d). Must be integer. \n"),fname, ivar, i+1 );
			return GWSUPPORT_ERROR;
		}
	}
	return GWSUPPORT_OK;
}

int gwsupport_CheckDoubleHasIntegerValue ( char * fname , int ivar , double dvalue )
{
	int status;

	if ( dvalue > INT_MAX ) {
		Scierror(999,_("%s: Too large integer value in argument #%d: found %e while maximum value is %d.\n"),fname,ivar , dvalue , INT_MAX );
		return GWSUPPORT_ERROR;
	}
	if ( dvalue < INT_MIN ) {
		Scierror(999,_("%s: Too large integer value in argument #%d: found %e while minimum value is %d.\n"),fname,ivar , dvalue , INT_MIN );
		return GWSUPPORT_ERROR;
	}
	status=gwsupport_CheckDoubleHasNofractpart ( fname, ivar, dvalue );
	if (status!=GWSUPPORT_OK)
	{
		return status;
	}
	return GWSUPPORT_OK;
}

int gwsupport_CheckDoubleHasNofractpart ( char * fname , int ivar , double dvalue )
{
	double floorvalue;

	floorvalue = floor(dvalue);
	if ( dvalue != floorvalue ) {
		Scierror(999,_("%s: Wrong integer in argument #%d: found %.17e which has a fractionnal part.\n"),fname,ivar , dvalue );
		return GWSUPPORT_ERROR;
	}
	
	return GWSUPPORT_OK;
}

int gwsupport_CheckDoubleMatrixHasNofractpart ( char * fname , int ivar , double * p, int nrows , int ncols )
{
	int i;
	int status;

	for ( i=0 ; i < nrows*ncols ; i++)
	{
		status = gwsupport_CheckDoubleHasNofractpart ( fname, ivar, *(p+i) );
		if ( status != GWSUPPORT_OK)
		{ 
			Scierror(999,_("%s: Wrong value for argument #%d: Wrong entry at index (%d). Must have no fractional part.\n"),fname, ivar, i+1 );
			return GWSUPPORT_ERROR;
		}
	}
	return GWSUPPORT_OK;
}

int gwsupport_GetOneIntegerArgument ( char * fname , int ivar , int * value )
{
	int _piRows;
	int _piCols;
	double * _pdblReal;
	int status;
	
	status = gwsupport_GetRealMatrixOfDoubles( fname, ivar , &_pdblReal, &_piRows , &_piCols );
	if ( status != GWSUPPORT_OK ) 
	{
		return GWSUPPORT_ERROR;
	}
	status = gwsupport_CheckSize ( fname , ivar , 1 , 1, _piRows , _piCols );
	if ( status != GWSUPPORT_OK ) 
	{
		return GWSUPPORT_ERROR;
	}
	status = gwsupport_Double2IntegerArgument ( fname , ivar , _pdblReal[0] , value );
	if ( status != GWSUPPORT_OK ) 
	{
		return GWSUPPORT_ERROR;
	}
	return GWSUPPORT_OK;
}

int gwsupport_CheckDoubleGreaterThan (char *fname, int ivar, double value, double mindouble)
{
	if ( value <= mindouble )
	{
		Scierror(204,_("%s: Wrong value for input argument #%d: Must be > %e.\n"),fname, ivar, mindouble);
		return GWSUPPORT_ERROR;
	}
	return GWSUPPORT_OK;
}

void gwsupport_PrintWarning ( char * message ) 
{
	if ( getWarningMode() )
	{
		sciprint( _("WARNING: %s"), message );
	}
}

int gwsupport_GetScalarDouble( char * fname, int ivar , double* lrA )
{
	int m, n;
	int status;
	double* _pdblReal;

	status = gwsupport_GetRealMatrixOfDoubles( fname, ivar, &_pdblReal, &m, &n);
	*lrA = *_pdblReal;
	if ( status != GWSUPPORT_OK)
	{ 
		return GWSUPPORT_ERROR;
	}
	status = gwsupport_CheckSize ( fname, ivar, 1, 1, m, n);
	if ( status != GWSUPPORT_OK)
	{ 
		return GWSUPPORT_ERROR;
	}
	return GWSUPPORT_OK;
}
int gwsupport_GetScalarString( char * fname, int ivar , char** mystring )
{
	int *_piAddress;
	int iRet = 0;
	SciErr sciErr;

	sciErr = getVarAddressFromPosition(pvApiCtx, ivar, &_piAddress);
	if(sciErr.iErr)
	{
		printError(&sciErr, 0);
		return GWSUPPORT_ERROR;
	}
	iRet = getAllocatedSingleString(pvApiCtx, _piAddress, mystring);
	if (iRet)
	{
        Scierror(999,_("%s: Wrong type for input argument #%d: Single string expected.\n" ), fname,ivar);
		return GWSUPPORT_ERROR;
	}
	return GWSUPPORT_OK;
}

int gwsupport_CheckDoubleMatrixLesserOrEqual (char *fname, int ivar, double* _pdblReal, int _piRows , int _piCols, 
	double maxdouble)
{
	double t;
	int i;
	int status;

	for ( i=0 ; i < _piRows*_piCols; i++)
	{
		t = *(_pdblReal + i );
		status = gwsupport_CheckDoubleLesserOrEqual (fname, ivar, t, maxdouble);
		if ( status != GWSUPPORT_OK )
		{
			Scierror(999,_("%s: Wrong value for argument #%d: Wrong entry at index (%d). Must be <=%e\n"),fname, ivar, i+1, maxdouble );
			return GWSUPPORT_ERROR;
		}
	}
	return GWSUPPORT_OK;
}

int gwsupport_CheckDoubleLesserOrEqual (char *fname, int ivar, double value, double maxdouble)
{
	if ( value > maxdouble )
	{
		Scierror(204,_("%s: Wrong value for input argument #%d: Must be <= %e.\n"),fname, ivar, maxdouble);
		return GWSUPPORT_ERROR;
	}
	return GWSUPPORT_OK;
}

int gwsupport_CheckDoubleLesserThan (char *fname, int ivar, double value, double maxdouble)
{
	if ( value >= maxdouble )
	{
		Scierror(204,_("%s: Wrong value for input argument #%d: Must be < %e.\n"),fname, ivar, maxdouble);
		return GWSUPPORT_ERROR;
	}
	return GWSUPPORT_OK;
}

int gwsupport_CheckDoubleMatrixLesserThan (char *fname, int ivar, double* _pdblReal, int _piRows , int _piCols, 
	double maxdouble)
{
	double t;
	int i;
	int status;

	for ( i=0 ; i < _piRows*_piCols; i++)
	{
		t = *(_pdblReal + i );
		status = gwsupport_CheckDoubleLesserThan (fname, ivar, t, maxdouble);
		if ( status != GWSUPPORT_OK )
		{
			Scierror(999,_("%s: Wrong value for argument #%d: Wrong entry at index (%d). Must be < %e\n"),fname, ivar, i+1, maxdouble );
			return GWSUPPORT_ERROR;
		}
	}
	return GWSUPPORT_OK;
}

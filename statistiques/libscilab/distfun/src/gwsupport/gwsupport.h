
// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2008-2009 - INRIA - Michael Baudin
// Copyright (C) 2009 - Digiteo - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

//
// gwsupport.h
//   Header for the C gateway support functions for DISTFUN
//
#ifndef __SCI_GWSUPPORT_H__
#define __SCI_GWSUPPORT_H__

#ifdef _MSC_VER
	#if LIBGWSUPPORT_EXPORTS 
		#define GWSUPPORT_IMPORTEXPORT __declspec (dllexport)
	#else
		#define GWSUPPORT_IMPORTEXPORT __declspec (dllimport)
	#endif
#else
	#define GWSUPPORT_IMPORTEXPORT
#endif

#undef __BEGIN_DECLS
#undef __END_DECLS
#ifdef __cplusplus
# define __BEGIN_DECLS extern "C" {
# define __END_DECLS }
#else
# define __BEGIN_DECLS /* empty */
# define __END_DECLS /* empty */
#endif

__BEGIN_DECLS

static int GWSUPPORT_OK = 1;
static int GWSUPPORT_ERROR = 0;

/*
Index of functions:

gwsupport_AllocateLhsMatrixOfDoubles
gwsupport_CheckDoubleGreaterOrEqual
gwsupport_CheckDoubleGreaterThan
gwsupport_CheckDoubleHasIntegerValue
gwsupport_CheckDoubleHasNofractpart
gwsupport_CheckDoubleInRange
gwsupport_CheckDoubleLesserOrEqual
gwsupport_CheckDoubleLesserThan
gwsupport_CheckDoubleMatrixGreaterOrEqual
gwsupport_CheckDoubleMatrixGreaterThan
gwsupport_CheckDoubleMatrixHasIntegerValue
gwsupport_CheckDoubleMatrixHasNofractpart
gwsupport_CheckDoubleMatrixInRange
gwsupport_CheckDoubleMatrixLesserOrEqual
gwsupport_CheckDoubleMatrixLesserThan
gwsupport_CheckSize
gwsupport_Double2IntegerArgument
gwsupport_GetMatrixOfDoublesGreaterOrEqualThanZero
gwsupport_GetMatrixOfDoublesGreaterThanZero
gwsupport_GetOneIntegerArgument
gwsupport_GetRealMatrixOfDoubles
gwsupport_GetScalarDouble
gwsupport_GetScalarString
gwsupport_PrintWarning
*/

// 
// gwsupport_CheckSize --
//   Checks the size of a matrix.
//
// Arguments
//   fname (input) : the name of the Scilab function generating this error
//   ivar (input) : the index of the input variable
//   expected_nrows (input) : the expected number of rows
//   expected_ncols (input) : the expected number of columns
//   actual_nrows (input) : the actual number of rows
//   actual_ncols (input) : the actual number of columns
//
// Description
//   Reports an error if the actual number of rows 
//   is not equal to the expected number of rows.
//   Reports an error if the actual number of columns 
//   is not equal to the expected number of columns.
//   Returns GWSUPPORT_ERROR if an error is detected, 
//   returns GWSUPPORT_OK if no error occurs.
//
int gwsupport_CheckSize ( char * fname , int ivar , int expected_nrows , int expected_ncols , int actual_nrows , int actual_ncols );

// 
// gwsupport_CheckVectorSize --
//   Checks that a matrix is a 1-by-n or n-by-1 matrix with required n.
//
// Arguments
//   fname (input) : the name of the Scilab function generating this error
//   ivar (input) : the index of the input variable
//   expected_n (input) : the expected number of entries
//   actual_nrows (input) : the actual number of rows
//   actual_ncols (input) : the actual number of columns
//
// Description
//   If the argument is not a 1-by-n or n-by-1 matrix, reports an error. 
//   Returns GWSUPPORT_ERROR if an error is detected, 
//   returns GWSUPPORT_OK if no error occurs.
//
int gwsupport_CheckVectorSize ( char * fname , int ivar , int expected_n, int actual_nrows , int actual_ncols );

// gwsupport_AllocateLhsMatrixOfDoubles --
//   Creates a double matrix variable on the Left Hand Side at location ovar.
//
// Arguments
//   fname (input) : the name of the Scilab function generating this error
//   ovar (input) : the index of the output variable
//   _piRows (input) : the number of rows
//   _piCols (input) : the number of columns
//   _pdblReal (output) : the matrix to create
//
// Description
// This function allocates the memory for _pdblReal. 
// After calling gwsupport_AllocateLhsMatrixOfDoubles, the caller 
// should fill the array with values. 
//
int gwsupport_AllocateLhsMatrixOfDoubles ( int ivar , int nRows , int nCols , double ** matrix );

// Get a pointer to the doubles of a real matrix of doubles from input argument #ivar.
//
// Arguments
// fname (input): the name of the function
// ivar (input): the index of the input argument
// _piAddress(input): a data structure for the API
// _pdblReal (output): the data
// _piRows (output): the number of rows
// _piCols (output): the number of columns
//
// Description
// If we cannot get the matrix, produce an error and returns GWSUPPORT_ERROR.
// Checks that the matrix is not complex.
// If we can get the matrix, returns GWSUPPORT_OK.
// TODO : add _pvApiCtx in the calling sequence
//
int gwsupport_GetRealMatrixOfDoubles( char * fname, int ivar , double** lrA, int * mA , int * nA );

// Get scalar double
//
// Arguments
// fname (input): the name of the function
// ivar (input): the index of the input argument
// lrA (output): the value
//
// Description
// If we cannot get the value, produce an error and returns GWSUPPORT_ERROR.
// Check that the matrix is 1-by-1.
// If we can get the value, returns GWSUPPORT_OK.
//
int gwsupport_GetScalarDouble( char * fname, int ivar , double* lrA );

// 
// gwsupport_GetOneIntegerArgument --
//   Gets one integer number from the argument #ivar in the function fname.
//   Returns GWSUPPORT_ERROR if an error is detected, returns GWSUPPORT_OK if no error occurs.
// Arguments
//   fname (input) : the name of the Scilab function generating this error
//   ivar (input) : the index of the input variable
//   value (output) : the value to get
//
int gwsupport_GetOneIntegerArgument ( char * fname , int ivar , int * value );

// 
// gwsupport_Double2IntegerArgument --
//   Compute if the given double is storable as an integer.
//   Returns GWSUPPORT_ERROR if an error is detected, returns GWSUPPORT_OK if no error occurs.
// Arguments
//   fname (input) : the name of the Scilab function generating this error
//   ivar (input) : the index of the input variable
//   dvalue (input) : the double value
//   ivalue (output) : the value to get
//
int gwsupport_Double2IntegerArgument ( char * fname , int ivar , double dvalue , int * ivalue );

// 
// gwsupport_CheckDoubleHasIntegerValue --
//   Check if the given double has an integer value. 
//   This means that dvalue is in the range [INT_MIN,INT_MAX], 
//   where INT_MIN= -2147483648, INT_MAX=2147483647.
//   Also check that the fractional part of dvalue is zero.
//   Returns GWSUPPORT_ERROR if an error is detected, returns GWSUPPORT_OK if no error occurs.
// Arguments
//   fname (input) : the name of the Scilab function generating this error
//   ivar (input) : the index of the input variable
//   dvalue (input) : the double value
//
int gwsupport_CheckDoubleHasIntegerValue ( char * fname , int ivar , double dvalue );

// 
// gwsupport_CheckDoubleMatrixHasIntegerValue --
//   Check if the given matrix of doubles has an integer value.
//   This means that dvalue is in the range [INT_MIN,INT_MAX], 
//   where INT_MIN= -2147483648, INT_MAX=2147483647.
//   Also check that the fractional part of dvalue is zero.
//   Returns GWSUPPORT_ERROR if an error is detected, returns GWSUPPORT_OK if no error occurs.
// Arguments
//   fname (input) : the name of the Scilab function generating this error
//   ivar (input) : the index of the input variable
//   dvalue (input) : the double value
//
int gwsupport_CheckDoubleMatrixHasIntegerValue ( char * fname , int ivar , double * p, int nrows , int ncols );

// 
// gwsupport_CheckDoubleHasNofractpart --
//   Check if the given double has no fractional part, i.e. has an integer value. 
//   Returns GWSUPPORT_ERROR if an error is detected, returns GWSUPPORT_OK if no error occurs.
// Arguments
//   fname (input) : the name of the Scilab function generating this error
//   ivar (input) : the index of the input variable
//   dvalue (input) : the double value
//
int gwsupport_CheckDoubleHasNofractpart ( char * fname , int ivar , double dvalue );

// 
// gwsupport_CheckDoubleMatrixHasNofractpart --
//   Check if the given matrix of doubles has no fractional part, i.e. has an integer value. 
//   Returns GWSUPPORT_ERROR if an error is detected, returns GWSUPPORT_OK if no error occurs.
// Arguments
//   fname (input) : the name of the Scilab function generating this error
//   ivar (input) : the index of the input variable
//   dvalue (input) : the double value
//
int gwsupport_CheckDoubleMatrixHasNofractpart ( char * fname , int ivar , double * p, int nrows , int ncols );

// Check that a double is in [mindouble,maxdouble] 
// i.e. mindouble <= mydouble <= maxdouble.
//
// Arguments
// fname (input): the name of the function
// ivar (input): the index of the input argument
// lrA (input): the data
// mA (input): the number of rows
// nA (input): the number of columns
// mindouble (input): the minimum value of the double.
// maxdouble (input): the maximum value of the double.
//
// Description
// If the matrix is >= mindouble, returns GWSUPPORT_OK.
// If not, produce an error and returns GWSUPPORT_ERROR.
//
int gwsupport_CheckDoubleInRange (char *fname, int ivar, double mydouble, double mindouble, double maxdouble);

// Check that a matrix of doubles is in [mindouble,maxdouble] 
// i.e. mindouble <= mydouble <= maxdouble.
//
// Arguments
// fname (input): the name of the function
// ivar (input): the index of the input argument
// lrA (input): the data
// mA (input): the number of rows
// nA (input): the number of columns
// mindouble (input): the minimum value of the double.
// maxdouble (input): the maximum value of the double.
//
// Description
// If the matrix is not in the range [mindouble,maxdouble], produce an error and returns GWSUPPORT_ERROR.
// If the matrix is OK, returns GWSUPPORT_OK.
//
int gwsupport_CheckDoubleMatrixInRange (char *fname, int ivar, double* lrA, int mA , int nA, double mindouble, double maxdouble);

// Check that a double is >= mindouble
//
// Arguments
// fname (input): the name of the function
// ivar (input): the index of the input argument
// value (input): the number of columns
// mindouble (input): the minimum value of the double.
//
// Description
// If the double >= mindouble, returns GWSUPPORT_OK.
// If not, produce an error and returns GWSUPPORT_ERROR.
//
int gwsupport_CheckDoubleGreaterOrEqual (char *fname, int ivar, double value, double mindouble);

// Check that a matrix of doubles is >= mindouble
//
// Arguments
// fname (input): the name of the function
// ivar (input): the index of the input argument
// lrA (input): the data
// mA (input): the number of rows
// nA (input): the number of columns
// mindouble (input): the minimum value of the double.
//
// Description
// If the matrix is >= mindouble, returns GWSUPPORT_OK.
// If not, produce an error and returns GWSUPPORT_ERROR.
//
int gwsupport_CheckDoubleMatrixGreaterOrEqual (char *fname, int ivar, double* lrA, int mA , int nA, 
	double mindouble);

// Check value > mindouble.
//
// Arguments
// fname (input): the name of the function
// ivar (input): the index of the input argument
// value (input): the number of columns
// mindouble (input): the minimum value of the double.
//
// Description
// If the double is > mindouble, returns GWSUPPORT_OK.
// If not, produce an error and returns GWSUPPORT_ERROR.
//
int gwsupport_CheckDoubleGreaterThan (char *fname, int ivar, double value, double mindouble);

// Check matrix value > mindouble.
//
// Arguments
// fname (input): the name of the function
// ivar (input): the index of the input argument
// lrA (input): the data
// mA (input): the number of rows
// nA (input): the number of columns
// mindouble (input): the minimum value of the double.
//
// Description
// If the matrix is > mindouble, returns GWSUPPORT_OK.
// If not, produce an error and returns GWSUPPORT_ERROR.
//
int gwsupport_CheckDoubleMatrixGreaterThan (char *fname, int ivar, double* lrA, int mA , int nA, 
	double mindouble);

// Check value <= maxdouble
//
// Arguments
// fname (input): the name of the function
// ivar (input): the index of the input argument
// value (input): the number of columns
// maxdouble (input): the maximum value of the double.
//
// Description
// If the value <= maxdouble, returns GWSUPPORT_OK.
// If not, produce an error and returns GWSUPPORT_ERROR.
//
int gwsupport_CheckDoubleLesserOrEqual (char *fname, int ivar, double value, double maxdouble);

// Check matrix value <= maxdouble
//
// Arguments
// fname (input): the name of the function
// ivar (input): the index of the input argument
// lrA (input): the data
// mA (input): the number of rows
// nA (input): the number of columns
// maxdouble (input): the maximum value of the double.
//
// Description
// If the matrix is <= maxdouble, returns GWSUPPORT_OK.
// If not, produce an error and returns GWSUPPORT_ERROR.
//
int gwsupport_CheckDoubleMatrixLesserOrEqual (char *fname, int ivar, double* lrA, int mA , int nA, 
	double maxdouble);

// Check value < maxdouble
//
// Arguments
// fname (input): the name of the function
// ivar (input): the index of the input argument
// value (input): the number of columns
// maxdouble (input): the maximum value of the double.
//
// Description
// If the value is < maxdouble, returns GWSUPPORT_OK.
// If not, produce an error and returns GWSUPPORT_ERROR.
//
int gwsupport_CheckDoubleLesserThan (char *fname, int ivar, double value, double maxdouble);

// Check matrix value < maxdouble
//
// Arguments
// fname (input): the name of the function
// ivar (input): the index of the input argument
// lrA (input): the data
// mA (input): the number of rows
// nA (input): the number of columns
// maxdouble (input): the maximum value of the double.
//
// Description
// If the matrix is < maxdouble, returns GWSUPPORT_OK.
// If not, produce an error and returns GWSUPPORT_ERROR.
//
int gwsupport_CheckDoubleMatrixLesserThan (char *fname, int ivar, double* lrA, int mA , int nA, 
	double maxdouble);

// Get a matrix of doubles >=0
//
// Arguments
// fname (input): the name of the function
// ivar (input): the index of the input argument
// lrP (output): the data
// rowsP (output): the number of rows
// colsP (output): the number of columns
//
// Description
// If we cannot get the matrix, produce an error and returns GWSUPPORT_ERROR.
// Checks that the matrix has entries greater or equal to 0.
//
int gwsupport_GetMatrixOfDoublesGreaterOrEqualThanZero( char * fname, int ivar , double** lrA, int * rowsA , int * colsA );

// Get a matrix of doubles >0
//
// Arguments
// fname (input): the name of the function
// ivar (input): the index of the input argument
// lrP (output): the data
// rowsP (output): the number of rows
// colsP (output): the number of columns
//
// Description
// If we cannot get the matrix, produce an error and returns GWSUPPORT_ERROR.
// Checks that the matrix has entries greater or equal to 0.
//
int gwsupport_GetMatrixOfDoublesGreaterThanZero( char * fname, int ivar , double** lrA, int * rowsA , int * colsA	);

// Prints a warning.
//
// Arguments
// message (input): the warning
//
// Description
// If the warning mode is ON, then prints the message.
//
void gwsupport_PrintWarning ( char * message );

// Get scalar string
//
// Arguments
// fname (input): the name of the function
// ivar (input): the index of the input argument
// mystring (output): the string
//
// Description
// If we cannot get the matrix, produce an error and returns GWSUPPORT_ERROR.
// Otherwise, returns GWSUPPORT_OK.
//
int gwsupport_GetScalarString( char * fname, int ivar , char** mystring );

__END_DECLS


/* ==================================================================== */

#endif /* __SCI_GWSUPPORT_H__ */

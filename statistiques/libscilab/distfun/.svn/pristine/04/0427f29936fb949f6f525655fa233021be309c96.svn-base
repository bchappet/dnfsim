
// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2008-2009 - INRIA - Michael Baudin
// Copyright (C) 2009 - Digiteo - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

//
// gw_distfuncdf_support.h
//   Header for the C gateway support functions for DISTFUN
//
#ifndef __SCI_DISTFUN_GWSUPPORT_H__
#define __SCI_DISTFUN_GWSUPPORT_H__


static int DISTFUNCDFGW_OK = 1;
static int DISTFUNCDFGW_ERROR = 0;

// This comes from Scilab
extern double dlamch_(char *, long int);

// Get the matrix of doubles of P.
//
// Arguments
// fname (input): the name of the function
// ivar (input): the index of the input argument
// rowsExpected (input): the number of expected rows
// colsExpected (input): the number of expected columns
// lrP (output): the data
// rowsP (output): the number of rows
// colsP (output): the number of columns
//
// Description
// If we cannot get the matrix, produces an error and returns DISTFUNCDFGW_ERROR.
// Checks that the matrix has the expected size.
// Checks that the matrix has entries in [0,1].
// If we can get the matrix, returns DISTFUNCDFGW_OK.
//
int distfun_GetMatrixP( char * fname, int ivar , int rowsExpected, int colsExpected , double** lrP, int * rowsP , int * colsP	);

// Produce a generic error message for CDF functions.
//
// Arguments
// fname : the name of the function
// index : the index of array which fails
//
// Description
// It may happen that CDFLIB fails to evaluate the CDF, the PDF, 
// or the inverse CDF for one particular index of the C for-loop, 
// which corresponds to the entry X(index+1).
// In this case, we print a message.
//
void distfun_defaultCDFError( char * fname, int index );
// For PDF
void distfun_defaultPDFError( char * fname, int index );
// For Inverse CDF
void distfun_defaultInvCDFError( char * fname, int index );

// Get the two matrices P (argument #ivar) and Q (argument #(ivar+1)) 
//
// Arguments
// fname (input): the name of the function
// ivar (input): the index of the input argument
// rowsExpected (input): the number of expected rows
// colsExpected (input): the number of expected columns
// lrP (output): the data for P
// rowsP (output): the number of rows in P
// colsP (output): the number of columns in P
// lrQ (output): the data in Q
// rowsQ (output): the number of rows in Q
// colsQ (output): the number of columns in Q
//
// Description
// If we cannot get both matrices, produces an error and returns DISTFUNCDFGW_ERROR.
// Checks that the matrices P and Q have entries in [0,1].
// If rowsExpected > 0, checks that the matrices have the expected size.
// Checks that P + Q ~ 1.
// If we can get either P or Q, returns 1.
//
int distfun_GetMatricesPQ( char * fname, int ivar, int rowsExpected, int colsExpected ,
	double** lrP, int * rowsP , int * colsP, 
	double** lrQ, int * rowsQ , int * colsQ );

// Check that X+Y=1
//
// Arguments
// fname (input): the name of the function
// ivar (input): the index of the input argument
// lrX (output): the data for X
// lrY (output): the data for Y
// rowsX (output): the number of rows in X (and in Y)
// colsX (output): the number of columns in X (and in Y)
//
// Description
// If the matrix is not in the range, produces an error and returns DISTFUNCDFGW_ERROR.
// If the matrix is OK, returns 1.
//
int distfun_CheckXYEqualOne (char *fname, double* lrX, double* lrY, int rowsX , int colsX);

// Get a real matrix of doubles and a given size
//
// Arguments
// fname (input): the name of the function
// ivar (input): the index of the input argument
// rowsExpected (input): the number of expected rows
// colsExpected (input): the number of expected columns
// lrP (output): the data
// rowsP (output): the number of rows
// colsP (output): the number of columns
//
// Description
// If we cannot get the matrix, produces an error and returns DISTFUNCDFGW_ERROR.
// If rowsExpected > 0, checks that the matrix has the expected size.
// If we can get the matrix, returns DISTFUNCDFGW_OK.
//
int distfun_GetSizedRealMatrixOfDoubles( char * fname, int ivar , int rowsExpected, int colsExpected ,
	double** _pdblReal, int * rowsA , int * colsA	);

// Get a matrix of doubles >=0 and a given size
//
// Arguments
// fname (input): the name of the function
// ivar (input): the index of the input argument
// rowsExpected (input): the number of expected rows
// colsExpected (input): the number of expected columns
// lrP (output): the data
// rowsP (output): the number of rows
// colsP (output): the number of columns
//
// Description
// If we cannot get the matrix, produces an error and returns DISTFUNCDFGW_ERROR.
// Checks that the matrix has entries greater or equal to 0.
// If rowsExpected > 0, checks that the matrix has the expected size.
// If we can get the matrix, returns DISTFUNCDFGW_OK.
//
int distfun_GetSizedRealMatrixOfDoublesGreaterOrEqualThanZero( char * fname, int ivar , int rowsExpected, int colsExpected ,
	double** lrA, int * rowsA , int * colsA	);

// Get a matrix of doubles >0 and a given size
//
// Arguments
// fname (input): the name of the function
// ivar (input): the index of the input argument
// rowsExpected (input): the number of expected rows
// colsExpected (input): the number of expected columns
// lrP (output): the data
// rowsP (output): the number of rows
// colsP (output): the number of columns
//
// Description
// If we cannot get the matrix, produces an error and returns DISTFUNCDFGW_ERROR.
// Checks that the matrix has entries greater or equal to 0.
// If rowsExpected > 0, checks that the matrix has the expected size.
// If we can get the matrix, returns DISTFUNCDFGW_OK.
//
int distfun_GetSizedRealMatrixOfDoublesGreaterThanZero( char * fname, int ivar , int rowsExpected, int colsExpected ,
	double** lrA, int * rowsA , int * colsA	);

// Get the lower tail as a boolean.
//
// Arguments
// fname (input): the name of the function
// ivar (input): the index of the input argument
// ilowertail (output): the data
//
// Description
// If we cannot get the matrix, produces an error and returns DISTFUNCDFGW_ERROR.
// Checks that the matrix has size 1-by-1 and is either 1 or 0.
// If we can get the matrix, returns DISTFUNCDFGW_OK.
//
// If lowertail is TRUE (the default), then ilowertail=1.
// If lowertail is FALSE, then ilowertail=0.
// If lowertail is an empty matrix, set ilowertail=1 (for TRUE).
//
int distfun_GetIlowertail( char * fname, int ivar , int* ilowertail);

#endif /* __SCI_DISTFUN_GWSUPPORT_H__ */

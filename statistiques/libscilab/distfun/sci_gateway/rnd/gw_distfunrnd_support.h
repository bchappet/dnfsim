
// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2008-2009 - INRIA - Michael Baudin
// Copyright (C) 2009 - Digiteo - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

//
// gw_distfunrnd_support.h
//   Header for the C gateway support functions for DISTFUN/GRAND
//
#ifndef __SCI_DISTFUN_GRAND_GWSUPPORT_H__
#define __SCI_DISTFUN_GRAND_GWSUPPORT_H__


// 
// distfun_GetMNV_A --
//   Returns the nrows and ncols scalars for distfun_*rnd(a) functions.
//   Manage the three cases :
//   R=distfun_*rnd(a)
//   R=distfun_*rnd(a,v)
//   R=distfun_*rnd(a,m,n)
// Arguments
//   fname (input) : the name of the Scilab function generating this error
//   nbInputArgs (input) : the actual number of input arguments
//   rRows (output) : the required number of rows
//   rCols (output) : the required number of columns
// Description
//   Returns GWSUPPORT_ERROR if an error is detected, returns GWSUPPORT_OK if OK.
int distfun_GetMNV_A ( char * fname, int nbInputArgs, int * rRows, int * rCols );

// 
// distfun_GetMNV_AB --
//   Returns the nrows and ncols scalars for distfun_*rnd(a,b) functions.
//   Manage the three cases :
//   R=distfun_*rnd(a,b)
//   R=distfun_*rnd(a,b,v)
//   R=distfun_*rnd(a,b,m,n)
// Arguments
//   fname (input) : the name of the Scilab function generating this error
//   nbInputArgs (input) : the actual number of input arguments
//   rRows (output) : the required number of rows
//   rCols (output) : the required number of columns
// Description
//   Returns GWSUPPORT_ERROR if an error is detected, returns GWSUPPORT_OK if OK.
int distfun_GetMNV_AB ( char * fname, int nbInputArgs, int * rRows, int * rCols );

// 
// distfun_GetMNV_ABC --
//   Returns the nrows and ncols scalars for distfun_*rnd(a,b,c) functions.
//   Manage the three cases :
//   R=distfun_*rnd(a,b,c)
//   R=distfun_*rnd(a,b,c,v)
//   R=distfun_*rnd(a,b,c,m,n)
// Arguments
//   fname (input) : the name of the Scilab function generating this error
//   nbInputArgs (input) : the actual number of input arguments
//   rRows (output) : the required number of rows
//   rCols (output) : the required number of columns
// Description
//   Returns GWSUPPORT_ERROR if an error is detected, returns GWSUPPORT_OK if OK.
int distfun_GetMNV_ABC ( char * fname, int nbInputArgs, int * rRows, int * rCols );

// 
// distfun_GetMN --
//   Returns the nrows and ncols scalars for distfun_*rnd(...,nrows,ncols) functions.
// Arguments
//   fname (input) : the name of the Scilab function generating this error
//   nrows (output) : the required number of rows
//   ncols (output) : the required number of columns
// Description
//   Returns GWSUPPORT_ERROR if an error is detected, returns GWSUPPORT_OK if OK.
//   Reports an error if the actual number of rows 
//   is not equal to 1.
//   Reports an error if the actual number of columns 
//   is not equal to 1.
int distfun_GetMN ( char * fname, int iargm, int iargn, int * nrows, int * ncols );

// 
// distfun_GetV --
//   Returns the vRows=v[1] and vCols=v[2] scalars for distfun_rnd(...,v) functions.
// Arguments
//   fname (input) : the name of the Scilab function generating this error
//   vRows (output) : the required number of rows
//   vCols (output) : the required number of columns
// Description
//   Returns GWSUPPORT_ERROR if an error is detected, returns GWSUPPORT_OK if OK.
//   Reports an error if v is the a row or column vector with two entries.
//   Reports an error if one the entries is not an integer or if it is <1.
int distfun_GetV ( char * fname, int iargv, int * vRows, int * vCols );

// Compute the result as the matrix of random numbers for 1 parameter A
//
// Arguments
// fname (input): the name of the function
// ivar (input): the index of the input argument
// ma (input): the number of rows in a
// na (input): the number of columns in a
// pa (input): the data in a
// xRows (input): the number of rows in x
// xCols (input): the number of columns in x
// randgen (input): the random number generator, with calling sequence
//   status = randgen(a,&x)
//
// Description
// If the computation is not possible, produces an error and returns GWSUPPORT_ERROR.
// If the computation is OK, returns GWSUPPORT_OK.
//
int distfun_computeRandgenA (char *fname, 
	int ma, int na, double * pa, 
	int rRows, int rCols, 
	int (*randgen)(double a, double *x) );

// Compute the result as the matrix of random numbers for 2 parameters A and B
//
// Arguments
// fname (input): the name of the function
// ivar (input): the index of the input argument
// ma (input): the number of rows in a
// na (input): the number of columns in a
// pa (input): the data in a
// mb (input): the number of rows in b
// nb (input): the number of columns in b
// pb (input): the data in b
// xRows (input): the number of rows in x
// xCols (input): the number of columns in x
// px (input): the data in x
// randgen (input): the random number generator, with calling sequence
//   status = randgen(a,b,&x)
//
// Description
// If the computation is not possible, produces an error and returns GWSUPPORT_ERROR.
// If the computation is OK, returns GWSUPPORT_OK.
//
int distfun_computeRandgenAB (char *fname, 
	int ma, int na, double * pa, 
	int mb, int nb, double * pb, 
	int rRows, int rCols, 
	int (*randgen)(double a , double b, double *x) );

// Compute the result as the matrix of random numbers for 3 parameters A and B and C
//
// Arguments
// fname (input): the name of the function
// ivar (input): the index of the input argument
// ma (input): the number of rows in a
// na (input): the number of columns in a
// pa (input): the data in a
// mb (input): the number of rows in b
// nb (input): the number of columns in b
// pb (input): the data in b
// mc (input): the number of rows in c
// nc (input): the number of columns in c
// pc (input): the data in c
// xRows (input): the number of rows in x
// xCols (input): the number of columns in x
// px (input): the data in x
// randgen (input): the random number generator, with calling sequence
//   status = randgen(a,b,c,&x)
//
// Description
// If the computation is not possible, produces an error and returns GWSUPPORT_ERROR.
// If the computation is OK, returns GWSUPPORT_OK.
//
int distfun_computeRandgenABC (char *fname, 
	int ma, int na, double * pa, 
	int mb, int nb, double * pb, 
	int mc, int nc, double * pc, 
	int rRows, int rCols, 
	int (*randgen)(double a , double b, double c, double *x) );


/* ==================================================================== */

#endif /* __SCI_DISTFUN_GRAND_GWSUPPORT_H__ */

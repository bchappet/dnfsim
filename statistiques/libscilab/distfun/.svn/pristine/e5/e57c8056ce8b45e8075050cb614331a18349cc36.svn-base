
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

#include "gw_distfunrnd_support.h" 
#include "gwsupport.h" 
#include "cdflib.h"

//   flag=0 if the size of the parameters of the distribution are wrong.
static int GRANDGW_SIZEERROR = 0;
//   flag=3 if the size of the parameters of the distribution are OK.
static int GRANDGW_SIZEOK = 3;


int distfun_CheckExpansionCaseA ( char * fname , int m, int n, int ma , int na );
int distfun_CheckExpansionCaseAB ( char * fname , int m, int n, int ma , int na , int mb , int nb);
int distfun_CheckExpansionCaseABC ( char * fname , int m, int n, int ma , int na , int mb , int nb , int mc , int nc );
int distfun_CheckParameterSize ( char * fname , int m, int n, int ma , int na );
int distfun_ComputeOffset(int ma, int na);
int distfun_Max(int ma, int mb);
int distfun_CheckSizesMatch ( char * fname, int iarga, int ma , int na, int mb, int nb );
int distfun_GetMNV_common ( char * fname, int nbparameters, int nbInputArgs, int * rRows, int * rCols );
int distfun_checkMNMaxValue ( char * fname, int iargm, int iargn, int m, int n);


// distfun_checkMNValue --
// Check the required size of x.
//
// Arguments
// fname: name of the function
// iargm : the index of the argument m in the calling sequence
// iargn : the index of the argument n in the calling sequence
// m : the number of required rows
// n : the number of required columns
//
// Description
// m and n are the size of the random number matrix x.
// The two possible calling sequences are :
// *rnd(...,[m n]) (case 1)
// *rnd(...,m,n)   (case 2)
//
// In the case 1, iargm=iargn.
// In the case 2, iargm+1=iargn.
//
// We check that m and n are positive integers. 
// it may happen that the doubles are larger than the largest 32 bits int (e.g. 1.e10) 
// so that the int is negative.
int distfun_checkMNValue ( char * fname, int iargm, int iargn, int m, int n)
{
	// Below is the maximum value of m and n: 2**31 - 1
	int maxsize=2147483647; 

	if ( (m<0) | (m>maxsize) )
	{
		Scierror(999,_("%s: Wrong value for input argument #%d: m must be <= %d.\n"),
			fname, iargm , maxsize );
		return GWSUPPORT_ERROR;
	} 
	if ( (n<0) | (n>maxsize) )
	{
		Scierror(999,_("%s: Wrong value for input argument #%d: n must be <= %d.\n"),
			fname, iargn , maxsize );
		return GWSUPPORT_ERROR;
	} 
	return GWSUPPORT_OK;
}

int distfun_GetMNV_A ( char * fname, int nbInputArgs, int * rRows, int * rCols )
{
	int readFlag;
	readFlag = distfun_GetMNV_common ( fname, 1, nbInputArgs, rRows, rCols );
	return readFlag;
}

int distfun_GetMNV_AB ( char * fname, int nbInputArgs, int * rRows, int * rCols )
{
	int readFlag;
	readFlag = distfun_GetMNV_common ( fname, 2, nbInputArgs, rRows, rCols );
	return readFlag;
}

int distfun_GetMNV_ABC ( char * fname, int nbInputArgs, int * rRows, int * rCols )
{
	int readFlag;
	readFlag = distfun_GetMNV_common ( fname, 3, nbInputArgs, rRows, rCols );
	return readFlag;
}

// 
// distfun_GetMNV_common --
//   Returns the size required by the user. 
// 
// distfun_GetMNV_ABC --
//   Returns the nrows and ncols scalars for distfun_*rnd(...) functions.
//   Manage the three cases :
//   R=distfun_*rnd(...)
//   R=distfun_*rnd(...,v)
//   R=distfun_*rnd(...,m,n)
// Arguments
//   fname (input) : the name of the Scilab function generating this error
//   nbparameters (input) : the number of parameters in the distribution, 1, 2 or 3.
//   nbInputArgs (input) : the actual number of input arguments
//   rRows (output) : the required number of rows
//   rCols (output) : the required number of columns
// Description
// This function is called by 
// distfun_GetMNV_A
// distfun_GetMNV_AB
// distfun_GetMNV_ABC
//
int distfun_GetMNV_common ( char * fname, int nbparameters, int nbInputArgs, int * rRows, int * rCols )
{
	int readFlag;
	if ( nbInputArgs == nbparameters )
	{
		*rRows=0;
		*rCols=0;
	}
	else if ( nbInputArgs == nbparameters+1 )
	{
		// Get Arg #nbparameters+1 = v. Get rRows=v[1], rCols=v[2]
		readFlag = distfun_GetV ( fname, nbparameters+1, rRows, rCols );
		if ( readFlag == GWSUPPORT_ERROR)
		{ 
			return GWSUPPORT_ERROR;
		}
	}
	else
	{
		// Get Arg #nbparameters+1 = M, Arg #nbparameters+2 = N
		readFlag = distfun_GetMN(fname, nbparameters+1, nbparameters+2, rRows, rCols);
		if ( readFlag == GWSUPPORT_ERROR)
		{ 
			return GWSUPPORT_ERROR;
		}
	}
	return GWSUPPORT_OK;
}

int distfun_GetMN ( char * fname, int iargm, int iargn, int * nrows, int * ncols )
{
	int readFlag;

	// Get Arg #iargm = M
	readFlag = gwsupport_GetOneIntegerArgument ( fname , iargm, nrows );
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return GWSUPPORT_ERROR;
	}
	// Get Arg #iargn = N
	readFlag = gwsupport_GetOneIntegerArgument ( fname , iargn, ncols );
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return GWSUPPORT_ERROR;
	}
	// Check the value of m and n
	readFlag = distfun_checkMNValue(fname, iargm, iargn, *nrows, *ncols);
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return GWSUPPORT_ERROR;
	}
	return GWSUPPORT_OK;
}

int distfun_GetV ( char * fname, int iargv, int * vRows, int * vCols )
{
	int readFlag;
	int mv, nv;
	double * pv = NULL;
	// Arg #3 : v
	readFlag = gwsupport_GetRealMatrixOfDoubles( fname, iargv, &pv, &mv, &nv);
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return GWSUPPORT_ERROR;
	}
	// We want only 2 entries
	readFlag = gwsupport_CheckVectorSize ( fname, iargv, 2, mv, nv);
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return GWSUPPORT_ERROR;
	}
	// We want integers in v
	readFlag = gwsupport_CheckDoubleMatrixHasNofractpart ( fname, iargv, pv, mv, nv);
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return GWSUPPORT_ERROR;
	}
	// v[:]>=1
	readFlag = gwsupport_CheckDoubleMatrixGreaterOrEqual (fname, iargv, pv, mv, nv, 1);
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return GWSUPPORT_ERROR;
	}
	// Now get the required size
	*vRows=(int)pv[0];
	*vCols=(int)pv[1];
	// Check the value of m and n
	readFlag = distfun_checkMNValue(fname, iargv, iargv, *vRows, *vCols);
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return GWSUPPORT_ERROR;
	}
	return GWSUPPORT_OK;
}

// 
// distfun_CheckParameterSize --
//   Returns flag=GRANDGW_SIZEOK if ma=1 and na=1, 
//   returns flag=GRANDGW_SIZEOK if m=1 and n=1,
//   otherwise, 
//   returns flag=GRANDGW_SIZEOK if ma=m and na=n, 
//   returns flag=GRANDGW_SIZEERROR otherwise.
// Arguments
//   fname (input) : the name of the Scilab function generating this error
//   m (input) : the required number of rows
//   n (input) : the required number of columns
//   ma (input) : the number of rows in a
//   na (input) : the number of columns in a
// Description
// Use this function to expand parameters.
//
int distfun_CheckParameterSize ( char * fname , int m, int n, int ma , int na )
{
	int flag;
	if ( ma==0 && na==0 ) 
	{ 
		flag = GRANDGW_SIZEOK;
	}
	else if ( m==0 && n==0 ) 
	{ 
		flag = GRANDGW_SIZEOK;
	}
	else if ( ma==1 && na==1 ) 
	{ 
		flag = GRANDGW_SIZEOK;
	}
	else if ( m==1 && n==1 ) 
	{ 
		flag = GRANDGW_SIZEOK;
	}
	else 
	{
		if ( ma==m && na==n ) 
		{
			flag = GRANDGW_SIZEOK;
		}
		else
		{
			flag = GRANDGW_SIZEERROR;
		}
	}
	return flag;
}

// distfun_ComputeOffset --
//   Compute the offset to go through the array.
//   Returns offset=0 if ma*na=1.
//   Returns offset=1 otherwise.
// Description
// This is a trick, so that there is only one loop 
// during the generation of the random numbers. 
// If the parameter a is a scalar, the offset is 0 
// so that the update of the index is 
//
//   a_index=a_index+0
//
// In this case, the same value a[0] is used all 
// along the loop.
//
// If the parameter a is a matrix, the offset is 1 
// so that the update of the index is 
//
//   a_index=a_index+1
//
// In this case, the value a[i] is used during the loop.
//
// This generic method is used for all parameters
// a, b, c, d, so that there is no particular 
// case in the algorithm. 
int distfun_ComputeOffset(int ma, int na)
{
	int offset;
	if ( ma*na==1 )
	{ 
		offset=0;
	} 
	else
	{
		offset=1;
	}
	return offset;
}

// distfun_Max --
//   Compute the maximum of the inputs
int distfun_Max(int ma, int mb)
{
	if (ma>mb)
	{ 
		return ma;
	} 
	else
	{
		return mb;
	}
}

// 
// distfun_CheckSizesMatch --
//   Returns expcase=GRANDGW_SIZEOK if size(a) matches size(b).
//   Returns expcase=GRANDGW_SIZEERROR (error) otherwise.
// Arguments
//   fname (input) : the name of the Scilab function generating this error
//   iarga (input) : the index of a in the calling sequence
//   ma (input) : the number of rows in a
//   na (input) : the number of columns in a
//   mb (input) : the number of rows in b
//   nb (input) : the number of columns in b
// Description
// This function is a support for the distfun_CheckExpansionCase* functions.
//
int distfun_CheckSizesMatch ( char * fname, int iarga, int ma , int na, int mb, int nb )
{
	int expcase;
	int flag;

	// Check size(a) versus size(b)
	flag = distfun_CheckParameterSize ( fname, ma, na, mb, nb);
	if (flag==GRANDGW_SIZEERROR)
	{
		// a is wrong
		Scierror(999,_("%s: Wrong size for input argument #%d: %d-by-%d matrix expected.\n"),
			fname, iarga, mb, nb );
		expcase = GRANDGW_SIZEERROR;
	}
	else
	{
		expcase = GRANDGW_SIZEOK;
	}
	return expcase;
}

// 
// distfun_CheckExpansionCaseA --
//   Returns expcase=GRANDGW_SIZEOK if size(a) matches [m,n].
//   Returns expcase=GRANDGW_SIZEERROR (error) otherwise.
// Arguments
//   fname (input) : the name of the Scilab function generating this error
//   m (input) : the number of rows required by the user
//   n (input) : the number of columns required by the user
//   ma (input) : the number of rows in a
//   na (input) : the number of columns in a
// Description
// Use this function to expand distfun_rndXXX(a,m,n) functions 
// to the case where a is non-scalar.
//
int distfun_CheckExpansionCaseA ( char * fname , int m, int n, int ma , int na )
{
	int flag;

	// Check size of a versus [m,n]
	flag = distfun_CheckSizesMatch ( fname, 1, ma , na, m, n );
	if (flag==GRANDGW_SIZEERROR)
	{
		return GRANDGW_SIZEERROR;
	}
	return GRANDGW_SIZEOK;
}

// 
// distfun_CheckExpansionCaseAB --
//   Returns expcase=GRANDGW_SIZEOK if size(a) matches [m,n],
//       and expcase=GRANDGW_SIZEOK if size(b) matches [m,n],
//       and expcase=GRANDGW_SIZEOK if size(a) matches size(b).
//   Returns expcase=GRANDGW_SIZEERROR (error) otherwise.
// Arguments
//   fname (input) : the name of the Scilab function generating this error
//   ma (input) : the number of rows in a
//   na (input) : the number of columns in a
//   mb (input) : the number of rows in b
//   nb (input) : the number of columns in b
//   a_offset (output) : the required increment in a
//   b_offset (output) : the required increment in b
// Description
// Use this function to expand distfun_rndXXX(a,b,m,n) functions 
// to the case where a and b are non-scalars.
//
int distfun_CheckExpansionCaseAB ( char * fname , int m, int n, int ma , int na , int mb , int nb )
{
	int expcase;
	int flag;

	expcase = GRANDGW_SIZEOK;

	// Check size of a versus [m,n]
	flag = distfun_CheckSizesMatch ( fname, 1, ma , na, m, n );
	if (flag==GRANDGW_SIZEERROR)
	{
		return GRANDGW_SIZEERROR;
	}

	// Check size of b versus [m,n]
	flag = distfun_CheckSizesMatch ( fname, 2, mb , nb, m, n );
	if (flag==GRANDGW_SIZEERROR)
	{
		return GRANDGW_SIZEERROR;
	}

	// Check size of a versus size of b
	flag = distfun_CheckSizesMatch ( fname, 1, ma , na, mb, nb );
	if (flag==GRANDGW_SIZEERROR)
	{
		return GRANDGW_SIZEERROR;
	}

	return expcase;
}
// 
// distfun_CheckExpansionCaseABC --
//   Returns expcase=GRANDGW_SIZEOK if size(a) matches [m,n],
//       and expcase=GRANDGW_SIZEOK if size(b) matches [m,n],
//       and expcase=GRANDGW_SIZEOK if size(c) matches [m,n],
//       and expcase=GRANDGW_SIZEOK if size(a) matches size(b).
//       and expcase=GRANDGW_SIZEOK if size(a) matches size(c).
//       and expcase=GRANDGW_SIZEOK if size(b) matches size(c).
//   Returns expcase=GRANDGW_SIZEERROR (error) otherwise.
// Arguments
//   fname (input) : the name of the Scilab function generating this error
//   ma (input) : the number of rows in a
//   na (input) : the number of columns in a
//   mb (input) : the number of rows in b
//   nb (input) : the number of columns in b
//   mc (input) : the number of rows in c
//   nc (input) : the number of columns in c
// Description
// Use this function to expand distfun_rndXXX(a,b,c,m,n) functions 
// to the case where a and b are non-scalars.
//
int distfun_CheckExpansionCaseABC ( char * fname , int m, int n, int ma , int na , int mb , int nb , int mc , int nc )
{
	int expcase;
	int flag;

	expcase = GRANDGW_SIZEOK;

	// Check size of a versus [m,n]
	flag = distfun_CheckSizesMatch ( fname, 1, ma , na, m, n );
	if (flag==GRANDGW_SIZEERROR)
	{
		return GRANDGW_SIZEERROR;
	}

	// Check size of b versus [m,n]
	flag = distfun_CheckSizesMatch ( fname, 2, mb , nb, m, n );
	if (flag==GRANDGW_SIZEERROR)
	{
		return GRANDGW_SIZEERROR;
	}

	// Check size of c versus [m,n]
	flag = distfun_CheckSizesMatch ( fname, 3, mc , nc, m, n );
	if (flag==GRANDGW_SIZEERROR)
	{
		return GRANDGW_SIZEERROR;
	}

	// Check size of a versus size of b
	flag = distfun_CheckSizesMatch ( fname, 1, ma , na, mb, nb );
	if (flag==GRANDGW_SIZEERROR)
	{
		return GRANDGW_SIZEERROR;
	}

	// Check size of a versus size of c
	flag = distfun_CheckSizesMatch ( fname, 1, ma , na, mc, nc );
	if (flag==GRANDGW_SIZEERROR)
	{
		return GRANDGW_SIZEERROR;
	}

	// Check size of b versus size of c
	flag = distfun_CheckSizesMatch ( fname, 2, mb , nb, mc, nc );
	if (flag==GRANDGW_SIZEERROR)
	{
		return GRANDGW_SIZEERROR;
	}

	return expcase;
}

int distfun_computeRandgenA (char *fname, 
	int ma, int na, double * pa, 
	int m, int n, 
	int (*randgen)(double a, double *x) )
{
	int i;
	int expcase;
	double * px = NULL;
	int readFlag;
	int status;
	int a_offset;
	int a_index=0;
	int finalRows;
	int finalCols;

	// See the expansion case
	expcase = distfun_CheckExpansionCaseA ( fname, m, n, ma, na);
	if ( expcase == GRANDGW_SIZEERROR)
	{ 
		return GWSUPPORT_ERROR;
	}

	// Compute the offset in a, depending on the size of a
	a_offset=distfun_ComputeOffset(ma,na);

	// Create Lhs
	finalRows = distfun_Max(ma,m);
	finalCols = distfun_Max(na,n);

	readFlag = gwsupport_AllocateLhsMatrixOfDoubles ( 1, finalRows, finalCols, &px);
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return GWSUPPORT_ERROR;
	}
	// Generate x
	for ( i=0 ; i < finalRows*finalCols ; i++)
	{
		status = (*randgen)(pa[a_index],px+i);
		if (status!=CDFLIB_OK)
		{
			return GWSUPPORT_ERROR;
		}
		a_index=a_index+a_offset;
	}
	return GWSUPPORT_OK;
}
int distfun_computeRandgenAB (char *fname, 
	int ma, int na, double * pa, 
	int mb, int nb, double * pb, 
	int m, int n,  
	int (*randgen)(double a, double b, double *x) )
{
	int i;
	int expcase;
	double * px = NULL;
	int readFlag;
	int status;
	int a_offset;
	int b_offset;
	int a_index=0;
	int b_index=0;
	int finalRows;
	int finalCols;

	// See the expansion case
	expcase = distfun_CheckExpansionCaseAB ( fname, m, n, ma, na, mb, nb);
	if ( expcase == GRANDGW_SIZEERROR)
	{ 
		return GWSUPPORT_ERROR;
	}

	// Compute the offset in a, depending on the size of a
	a_offset=distfun_ComputeOffset(ma,na);

	// Compute the offset in b, depending on the size of b
	b_offset=distfun_ComputeOffset(mb,nb);

	// Create Lhs
	finalRows = distfun_Max(distfun_Max(ma,mb),m);
	finalCols = distfun_Max(distfun_Max(na,nb),n);

	readFlag = gwsupport_AllocateLhsMatrixOfDoubles ( 1, finalRows, finalCols, &px);
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return GWSUPPORT_ERROR;
	}
	// Generate x
	for ( i=0 ; i < finalRows*finalCols ; i++)
	{
		status = (*randgen)(pa[a_index],pb[b_index],px+i);
		if (status!=CDFLIB_OK)
		{
			return GWSUPPORT_ERROR;
		}
		a_index=a_index+a_offset;
		b_index=b_index+b_offset;
	}
	return GWSUPPORT_OK;
}

int distfun_computeRandgenABC (char *fname, 
	int ma, int na, double * pa, 
	int mb, int nb, double * pb, 
	int mc, int nc, double * pc, 
	int m, int n,  
	int (*randgen)(double a, double b, double c, double *x) )
{
	int i;
	int expcase;
	double * px = NULL;
	int readFlag;
	int status;
	int a_offset;
	int b_offset;
	int c_offset;
	int a_index=0;
	int b_index=0;
	int c_index=0;
	int finalRows;
	int finalCols;

	// See the expansion case
	expcase = distfun_CheckExpansionCaseABC ( fname, m, n, ma, na, mb, nb, mc, nc);
	if ( expcase == GRANDGW_SIZEERROR)
	{ 
		return GWSUPPORT_ERROR;
	}

	// Compute the offset in a, depending on the size of a
	a_offset=distfun_ComputeOffset(ma,na);

	// Compute the offset in b, depending on the size of b
	b_offset=distfun_ComputeOffset(mb,nb);

	// Compute the offset in c, depending on the size of c
	c_offset=distfun_ComputeOffset(mc,nc);

	// Create Lhs
	finalRows = distfun_Max(distfun_Max(distfun_Max(ma,mb),mc),m);
	finalCols = distfun_Max(distfun_Max(distfun_Max(na,nb),nc),n);

	readFlag = gwsupport_AllocateLhsMatrixOfDoubles ( 1, finalRows, finalCols, &px);
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return GWSUPPORT_ERROR;
	}
	// Generate x
	for ( i=0 ; i < finalRows*finalCols ; i++)
	{
		status = (*randgen)(pa[a_index],pb[b_index],pc[c_index],px+i);
		if (status!=CDFLIB_OK)
		{
			return GWSUPPORT_ERROR;
		}
		a_index=a_index+a_offset;
		b_index=b_index+b_offset;
		c_index=c_index+c_offset;
	}
	return GWSUPPORT_OK;
}


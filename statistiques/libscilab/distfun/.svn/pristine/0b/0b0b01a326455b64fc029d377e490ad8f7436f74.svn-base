
/*
* Copyright (C)  2012 - 2014 - Michael Baudin
* Copyright (C)  2001 - Bruno Pincon
* Copyright (C) ENPC - jpc@cermics.enpc.fr
*
* This file must be used under the terms of the CeCILL.
* This source file is licensed as described in the file COPYING, which
* you should have received as part of this distribution.  The terms
* are also available at
* http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
*
*/

#include <string.h>
#include <math.h>
#include <stdlib.h>

// From Distfun:
#include "gwsupport.h"
#include "cdflib.h"
#include "gw_distfunrnd.h"
#include "gw_distfunrnd_support.h" 

// From Scilab:
#include "api_scilab.h"
#include "Scierror.h"
#include "sciprint.h"
#include "localization.h"
#include "stack-c.h"

/**************************************************
Y=distfun_rndmvn(mean,sigma) 
Y=distfun_rndmvn(mean,sigma,n) 

generates n multivariate normal random variates

mean : 1 x d matrix
sigma d x d symmetric positive definite matrix 
n : 1 x 1 matrix, the number of samples (default n=1)
Y : n x d matrix
***********************************************************************/

int sci_distfun_rndmvn(char *fname,unsigned long fname_len)
{
	int rowsSigma,colsSigma,rowsMean,colsMean;
	int i, j;
	int nn,mp;

	int status;

	double * pMean = NULL;
	double * pSigma = NULL;
	double * pY = NULL;
	double * work = NULL;
	double * choleskyFactors = NULL;
	double * pYi = NULL; // One single outcome (size=rowsMean)

	int nbargs;
	int d;

	CheckInputArgument(pvApiCtx,2,3);
	CheckOutputArgument(pvApiCtx,1,1);

	nbargs=*getNbInputArgument(pvApiCtx);

	// Arg #1 : Mean
	status=gwsupport_GetRealMatrixOfDoubles(fname, 1, &pMean, &rowsMean, &colsMean);
	if (status==GWSUPPORT_ERROR)
	{
		return 0;
	}
	if ( rowsMean != 1) 
	{ 
		Scierror(999,_("%s: Wrong size for input argument #%d: Row vector expected.\n"),fname,1);
		return 0;
	}
	if ( colsMean <= 0 )
	{
		Scierror(999,_("%s: Wrong size for input argument #%d: 1-by-n matrix expected.\n"),fname,1);
		return 0;
	}
	d = colsMean;
	// Arg #2 : C
	status=gwsupport_GetRealMatrixOfDoubles(fname, 2, &pSigma, &rowsSigma, &colsSigma);
	if (status==GWSUPPORT_ERROR)
	{
		return 0;
	}
	if (( rowsSigma != colsSigma ) || ( rowsSigma != d ) )
	{ 
		Scierror(999,_("%s: Wrong size for input argument #%d: %d-by-%d matrix expected.\n"),fname,2,d,d);
		return 0;
	}
	// Arg #3 : n
	if (nbargs==2)
	{
		nn=1;
	}
	else
	{
		status=gwsupport_GetOneIntegerArgument( fname, 3, &nn);
		if (status==GWSUPPORT_ERROR)
		{
			return 0;
		}
		if ( nn < 0 ) 
		{ 
			Scierror(999,_("%s: Wrong value for input argument #%d: Must be >= %d.\n"),fname,3,0);
			return 0;
		}
	}	
	// Create Y
	status=gwsupport_AllocateLhsMatrixOfDoubles (1, nn, d, &pY);
	if ( status == GWSUPPORT_ERROR)
	{ 
		return GWSUPPORT_ERROR;
	}
	// Allocate working arrays
	work=(double *) malloc(d*sizeof(double));
	mp=d*(d+1)/2;
	choleskyFactors=(double *) malloc(mp*sizeof(double));
	pYi=(double *) malloc(d*sizeof(double));
	// Decompose the covariance matrix
	status=cdflib_mvnsetup(d,pMean,pSigma,choleskyFactors);
	if ( status == CDFLIB_ERROR)
	{
		return 0;
	}
	for ( i=0 ; i < nn ; i++)
	{
		status=cdflib_mvnrnd(d,pMean,choleskyFactors,work,pYi);
		if ( status == CDFLIB_ERROR)
		{
			return 0;
		}
		// Copy the results into i-th row of Y
		for ( j=0 ; j < d ; j++)
		{
			pY[nn*j+i]=pYi[j];
		}
	}
	LhsVar(1) = Rhs+1;
	free(work);
	free(choleskyFactors);
	free(pYi);
	return 0;
}

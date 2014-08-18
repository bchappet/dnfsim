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
#include <stdlib.h>

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

Multivariate Normal PDF

x : n x d matrix
mean : 1 x d matrix
sigma d x d symmetric positive definite matrix 
Y : n x 1 matrix, the density

Calling sequences:

y=distfun_pdfmvn(x,mu,sigma)

*/
/*--------------------------------------------------------------------------*/
int sci_distfun_pdfmvn(char* fname,unsigned long l)
{
	int readFlag;

	int rowsX = 0, colsX = 0;
	int rowsMean = 0, colsMean = 0;
	int rowsSigma = 0, colsSigma = 0;
	int rowsy = 0, colsy = 0;

	double * lrX = NULL;
	double * lrMean = NULL;
	double * lrSigma = NULL;
	double * lry = NULL;
	double * xi = NULL;

	int status = 0;
	int i;
	int j;
	int d;
	int n;

	double * choleskyFactors = NULL;
	double * work = NULL;

	CheckInputArgument(pvApiCtx,3,3);
	CheckOutputArgument(pvApiCtx,0,1);

	// Arg #1 : X
	readFlag = gwsupport_GetRealMatrixOfDoubles( fname, 1, &lrX, &rowsX , &colsX );
	if(readFlag==GWSUPPORT_ERROR)
	{
		return 0;
	}
	n=rowsX;
	d=colsX;
	// Arg #2 : Mean
	readFlag = gwsupport_GetRealMatrixOfDoubles( fname, 2, &lrMean, &rowsMean , &colsMean );
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	readFlag=gwsupport_CheckSize(fname, 2, 1, d, rowsMean, colsMean);
	if(readFlag==GWSUPPORT_ERROR)
	{
		return 0;
	}
	// Arg #3 : Sigma
	readFlag = gwsupport_GetRealMatrixOfDoubles( fname, 3,  &lrSigma, &rowsSigma , &colsSigma );
	if(readFlag==DISTFUNCDFGW_ERROR)
	{
		return 0;
	}
	readFlag=gwsupport_CheckSize(fname, 3, d, d, rowsSigma, colsSigma);
	if(readFlag==GWSUPPORT_ERROR)
	{
		return 0;
	}
	// Create LHS : y
	rowsy = rowsX;
	colsy = 1;
	iAllocMatrixOfDouble ( Rhs + 1 , rowsy , colsy , &lry );
	LhsVar(1) = Rhs+1;
	// Allocate xi
	xi = (double *) malloc(d*sizeof(double));
	// Allocate working arrays
	work=(double *) malloc(d*sizeof(double));
	choleskyFactors=(double *) malloc(d*(d+1)/2*sizeof(double));
	// Decompose the covariance matrix
	status=cdflib_mvnsetup(d,lrMean,lrSigma,choleskyFactors);
	if ( status == CDFLIB_ERROR)
	{
		return 0;
	}
	// Fill y
	status = CDFLIB_OK;
	for ( i=0 ; i < rowsX*colsX; i++)
	{
		// Copy x(i,:) into xi
		for ( j=0 ; j < d; j++)
		{
			xi[j]=lrX[i+j*n];
		}
		status=cdflib_mvnpdf(d, xi, lrMean, choleskyFactors, work, lry+i);
		if (status != CDFLIB_OK)
		{
			distfun_defaultPDFError( fname, i );
			break;
		}
	}
	// Free memory
	free(xi);
	free(choleskyFactors);
	free(work);
	return 0;
}
/*--------------------------------------------------------------------------*/

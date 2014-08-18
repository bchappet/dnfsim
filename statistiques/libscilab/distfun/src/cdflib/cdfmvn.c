// Copyright (C) 2012 - 2014 - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#include "cdflib.h"
#include "cdflib_private.h"
#include "stdlib.h"
#include "math.h"

int cdflib_mvnsetup(int d, double *mean, double *sigma, double *choleskyFactors)
{
	int i, j;
	int info;
	int icount;

	char uplo='U';
	int uplo_len=1;

	// TODO : check the input arguments
	// Check that sigma is symmetric
	icount = 0;
	for (j=0;j<d;j++)
	{
		for (i=0;i<=j;i++)
		{
			if (sigma[i + j * d]!=sigma[j + i * d])
			{
				cdflib_messageprint("cdflib_mvnsetup: Multivariate Normal Random Number: sigma not symmetric\n");
				return CDFLIB_ERROR;
			}
		}
	}	

	// Pack sigma for dpptrf_
	icount = 0;
	for (j=0;j<d;j++)
	{
		for (i=0;i<=j;i++)
		{
			choleskyFactors[icount] = sigma[i + j * d];
			icount=icount+1;
		}
	}	
	// Compute Cholesky factors
	dpptrf_(&uplo, &d, choleskyFactors, &info, uplo_len);
	if (info != 0) 
	{
		cdflib_messageprint("cdflib_mvnsetup: Multivariate Normal Random Number: sigma not positive definite\n");
		return CDFLIB_ERROR;
	}

	return CDFLIB_OK;
}

int cdflib_mvnrnd(int d, double * mean, double *choleskyFactors, 
double *work, double *x)
{
	int i, j;
	double ae;
	int icount;

	/*     Generate P independent standard normal deviates - WORK ~ N(0,1) */
	for (i = 0; i < d; ++i) 
	{
		work[i] = cdflib_snorm();
	}
	icount = 0;
	for (i = 0; i < d; ++i) {
		/*     trans(A)*WORK + MEANV ~ N(MEANV,COVM) */
		ae = 0.f;
		for (j = 0; j <= i; ++j) 
		{
			ae = ae + choleskyFactors[icount] * work[j];
			icount = icount + 1;
		}
		x[i] = ae + mean[i];
	}
	return CDFLIB_OK;
}

int cdflib_mvnpdf(int d, double * x, double * mean, double * choleskyFactors, 
double *work, double * y)
{
	char uplo='U';
	int i;
	int icount;
	int nrhs=1;
	int uplo_len=1;
	double dByTwo;
	double logDetSigma;
	double pi=3.14159265358979323846;
	double t;
	int incx=1;
	int incy=1;
	double logy;
	char trans='T';
	char diag='N';

	// det(sigma) = prod(diag(U))**2
    // => log(det(sigma))=2*sum(log(diag(U)))
    logDetSigma=0.;
	icount=0;
	for (i = 0; i < d; ++i) {
		logDetSigma=logDetSigma+log(choleskyFactors[icount]);
		icount=icount+i+2;
	}
	logDetSigma=2*logDetSigma;
    dByTwo=-d/2.*log(2*pi)-logDetSigma/2.;
	// Computes the right-hand side : xi-mu
	for (i = 0; i < d; ++i) {
		work[i]=x[i]-mean[i];
	}
    // Solves U'*z = (xi-mu)
	dtpsv_(&uplo, &trans, &diag, &d, choleskyFactors, work, &incx);
	// Computes t=z'*z
	t = ddot_(&d, work, &incx, work, &incy);
	//
    logy=dByTwo-t/2.;
    *y=exp(logy);
	return CDFLIB_OK;
}

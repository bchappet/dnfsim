
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
#include <stdlib.h>     /* malloc, free */

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
Note :
Technically, only the first k-1 entries of P are used by cdflib_genmul, 
but P is provided as a k-by-1 vector.
***********************************************************************/

int sci_distfun_rndmn(char *fname,unsigned long fname_len)
{
	int nP;
	int i;
	int j;
	int m; // The number of random vectors to generate
	int k; // The number of categories
	int n; // The number of trials
	double ptot; // sum(P)
	double epsilon=2.e-15;
	int readFlag;
	double * pP = NULL;
	double * px = NULL;
	int * intx; // Temporary storage of size(k)

	CheckInputArgument(pvApiCtx,2,3);
	CheckOutputArgument(pvApiCtx,1,1);

	// Get Arg #1 : n
	readFlag = gwsupport_GetOneIntegerArgument (fname, 1, &n );
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	if ( n <= 0 )
	{
		Scierror(999,_("%s: Wrong value for input argument #%d: Must be > %d.\n"),fname,1,1);
		return 0;
	}
	// Get Arg #2 : P, a row vector
	readFlag = gwsupport_GetRealMatrixOfDoubles( fname, 2, &pP, &nP, &k);
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	readFlag = gwsupport_CheckDoubleMatrixInRange (fname, 2, pP, nP, k, 0., 1.);
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	if ( nP != 1 || k < 1 )
	{
		Scierror(999,_("%s: Wrong size for input argument #%d: Row vector expected.\n"),fname,2);
		return 0;
	}
	// Check sum(P)==1
	ptot = 0.0;
	for ( i= 0 ; i < k; i++ )
	{
		ptot += pP[i];
	}
	if ( ptot > 1.0 || fabs(ptot-1)>epsilon)
	{
		Scierror(999,_("%s: Wrong value for input argument #%d: ""%s"" expected .\n"),fname,2,"sum(P)==1");
		return 0;
	}
	// Get Arg #3 : m
	if (Rhs==2)
	{
		m=1;
	}
	else
	{
		readFlag = gwsupport_GetOneIntegerArgument (fname, 3, &m );
		if ( readFlag == GWSUPPORT_ERROR)
		{ 
			return 0;
		}
		if ( m < 1)
		{
			Scierror(999,_("%s: Wrong value for input argument #%d: Must be >= %d.\n"),fname,3,1);
			return 0;
		}
	}

	// Create output argument
	readFlag = gwsupport_AllocateLhsMatrixOfDoubles ( 1, m, k, &px);
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	intx=(int *) malloc(k*sizeof(int));
	// Generate the numbers
	for ( i=0 ; i < m ; i++)
	{
		readFlag=cdflib_mnrnd(n,pP,k,intx);
		if ( readFlag == CDFLIB_ERROR)
		{ 
			return 0;
		}
		// Copy the results into x
		for ( j=0 ; j < k ; j++)
		{
			px[m*j+i]=(double)intx[j];
		}
	}
	free(intx);
	LhsVar(1) = Rhs+1;

	return 0;
}

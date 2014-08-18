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

// From Distfun
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


int sci_distfun_rndprm(char *fname,unsigned long fname_len)
{
	int rowsVect,colsVect;
	int i;
	int n;
	int readFlag;
	int j ;
	double * lvect;
	double * lr;

	CheckInputArgument(pvApiCtx,1,2);
	CheckOutputArgument(pvApiCtx,1,1);

	// Get Arg #1 : vect
	readFlag = gwsupport_GetRealMatrixOfDoubles( fname, 1, &lvect, &rowsVect, &colsVect );
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	// Expect a column vector
	if (colsVect!=1)
	{
	
		Scierror(999,_("%s: Wrong size for input argument #%d: Column vector expected.\n"),fname, 1);
		return 0;
	}
	if (*getNbInputArgument(pvApiCtx)==1)
	{
		n=1;
	}
	else
	{
		// Arg #2 : N>=1
		readFlag = gwsupport_GetOneIntegerArgument ( fname , 2, &n );
		if ( readFlag == GWSUPPORT_ERROR)
		{ 
			return 0;
		}
		if ( n <= 0 )
		{
			Scierror(999,_("%s: Wrong value for input argument #%d: Must be >= %d.\n"),fname, 2, 1);
			return 0;
		}
	}
	// Create R
	readFlag = gwsupport_AllocateLhsMatrixOfDoubles ( 1, rowsVect, n, &lr);
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	// Fill R
	for ( i=0 ; i < n ; i++)
	{
		// Copy the vector lvect into lr
		for (j=0; j < rowsVect ; j++ ) {
			lr[(rowsVect)*i+j]= lvect[j];
		}
		// Permute the i-th column of lr
		cdflib_genprm(lr+rowsVect*i,rowsVect);
	}
	LhsVar(1) = Rhs+1;

	return 0;
}

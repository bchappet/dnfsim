
/*
* Copyright (C)  2012 - Michael Baudin
* Copyright (C)  2001 - Bruno Pincon
* Copyright (C) ENPC
*
* This file must be used under the terms of the CeCILL.
* This source file is licensed as described in the file COPYING, which
* you should have received as part of this distribution.  The terms
* are also available at
* http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
*
*/

/*------------------------------------------------------------------------
*    Interface for grand
*    jpc@cermics.enpc.fr
*    stuff to deal with several generators added
*         by Bruno Pincon (12/11/2001)
*
--------------------------------------------------------------------------*/
#include <string.h>
#include <math.h>

// From Distfun:
#include "gwsupport.h"
#include "gw_distfunurng.h"
#include "unifrng.h"

// From Scilab:
#include "Scierror.h"
#include "sciprint.h"
#include "localization.h"
#include "stack-c.h"

/**************************************************
S=distfun_seedget() gets the current state (the current seeds) 
of the current base generator ; 

S is given as a column vector (of integers) of dimension 
* 625 for mt (the first being an index in [1,624]), 
* 4 for kiss, 
* 2 for clcg2, 
* 40 for fsultra, 
* 4 for clcg4, 
* 1 for urand,
* 1 for crand.
For clcg4, S is the current state of the current virtual generator.
***********************************************************************/

int sci_distfun_seedget(char *fname,unsigned long fname_len)
{

	int dim;
	int readFlag;
	double * seed = NULL;
	
	CheckRhs(0,0);
	CheckLhs(1,1);

	dim = unifrng_getcurrentgenstatedim();
	readFlag = gwsupport_AllocateLhsMatrixOfDoubles (1,dim,1, &seed);
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	unifrng_getcurrentgenstate(seed);
	LhsVar(1) = Rhs+1;
	return 0;
}

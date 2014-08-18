
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
S=distfun_streamget() 

For CLCG4 only.

returns the number of the current virtual clcg4 generator.
***********************************************************************/

int sci_distfun_streamget(char *fname,unsigned long fname_len)
{
	int current_gen;
	int current_virtualgen;
	double *stream = NULL;
	int readFlag;

	CheckRhs(0,0);
	CheckLhs(1,1);

	unifrng_getcurrentgen(&current_gen);
    if ( current_gen != CLCG4 )
	{
		Scierror(999,_("%s: This function concerns only the clcg4 generator\n"),fname);
		return 0;
	}
	readFlag = gwsupport_AllocateLhsMatrixOfDoubles ( 1, 1, 1, &stream);
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return GWSUPPORT_ERROR;
	}
	current_virtualgen = unifrng_clcg4_get_current_virtual();
	stream[0] = (double)current_virtualgen;
	LhsVar(1) = Rhs+1;
	return 0;
}

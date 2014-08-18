
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
distfun_streaminit(I) 

reinitializes the state of the current virtual generator

For CLCG4 only.

I = -1
sets the state to its initial seed
I = 0
sets the state to its last (previous) seed (i.e. to the beginning of the current segment)
I = 1
sets the state to a new seed W values from its last seed (i.e. to the beginning of the next segment) and resets the current segment parameters.

***********************************************************************/

int sci_distfun_streaminit(char *fname,unsigned long fname_len)
{
	unifrng_clcg4_SeedType Where;
	int current_gen;
	int readFlag;
	int Iflag;

	Nbvars = 0;
	CheckRhs(1,1);
	CheckLhs(0,1);

	unifrng_getcurrentgen(&current_gen);
	if ( current_gen != CLCG4 ) {
		Scierror(999,_("%s: This function affects only the %s generator\n"),fname,"clcg4");
		return 0;
	}
	readFlag = gwsupport_GetOneIntegerArgument ( fname , 1 , &Iflag );
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	if ( Iflag!= 0 && Iflag!= -1 && Iflag!= 1)
	{
		Scierror(999,_("%s: Wrong value for second input argument: %d, %d or %d expected.\n"),fname, -1, 0, 1);
		return 0;
	}
	Where = (unifrng_clcg4_SeedType) (Iflag + 1);
	unifrng_clcg4_init_currentgenerator(Where);
	LhsVar(1) = 1;
	return 0;
}

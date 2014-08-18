
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

// From Distfun
#include "gwsupport.h"
#include "gw_distfunurng.h"
#include "unifrng.h"

// From Scilab:
#include "Scierror.h"
#include "sciprint.h"
#include "localization.h"
#include "stack-c.h"

/**************************************************
distfun_streamset(g) 

g : an integer in [0,100]

Sets the current virtual generator for clcg4 to g

When clcg4 is set, this is the virtual (clcg4) generator number g which is used

The virtual clcg4 generators are numbered from 0,1,..,100.

By default, the current virtual generator is g=0.
***********************************************************************/

int sci_distfun_streamset(char *fname,unsigned long fname_len)
{
	int current_gen;
	int newvirtualgen;
	int readFlag;

	Nbvars = 0;
	CheckRhs(1,1);
	CheckLhs(0,1);

	unifrng_getcurrentgen(&current_gen);
	if ( current_gen != CLCG4 )
	{
		Scierror(999,_("%s: This function affects only the %s generator\n"),fname,"clcg4");
		return 0;
	}
	readFlag = gwsupport_GetOneIntegerArgument ( fname , 1 , &newvirtualgen );
	if ( readFlag == GWSUPPORT_ERROR)
	{ 
		return 0;
	}
	if ( newvirtualgen < 0 || newvirtualgen > Maxgen_clcg4 )
	{
		Scierror(999,_("%s: Wrong value for second input argument: Must be between %d and %d.\n"),fname,0, Maxgen_clcg4);
		return 0;
	}
	unifrng_clcg4_set_current_virtual(newvirtualgen);
	LhsVar(1) = 1;

	return 0;
}

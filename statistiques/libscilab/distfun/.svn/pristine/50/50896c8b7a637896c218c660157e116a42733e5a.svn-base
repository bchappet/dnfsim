
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

// From Distfun
#include "gwsupport.h"
#include "gw_distfunurng.h"
#include "unifrng.h"

// From Scilab:
#include "Scierror.h"
#include "localization.h"
#include "api_scilab.h"
#include "stack-c.h"

/**************************************************
distfun_genset(gen) 

sets the current base generator to be gen a string among 
'mt', 'kiss', 'clcg2', 'clcg4', 'urand', 'fsultra', 'crand'

Notes that this call returns the new current generator, ie gen.
***********************************************************************/

int sci_distfun_genset(char *fname,unsigned long fname_len)
{
	char *newgen = NULL;
	int readFlag;
	int iRet = 0;

	CheckRhs(1,1);
	CheckLhs(0,1);

	readFlag = gwsupport_GetScalarString( fname, 1 , &newgen );
	if (readFlag==GWSUPPORT_ERROR)
	{
		return 0;
	}
	readFlag = unifrng_setcurrentgenname(newgen);
	if (readFlag==UNIFRNG_ERROR)
	{
		Scierror(999,_("%s: Wrong value for second input argument: '%s', '%s', '%s', '%s', '%s', '%s' or '%s' expected.\n"),fname,"mt","kiss","clcg4","clcg2","urand","fsultra","crand");
		return 0;
	}

	LhsVar(1) = 1;

	return 0;
}

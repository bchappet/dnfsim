
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

// From Distfun:
#include "gw_distfunurng.h"
#include "unifrng.h"

// From Scilab:
#include "api_scilab.h"
#include "stack-c.h"

/**************************************************
gen = distfun_genget() 

returns the current base generator : 'mt', 'kiss', 'clcg2', 'clcg4', 'urand', 'fsultra'

***********************************************************************/

int sci_distfun_genget(char *fname,unsigned long fname_len)
{
	char *gen = NULL;
	int iRet = 0;

	CheckRhs(0,0);
	CheckLhs(0,1);

	unifrng_getcurrentgenname(&gen);
	iRet = createSingleString(pvApiCtx, Rhs + 1, gen);
	LhsVar(1) = Rhs + 1;
	return 0;
}

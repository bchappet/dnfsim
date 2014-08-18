/*
* Copyright (C)  2012 - 2014 - Michael Baudin
*
* This file must be used under the terms of the CeCILL.
* This source file is licensed as described in the file COPYING, which
* you should have received as part of this distribution.  The terms
* are also available at
* http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
*
*/

#include <string.h>

// From Distfun:
#include "unifrng.h"
#include "cdflib.h"

// From Scilab:
#include "api_scilab.h"
#include "Scierror.h"
#include "sciprint.h"
#include "localization.h"
#include "stack-c.h"
#include "gwsupport.h"

// sci_unifrng_messageFunction --
//   The message callback used by unifrng library.
//   Redirect the message to Scilab's error function.
void sci_unifrng_messageFunction ( char * message ) {
	gwsupport_PrintWarning(message);
}

// sci_cdflib_messageFunction --
//   The message callback used by cdflib library.
//   Redirect the message to Scilab's error function.
void sci_cdflib_messageFunction ( char * message ) {
	gwsupport_PrintWarning(message);
}

// sci_genrand_randfunction --
//   The RNG callback used by genrand library.
//   Redirect the message to unifrng's rand function.
double sci_cdflib_randfunction (void) {
	double R;
	R = unifrng_rand();
	return R;
}

// sci_genrand_randIntegerInRangeFunction --
//   The RNG callback used by genrand library.
//   Redirect the message to unifrng's rand function.
double sci_cdflib_randIntegerInRangeFunction (double a, double b) {
	double R;
	R = unifrng_generateIntegerInRange(a,b);
	return R;
}

/**************************************************
distfun_startup() 
****************************************************/

int sci_distfun_startup(char *fname,unsigned long fname_len)
{
	CheckRhs(0,0);
	CheckLhs(0,1);

	cdflib_startup();
	unifrng_messagesetfunction(sci_unifrng_messageFunction);
	cdflib_randSetFunction(sci_cdflib_randfunction);
	cdflib_randIntegerInRangeSetFunction(sci_cdflib_randIntegerInRangeFunction);
	cdflib_messageSetFunction(sci_cdflib_messageFunction);
	return 0;
}

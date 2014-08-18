// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#include <string.h>
#include <stdio.h>
#include <math.h>

#include "cdflib.h"
#include "cdflib_private.h" 

#ifdef _MSC_VER
#include <float.h>
#endif

// Parameters used in the cdf*.c functions

double cdflib_doubleEps()
{
	double ret_val;
	double eps;
	eps = 2.2204460492503131e-016;
	ret_val = 2*eps;
	return ret_val;

}

double cdflib_doubleTiny()
{
	double ret_val;
	ret_val = 2.2250738585072014e-308;
	return ret_val;
}

double cdflib_doubleHuge()
{
	double ret_val;
	ret_val = 1.7976931348623157e+308;
	return ret_val;
}

double cdflib_infinite(void)
{
	double ret_val;
	double one;
	one=1.;
	ret_val = 1./(one-one);
	return ret_val;
}

double cdflib_isnan(double x)
{
	double r;
#ifdef _MSC_VER 
	r=_isnan(x);
#else 
	r=isnan(x);
#endif 
	return r;
}

double cdflib_nan()
{
	double r;
	double one=1.;
	r=(one-one)/(one-one);
	return r;
}

// Initialize the message function to NULL.
void (* cdflib_messagefunction)(char * message) = NULL;

// Prints a message.
void cdflib_messageprint ( char * message ) 
{
	if (cdflib_messagefunction==NULL) {
		printf("%s\n",message);
	}
	else 
	{
		cdflib_messagefunction(message);
	}
}

// Configures the message function
void cdflib_messageSetFunction ( void (* f)(char * message) ) 
{
	cdflib_messagefunction = f;
}

// Checks that value>=minparam and value<=maxparam (int).
int cdflib_checkrangeint(char * fname, int param, 
	char * paramname, int minparam, int maxparam) 
{
	char buffer [1024];
	if (param < minparam) 
	{
		sprintf (buffer, CDFLIB_MSGINTGREQ,fname,paramname,minparam);
		cdflib_messageprint(buffer);
		return ( CDFLIB_ERROR );
	} 
	else if (param > maxparam) 
	{
		sprintf (buffer, CDFLIB_MSGINTLOWEQ,fname,paramname,maxparam);
		cdflib_messageprint(buffer);
		return ( CDFLIB_ERROR );
	}
	return ( CDFLIB_OK );
}

// Checks that value>=minparam and value<=maxparam (double).
int cdflib_checkrangedouble(char * fname, double param, 
	char * paramname, double minparam, double maxparam) 
{
	char buffer [1024];
	if (param < minparam) 
	{
		sprintf (buffer, CDFLIB_MSGFLOATGREQ,fname,paramname,minparam);
		cdflib_messageprint(buffer);
		return ( CDFLIB_ERROR );
	} 
	else if (param > maxparam) 
	{
		sprintf (buffer, CDFLIB_MSGFLOATLOWEQ,fname,paramname,maxparam);
		cdflib_messageprint(buffer);
		return ( CDFLIB_ERROR );
	}
	return ( CDFLIB_OK );
}

// Checks the value of p in [0,1]
int cdflib_checkp(char * fname, double p, char * pname) 
{
	int status;
	// Check P
	status = cdflib_checkrangedouble(fname, p, pname, 0., 1.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return ( CDFLIB_OK );
}

// Checks the values of p and q
int cdflib_checkpq(char * fname, double p, char * pname, double q, char * qname) 
{
	int status;
	char pqname [1024];
	// Check P
	status = cdflib_checkrangedouble(fname, p, pname, 0., 1.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}

	// Check Q
	status = cdflib_checkrangedouble(fname, q, qname, 0., 1.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}

	// Check P+Q
	sprintf (pqname, "%s+%s",pname,qname);
	status = cdflib_checksumtoone(fname, p, q, pqname);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return CDFLIB_OK;
}

// Checks that x+y==1.
// The formal name for x+y is paramname.
int cdflib_checksumtoone(char * fname, double x, double y, char * paramname) 
{
	double xyminus1;
	double xy;	
	double epsilon;
	char buffer [1024];

	//
	xy = x + y;
	xyminus1 = xy - .5 - .5;
	epsilon = cdflib_doubleEps();
	if (fabs(xyminus1) > epsilon * 3.) 
	{
		sprintf (buffer, "%s: Wrong value for input arguments ""%s"": %s.\n",fname,paramname,"not equal to 1");
		cdflib_messageprint(buffer);
		return ( CDFLIB_ERROR );
	}
	if (xy < 0.) 
	{
		sprintf (buffer, "%s: Wrong value for input arguments ""%s"": %s.\n",fname,paramname,"< 0");
		cdflib_messageprint(buffer);
		return ( CDFLIB_ERROR );
	}
	return (CDFLIB_OK);
}

// Checks that value>minparam.
int cdflib_checkgreaterthan(char * fname, double param, 
	char * paramname, double minparam) 
{
	char buffer [1024];
	if (param <= minparam) 
	{
		sprintf (buffer, CDFLIB_MSGFLOATGREATER,fname,paramname,minparam);
		cdflib_messageprint(buffer);
		return ( CDFLIB_ERROR );
	} 
	return ( CDFLIB_OK );
}

// Checks that value>=minparam.
int cdflib_checkgreqthan(char * fname, double param, 
	char * paramname, double minparam) 
{
	char buffer [1024];
	if (param < minparam) 
	{
		sprintf (buffer, CDFLIB_MSGFLOATGREQ,fname,paramname,minparam);
		cdflib_messageprint(buffer);
		return ( CDFLIB_ERROR );
	} 
	return ( CDFLIB_OK );
}

// Checks that value<=maxparam.
int cdflib_checkloweqthan(char * fname, double param, 
	char * paramname, double maxparam) 
{
	char buffer [1024];
	if (param > maxparam) 
	{
		sprintf (buffer, CDFLIB_MSGFLOATLOWEQ,fname,paramname,maxparam);
		cdflib_messageprint(buffer);
		return ( CDFLIB_ERROR );
	} 
	return ( CDFLIB_OK );
}
int cdflib_checkintloweqthan(char * fname, int param, 
	char * paramname, int maxparam) 
{
	char buffer [1024];
	if (param > maxparam) 
	{
		sprintf (buffer, CDFLIB_MSGINTLOWEQ,fname,paramname,maxparam);
		cdflib_messageprint(buffer);
		return ( CDFLIB_ERROR );
	} 
	return ( CDFLIB_OK );
}

// Computes the zero function for inverse CDF computation.
double cdflib_computefx(double p, double q, double cum, double ccum) 
{
	double fx;
	if (p <= q) 
	{
		fx = cum - p;
	}
	else
	{
		fx = ccum - q;
	}
	return fx;
}

// Print a "Unable to invert message"
void cdflib_unabletoinvert(char * fname, double bound, char * paramname) 
{
	char buffer [1024];
	sprintf (buffer, "%s: Unable to compute ""%s"". Bound reached=%e.\n",fname,paramname,bound);
	cdflib_messageprint(buffer);
	return;
}
// Checks that value>minparam.
int cdflib_checkintgreaterthan(char * fname, int param, 
	char * paramname, int minparam) 
{
	char buffer [1024];
	if (param <= minparam) 
	{
		sprintf (buffer, CDFLIB_MSGINTGREATER,fname,paramname,minparam);
		cdflib_messageprint(buffer);
		return ( CDFLIB_ERROR );
	} 
	return ( CDFLIB_OK );
}

// Checks that value>=minparam.
int cdflib_checkintgreqthan(char * fname, int param, 
	char * paramname, int minparam) 
{
	char buffer [1024];
	if (param < minparam) 
	{
		sprintf (buffer, CDFLIB_MSGINTGREQ,fname,paramname,minparam);
		cdflib_messageprint(buffer);
		return ( CDFLIB_ERROR );
	} 
	return ( CDFLIB_OK );
}
// Check the tail option of a function
int cdflib_checklowertail(char * fname, int lowertail) 
{
	char buffer [1024];
	if (lowertail!=CDFLIB_UPPERTAIL && lowertail!=CDFLIB_LOWERTAIL)
	{
		sprintf (buffer, CDFLIB_MSGGENERIC,fname,"lowertail","0 or 1");
		cdflib_messageprint(buffer);
		return ( CDFLIB_ERROR );
	}
	return ( CDFLIB_OK );
}

// Check that a double has an integer value.
// That is, check that the double has no fractionnal part.
int cdflib_checkIntValue(char * fname, double x, char * paramname)
{
	double t;
	char buffer [1024];
	t = floor(x);
	if (x!=t)
	{
		sprintf (buffer, CDFLIB_MSGFLOATVALUEINT,fname,"x");
		cdflib_messageprint(buffer);
		return ( CDFLIB_ERROR );
	} 
	return ( CDFLIB_OK );
}

// Initialize the RNG function to NULL.
double (* cdflib_randfunction)(void) = NULL;

// Initialize the RNG function to NULL.
double (* cdflib_randIntegerInRange)(double a, double b) = NULL;

// Configures the RNG function
void cdflib_randSetFunction ( double (* f)(void) )
{
	cdflib_randfunction = f;
}

// Configures the RNG function
void cdflib_randIntegerInRangeSetFunction ( double (* f)(double a, double b) )
{
	cdflib_randIntegerInRange = f;
}

// The function which generates random numbers uniform in [0,1).
double cdflib_randgenerate()
{
	double R;
	if (cdflib_randfunction==NULL)
	{
		cdflib_messageprint("genrand: No uniform random number generator configured.\n");
		cdflib_messageprint("genrand: Please call cdflib_randSetFunction at least once.\n");
		R=cdflib_nan();
	}
	else
	{
		R = cdflib_randfunction();
	}
	return R;
}

// The function which generates random integers uniform in [a,b].
double cdflib_generateIntegerInRange(double a , double b)
{
	double R;
	if (cdflib_randfunction==NULL)
	{
		cdflib_messageprint("genrand: No uniform random number generator configured.\n");
		cdflib_messageprint("genrand: Please call cdflib_randIntegerInRangeSetFunction at least once.\n");
		R=cdflib_nan();
	}
	else
	{
		R = cdflib_randIntegerInRange(a,b);
	}
	return R;
}

void cdflib_printiter(char * fname, int iteration) 
{
	char buffer [1024];
	static int total_iterations=0;
	total_iterations=total_iterations+iteration;
	if (cdflib_verbose==CDFLIB_VERBOSEON)
	{
		sprintf (buffer, "%s: Iterations: %d (total=%d)\n", \
			fname,iteration,total_iterations);
		cdflib_messageprint(buffer);
	}
	return;
}

// Configure the verbose mode.
void cdflib_verboseset(int verbosemode)
{
	char buffer [1024];
	if (verbosemode==CDFLIB_VERBOSEON)
	{
		cdflib_verbose = CDFLIB_VERBOSEON;
	}
	else if (verbosemode==CDFLIB_VERBOSEOFF)
	{
		cdflib_verbose = CDFLIB_VERBOSEOFF;
	}
	else
	{
		sprintf (buffer, "%s: Unknown verbose mode %d\n","cdflib_verboseset",verbosemode);
		cdflib_messageprint(buffer);
	}
}

int cdflib_largestint(void)
{
	int huge=2147483647;
	return huge;
}

int cdflib_radix()
{
	int radix=2;
	return radix;
}

int cdflib_emin()
{
	int emin=-1021;
	return emin;
}

int cdflib_emax()
{
	int emin=1024;
	return emin;
}

double cdflib_powdd(double ap, double bp)
{
	return(pow(ap, bp) );
}

double cdflib_dsign(double a, double b)
{
	double absa;
	double result;
	if (a >= 0)
	{
		absa = a;
	}
	else
	{
		absa = -a;
	}
	if (b >= 0)
	{
		result = absa;
	}
	else
	{
		result = -absa;
	}
	return result;
}

double cdflib_dint(double x)
{
	return( (x>0) ? floor(x) : -floor(- x) );
}

int cdflib_nearestint(double x)
{
	double nearest;
	int n;
	nearest=floor(x+0.5);
	n=(int) nearest;
	return n;
}

void cdflib_startup()
{
	incgam_startup();
}

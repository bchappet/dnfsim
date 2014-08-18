// Copyright (C) 2012 - 2014 - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#include <math.h>

#include "cdflib.h"
#include "cdflib_private.h" 
#include "brent.h"

// Negative Binomial Distribution

int cdflib_nbnCheckX(char * fname, double x)
{
	int status;
	/*     X >= 0 */
	status=cdflib_checkgreqthan(fname, x, "x", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return CDFLIB_OK;
}

int cdflib_nbnCheckParams(char * fname, double R, double Pr)
{
	int status;

	/* R >= 0 */
	status=cdflib_checkgreqthan(fname, R, "R", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}

	// PR in [0,1]
	status=cdflib_checkp(fname, Pr, "Pr");
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return CDFLIB_OK;
}

int cdflib_nbnpdf(double x, double R, double Pr, double *p)
{
	int status;
	double c;
	// Check x
	status=cdflib_nbnCheckX("cdflib_cdfnbn", x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Check R, PR
	status=cdflib_nbnCheckParams("cdflib_cdfnbn", R, Pr);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	//
	c = incgam_loggam(x+R)-incgam_loggam(x+1)-incgam_loggam(R);
    *p = c +x*cdflib_log1p(-Pr)+R*log(Pr);
    *p = exp(*p);

	return status;
}

int cdflib_nbncdf(double x, double R, double Pr, int lowertail, double *p)
{
	int status;
	double ompr;
	double q;

	// Check x
	status=cdflib_nbnCheckX("cdflib_cdfnbn", x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Check R, PR
	status=cdflib_nbnCheckParams("cdflib_cdfnbn", R, Pr);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	/*     Calculating P and Q */
	if (cdflib_isnan(x) || cdflib_isnan(R) || cdflib_isnan(Pr))
	{
		*p = x+R+Pr;
		status = CDFLIB_OK;
		return status;
	}
	ompr=1-Pr;
	cdflib_cumnbn(x, R, Pr, ompr, p, &q, &status);
	if (status==CDFLIB_ERROR)
	{
		return status;
	}
	if (lowertail==CDFLIB_UPPERTAIL)
	{
		*p=q;
	}
	status = CDFLIB_OK;
	return status;
}

int cdflib_nbninv(double p, double R, double Pr, int lowertail, double *x)
{
	int status;
	double q;
	double ompr;
	double b;
	int iteration;
	double fx;
	double cum, ccum;
	double atol = cdflib_doubleTiny();
	double inf = cdflib_infinite();
	double nan = cdflib_nan();
	double mean, Var, Sigma, Delta;
	double huge = cdflib_doubleHuge();
	int inversionlabel;

	// P in [0,1]
	status=cdflib_checkp("cdflib_nbninv", p, "p");
	if (status!=CDFLIB_OK)
	{
		return status;
	}

	// Check R, PR
	status=cdflib_nbnCheckParams("cdflib_nbninv", R, Pr);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Compute the couple (p,q) from (p,lowertail)
	if (lowertail==CDFLIB_LOWERTAIL)
	{
		q=1-p;
	}
	else
	{
		q=p;
		p=1-q;
	}
	ompr=1-Pr;
	/*     Calculating X */
	if (cdflib_isnan(p) || cdflib_isnan(q) || cdflib_isnan(R) || cdflib_isnan(Pr) )
	{
		*x = p+R+Pr;
		status = CDFLIB_OK;
		return status;
	}
	if ((p==1 && lowertail==CDFLIB_LOWERTAIL)||(q==1 && lowertail==CDFLIB_UPPERTAIL))
	{
		if (Pr==1.)
		{
			*x=0;
		}
		else if (Pr==0.)
		{
			*x=nan;
		}
		else
		{
			if (lowertail==CDFLIB_LOWERTAIL)
			{
				*x=inf;
			}
			else
			{
				*x=0;
			}
		}
		status = CDFLIB_OK;
		return status;
	}
	// 
	if ((p==0 && lowertail==CDFLIB_LOWERTAIL)||(q==0 && lowertail==CDFLIB_UPPERTAIL))
	{
		if (Pr==1.)
		{
			*x=0;
		}
		else if (Pr==0.)
		{
			*x=nan;
		}
		else
		{
			if (lowertail==CDFLIB_LOWERTAIL)
			{
				*x=0;
			}
			else
			{
				*x=inf;
			}
		}
		status = CDFLIB_OK;
		return status;
	}
	// See if x=0
	cdflib_cumnbn(0., R, Pr, ompr, &cum, &ccum, &status);
	if (status==CDFLIB_ERROR)
	{
		return status;
	}
	if (p<cum)
	{
		*x=0;
		status = CDFLIB_OK;
		return status;
	}
	//
	// Step A: Find a rough estimate of an interval.
	// Compute mean, Variance
	mean=R*(1-Pr)/Pr;
    Var=R*(1-Pr)/(Pr*Pr);
	Sigma=sqrt(Var);
	Delta=Sigma;
	if (Delta<1)
	{
		Delta=1;
	}
	// We know that x=0 is the lowest bound. 
	// Compute a "small" b, so that f(0)*f(b)<0.
	// This should be large enough, in general.
	if (mean+38*Delta<inf)
	{
		b=mean+38*Delta;
	}
	else
	{
		b=huge;
	}
	iteration=0;
	while (1)
	{
		cdflib_cumnbn(b, R, Pr, ompr, &cum, &ccum, &status);
		if (status==CDFLIB_ERROR)
		{
			return status;
		}
		fx = cdflib_computefx(p, q, cum, ccum);
		if (p <= q && fx>0)
		{
			break;
		}
		if (p > q && fx<0)
		{
			break;
		}
		// Assumes that the variable is approximated 
		// by a normal distribution : adding sigma 
		// will make the sign change.
		b=b+Delta;
		iteration=iteration+1;
		if (b==inf)
		{
			cdflib_unabletoinvert("cdflib_nbninv", b, "x");
			status = CDFLIB_ERROR;
			return status;
		}
	}
	// Step B: Refine this estimate.
	*x = 0.;
	inversionlabel = 0;
	while (1)
	{
		zero_rc ( 0., b, atol, x, &inversionlabel, fx );
		if (inversionlabel<0) 
		{
			break;
		}
		cdflib_cumnbn(*x, R, Pr, ompr, &cum, &ccum, &status);
		if (status==CDFLIB_ERROR)
		{
			return status;
		}
		fx = cdflib_computefx(p, q, cum, ccum);
		if (inversionlabel==0) 
		{
			break;
		}
		iteration=iteration+1;
	}
	if (inversionlabel == 0) 
	{
		status = CDFLIB_OK;
		*x=ceil(*x);
	}
	else
	{
		cdflib_unabletoinvert("cdflib_nbninv", *x, "x");
		status = CDFLIB_ERROR;
	}
	cdflib_printiter("cdflib_nbninv", iteration);
	return status;
}

void cdflib_cumnbn(double x, double xn, double pr, 
	double ompr, double *cum, double *ccum, int *status)
{
	cdflib_cumbet(pr, ompr, xn, x + 1., cum, ccum, status);
	return;
}

int cdflib_nbnrnd(int n, double p, int *x)
{
	static double a, r__, y;
	int status;
	double poissx;

	// TODO : check the arguments

	/*     JJV changed to call cdflib_sgamma directly */

	/*     Check Arguments */
	/*     JJV changed argumnet checker to abort if N <= 0 */
	/*     See Rand,c */
	/*     Generate Y, a random gamma (n,(1-p)/p) variable */
	/*     JJV Note: the above parametrization is consistent with Devroye, */
	/*     JJV       but gamma (p/(1-p),n) is the equivalent in our code */
	r__ = (double) (n);
	a = p / (1. - p);
	y = cdflib_sgamma(r__) / a;
	/*     Generate a random Poisson(y) variable */
	// TODO : check status
	status = cdflib_poissrnd(y, &poissx);
	*x=(int)poissx;
	return CDFLIB_OK;
}


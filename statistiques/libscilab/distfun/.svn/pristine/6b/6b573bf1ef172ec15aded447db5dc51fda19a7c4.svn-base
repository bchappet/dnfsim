// Copyright (C) 2012 - Amparo Gil, Javier Segura, Nico M. Temme
// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#include <math.h>
#include <stdio.h>

#include "cdflib.h"
#include "cdflib_private.h" 

#define INCGAM_FALSE 0
#define INCGAM_TRUE 1

// Incomplete Gamma Function Ratio
// ----------------------------------------------------------------------
// Authors:
//  Amparo Gil    (U. Cantabria, Santander, Spain)
//                 e-mail: amparo.gil@unican.es
//  Javier Segura (U. Cantabria, Santander, Spain)
//                 e-mail: javier.segura@unican.es
//  Nico M. Temme (CWI, Amsterdam, The Netherlands)
//                 e-mail: nico.temme@cwi.nl
// -------------------------------------------------------------
//  References: "Efficient and accurate algorithms for 
//  the computation and inversion of the incomplete incgam_gamma function ratios",    
//  A. Gil, J. Segura and N.M. Temme, SIAM Journal on Scientific Computing 34(6) (2012)
// -------------------------------------------------------------------
// C port : 2014 - Michael Baudin

// PUBLIC   incgam_incgam, incgam_invincgam, incgam_checkincgam

static double lnsqrttwopi=0.9189385332046727418;     // log(sqrt(2*pi))
static double sqrttwopi=2.5066282746310005024;       // sqrt(2*pi)
static double eulmasc=0.5772156649015328606;         // Euler-Mascheroni constant
static double pihalf=1.5707963267948966192;          // pi/2
static double pikwart=0.7853981633974483096;         // pi/4
static double pidriekwart=2.3561944901923449288;     // 3pi/4
static double sqrtpi=1.7724538509055160272;          // sqrt(pi)
static double lnpi=1.1447298858494001741;            // log(pi)
static double pi=3.1415926535897932385;              // pi
static double onepi=.31830988618379067153;           // 1/pi
static double sqrt2=1.4142135623730950488;           // sqrt(2)
static double sqrt3=1.7320508075688772935;           // sqrt(3)
static double sqrt2opi=0.7978845608028653559;        // sqrt(2/pi)
static double twopi=6.2831853071795864769;           // 2*pi
static double oneoversqrtpi=0.5641895835477562869;   // 1/sqrt(pi)
static double twooversqrtpi=1.1283791670955125739;   // 2/sqrt(pi)
static double onethird=0.33333333333333333;          // 1/3
static double twothird=0.6666666666666666666667;     // 2/3
static double onesix=.166666666666666666666666666667;// 1/6 
static double piquart=0.78539816339744830962;        // pi/4
static double twoexp14=1.18920711500272106671749997; // 2**(1/4)

// Floating point parameters
static double epss;                 // demanded accuracy 
static double dwarf;                // safe underflow limit
static double giant;                // safe overflow limit
static double machtol;              // machine-epsilon    
static double inf;                  // INF
static double explow = -300; // TODO : REMOVE
static double exphigh = 300;

static int incgam_startedup=0;

double incgam_sinh(double x, double eps);
double incgam_exmin1(double x, double eps);
double incgam_exmin1minx(double x, double eps);
double incgam_lnec(double x);
double incgam_alfa(double x);
double incgam_dompart(double a, double x, int qt);	
double incgam_chepolsum(int n, double x, double * a);
double incgam_auxloggam(double x);
double incgam_auxgam(double x);
double incgam_lngam1(double x);
double incgam_stirling(double x);
double incgam_gamstar(double x);
double incgam_fractio(double x, int n, double * r, double * s);
double incgam_pqasymp (double a, double x, double dp, int p);
double incgam_saeta(double a, double eta);
double incgam_qfraction(double a, double x, double dp);
double incgam_qtaylor(double a, double x, double dp);
double incgam_ptaylor(double a, double x, double dp);
double incgam_eps1(double eta);
double incgam_eps2(double eta);
double incgam_eps3(double eta);
double incgam_lambdaeta(double eta);
double incgam_invq(double x);
double incgam_inverfc(double x);
double incgam_ratfun(double x, double * ak, double * bk);


void incgam_startup()
{
	double tiny;
	double huge;
	double eps;
	tiny=cdflib_doubleTiny();
	huge=cdflib_doubleHuge();
	eps=cdflib_doubleEps();
	epss=10*eps;                  // demanded accuracy 
	dwarf=tiny*1000.0;            // safe underflow limit
	giant=huge/1000.0;            // safe overflow limit
	machtol=eps;                  // machine-epsilon    
	explow = -300;                // minimum decimal exponent
	exphigh = 300;                // minimum decimal exponent
	inf=cdflib_infinite();
}

void incgam_incgam(double a, double x, double *p, double *q, int *ierr)
{
	double lnx, dp;
	// -------------------------------------------------------------------
	if (x==inf)
	{
		*ierr=0;
		*p=1;
		*q=0;
		return;
	}
	//
	*ierr=0;;
	if (x<dwarf) {
		lnx=log(dwarf);
	} else {
		lnx=log(x);
	}

	if (a>incgam_alfa(x)) {
		dp=incgam_dompart(a,x,INCGAM_FALSE);
		if (dp<0) {
			*ierr=1;
			p=0; 
			q=0;
		} else {
			if ((x < 0.3*a)||(a<12)) {
				*p=incgam_ptaylor(a,x,dp);
			} else {
				*p=incgam_pqasymp(a,x,dp,INCGAM_TRUE);
			}
			*q=1.0-*p;
		}
	} else {
		if (a<-dwarf/lnx) {
			*q=0.0;
		} else {
			if (x<1.0) {
				dp=incgam_dompart(a,x,INCGAM_TRUE);
				if (dp<0) {
					*ierr=1;
					*q=0;
					*p=0;
				} else {
					*q=incgam_qtaylor(a,x,dp);
					*p=1.0-*q;
				}
			} else {
				dp=incgam_dompart(a,x,INCGAM_FALSE);
				if (dp<0) {
					*ierr=1;
					*p=0; 
					*q=0;
				} else {
					if ((x>2.35*a)||(a<12)) {
						*q=incgam_qfraction(a,x,dp);
					} else {
						*q=incgam_pqasymp(a,x,dp,INCGAM_FALSE);
					}
					*p=1.0-*q;
				}
			}
		}
	}
}

void incgam_invincgam(double a, double p, double q, double * xr, int * ierr)
{

	double porq, s, dlnr, logr, r, a2, a3, a4, ap1, ap12, ap13, ap14,
		ap2, ap22, x0, b, eta, L, L2, L3, L4,
		b2, b3, x, x2, t, px, qx, y, fp;
	double ck[5];
	int n, m, ierrf;
	int pcase;
	//
	*ierr=0;
	if (p<0.5) {
		pcase=INCGAM_TRUE;
		porq=p;
		s=-1;
	} else {
		pcase=INCGAM_FALSE;
		porq=q;
		s=1;
	}

	logr=(1.0/a)*(log(p)+incgam_loggam(a+1.0));
	if (logr <log(0.2*(1+a))) {
		r=exp(logr);
		m=0;
		a2=a*a;
		a3=a2*a;
		a4=a3*a;
		ap1=a+1.0;
		ap12=(a+1.0)*ap1;
		ap13=(a+1.0)*ap12;
		ap14=ap12*ap12;
		ap2=a+2;
		ap22=ap2*ap2;
		ck[0]= 1.0;
		ck[1]= 1.0/(1.0+a);
		ck[2]=0.5*(3*a+5)/(ap12*(a+2));
		ck[3]= (1.0/3.0)*(31+8*a2+33*a)/(ap13*ap2*(a+3));
		ck[4]= (1.0/24.0)*(2888+1179*a3+125*a4+3971*a2+5661*a)/(ap14*ap22*(a+3)*(a+4)); 
		x0=r*(1+r*(ck[1]+r*(ck[2]+r*(ck[3]+r*ck[4]))));
	} else if ((q < cdflib_min(0.02,exp(-1.5*a)/incgam_gamma(a)))&&(a<10)) {
		m=0;
		b=1.0-a; 
		b2=b*b;
		b3=b2*b;
		eta=sqrt(-2/a*log(q*incgam_gamstar(a)*sqrttwopi/sqrt(a)));
		x0=a*incgam_lambdaeta(eta); 
		L=log(x0); 
		if ((a>0.12)||(x0>5)) {
			L2=L*L;
			L3=L2*L;
			L4=L3*L;
			r=1.0/x0;
			ck[0]=L-1;
			ck[1]=(3*b-2*b*L+L2-2*L+2)/2.0;
			ck[2]=(24*b*L-11*b2-24*b-6*L2+12*L-12-9*b*L2+6*b2*L+2*L3)/6.0;
			ck[3]=(-12*b3*L+84*b*L2-114*b2*L+72+36*L2+3*L4-72*L+162*b-168*b*L-12*L3+25*b3
				-22*b*L3+36*b2*L2+120*b2)/12.0;
			x0=x0-L+b*r*(ck[0]+r*(ck[1]+r*(ck[2]+r*ck[3])));
		} else {
			r=1.0/x0;
			L2=L*L;
			ck[0]=L-1;
			x0=x0-L+b*r*ck[0];    
		}
	} else if (fabs(porq-0.5)< 1.0e-5) {
		m=0;
		x0=a-1.0/3.0+(8.0/405.0+184.0/25515.0/a)/a;
	} else if (fabs(a-1)<1.0e-4) {
		m=0;
		if (pcase) {
			x0=-log(1.0-p);
		} else {
			x0=-log(q);
		}      
	} else if (a<1.0) {
		m=0;
		if (pcase) { 
			x0=exp((1.0/a)*(log(porq)+incgam_loggam(a+1.0)));
		} else {
			x0=exp((1.0/a)*(log(1.0-porq)+incgam_loggam(a+1.0)));
		}
	} else { 
		m=1;
		r=incgam_inverfc(2*porq);
		eta=s*r/sqrt(a*0.5);
		eta=eta+(incgam_eps1(eta)+(incgam_eps2(eta)+incgam_eps3(eta)/a)/a)/a;
		x0=a*incgam_lambdaeta(eta);
	}
	t=1;
	x=x0; 
	n=1;
	a2=a*a;
	a3=a2*a;
	// Implementation of the high order Newton-like method
	while ((t>1.0e-15)&&(n< 15)) {
		x=x0;
		x2=x*x;
		if (m==0) {
			dlnr=(1.0-a)*log(x)+x+incgam_loggam(a);
			if (dlnr>log(giant)) {
				n=20;
				*ierr=-1;
			} else {
				r=exp(dlnr);
				if (pcase) { 
					incgam_incgam(a,x,&px,&qx,&ierrf);
					ck[0]=-r*(px-p);  
				} else {
					incgam_incgam(a,x,&px,&qx,&ierrf);
					ck[0]=r*(qx-q);
				}
				ck[1]=(x-a+1.0)/(2.0*x);
				ck[2]=(2*x2-4*x*a+4*x+2*a2-3*a+1)/(6*x2);
				r=ck[0];
				if (a>0.1) {
					x0=x+r*(1+r*(ck[1]+r*ck[2]));
				} else {  
					if (a>0.05) {
						x0=x+r*(1+r*(ck[1]));
					} else {
						x0=x+r;
					}
				}
			}
		} else {
			y=eta;
			fp=-sqrt(a/twopi)*exp(-0.5*a*y*y)/(incgam_gamstar(a));
			r=-(1/fp)*x;
			if (pcase) { 
				incgam_incgam(a,x,&px,&qx,&ierrf);
				ck[0]=-r*(px-p);  
			} else {
				incgam_incgam(a,x,&px,&qx,&ierrf);
				ck[0]=r*(qx-q);
			}  
			ck[1]=(x-a+1.0)/(2.0*x);
			ck[2]=(2*x2-4*x*a+4*x+2*a2-3*a+1)/(6*x2);
			r=ck[0];
			if (a>0.1) {
				x0=x+r*(1+r*(ck[1]+r*ck[2]));
			} else {  
				if (a>0.05) {
					x0=x+r*(1+r*(ck[1]));
				} else {
					x0=x+r;
				}
			}
		}
		t=fabs(x/x0-1.0);
		n=n+1; 
		x=x0;
	}
	if (n==15) {
		*ierr=-2;
	}
	*xr=x;
}


double incgam_sinh(double x, double eps)
{
	// to compute hyperbolic function incgam_sinh (x)}
	double ax, e, t, x2, y;
	int u, k;
	ax=fabs(x);
	if (x==0) {
		y=0;
	} else if (ax<0.12) {
		e=eps/10.0;
		x2=x*x;
		y=1;
		t=1;
		u=0;
		k=1;
		while(t>e) {
			u=u+8*k-2;
			k=k+1;
			t=t*x2/u;
			y=y+t;
		}
		y=x*y;
	} else if (ax<0.36) {
		t=incgam_sinh(x/3.0,eps);
		y=t*(3+4*t*t);
	} else {
		t=exp(x);
		y=(t-1.0/t)/2.0;
	}
	return y;
}


double incgam_exmin1(double x, double eps)
{
	// computes (exp(x)-1)/x 
	double t, y;
	if (x==0) {
		y=1.0;
	} else if ((x<-0.69)||(x > 0.4)) {
		y=(exp(x)-1.0)/x;
	} else {
		t=x/2.0;
		y=exp(t)*incgam_sinh(t,eps)/t;
	}
	return y;
}

double incgam_exmin1minx(double x, double eps)
{
	// computes (exp(x)-1-x)/(0.5*x*x) 
	double t, t2, y;
	if (x==0) {
		y=1.0;
	} else if (fabs(x)>0.9) {
		y=(exp(x)-1-x)/(x*x/2.0);
	} else {
		t=incgam_sinh(x/2.0,eps);
		t2=t*t;
		y=(2*t2+(2*t*sqrt(1.0+t2)-x))/(x*x/2.0);
	}
	return y;
}

double incgam_lnec(double x)
{
	double ln1, y0, z, e2, r, s;
	// x>-1; incgam_lnec:=ln1:=ln(1+x)-x
	z=cdflib_log1p(x);
	y0=z-x;
	e2=incgam_exmin1minx(z,machtol);
	s=e2*z*z/2;
	r=(s+y0)/(s+1+z);
	ln1=y0-r*(6-r)/(6-4*r);
	// incgam_lnec := x + ln1
	return ln1;
}

double incgam_alfa(double x)
{
    double lnx;
	double y;
    lnx=log(x);
    if (x>0.25) {
      y=x+0.25;
    } else if (x>=dwarf) {
      y=-0.6931/lnx;
    } else {
      y=-0.6931/log(dwarf);
	}
	return y;
}

double incgam_dompart(double a, double x, int qt)
{
	// incgam_dompart is approx. of  x^a * exp(-x) / incgam_gamma(a+1)   
	double lnx, c, dp, la, mu, r;
	double y;
	lnx=log(x);
	if (a<=1) {                     
		r=-x+a*lnx;
	} else {
		if (x==a) {
			r=0;
		} else {
			la=x/a;
			r=a*(1.0-la+log(la));
		}
		r=r-0.5*log(6.2832*a);
	}
	if (r<explow) {
		dp=0.0;
	} else {
		dp=exp(r);
	}
	if (qt) {
		y=dp;
	} else {
		if ((a<3)||(x<0.2)) {
			y=exp(a*lnx-x)/incgam_gamma(a+1.0);
		} else {
			mu=(x-a)/a;
			c=incgam_lnec(mu);
			if ((a*c)>log(giant)) {
				y=-100;
			} else {
				y=exp(a*c)/(sqrt(a*2*pi)*incgam_gamstar(a));
			}
		}
	}
	return y;
}



double incgam_chepolsum(int n, double x, double * a)
{
	// a : array(0:n)
	double  incgam_chepolsum;
	double  h, r, s, tx;
	int  k;
	// a[0]/2+a[1]T1(x)+...a[n]Tn(x); series of Chebychev polynomials
	if (n==0) {
		incgam_chepolsum=a[0]/2.0;
	} else if (n==1) {
		incgam_chepolsum=a[0]/2.0+a[1]*x;
	} else {
		tx=x+x;
		r=a[n];
		h=a[n-1]+r*tx;
		for (k=n-2; k>=1; k=k-1)
		{
			s=r;
			r=h;
			h=a[k]+r*tx-s;
		}
		incgam_chepolsum=a[0]/2.0-r+h*x;
	}
	return incgam_chepolsum;
}

double incgam_auxloggam(double x)
{
	// function g in ln(Gamma(1+x))=x*(1-x)*g(x), 0<=x<=1
	double  auxloggamm;
	double ak[26];
	double  g, t;
	if (x<-1) { 
		g=giant;
	} else if (fabs(x)<=dwarf) { 
		g=-eulmasc;
	} else if (fabs(x - 1)<=machtol) {
		g=eulmasc-1.0;
	} else if (x<0) {
		g=-(x*(1+x)*incgam_auxloggam(x+1.0)+cdflib_log1p(x))/(x*(1.0-x));
	} else if (x<1) {
		ak[0]=-0.98283078605877425496;
		ak[1]=0.7611416167043584304e-1;
		ak[2]=-0.843232496593277796e-2;
		ak[3]=0.107949372632860815e-2;
		ak[4]=-0.14900748003692965e-3;
		ak[5]=0.2151239988855679e-4;
		ak[6]=-0.319793298608622e-5;
		ak[7]=0.48516930121399e-6;
		ak[8]=-0.7471487821163e-7;
		ak[9]=0.1163829670017e-7;
		ak[10]=-0.182940043712e-8;
		ak[11]= 0.28969180607e-9;
		ak[12]=-0.4615701406e-10;
		ak[13]= 0.739281023e-11;
		ak[14]= -0.118942800e-11;
		ak[15]= 0.19212069e-12;
		ak[16]= -0.3113976e-13;
		ak[17]= 0.506284e-14;
		ak[18]= -0.82542e-15;
		ak[19]= 0.13491e-15;
		ak[20]= -0.2210e-16;
		ak[21]= 0.363e-17;
		ak[22]= -0.60e-18;
		ak[23]= 0.98e-19;
		ak[24]= -0.2e-19;
		ak[25]= 0.3e-20;
		t=2*x-1;
		g=incgam_chepolsum(25, t, ak);
	} else if (x<1.5) {
		g=(cdflib_log1p(x-1.0) + (x-1.0)*(2.0-x)*incgam_auxloggam(x-1.0))/(x*(1.0-x));
	} else {
		g=(log(x)+(x-1.0)*(2.0-x)*incgam_auxloggam(x-1.0))/(x*(1.0-x));
	}
	auxloggamm= g;
	return auxloggamm;
}

double incgam_loggam(double x)
{
	double  incgam_loggam;
	if (x>=3) {
		incgam_loggam=(x-0.5)*log(x)- x+lnsqrttwopi+incgam_stirling(x);
	} else if (x >= 2) {
		incgam_loggam=(x-2)*(3-x)*incgam_auxloggam(x-2.0)+cdflib_log1p(x-2.0);
	} else if (x>=1) {
		incgam_loggam=(x-1.0)*(2.0-x)*incgam_auxloggam(x-1.0);
	} else if (x>0.5) {
		incgam_loggam=x*(1.0-x)*incgam_auxloggam(x)-cdflib_log1p(x-1.0);
	} else if (x>0) {
		incgam_loggam=x*(1-x)*incgam_auxloggam(x)-log(x);
	} else {
		incgam_loggam=giant;
	}
	return incgam_loggam;
}

double incgam_auxgam(double x)
{
	// function g in 1/incgam_gamma(x+1)=1+x*(x-1)*g(x), -1<=x<=1
	double  auxgamm;
	double  t;
	double dr[18];
	if (x<0) {
		auxgamm=-(1.0+(1+x)*(1+x)*incgam_auxgam(1+x))/(1.0-x);
	} else {
		dr[0]= -1.013609258009865776949;
		dr[1]= 0.784903531024782283535e-1;
		dr[2]= 0.67588668743258315530e-2;
		dr[3]= -0.12790434869623468120e-2;
		dr[4]= 0.462939838642739585e-4;
		dr[5]= 0.43381681744740352e-5;
		dr[6]= -0.5326872422618006e-6;
		dr[7]= 0.172233457410539e-7;
		dr[8]= 0.8300542107118e-9;
		dr[9]= -0.10553994239968e-9;
		dr[10]= 0.39415842851e-11;
		dr[11]= 0.362068537e-13;
		dr[12]= -0.107440229e-13;
		dr[13]= 0.5000413e-15;
		dr[14]= -0.62452e-17;
		dr[15]= -0.5185e-18;
		dr[16]= 0.347e-19;
		dr[17]= -0.9e-21;
		t=2*x-1.0;
		auxgamm=incgam_chepolsum(17,t,dr);
	}
	return auxgamm;
}

double incgam_lngam1(double x)
{
	double incgam_lngam1;
	// ln(incgam_gamma(1+x)), -1<=x<=1
	incgam_lngam1=-cdflib_log1p(x*(x-1.0)*incgam_auxgam(x));
	return incgam_lngam1;
}

double incgam_stirling(double x)
{
	// Stirling series, function corresponding with
	// asymptotic series for log(incgam_gamma(x))
	// that is:  1/(12x)-1/(360x**3)...; x>= 3

	double  incgam_stirling;
	double a[18];
	double c[7];
	double z;
	if (x<dwarf) {
		incgam_stirling=giant;
	} else if (x<1) {
		incgam_stirling= incgam_lngam1(x)-(x+0.5)*log(x)+x-lnsqrttwopi;
	} else if (x<2) {
		incgam_stirling=incgam_lngam1(x-1)-(x-0.5)*log(x)+x-lnsqrttwopi;
	} else if (x<3) {
		incgam_stirling=incgam_lngam1(x-2)-(x-0.5)*log(x)+x-lnsqrttwopi+log(x-1);
	} else if (x<12) {
		a[0]=1.996379051590076518221;
		a[1]=-0.17971032528832887213e-2;
		a[2]=0.131292857963846713e-4;
		a[3]=-0.2340875228178749e-6;
		a[4]=0.72291210671127e-8;
		a[5]=-0.3280997607821e-9;
		a[6]=0.198750709010e-10;
		a[7]=-0.15092141830e-11;
		a[8]=0.1375340084e-12;
		a[9]=-0.145728923e-13;
		a[10]=0.17532367e-14;
		a[11]=-0.2351465e-15;
		a[12]=0.346551e-16;
		a[13]=-0.55471e-17;
		a[14]=0.9548e-18;
		a[15]=-0.1748e-18;
		a[16]=0.332e-19;
		a[17]=-0.58e-20;
		z=18.0/(x*x)-1.0;
		incgam_stirling=incgam_chepolsum(17,z,a)/(12.0*x);
	} else {
		z=1.0/(x*x);
		if (x<1000) {
			c[0]=0.25721014990011306473e-1;
			c[1]=0.82475966166999631057e-1;
			c[2]=-0.25328157302663562668e-2;
			c[3]=0.60992926669463371e-3;
			c[4]=-0.33543297638406e-3;
			c[5]=0.250505279903e-3;
			c[6]=0.30865217988013567769;
			incgam_stirling=((((((c[5]*z+c[4])*z+c[3])*z+c[2])*z+c[1])*z+c[0])/(c[6]+z)/x);
		} else {
			incgam_stirling=(((-z/1680.0+1.0/1260.0)*z-1.0/360.0)*z+1.0/12.0)/x;
		}
	} 
	return incgam_stirling;
}

double incgam_gamma(double x)
{
	double  dw, gam, z;
	int  k, k1, n;
	// Euler incgam_gamma function Gamma(x), x real
	k=cdflib_nearestint(x);
	k1=k-1;
	if (k==0) {
		dw=dwarf;
	} else {
		dw=machtol;
	}
	if ((k <= 0)&&(fabs(k - x)<= dw)) {
		if (k%2>0) {
			// k is odd
			gam=cdflib_dsign(1.0,k-x)*giant;
		} else {
			// k is even
			gam=cdflib_dsign(1.0,x-k)*giant;
		}
	} else if (x<0.45) {
		gam=pi/(sin(pi*x)*incgam_gamma(1-x));
	} else if ((fabs(k-x)<dw)&&(x<21)) {
		gam=1;
		for (n=2; n<=k1; n=n+1)
		{
			gam=gam*n;
		}
	} else if ((fabs(k-x-0.5)<dw)&&(x<21)) {
		gam=sqrt(pi);
		for (n=1; n<=k1; n=n+1)
		{
			gam=gam*(n-0.5);
		}
	} else if (x<3) {
		if (k>x) {
			k=k1;
		}
		k1=3-k;
		z=k1+x;
		gam=incgam_gamma(z);
		for (n=1; n<=k1; n=n+1)
		{
			gam=gam/(z-n);
		}
	} else {
		gam=sqrttwopi*exp(-x+(x-0.5)*log(x)+incgam_stirling(x));
	}
	return gam;
}

double incgam_gamstar(double x) 
{   
	// incgam_gamstar(x)=exp(incgam_stirling(x)), x>0; or 
	// incgam_gamma(x)/(exp(-x+(x-0.5)*ln(x))/sqrt(2pi)

	double  incgam_gamstar;
	if (x>=3) {
		incgam_gamstar=exp(incgam_stirling(x));
	} else if (x>0) {
		incgam_gamstar=incgam_gamma(x)/(exp(-x+(x-0.5)*log(x))*sqrttwopi);
	} else {
		incgam_gamstar=giant;
	}
	return incgam_gamstar;
}

double incgam_errorfunction (double x, int erfcc, int expo)
{
    // coefficients are from Cody (1969), Math. Comp., 23, 631-637
    double y, z;
	double r[9];
    double s[9];
	double errfu;
	if (erfcc) {
		if (x < -6.5) {
			y= 2.0;
		} else if (x < 0) {
			y= 2.0 - incgam_errorfunction(-x, INCGAM_TRUE, INCGAM_FALSE);
		} else if (x == 0) {
			y= 1.0;
		} else if (x < 0.5) {
			if (expo) {
				y=exp(x*x);
			} else {
				y=1.0;
			}
			y=y*(1.0 - incgam_errorfunction(x, INCGAM_FALSE, INCGAM_FALSE));
		} else if (x < 4) {
			if (expo) {
				y= 1.0;
			} else {
				y= exp(-x*x);
			}
			r[0]= 1.230339354797997253e3;
			r[1]= 2.051078377826071465e3;
			r[2]= 1.712047612634070583e3;
			r[3]= 8.819522212417690904e2;
			r[4]= 2.986351381974001311e2;
			r[5]= 6.611919063714162948e1;
			r[6]= 8.883149794388375941;
			r[7]= 5.641884969886700892e-1;
			r[8]= 2.153115354744038463e-8;
			s[0]= 1.230339354803749420e3;
			s[1]= 3.439367674143721637e3;
			s[2]= 4.362619090143247158e3;
			s[3]= 3.290799235733459627e3;
			s[4]= 1.621389574566690189e3;
			s[5]= 5.371811018620098575e2;
			s[6]= 1.176939508913124993e2;
			s[7]= 1.574492611070983473e1;
			y=y*incgam_fractio(x,8,r,s);
		} else {
			z=x*x;
			if (expo) {
				y=1.0;
			} else {
				y= exp(-z);
			}
			z=1.0/z;
			r[0]=6.587491615298378032e-4;
			r[1]=1.608378514874227663e-2;
			r[2]=1.257817261112292462e-1;
			r[3]=3.603448999498044394e-1;
			r[4]=3.053266349612323440e-1;
			r[5]=1.631538713730209785e-2;
			s[0]=2.335204976268691854e-3;
			s[1]=6.051834131244131912e-2;
			s[2]=5.279051029514284122e-1;
			s[3]=1.872952849923460472;
			s[4]=2.568520192289822421;
			y=y*(oneoversqrtpi-z*incgam_fractio(z,5,r,s))/x;
		}
		errfu=y;
	} else {
		if (x == 0.0) { 
			y=0;
		} else if (fabs(x) > 6.5) { 
			y=x/fabs(x);
		} else if (x > 0.5) {
			y=1.0 - incgam_errorfunction(x, INCGAM_TRUE, INCGAM_FALSE);
		} else if (x < -0.5) {
			y=incgam_errorfunction(-x, INCGAM_TRUE, INCGAM_FALSE)-1.0;
		} else {
			r[0]=3.209377589138469473e3;
			r[1]=3.774852376853020208e2;
			r[2]=1.138641541510501556e2;
			r[3]=3.161123743870565597e0;
			r[4]=1.857777061846031527e-1;
			s[0]=2.844236833439170622e3;
			s[1]=1.282616526077372276e3;
			s[2]=2.440246379344441733e2;
			s[3]=2.360129095234412093e1;
			z=x*x;
			y=x*incgam_fractio(z,4,r,s);
		}  
		errfu= y;
	}
	return errfu;
}

double incgam_fractio(double x, int n, double * r, double * s)
{    
	// r(0:8), s(0:8)
    int k;
    double incgam_fractio;
	double a, b;
    a=r[n]; 
	b=1;
    for (k=n-1; k>=0; k=k-1)
	{
      a=a*x+r[k];
	  b=b*x+s[k];
    }
    incgam_fractio=a/b;
	return incgam_fractio;
}

double incgam_pqasymp (double a, double x, double dp, int p)
{
	double  incgam_pqasymp;
	double  y, mu, eta, u, v;
	int  s;
	if (dp==0.0) {
		if (p) {
			incgam_pqasymp=0.0;
		} else {
			incgam_pqasymp=1.0;
		}
	} else {
		if (p) {
			s=-1;
		} else {
			s=1;
		}
		mu=(x-a)/a;
		y=-incgam_lnec(mu);
		if (y<0) {
			eta=0.0;
		} else {
			eta=sqrt(2.0*y);
		}
		y=y*a;
		v=sqrt(fabs(y));
		if (mu<0.0) {     
			eta=-eta;
			v=-v;
		}  
		u=0.5*incgam_errorfunction(s*v,INCGAM_TRUE,INCGAM_FALSE);
		v=s*exp(-y)*incgam_saeta(a,eta)/sqrt(2.0*pi*a);
		incgam_pqasymp=u+v;
	}
	return incgam_pqasymp;
}
                    
double incgam_saeta(double a, double eta)
{
	double incgam_saeta, y, s, t;
	double  eps;
	double fm[27];
	double bm[27];
	int    m;
	eps=epss;
	fm[0]=1.0;
	fm[1]=-1.0/3.0;
	fm[2]=1.0/12.0;
	fm[3]=-2.0/135.0;
	fm[4]=1.0/864.0;
	fm[5]=1.0/ 2835.0;
	fm[6]=-139.0/777600.0;
	fm[7]=1.0/25515.0;
	fm[8]=-571.0/261273600.0;
	fm[9]=-281.0/151559100.0;
	fm[10]=8.29671134095308601e-7;
	fm[11]=-1.76659527368260793e-7;
	fm[12]=6.70785354340149857e-9;
	fm[13]=1.02618097842403080e-8;
	fm[14]=-4.38203601845335319e-9;
	fm[15]=9.14769958223679023e-10;
	fm[16]=-2.55141939949462497e-11;
	fm[17]=-5.83077213255042507e-11;
	fm[18]=2.43619480206674162e-11;
	fm[19]=-5.02766928011417559e-12;
	fm[20]=1.10043920319561347e-13;
	fm[21]=3.37176326240098538e-13;
	fm[22]=-1.39238872241816207e-13;
	fm[23]=2.85348938070474432e-14;
	fm[24]=-5.13911183424257258e-16;
	fm[25]=-1.97522882943494428e-15;
	fm[26]= 8.09952115670456133e-16;
	bm[25]=fm[26];
	bm[24]=fm[25];
	for (m=24; m>=1; m=m-1)
	{
		bm[m-1]=fm[m]+(m+1)*bm[m+1]/a;
	}
	s=bm[0];
	t=s;
	y=eta;
	m=1;
	while ((fabs(t/s)>eps)&&(m<25))
	{
		t=bm[m]*y;
		s=s+t;
		m=m+1;
		y=y*eta;
	} 
	incgam_saeta=s/(1.0+bm[1]/a);
	return incgam_saeta;
}

double incgam_qfraction(double a, double x, double dp)
{
	double  incgam_qfraction;
	double  eps, g, p, q, r, s, t, tau, ro;
	eps=epss;
	if (dp==0) {
		q=0.0;
	} else {
		p=0;
		q=(x-1.0-a)*(x+1.0-a);
		r=4*(x+1.0-a);
		s=1.0-a;
		ro=0.0;
		t=1.0;
		g=1.0;
		while(fabs(t/g)>=eps)
		{
			p=p+s;
			q=q+r;
			r=r+8;
			s=s+2;
			tau=p*(1.0+ro);
			ro=tau/(q-tau);
			t=ro*t;
			g=g+t;
		}
		q=(a/(x+1.0-a))*g*dp; 
	}
	incgam_qfraction= q;
	return incgam_qfraction;
}

double incgam_qtaylor(double a, double x, double dp)
{
	double  incgam_qtaylor;
	double  eps, lnx, p, q, r, s, t, u, v;
	eps=epss;
	lnx=log(x);
	if (dp==0) {
		q=0.0;
	} else {
		r=a*lnx;
		q=r*incgam_exmin1(r,eps);   // {q = x^a - 1 }
		s=a*(1.0-a)*incgam_auxgam(a); // {s = 1-1/Gamma(1+a) }
		q=(1-s)*q;
		u=s-q;               // {u = 1 - x^a/Gamma(1+a)}
		p=a*x;
		q=a+1;
		r=a+3;
		t=1.0;
		v=1.0;
		while (fabs(t / v) > eps)
		{
			p=p+x;
			q=q+r;
			r=r+2;
			t=-p*t/q;
			v=v+t;
		}
		v=a*(1-s)*exp((a+1.0)*lnx)*v/(a+1.0);
		q=u+v;
	}
	incgam_qtaylor=q;
	return incgam_qtaylor;
}

double incgam_ptaylor(double a, double x, double dp)
{
	double  incgam_ptaylor;
	double  eps,p,c,r;
	eps=epss;
	if (dp==0) {
		p=0.0;
	} else {
		p=1.0;
		c=1.0;
		r=a;
		while ((c/p)>eps)
		{
			r=r+1;
			c=x*c/r;
			p=p+c;
		}
		p=p*dp;
	}
	incgam_ptaylor=p;
	return incgam_ptaylor;
}

double incgam_checkincgam(double a, double x)
{
	double  incgam_checkincgam;
	double  mu, d2, p, q, p1, q1, r, s;
	int  ierr1,ierr2;
	//
	incgam_incgam(a+1,x,&p1,&q1,&ierr1);
	incgam_incgam(a,x,&p,&q,&ierr2);
	if (a < 4) {
		d2=exp(a*log(x)-x)/incgam_gamma(a + 1.0) ; 
	} else {
		mu=(x-a)/a;
		r=incgam_lnec(mu);
		s=a*r;
		if (s>explow) {
			d2=exp(s)/(sqrt(a*2*pi)*incgam_gamstar(a));
		} else {
			d2= 0;
		}
	}
	if (d2>0) {
		if (x>a) {
			incgam_checkincgam=q1/(q+d2)-1.0;
		} else {
			incgam_checkincgam=(p1+d2)/p-1.0;
		}
	} else {
		incgam_checkincgam=0;
	}
	return incgam_checkincgam;
}


double incgam_eps1(double eta)
{
	double  incgam_eps1, la;
	double ak[5];
	double bk[5];
	if (fabs(eta)<1.0) {
		ak[0]=-3.333333333438e-1;  bk[0]= 1.000000000000e+0;     
		ak[1]=-2.070740359969e-1;  bk[1]= 7.045554412463e-1;     
		ak[2]=-5.041806657154e-2;  bk[2]= 2.118190062224e-1;     
		ak[3]=-4.923635739372e-3;  bk[3]= 3.048648397436e-2;     
		ak[4]=-4.293658292782e-5;  bk[4]= 1.605037988091e-3;     
		incgam_eps1=incgam_ratfun(eta,ak,bk);
	} else {
		la=incgam_lambdaeta(eta);
		incgam_eps1=log(eta/(la-1.0))/eta;
	}
	return incgam_eps1;
}

double incgam_eps2(double eta)
{
	double  incgam_eps2, x, lnmeta;
	double ak[5];
	double bk[5];
	if (eta < -5.0) {
		x=eta*eta;
		lnmeta=log(-eta);
		incgam_eps2=(12.0-x-6.0*(lnmeta*lnmeta))/(12.0*x*eta);
	} else if (eta<-2.0) {
		ak[0]=-1.72847633523e-2;  bk[0]=1.00000000000e+0;     
		ak[1]= -1.59372646475e-2;  bk[1]= 7.64050615669e-1;     
		ak[2]= -4.64910887221e-3;  bk[2]= 2.97143406325e-1;     
		ak[3]= -6.06834887760e-4;  bk[3]= 5.79490176079e-2;     
		ak[4]= -6.14830384279e-6;  bk[4]= 5.74558524851e-3;     
		incgam_eps2= incgam_ratfun(eta,ak,bk);
	} else if (eta < 2.0) {
		ak[0]=-1.72839517431e-2;  bk[0]= 1.00000000000e+0;     
		ak[1]=-1.46362417966e-2;  bk[1]= 6.90560400696e-1;     
		ak[2]=-3.57406772616e-3;  bk[2]= 2.49962384741e-1;     
		ak[3]=-3.91032032692e-4;  bk[3]= 4.43843438769e-2;     
		ak[4]=2.49634036069e-6;   bk[4]= 4.24073217211e-3;     
		incgam_eps2= incgam_ratfun(eta,ak,bk);
	} else if (eta < 1000.0) {
		ak[0]= 9.99944669480e-1;  bk[0]= 1.00000000000e+0;     
		ak[1]= 1.04649839762e+2;  bk[1]= 1.04526456943e+2;     
		ak[2]= 8.57204033806e+2;  bk[2]= 8.23313447808e+2;     
		ak[3]= 7.31901559577e+2;  bk[3]= 3.11993802124e+3;     
		ak[4]= 4.55174411671e+1;  bk[4]= 3.97003311219e+3;     
		x=1.0/eta;
		incgam_eps2=incgam_ratfun(x,ak,bk)/(-12.0*eta);
	} else {
		incgam_eps2=-1.0/(12.0*eta);
	}
	return incgam_eps2;
}

double incgam_eps3(double eta)
{
	double incgam_eps3, eta3, x, y;
	double ak[5];
	double bk[5];
	if (eta <-8.0) {
		x=eta*eta;
		y=log(-eta)/eta;
		incgam_eps3=(-30.0+eta*y*(6.0*x*y*y-12.0+x))/(12.0*eta*x*x);
	} else if (eta <-4.0) {
		ak[0]= 4.95346498136e-2;  bk[0]= 1.00000000000e+0;     
		ak[1]= 2.99521337141e-2;  bk[1]= 7.59803615283e-1;     
		ak[2]= 6.88296911516e-3;  bk[2]= 2.61547111595e-1;     
		ak[3]= 5.12634846317e-4;  bk[3]= 4.64854522477e-2;     
		ak[4]= -2.01411722031e-5; bk[4]= 4.03751193496e-3;     
		incgam_eps3=incgam_ratfun(eta,ak,bk)/(eta*eta);
	} else if (eta <-2.0) {
		ak[0]=4.52313583942e-3;  bk[0]= 1.00000000000e+0;     
		ak[1]=1.20744920113e-3;  bk[1]= 9.12203410349e-1;     
		ak[2]=-7.89724156582e-5; bk[2]= 4.05368773071e-1;     
		ak[3]=-5.04476066942e-5; bk[3]= 9.01638932349e-2;     
		ak[4]=-5.35770949796e-6; bk[4]= 9.48935714996e-3;     
		incgam_eps3=incgam_ratfun(eta,ak,bk);
	} else if  (eta < 2.0) {
		ak[0]= 4.39937562904e-3;  bk[0]= 1.00000000000e+0;     
		ak[1]= 4.87225670639e-4;  bk[1]= 7.94435257415e-1;     
		ak[2]= -1.28470657374e-4; bk[2]= 3.33094721709e-1;     
		ak[3]= 5.29110969589e-6;  bk[3]= 7.03527806143e-2;     
		ak[4]= 1.57166771750e-7;  bk[4]= 8.06110846078e-3;     
		incgam_eps3= incgam_ratfun(eta,ak,bk);
	} else if (eta < 10.0) {
		ak[0]= -1.14811912320e-3;  bk[0]= 1.00000000000e+0;     
		ak[1]= -1.12850923276e-1;  bk[1]= 1.42482206905e+1;     
		ak[2]= 1.51623048511e+0;   bk[2]= 6.97360396285e+1;     
		ak[3]= -2.18472031183e-1;  bk[3]= 2.18938950816e+2;     
		ak[4]= 7.30002451555e-2;   bk[4]= 2.77067027185e+2;     
		x= 1.0/eta;
		incgam_eps3= incgam_ratfun(x,ak,bk)/(eta*eta);
	} else if (eta < 100.0) {
		ak[0]= -1.45727889667e-4;  bk[0]= 1.00000000000e+0;     
		ak[1]= -2.90806748131e-1;  bk[1]= 1.39612587808e+2;     
		ak[2]= -1.33085045450e+1;  bk[2]= 2.18901116348e+3;     
		ak[3]= 1.99722374056e+2;   bk[3]= 7.11524019009e+3;     
		ak[4]= -1.14311378756e+1;  bk[4]= 4.55746081453e+4;     
		x= 1.0/eta;
		incgam_eps3= incgam_ratfun(x,ak,bk)/(eta*eta);
	} else {
		eta3=eta*eta*eta;
		incgam_eps3=-log(eta)/(12.0*eta3);
	}
	return incgam_eps3;
}

double incgam_lambdaeta(double eta)
{
	// incgam_lambdaeta is the positive number satisfying 
	// eta^2/2=lambda-1-ln(lambda)
	// with sign(lambda-1)=sign(eta); 
	double  incgam_lambdaeta, q, r, s, L, la;
	double ak[7]; 
	double  L2, L3, L4, L5;
	s=eta*eta*0.5;
	if (eta==0) {
		la=1;
	} else if (eta < -1) { 
		r=exp(-1-s);
		ak[0]=1.0;
		ak[1]=1.0;
		ak[2]=3.0/2.0;
		ak[3]=8.0/3.0;
		ak[4]=125.0/24.0;
		ak[5]=54.0/5.0;
		la=r*(ak[0]+r*(ak[1]+r*(ak[2]+r*(ak[3]+r*(ak[4]+r*ak[5])))));
	} else if (eta<1) { 
		ak[0]= 1.0;
		ak[1]= 1.0/3.0;
		ak[2]=1.0/36.0;
		ak[3]= -1.0/270.0;
		ak[4]= 1.0/4320.0;
		ak[5]= 1.0/17010.0;
		r=eta;
		la=1+r*(ak[0]+r*(ak[1]+r*(ak[2]+r*(ak[3]+r*(ak[4]+r*ak[5])))));
	} else {
		r=11+s; L=log(r); la=r+L; r=1.0/r;
		L2=L*L;
		L3=L2*L;
		L4=L3*L;
		L5=L4*L;
		ak[0]= 1;
		ak[1]=(2-L)*0.5;
		ak[2]=(-9*L+6+2*L2)/6.0;
		ak[3]= -(3*L3+36*L-22*L2-12)/12.0;
		ak[4]=(60+350*L2-300*L-125*L3+12*L4)/60.0;
		ak[5]=-(-120-274*L4+900*L-1700*L2+1125*L3+20*L5)/120.0;
		la=la+L*r*(ak[0]+r*(ak[1]+r*(ak[2]+r*(ak[3]+r*(ak[4]+r*ak[5])))));
	}
	r= 1;
	if (((eta>-3.5)&&(eta<-0.03))||((eta>0.03)&&(eta<40))) {
		r=1; 
		q=la;
		while (r > 1.0e-8)
		{
			la=q*(s+log(q))/(q-1.0);
			r= fabs(q/la-1); 
			q= la;
		}
	}
	incgam_lambdaeta=la;
	return incgam_lambdaeta;
}

double incgam_invq(double x)
{
	double  incgam_invq, t;
	//  Abramowitx & Stegun 26.2.23; 
	t=sqrt(-2*log(x));
	t=t-(2.515517+t*(0.802853+t*0.010328))/(1.0+t*(1.432788+t*(0.189269+t*0.001308)));
	incgam_invq=t;
	return incgam_invq;
}

double incgam_inverfc(double x)
{
	double   y, y0, y02, h, r, f, fp, c1, c2, c3, c4, c5;
	if (x > 1) {
		y=-incgam_inverfc(2-x);
	} else {
		y0=0.70710678*incgam_invq(x/2.0);
		f= cdflib_erfc(y0)-x;
		f=incgam_errorfunction(y0,INCGAM_TRUE,INCGAM_FALSE)-x;
		y02= y0*y0;
		fp=-2.0/sqrt(pi)*exp(-y02);
		c1=-1.0/fp;
		c2= y0;
		c3=(4*y02+1)/3.0;
		c4=y0*(12*y02+7)/6.0;
		c5=(8*y02+7)*(12*y02+1)/30.0;
		r= f*c1;
		h=r*(1+r*(c2+r*(c3+r*(c4+r*c5))));
		y=y0+h;
	}
	return y;
}

double incgam_ratfun(double x, double * ak, double * bk)
{
	// Evaluates p/q, 
	// where p=ak[0]+x*ak[1]+x**2*ak[2]+x**3*ak[3]+x**4*ak[4]
	// and   q=bk[0]+x*bk[1]+x**2*bk[2]+x**3*bk[3]+x**4*bk[4]
	double  incgam_ratfun;
	// ak[0:4), bk[0:4)
	double p, q;
	p= ak[0]+x*(ak[1]+x*(ak[2]+x*(ak[3]+x*ak[4])));
	q= bk[0]+x*(bk[1]+x*(bk[2]+x*(bk[3]+x*bk[4])));
	incgam_ratfun=p/q;
	return incgam_ratfun;
}

void incgam_testincgam()
{
	// -------------------------------------------------------
	// This is a test program for the module incgam. 
	// ------------------------------------------------------- 
	double a, x, p, q, xr, delta, d0, erxr;
	int ierr,ierri,i,j;
	double check;
	// ----------------------------------------------------
	// 1) Test of the incgam routine: the function 
	//    checkincgam checks the relative accuracy 
	//    in the recursions 
	//       Q(a+1,x)=Q(a,x)+x^a*exp(-x)/Gamma(a+1)
	//       P(a+1,x)=P(a,x)-x^a*exp(-x)/Gamma(a+1)
	// ----------------------------------------------------
	d0=-1; 
	for (i=1; i<=100; i=i+1) {
		a=i*0.1;
		for (j=1; j<=100; j=j+1) {
			x=j*0.1;
			check=incgam_checkincgam(a,x);
			delta=fabs(check); 
			if (delta>d0) {
				d0=delta;
			}
		}
	} 
	printf("Max. absolute error (direct computation): %e\n",d0);
	// ----------------------------------------------------
	// 2) Test of the invincgam routine: the composition of
	//   the functions with their inverse is the identity.
	// ----------------------------------------------------
	d0=-1; 
	for (i=1; i<=100; i=i+1) {
		a=i*0.1;
		for (j=1; j<=100; j=j+1) {
			x=j*0.1;
			incgam_incgam(a,x,&p,&q,&ierr);
			incgam_invincgam(a,p,q,&xr,&ierri);
			erxr=fabs(1.0-x/xr);
			if (erxr>d0) {
				d0=erxr;
			}
		}
	}
	printf("Max. relative error (inversion): %e\n",d0);
}

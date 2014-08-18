//
// This help file was automatically generated from specfun_lambertw.sci using help_from_sci().
// PLEASE DO NOT EDIT
//
mode(1)
//
// Demo of specfun_lambertw.sci
//

c = specfun_lambertw([0 -exp(-1); %pi 1])
e = [
0                     -1.
1.07365819479614921    0.56714329040978384
]
halt()   // Press return to continue
 
// Compute Wk(1), for k=-4,-3,...,4
w = specfun_lambertw(1,(-4:4)')
e = [
-3.162952738804083896D+00-%i*2.342774750375521364D+01;
-2.853581755409037690D+00-%i*1.711353553941214756D+01;
-2.401585104868003029D+00-%i*1.077629951611507053D+01;
-1.533913319793574370D+00-%i*4.375185153061898369D+00;
5.671432904097838401D-01;
-1.533913319793574370D+00+%i*4.375185153061898369D+00;
-2.401585104868003029D+00+%i*1.077629951611507053D+01;
-2.853581755409037690D+00+%i*1.711353553941214756D+01;
-3.162952738804083896D+00+%i*2.342774750375521364D+01
];
halt()   // Press return to continue
 
// Produce a plot of |W0(z)|.
x = linspace(-6,6,51);
y = linspace(-6,6,51);
[x,y] = meshgrid(x,y);
w = specfun_lambertw(x + %i*y);
surf(x,y,abs(w));
xlabel("Re z");
ylabel("Im z");
title("|W_0(z)|");
halt()   // Press return to continue
 
// The following is an Inversion Error Test.
// Ideally, lambertw(z,b)*exp(lambertw(z,b)) = z for any complex z
// and any integer branch index b, but this is limited by machine
// precision.  The inversion error |lambertw(z,b)*exp(lambertw(z,b)) - z|
// is small but worth minding.
halt()   // Press return to continue
 
// Experimentation finds that the error is usually on the order of
// |z|*1e-16 on the principal branch.  This test computes the inversion
// error over the square [-10,10]x[-10,10] in the complex plane, large
// enough to characterize the error away from the branch points at
// z = 0 and -1/e.
halt()   // Press return to continue
 
// Use NxN points to sample the complex plane
N = 81;
// Sample in the square [-R,R]x[-R,R]
R = 10;
x = linspace(-R,R,N);
y = linspace(-R,R,N);
[xx,yy] = meshgrid(x,y);
z = xx + %i*yy;
i = 1;
j = 1;
msubp = 3;
nsubp = 3;
for b = -4:4
w = specfun_lambertw(z,b);
InvError = abs(w.*exp(w) - z);
mprintf("Largest error for b = %2d:  %.2e\n",b,max(InvError));
p = (i-1)*nsubp + j;
subplot(msubp,nsubp,p);
h = gcf();
cmap = graycolormap (10);
h.color_map = cmap ;
surf(x,y,InvError');
xtitle("Inversion error for W_"+string(b)+"(z)" , "Re z" , "Im z" , "Error" );
j = j + 1;
if ( j > nsubp ) then
j = 1;
i = i + 1;
end
end
halt()   // Press return to continue
 
//========= E N D === O F === D E M O =========//
//
// Load this script into the editor
//
filename = "specfun_lambertw.sce";
dname = get_absolute_file_path(filename);
editor ( fullfile(dname,filename) );

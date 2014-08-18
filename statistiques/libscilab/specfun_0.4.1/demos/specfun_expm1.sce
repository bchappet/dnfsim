//
// This help file was automatically generated from specfun_expm1.sci using help_from_sci().
// PLEASE DO NOT EDIT
//
mode(1)
//
// Demo of specfun_expm1.sci
//

specfun_expm1(2)
halt()   // Press return to continue
 
// Plot this function for positive inputs.
scf();
plot(linspace(-0.5,2,1000),specfun_expm1)
halt()   // Press return to continue
 
// Compare the precision of expm1 and exp
// for small x: expm1 gives the exact result
// while exp(x)-1 is not as accurate.
x = 0.000001;
e = 1.000000500000166666e-6 // From Wolfram Alpha
y1 = specfun_expm1(x); abs(y1-e)/abs(e)
y2 = exp(x)-1;  abs(y2-e)/abs(e)
halt()   // Press return to continue
 
//========= E N D === O F === D E M O =========//
//
// Load this script into the editor
//
filename = "specfun_expm1.sce";
dname = get_absolute_file_path(filename);
editor ( fullfile(dname,filename) );

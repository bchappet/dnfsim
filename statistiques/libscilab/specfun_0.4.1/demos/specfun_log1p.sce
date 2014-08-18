//
// This help file was automatically generated from specfun_log1p.sci using help_from_sci().
// PLEASE DO NOT EDIT
//
mode(1)
//
// Demo of specfun_log1p.sci
//

specfun_log1p(2)
halt()   // Press return to continue
 
// Plot this function for positive inputs.
scf();
plot(linspace(-0.5,2,1000),specfun_log1p)
halt()   // Press return to continue
 
// Compare the precision of log1p and log
// for small x: log1p gives the exact result
// while log(1+x) is not as accurate.
x = 0.000001;
e = 9.9999950000033333308e-7 // From Wolfram Alpha
y1 = specfun_log1p(x); abs(y1-e)/abs(e)
y2 = log(1+x);  abs(y2-e)/abs(e)
halt()   // Press return to continue
 
//========= E N D === O F === D E M O =========//
//
// Load this script into the editor
//
filename = "specfun_log1p.sce";
dname = get_absolute_file_path(filename);
editor ( fullfile(dname,filename) );

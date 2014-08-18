//
// This help file was automatically generated from specfun_factorial.sci using help_from_sci().
// PLEASE DO NOT EDIT
//
mode(1)
//
// Demo of specfun_factorial.sci
//

// Make a table of factorial
n = (0:30)';
[n specfun_factorial(n)]
halt()   // Press return to continue
 
// See that we must round the gamma function
n = 12;
specfun_factorial(n)
gamma(n+1)
halt()   // Press return to continue
 
// See the limits of factorial: f(171)=%inf
specfun_factorial(170) // 7.257415615307998967e306
specfun_factorial(171) // %inf
halt()   // Press return to continue
 
// Plot the function on all its range.
scf();
plot ( 1:170 , specfun_factorial , "b-o" )
h = gcf();
h.children.log_flags="nln";
halt()   // Press return to continue
 
//========= E N D === O F === D E M O =========//
//
// Load this script into the editor
//
filename = "specfun_factorial.sce";
dname = get_absolute_file_path(filename);
editor ( fullfile(dname,filename) );

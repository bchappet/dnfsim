//
// This help file was automatically generated from specfun_factoriallog.sci using help_from_sci().
// PLEASE DO NOT EDIT
//
mode(1)
//
// Demo of specfun_factoriallog.sci
//

// Generates an error: this is not an integer
specfun_factoriallog(1.5)
halt()   // Press return to continue
 
// Generates an error: this is not positive
specfun_factoriallog(-1)
halt()   // Press return to continue
 
// Plot the function
scf();
plot(0:170,specfun_factoriallog)
halt()   // Press return to continue
 
//========= E N D === O F === D E M O =========//
//
// Load this script into the editor
//
filename = "specfun_factoriallog.sce";
dname = get_absolute_file_path(filename);
editor ( fullfile(dname,filename) );

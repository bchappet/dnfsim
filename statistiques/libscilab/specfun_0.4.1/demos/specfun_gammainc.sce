//
// This help file was automatically generated from specfun_gammainc.sci using help_from_sci().
// PLEASE DO NOT EDIT
//
mode(1)
//
// Demo of specfun_gammainc.sci
//

specfun_gammainc(1,2) // Expected : 0.264241117657115
specfun_gammainc(2,3) // Expected : 0.323323583816936
specfun_gammainc(2,3,"lower") // Expected : 0.323323583816936
// We have specfun_gammainc(x,a,"lower") == 1 - specfun_gammainc(x,a,"upper")
specfun_gammainc(2,3,"upper") // Expected : 0.676676416183064
halt()   // Press return to continue
 
// The following example shows how to use the tail argument.
// For a=1 and x>40, the result is so close to 1 that the
// result is represented by the floating point number y=1.
specfun_gammainc(40,1) // Expected : 1
// This is why we may compute the complementary probability with
// the tail option.
specfun_gammainc(40,1,"upper") // Expected : 4.248354255291594e-018
halt()   // Press return to continue
 
// Show the expansion of a
x = [1 2 3;4 5 6];
a = 2;
specfun_gammainc(x,a)
halt()   // Press return to continue
 
//========= E N D === O F === D E M O =========//
//
// Load this script into the editor
//
filename = "specfun_gammainc.sce";
dname = get_absolute_file_path(filename);
editor ( fullfile(dname,filename) );

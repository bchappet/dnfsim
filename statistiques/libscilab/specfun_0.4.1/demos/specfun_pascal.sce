//
// This help file was automatically generated from specfun_pascal.sci using help_from_sci().
// PLEASE DO NOT EDIT
//
mode(1)
//
// Demo of specfun_pascal.sci
//

specfun_pascal(5) // symetric
specfun_pascal(5,-1) // upper
specfun_pascal(5,0) // symetric
specfun_pascal(5,1) // lower
halt()   // Press return to continue
 
// Check a famous identity
n = 5;
U = specfun_pascal(n,-1)
S = specfun_pascal(n,0)
L = specfun_pascal(n,1)
L*U - S
halt()   // Press return to continue
 
//========= E N D === O F === D E M O =========//
//
// Load this script into the editor
//
filename = "specfun_pascal.sce";
dname = get_absolute_file_path(filename);
editor ( fullfile(dname,filename) );

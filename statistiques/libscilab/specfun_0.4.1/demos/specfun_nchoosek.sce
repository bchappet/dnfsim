//
// This help file was automatically generated from specfun_nchoosek.sci using help_from_sci().
// PLEASE DO NOT EDIT
//
mode(1)
//
// Demo of specfun_nchoosek.sci
//

c = specfun_nchoosek ( 4 , 1 ) // 4
c = specfun_nchoosek ( 5 , 0 ) // 1
c = specfun_nchoosek ( 5 , 1 ) // 5
c = specfun_nchoosek ( 5 , 2 ) // 10
c = specfun_nchoosek ( 5 , 3 ) // 10
c = specfun_nchoosek ( 5 , 4 ) // 5
c = specfun_nchoosek ( 5 , 5 ) // 1
halt()   // Press return to continue
 
// The following test shows that the implementation is not naive
c = specfun_nchoosek ( 10000 , 134 ) //
exact = 2.050083865033972676e307
relerror = abs(c-exact)/exact
// On a Windows system, we got ~ 1.e-11, which shows that the implementation
// was, in this case, accurate up to 11 digits (instead of 15-17).
halt()   // Press return to continue
 
// The following test shows that the implementation is vectorized.
specfun_nchoosek (10,0:10) // [1,10,45,120,210,252,210,120,45,10,1]
// The following would be different in Matlab
specfun_nchoosek (1:10,1)
// The following would be impossible in Matlab
specfun_nchoosek (1:10,1:10)
halt()   // Press return to continue
 
// Generates an error
c = specfun_nchoosek ( 17 , 18 )
c = specfun_nchoosek ( 17 , -1 )
c = specfun_nchoosek ( 1.5 , 0.5 )
halt()   // Press return to continue
 
// This generates an error, since n and k do not
// have the same size.
specfun_nchoosek (ones(4,5),ones(2,3))
halt()   // Press return to continue
 
//========= E N D === O F === D E M O =========//
//
// Load this script into the editor
//
filename = "specfun_nchoosek.sce";
dname = get_absolute_file_path(filename);
editor ( fullfile(dname,filename) );

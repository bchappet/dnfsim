//
// This help file was automatically generated from specfun_subset.sci using help_from_sci().
// PLEASE DO NOT EDIT
//
mode(1)
//
// Demo of specfun_subset.sci
//

specfun_subset ( (1:4) , 3 )
specfun_subset ( [17 32 48 53] , 3 )
specfun_subset ( [17 32 48 53 72] , 3 )
halt()   // Press return to continue
 
// Produce column-by-column combinations
specfun_subset ( [17 32 48 53 72]' , 3 , "c" )
// Produce row-by-row combinations (default)
specfun_subset ( [17 32 48 53 72] , 3 , "r" )
// Generates an error: x must be consistent with d
specfun_subset ( [17 32 48 53 72]' , 3 , "r" )
specfun_subset ( [17 32 48 53 72] , 3 , "c" )
halt()   // Press return to continue
 
// Compare combinerepeat, perms and subset
specfun_subset((1:3),3)
// combinerepeat computed combinations with replacement
specfun_combinerepeat(1:3,3)'
// Scilab/perms compute permutations without replacement
perms(1:3)
halt()   // Press return to continue
 
// Indirectly combine strings
x = ["a" "b" "c" "d" "e"]';
k = 3;
n=size(x,"*");
map = specfun_subset((1:n),k);
cnk = specfun_nchoosek(n,k);
computed = matrix(x(map),cnk,k)
halt()   // Press return to continue
 
// Directly combine strings
computed = specfun_subset(["a" "b" "c" "d" "e" "f"],4)
halt()   // Press return to continue
 
// Combinations of booleans
computed = specfun_subset ( [%t %f %t %f] , 3 )
halt()   // Press return to continue
 
//========= E N D === O F === D E M O =========//
//
// Load this script into the editor
//
filename = "specfun_subset.sce";
dname = get_absolute_file_path(filename);
editor ( fullfile(dname,filename) );

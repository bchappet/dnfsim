//
// This help file was automatically generated from specfun_combinerepeat.sci using help_from_sci().
// PLEASE DO NOT EDIT
//
mode(1)
//
// Demo of specfun_combinerepeat.sci
//

// Compute repeated combinations of x:
x = [1 2 3];
specfun_combinerepeat ( x , 1 )
specfun_combinerepeat ( x , 2 )
specfun_combinerepeat ( x , 3 )
specfun_combinerepeat ( x , 4 )
halt()   // Press return to continue
 
// Compare to specfun_combine
// Same as k=2
specfun_combine ( x , x )
// Same as k=3
specfun_combine ( x , x , x )
// Same as k=4
specfun_combine ( x , x , x , x )
halt()   // Press return to continue
 
// Repeated combinations of booleans
computed = specfun_combinerepeat ( [%t %f] , 2 )
// Repeated combinations of strings
computed = specfun_combinerepeat ( ["A" "C" "T" "G"] , 2 )
// Repeated combinations of integers
computed = specfun_combinerepeat ( uint8(1:3) , 2 )
halt()   // Press return to continue
 
// Compare combinerepeat, perms and subset
// Scilab/perms compute permutations without replacement
perms ( 1:3 )
// specfun_combinerepeat compute combinations with replacement
specfun_combinerepeat(1:3,3)'
// specfun_subset compute subsets with k elements
specfun_subset(1:3,3)
halt()   // Press return to continue
 
//========= E N D === O F === D E M O =========//
//
// Load this script into the editor
//
filename = "specfun_combinerepeat.sce";
dname = get_absolute_file_path(filename);
editor ( fullfile(dname,filename) );

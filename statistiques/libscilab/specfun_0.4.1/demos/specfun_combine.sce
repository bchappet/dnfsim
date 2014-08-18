//
// This help file was automatically generated from specfun_combine.sci using help_from_sci().
// PLEASE DO NOT EDIT
//
mode(1)
//
// Demo of specfun_combine.sci
//

// Compute all combinations of x and y: vectors
x = [1 2 3];
y = [4 5 6];
specfun_combine ( x , y )
expected = [
1 1 1 2 2 2 3 3 3
4 5 6 4 5 6 4 5 6
];
halt()   // Press return to continue
 
// Compute all combinations of x and y and z: vectors
x = [1 2 3];
y = [4 5 6];
z = [7 8 9];
specfun_combine ( x , y , z )
expected = [
1 1 1 1 1 1 1 1 1 2 2 2 2 2 2 2 2 2 3 3 3 3 3 3 3 3 3
4 4 4 5 5 5 6 6 6 4 4 4 5 5 5 6 6 6 4 4 4 5 5 5 6 6 6
7 8 9 7 8 9 7 8 9 7 8 9 7 8 9 7 8 9 7 8 9 7 8 9 7 8 9
];
halt()   // Press return to continue
 
// Compute all combinations of x and y: matrices
x = [1 2;3 4];
y = [5 6;7 8];
specfun_combine ( x , y )
expected = [
1 1 2 2
3 3 4 4
5 6 5 6
7 8 7 8
];
halt()   // Press return to continue
 
// Combine random matrices of random sizes.
// Shows that matrices of any dimensions can be combined.
m = grand(1,2,"uin",1,5);
n = grand(1,2,"uin",1,5);
x = grand(m(1),n(1),"uin",1,m(1)*n(1));
y = grand(m(2),n(2),"uin",1,m(2)*n(2));
c = specfun_combine ( x , y );
and(size(c) == [m(1)+m(2) n(1)*n(2)])
halt()   // Press return to continue
 
// Indirectly produce combinations of characters
k = specfun_combine ( 1:2 , 1:2 )
m1=["a" "b"];
m2=["c" "d"];
c = [m1(k(1,:));m2(k(2,:))]
halt()   // Press return to continue
 
// Directly combine strings
x = ["a" "b" "c"];
y = ["d" "e"];
z = ["f" "g" "h"];
computed = specfun_combine ( x , y , z )
halt()   // Press return to continue
 
// Produces combinations of booleans
c = specfun_combine ( [%t %f] , [%t %f] , [%t %f] )
halt()   // Press return to continue
 
// Combine 2 DNA genes
c = specfun_combine ( ["A" "C" "G" "T"]  , ["A" "C" "G" "T"] )
halt()   // Press return to continue
 
// Produces combinations of integers
c = specfun_combine(uint8(1:4),uint8(1:3))
halt()   // Press return to continue
 
//========= E N D === O F === D E M O =========//
//
// Load this script into the editor
//
filename = "specfun_combine.sce";
dname = get_absolute_file_path(filename);
editor ( fullfile(dname,filename) );

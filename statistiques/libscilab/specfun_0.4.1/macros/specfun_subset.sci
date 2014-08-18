// Copyright (C) 2009 - 2010 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function c = specfun_subset ( varargin )
    // Generate all combinations of k values from x without replacement.
    // 
    // Calling Sequence
    //   c = specfun_subset ( x , k )
    //   c = specfun_subset ( x , k , d )
    //
    // Parameters
    //   x : a 1-by-n or n-by-1 matrix, the values to be combined. If d=="r" (default), then x must be 1-by-n. If d=="c" (default), then x must be n-by-1.
    //   k : a 1-by-1 matrix of floating point integers, the number of elements in each combination
    //   d : a 1-by-1 matrix of strings, the direction of the combinations. If d=="r" (default), then each combination is in a row of c. If d=="c", each combination is in a column of c. Default d="r".
    //   c : a cnk-by-k or k-by-cnk matrix, the combinations. Here, cnk is equal to the number of combinations of n elements from k. If d=="r" (default), then c is a cnk-by-k matrix.  If d=="c", then c is a k-by-cnk matrix. 
    //
    // Description
    // Generate all subsets of k elements from x. 
    // Here, cnk=(n,k) is the binomial coefficient and n is the number of entries in x. 
    // <itemizedlist>
    // <listitem>If d=="r", then the row vector c(i,1:k) is a particular combination, where i=1,2,...,cnk.</listitem>
    // <listitem>If d=="c", then the column vector c(1:k,i) is a particular combination, where i=1,2,...,cnk.</listitem>
    // </itemizedlist>
    //
    // Any optional argument equal to the empty matrix [] is replaced by its default value.
    //
    // Notice that the combinations are computed without replacement.
    // Use specfun_combine if all combinations with replacement are to be generated.
    // Use perms if all permutations are to be generated.
    //
    // Can process x if x is double, strings boolean and integer (signed,unsigned, 
    // 8-bits, 16-bits, 32-bits).
    //
    // Implementation Notes
    //
    // The algorithm proceeds as following.
    // Assume that d=="c".
    // The number of nchoosek is computed from c = specfun_nchoosek ( n , k )
    // For example, with n=5, k=3, we get c=10.
    // The first possible combination is a = 1:k.
    //
    // For example, with n=5 and k = 3, the first combination is 
    //
    // <programlisting>
    // a = [1 2 3]'
    // </programlisting>
    //
    // We store in c the value of x(a), that is, the first column of 
    // c is [x(1) x(2) x(3)]'.
    // Then a loop is performed for all remaining nchoosek, from 2 to c.
    // The remaining combinations are enumerated in order.
    //
    // For example, with n=5, k=3, we compute the remaining combinations in the 
    // following order :
    //
    // <programlisting>
    // a=[1 2 4]'
    // a=[1 2 5]'
    // a=[1 3 4]'
    // a=[1 3 5]'
    // a=[1 4 5]'
    // a=[2 3 4]'
    // a=[2 3 5]'
    // a=[2 4 5]'
    // a=[3 4 5]'
    // </programlisting>
    //
    // Once the combination vector a is computed, we store 
    // x(a) in the corresponding column of the matrix c.
    // Let us detail how the nchoosek are computed, that 
    // is, how the vector a is updated.
    // First, we search for i, the first digit to be updated.
    // We use a backward search, starting from i = k and look for the 
    // condition a(i) == n - k + i. If that condition is satisfied,
    // we continue the search and decrease i. If not, we stop.
    // For example, if a=[1 3 4], we find i = 3 (because a(3)=4
    // is different from n=5) and if a=[1 4 5], we find i=1
    // (because a(3)=5=n and a(2)=4=n-1).
    // Then we increase a(i) and update all the integers  
    // a(i+1), a(i+2), ..., a(k).
    //
    // This procedure is as vectorized as possible : I guess 
    // that more speed will require to write compiled source code.
    //
    // Examples
    //   specfun_subset ( (1:4) , 3 )
    //   specfun_subset ( [17 32 48 53] , 3 )
    //   specfun_subset ( [17 32 48 53 72] , 3 )
    //
    // // Produce column-by-column combinations
    //   specfun_subset ( [17 32 48 53 72]' , 3 , "c" )
    // // Produce row-by-row combinations (default)
    //   specfun_subset ( [17 32 48 53 72] , 3 , "r" )
    // // Generates an error: x must be consistent with d
    //   specfun_subset ( [17 32 48 53 72]' , 3 , "r" )
    //   specfun_subset ( [17 32 48 53 72] , 3 , "c" )
    //
    // // Compare combinerepeat, perms and subset
    // specfun_subset((1:3),3)
    // // combinerepeat computed combinations with replacement
    // specfun_combinerepeat(1:3,3)'
    // // Scilab/perms compute permutations without replacement
    // perms(1:3)
    //
    // // Indirectly combine strings
    // x = ["a" "b" "c" "d" "e"]';
    // k = 3;
    // n=size(x,"*");
    // map = specfun_subset((1:n),k);
    // cnk = specfun_nchoosek(n,k);
    // computed = matrix(x(map),cnk,k)
    //
    // // Directly combine strings
    // computed = specfun_subset(["a" "b" "c" "d" "e" "f"],4)
    //
    // // Combinations of booleans
    // computed = specfun_subset ( [%t %f %t %f] , 3 )
    //
    // Authors
    //   2009-2010 - DIGITEO - Michael Baudin
    //
    // Bibliography
    // http://home.att.net/~srschmitt/script_nchoosek.html
    // Kenneth H. Rosen, Discrete Mathematics and Its Applications, 2nd edition (NY: McGraw-Hill, 1991), pp. 284-286.

    [lhs, rhs] = argn()
    apifun_checkrhs ( "specfun_subset" , rhs , 2:3 )
    apifun_checklhs ( "specfun_subset" , lhs , 1 )
    //
    // Get arguments
    x = varargin(1)
    k = varargin(2)
    d = apifun_argindefault ( varargin , 3 , "r" )
    //
    // Check Type
    apifun_checktype ( "specfun_subset" , x , "x" , 1 , ["constant" "string" "boolean" "int8" "uint8"  "int16" "uint16" "int32" "uint32"])
    apifun_checktype ( "specfun_subset" , k , "k" , 2 , "constant" )
    apifun_checktype ( "specfun_subset" , d , "d" , 3 , "string" )
    //
    // Check Size
    if ( d == "c" ) then
      apifun_checkveccol ( "specfun_subset" , x , "x" , 1 , size(x,"*") )
    else
      apifun_checkvecrow ( "specfun_subset" , x , "x" , 1 , size(x,"*") )
    end
    apifun_checkscalar ( "specfun_subset" , k , "k" , 2 )
    apifun_checkscalar ( "specfun_subset" , d , "d" , 3 )
    //
    // Check Content
    apifun_checkrange ( "specfun_subset" , k , "k" , 2 , 1 , %inf )
    n = size(x,"*")
    apifun_checkloweq ( "specfun_subset" , k , "k" , 2 , n )
    apifun_checkflint ( "specfun_subset" , k , "k" , 2 )
    apifun_checkoption ( "specfun_subset" , d , "d" , 3 , ["r" "c"] )
    // TODO : use apifun_checkflint when ready
    //
    // Proceed...
    cnk = specfun_nchoosek ( n , k )
    if ( typeof(x)=="constant" ) then
        //
        // Matrix of doubles
        if ( d == "c" ) then
            c = zeros(k,cnk)
            a = 1:k
            c(:,1) = x(a)
            for m = 2 : cnk
                r = k : -1 : 1
                i = k - find ( a(r) <> n - k + r , 1 ) + 1
                a(i) = a(i) + 1
                j = i+1 : k
                a(j) = a(i) + j - i
                c(:,m) = x(a)
            end
        else
            c = zeros(cnk,k)
            a = 1:k
            c(1,:) = x(a)
            for m = 2 : cnk
                r = k : -1 : 1
                i = k - find ( a(r) <> n - k + r , 1 ) + 1
                a(i) = a(i) + 1
                j = i+1 : k
                a(j) = a(i) + j - i
                c(m,:) = x(a)
            end
        end
    else
        //
        // String, boolean, integer...
        if ( d == "c" ) then
          imap = specfun_subset((1:n)',k,d);
          c = matrix(x(imap),k,cnk);
        else
          imap = specfun_subset((1:n),k,d);
          c = matrix(x(imap),cnk,k);
        end
    end
endfunction

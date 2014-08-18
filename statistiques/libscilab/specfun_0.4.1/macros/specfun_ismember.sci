// Copyright (C) 2010-2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function tf = specfun_ismember ( a , s )
    // Array elements that are members of set.
    //
    // Calling Sequence
    // tf = specfun_ismember ( a , s )
    //
    // Parameters
    // a : a ma-by-na matrix
    // s : a 1-by-ns matrix
    // tf : a ma-by-na matrix of booleans, tf(i,j) is %t if there is an entry in s matching a(i,j).
    //
    // Description
    // Search in a the entries which are in s.
    // 
    // Uses vectorized statements, based on the Kronecker product. 
    // The intermediate memory required is ma*na*ns.
    // 
    // Examples
    // a = (1:5)'
    // s = [0 2 4 6 8 10 12 14 16 18 20]
    // tf = specfun_ismember ( a , s )
    // expected = [%f %t %f %t %f]'
    //
    // // An example with a matrix
    // a = [
    // 7 35 14 86 76   
    // 15 51 24 96 49   
    // 35 40 46 35 93   
    // 85 34 74 82 22
    // ];
    // s = [51 74 22 15 86] 
    // tf = specfun_ismember(a, s)
    //
    // // With strings
    // a = ["1" "2" "3" "4" "5"];
    // s = ["0" "2" "4" "6" "8" "10" "12" "14" "16" "18" "20"];
    // tf = specfun_ismember(a, s)
    //
    // Authors
    // Copyright (C) 2010 - DIGITEO - Michael Baudin

    [lhs, rhs] = argn()
    apifun_checkrhs ( "specfun_ismember" , rhs , 2:2 )
    apifun_checklhs ( "specfun_ismember" , lhs , 0:1 )
    //
    // Check type
    apifun_checktype ( "specfun_ismember" , a , "a" , 1 , ["constant" "string"])
    apifun_checktype ( "specfun_ismember" , s , "s" , 2 , ["constant" "string"] )
    if ( typeof(a)<>typeof(s) ) then
        lcmsg = "%s: Incompatible input arguments #%d and #%d: Same types expected.\n"
        error(msprintf(gettext(lclmsg),"specfun_ismember",1,2))
    end
    //
    // Check size
    // a can have any shape
    if ( s==[] ) then
      tf=(ones(a)==zeros(a))
      return
    else
      apifun_checkvecrow ( "specfun_ismember" , s , "s" , 2 , size(s,"*") )
    end
    //
    // Check content
    // Nothing to do.
    //
    // Proceed...
    if ( a==[] ) then
      tf=[]
      return
    end
    //
    // Convert a into a column vector
    ma = size(a,"r")
    na = size(a,"c")
    a=a(:)
    //
    ns = size(s,"c")
    sp = s(ones(na*ma,1),:)
    ap = a(:,ones(1,ns))
    tf = or(ap==sp,"c")
    //
    // Convert tf into the same shape as a
    tf=matrix(tf,ma,na)
endfunction



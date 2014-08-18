// Copyright (C) 2010 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- JVM NOT MANDATORY -->

//
// assert_equal --
//   Returns 1 if the two real matrices computed and expected are equal.
// Arguments
//   computed, expected : the two matrices to compare
//   epsilon : a small number
//
function flag = assert_equal ( computed , expected )
  if ( and ( computed==expected ) ) then
    flag = 1;
  else
    flag = 0;
  end
  if flag <> 1 then pause,end
endfunction

//
// With strings - argument is there
vararglist = list("a","b","c");
ivar = 2;
default = "e";
argin = apifun_argindefault ( vararglist , ivar , default );
assert_equal ( argin , "b" );

//
// With strings - argument is not there
vararglist = list("a","b","c");
ivar = 4;
default = "e";
argin = apifun_argindefault ( vararglist , ivar , default );
assert_equal ( argin , "e" );

//
// With strings - argument is there, but is empty matrix
vararglist = list([],"b","c");
ivar = 1;
default = "e";
argin = apifun_argindefault ( vararglist , ivar , default );
assert_equal ( argin , "e" );

//
// With a typed-list.
myobj = tlist(["T_MYSTUFF","nb"]);
vararglist = list(4,5,myobj);
ivar = 3;
default = [];
argin = apifun_argindefault ( vararglist , ivar , default );
assert_equal ( argin , myobj );

//
// A practical use-case

function y = myfun ( varargin )
  //   y = myfun(x)
  //   y = myfun(x,a)
  //   y = myfun(x,a,b)
  //   y = myfun(x,a,b,c)
  //
  // Returns y = a*x^b+c
  // Defaults are a=5, b=6, c=7.
  // If any optional argument is [], we use the default value.
  
  [lhs, rhs] = argn();
  if ( rhs < 1 | rhs > 4 ) then
    errmsg = msprintf(gettext("%s: Wrong number of input arguments: %d to %d expected.\n"), "myfun", 1,4);
    error(errmsg)
  end
  //
  x = varargin(1)
  a = apifun_argindefault ( varargin , 2 , 5 )
  b = apifun_argindefault ( varargin , 3 , 6 )
  c = apifun_argindefault ( varargin , 4 , 7 )
  y = a.*x.^b + c
endfunction

y = myfun(7);
assert_equal ( y , 5*7^6+7 );
//
y = myfun(7,2);
assert_equal ( y , 2*7^6+7 );
//
y = myfun(7,2,3);
assert_equal ( y , 2*7^3+7 );
//
y = myfun(7,2,3,4);
assert_equal ( y , 2*7^3+4 );
//
y = myfun(7,[],3,4);
assert_equal ( y , 5*7^3+4 );
//
y = myfun(7,[],[],4);
assert_equal ( y , 5*7^6+4 );
//
y = myfun(7,[],[],[]);
assert_equal ( y , 5*7^6+7 );
//

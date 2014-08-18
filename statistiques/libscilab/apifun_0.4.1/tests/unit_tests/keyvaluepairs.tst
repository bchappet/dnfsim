// Copyright (C) 2011 - DIGITEO - Michael Baudin
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
  if flag <> 1 then
    error("Not equal")
  end
endfunction

//
// Set the defaults
default.a = 1;
default.b = 1;
default.c = 1;
//
// Check with doubles
options = apifun_keyvaluepairs (default);
expected.a=1;
expected.b=1;
expected.c=1;
assert_equal ( options , expected );
//
options = apifun_keyvaluepairs (default,"a",2);
expected.a=2;
expected.b=1;
expected.c=1;
assert_equal ( options , expected );
//
options = apifun_keyvaluepairs (default,"b",12);
expected.a=1;
expected.b=12;
expected.c=1;
assert_equal ( options , expected );
//
options = apifun_keyvaluepairs (default,"b",12,"a",999);
expected.a=999;
expected.b=12;
expected.c=1;
assert_equal ( options , expected );
//
options = apifun_keyvaluepairs (default,"c",-1,"b",12,"a",999);
expected.a=999;
expected.b=12;
expected.c=-1;
assert_equal ( options , expected );
//
// Error cases:
//
// Wrong number of arguments
instr = "options = apifun_keyvaluepairs ()";
errmsg = msprintf(gettext("%s: Wrong number of input arguments: At least %d expected.\n"), "apifun_keyvaluepairs",1);
ierr = execstr(instr,"errcatch");
laerr = lasterror();
assert_equal ( ierr , 10000 );
assert_equal ( laerr , errmsg );
//
// Wrong number of arguments
instr = "options = apifun_keyvaluepairs (default,""c"")";
errmsg = msprintf(gettext("%s: Even number of arguments."), "apifun_keyvaluepairs");
ierr = execstr(instr,"errcatch");
laerr = lasterror();
assert_equal ( ierr , 10000 );
assert_equal ( laerr , errmsg );
//
// Wrong type of default
instr = "options = apifun_keyvaluepairs ([])";
errmsg = msprintf(gettext("%s: Wrong type for argument %d: Struct expected.\n"), "apifun_keyvaluepairs",1);
ierr = execstr(instr,"errcatch");
laerr = lasterror();
assert_equal ( ierr , 10000 );
assert_equal ( laerr , errmsg );
//
// Unknown key
instr = "options = apifun_keyvaluepairs (default,""d"",2)";
errmsg = msprintf(gettext("%s: Unknown key: %s."), "apifun_keyvaluepairs","d");
ierr = execstr(instr,"errcatch");
laerr = lasterror();
assert_equal ( ierr , 10000 );
assert_equal ( laerr , errmsg );

//
// Check with functions
function foo()
endfunction
function faa()
endfunction
function fii()
endfunction
//
// Set the defaults
default.a = 1;
default.b = 1;
default.c = [];
options = apifun_keyvaluepairs (default,"c",foo,"b",12,"a",999)
expected.a=999;
expected.b=12;
expected.c=foo;
assert_equal ( options , expected );
//
options = apifun_keyvaluepairs (default,"c",foo,"b",faa,"a",999)
expected.a=999;
expected.b=faa;
expected.c=foo;
assert_equal ( options , expected );
//
options = apifun_keyvaluepairs (default,"c",foo,"b",faa,"a",fii)
expected.a=fii;
expected.b=faa;
expected.c=foo;
assert_equal ( options , expected );

//
// Incremental method
default.a = 1;
default.b = 1;
default.c = [];
//
options = apifun_keyvaluepairs (default);
expected.a=1;
expected.b=1;
expected.c=[];
assert_equal ( options , expected );
//
options = apifun_keyvaluepairs (options,"c",foo);
expected.a=1;
expected.b=1;
expected.c=foo;
assert_equal ( options , expected );
//
options = apifun_keyvaluepairs (options,"b",faa);
expected.a=1;
expected.b=faa;
expected.c=foo;
assert_equal ( options , expected );
//
options = apifun_keyvaluepairs (options,"a",2);
expected.a=2;
expected.b=faa;
expected.c=foo;
assert_equal ( options , expected );
//
options = apifun_keyvaluepairs (options,"c",foo);
expected.a=2;
expected.b=faa;
expected.c=foo;
assert_equal ( options , expected );

// y = myfunction(x)
// y = myfunction(x,key1,value1,...)
// Available keys: 
// "a" (default a=1)
// "b" (default b=1)
// "c" (default c=1)
function y = myfunction(x,varargin)
    //
    // 1. Set the defaults
    default.a = 1
    default.b = 1
    default.c = 1
    //
    // 2. Manage (key,value) pairs
    options = apifun_keyvaluepairs (default,varargin(1:$))
    //
    // 3. Get parameters
    a = options.a
    b = options.b
    c = options.c
    // TODO : check the types, size and content of a, b, c
    y = a*x^2+b*x+c
endfunction

y = myfunction(1);
expected = 1*1^2+1*1+1;
assert_equal ( y , expected );
//
y = myfunction(1,"a",2);
expected = 2*1^2+1*1+1;
assert_equal ( y , expected );
//
y = myfunction(1,"b",3);
expected = 1*1^2+3*1+1;
assert_equal ( y , expected );
//
y = myfunction(1,"c",4,"b",3);
expected = 1*1^2+3*1+4;
assert_equal ( y , expected );
//
// // Error cases:
instr = "y = myfunction(1,""d"")";
errmsg = msprintf(gettext("%s: Even number of arguments."), "apifun_keyvaluepairs");
ierr = execstr(instr,"errcatch");
laerr = lasterror();
assert_equal ( ierr , 10000 );
assert_equal ( laerr , errmsg );
// 
instr = "y = myfunction(1,""d"",4)";
errmsg = msprintf(gettext("%s: Unknown key: %s."), "apifun_keyvaluepairs","d");
ierr = execstr(instr,"errcatch");
laerr = lasterror();
assert_equal ( ierr , 10000 );
assert_equal ( laerr , errmsg );
// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
//

//
// Check the error messages in the uniform random number generators.
//

// clcg2.c (1 hits)
// clcg4.c (2 hits)
// fsultra.c (4 hits)
// kiss.c (1 hits)
// mt.c (2 hits)
// unifrng.c (1 hits)
// unifrng.h (1 hits)
// urand.c (1 hits)

// There are (at least) two things to check
// - negative inputs generate an error
// - floating point inputs generate an error

// /////////////////////////////////////////////////////
//
// clcg2
//
distfun_genset("clcg2");
instr="distfun_seedset(-1,-1)";
assert_checkerror ( instr, "distfun_seedset: Internal error: Cannot set state of clcg2 generator." , 999 );
//
distfun_genset("clcg2");
instr="distfun_seedset(1.5,1.5)";
assert_checkerror ( instr, "distfun_seedset: Internal error: Cannot set state of clcg2 generator." , 999 );
// /////////////////////////////////////////////////////
//
// clcg4
//
//
distfun_genset("clcg4");
instr="distfun_seedset(-1,-1,-1,-1)";
assert_checkerror ( instr, "distfun_seedset: Internal error: Cannot set state of clcg4 generator." , 999 );
//
distfun_genset("clcg4");
instr="distfun_seedset(1.5,1.5,1.5,1.5)";
assert_checkerror ( instr, "distfun_seedset: Internal error: Cannot set state of clcg4 generator." , 999 );
//
// The second error message in clcg4 cannot be 
// generated with the current interface.
// This is on purpose: the user cannot change the seeds 
// in a way which may lead to unconsistent results.
// /////////////////////////////////////////////////////
//
// fsultra
//
//
distfun_genset("fsultra");
instr="distfun_seedset(-1,-1)";
assert_checkerror ( instr, "distfun_seedset: Internal error: Cannot set state of fsultra generator." , 999 );
//
distfun_genset("fsultra");
instr="distfun_seedset(1.5,1.5)";
assert_checkerror ( instr, "distfun_seedset: Internal error: Cannot set state of fsultra generator." , 999 );
//
distfun_genset("fsultra");
for i=1:5:40
    s = ones(40,1);
    s(i)=-1;
    instr="distfun_seedset(s)";
    assert_checkerror ( instr, "distfun_seedset: Internal error: Cannot set state of fsultra generator." , 999 );
end
//
distfun_genset("fsultra");
for i=1:5:40
    s = ones(40,1);
    s(i)=1.5;
    instr="distfun_seedset(s)";
    assert_checkerror ( instr, "distfun_seedset: Internal error: Cannot set state of fsultra generator." , 999 );
end
// /////////////////////////////////////////////////////
//
// kiss
//
//
distfun_genset("kiss");
instr="distfun_seedset(-1,-1,-1,-1)";
assert_checkerror ( instr, "distfun_seedset: Internal error: Cannot set state of kiss generator." , 999 );
//
distfun_genset("kiss");
instr="distfun_seedset(1.5,1.5,1.5,1.5)";
assert_checkerror ( instr, "distfun_seedset: Internal error: Cannot set state of kiss generator." , 999 );
// /////////////////////////////////////////////////////
//
// mt
//
//
distfun_genset("mt");
instr="distfun_seedset(-1)";
assert_checkerror ( instr, "distfun_seedset: Internal error: Cannot set state of mt generator." , 999 );
//
distfun_genset("mt");
instr="distfun_seedset(1.5)";
assert_checkerror ( instr, "distfun_seedset: Internal error: Cannot set state of mt generator." , 999 );
//
distfun_genset("mt");
for i=1:100:625
    s=ones(625,1);
    s(i)=-1;
    instr="distfun_seedset(s)";
    assert_checkerror ( instr, "distfun_seedset: Internal error: Cannot set state of mt generator." , 999 );
end
//
distfun_genset("mt");
for i=1:100:625
    s=ones(625,1);
    s(i)=1.5;
    instr="distfun_seedset(s)";
    assert_checkerror ( instr, "distfun_seedset: Internal error: Cannot set state of mt generator." , 999 );
end
// /////////////////////////////////////////////////////
//
// urand
//
//
distfun_genset("urand");
instr="distfun_seedset(-1)";
assert_checkerror ( instr, "distfun_seedset: Internal error: Cannot set state of urand generator." , 999 );
//
distfun_genset("urand");
instr="distfun_seedset(1.5)";
assert_checkerror ( instr, "distfun_seedset: Internal error: Cannot set state of urand generator." , 999 );

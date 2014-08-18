// =============================================================================

// Copyright (C) 2012 - Michael Baudin
// Copyright (C) ????-2008 - INRIA
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
// =============================================================================

distfun_genset("clcg4");

// Low level test for grand

//**********************************************************************
//     A test program for the bottom level routines
//     Scilab implementation of tstbot.f
//**********************************************************************
NB       = 10;
NR       = 1000;
answer   = ones(NB,NR);
genlst   = [1,5,10,20,32];
nbad     = 0;

str      = ["For five virtual generators of the 101";..
" This test generates "+string(NB)+" numbers then resets the block";..
"      and does it again";..
" Any disagreements are reported -- there should be none"];

write (%io(2),str);

//
//     Set up Generators
//
distfun_seedset(12345,54321,6789,9876);

//
//     For a selected set of generators
//
for ixgen = 1:5
    igen = genlst(ixgen);
    distfun_streamset(igen);
    write(%io(2)," Testing generator "+string(igen));
    //
    //     Use NB blocks
    //
    distfun_streaminit(-1);
    SD=distfun_seedget();
    iseed1=SD(1);
    iseed2=SD(2);
    for iblock = 1:NB
        //     Generate NR numbers
        answer(iblock,1:NR)= distfun_unifrnd(0.,1.,1,NR);
        distfun_streaminit(1);
    end
    distfun_streaminit(-1);
    //
    //     Do it again and compare answers
    //
    SD=distfun_seedget();
    iseed1=SD(1);
    iseed2=SD(2);
    //
    //     Use NB blocks
    //
    for iblock = 1:NB
        //     Generate NR numbers
        itmp = distfun_unifrnd(0.,1.,1,NR);
        if ( itmp<>answer(iblock,:) ) then
            str=["     ERROR : Disagreement on regeneration of numbers";...
            " Block "+string(iblock)+" N within Block "];
            write(%io(2),str);
        end

        distfun_streaminit(1);

    end
    write (%io(2), "     Finished testing generator "+string(igen));
    write (%io(2), "     Test completed successfully");
end
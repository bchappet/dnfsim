// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2011 - INRIA - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function distfun_builderUnifrng()
    src_dir = get_absolute_file_path("builder_unifrng.sce");


    src_path = "c";
    linknames = ["unifrng"];
    files = [
    "clcg2.c"    
    "clcg4.c"    
    "fsultra.c"  
    "kiss.c"     
    "mt.c"        
    "unifrng.c"
    "unifrng_phraseToSeed.c"
    "urand.c"
    "i_indx.c"
    "crand.c"
    ];
    ldflags = "";

    if ( getos() == "Windows" ) then
        cflags = "-DWIN32 -DLIBUNIFRNG_EXPORTS";
    else
        include1 = src_dir;
        cflags = "-I"""+include1+"""";
    end

    libs = [
    ];
    tbx_build_src(linknames, files, src_path, src_dir, libs, ldflags, cflags);

endfunction 
distfun_builderUnifrng();
clear distfun_builderUnifrng;

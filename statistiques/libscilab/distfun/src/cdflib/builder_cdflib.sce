// Copyright (C) 2011 - 2014 - Michael Baudin
// Copyright (C) 2011 - INRIA - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function distfun_builderCdflib()
    src_dir = get_absolute_file_path("builder_cdflib.sce");


    src_path = "c";
    linknames = ["cdflib"];
    files = [
    "betafunction.c"
    "brent.c"
    "cdfbet.c"
    "cdfbin.c"
    "cdfchi.c"
    "cdfchn.c"
    "cdfhyge.c"
    "cdfev.c"
    "cdfexp.c"
    "cdff.c"
    "cdfgam.c"
    "cdfgeo.c"
    "cdfks.c"
    "cdflib.c"
    "cdflogn.c"
    "cdfmn.c"
    "cdfmvn.c"
    "cdfnbn.c"
    "cdfncf.c"
    "cdfnct.c"
    "cdfnor.c"
    "cdfpoi.c"
    "cdft.c"
    "cdfunif.c"
    "cdfwbl.c"
    "devlpl.c"
    "dinvnr.c"
    "erf.c"
    "esum.c"
    "exparg.c"
    "expm1.c"
    "gammafunction.c"
    "genprm.c"
    "log1p.c"
    "logfunction.c"
    "linearalgebra.c"
    "sexpo.c"
    "sgamma.c"
    "snorm.c"
    "incgam.c"    ];
    ldflags = "";

    if ( getos() == "Windows" ) then
        cflags = "-DWIN32 -DLIBDISTFUN_C_EXPORTS";
    else
        include1 = src_dir;
        cflags = "-I"""+include1+"""";
    end

    libs = [
    ];
    tbx_build_src(linknames, files, src_path, src_dir, libs, ldflags, cflags);

endfunction 
distfun_builderCdflib();
clear distfun_builderCdflib;

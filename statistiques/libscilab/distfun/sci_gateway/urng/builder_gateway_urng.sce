// ====================================================================
// Copyright (C) 2012 - Michael Baudin
// This file is released into the public domain
// ====================================================================

function distfun_builderGwUrng()
    gateway_path = get_absolute_file_path("builder_gateway_urng.sce");

    libname = "distfunurnggateway";
    namelist = [
    "distfun_seedget"   "sci_distfun_seedget"
    "distfun_seedset" "sci_distfun_seedset"
    "distfun_genget" "sci_distfun_genget"
    "distfun_genset" "sci_distfun_genset"
    "distfun_streamset" "sci_distfun_streamset"
    "distfun_streamget" "sci_distfun_streamget"
    "distfun_streaminit" "sci_distfun_streaminit"
    ];
    files = [
    "sci_distfun_seedget.c"
    "sci_distfun_seedset.c"
    "sci_distfun_genget.c"
    "sci_distfun_genset.c"
    "sci_distfun_streamset.c"
    "sci_distfun_streamget.c"
    "sci_distfun_streaminit.c"
    "gw_distfunurng_support.c"
    ];

    ldflags = ""

    if ( getos() == "Windows" ) then
        include1 = "..\..\src\unifrng";
        include2 = "..\..\src\gwsupport";
        include3 = SCI+"/modules/output_stream/includes";
        cflags = "-DWIN32"+..
		" -I"""+include1+""""+..
		" -I"""+include2+""""+..
		" -I"""+include3+"""";
    else
        include1 = gateway_path;
        include2 = gateway_path+"../../src/unifrng";
        include3 = gateway_path+"../../src/gwsupport";
        include4 = SCI+"/../../include/scilab/localization";
        cflags = "-I"""+include1+""""+..
		" -I"""+include2+""""+..
		" -I"""+include3+""""+..
		" -I"""+include4+"""";
    end
    // Caution : the order matters !
    libs = [
    "../../src/unifrng/libunifrng"
    "../../src/gwsupport/libgwsupport"
    ];

    tbx_build_gateway(libname, namelist, files, gateway_path, libs, ldflags, cflags);

endfunction
distfun_builderGwUrng();
clear distfun_builderGwUrng;

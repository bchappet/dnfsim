// ====================================================================
// Copyright (C) 2012 - Michael Baudin
// This file is released into the public domain
// ====================================================================

function distfun_builderGatewayC()
    gateway_path = get_absolute_file_path("builder_gateway_driver.sce");

    libname = "distfundrivergateway";
    namelist = [
    "distfun_startup"   "sci_distfun_startup"
    "distfun_verboseset"   "sci_distfun_verboseset"
    ];
    files = [
    "sci_distfun_startup.c"
    "sci_distfun_verboseset.c"
    ];

    ldflags = ""

    if ( getos() == "Windows" ) then
        include1 = "..\..\src\unifrng";
        include2 = "..\..\src\gwsupport";
        include3 = "..\..\src\cdflib";
        include4 = SCI+"/modules/output_stream/includes";
        cflags = "-DWIN32"+..
		" -I"""+include1+""""+..
		" -I"""+include2+""""+..
		" -I"""+include3+""""+..
		" -I"""+include4+"""";
    else
        include1 = gateway_path;
        include2 = gateway_path+"../../src/unifrng";
        include3 = gateway_path+"../../src/gwsupport";
        include4 = gateway_path+"../../src/cdflib";
        include5 = SCI+"/../../include/scilab/localization";
        cflags = "-I"""+include1+""""+..
		" -I"""+include2+""""+..
		" -I"""+include3+""""+..
		" -I"""+include4+""""+..
		" -I"""+include5+"""";
    end
    // Caution : the order matters !
    libs = [
    "../../src/gwsupport/libgwsupport"
    "../../src/unifrng/libunifrng"
    "../../src/cdflib/libcdflib"
    ];

    tbx_build_gateway(libname, namelist, files, gateway_path, libs, ldflags, cflags);

endfunction
distfun_builderGatewayC();
clear distfun_builderGatewayC;


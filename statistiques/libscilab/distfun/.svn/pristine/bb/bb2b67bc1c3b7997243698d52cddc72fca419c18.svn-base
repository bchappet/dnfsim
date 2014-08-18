// Copyright (C) 2012 - 2014 - Michael Baudin
// This file is released into the public domain

function distfun_builderGatewayC()
    gateway_path = get_absolute_file_path("builder_gateway_rnd.sce");

    libname = "distfungrandgateway";
    namelist = [
    "distfun_betarnd" "sci_distfun_rndbeta"
    "distfun_normrnd" "sci_distfun_rndnorm"
    "distfun_unifrnd" "sci_distfun_rndunf"
    "distfun_binornd" "sci_distfun_rndbino"
    "distfun_chi2rnd" "sci_distfun_rndchi2"
    "distfun_exprnd" "sci_distfun_rndexp"
    "distfun_evrnd" "sci_distfun_rndev"
    "distfun_frnd" "sci_distfun_rndf"
    "distfun_gamrnd" "sci_distfun_rndgam"
    "distfun_geornd" "sci_distfun_rndgeom"
    "distfun_hygernd" "sci_distfun_rndhyge"
    "distfun_lognrnd" "sci_distfun_rndlogn"
    "distfun_poissrnd" "sci_distfun_rndpoiss"
    "distfun_trnd" "sci_distfun_rndt"
    "distfun_unidrnd" "sci_distfun_rndunid"
    "distfun_mnrnd" "sci_distfun_rndmn"
    "distfun_nbinrnd" "sci_distfun_rndnbn"
    "distfun_ncx2rnd" "sci_distfun_rndncx2"
    "distfun_permrnd" "sci_distfun_rndprm"
    "distfun_rndmvn" "sci_distfun_rndmvn"
    "distfun_ncfrnd" "sci_distfun_rndncf"
    "distfun_nctrnd" "sci_distfun_rndnct"
    "distfun_wblrnd" "sci_distfun_rndwbl"
    "distfun_ksrnd" "sci_distfun_rndks"
    ];
    files = [
    "sci_distfun_rndbeta.c"
    "sci_distfun_rndf.c"
    "sci_distfun_rndmn.c"
    "sci_distfun_rndgam.c"
    "sci_distfun_rndnorm.c"
    "sci_distfun_rndunf.c"
    "sci_distfun_rndunid.c"
    "sci_distfun_rndnbn.c"
    "sci_distfun_rndbino.c"
    "sci_distfun_rndmvn.c"
    "sci_distfun_rndncx2.c"
    "sci_distfun_rndncf.c"
    "sci_distfun_rndchi2.c"
    "sci_distfun_rndexp.c"
    "sci_distfun_rndpoiss.c"
    "sci_distfun_rndprm.c"
    "sci_distfun_rndgeom.c"
    "sci_distfun_rndlogn.c"
    "sci_distfun_rndt.c"
    "sci_distfun_rndhyge.c"
    "sci_distfun_rndnct.c"
    "sci_distfun_rndwbl.c"
    "sci_distfun_rndev.c"
    "sci_distfun_rndks.c"
    "gw_distfunrnd_support.c"
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
    "../../src/cdflib/libcdflib"
    "../../src/unifrng/libunifrng"
    "../../src/gwsupport/libgwsupport"
    ];

    tbx_build_gateway(libname, namelist, files, gateway_path, libs, ldflags, cflags);

endfunction
distfun_builderGatewayC();
clear distfun_builderGatewayC;

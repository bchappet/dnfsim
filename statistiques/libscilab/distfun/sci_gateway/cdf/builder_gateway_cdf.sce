// ====================================================================
// Copyright (C) 2012 - 2014 - Michael Baudin
// This file is released into the public domain
// ====================================================================

function distfun_builderGatewayC()
    gateway_path = get_absolute_file_path("builder_gateway_cdf.sce");

    libname = "distfuncdfgateway";
    namelist = [
    "distfun_cdfbeta"  "sci_distfun_cdfbeta"
    "distfun_cdfbino"  "sci_distfun_cdfbino"
    "distfun_cdfchi2"  "sci_distfun_cdfchi2"
    "distfun_cdfncx2"  "sci_distfun_cdfncx2"
    "distfun_cdff"    "sci_distfun_cdff"
    "distfun_cdfncf"  "sci_distfun_cdfncf"
    "distfun_cdfgam"  "sci_distfun_cdfgam"
    "distfun_cdfks"  "sci_distfun_cdfks"
    "distfun_cdfnbn"  "sci_distfun_cdfnbn"
    "distfun_cdfnorm"  "sci_distfun_cdfnorm"
    "distfun_cdfpoiss"  "sci_distfun_cdfpoiss"
    "distfun_cdft"    "sci_distfun_cdft"
    "distfun_cdfhyge" "sci_distfun_cdfhyge"
    "distfun_cdfunif" "sci_distfun_cdfunif"
    "distfun_cdfexp" "sci_distfun_cdfexp"
    "distfun_cdfgeo" "sci_distfun_cdfgeo"
    "distfun_cdflogn" "sci_distfun_cdflogn"
    "distfun_cdfnct"    "sci_distfun_cdfnct"
    "distfun_cdfwbl"  "sci_distfun_cdfwbl"
    "distfun_cdfev"  "sci_distfun_cdfev"
    "distfun_invpoiss" "sci_distfun_invpoiss"
    "distfun_invbeta" "sci_distfun_invbeta"
    "distfun_invbino" "sci_distfun_invbino"
    "distfun_invchi2" "sci_distfun_invchi2"
    "distfun_invncx2" "sci_distfun_invncx2"
    "distfun_invf" "sci_distfun_invf"
    "distfun_invks" "sci_distfun_invks"
    "distfun_invnbn" "sci_distfun_invnbn"
    "distfun_invt" "sci_distfun_invt"
    "distfun_invgam" "sci_distfun_invgam"
    "distfun_invnorm" "sci_distfun_invnorm"
    "distfun_invncf" "sci_distfun_invncf"
    "distfun_invunif" "sci_distfun_invunif"
    "distfun_invhyge" "sci_distfun_invhyge"
    "distfun_invexp" "sci_distfun_invexp"
    "distfun_invgeo" "sci_distfun_invgeo"
    "distfun_invlogn" "sci_distfun_invlogn"
    "distfun_invnct" "sci_distfun_invnct"
    "distfun_invwbl" "sci_distfun_invwbl"
    "distfun_invev" "sci_distfun_invev"
    "distfun_pdfhyge" "sci_distfun_pdfhyge"
    "distfun_pdfgam" "sci_distfun_pdfgam"
    "distfun_pdff" "sci_distfun_pdff"
    "distfun_pdfncf" "sci_distfun_pdfncf"
    "distfun_pdfbino" "sci_distfun_pdfbino"
    "distfun_pdft" "sci_distfun_pdft"
    "distfun_pdfunif" "sci_distfun_pdfunif"
    "distfun_pdfnorm" "sci_distfun_pdfnorm"
    "distfun_pdfbeta" "sci_distfun_pdfbeta"
    "distfun_pdfexp" "sci_distfun_pdfexp"
    "distfun_pdfgeo" "sci_distfun_pdfgeo"
    "distfun_pdflogn" "sci_distfun_pdflogn"
    "distfun_pdfpoiss" "sci_distfun_pdfpoiss"
    "distfun_pdfchi2" "sci_distfun_pdfchi2"
    "distfun_pdfnbn" "sci_distfun_pdfnbn"
    "distfun_pdfncx2" "sci_distfun_pdfncx2"
    "distfun_pdfnct" "sci_distfun_pdfnct"
    "distfun_mvnpdf" "sci_distfun_pdfmvn" // This one is direct.
    "distfun_pdfwbl" "sci_distfun_pdfwbl"
    "distfun_pdfev" "sci_distfun_pdfev"
    "distfun_pdfks" "sci_distfun_pdfks"
    "distfun_incgamma"   "sci_distfun_incgamma"
    ];
    files = [
    "sci_distfun_cdfbeta.c"
    "sci_distfun_cdfbino.c"
    "sci_distfun_cdfchi2.c"
    "sci_distfun_cdfncx2.c"
    "sci_distfun_cdff.c"
    "sci_distfun_cdfncf.c"
    "sci_distfun_cdfgam.c"
    "sci_distfun_cdfnbn.c"
    "sci_distfun_cdfnorm.c"
    "sci_distfun_cdfpoiss.c"
    "sci_distfun_cdft.c"
    "sci_distfun_cdfhyge.c"
    "sci_distfun_cdfunif.c"
    "sci_distfun_cdfexp.c"
    "sci_distfun_cdfgeo.c"
    "sci_distfun_cdfks.c"
    "sci_distfun_cdflogn.c"
    "sci_distfun_cdfnct.c"
    "sci_distfun_cdfwbl.c"
    "sci_distfun_cdfev.c"
    "sci_distfun_invpoiss.c"
    "sci_distfun_invbeta.c"
    "sci_distfun_invbino.c"
    "sci_distfun_invchi2.c"
    "sci_distfun_invncx2.c"
    "sci_distfun_invf.c"
    "sci_distfun_invks.c"
    "sci_distfun_invt.c"
    "sci_distfun_invncf.c"
    "sci_distfun_invgam.c"
    "sci_distfun_invnbn.c"
    "sci_distfun_invnorm.c"
    "sci_distfun_invhyge.c"
    "sci_distfun_invunif.c"
    "sci_distfun_invexp.c"
    "sci_distfun_invgeo.c"
    "sci_distfun_invlogn.c"
    "sci_distfun_invnct.c"
    "sci_distfun_invwbl.c"
    "sci_distfun_invev.c"
    "sci_distfun_pdfhyge.c"
    "sci_distfun_pdfgam.c"
    "sci_distfun_pdff.c"
    "sci_distfun_pdfncf.c"
    "sci_distfun_pdfbino.c"
    "sci_distfun_pdft.c"
    "sci_distfun_pdfunif.c"
    "sci_distfun_pdfnorm.c"
    "sci_distfun_pdfbeta.c"
    "sci_distfun_pdfexp.c"
    "sci_distfun_pdfgeo.c"
    "sci_distfun_pdflogn.c"
    "sci_distfun_pdfpoiss.c"
    "sci_distfun_pdfchi2.c"
    "sci_distfun_pdfnbn.c"
    "sci_distfun_pdfncx2.c"
    "sci_distfun_pdfnct.c"
    "sci_distfun_pdfmvn.c"
    "sci_distfun_pdfwbl.c"
    "sci_distfun_pdfev.c"
    "sci_distfun_pdfks.c"
    "gw_distfuncdf_support.c"
    "sci_distfun_incgamma.c"
    ];

    ldflags = ""

    if ( getos() == "Windows" ) then
        include1 = "..\..\src\cdflib";
        include2 = "..\..\src\gwsupport";
        include3 = SCI+"\modules\output_stream\includes";
        cflags = "-DWIN32 "+..
        " -I"""+include1+""""+..
        " -I"""+include2+""""+..
        " -I"""+include3+"""";
    else
        include1 = gateway_path;
        include2 = gateway_path+"../../src/cdflib";
        include3 = gateway_path+"../../src/gwsupport";
        include5 = SCI+"/../../include/scilab/localization";
        cflags = "-I"""+include1+""""+..
        " -I"""+include2+""""+..
        " -I"""+include3+""""+..
        " -I"""+include5+"""";
    end
    // Caution : the order matters !
    libs = [
    "../../src/cdflib/libcdflib"
    "../../src/gwsupport/libgwsupport"
    ];

    tbx_build_gateway(libname, namelist, files, gateway_path, libs, ldflags, cflags);

endfunction
distfun_builderGatewayC();
clear distfun_builderGatewayC;

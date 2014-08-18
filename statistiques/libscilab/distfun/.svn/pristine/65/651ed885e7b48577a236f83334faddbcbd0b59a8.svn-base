// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2011 - INRIA - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function distfun_builderGwsupport()
    src_dir = get_absolute_file_path("builder_gwsupport.sce");

    src_path = "c";
    linknames = ["gwsupport"];
    files = [
    "gwsupport.c"    
    ];
    ldflags = "";

    if ( getos() == "Windows" ) then
        include1 = SCI+"\modules\output_stream\includes";
        include2 = SCI+"\modules\api_scilab\includes";
        include3 = SCI+"\modules\core\includes";
        include4 = SCI+"\modules\localization\includes";
        cflags = "-DWIN32 -DLIBGWSUPPORT_EXPORTS"+..
		" -I"""+include1+""""+..
		" -I"""+include2+""""+..
		" -I"""+include3+""""+..
		" -I"""+include4+"""";
    else
        include1 = src_dir;
        include2 = SCI+"/../../include/scilab/";
        cflags = "-I"""+include1+""""+..
		" -I"""+include2+"""";
    end
    libs = [
    ];

    tbx_build_src(linknames, files, src_path, src_dir, libs, ldflags, cflags);

endfunction 
distfun_builderGwsupport();
clear distfun_builderGwsupport;

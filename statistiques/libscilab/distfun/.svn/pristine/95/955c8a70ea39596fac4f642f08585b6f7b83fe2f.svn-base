// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function distfun_buildToolbox()

    mode(-1);
    lines(0);
    try
        getversion("scilab");
    catch
        error(gettext("Scilab 5.0 or more is required."));  
    end;
    // ====================================================================
    if ~with_module("development_tools") then
        error(msprintf(gettext("%s module not installed."),"development_tools"));
    end
    // Uncomment this line to make a debug version of the Toolbox
    //setenv("DEBUG_SCILAB_DYNAMIC_LINK","YES")
	setenv("__USE_DEPRECATED_STACK_FUNCTIONS__","YES");
    // ====================================================================
    TOOLBOX_NAME = "distfun";
    TOOLBOX_TITLE = "Distfun";
    // ====================================================================
    toolbox_dir = get_absolute_file_path("builder.sce");

    tbx_builder_src(toolbox_dir);
    tbx_builder_gateway(toolbox_dir);
    tbx_builder_macros(toolbox_dir);
    tbx_builder_help(toolbox_dir);
    tbx_build_loader(TOOLBOX_NAME, toolbox_dir);
    tbx_build_cleaner(TOOLBOX_NAME, toolbox_dir);

endfunction 

distfun_buildToolbox();
clear distfun_buildToolbox;

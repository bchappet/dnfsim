// ====================================================================
// 2010 - DIGITEO - Michael Baudin
// 2011 - DIGITEO - Allan CORNET
// ====================================================================
function clean_help()
  libpath = get_absolute_file_path('cleanhelp.sce');
  exec(fullfile(libpath,"en_US","cleanhelp.sce"));
endfunction
// ====================================================================
clean_help();
clear clean_help;
// ====================================================================

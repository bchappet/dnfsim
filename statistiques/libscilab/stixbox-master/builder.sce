// =============================================================================
// Copyright DIGITEO - 2008 - 2011 - Allan CORNET
// =============================================================================
mode(-1);
// =============================================================================
function builder_main()

  // Check Scilab's version
  // =============================================================================
  try
    v = getversion("scilab");
  catch
    error(gettext("Scilab 5.4 or more is required."));
  end

  // =============================================================================
  // Check modules_manager module availability
  // =============================================================================
  if ~isdef('tbx_build_loader') then
    error(msprintf(gettext('%s module not installed."), 'modules_manager'));
  end
  // =============================================================================
  TOOLBOX_NAME = 'stixbox';
  TOOLBOX_TITLE = 'Stixbox';
  // =============================================================================
  toolbox_dir = get_absolute_file_path('builder.sce');

  tbx_builder_macros(toolbox_dir);
  tbx_builder_help(toolbox_dir);
  tbx_build_loader(TOOLBOX_NAME, toolbox_dir);
  tbx_build_cleaner(TOOLBOX_NAME, toolbox_dir);
endfunction 
// =============================================================================
builder_main();
clear builder_main;
// =============================================================================


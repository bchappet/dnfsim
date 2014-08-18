// Copyright (C) 2013 - Michael Baudin
// Copyright (C) 2010 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// Updates the .xml files by deleting existing files and 
// creating them again from the .sci with help_from_sci.

//
cwd = get_absolute_file_path("update_help.sce");
mprintf("Working dir = %s\n",cwd);
//
// Generate the misc help
mprintf("Updating stixbox/misc\n");
helpdir = fullfile(cwd,"misc");
funmat = [
  "cov"
  "corrcoef"
  "quantile"
  "ksdensity"
  "betaln"
  ];
macrosdir = cwd +"../../macros";
demosdir = [];
modulename = "stixbox";
helptbx_helpupdate ( funmat , helpdir , macrosdir , demosdir , modulename , %t );
//
// Generate the regression help
mprintf("Updating stixbox/regression\n");
helpdir = fullfile(cwd,"regression");
funmat = [
  "regres"
  "regresprint"
  ];
macrosdir = cwd +"../../macros";
demosdir = [];
modulename = "stixbox";
helptbx_helpupdate ( funmat , helpdir , macrosdir , demosdir , modulename , %t );
//
// Generate the resampling help
mprintf("Updating stixbox/resampling\n");
helpdir = fullfile(cwd,"resampling");
funmat = [
  "ciboot"
  "covboot"
  "covjack"
  "rboot"
  "stdjack"
  "stdboot"
  ];
macrosdir = cwd +"../../macros";
demosdir = [];
modulename = "stixbox";
helptbx_helpupdate ( funmat , helpdir , macrosdir , demosdir , modulename , %t );
//
// Generate the graphics help
mprintf("Updating stixbox/graphics\n");
helpdir = fullfile(cwd,"graphics");
funmat = [
  "histo"
  "identify"
  "qqplot"
  "stairs"
  "plotmatrix"
  "plotsym"
  "bubblechart"
  "bubblematrix"
  ];
macrosdir = cwd +"../../macros";
demosdir = [];
modulename = "stixbox";
helptbx_helpupdate ( funmat , helpdir , macrosdir , demosdir , modulename , %t );
//
// Generate the datasets help
mprintf("Updating stixbox/datasets\n");
helpdir = fullfile(cwd,"datasets");
funmat = [
  "getdata"
  ];
macrosdir = cwd +"../../macros";
demosdir = [];
modulename = "stixbox";
helptbx_helpupdate ( funmat , helpdir , macrosdir , demosdir , modulename , %t );
//
// Generate the polynomials help
mprintf("Updating stixbox/polynomials\n");
helpdir = fullfile(cwd,"polynomials");
funmat = [
  "polyfit"
  "polyval"
  ];
macrosdir = cwd +"../../macros";
demosdir = [];
modulename = "stixbox";
helptbx_helpupdate ( funmat , helpdir , macrosdir , demosdir , modulename , %t );

//
// Generate the tests help
mprintf("Updating stixbox/tests\n");
helpdir = fullfile(cwd,"tests");
funmat = [
  "test1b"
  "test1n"
  "test2n"
  "test1r"
  "test2r"
  ];
macrosdir = cwd +"../../macros";
demosdir = [];
modulename = "stixbox";
helptbx_helpupdate ( funmat , helpdir , macrosdir , demosdir , modulename , %t );


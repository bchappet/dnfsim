// Copyright (C) 2012 - 2014 - Michael Baudin
// Copyright (C) 2010 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt


////////////////////////////////////////////////////////////////////////////
cwd = get_absolute_file_path("update_help.sce");
mprintf("Working dir = %s\n",cwd);
//
modulename = "distfun";
demosdir = [];
//
mprintf("Updating beta\n");
funarray = [
  "distfun_betacdf"
  "distfun_betainv"
  "distfun_betapdf"
  "distfun_betastat"
  "distfun_betafitmm"
  ];
helpdir = fullfile(cwd,"beta");
macrosdir = fullfile(cwd ,"..","..","macros");
helptbx_helpupdate ( funarray , helpdir , macrosdir , demosdir , modulename , %t );
//
mprintf("Updating binom\n");
funarray = [
  "distfun_binocdf"
  "distfun_binoinv"
  "distfun_binopdf"
  "distfun_binostat"
  "distfun_binofitmm"
  ];
helpdir = fullfile(cwd,"bino");
macrosdir = fullfile(cwd ,"..","..","macros");
helptbx_helpupdate ( funarray , helpdir , macrosdir , demosdir , modulename , %t );
//
mprintf("Updating chi2\n");
funarray = [
  "distfun_chi2pdf"
  "distfun_chi2cdf"
  "distfun_chi2inv"
  "distfun_chi2stat"
  ];
helpdir = fullfile(cwd,"chi2");
macrosdir = fullfile(cwd ,"..","..","macros");
helptbx_helpupdate ( funarray , helpdir , macrosdir , demosdir , modulename , %t );
//
mprintf("Updating exp\n");
funarray = [
  "distfun_exppdf"
  "distfun_expcdf"
  "distfun_expinv"
  "distfun_expstat"
  ];
helpdir = fullfile(cwd,"exp");
macrosdir = fullfile(cwd ,"..","..","macros");
helptbx_helpupdate ( funarray , helpdir , macrosdir , demosdir , modulename , %t );
//
mprintf("Updating gam\n");
funarray = [
  "distfun_gampdf"
  "distfun_gamcdf"
  "distfun_gaminv"
  "distfun_gamstat"
  "distfun_gamfitmm"
  ];
helpdir = fullfile(cwd,"gam");
macrosdir = fullfile(cwd ,"..","..","macros");
helptbx_helpupdate ( funarray , helpdir , macrosdir , demosdir , modulename , %t );
//
mprintf("Updating geo\n");
funarray = [
  "distfun_geopdf"
  "distfun_geocdf"
  "distfun_geoinv"
  "distfun_geostat"
  ];
helpdir = fullfile(cwd,"geo");
macrosdir = fullfile(cwd ,"..","..","macros");
helptbx_helpupdate ( funarray , helpdir , macrosdir , demosdir , modulename , %t );
//
mprintf("Updating hyge\n");
funarray = [
  "distfun_hygepdf"
  "distfun_hygecdf"
  "distfun_hygeinv"
  "distfun_hygestat"
  "distfun_hygetable"
  ];
helpdir = fullfile(cwd,"hyge");
macrosdir = fullfile(cwd ,"..","..","macros");
helptbx_helpupdate ( funarray , helpdir , macrosdir , demosdir , modulename , %t );
//
mprintf("Updating logn\n");
funarray = [
  "distfun_lognpdf"
  "distfun_logncdf"
  "distfun_logninv"
  "distfun_lognstat"
  ];
helpdir = fullfile(cwd,"logn");
macrosdir = fullfile(cwd ,"..","..","macros");
helptbx_helpupdate ( funarray , helpdir , macrosdir , demosdir , modulename , %t );
//
mprintf("Updating norm\n");
funarray = [
  "distfun_normcdf"
  "distfun_normpdf"
  "distfun_norminv"
  "distfun_normstat"
  ];
helpdir = fullfile(cwd,"norm");
macrosdir = fullfile(cwd ,"..","..","macros");
helptbx_helpupdate ( funarray , helpdir , macrosdir , demosdir , modulename , %t );
//
mprintf("Updating poiss\n");
funarray = [
  "distfun_poisspdf"
  "distfun_poisscdf"
  "distfun_poissinv"
  "distfun_poissstat"
  ];
helpdir = fullfile(cwd,"poiss");
macrosdir = fullfile(cwd ,"..","..","macros");
helptbx_helpupdate ( funarray , helpdir , macrosdir , demosdir , modulename , %t );
//
mprintf("Updating unif\n");
funarray = [
  "distfun_unifpdf"
  "distfun_unifcdf"
  "distfun_unifinv"
  "distfun_unifstat"
  "distfun_uniffitmm"
  ];
helpdir = fullfile(cwd,"unif");
macrosdir = fullfile(cwd ,"..","..","macros");
helptbx_helpupdate ( funarray , helpdir , macrosdir , demosdir , modulename , %t );
//
//
mprintf("Updating F\n");
funarray = [
  "distfun_fpdf"
  "distfun_fcdf"
  "distfun_finv"
  "distfun_fstat"
  ];
helpdir = fullfile(cwd,"f");
macrosdir = fullfile(cwd ,"..","..","macros");
helptbx_helpupdate ( funarray , helpdir , macrosdir , demosdir , modulename , %t );
//
//
mprintf("Updating T\n");
funarray = [
  "distfun_tpdf"
  "distfun_tcdf"
  "distfun_tinv"
  "distfun_tstat"
  ];
helpdir = fullfile(cwd,"t");
macrosdir = fullfile(cwd ,"..","..","macros");
helptbx_helpupdate ( funarray , helpdir , macrosdir , demosdir , modulename , %t );
//
mprintf("Updating urng\n");
funarray = [
  "distfun_genget"
  "distfun_genset"
  "distfun_seedget"
  "distfun_seedset"
  "distfun_streamget"
  "distfun_streamset"
  "distfun_streaminit"
  ];
helpdir = fullfile(cwd,"urng");
macrosdir = fullfile(cwd ,"urng","pseudomacros");
helptbx_helpupdate ( funarray , helpdir , macrosdir , demosdir , modulename , %t );
//
mprintf("Updating logu\n");
funarray = [
  "distfun_logupdf"
  "distfun_logucdf"
  "distfun_loguinv"
  "distfun_logurnd"
  "distfun_logustat"
  ];
helpdir = fullfile(cwd,"logu");
macrosdir = fullfile(cwd ,"..","..","macros");
helptbx_helpupdate ( funarray , helpdir , macrosdir , demosdir , modulename , %t );
//
mprintf("Updating tnorm\n");
funarray = [
  "distfun_tnormpdf"
  "distfun_tnormcdf"
  "distfun_tnorminv"
  "distfun_tnormrnd"
  "distfun_tnormstat"
  ];
helpdir = fullfile(cwd,"tnorm");
macrosdir = fullfile(cwd ,"..","..","macros");
helptbx_helpupdate ( funarray , helpdir , macrosdir , demosdir , modulename , %t );
//
mprintf("Updating ev\n");
funarray = [
  "distfun_evcdf"
  "distfun_evinv"
  "distfun_evpdf"
  "distfun_evstat"
  ];
helpdir = fullfile(cwd,"ev");
macrosdir = fullfile(cwd ,"..","..","macros");
helptbx_helpupdate ( funarray , helpdir , macrosdir , demosdir , modulename , %t );
//
mprintf("Updating mn\n");
funarray = [
  "distfun_mnpdf"
  "distfun_mnstat"
  ];
helpdir = fullfile(cwd,"mn");
macrosdir = fullfile(cwd ,"..","..","macros");
helptbx_helpupdate ( funarray , helpdir , macrosdir , demosdir , modulename , %t );
//
mprintf("Updating unid\n");
funarray = [
  "distfun_unidpdf"
  "distfun_unidstat"
  "distfun_unidcdf"
  "distfun_unidinv"
  ];
helpdir = fullfile(cwd,"unid");
macrosdir = fullfile(cwd ,"..","..","macros");
helptbx_helpupdate ( funarray , helpdir , macrosdir , demosdir , modulename , %t );
//
mprintf("Updating nbin\n");
funarray = [
  "distfun_nbinpdf"
  "distfun_nbincdf"
  "distfun_nbinstat"
  "distfun_nbininv"
  ];
helpdir = fullfile(cwd,"nbin");
macrosdir = fullfile(cwd ,"..","..","macros");
helptbx_helpupdate ( funarray , helpdir , macrosdir , demosdir , modulename , %t );
//
mprintf("Updating ncx2\n");
funarray = [
  "distfun_ncx2cdf"
  "distfun_ncx2stat"
  "distfun_ncx2inv"
  "distfun_ncx2pdf"
  ];
helpdir = fullfile(cwd,"ncx2");
macrosdir = fullfile(cwd ,"..","..","macros");
helptbx_helpupdate ( funarray , helpdir , macrosdir , demosdir , modulename , %t );
//
mprintf("Updating ncf\n");
funarray = [
  "distfun_ncfcdf"
  "distfun_ncfstat"
  "distfun_ncfinv"
  "distfun_ncfpdf"
  ];
helpdir = fullfile(cwd,"ncf");
macrosdir = fullfile(cwd ,"..","..","macros");
helptbx_helpupdate ( funarray , helpdir , macrosdir , demosdir , modulename , %t );
//
mprintf("Updating nct\n");
funarray = [
  "distfun_nctcdf"
  "distfun_nctstat"
  "distfun_nctinv"
  "distfun_nctpdf"
  ];
helpdir = fullfile(cwd,"nct");
macrosdir = fullfile(cwd ,"..","..","macros");
helptbx_helpupdate ( funarray , helpdir , macrosdir , demosdir , modulename , %t );
//
mprintf("Updating mvn\n");
// Nothing to do.
//
mprintf("Updating distfun/support\n");
funarray = [
  "distfun_erfcinv"
  "distfun_getpath"
  "distfun_plotintcdf"
  "distfun_inthisto"
  "distfun_genericpdf"
  "distfun_gammainc"
  "distfun_betainc"
  ];
helpdir = fullfile(cwd,"support");
macrosdir = fullfile(cwd ,"..","..","macros");
helptbx_helpupdate ( funarray , helpdir , macrosdir , demosdir , modulename , %t );
//
mprintf("Updating weibull\n");
funarray = [
  "distfun_wblcdf"
  "distfun_wblpdf"
  "distfun_wblinv"
  "distfun_wblstat"
  "distfun_wblfit"
  "distfun_wblfitmm"
  "distfun_wbllike"
  "distfun_wblplot"
  ];
helpdir = fullfile(cwd,"wbl");
macrosdir = fullfile(cwd ,"..","..","macros");
helptbx_helpupdate ( funarray , helpdir , macrosdir , demosdir , modulename , %t );
//
mprintf("Updating ks\n");
funarray = [
  "distfun_kscdf"
  "distfun_ksinv"
  "distfun_kspdf"
  "distfun_ksstat"
  ];
helpdir = fullfile(cwd,"ks");
macrosdir = fullfile(cwd ,"..","..","macros");
helptbx_helpupdate ( funarray , helpdir , macrosdir , demosdir , modulename , %t );
//
mprintf("Updating distfun/pseudomacros\n");
function distfun_updatepseumac(funcname,targetdir)
    // Update the help page of the function funcname from 
	// the pseudomacros dir to targetdir.
    helptbx_helpupdate ( funcname, fullfile(cwd,targetdir), ..
      fullfile(cwd ,"pseudomacros"), [], "distfun", %t );
endfunction
distfun_updatepseumac ( "distfun_verboseset", "support");
distfun_updatepseumac ( "distfun_normrnd", "norm");
distfun_updatepseumac ( "distfun_unifrnd", "unif");
distfun_updatepseumac ( "distfun_betarnd", "beta");
distfun_updatepseumac ( "distfun_binornd", "bino");
distfun_updatepseumac ( "distfun_chi2rnd", "chi2");
distfun_updatepseumac ( "distfun_exprnd", "exp");
distfun_updatepseumac ( "distfun_frnd", "f");
distfun_updatepseumac ( "distfun_gamrnd", "gam");
distfun_updatepseumac ( "distfun_geornd", "geo");
distfun_updatepseumac ( "distfun_hygernd", "hyge");
distfun_updatepseumac ( "distfun_lognrnd", "logn");
distfun_updatepseumac ( "distfun_poissrnd", "poiss");
distfun_updatepseumac ( "distfun_trnd", "t");
distfun_updatepseumac ( "distfun_mnrnd", "mn");
distfun_updatepseumac ( "distfun_unidrnd", "unid");
distfun_updatepseumac ( "distfun_permrnd", "support");
distfun_updatepseumac ( "distfun_nbinrnd", "nbin");
distfun_updatepseumac ( "distfun_ncx2rnd", "ncx2");
distfun_updatepseumac ( "distfun_ncfrnd", "ncf");
distfun_updatepseumac ( "distfun_nctrnd", "nct");
distfun_updatepseumac ( "distfun_mvnrnd", "mvn");
distfun_updatepseumac ( "distfun_mvnpdf", "mvn");
distfun_updatepseumac ( "distfun_wblrnd", "wbl");
distfun_updatepseumac ( "distfun_evrnd", "ev");
distfun_updatepseumac ( "distfun_ksrnd", "ks");

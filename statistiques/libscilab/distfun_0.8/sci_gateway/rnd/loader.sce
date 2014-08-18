// This file is released under the 3-clause BSD license. See COPYING-BSD.
// Generated by builder.sce : Please, do not edit this file
// ----------------------------------------------------------------------------
//
libdistfungrandgate_path = get_absolute_file_path('loader.sce');
//
// ulink previous function with same name
[bOK, ilib] = c_link('libdistfungrandgateway');
if bOK then
  ulink(ilib);
end
//
link(libdistfungrandgate_path + filesep() + '../../src/cdflib/libcdflib' + getdynlibext());
link(libdistfungrandgate_path + filesep() + '../../src/unifrng/libunifrng' + getdynlibext());
link(libdistfungrandgate_path + filesep() + '../../src/gwsupport/libgwsupport' + getdynlibext());
list_functions = [ 'distfun_betarnd';
                   'distfun_normrnd';
                   'distfun_unifrnd';
                   'distfun_binornd';
                   'distfun_chi2rnd';
                   'distfun_exprnd';
                   'distfun_evrnd';
                   'distfun_frnd';
                   'distfun_gamrnd';
                   'distfun_geornd';
                   'distfun_hygernd';
                   'distfun_lognrnd';
                   'distfun_poissrnd';
                   'distfun_trnd';
                   'distfun_unidrnd';
                   'distfun_mnrnd';
                   'distfun_nbinrnd';
                   'distfun_ncx2rnd';
                   'distfun_permrnd';
                   'distfun_rndmvn';
                   'distfun_ncfrnd';
                   'distfun_nctrnd';
                   'distfun_wblrnd';
];
addinter(libdistfungrandgate_path + filesep() + 'libdistfungrandgateway' + getdynlibext(), 'libdistfungrandgateway', list_functions);
// remove temp. variables on stack
clear libdistfungrandgate_path;
clear bOK;
clear ilib;
clear list_functions;
// ----------------------------------------------------------------------------
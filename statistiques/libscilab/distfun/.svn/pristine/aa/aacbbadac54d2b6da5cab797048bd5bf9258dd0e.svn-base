// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function distfun_buildGateway()
    sci_gateway_dir = get_absolute_file_path("builder_gateway.sce");

    tbx_builder_gateway_lang(["cdf" "urng" "rnd" "driver"], sci_gateway_dir);
    tbx_build_gateway_loader(["cdf" "urng" "rnd" "driver"], sci_gateway_dir);
    tbx_build_gateway_clean(["cdf" "urng" "rnd" "driver"], sci_gateway_dir);

endfunction
distfun_buildGateway();
clear distfun_buildGateway;

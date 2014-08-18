// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function distfun_buildInternal()
	path = get_absolute_file_path("buildmacros.sce")
	genlib("distfuninternalslib",path,%f,%t);
endfunction

distfun_buildInternal();
clear distfun_buildInternal;

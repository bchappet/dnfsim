// Copyright (C) 2010 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function path = specfun_getpath (  )
    // Returns the path to the current module.
    // 
    // Calling Sequence
    //   path = specfun_getpath (  )
    //
    // Parameters
    //   path : a 1-by-1 matrix of strings, the path to the current module.
    //
    // Examples
    //   path = specfun_getpath (  )
    //
    // Authors
    //   2010 - DIGITEO - Michael Baudin

    [lhs, rhs] = argn()
    apifun_checkrhs ( "specfun_getpath" , rhs , 0 )
    apifun_checklhs ( "specfun_getpath" , lhs , 1 )
    
    path = get_function_path("specfun_getpath")
    path = fullpath(fullfile(fileparts(path),".."))
endfunction


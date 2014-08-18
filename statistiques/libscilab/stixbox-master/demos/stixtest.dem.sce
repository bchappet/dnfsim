// Copyright (C) 2013 - Michael Baudin
// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
// Copyright (C) 1998 - 2000 - Mathematique Universite de Paris-Sud - Jean Coursol
// Copyright (C) INRIA
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// Create a new graphic window (avoid interacting with the demo window)
scf();
disp('Compare the exact CDF and the empirical CDF for various distributions.');
n=100;
for lambda=1:4
    subplot(2,2,lambda)
    x=distfun_exprnd(lambda,n,1);
    x=gsort(x,"g","i");
    F=distfun_expcdf(x,lambda);
    stairs(x,(0:n-1)/n);
    plot(x,F)
    legend(["Exact" "Empirical"]);
    str="$\lambda=" + string(lambda)+"$";
    xtitle(str,"x","P(X<x)");
end

//
// Load this script into the editor
//
filename = "stixtest.dem.sce";
dname = get_absolute_file_path(filename);
editor ( fullfile(dname,filename) );


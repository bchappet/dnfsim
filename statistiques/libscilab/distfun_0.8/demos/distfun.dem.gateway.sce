// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

demopath = get_absolute_file_path("distfun.dem.gateway.sce");
subdemolist = [
"Accuracy: Poisson", "accuracy_poisson.sce"; ..
"Accuracy: Hypergeometric", "accuracy_hypergeometric.sce"; ..
"RNG: urand", "lattice_urand.sce"; ..
"RNG: Variance Reduction", "varianceReduction.sce"; ..
];
subdemolist(:,2) = demopath + subdemolist(:,2)

// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// Plot the geometric distribution
x=(0:11)';
pr=0.2;
p=distfun_geocdf(x,pr);
scf();
distfun_plotintcdf(x,p);

// Plot several geometric distributions
x=(0:11)';
scf();
p1=distfun_geocdf(x,0.2);
p2=distfun_geocdf(x,0.5);
p3=distfun_geocdf(x,0.8);
legendspec=["pr=0.2" "pr=0.5" "pr=0.8"];
distfun_plotintcdf(x,[p1,p2,p3],["r" "b" "g"],legendspec);
xtitle("Geometric CDF")

// Plot several geometric distributions (no legend)
x=(0:11)';
scf();
p1=distfun_geocdf(x,0.2);
p2=distfun_geocdf(x,0.5);
p3=distfun_geocdf(x,0.8);
distfun_plotintcdf(x,[p1,p2,p3],["r" "b" "g"]);

// Plot several geometric distributions (no colors)
x=(0:11)';
scf();
p1=distfun_geocdf(x,0.2);
p2=distfun_geocdf(x,0.5);
p3=distfun_geocdf(x,0.8);
distfun_plotintcdf(x,[p1,p2,p3]);

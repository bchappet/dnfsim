Specfun toolbox

Purpose
--------

The goal of this toolbox is to provide special functions.
The toolbox is based on macros.
Any distribution function (cumulated or not) should not be placed 
into this module: the good module for this is distfun.

Features
--------

The following is a list of the current features: 

 * specfun_expm1 : Compute exp(x)-1 accurately for small values of x.
 * specfun_gammainc : The incomplete gamma function.
 * specfun_lambertw : The Lambert W-Function.
 * specfun_log1p : Compute log(1+x) accurately for small values of x.
 *  Discrete Maths
   * specfun_combine : Returns the all the combinations of the given vectors.
   * specfun_combinerepeat : Returns repeated combinations with replacement.
   * specfun_factorial : The factorial function
   * specfun_factoriallog : The log-factorial function
   * specfun_ismember : Array elements that are members of set.
   * specfun_nchoosek : Returns the binomial number (n,k).
   * specfun_pascal : Returns the Pascal matrix.
   * specfun_subset : Generate all combinations of k values from x without replacement.

Dependencies
------------

 * This module depends on the apifun module (>= v0.2)
 * This module depends on the assert module
 * This module depends on the helptbx module


TODO
----

Ready implementations :

TODO : erfcinv : http://bugzilla.scilab.org/show_bug.cgi?id=6365 The implementation is available in the bug report.
TODO : hypot The implementation is available at : http://forge.scilab.org/index.php/p/compdiv/source/tree/HEAD/macros/compdiv_hypot.sci
TODO : pow2 See the implementation on http://forge.scilab.org/index.php/p/compdiv/source/tree/HEAD/macros/compdiv_pow2.sci
TODO : isfinite : http://forge.scilab.org/index.php/p/compdiv/source/tree/HEAD/macros/compdiv_isfinite.sci
TODO : copysign : http://forge.scilab.org/index.php/p/compdiv/source/tree/HEAD/macros/compdiv_copysign.sci
TODO : signbit, which returns %t if sign bit is positive, %f if signbit is negative : http://forge.scilab.org/index.php/p/floatingpoint/source/tree/HEAD/macros/flps_signbit.sci

Unidentified implementations :

TODO : remove specfun_checkflint when checkflint is in apifun.
TODO : check for arguments with imaginary part.
TODO : erfi : http://bugzilla.scilab.org/show_bug.cgi?id=6092
TODO : elliptic integral of the second kind : http://bugzilla.scilab.org/show_bug.cgi?id=5798
TODO : indefinite integrals : http://bugzilla.scilab.org/show_bug.cgi?id=6259
TODO : extend nearfloat and take n, the number of floats to produce
TODO : isinf
TODO : isnormal
TODO : fpclassify
TODO : nexttoward
TODO : fma (Floating multiply-add)
TODO : zeta, totient, deta, euler, eulergamma : http://www.mathworks.com/matlabcentral/fileexchange/978

Forge
-----

http://forge.scilab.org/index.php/p/specfun/

ATOMS
-----

http://atoms.scilab.org/toolboxes/specfun

Authors
------

2010, DIGITEO, Michael Baudin
2009, Pascal Getreuer

Acknowledgements
----------------

 * Samuel Gougeon
 * Calixte Denizet

Licence
-------

This toolbox is released under the CeCILL_V2 licence :

http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt


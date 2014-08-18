// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [x,txt]=datas12()
  txt=['Cables';
'Source: Rao (1973), ""Linear Statistical Inference and its applications"", (2nd Edition), Wiley.';
'Taken From:   J Bickel, A. Doksum (1977), ""Mathematical Statistics"", Holden Day.';
'Dimension:  9*12 observations ';
'Description: force de tension moins 340 des fils contenus dans 9';
' cables (lignes 1 a 9) fabriques pour un reseau a haute';
' tension. Chaque cable est constitue de 12 fils (colonnes';
' 1 a 12).'];


 x=[   5   -13    -5    -2   -10    -6    -5     0    -3     2    -7    -5
     -11   -13    -8     8    -3   -12   -12   -10     5    -6   -12   -10
       0   -10   -15   -12    -2    -8    -5     0    -4    -1    -5   -11
     -12     4     2    10    -5    -8   -12     0    -5    -3    -3     0
       7     1     5     0    10     6     5     2     0    -1   -10    -2
       1     0    -5    -4    -1     0     2     5     1    -2     6     7
      -1     0     2     1    -4     2     7     5     1     0    -4     2
      -1     0     7     5    10     8     1     2    -3     6     0     5
       2     6     7     8    15    11    -7     7    10     7     8     1];
endfunction

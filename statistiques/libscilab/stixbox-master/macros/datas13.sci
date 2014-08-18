// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [x,txt]=datas13()
  txt=['Service calls';
'Source: S. Chatterjee, B. Price, (1991) ""Regression analysis by example"" (2nd Edition), Wiley.';
'Taken From: source.';
'Dimension: 14 observations portant sur  2 variables';
'Description:  nombre de composants electriques en panne dans un';
' ordinateur (1ere colonne) et duree de la reparation ';
' de l''ordinateur en minutes (2eme colonne) ']


x=[ 1 	23
    2 	29
    3 	49
    4 	64
    4 	74
    5 	87
    6 	96
    6 	97
    7 	109
    8 	119
    9 	149
    9 	145
    10 	154
    10 	166];
endfunction

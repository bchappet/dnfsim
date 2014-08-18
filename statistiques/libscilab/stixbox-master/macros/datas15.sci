// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [x,txt]=datas15();
  txt=['Turnover';
'Source: J-P Lecoutre, S. Legait-Maille, Ph. Tassi (1990), ""statistique, exercices corriges avec rappels de cours"", Masson.';
'Taken From: source';
'Dimensions: 11 observations de  3 variables';
'Description: Le chiffre d''affaire en millions de francs (2eme colonne)';
' et le nombre de salaries en milliers (1ere colonne) d''un';
' un secteur industriel pour chaque annee de 1957 a 1967 ';
' (1ere colonne) ']


 x=[	1957	294	624;
 	1958	271	661;
 	1959	314	728;
 	1960	356	782;
 	1961	383	819;
 	1962	369	869;
 	1963	402	938;
 	1964	422	1023;
 	1965	475	1136;
 	1966	475	1227;
 	1967	486	1317];
endfunction

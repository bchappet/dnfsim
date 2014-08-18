// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [x,txt]=datas16()
  txt=['Unemployment';
'Source: OCDE et INSEE';
'Taken From: J-P Lecoutre, S. Legait-Maille, Ph. Tassi (1990), ""statistique, exercices corriges avec rappels de cours"", Masson.';
'Dimensions: 11 observations de 3 variables';
'Description: Le taux de chomage en France (2eme colonne) et le taux';
' d''inflation (3eme colonne) pour chaque annee de 1974 a 1984';
' (1ere colonne). Le taux de chomage est donne en';
' pourcentage de la population active. Le taux d''inflation';
' est mesure par l''evolution en pourcentage de l''indice des';
' prix de detail.']


x=[	1974	2.8	13.7;
 	1975	4.1	11.8;
 	1976	4.4	9.5;
 	1977	4.9	9.4;
 	1978	5.3	9.1;
 	1979 	6.0	10.8;
 	1980	6.4	13.6;
 	1981	7.4	13.4;	
 	1982	8.2	11.8;
 	1983	8.4	9.6;
 	1984	9.8	7.4];
endfunction

// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [x,txt]=datas17()
  txt=['Quality control';
'Source: J. Milton, J. Arnold (1995), ""Introduction to Probability and statistics"", McGraw-Hill international editions';
'Taken From: source';
'Dimensions: 4 observations de 5 variables';
'Description:  Un echantillon de 500 pieces a ete choisi au hasard dans';
' le stock d''une production. Chaque piece a ete classee ';
' en fonction du jour de la semaine ou elle a ete produite';
' et de sa qualite. On donne le nombre de pieces de ';
' l''echantillon de chaque classe (les colonnes 1 a 5';
' correspondent dans l''ordre a la production de lundi,';
' mardi, mercredi, jeudi, et vendredi, les lignes';
' correspondent aux differentes qualites de production';
' (excellente, bonne, convenable, mauvaise).']


x=[	 44	74	79	72	31
	 14	25	27	24	10	
	 15	20	20	23	9
	 3	5	5	0	0 ];
endfunction

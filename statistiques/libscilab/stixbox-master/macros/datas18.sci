// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [x,txt]=datas18()
  txt=['Graphics cards';
'Source: ""Gauging Video Speed"" dans MAC WORLD, Juin 1993';
'Taken From: J. Milton, J. Arnold (1995), ""Introduction to Probability and statistics"", p 586, McGraw-Hill international editions';
'Dimensions: 8 observations de 5 variables';
'Description: temps en secondes necessaire pour qu''une page d''un';
' document de type WORD s''affiche a l''ecran. Ce temps a ete';
' determine avec des moniteurs de 24 pouces utilisant 5';
' cartes  graphiques differentes (colonnes 1 a 5).']

x=[	30.5	48.3	79.2	51.6	79.0
	32.4	42.1	84.7	59.4	85.3	
	27.2	43.5	85.0	57.3	86.2		
	26.3	40.6	88.2	59.0	82.0	
	25.1	38.6	76.3	58.7	87.2
	38.2	32.1	83.1	68.1	81.7
	30.6	41.6	92.6	64.8	93.5	
	33.7	38.8	88.5	55.5	89.1];
endfunction

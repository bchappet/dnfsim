// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [x,txt]=datas19()
  txt=['Processing system development';
'Source: ""A Software Matrix for Cost Estimation and Efficiency Measurement in Data Processing System Development"" in Journal of Systems Software, Vol 3, 1983.';
'Taken From:   J. Milton, J. Arnold (1995), ""Introduction to Probability and statistics"", p 522, McGraw-Hill international edition.';
'Dimensions: 8 observations de 5 variables';
'Description: Pour 11 logiciels de traitement de donnees, on donne le';
' cout de developpement du logiciel (1ere colonne), le';
' nombre de fichiers constituant le logiciel (2eme';
' colonne), le nombre d''interfaces construites entre le ';
' systeme et l''environnement du logiciel (3eme colonne), et';
' le nombre d''operations logiques sur les donnees';
' qu''effectue le logiciel (4eme colonne).']


x=[ 	22.6	4	44	18
	15.0	2	33	15	
	78.1	20	80	80
	28.0	6	24	21
	80.5	6	227	50
	24.5	3	20	18
	20.5	4	41	13
	147.6	16	187	137
	4.2	4	19	15
	48.2	6	50	21
	20.5	5	48	17];
endfunction

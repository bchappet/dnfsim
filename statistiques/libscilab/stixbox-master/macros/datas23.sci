// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [x,txt]=datas23()
  txt=['French firms datas';
'Source: ""Les 500 premiers groupes francais et europeens"" dans Enjeux-Les Echos, hors-serie, novembre 1998.';
'Taken From: ''La France en faits et chiffres'' (2000), INSEE http://www.insee.fr/fr/home/home_page.asp';
'Dimensions: 45 observations de 3 variables';
'Description:     Donnees sur 45 groupes francais de l''industrie et des ';
'     services pour l''annee 1997 : chiffre d''affaire en ';
'    milliards de francs (1ere colonne), nombre de salaries ';
'       en milliers (2eme colonne) et revenu net en milliards';
'    de francs (3eme colonne).'];


  

x=[254.3      83.7    5.6
   207.9     141.3   5.4
   191.1     54.4    7.6
   190.4     305.9   4.0
   189.9     122.8   4.1
   186.8     140.2   -2.8
   185.9     189.5   4.7
   169.3     113.3   3.6
   165.0     193.3   5.4
   156.7      165.0   14.9
   110.7       57.4    1.6
   107.1      106.8   5.6
   97.2     207.8   -0.6
   91.1      26.4    0.8
   90.0      68.4   -5.0
   89.9      286.6   0.1
   89.2      60.3   2.9
   88.5      80.6   3.7
   79.7      123.3  3.9
   76.3       58.6  1.1
   72.0       51.4   2.1
   69.7       34.0   1.8
   69.1       47.2   4.0
   65.9       46.2   1.4
   63.7       20.7   1.9
   60.7       54.3   1.9
   60.3       30.3    1.8
   56.3       37.1   1.4
   53.6       42.1   1.9
   51.7       23.2    1.4
   48.0       33.5     4.5
   47.4       61.5     2.2
   42.1      37.1    2.4
   38.4       27.6    3.1
   37.7       5.4     0.3
   37.5       4.7     -1.4
   34.7       1.2     0.4
   34.6       2.0     2.3
   34.1       7.6     0.2
   34.0       36.1    1.5
   32.8       42.5    0.6
   32.7       24.6    0.6
   32.7       18.6    1.0
   31.8       121.0   1.5
   30.4       338.8   0.6];
endfunction



# pour tester Stat.sce

#python generateData.py --weigths 0.0 0.1 0.2 0.3 0.4 --times 1 2 --packet_initialisation a_send b_send ab_send --iterations 100 --tailles_grilles 9 #--forcerewrite
#scilab -nw -f Additivite.sce -args '9' '1 2' '0.0 0.1 0.2 0.3 0.4' '5 20 50 75 100' 'a_send b_send' ab_send 0

#python generateData.py --weigths 0.0 0.1 0.2 0.3 0.4 --times 1 2 --packet_initialisation twoa twob twoab --iterations 100 --tailles_grilles 9 #--forcerewrite
#scilab -nw -f Additivite.sce -args '9' '1 2' '0.0 0.1 0.2 0.3 0.4' '5 20 50 75 100' 'twoa twob' twoab 0


# pour test MeanAndVariance
#python generateData.py --weigths 0.0 0.1 0.2 --times 1 2 --packet_initialisation twoa --iterations 100 --tailles_grilles 9
#scilab -nw -f MeanAndVariance.sce -args 9 '1 2' '0.0 0.1 0.2' 100 twoa temp



# pour generer toutes les données

#python generateData.py --weigths 0.65 0.655 0.66 0.665 0.67 0.675 0.68 0.685 0.69 0.695 0.7 0.705 0.71 0.715 0.72 0.725 0.73 0.735 0.74 0.745 0.75 0.755 0.8 --times 1 3 5 --iterations 1000 --tailles_grilles 9 --packet_initialisation a_send b_send ab_send
#python generateData.py --weigths 0.0 0.1 0.2 0.3 0.4 0.5 0.6 0.7 0.8 0.9 1.0 --times 1 3 5 --iterations 1000 --tailles_grilles 9 --packet_initialisation twoa twob twoab
#--forcerewrite 

#scilab -nw -f Additivite.sce -args '9' '1 3 5' '0.0 0.1 0.2 0.3 0.4 0.5 0.6 0.65 0.66 0.67 0.68 0.69 0.695 0.7 0.705 0.71 0.72 0.73 0.74 0.75 0.8 0.9 1.0' '10 100 500 1000' 'a_send b_send' ab_send 0
#scilab -nw -f Additivite.sce -args '9' '1 3 5' '0.65 0.655 0.66 0.665 0.67 0.675 0.68 0.685 0.69 0.695 0.7 0.705 0.71 0.715 0.72 0.725 0.73 0.735 0.74 0.745 0.75 0.755 0.8' '10 50 100 250 500 750 1000' 'a_send b_send' ab_send 0

#scilab -nw -f MeanAndVariance.sce -args 9 '1 2 3 4 5' '0.7' 1000 ab_send rapport/ab_send

#python generateData.py --weigths 0.7 --times 3 5 --iterations 1000 --tailles_grilles 19 --packet_initialisation 19_ab_send --forcerewrite
#scilab -nw -f MeanAndVariance.sce -args 19 '3 5' '0.7' 1000 19_ab_send .

#scilab -nw -f Additivite.sce -args '9' '1 3 5' '0.0 0.05 0.1 0.15 0.2 0.25 0.3 0.35 0.4 0.45 0.5 0.55 0.6 0.65 0.7 0.75 0.8 0.85 0.9 0.95 1.0'

#python generateData.py --weigths 0.7  --iterations 1000 --times 5 --taille 9 19 --packet_initialisation a_send_20

scilab -nw -f MeanAndVariance.sce -args 9 '5' '0.7' 1000 a_send_20 temp/
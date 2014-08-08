

# pour tester Stat.sce

#python generateData.py --weigths 0.0 0.1 0.2 0.3 0.4 --times 1 2 --packet_initialisation a_send b_send ab_send --iterations 100 --tailles_grilles 9 #--forcerewrite
#scilab -nw -f Additivite.sce -args '9' '1 2' '0.0 0.1 0.2 0.3 0.4' '5 20 50 75 100' 'a_send b_send' ab_send 0

#python generateData.py --weigths 0.0 0.1 0.2 0.3 0.4 --times 1 2 --packet_initialisation twoa twob twoab --iterations 100 --tailles_grilles 9 #--forcerewrite
#scilab -nw -f Additivite.sce -args '9' '1 2' '0.0 0.1 0.2 0.3 0.4' '5 20 50 75 100' 'twoa twob' twoab 0

# pour test MeanAndVariance
#python generateData.py --weigths 0.0 0.1 0.2 --times 1 2 --packet_initialisation twoa --iterations 100 --tailles_grilles 9
#scilab -nw -f MeanAndVariance.sce -args 9 '1 2' '0.0 0.1 0.2' 100 twoa temp



# pour generer toutes les données

#python generateData.py --weigths 0.0 0.05 0.1 0.15 0.2 0.25 0.3 0.35 0.4 0.45 0.5 0.55 0.6 0.65 0.7 0.75 0.8 0.85 0.9 0.95 1.0 --times 1 3 5 --iterations 5000 --tailles_grilles 9 --forcerewrite

#scilab -nw -f Additivite.sce -args '9' '1 3 5' '0.0 0.05 0.1 0.15 0.2 0.25 0.3 0.35 0.4 0.45 0.5 0.55 0.6 0.65 0.7 0.75 0.8 0.85 0.9 0.95 1.0'

python generateData.py --weigths 0.7 0.5 0.3 0.9 0.6 --iterations 1000 --times 1 2 3 4 5 --packet_initialisation a_send
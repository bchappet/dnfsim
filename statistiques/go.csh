#python generateData.py --weigths '0.0' '0.1' '0.2' --iteration 1000
#python generateData.py --weigths 0.7 --times 5 --iterations 10 --tailles_grilles 9

# pour tester Stat.sce
python generateData.py --weigths 0.0 0.7 --times 1 2 3 --iterations 100 --tailles_grilles 9 19

# pour generer toutes les données
#python generateData.py --weigths 0.0 0.05 0.1 0.15 0.2 0.25 0.3 0.35 0.4 0.45 0.5 0.55 0.6 0.65 0.7 0.75 0.8 0.85 0.9 0.95 1.0 --times 1 --iterations 5000 --tailles_grilles 9

scilab -nw -f Stats.sce
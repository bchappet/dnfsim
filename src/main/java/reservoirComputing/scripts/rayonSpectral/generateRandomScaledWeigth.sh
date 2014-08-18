#!/bin/bash

lenRes=$1
rhoT=$2
resFile=$3

#### generate random weight between -0.5 and 0.5
#### compute spectral radius rho
#### scale the weight with rhoT/rho (rho target)

./generateRandomMatrix.py $lenRes tmp1.csv
rho=`./computeSpectralRadius.py tmp1.csv`
scale=$(echo "$rhoT/$rho" | bc -l)
./scaleWeights.py $scale tmp1.csv $resFile
rm tmp1.csv




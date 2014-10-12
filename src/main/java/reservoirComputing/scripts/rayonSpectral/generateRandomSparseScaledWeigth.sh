#!/bin/bash

lenRes=$1
rhoT=$2
connectivity=$3
scale=$4
resFile=$5

#### generate random sparse weight between -scale and scale
#### compute spectral radius rho
#### scale the weight with rhoT/rho (rho target)

./generateSparseConnectionMatrixWeights.py $lenRes tmp1.csv $connectivity $scale
rho=`./computeSpectralRadius.py tmp1.csv`
scale=$(echo "$rhoT/$rho" | bc -l)
./scaleWeights.py $scale tmp1.csv $resFile
rm tmp1.csv





#!/bin/bash


#param : n
#param : scale (0.2 0.1)
#param : name

./generateRandomWeightMatrix.py $1 weightsExc.csv
./generateRandomWeightMatrix.py $1 weightsInh.csv
./subWeights.py weightsExc.csv weightsInh.csv weightsRes.csv
./scaleWeights.py $2 weightsRes.csv weightsResScaled.csv

./computeSpectralRadius.py weightsResScaled.csv
cp weightsResScaled.csv $3.csv
./plotMaps.py $3.csv $3
eog $3.png


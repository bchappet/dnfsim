#!/bin/bash


#param : scale (0.2 0.1)

./scaleWeights.py $1 weightsRes.csv weightsResScaled.csv
./plotMaps.py weightsResScaled.csv weightsResScaled

./computeSpectralRadius.py weightsResScaled.csv
cp weightsResScaled.csv weights_0.fig
eog weightsResScaled.png


#!/usr/bin/python


##Compute the spectral rayon of a square matrix given as a csv file



import sys
import numpy as np
import csv


fileIn = sys.argv[1]




def csvToArray(fileName):#return array
                data = csv.reader(open(fileName),delimiter=',')
                data = list(data)
                data = np.array(data).astype('float')
                return data




mat = np.matrix(csvToArray(fileIn))
#cf re-visiting the echo state property (Yildiz-2012)
mat = abs(mat)
eVal,eVect = np.linalg.eig(mat)


#idx = eVal.argsort()   
#eVal = eVal[idx]
#eVect = eVect[:,idx]

ret = max(abs(eVal))

print ret





#!/usr/bin/python


#Scale the matrix with the given parameter


import csv
import numpy as np
import sys

scale = eval(sys.argv[1])
fileIn = sys.argv[2]
fileOut = sys.argv[3]


def csvToArray(fileName):#return array
                 data = csv.reader(open(fileName),delimiter=',')
                 data = list(data)
                 data = np.array(data).astype('float')
                 return data

def exportToCSV(m,fileName):
        a = np.asarray(m)
        np.savetxt(fileName,a,delimiter=",")

	


mat = csvToArray(fileIn)
mat = mat * scale

exportToCSV(mat,fileOut)

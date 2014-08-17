#!/usr/bin/python

##Sub to matrix weight csv files res = A - B



import sys
import numpy as np
import csv


fileA = sys.argv[1]
fileB = sys.argv[2]
fileRes = sys.argv[3]


def csvToArray(fileName):#return array
                data = csv.reader(open(fileName),delimiter=',')
                data = list(data)
                data = np.array(data).astype('float')
                return data

def exportToCSV(m,fileName):
        a = np.asarray(m)
        np.savetxt(fileName,a,delimiter=",")



a = csvToArray(fileA)
b = csvToArray(fileB)
res = a - b




exportToCSV(res,fileRes)

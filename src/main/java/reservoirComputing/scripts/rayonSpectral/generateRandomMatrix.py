#!/usr/bin/python


#Just generate random matrix of size resSize * resSize

import sys
from numpy import *

resSize = eval(sys.argv[1])
fileName = sys.argv[2]

def exportToCSV(m,fileName):
        a = asarray(m)
        savetxt(fileName,a,delimiter=",")



random.seed(42)

W = random.rand(resSize,resSize)-0.5
exportToCSV(W,fileName)

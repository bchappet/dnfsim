#!/usr/bin/python


#Just generate random matrix of size resSize * resSize

import sys
from numpy import *

resSize = eval(sys.argv[1])
scale = eval(sys.argv[2]) #default = 1 : uniform distribution for non zero weighs in [-1,1]
fileName = sys.argv[3]

def exportToCSV(m,fileName):
        a = asarray(m)
        savetxt(fileName,a,delimiter=",")



random.seed(42)

W = random.rand(resSize,resSize)*scale*2-scale
exportToCSV(W,fileName)

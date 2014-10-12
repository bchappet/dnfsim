#!/usr/bin/python


#Just generate random matrix of size resSize * resSize
#but with sparce connectivity
#   

import sys
from numpy import *
from random import *

resSize = eval(sys.argv[1])
fileName = sys.argv[2]
connectivity = eval(sys.argv[3]) #default = 0.05 (5%)
scale = eval(sys.argv[4]) #default = 1 : uniform distribution for non zero weighs in [-1,1]

def exportToCSV(m,fileName):
        a = asarray(m)
        savetxt(fileName,a,delimiter=",")






seed(42)
#print round(resSize*resSize*connectivity)

flatSample = sample(xrange(resSize*resSize),int(round(resSize*resSize*connectivity)))

mat = zeros((resSize,resSize))

        
for coord in flatSample:
    mat[coord%resSize,coord/resSize] = uniform(-scale,+scale)


exportToCSV(mat,fileName)

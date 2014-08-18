#!/usr/bin/python

import subprocess
import sys
import re
import matplotlib.pyplot as plt
import csv
import numpy
import math
from matplotlib.ticker import FuncFormatter





def csvToArray(fileName):#return array
		data = csv.reader(open(fileName),delimiter=',')
		data = list(data)
		data = numpy.array(data).astype('float')
		return data
def egaliseColorBar(data,bar):
		maximum = numpy.amax(data)
		minimum = numpy.amin(data)
		egal = max(abs(maximum),abs(minimum))
		plt.clim(-egal,+egal)
		bar.set_ticks([roundUp(-egal),0,roundDown(+egal)])
		
def roundDown(x):
	return math.floor(x*100)/100
def roundUp(x):
	return math.ceil(x*100)/100
	
name = sys.argv[1]
fileName = sys.argv[2]

data = csvToArray(name)
plt.imshow(data,interpolation='nearest',cmap='RdYlBu_r',origin='lower')
bar = plt.colorbar(shrink=.92)
egaliseColorBar(data,bar)
plt.xticks([])
plt.yticks([])
plt.savefig(fileName+'.eps',dpi=100,format='eps')
plt.savefig(fileName+'.png')
				

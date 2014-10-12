#!/usr/bin/python

import numpy as np;
import math;
import random;
import sys
import matplotlib.pyplot as plt


##09/04/2014
##1) With a given connection toplogy (oriented graph), with given probability on each arc (adjacence matrix)
## a vertex in the graph is not necessarily a neuron (rather a transmitter)
## we also have the information (table) associating each vertex to one neuron
##2) With given possible path between neurons
## We can compute the weight matrix and store it into a file

#ctions
N = 0
S = 1
E = 2
W = 3

n = eval(sys.argv[1]) #let n be the number of neuron
fileName = sys.argv[2]
t = 4;# let t be the number of transmitter per neuron

def egaliseColorBar(data,bar):
                maximum = np.amax(data)
                minimum = np.amin(data)
                egal = max(abs(maximum),abs(minimum))
                plt.clim(-egal,+egal)
                bar.set_ticks([roundUp(-egal),0,roundDown(+egal)])
    
def roundDown(x):
	        return math.floor(x*100)/100
def roundUp(x):
	        return math.ceil(x*100)/100


def getRandom(i,j):
	#return random.random();
	return 1

def generateRandomAdjMatrix(n,t):
	adj = np.zeros((n,n,t,t),dtype=np.float) #adjacent matric of the graph
	sqrt = int( math.sqrt(n))

	for i in range(n):
		for j in range(n):
			if j == i-sqrt:#N
				rand = getRandom(i,j);
				adj[i,j,N,N] =rand;
				adj[i,j,N,E] =rand;
				adj[i,j,N,W] =rand;
			elif j == i+sqrt:#S
				rand = getRandom(i,j);
				adj[i,j,S,S] =rand;
				adj[i,j,S,E] =rand;
				adj[i,j,S,W] =rand;
			elif j == (i+1) and (i+1) % sqrt != 0:#E
				adj[i,j,E,E] =getRandom(i,j);
			elif j == (i-1) and i %sqrt != 0:#W
				adj[i,j,W,W] =getRandom(i,j);
			else:
				adj[i,j] = 0
	
	return adj

def matToStr(m):
	ret = "";
	for i in range(m.shape[0]):
		for j in range(m.shape[1]):
			if(m[i,j] != 0):
				ret += "1,"
			else:
				ret += "0,"

			#ret += "%.2f," % m[i,j]
		ret += "\n"
	return ret

def matToFlat(m,n,t):
	flatMat = np.zeros((n*t,n*t),dtype=np.float) #adjacent matric of the graph
	for i in range(m.shape[0]):
		for j in range(m.shape[1]):
			for t1 in range(t):
				for t2 in range(t):
					flatMat[j*t+t1,i*t+t2] = m[i,j,t2,t1]

	return flatMat

def flatToNeuralAdj(m,n,t):
	"""
	Transform a flat adj matrix (transmitters connection) into a neuron adj connection
	if there are several weight for the same neuron arc, we take the first weight
	(in the RSDNF implementation, the 3 weights should be the same)
	"""

	adj = np.zeros((n,n),dtype=np.float) #adjacent matric of the graph
	for i in range(n):
		for j in range(n):
			found = False # true when we found a weight
			for t1 in range(t):
				for t2 in range(t):
					if not(found)  and m[j*t+t1,i*t+t2] != 0:
						found = True
						adj[j,i]= m[j*t+t1,i*t+t2]
	return adj

def exportToCSV(m,fileName):
	a = np.asarray(m)
	np.savetxt(fileName,a,delimiter=",")





adj = generateRandomAdjMatrix(n,t)
flat = np.matrix(matToFlat(adj,n,t))
print "flat : "
print matToStr(flat)
adjN = flatToNeuralAdj(flat,n,t)
print matToStr(adjN)

end = int(math.sqrt(n)*2-1)
res = np.copy(flat)
for power in range(2,end):
	print power
	res += flat**power 
#	print matToStr(flat**power)
	adjN = flatToNeuralAdj(flat**power,n,t)
#	print matToStr(adjN)

print "in the end: "
print matToStr(res)

adjN = flatToNeuralAdj(res,n,t)
print matToStr(adjN)
print "Saving into " + fileName
exportToCSV(adjN,fileName)
#print matToStr(flat**4)







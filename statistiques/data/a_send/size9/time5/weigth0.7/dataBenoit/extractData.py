#!/usr/bin/python
import os
import re



def extractDataArray(fileName):
    ret = []
    f = open(fileName,'r')
    for line in f:
        line = line.rstrip()
        data = re.split(",",line)
       # print('split : ' + str(data))
        for d in data:
            ret.append(d)
   # print('split : ' + str(ret))
    f.close()
    return ret



newFile = open("data.csv",'w')
newFile.write("it,index,nbSpike\n")

for files in os.listdir("."):
        if files.endswith(".csv"):
                    print files
                    #Find the it number in the file name
                    m = re.search('ReceiveMap_(.+)_',files)
                    if m:
                        it = eval(m.group(1))
                        print it
                        data = extractDataArray(files)
                        i = 0
                        for d in data:
                  #          print d
                            newFile.write(str(it)+','+str(i) + ','+str(d) + '\n')
                            i = i + 1
                    else:
                        print 'nope'
newFile.close()




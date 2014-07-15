import os
import argparse

parser = argparse.ArgumentParser(description='Generateur de fichier pour le test de PFModel')
parser.add_argument('--weigths',
                            nargs='*',
                            type=str,
                            default=['0.7'],
                            help='les poids que l\'on veut tester')
parser.add_argument('--packet_initialisation',
                            nargs='*',
                            type=str,
                            default=['a_send','b_send','ab_send'],
                            help='comment initialiser le graphe')
parser.add_argument('--time',
                            nargs='*',
                            type=str,
                            default=['5'],
                            help='nombre de seconde pendant lesquelles le model est lance')
parser.add_argument('--tailleGrille',
                            nargs='*',
                            type=str,
                            default=['9'],
                            help='taille du graphe')
parser.add_argument('--iteration', 
							default='100',
							type=int,
							help='valeur de l\'iteration maximale a compute')

args = parser.parse_args()

def writeDir(path):
	if(not(os.path.exists(path))):
		os.mkdir(path)

def getLastComputation(path):
	listFile = os.listdir(path)
	listNum = []
	for file in listFile :
		listNum.append(int(file.split('_')[1]))
	if len(listNum)==0 :
		return -1
	else :
		return sorted(listNum)[len(listNum)-1]

writeDir("./data")

iteration = int(args.iteration)
time = args.time
packet_initialisation = args.packet_initialisation
weigth = args.weigths
	
for packet_initialisation in ['a_send']:
	writeDir("./data/" + packet_initialisation)
	for tailleGrille in ['9']: 
		writeDir("./data/" + packet_initialisation + "/size"+ tailleGrille)
		for time in [ '1']:
			writeDir("./data/" + packet_initialisation + "/size"+ tailleGrille + "/time" + time)
			for weigth in ['0.69']:
				path = "./data/" + packet_initialisation + "/size"+ tailleGrille + "/time" + time + "/weigth" + weigth
				writeDir(path)
				# on recupere la valeur de la computation la plus elevee pour ne pas tout refaire.
				c = getLastComputation(path)
				print(
					"initialisation : "+packet_initialisation + 
					" tailleGrille : "+ tailleGrille + 
					" time : " + time + 
					" weigth : " + weigth
				)
				if c < iteration : 
					firstIteration = c + 1
					iteration = iteration - firstIteration
					if iteration > 0 :
						print("firstIteration : "+str(firstIteration)+" iteration a effectuer : "+str(iteration))
						os.system(
							"java "+
							"-cp ../bin:../src "+
							"main.java.controler.Printer " +
							#"-Duser.dir ~/Work/Loria2014/dnfsim2 "+
							"firstIteration="+ str(firstIteration) + " " +
							"model=PFModel "+
							"show=false "+
							"context=\""+
								"packet_initialisation="+ packet_initialisation+ ";"+
								"weigth="+ weigth +";"+
								"size="+ tailleGrille+ ";"+
								"mapToSave=ReceiveMap;"+
								"pathToSave="+path+"/;"+
								"transition_matrix_file="+"/home/nikolai/Work/Loria2014/dnfsim2/src/main/java/network/ressource/1.pf;"+
							"\" " +
							"it="+ str(iteration) + " "+
							"scenario=\""+
								"wait="+ time+";"+
							"\" "+
							"core=2"
						)
					else :
						print("-> pas de computation a effectuer, il y a extactement le nombre de computations demandees dans ce dossier")
				else :
					print("-> pas de computation a effectuer, il y a deja plus de computations dans ce dossier que necessaire")
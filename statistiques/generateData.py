import os
import argparse
import multiprocessing

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
parser.add_argument('--times',
                            nargs='*',
                            type=str,
                            default=['5'],
                            help='nombre de seconde pendant lesquelles le model est lance')
parser.add_argument('--tailles_grilles',
                            nargs='*',
                            type=str,
                            default=['9'],
                            help='taille du graphe')
parser.add_argument('--iterations', 
							default='100',
							type=int,
							help='le nombre d\'iterations que l\'on veut voir apparaitre dans les sous dossiers)')

parser.add_argument('--forcerewrite',
                   help='Force this script to generate a file even if this one exists (default : False)',
                   action='store_true')




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

forcerewrite = bool(args.forcerewrite)


nbCores = str(multiprocessing.cpu_count())

print(nbCores)
	
for packet_initialisation in args.packet_initialisation:
	writeDir("./data/" + packet_initialisation)
	for tailleGrille in args.tailles_grilles: 
		writeDir("./data/" + packet_initialisation + "/size"+ tailleGrille)
		for time in args.times:
			writeDir("./data/" + packet_initialisation + "/size"+ tailleGrille + "/time" + time)
			for weigth in args.weigths:
				iteration = int(args.iterations)
				path = "./data/" + packet_initialisation + "/size"+ tailleGrille + "/time" + time + "/weigth" + weigth
				writeDir(path)
				print("\n\n"+
					"initialisation : "+packet_initialisation + 
					" tailleGrille : "+ tailleGrille + 
					" time : " + time + 
					" weigth : " + weigth
				)
				if not forcerewrite :
					# on recupere la valeur de la computation la plus elevee pour ne pas tout refaire.
					c = getLastComputation(path)
				
				if forcerewrite  or c < iteration : 
					if not forcerewrite :
						firstIteration = c + 1
					else  : 
						firstIteration = 0
					iteration = iteration - firstIteration
					if iteration > 0 or forcerewrite :
						print("-> firstIteration : "+str(firstIteration)+" iteration a effectuer : "+str(iteration))
						transitionFile = "../PFTransitionMatrixFile"+tailleGrille
						writeTransitionMatrixFile = not(os.path.isfile(transitionFile))
						os.system(
							"java "+
							"-jar "+#"-cp ../bin:../src "+
							"../dnfSim2.jar "+#main.java.controler.Printer " +
							#"-Duser.dir ~/Work/Loria2014/dnfsim2 "+
							"firstIteration="+ str(firstIteration) + " " +
							"model=PFModel "+
							"show=false "+
							"context=\""+
								"stimulis_file="+ "stimulis/"+packet_initialisation+".stimulis;"+
								"weigth="+ weigth +";"+
								"size="+ tailleGrille+ ";"+
								"mapToSave=ReceiveMap;"+
								"pathToSave="+path+"/;"+
								"transition_matrix_file="+transitionFile+";"+
								"write_transition_matrix_file="+str(writeTransitionMatrixFile)+";"+
							"\" " +
							"it="+ str(iteration) + " "+
							"scenario=\""+
								"wait="+ time+";"+
							"\" "+
							"core="+nbCores
						)
					else :
						print("-> pas de computation a effectuer, il y a exactement le nombre de computations demandees dans ce dossier")
				else :
					print("-> pas de computation a effectuer, il y a deja plus de computations dans ce dossier que necessaire")
import os

def writeDir(path):
	if(not(os.path.exists(path))):
		os.mkdir(path)

writeDir("./data")
	
for packet_initialisation in ['a_send']:
	writeDir("./data/" + packet_initialisation)
	for tailleGrille in ['9']: 
		writeDir("./data/" + packet_initialisation + "/size"+ tailleGrille)



	#os.system("java")


# set iteration = 5

# mkdir statistiques/data

# foreach packet_initialisation ( "a_send" ) #"b_send" "ab_send" )
# 	mkdir statistiques/data/$packet_initialisation
# 	foreach tailleGrille ( 9 ) # ( 9 10 19 20 29 30 39 40 49 50 )
# 		mkdir statistiques/data/$packet_initialisation/size$tailleGrille
# 		foreach time ( 1 )
# 			mkdir statistiques/data/$packet_initialisation/size$tailleGrille/time$time
# 			foreach weigth ( 0.69 )
# 				mkdir statistiques/data/$packet_initialisation/size$tailleGrille/time$time/weigth$weigth
#             	printf "\n------------------------------------------------------------------------\n"
#             	printf "\nTest tailleGrille=$tailleGrille time=$time weigth=$weigth\n\n"
#             	set FILE = `ls statistiques/data/$packet_initialisation/size$tailleGrille/time$time/weigth$weigth -1 | tail -n 1`
#             	echo $FILE
#             	#set FIRSTITERATIOn = `echo $FILE | grep
#             	java -cp bin main.java.controler.Printer firstIteration=0 model=PFModel show=false context="packet_initialisation=$packet_initialisation;weigth=$weigth;size=$tailleGrille;mapToSave=ReceiveMap;pathToSave=statistiques/data/$packet_initialisation/size$tailleGrille/time$time/weigth$weigth/" it=$iteration scenario="wait=$time;" core=2
#         	end
#     	end
# 	end
# end
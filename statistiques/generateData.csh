#!/bin/csh

cd ..

set iteration = 1000

foreach packet_initialisation ( "a_send" "b_send" "ab_send" )
	mkdir statistiques/data/$packet_initialisation
	foreach tailleGrille ( 9 19 ) # ( 9 10 19 20 29 30 39 40 49 50 )
		mkdir statistiques/data/$packet_initialisation/size$tailleGrille
		foreach time ( 5 )
			mkdir statistiques/data/$packet_initialisation/size$tailleGrille/time$time
			foreach weigth ( 0.0 0.05 0.1 0.15 0.2 0.25 0.3 0.35 0.4 0.45 0.5 0.55 0.6 0.65 0.7 0.75 0.8 0.85 0.9 0.95 1.0 )
				#rm -r statistiques/data/$packet_initialisation/size$tailleGrille/time$time/weigth$weigth => prend trop de temps et pas utile
				mkdir statistiques/data/$packet_initialisation/size$tailleGrille/time$time/weigth$weigth
            	printf "\n------------------------------------------------------------------------\n"
            	printf "\nTest tailleGrille=$tailleGrille time=$time weigth=$weigth\n\n"
            	java -cp bin main.java.controler.Printer model=PFModel show=false context="packet_initialisation=$packet_initialisation;weigth=$weigth;size=$tailleGrille;mapToSave=ReceiveMap;pathToSave=statistiques/data/$packet_initialisation/size$tailleGrille/time$time/weigth$weigth/" it=$iteration scenario="wait=$time;" core=4
        	end
    	end
	end
end
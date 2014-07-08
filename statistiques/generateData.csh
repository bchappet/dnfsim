#!/bin/csh

cd ..

foreach packet_initialisation ( "a_send" "b_send" "ab_send")
	mkdir statistiques/data/$packet_initialisation
	foreach tailleGrille ( 9 ) # ( 9 10 19 20 29 30 39 40 49 50 )
		mkdir statistiques/data/$packet_initialisation/size$tailleGrille
		foreach time ( 5 ) # (1 2 3 4 5 6 7 8 9 10)
			mkdir statistiques/data/$packet_initialisation/size$tailleGrille/time$time
			foreach weigth ( 0.0 0.5 ) # ( 0.1 0.2 0.3 0.4 0.5 0.6 0.7 0.8 0.9 )
				rm -r statistiques/data/$packet_initialisation/size$tailleGrille/time$time/weigth$weigth
				mkdir statistiques/data/$packet_initialisation/size$tailleGrille/time$time/weigth$weigth
				printf "\n------------------------------------------------------------------------\n"
				printf "\nTest tailleGrille=$tailleGrille time=$time weigth=$weigth\n\n"
				java -cp bin main.java.controler.Printer model=PFModel show=false context="packet_initialisation=		$packet_initialisation;weigth=$weigth;size=$tailleGrille;mapToSave=ReceiveMap;pathToSave=statistiques/data/$packet_initialisation/size$tailleGrille/time$time/weigth$weigth/" it=100 scenario="wait=$time;" core=2
			end
		end
	end
end

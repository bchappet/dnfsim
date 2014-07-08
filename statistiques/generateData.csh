#!/bin/csh

#foreach tailleGrille ( 9 10 19 20 29 30 39 40 49 50 59 60 69 70 79 80 89 90 99 100 )
	#mkdir data/size$tailleGrille
	#java -cp bin main.java.controler.Printer model=PFModel show=false context="size=$tailleGrille;mapToSave=ReceiveMap;pathToSave=data/size$tailleGrille/" it=1 scenario="wait=0.1;" core=2
#end

#rm -r data/*

foreach tailleGrille ( 9 )
	mkdir data/size$tailleGrille
	foreach time (1 2 3 4 5 6 7 8 9 10)
		rm -r data/size$tailleGrille/time$time
		mkdir data/size$tailleGrille/time$time
		echo "------------------------------------------------------------------------"
		echo "Test tailleGrille=$tailleGrille time=$time"
		java -cp bin main.java.controler.Printer model=PFModel show=false context="size=$tailleGrille;mapToSave=ReceiveMap;pathToSave=data/size$tailleGrille/time$time/" it=100 scenario="wait=$time;" core=2
	end
end

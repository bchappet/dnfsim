package main.java.initRecherche;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.console.CNFTCommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.maps.ConvolutionMatrix2D;
import main.java.maps.Leaf;
import main.java.maps.Map;
import main.java.maps.Parameter;
import main.java.maps.Var;
import main.java.model.ModelCNFT;
import main.java.unitModel.RateCodedUnitModel;

public class ModelCNFTPredictive extends ModelCNFT {

    protected Parameter closestTrackX; //coordon�eX de la cible la plus proche
    protected Parameter closestTrackY; //coordon�eY de la cible la plus proche
    
    //east
    protected Map predictiveMapEast1; //vecteur : 0,1
    protected Map predictiveMapEast2; //vecteur : 0,2
    protected Map predictiveMapEast3; //vecteur : 0,3
    //south
    protected Map predictiveMapSouth1; //vecteur : 1,0
    protected Map predictiveMapSouth2; //vecteur : 2,0
    protected Map predictiveMapSouth3; //vecteur : 3,0
    //west
    protected Map predictiveMapWest1; //vecteur : 0,-1
    protected Map predictiveMapWest2; //vecteur : 0,-2
    protected Map predictiveMapWest3; //vecteur : 0,-3
    //north
    protected Map predictiveMapNorth1; //vecteur : -1,0
    protected Map predictiveMapNorth2; //vecteur : -2,0
    protected Map predictiveMapNorth3; //vecteur : -3,0
    //north east
    protected Map predictiveMapNorthEast1; //vecteur : -1,1
    protected Map predictiveMapNorthEast2; //vecteur : -1,2
    protected Map predictiveMapNorthEast3; //vecteur : -1,3
    //south east
    protected Map predictiveMapSouthEast1; //vecteur : 1,1
    protected Map predictiveMapSouthEast2; //vecteur : 2,2
    protected Map predictiveMapSouthEast3; //vecteur : 3,3
    //south west
    protected Map predictiveMapSouthWest1; //vecteur : 1,-1
    protected Map predictiveMapSouthWest2; //vecteur : 2,-2
    protected Map predictiveMapSouthWest3; //vecteur : 3,-3
    //north west
    protected Map predictiveMapNorthWest1; //vecteur : -1,-1
    protected Map predictiveMapNorthWest2; //vecteur : -2,-2
    protected Map predictiveMapNorthWest3; //vecteur : -3,-3
    
    protected Map biaisedInput;
    
    protected Map globalPredictionMap; //combinaison des pr�dictions
    
    protected List<Map> listPredictive;
    protected List<Map> listPonderation;
    
    //protected Map speedMapX; //vitesse X
    //protected Map speedMapY; //et vitesse Y
    
    public static final String PREDICTIVE = "Predictive";
    public static final String PONDERATION = "Ponderation";
    public static final String PONDERATION_PARAMETER = "PonderationParameter";
    public static final String GLOBAL_PREDICTION = "GlobalPrediction";
    public static final String BIAISED_INPUT = "BiaisedInput";
    public final static int NB_PREDICTIONS = 24;

    public ModelCNFTPredictive(String name) {
        super(name);
    }
    
	public void update(BigDecimal timeToReach) throws NullCoordinateException,
	CommandLineFormatException {
		super.update(timeToReach);
		
		
		
	}


    /**
     * Construct the main.java.model architecture
     * @throws CommandLineFormatException if the given parameter was not initialized in
     * the default script or main.java.model script
     * @throws NullCoordinateException
     * @throws CloneNotSupportedException
     */
    protected void initModel() throws CommandLineFormatException, NullCoordinateException
    {
        Var vdt = command.get(CNFTCommandLine.DT); //default dt
        initLateralWeights();
        cnft = new ConvolutionMatrix2D(CNFT,vdt,extendedComputationSpace);

        potential = new Map(POTENTIAL,new RateCodedUnitModel(),vdt,extendedComputationSpace);
        biaisedInput = new Map(BIAISED_INPUT,new BiaisedInputUnitModel(),vdt,extendedComputationSpace);

        listPredictive = new ArrayList<Map>();
        listPonderation = new ArrayList<Map>();
        
        initPredictive(vdt);
        initPonderation(vdt);        
        initGlobalPrediction(vdt);
        biaisedInput.addParameters(input,globalPredictionMap);
        biaisedInput.constructMemory();
        this.addParameters(biaisedInput);
        potential.addParameters(new Leaf(potential),command.get(CNFTCommandLine.TAU),
biaisedInput,cnft,command.get(CNFTCommandLine.RESTING_POTENTIAL));
        cnft.addParameters(cnftW,new Leaf(potential));
        cnft.constructMemory();
        potential.constructMemory();

         //Construire  speedMapX,Y et predictiveMap
        //et ajouter les param�tres de predictiveMap: ceux de speedMap le sont dans la m�those initializeStatistics()

        //speedMapX = new Map(PREDICTIVE,new SpeedUnitModel(),vdt,extendedComputationSpace);
        //speedMapY = new Map(PREDICTIVE,new SpeedUnitModel(),vdt,extendedComputationSpace);
        
        
        
        
        this.root = potential;
    }

    @Override
    public List<Parameter> getDefaultDisplayedParameter() {
        Parameter[] ret = {input,cnftW,cnft,potential,globalPredictionMap,biaisedInput};
        return Arrays.asList(ret);
    }
    
    protected void initGlobalPrediction(Var vdt) {
        
        //carte de combinaison des pr�dictions
        globalPredictionMap = new GlobalPredictionMap(GLOBAL_PREDICTION,new CombinaisonUnitModel(),vdt,extendedComputationSpace);
        
        for(int i=0; i<listPonderation.size(); i++) {
        	globalPredictionMap.addParameters(listPonderation.get(i));
        }
        
        for(int i=0; i<listPredictive.size(); i++) {
        	globalPredictionMap.addParameters(listPredictive.get(i));
        }
        
        this.addParameters(globalPredictionMap);
    }
    
    protected void initPonderation(Var vdt) {
    	Map ponderationMap;
        
        for(int i=0; i<listPredictive.size(); i++) {
        	ponderationMap = new Map(PONDERATION+(i+1),new PonderationUnitModel(),vdt,extendedComputationSpace);
        	ponderationMap.addParameters(new Leaf(potential),new Leaf(listPredictive.get(i)));
        	listPonderation.add(ponderationMap);
        	ponderationMap.constructMemory();/*********************************/
        	this.addParameters(ponderationMap); 
        }

    }
    
    protected void initPredictive(Var vdt) {
        
        predictiveMapEast1 = new Map(PREDICTIVE+1,new PredictiveUnitModel(),vdt,extendedComputationSpace);
        predictiveMapEast1.addParameters(new Leaf(potential),new Var("speedX",0),new Var("speedY",1));
        listPredictive.add(predictiveMapEast1);        
        predictiveMapEast2 = new Map(PREDICTIVE+2,new PredictiveUnitModel(),vdt,extendedComputationSpace);
        predictiveMapEast2.addParameters(new Leaf(potential),new Var("speedX",0),new Var("speedY",2));
        listPredictive.add(predictiveMapEast2);       
        predictiveMapEast3 = new Map(PREDICTIVE+3,new PredictiveUnitModel(),vdt,extendedComputationSpace);
        predictiveMapEast3.addParameters(new Leaf(potential),new Var("speedX",0),new Var("speedY",3));
        listPredictive.add(predictiveMapEast3);
        

        
        predictiveMapSouth1 = new Map(PREDICTIVE+4,new PredictiveUnitModel(),vdt,extendedComputationSpace);
        predictiveMapSouth1.addParameters(new Leaf(potential),new Var("speedX",1),new Var("speedY",0));
        listPredictive.add(predictiveMapSouth1);         
        predictiveMapSouth2 = new Map(PREDICTIVE+5,new PredictiveUnitModel(),vdt,extendedComputationSpace);
        predictiveMapSouth2.addParameters(new Leaf(potential),new Var("speedX",2),new Var("speedY",0));
        listPredictive.add(predictiveMapSouth2);         
        predictiveMapSouth3 = new Map(PREDICTIVE+6,new PredictiveUnitModel(),vdt,extendedComputationSpace);
        predictiveMapSouth3.addParameters(new Leaf(potential),new Var("speedX",3),new Var("speedY",0));
        listPredictive.add(predictiveMapSouth3); 
       
        predictiveMapWest1 = new Map(PREDICTIVE+7,new PredictiveUnitModel(),vdt,extendedComputationSpace);
        predictiveMapWest1.addParameters(new Leaf(potential),new Var("speedX",0),new Var("speedY",-1));
        listPredictive.add(predictiveMapWest1);        
        predictiveMapWest2 = new Map(PREDICTIVE+8,new PredictiveUnitModel(),vdt,extendedComputationSpace);
        predictiveMapWest2.addParameters(new Leaf(potential),new Var("speedX",0),new Var("speedY",-2));
        listPredictive.add(predictiveMapSouth2);        
        predictiveMapWest3 = new Map(PREDICTIVE+9,new PredictiveUnitModel(),vdt,extendedComputationSpace);
        predictiveMapWest3.addParameters(new Leaf(potential),new Var("speedX",0),new Var("speedY",-3));
        listPredictive.add(predictiveMapWest3); 
        
        predictiveMapNorth1 = new Map(PREDICTIVE+10,new PredictiveUnitModel(),vdt,extendedComputationSpace);
        predictiveMapNorth1.addParameters(new Leaf(potential),new Var("speedX",-1),new Var("speedY",0));
        listPredictive.add(predictiveMapNorth1);        
        predictiveMapNorth2 = new Map(PREDICTIVE+11,new PredictiveUnitModel(),vdt,extendedComputationSpace);
        predictiveMapNorth2.addParameters(new Leaf(potential),new Var("speedX",-2),new Var("speedY",0));
        listPredictive.add(predictiveMapNorth2);         
        predictiveMapNorth3 = new Map(PREDICTIVE+12,new PredictiveUnitModel(),vdt,extendedComputationSpace);
        predictiveMapNorth3.addParameters(new Leaf(potential),new Var("speedX",-3),new Var("speedY",0));
        listPredictive.add(predictiveMapNorth3); 
       
        predictiveMapNorthEast1 = new Map(PREDICTIVE+13,new PredictiveUnitModel(),vdt,extendedComputationSpace);
        predictiveMapNorthEast1.addParameters(new Leaf(potential),new Var("speedX",-1),new Var("speedY",1));
        listPredictive.add(predictiveMapNorthEast1);         
        predictiveMapNorthEast2 = new Map(PREDICTIVE+14,new PredictiveUnitModel(),vdt,extendedComputationSpace);
        predictiveMapNorthEast2.addParameters(new Leaf(potential),new Var("speedX",-1),new Var("speedY",2));
        listPredictive.add(predictiveMapNorth2);        
        predictiveMapNorthEast3 = new Map(PREDICTIVE+15,new PredictiveUnitModel(),vdt,extendedComputationSpace);
        predictiveMapNorthEast3.addParameters(new Leaf(potential),new Var("speedX",-1),new Var("speedY",3));
        listPredictive.add(predictiveMapNorthEast3);
        
        predictiveMapSouthEast1 = new Map(PREDICTIVE+16,new PredictiveUnitModel(),vdt,extendedComputationSpace);
        predictiveMapSouthEast1.addParameters(new Leaf(potential),new Var("speedX",1),new Var("speedY",1));
        listPredictive.add(predictiveMapSouthEast1);         
        predictiveMapSouthEast2 = new Map(PREDICTIVE+17,new PredictiveUnitModel(),vdt,extendedComputationSpace);
        predictiveMapSouthEast2.addParameters(new Leaf(potential),new Var("speedX",2),new Var("speedY",2));
        listPredictive.add(predictiveMapSouthEast2); 
        predictiveMapSouthEast3 = new Map(PREDICTIVE+18,new PredictiveUnitModel(),vdt,extendedComputationSpace);
        predictiveMapSouthEast3.addParameters(new Leaf(potential),new Var("speedX",3),new Var("speedY",3));
        listPredictive.add(predictiveMapSouthEast3); 
        
        predictiveMapSouthWest1 = new Map(PREDICTIVE+19,new PredictiveUnitModel(),vdt,extendedComputationSpace);
        predictiveMapSouthWest1.addParameters(new Leaf(potential),new Var("speedX",1),new Var("speedY",-1));
        listPredictive.add(predictiveMapSouthWest1); 
        predictiveMapSouthWest2 = new Map(PREDICTIVE+20,new PredictiveUnitModel(),vdt,extendedComputationSpace);
        predictiveMapSouthWest2.addParameters(new Leaf(potential),new Var("speedX",2),new Var("speedY",-2));
        listPredictive.add(predictiveMapSouthWest2); 
        predictiveMapSouthWest3 = new Map(PREDICTIVE+21,new PredictiveUnitModel(),vdt,extendedComputationSpace);
        predictiveMapSouthWest3.addParameters(new Leaf(potential),new Var("speedX",3),new Var("speedY",-3));
        listPredictive.add(predictiveMapSouthWest3); 
        
        predictiveMapNorthWest1 = new Map(PREDICTIVE+22,new PredictiveUnitModel(),vdt,extendedComputationSpace);
        predictiveMapNorthWest1.addParameters(new Leaf(potential),new Var("speedX",-1),new Var("speedY",-1));
        listPredictive.add(predictiveMapNorthWest1); 
        predictiveMapNorthWest2 = new Map(PREDICTIVE+23,new PredictiveUnitModel(),vdt,extendedComputationSpace);
        predictiveMapNorthWest2.addParameters(new Leaf(potential),new Var("speedX",-2),new Var("speedY",-2));
        listPredictive.add(predictiveMapNorthWest2); 
        predictiveMapNorthWest3 = new Map(PREDICTIVE+24,new PredictiveUnitModel(),vdt,extendedComputationSpace);
        predictiveMapNorthWest3.addParameters(new Leaf(potential),new Var("speedX",-3),new Var("speedY",-3));
        listPredictive.add(predictiveMapNorthWest3); 

        
        for(Map map : listPredictive) {
        	this.addParameters(map); 
        	map.constructMemory();/**********************************************/
        }
        
    }

    /*protected void initializeStatistics() throws CommandLineFormatException {
        super.initializeStatistics();
        closestTrackX = this.stats.getParam(Statistics.TRACK_X);
        closestTrackY = this.stats.getParam(Statistics.TRACK_Y);
        closestTrackX.addMemories(1); // we need to access the previous position
        closestTrackY.addMemories(1);
        speedMapX.addParameters(closestTrackX);
        speedMapY.addParameters(closestTrackY);
    }*/

}

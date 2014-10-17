package main.java.controler;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import main.java.network.generic.Utils;
import main.java.view.ComputationControlerView;

/**
 * The {@link ComputationControler} will control the computation of main.java.model and will warn the main.java.view for changes
 * respecting the MVC design
 * @author benoit
 *
 */
public class ComputationControler {

    private final transient Logger LOGGER = Logger.getLogger(ComputationControler.class.getName());


    /**
     * Scale limit for dt (used in big decimal)
     */
    public static final int SCALE_LIMIT = 10;
    public static final int ROUDING_MODE = BigDecimal.ROUND_HALF_EVEN;

    /**Current time of the simulation**/
    private BigDecimal time;

    /**OrderedMap for computation**/
    private List<ComputableControler> orderedMap;

    /**Will handle pause step and delaying if there is a gui (Optional)**/
    private ComputationControlerView view;

    /**Unique id for this iteration**/
    private int iterationId;

    private ParameterControlerTree tree;
    /**
     * Map controler to save at every it
     */
    private MapControler[] mcToSave;

    /**
     * The global command line parameter save
     */
    private String[] mapsToSave;
    private String savePath = "maps/";



    public ComputationControler(ParameterControlerTree tree) {
        this.time = new BigDecimal("0");
        this.iterationId = -99;
        this.orderedMap = new ArrayList<ComputableControler>();
        this.tree = tree;
        initOrderedMap( tree.getRoot());
        LOGGER.info("this.orderedMap initiated.");
        LOGGER.info("this.orderedMap="+this.orderedMap);
    }

    public void setComputationControlerView(ComputationControlerView ccView){
        this.view = ccView;
    }


    /**
     * Fill the ordered map with the computated map in the good order
     *
     * And if we find infiniteDt, we compute the state
     * @param root //TODO register on tree change
     */
    private void initOrderedMap(ParameterControler root) {
        for(int i = 0 ;i < root.getChildCount() ; i++){
            ParameterControler pc =  root.getChild(i);
            if(pc instanceof ComputableControler){
                initOrderedMap( pc);
                this.orderedMap.add( (ComputableControler) pc);
            }
        }
    }

    public void step() {
        LOGGER.info("Step" );
        this.checkFirstComputation();
        this.actualComputation();
    }

    /**
     * Compute the main.java.model main.java.maps until time == timeLimit
     */
    public void compute(){
        this.actualComputation();

    }

    public void checkFirstComputation(){
        if(time.equals(new BigDecimal("0"))){
            LOGGER.info("First computation");
            this.firstComputation();
        }
    }

    public void actualComputation(){
        //This is the time for this simulation
        time = getSmallestNextTime();
        LOGGER.fine("time = " + time);
        for(ComputableControler m : orderedMap){
            if(m.getNextTime().compareTo(time) == 0){
                LOGGER.fine("compute : " + m);
                m.compute(this.time);

            }
        }
        this.saveMap(mapsToSave,savePath);

    }
    /**
     * Called when time = 0
     * we compiute static map
     */
    private void firstComputation() {
        for(ComputableControler m : orderedMap){
            if(m.isStatic()){
                LOGGER.fine("firstComputation : " + m);
                m.compute(this.time);

            }
        }
        this.saveMap(mapsToSave,savePath);

    }

    /**
     * Set the maps to save and get the corresponding map controller to save time
     * They will be save at each computation
     * @param mapsToSave
     */
    public void setMapsToSave(String[] mapsToSave,String savePath) {
        this.mapsToSave = mapsToSave;
        this.savePath = savePath;
        MapControler[] mcToSave = new MapControler[mapsToSave.length];
        for (int i = 0; i < mcToSave.length; i++) {
            // System.out.println("map to save : " + mapsToSave[i]);
            mcToSave[i] = (MapControler) tree.getControler(mapsToSave[i]);
        }
    }

    /**
     * Return the smallest time for next comp
     * @return
     */
    public BigDecimal getSmallestNextTime(){
        BigDecimal smallestNextTime = new BigDecimal(Double.MAX_VALUE);
        for(ComputableControler m : this.orderedMap){
            BigDecimal nextTime = m.getNextTime();
            if(nextTime.compareTo(smallestNextTime) < 0){
                smallestNextTime = nextTime;
            }

        }
        return smallestNextTime;
    }

    public BigDecimal getTime() {
        return this.time;
    }

    /**
     * External way to save specific maps
     * @param mapsToSaveArray
     * @param path
     */
    public void saveMap(String[] mapsToSaveArray,String path) {
        if(mapsToSaveArray != null) {
            for (String map : mapsToSaveArray)
                this.saveMap((MapControler) tree.getControler(map), path);
        }

    }

    private void saveMap(MapControler map,String path) {
        String fileName = path + map.getName() + "_" + this.iterationId +"_"+this.time + ".csv";
        //System.out.println(" Save file : " + fileName);
        Utils.writeCSVFile(new File(fileName), ((MapControler) map).getArray2D());



    }

    public void setComputationId(int iterationId) {
        this.iterationId = iterationId;

    }

    /**
     * Reset all map on original state
     */
    public void reset() {
        this.time = new BigDecimal("0");
        tree.reset();

    }



}
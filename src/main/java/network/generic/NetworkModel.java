package main.java.network.generic;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.maps.Computable;
import main.java.maps.InfiniteDt;
import main.java.maps.Parameter;
import main.java.maps.Trajectory;
import main.java.maps.Var;
import main.java.model.Model;
import main.java.statistics.Statistics;
import main.java.unitModel.UnitModel;

/**
 * Le modèle du reseau. Contient le graphe et permet de faire différents relevés
 * sur le déroulement de la propagation des signaux.
 *
 * @author CARRARA Nicolas
 */
public abstract class NetworkModel extends Model implements Computable {

    private SpreadingGraph spreadingGraph;

    private final SpreadingGraphFactory spreadingGraphFactory;

    public NetworkModel(String name) {
        super(name);
        spreadingGraphFactory = SpreadingGraphFactory.getInstance();
    }

    /**
     * Doit initilialiser spreadingGraph
     */
    protected abstract void constructGraph();


    /*--------------------------- model --------------------------------------*/
    @Override
    protected void initializeStatistics() throws CommandLineFormatException {
        System.out.println("Initializing statistics");
        Var<BigDecimal> stat_dt = command.get(NetworkCommandLine.STAT_DT);

        // main statistic here is the maximum load of buffers
        Trajectory maximumLoad = new Trajectory(
                NetworkStatistics.BUFF_MAX_LOAD,
                stat_dt,
                new UnitModel<Double>(0d) {
                    @Override
                    public Double compute(BigDecimal time, int index, List<Parameter> params) {
                        SpreadingGraph spreadingGraph = (SpreadingGraph) params.get(0);
                        Double maxLoad = (double) spreadingGraph.getMostLoadedNode().getLoad();
                        System.out.println("maxLoad : " + maxLoad);
                        return maxLoad;
                    }
                }, spreadingGraph);

        // load remaining
        Trajectory loadRemaining = new Trajectory(
                NetworkStatistics.LOAD_REMAINING,
                stat_dt,
                new UnitModel<Double>(0d) {
                    @Override
                    public Double compute(BigDecimal time, int index, List<Parameter> params) {
                        SpreadingGraph spreadingGraph = (SpreadingGraph) params.get(0);
                        Double loadRemaining = (double) spreadingGraph.getLoadRemaining();
                        System.out.println("loadRemaining : " + loadRemaining);
                        return loadRemaining;
                    }
                }, spreadingGraph);

        stats = new NetworkStatistics(
                Statistics.NAME,
                NetworkStatistics.BUFF_MAX_LOAD,
                stat_dt,
                maximumLoad,
                loadRemaining,
                spreadingGraph);

    }

    @Override
    protected void initializeCharacteristics() {
        System.out.println("Initializing characteristic");
        // main charac here is the maximum load of buffers
        Trajectory maximumLoad = new Trajectory(
                NetworkCharacteristics.BUFF_MAX_LOAD,
                new InfiniteDt(),
                new UnitModel<Double>(0d) {
                    @Override
                    public Double compute(BigDecimal time, int index, List<Parameter> params) {
                        SpreadingGraph spreadingGraph = (SpreadingGraph) params.get(0);
                        Double maxLoad = (double) spreadingGraph.getMostLoadedNode().getLoad();
                        System.out.println("charac maxLoad : " + maxLoad);
                        return maxLoad;
                    }
                }, spreadingGraph);

        // load remaining
        Trajectory loadRemaining = new Trajectory(
                NetworkCharacteristics.LOAD_REMAINING,
                new InfiniteDt(),
                new UnitModel<Double>(0d) {
                    @Override
                    public Double compute(BigDecimal time, int index, List<Parameter> params) {
                        SpreadingGraph spreadingGraph = (SpreadingGraph) params.get(0);
                        Double loadRemaining = (double) spreadingGraph.getLoadRemaining();
                        System.out.println("charac loadRemaining : " + loadRemaining);
                        return loadRemaining;
                    }
                }, spreadingGraph);

        // transmission time
        Trajectory transmissionTime = new Trajectory(
                NetworkCharacteristics.TRANSMISSION_TIME_BEFORE_CONGESTION,
                new InfiniteDt(),
                new UnitModel<Double>(0d) {
                    @Override
                    public Double compute(BigDecimal time, int index, List<Parameter> params) {
                        //todo
//                SpreadingGraph spreadingGraph = (SpreadingGraph) params.get(0);
//                Double loadRemaining = (double) spreadingGraph.getLoadRemaining();
//                System.out.println("charac loadRemaining : " + loadRemaining);
                        return 0.0;//loadRemaining;
                    }
                }, spreadingGraph);

        charac = new NetworkCharacteristics(
                maximumLoad,
                loadRemaining,
                transmissionTime);

    }

    @Override
    public String getDefaultDisplayedStatistic() {
        return NetworkStatistics.BUFF_MAX_LOAD;
    }

    @Override
    protected void initializeParameters() throws CommandLineFormatException, NullCoordinateException {
        constructGraph();
        this.root = spreadingGraph;
    }

    @Override
    public String[] getDefaultDisplayedParameter() {
        // todo
        return new String[0];
    }

    @Override
    public void modifyModel() throws CommandLineFormatException, NullCoordinateException {
    }

    /*---------------------- computable --------------------------------------*/
    @Override
    public void compute() {
        try {
            int imax = ((Var<Integer>) getCommandLine().get(NetworkCommandLine.STOP_CONDITION_ITERATE_VALUE)).get();
            compute(imax);
        } catch (CommandLineFormatException ex) {
            Logger.getLogger(NetworkModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void compute(int imax) {
        int i = 0;
        while (!computeStopCondition() && (i < imax)) {
            computeAtomique();
            i++;
        }
    }

    public void computeAtomique() {
        spreadingGraph.compute();
    }

    protected abstract boolean computeStopCondition();

    @Override
    public Var<BigDecimal> getDt() {
        // todo
        return null;
    }

    @Override
    public void setTime(BigDecimal currentTime) {
        // todo
    }

    //-----------------------  getteur/setteur ---------------------------------
    /**
     * @return the spreadingGraph
     */
    public SpreadingGraph getSpreadingGraph() {
        return spreadingGraph;
    }

    /**
     * @param spreadingGraph the spreadingGraph to set
     */
    public void setSpreadingGraph(SpreadingGraph spreadingGraph) {
        this.spreadingGraph = spreadingGraph;
    }

    /**
     * @return the spreadingGraphFactory
     */
    public SpreadingGraphFactory getSpreadingGraphFactory() {
        return spreadingGraphFactory;
    }
}

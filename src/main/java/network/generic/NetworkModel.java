package main.java.network.generic;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.console.CommandLineFormatException;
import main.java.maps.Computable;
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

    /*--------------------------- model --------------------------------------*/
    @Override
    protected void initializeStatistics() throws CommandLineFormatException {
        
        Var<BigDecimal> stat_dt = command.get(NetworkCommandLine.STAT_DT);
        
        // main statistic here is the maximum load of buffers
        Trajectory maximumLoad = new Trajectory(NetworkStatistics.BUFF_MAX_LOAD, stat_dt, new UnitModel<Double>(0d) {
            @Override
            public Double compute(BigDecimal time, int index, List<Parameter> params) {
                SpreadingGraph spreadingGraph = (SpreadingGraph) params.get(0);
                Double maxLoad = (double) spreadingGraph.getMostLoadedNode().getBufferPacket().size();
                System.out.println("maxLoad : "+maxLoad);
                return maxLoad;
            }
        }, spreadingGraph);

        stats = new NetworkStatistics(Statistics.NAME, NetworkStatistics.BUFF_MAX_LOAD, stat_dt, maximumLoad, spreadingGraph);

    }

    @Override
    protected void initializeCharacteristics() {

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

    /**
     * Doit initilialiser spreadingGraph
     */
    public abstract void constructGraph();

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

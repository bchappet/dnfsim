package test.java.unitModel;

import junit.framework.TestCase;
import main.java.maps.Map;
import main.java.maps.Trajectory;
import main.java.maps.UnitMap;
import main.java.maps.Var;
import main.java.reservoirComputing.TanhInvUM;
import main.java.space.NoDimSpace;
import main.java.unitModel.CosTraj;
import main.java.unitModel.UMWrapper;
import main.java.view.BigDecimalModifierPanel;

import java.math.BigDecimal;

public class TanhInvUMTest extends TestCase {

    private UnitMap uut;
    private Trajectory<Double> traj;

    public void setUp() throws Exception {
        super.setUp();
        Var dt = new Var<BigDecimal>(BigDecimal.ONE);

       traj = new Trajectory<>("traj",dt,new UMWrapper<>(0d), new Var<Double>(0.3d) );
       uut = new UnitMap("uut",dt,new NoDimSpace(),new TanhInvUM(0d),traj);

    }

    public void tearDown() throws Exception {

    }

    public void testCompute() throws Exception {

        traj.compute();
        uut.compute();
        assertEquals(0.3095196042031117,(double)uut.getIndex(0),1e-8);




    }
}
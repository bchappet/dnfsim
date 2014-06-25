package test.java.view;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.List;

import javax.swing.JFrame;

import main.java.maps.Parameter;
import main.java.maps.Trajectory;
import main.java.maps.Var;
import main.java.space.Coord2D;
import main.java.space.DoubleSpace2D;
import main.java.unitModel.UnitModel;
import main.java.view.DisplaySampleMap2D;

import org.junit.Before;
import org.junit.Test;

public class DisplaySampleMap2DTest extends JFrame {
	DisplaySampleMap2D uut;

	@Before
	public void setUp() throws Exception {
		Var<BigDecimal> dt = new Var<BigDecimal>(new BigDecimal("0"));
		Var<Integer> res = new Var<Integer>("res",20);
		DoubleSpace2D inputSpace = new DoubleSpace2D(new Var<Double>("OriX",0.),new Var<Double>("OriY",0.),
				new Var<Double>("SizeX",1.),new Var<Double>("SizeY",1.),res);
		Trajectory<Coord2D<Double>> input = new Trajectory<Coord2D<Double>>("input", dt, new UnitModel<Coord2D<Double>>(new Coord2D<Double>(0d, 0d)){
			@Override
			protected Coord2D<Double> compute(BigDecimal time, int index,
					List<Parameter> params) {
				DoubleSpace2D inputSpace = (DoubleSpace2D)params.get(0);
				return inputSpace.getGaussianSample();
			}
		}, inputSpace);
		
		 uut = new DisplaySampleMap2D("uut", new Coord2D<Double>(0d,0d),inputSpace.getOrigin()
				, inputSpace.getLength());
		
		this.add(uut);
		
		this.setSize(300,300);
		this.setVisible(true);
		
	}

	@Test
	public void testRender() throws InterruptedException {
		Thread.sleep(1000);
		uut.update(new Coord2D<Double>(0.5,0.5));
		this.repaint();
		Thread.sleep(10000);
		assertTrue("visual test ",true);
		
	}
	

}

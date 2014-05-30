package test.java.view;

import java.math.BigDecimal;

import javax.swing.JFrame;

import main.java.maps.UnitMap;
import main.java.maps.Var;
import main.java.neigborhood.V4Neighborhood2D;
import main.java.space.Coord2D;
import main.java.space.DoubleSpace2D;
import main.java.space.Space2D;
import main.java.unitModel.UMWrapper;
import main.java.view.Vector2DMapSOMView;

import org.junit.Before;
import org.junit.Test;

public class Vector2DMapSOMViewTest extends JFrame{
	
	Vector2DMapSOMView uut;

	@Before
	public void setUp() throws Exception {
		
		Var<Integer> res = new Var<Integer>("res",10);
		Space2D space = new Space2D(res, res);
		DoubleSpace2D inputSpace = new DoubleSpace2D(new Var<Double>("OriX",0.),new Var<Double>("OriY",0.),
				new Var<Double>("SizeX",1.),new Var<Double>("SizeY",1.),res);
		Var<BigDecimal> dt = new Var<BigDecimal>(new BigDecimal(0.1));
		UnitMap<Coord2D<Double>,Integer> afferent_weights = new UnitMap<Coord2D<Double>,Integer>("map",dt,space,
				new UMWrapper<Coord2D<Double>>(new Coord2D<Double>(0d,0d)));//afferent_weights
		for(int i = 0 ; i < afferent_weights.getSpace().getVolume() ; i++){
			Coord2D<Double> coord = (Coord2D<Double>) inputSpace.indexToCoord(i);
			afferent_weights.set(i, coord);
		}
		
		uut = new Vector2DMapSOMView("uut", afferent_weights.getValues(), inputSpace.getOrigin(), inputSpace.getLength(),
				new V4Neighborhood2D<Double>(inputSpace),space.getVolume());
	}

	@Test
	public void testRender() throws InterruptedException {
		this.add(uut);
		this.setSize(300,300);
		this.setVisible(true);
		this.repaint();
		Thread.sleep(10000);
	}

}

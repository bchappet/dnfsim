package test.java.unitModel;

import static org.junit.Assert.fail;

import javax.print.DocFlavor.INPUT_STREAM;

import main.java.maps.InfiniteDt;
import main.java.maps.Trajectory;
import main.java.maps.UnitMap;
import main.java.maps.Var;
import main.java.unitModel.NARMAnthOrderUM;
import main.java.unitModel.RandTrajUnitModel;

import org.junit.Before;
import org.junit.Test;

public class NARMAnthOrderUMTest {
	
	private Trajectory<Double> uut;
	private Trajectory<Double> input;

	@Before
	public void setUp() throws Exception {
		Var<Integer> N = new Var<Integer>("N",10);
		 input =new Trajectory<Double>("input", new InfiniteDt(), new RandTrajUnitModel(0d),new Var<>("center",0.25),new Var<>("radius",0.25) );
		uut = new Trajectory<Double>("uut", new InfiniteDt(), new NARMAnthOrderUM(0d));
		uut.addParameters(input,uut,N);
		
		Double[] mem = new Double[N.get()];
		for(int i = 0 ; i < N.get() ; i++){
			mem[i] = Math.random()*0.5;
		}
		uut.addMemories(N.get());
		input.addMemories(N.get(),mem);
		
	}

	@Test
	public void test() {
		System.out.println(uut.get());
		for(int i = 0 ; i < 100 ; i++){
			input.compute();
			uut.compute();
			System.out.println("i : " + input.get() + " x ; " + uut.get());
		}
	}

}

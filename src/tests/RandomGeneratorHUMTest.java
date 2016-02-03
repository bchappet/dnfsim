//package tests;
//
//import coordinates.DefaultRoundedSpace;
//import hardSimulator.NeuronRandomGeneratorHUM;
//import junit.framework.TestCase;
//import maps.Var;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import precision.PrecisionVar;
//
//public class RandomGeneratorHUMTest  extends TestCase {
//
//	NeuronRandomGeneratorHUM rg;
//
//	@Before
//	public void setUp() throws Exception {
//		rg = new NeuronRandomGeneratorHUM(new Var(0.1),
//				new DefaultRoundedSpace(new Var(19), 2, true),
//				new PrecisionVar(512,new Var(10)));
//	}
//
//	@After
//	public void tearDown() throws Exception {
//		rg = null;
//	}
//
//	@Test
//	public void test() {
//		rg.compute();
//		System.out.println(rg.getRandom());
//
//		int max = Integer.MAX_VALUE/1000;
//
//		int[] res = new int[max];
//
//		for(int i = 0 ; i < max ; i++){
//			res[i] = (int) rg.computeActivity();
//		}
//
//		int sum = 0;
//		for(int i = 0 ; i < max ; i ++){
//			sum += res[i];
//		}
//		System.out.println(sum + " ==> " + (Integer.MAX_VALUE/1000)/2);
//
//	}
//
//}

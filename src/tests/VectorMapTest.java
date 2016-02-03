//package tests;
//
//import static org.junit.Assert.fail;
//import maps.Var;
//import maps.VectorMap;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import unitModel.UnitModel;
//import coordinates.DefaultRoundedSpace;
//import coordinates.NullCoordinateException;
//import coordinates.Space;
//
//public class VectorMapTest {
//
//	private VectorMap uut;
//	@Before
//	public void setUp() throws Exception {
//		Space space = new DefaultRoundedSpace(new Var(49), 2, true);
//		uut = new VectorMap("UUT",new Var("dt",0.1),space) {
//
//
//
//			@Override
//			public void compute() throws NullCoordinateException {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void addMemories(int nb, UnitModel... historic)
//					throws NullCoordinateException {
//				// TODO Auto-generated method stub
//
//			}
//		};
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}
//
//	@Test
//	public void test1() {
//		fail("Not yet implemented");
//	}
//
//}

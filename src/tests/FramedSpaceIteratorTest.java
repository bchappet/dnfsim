package tests;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import maps.FramedSpaceIterator;
import maps.Unit;
import maps.Var;

import org.junit.Before;
import org.junit.Test;

import unitModel.ConstantUnit;
import coordinates.DefaultRoundedSpace;
import coordinates.Space;

public class FramedSpaceIteratorTest {
	
	private FramedSpaceIterator it;

	@Before
	public void setUp() throws Exception {
		List<Unit> list = new ArrayList<Unit>();
		
		
		Space space = new DefaultRoundedSpace(new Var(49), 2, false); 
		Space extendedSpace = space.extend(2, true);
		
		for(int i = 0 ; i < extendedSpace.getDiscreteVolume() ; i++){
			list.add(new Unit(new ConstantUnit(i)));
		}
		it = new FramedSpaceIterator(extendedSpace, list);
	}

	@Test
	public void test() {
		while(it.hasNext()){
			System.out.println(it.next().get());
		}
	}

}

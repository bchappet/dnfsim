package test.java.view;
import static org.junit.Assert.*;
import main.java.view.AdaptiveAndEquilibratedColorMap;
import main.java.view.ViewConfiguration;

import org.junit.Before;
import org.junit.Test;

public class ViewConfigurationTest {

	private ViewConfiguration uut;
	@Before
	public void setUp() throws Exception {
		uut = new ViewConfiguration("src/test/scripts/gui/testViewConfig.gui");
	}
	
	
	@Test 
	public void testFirstParameters(){
		assertEquals("The color map type should be AdaptativeAndEquilibratedMap",
				AdaptiveAndEquilibratedColorMap.class,uut.getColorMap().getClass());
	}

	@Test
	public void testDisplay() {
		System.out.println(uut.getTree().printTree( 0));
		assertTrue(true);
	}

}

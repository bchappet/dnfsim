package globalTests;

import junit.framework.Test;
import junit.framework.TestSuite;
import tests.*;

public class Global_hardware_tests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Hardware tests" );
		suite.addTestSuite(test_random_generator_caprng.class);
		suite.addTestSuite(test_random_generator_wrap_caprng.class);
		suite.addTestSuite(test_RSDNFNetworkVisualAttention.class);
		suite.addTestSuite(test_spiking_neuron.class);
		suite.addTestSuite(test_spiking_neuron_caprng.class);
		suite.addTestSuite(test_spiking_unit.class);
		suite.addTestSuite(test_transmitter4.class);
		return suite;
	}
	public static void main(String args[]) {
		junit.textui.TestRunner.run(suite());

	}

}

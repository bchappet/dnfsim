package globalTests;

import junit.framework.Test;
import junit.framework.TestSuite;
import tests.*;

public class Global_test_all {

	public static Test suite() {
		TestSuite suite = new TestSuite("All tests" );
		suite.addTestSuite(ArrayUtilsTest.class);
		suite.addTestSuite(Assymetric2DRoutingTest.class);
		suite.addTestSuite(BufferedNeuronUMTest.class);
		suite.addTestSuite(BufferHUMTest.class);
		suite.addTestSuite(BullesTest.class);
		suite.addTestSuite(CAUnitModelTest.class);
		suite.addTestSuite(ColorMapTest.class);
		suite.addTestSuite(CommandLineTest.class);
		suite.addTestSuite(ConvolutionTest.class);
		suite.addTestSuite(ConvolutionTest2.class);
		suite.addTestSuite(CoorTest.class);
		suite.addTestSuite(DisplaySampleMapTest.class);
		suite.addTestSuite(FFTConvolutionMatrix2DTest.class);
		suite.addTestSuite(FixedPointTest.class);
		suite.addTestSuite(FramedSpaceIteratorTest.class);
		suite.addTestSuite(GaussianMapTest.class);
		suite.addTestSuite(GaussianNDTest.class);
//		suite.addTestSuite(GlobalTestCNFT.class);
		//suite.addTestSuite(GlobalTestCNFTFFT.class);
		//suite.addTestSuite(GlobalTestCNFTSlow.class);
		//suite.addTestSuite(GlobalTestCompareSpeedQuinton.class);
		//suite.addTestSuite(GlobalTestCompareWithQuintonsDNF.class);
		suite.addTestSuite(HardwareTest.class);
		suite.addTestSuite(MapTest.class);
		suite.addTestSuite(MatrixFileReaderTest.class);
		suite.addTestSuite(ModelNSpike2Test.class);
//		suite.addTestSuite(ModelTimeTest.class);
		suite.addTestSuite(NeuronHUMTest.class);
		suite.addTestSuite(PotentialNeuronUMTest.class);
		suite.addTestSuite(PrecisionVarTest.class);
//		suite.addTestSuite(PRNGUnitModelTest.class);
		//suite.addTestSuite(PRNGWrapperUMTest.class);
		//suite.addTestSuite(PRNGWrapperUMTest2.class);
		//suite.addTestSuite(RandomGeneratorHUMTest.class);
		suite.addTestSuite(RoundedSpaceTest.class);
		suite.addTestSuite(SomUMTest.class);
		suite.addTestSuite(SourceHUMTest.class);
		suite.addTestSuite(SpikingNeuronHUMMapTest.class);
		suite.addTestSuite(SpikingNeuronHUMTest.class);
		suite.addTestSuite(SVDTest.class);
		suite.addTestSuite(test_random_generator_caprng.class);
		suite.addTestSuite(test_random_generator_wrap_caprng.class);
		suite.addTestSuite(test_RSDNFNetworkVisualAttention.class);
		suite.addTestSuite(test_spiking_neuron_caprng.class);
		suite.addTestSuite(test_spiking_neuron.class);
		suite.addTestSuite(test_spiking_unit.class);
		suite.addTestSuite(test_transmitter4.class);
		suite.addTestSuite(TestInputParallel.class);
		suite.addTestSuite(UnitModelTest.class);
		suite.addTestSuite(UnitTest.class);
		suite.addTestSuite(V4Neighborhood2DTest.class);
		suite.addTestSuite(VHDLGeneratorCNFTTest.class);
		suite.addTestSuite(VHDLNeuronGeneratorCNFTTest.class);
		suite.addTestSuite(WrappedConvolutionNeigborhoodTest.class);
		suite.addTestSuite(CharacClosestTrackTest.class);
		return suite;
	}
	public static void main(String args[]) {
		junit.textui.TestRunner.run(suite());

	}
	
	

}

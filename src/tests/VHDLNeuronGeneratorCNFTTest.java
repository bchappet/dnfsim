package tests;

import java.io.IOException;

import maps.AbstractMap;
import maps.Matrix2D;
import maps.Parameter;
import maps.Var;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import vhdl.VHDLNeuronGeneratorCNFT;
import coordinates.DefaultRoundedSpace;
import coordinates.Space;

public class VHDLNeuronGeneratorCNFTTest {
	
	VHDLNeuronGeneratorCNFT vhg;
	Parameter weights;
	int res;

	@Before
	public void setUp() throws Exception {
		res = 3;
		Space space = new DefaultRoundedSpace(new Var(res), 2, true);
		weights = new Matrix2D("weights",new Var(0.1),space,new double[]{0d,1d,2d,3d,4d,5d,6d,7d,8d});
		vhg = new VHDLNeuronGeneratorCNFT(0,0,res,weights,weights);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWeight() {
		
		vhg.weight(2,2,0,0,new Var(2));
		
	}
	
	@Test
	public void testWeight2() {
		System.out.println(((AbstractMap) weights).display2D());
		System.out.println(vhg.generateWeights(1, 0, weights,"exc"));
	}
	@Test
	public void testPotentialPorts() {
		System.out.println(vhg.generatePotentialPorts(res));
	}
	
	@Test
	public void testConnectPotentialMap() {
		System.out.println(vhg.generateConnectPotentialMap());
	}
	
	@Test
	public void testGenerateFile() throws IOException{
		vhg.generateNeuron("vhdlSource", "CNFTMixt");
	}
	
	
	

}

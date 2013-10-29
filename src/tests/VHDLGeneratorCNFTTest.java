package tests;

import java.io.IOException;
import java.util.Arrays;

import junit.framework.TestCase;

import maps.Map;
import maps.Matrix2D;
import maps.Parameter;
import maps.TrajectoryUnitMap;
import maps.Var;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import console.CNFTCommandLine;
import console.CommandLine;
import console.CommandLineFormatException;

import unitModel.GaussianND;
import unitModel.UnitModel;
import vhdl.VHDLGeneratorCNFT;
import coordinates.DefaultRoundedSpace;
import coordinates.NullCoordinateException;
import coordinates.Space;

public class VHDLGeneratorCNFTTest  extends TestCase {

	VHDLGeneratorCNFT gen;
	int res;
	CommandLine command;
	Parameter hpA,hpB,pa,pb;

	/**
	 * Initialize somme Parameter used in lateral weights
	 * the objective here is to normalize some parameters
	 * Warning : use "this.space.getSimulationSpace().getResolution()" to acces the
	 * 	original resolution of the model and not the extended space resolution
	 * @throws CommandLineFormatException
	 * @throws CloneNotSupportedException
	 * @throws NullCoordinateException 
	 */
	protected void initLateralWeightParams(final Space extendedSpace) 
			throws CommandLineFormatException
			{

		Parameter alphaP = command.get(CNFTCommandLine.ALPHA);

		Parameter pA = command.get(CNFTCommandLine.IA);
		hpA = new TrajectoryUnitMap("A_hidden",command.get(CNFTCommandLine.DT),
				extendedSpace,pA,alphaP) {

			@Override
			public double computeTrajectory(double... param) {
				return param[0] / 
						(extendedSpace.getSimulationSpace().getResolution()*
								extendedSpace.getSimulationSpace().getResolution()) *
								(40*40)/(param[1]);
			}
		}; 
		hpA.toStatic();


		Parameter pB = command.get(CNFTCommandLine.IB);
		hpB = new TrajectoryUnitMap("B_hidden",command.get(CNFTCommandLine.DT),
				extendedSpace,pB,alphaP) {

			@Override
			public double computeTrajectory(double... param)  {
				return param[0] / 
						(extendedSpace.getSimulationSpace().getResolution()*
								extendedSpace.getSimulationSpace().getResolution()) *
								(40*40)/param[1];
			}
		}; 
		hpB.toStatic();

		pa = command.get(CNFTCommandLine.WA);
		pb = command.get(CNFTCommandLine.WB);

			}






	@Before
	public void setUp() throws Exception {
		res = 19;
		command = new CommandLine(
				"ia=1.25;"
						+"ib=-0.70;"
						+"wa=0.10;"
						+"wb=1.00;"
						+"tau=0.75;"
						+"alpha=10.0;"
						+"dt=0.1");

		Space space = new DefaultRoundedSpace(new Var(res), 2, true);
		initLateralWeightParams(space);

		UnitModel a = new GaussianND(new Var(0.1), space, pa, hpA, new Var(0),new Var(0));
		Map cnfta = new Map("cnftW" + "_A",a);
		cnfta.toStatic();
		UnitModel b = new GaussianND(new Var(0.1), space, pb, hpB, new Var(0),new Var(0));
		Map cnftb = new Map("cnftW" + "_B",b);
		cnftb.toStatic();
		//System.out.println(cnfta.display2D());
		gen   = new VHDLGeneratorCNFT(res,cnfta,cnftb);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws IOException {
		gen.generateFile("vhdlSource","CNFTMap");
		gen.generateNeuronsFiles("vhdlSource","neuron_mixt");
	}

}

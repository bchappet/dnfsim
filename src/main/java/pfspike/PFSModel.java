package main.java.pfspike;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.maps.UnitMap;
import main.java.maps.Var;
import main.java.model.ModelNSpike;
import main.java.network.generic.AccumulationUnitMap;
import main.java.network.generic.ConcentrationUnitMap;
import main.java.network.generic.NetworkException;
import main.java.network.generic.SpreadingGraphFactory;
import main.java.network.generic.TypeGraph;
import main.java.network.probalisticFlooding.PFUtils;
import main.java.space.Space2D;
import main.java.unitModel.Sum;

public class PFSModel extends ModelNSpike{

	public PFSModel(String name) {
		super(name);
	}

	@Override
	public CommandLine constructCommandLine(){
		return new PFSCommandLine();
	}

	@Override
	protected void initLateralWeights() throws NullCoordinateException, CommandLineFormatException 
	{
		try {
			Var<BigDecimal> dt = (Var<BigDecimal>)getCommandLine().get(PFSCommandLine.MAIN_DT);
			Var<Integer> size = (Var<Integer>) getCommandLine().get(PFSCommandLine.SIZE);
			String path = (String) command.get(PFSCommandLine.TRANSITION_MATRIX_FILE).get();
			String pathMatrixTransitionFile = 
					((Var<String>)getCommandLine().get(PFSCommandLine.TRANSITION_MATRIX_FILE)).get();
			Var<String> write = (Var<String>)((PFSCommandLine)command).get(PFSCommandLine.WRITE_TRANSITION_MATRIX_FILE);
			Var<BigDecimal> a_e = (Var<BigDecimal>)getCommandLine().get(PFSCommandLine.A_E);
			Var<BigDecimal> a_i = (Var<BigDecimal>)getCommandLine().get(PFSCommandLine.A_I);
			Var<BigDecimal> b_e = (Var<BigDecimal>)getCommandLine().get(PFSCommandLine.B_E);
			Var<BigDecimal> b_i = (Var<BigDecimal>)getCommandLine().get(PFSCommandLine.B_I);

			if("True".equals(write.get())){
				System.out.println("ecriture du fichier ..."); // todo debug apparait deux fois
				try {

					PFUtils.writePFAdjacentMatrix(path,size.get());
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}

			File file = new File(pathMatrixTransitionFile);
			PFSSpreadingGraph pfssge = (PFSSpreadingGraph) SpreadingGraphFactory.getInstance().constructGraph(
					file, 
					TypeGraph.EXCITATORY_GRAPH_PFSPIKE, 
					getCommandLine(),
					focus);
			AccumulationUnitMap accE = new AccumulationUnitMap("AccuMapE",pfssge, pfssge.getDt()/*dt*/, size);
			PFSFiltreLineaire pfseum = new PFSFiltreLineaire(/*accE,4*/);
			UnitMap<BigDecimal, Integer> e = new UnitMap<BigDecimal, Integer>(
					"ExcitatoryMap",
					dt, 
					new Space2D(size, size),
					pfseum,
					accE,
					a_e,
					b_e);		


			PFSSpreadingGraph pfssgi = (PFSSpreadingGraph) SpreadingGraphFactory.getInstance().constructGraph(
					file,
					TypeGraph.INHIBITORY_GRAPH_PFSPIKE,
					getCommandLine(),
					focus);

			AccumulationUnitMap accI = new AccumulationUnitMap("AccuMapI",pfssgi, pfssgi.getDt()/*dt*/, size);
			
			PFSFiltreLineaire pfsium = new PFSFiltreLineaire(/*accI,-1*/);
			UnitMap<BigDecimal, Integer> i = new UnitMap<BigDecimal, Integer>(
					"InhibitoryMap",
					dt, 
					new Space2D(size, size),
					pfsium,
					accI,
					a_i,
					b_i);		

			ConcentrationUnitMap concI = new ConcentrationUnitMap("ConcentrationMapI",pfssgi, pfssgi.getDt()/*dt*/, size);
			ConcentrationUnitMap concE = new ConcentrationUnitMap("ConcentrationMapE",pfssge, pfssge.getDt()/*dt*/, size);
			UnitMap subMap = new UnitMap(CNFT, dt, space, new Sum(0.), e, i,concI, concE);
			cnft = subMap;

		} catch (NetworkException e1) {
			e1.printStackTrace();
		}
	}

}
